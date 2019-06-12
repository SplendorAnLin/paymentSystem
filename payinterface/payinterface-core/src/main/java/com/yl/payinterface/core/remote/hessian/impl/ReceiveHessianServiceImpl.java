package com.yl.payinterface.core.remote.hessian.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.lefu.commons.utils.lang.AmountUtils;
import com.lefu.commons.utils.lang.StringUtils;
import com.yl.payinterface.core.service.InterfaceInfoService;
import com.yl.payinterface.core.service.InterfaceRequestService;
import com.yl.payinterface.core.bean.ReceiveQueryBean;
import com.yl.payinterface.core.bean.ReceiveResponseBean;
import com.yl.payinterface.core.bean.ReceiveTradeBean;
import com.yl.payinterface.core.enums.BusinessCompleteType;
import com.yl.payinterface.core.enums.InterfaceInfoStatus;
import com.yl.payinterface.core.enums.InterfaceTradeStatus;
import com.yl.payinterface.core.exception.ExceptionMessages;
import com.yl.payinterface.core.handler.ReceiveHandler;
import com.yl.payinterface.core.hessian.ReceiveHessianService;
import com.yl.payinterface.core.model.InterfaceInfo;
import com.yl.payinterface.core.model.InterfaceRequest;
import com.yl.payinterface.core.utils.FeeUtils;

/**
 * 代收接口系统业务处理
 * 
 * @author 聚合支付有限公司
 * @since 2016年10月22日
 * @version V1.0.0
 */
@Service("receiveHessianService")
public class ReceiveHessianServiceImpl implements ReceiveHessianService {
	
	private static Logger logger = LoggerFactory.getLogger(ReceiveHessianServiceImpl.class);
	
	@Resource
	private Map<String, ReceiveHandler> receiveHandlers;
	@Resource
	private InterfaceInfoService interfaceInfoService;
	@Resource
	private InterfaceRequestService interfaceRequestService;

	@Override
	public Map<String, String> trade(ReceiveTradeBean receiveTradeBean) {
		logger.info("发起[代收]指令，请求参数：{}", receiveTradeBean);
		Map<String,String> resMap = new HashMap<>();
		
		// 查詢接口配置
		InterfaceInfo interfaceInfo = interfaceInfoService.queryByCode(receiveTradeBean.getInterfaceCode());
		
		// 校验参数，通道限额
		check(receiveTradeBean, interfaceInfo);
		
		// 记录接口请求
		InterfaceRequest interfaceRequest = new InterfaceRequest();
		interfaceRequest.setAmount(receiveTradeBean.getAmount());
		interfaceRequest.setBussinessOrderID(receiveTradeBean.getBusinessOrderID());
		interfaceRequest.setCardType(receiveTradeBean.getCardType());
		interfaceRequest.setInterfaceInfoCode(receiveTradeBean.getInterfaceCode());
		interfaceRequest.setInterfaceProviderCode(interfaceInfo.getProvider());
		interfaceRequest.setOwnerID(receiveTradeBean.getOwnerId());
		interfaceRequest.setRequestTime(receiveTradeBean.getBusinessOrderTime());
		interfaceRequestService.save(interfaceRequest);
		
		// 获取通道处理类
		ReceiveHandler receiveHandler = receiveHandlers.get(receiveTradeBean.getInterfaceCode());
		
		// 組裝付款參數
		Map<String,String> reqMap = new HashMap<>();
		reqMap.put("amount", String.valueOf(receiveTradeBean.getAmount()));
		reqMap.put("accountName", receiveTradeBean.getAccountName());
		reqMap.put("accountNo", receiveTradeBean.getAccountNo());
		reqMap.put("accountType", receiveTradeBean.getAccountType());
		reqMap.put("bankCode", receiveTradeBean.getBankCode());
		reqMap.put("bankName", receiveTradeBean.getBankName());
		reqMap.put("cardType", receiveTradeBean.getCardType());
		reqMap.put("cerType", receiveTradeBean.getCertType());
		reqMap.put("cerNo", receiveTradeBean.getCertCode());
		reqMap.put("tradeConfigs", interfaceInfo.getTradeConfigs());
		reqMap.put("remark", receiveTradeBean.getRemark());
		reqMap.put("requestNo", interfaceRequest.getInterfaceRequestID());
		reqMap.put("phone", receiveTradeBean.getPhone());
		reqMap.put("orderTime", new SimpleDateFormat("YYYYMMDDHHmmss").format(interfaceRequest.getCreateTime()));
		
		try {
			resMap = receiveHandler.trade(reqMap);
			if(interfaceInfo.getIsReal().equals("TRUE")){
				complete(resMap,interfaceInfo);
				return resMap;
			}
		} catch (Exception e) {
			logger.error("",e);
		}
		
		resMap = new HashMap<>();
		resMap.put("tranStat", "UNKNOWN");
		return resMap;
	}

