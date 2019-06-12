package com.yl.payinterface.core.remote.hessian.impl;

import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.lefu.commons.utils.lang.AmountUtils;
import com.lefu.commons.utils.lang.JsonUtils;
import com.lefu.commons.utils.lang.StringUtils;
import com.yl.dpay.hessian.beans.CallbackBean;
import com.yl.dpay.hessian.service.CallbackFacade;
import com.yl.payinterface.core.service.InterfaceInfoService;
import com.yl.payinterface.core.service.InterfaceRequestReverseService;
import com.yl.payinterface.core.service.InterfaceRequestService;
import com.yl.payinterface.core.bean.InterfaceRemitBillHessian;
import com.yl.payinterface.core.enums.BusinessCompleteType;
import com.yl.payinterface.core.enums.InterfaceInfoStatus;
import com.yl.payinterface.core.enums.InterfaceTradeStatus;
import com.yl.payinterface.core.exception.ExceptionMessages;
import com.yl.payinterface.core.handler.RemitHandler;
import com.yl.payinterface.core.hessian.RemitHessianService;
import com.yl.payinterface.core.model.InterfaceInfo;
import com.yl.payinterface.core.model.InterfaceRequest;
import com.yl.payinterface.core.utils.FeeUtils;

/**
 * 付款hessian协议服务接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年10月22日
 * @version V1.0.0
 */
@Service("remitHessianService")
public class RemitHessianServiceImpl implements RemitHessianService {

	private static Logger logger = LoggerFactory.getLogger(RemitHessianServiceImpl.class);

	@Resource
	private InterfaceInfoService interfaceInfoService;
	@Resource
	private InterfaceRequestService interfaceRequestService;
	@Resource
	private Map<String, RemitHandler> remitHandlers;
	@Resource
	private CallbackFacade callbackFacade;
	@Resource
	private InterfaceRequestReverseService interfaceRequestReverseService;

	@Override
	public Map<String, String> remit(InterfaceRemitBillHessian interfaceRemitBillHessian) {
		logger.info("发起[付款]指令，请求参数：{}", interfaceRemitBillHessian);
		Map<String,String> resMap = new HashMap<>();
		
		// 查詢接口配置
		InterfaceInfo interfaceInfo = interfaceInfoService.queryByCode(interfaceRemitBillHessian.getInterfaceCode());
		
		// 校验参数，通道限额
		check(interfaceRemitBillHessian, interfaceInfo);
		
		InterfaceRequest request = interfaceRequestService.queryByBusinessOrderID(interfaceRemitBillHessian.getBillCode());
		
		if(request != null){ //&& !request.getStatus().name().equals("UNKNOWN")
			logger.error("付款订单:["+interfaceRemitBillHessian.getBillCode()+"] 接口已处理");
			resMap = new HashMap<>();
			resMap.put("tranStat", "UNKNOWN");
			return resMap;
		}
		
		// 记录接口请求
		InterfaceRequest interfaceRequest = new InterfaceRequest();
		interfaceRequest.setAmount(interfaceRemitBillHessian.getAmount());
		interfaceRequest.setBussinessOrderID(interfaceRemitBillHessian.getBillCode());
		interfaceRequest.setCardType(interfaceRemitBillHessian.getCardType());
		interfaceRequest.setInterfaceInfoCode(interfaceRemitBillHessian.getInterfaceCode());
		interfaceRequest.setInterfaceProviderCode(interfaceInfo.getProvider());
		interfaceRequest.setOwnerID(interfaceRemitBillHessian.getOwnerId());
		interfaceRequest.setRequestTime(new Date());
		interfaceRequestService.save(interfaceRequest);
		
		// 获取通道处理类
		RemitHandler remitHandler = remitHandlers.get(interfaceRemitBillHessian.getInterfaceCode());
		interfaceRequest = interfaceRequestService.queryByInterfaceRequestID(interfaceRequest.getInterfaceRequestID());
		
		// 組裝付款參數
		Map<String,String> reqMap = new HashMap<>();
		reqMap.put("amount", String.valueOf(interfaceRemitBillHessian.getAmount()));
		reqMap.put("accountName", interfaceRemitBillHessian.getAccountName());
		reqMap.put("accountNo", interfaceRemitBillHessian.getAccountNo());
		reqMap.put("accountType", interfaceRemitBillHessian.getAccountType());
		reqMap.put("bankCode", interfaceRemitBillHessian.getBankCode());
		reqMap.put("bankName", interfaceRemitBillHessian.getBankName());
		reqMap.put("cardType", interfaceRemitBillHessian.getCardType());
		reqMap.put("cerType", interfaceRemitBillHessian.getCerType());
		reqMap.put("cerNo", interfaceRemitBillHessian.getCerNo());
		reqMap.put("cvv", interfaceRemitBillHessian.getCvv());
		reqMap.put("validity", interfaceRemitBillHessian.getValidity());
		reqMap.put("tradeConfigs", interfaceInfo.getTradeConfigs());
		reqMap.put("remark", interfaceRemitBillHessian.getRemark());
		reqMap.put("orderPayCode", interfaceRemitBillHessian.getOrderPayCode());
		reqMap.put("requestNo", interfaceRequest.getInterfaceRequestID());
		reqMap.put("interfaceInfoCode", interfaceRequest.getInterfaceInfoCode());
		reqMap.put("customerNo", interfaceRequest.getOwnerID());
		reqMap.put("orderTime", new SimpleDateFormat("YYYYMMddHHmmss").format(interfaceRequest.getCreateTime()));
		
		try {
			resMap = remitHandler.remit(reqMap);
			if((interfaceInfo.getIsReal().equals("TRUE") && (resMap.get("tranStat").equals("SUCCESS"))) || resMap.get("tranStat").equals("FAILED")){
				complete(resMap,interfaceInfo);
				return resMap;
			}else{
				// 记录补单信息
				interfaceRequestReverseService.recordInterfaceRequestReverse(interfaceRequest, interfaceInfo.getType().toString());
			}
		} catch (Exception e) {
			logger.error("",e);
			// 记录补单信息
			interfaceRequestReverseService.recordInterfaceRequestReverse(interfaceRequest, interfaceInfo.getType().toString());
		}
		
		resMap = new HashMap<>();
		resMap.put("tranStat", "UNKNOWN");
		return resMap;
	}

	@Override
	public Map<String, String> query(Map<String, String> map) {
		logger.info("发起[付款查詢]指令，请求参数：{}", map);
		InterfaceRequest interfaceRequest = interfaceRequestService.queryByBusinessOrderID(map.get("businessOrderID"));
		Map<String, String> resMap = new HashMap<>();
		if(interfaceRequest == null){
			resMap.put("tranStat", "FAILED");
			resMap.put("responseCode", "I1010");
			resMap.put("responseMsg", "支付请求不存在");
			return resMap;
		}
		if(interfaceRequest.getStatus() != InterfaceTradeStatus.UNKNOWN){
			resMap.put("tranStat", String.valueOf(interfaceRequest.getStatus()));
			resMap.put("interfaceRequestID", interfaceRequest.getInterfaceRequestID());
			resMap.put("responseCode", interfaceRequest.getResponseCode());
			resMap.put("responseMsg", interfaceRequest.getResponseMessage());
			return resMap;
		}
		
		RemitHandler remitHandler = remitHandlers.get(interfaceRequest.getInterfaceInfoCode());
		// 获取接口交易配置
		InterfaceInfo interfaceInfo = interfaceInfoService.queryByCode(interfaceRequest.getInterfaceInfoCode());
		Map<String, String> mapParam = new HashMap<>();
		mapParam.put("orderTime", new SimpleDateFormat("YYYYMMddHHmmss").format(interfaceRequest.getCreateTime()));
		mapParam.put("requestNo", interfaceRequest.getInterfaceRequestID());
		mapParam.put("tradeConfigs", interfaceInfo.getTradeConfigs());
		mapParam.put("transDate", new SimpleDateFormat("YYYYMMdd").format(interfaceRequest.getRequestTime()));
		mapParam.put("interfaceInfoCode", interfaceRequest.getInterfaceInfoCode());

		mapParam = remitHandler.query(mapParam);
		if(StringUtils.notBlank(mapParam.get("tranStat"))){
			mapParam.put("compType", BusinessCompleteType.AUTO_REPAIR.toString());
			mapParam.put("interfaceCode", interfaceRequest.getInterfaceInfoCode());
			complete(mapParam);
		}
		
		resMap.put("tranStat", mapParam.get("tranStat"));
		resMap.put("interfaceRequestID", interfaceRequest.getInterfaceRequestID());
		resMap.put("responseCode", interfaceRequest.getResponseCode());
		resMap.put("responseMsg", interfaceRequest.getResponseMessage());
		return resMap;
 	}
	