	@Override
	public void complete(ReceiveResponseBean receiveResponseBean) {
		// TODO Auto-generated method stub

	}

	@Override
	public Map<String, String> query(ReceiveQueryBean receiveQueryBean) {
		logger.info("发起[付款查詢]指令，请求参数：{}", receiveQueryBean);
		InterfaceRequest interfaceRequest = interfaceRequestService.queryByBusinessOrderID(receiveQueryBean.getBusinessOrderID());
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
		
		ReceiveHandler receiveHandler = receiveHandlers.get(interfaceRequest.getInterfaceInfoCode());
		// 获取接口交易配置
		InterfaceInfo interfaceInfo = interfaceInfoService.queryByCode(interfaceRequest.getInterfaceInfoCode());
		Map<String, String> mapParam = new HashMap<>();
		mapParam.put("orderTime", new SimpleDateFormat("YYYYMMDDHHmmss").format(interfaceRequest.getCreateTime()));
		mapParam.put("requestNo", interfaceRequest.getInterfaceRequestID());
		mapParam.put("tradeConfigs", interfaceInfo.getTradeConfigs());
		mapParam.put("transDate", new SimpleDateFormat("YYYYMMDD").format(interfaceRequest.getRequestTime()));
		
		mapParam = receiveHandler.query(mapParam);
		if(StringUtils.notBlank(mapParam.get("tranStat"))){
			mapParam.put("compType", BusinessCompleteType.AUTO_REPAIR.toString());
			mapParam.put("interfaceCode", interfaceRequest.getInterfaceInfoCode());
			complete(mapParam,interfaceInfo);
		}
		
		resMap.put("tranStat", String.valueOf(interfaceRequest.getStatus()));
		resMap.put("interfaceRequestID", interfaceRequest.getInterfaceRequestID());
		resMap.put("responseCode", interfaceRequest.getResponseCode());
		resMap.put("responseMsg", interfaceRequest.getResponseMessage());
		return resMap;
	}
	
	private void complete(Map<String,String> completeMap,InterfaceInfo interfaceInfo){
		InterfaceRequest request = interfaceRequestService.queryByInterfaceRequestID(completeMap.get("requestNo"), interfaceInfo.getCode());
		if(request.getStatus() != InterfaceTradeStatus.UNKNOWN){
			logger.warn("接口订单:{} 已处理", request.getInterfaceRequestID());
			return;
		}
		
		if(!AmountUtils.eq(request.getAmount(), Double.valueOf(completeMap.get("amount")))){
			throw new RuntimeException("接口订单:"+request.getInterfaceRequestID()+",完成时金额不一致");
		}
		
		request.setBusinessCompleteType(BusinessCompleteType.valueOf(completeMap.get("compType")));
		request.setCompleteTime(new Date());
		request.setFee(FeeUtils.computeFee(Double.valueOf(completeMap.get("amount")), interfaceInfo.getFeeType(), interfaceInfo.getFee()));
		request.setInterfaceOrderID(completeMap.get("interfaceOrderID"));
		request.setResponseCode(completeMap.get("resCode"));
		request.setResponseMessage(completeMap.get("resMsg"));
		request.setStatus(InterfaceTradeStatus.valueOf(completeMap.get("tranStat")));
		interfaceRequestService.modify(request);
		completeMap.put("cost", String.valueOf(request.getFee()));
	}
	
	@SuppressWarnings("deprecation")
	private void check(ReceiveTradeBean receiveTradeBean,InterfaceInfo interfaceInfo){
		if(interfaceInfo.getStatus() == InterfaceInfoStatus.FALSE){
			throw new RuntimeException(ExceptionMessages.INTERFACE_DISABLE);
		}
		if(interfaceInfo.getSingleAmountLimit() != null && interfaceInfo.getSingleAmountLimit() > 0){
			if(!AmountUtils.leq(receiveTradeBean.getAmount(), interfaceInfo.getSingleAmountLimit())){
				throw new RuntimeException(ExceptionMessages.EXCEED_LIMIT);
			}
		}
		if(interfaceInfo.getSingleAmountLimitSmall() != null && interfaceInfo.getSingleAmountLimitSmall() > 0){
			if(AmountUtils.less(receiveTradeBean.getAmount(), interfaceInfo.getSingleAmountLimitSmall())){
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

}