	@SuppressWarnings("deprecation")
	private void check(InterfaceRemitBillHessian interfaceRemitBillHessian,InterfaceInfo interfaceInfo){
		if(interfaceInfo.getStatus() == InterfaceInfoStatus.FALSE){
			throw new RuntimeException(ExceptionMessages.INTERFACE_DISABLE);
		}
		if(interfaceInfo.getSingleAmountLimit() != null && interfaceInfo.getSingleAmountLimit() > 0){
			if(!AmountUtils.leq(interfaceRemitBillHessian.getAmount(), interfaceInfo.getSingleAmountLimit())){
				throw new RuntimeException(ExceptionMessages.EXCEED_LIMIT);
			}
		}
		if(interfaceInfo.getSingleAmountLimitSmall() != null && interfaceInfo.getSingleAmountLimitSmall() > 0){
			if(AmountUtils.less(interfaceRemitBillHessian.getAmount(), interfaceInfo.getSingleAmountLimitSmall())){
				throw new RuntimeException(ExceptionMessages.LOWER_LIMIT);
			}
		}
		
		if(StringUtils.notBlank(interfaceInfo.getStartTime()) && StringUtils.notBlank(interfaceInfo.getEndTime())){
			Date now = new Date();
			if(Integer.parseInt((now.getHours() + "" + (now.getMinutes()<10?"0"+now.getMinutes():now.getMinutes()))) < Integer.parseInt(interfaceInfo.getStartTime().replace(":", "")) ||
					Integer.parseInt((now.getHours() + "" + (now.getMinutes()<10?"0"+now.getMinutes():now.getMinutes()))) >= Integer.parseInt(interfaceInfo.getEndTime().replace(":", ""))){
					throw new RuntimeException(ExceptionMessages.NOT_CHENNEL_TIME);
			}
		}else if(StringUtils.notBlank(interfaceInfo.getStartTime())){
			Date now = new Date();
			if(Integer.parseInt((now.getHours() + "" + (now.getMinutes()<10?"0"+now.getMinutes():now.getMinutes()))) < Integer.parseInt(interfaceInfo.getStartTime().replace(":", ""))){
					throw new RuntimeException(ExceptionMessages.NOT_CHENNEL_TIME);
			}
		}else if(StringUtils.notBlank(interfaceInfo.getEndTime())){
			Date now = new Date();
			if(Integer.parseInt((now.getHours() + "" + (now.getMinutes()<10?"0"+now.getMinutes():now.getMinutes()))) >= Integer.parseInt(interfaceInfo.getEndTime().replace(":", ""))){
					throw new RuntimeException(ExceptionMessages.NOT_CHENNEL_TIME);
			}
		}
	}
	
	private void complete(Map<String,String> completeMap,InterfaceInfo interfaceInfo){
		InterfaceRequest request = interfaceRequestService.queryByInterfaceRequestID(completeMap.get("requestNo"));
		if(request.getStatus() != InterfaceTradeStatus.UNKNOWN){
			logger.warn("接口订单:{} 已处理", request.getInterfaceRequestID());
			return;
		}
		
		if("UNKNOWN".equals(completeMap.get("tranStat"))){
			return;
		}
		
		if(StringUtils.notBlank(completeMap.get("amount"))){
			if(!AmountUtils.eq(AmountUtils.round(request.getAmount(), 2, RoundingMode.HALF_UP), Double.valueOf(completeMap.get("amount")))){
				throw new RuntimeException("接口订单:"+request.getInterfaceRequestID()+",完成时金额不一致 订单金额:["+request.getAmount()+"],通道订单金额:["+Double.valueOf(completeMap.get("amount"))+"]");
			}
		}
		
		request.setBusinessCompleteType(BusinessCompleteType.valueOf(completeMap.get("compType")));
		request.setCompleteTime(new Date());
		request.setFee(FeeUtils.computeFee(request.getAmount(), interfaceInfo.getFeeType(), interfaceInfo.getFee()));
		request.setInterfaceOrderID(completeMap.get("interfaceOrderID"));
		request.setResponseCode(completeMap.get("resCode"));
		request.setResponseMessage(completeMap.get("resMsg"));
		request.setStatus(InterfaceTradeStatus.valueOf(completeMap.get("tranStat")));
		interfaceRequestService.modify(request);
		completeMap.put("cost", request.getFee().toString());
		logger.info("接口系统订单处理完成:{}", JsonUtils.toJsonString(request));
	}

	@Override
	public void complete(Map<String, String> completeMap) {
		InterfaceInfo interfaceInfo = interfaceInfoService.queryByCode(completeMap.get("interfaceCode"));
		InterfaceRequest request = interfaceRequestService.queryByInterfaceRequestID(completeMap.get("requestNo"), completeMap.get("interfaceCode"));
		if(request.getStatus() != InterfaceTradeStatus.UNKNOWN){
			logger.warn("接口订单:{} 已处理", request.getInterfaceRequestID());
			return;
		}
		
		if("UNKNOWN".equals(completeMap.get("tranStat"))){
			return;
		}
		
		if(StringUtils.notBlank(completeMap.get("amount"))){
			if(!AmountUtils.eq(AmountUtils.round(request.getAmount(), 2, RoundingMode.HALF_UP), Double.valueOf(completeMap.get("amount")))){
				throw new RuntimeException("接口订单:"+request.getInterfaceRequestID()+",完成时金额不一致 订单金额:["+request.getAmount()+"],通道订单金额:["+Double.valueOf(completeMap.get("amount"))+"]");
			}
		}
		
		request.setBusinessCompleteType(BusinessCompleteType.valueOf(completeMap.get("compType")));
		request.setCompleteTime(new Date());
		request.setFee(FeeUtils.computeFee(request.getAmount(), interfaceInfo.getFeeType(), interfaceInfo.getFee()));
		request.setInterfaceOrderID(completeMap.get("interfaceOrderID"));
		request.setResponseCode(completeMap.get("resCode"));
		request.setResponseMessage(completeMap.get("resMsg"));
		request.setStatus(InterfaceTradeStatus.valueOf(completeMap.get("tranStat")));
		interfaceRequestService.modify(request);
		logger.info("接口系统订单处理完成:{}", JsonUtils.toJsonString(request));
		
		CallbackBean callbackBean = new CallbackBean();
		callbackBean.setAmount(request.getAmount());
		callbackBean.setFlowNo(request.getBussinessOrderID());
		callbackBean.setInterfaceRequestId(request.getInterfaceRequestID());
		callbackBean.setResponseCode(request.getResponseCode());
		callbackBean.setResponseMsg(request.getResponseMessage());
		callbackBean.setStatus(completeMap.get("tranStat"));
		callbackBean.setInterfaceCode(completeMap.get("interfaceCode"));
		callbackBean.setFee(request.getFee());
		callbackFacade.callback(callbackBean);
		logger.info("接口系统通知代付系统:{}", JsonUtils.toJsonString(callbackBean));
	}

}
