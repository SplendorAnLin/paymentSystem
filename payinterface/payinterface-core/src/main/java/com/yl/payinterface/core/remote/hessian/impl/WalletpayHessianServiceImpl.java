package com.yl.payinterface.core.remote.hessian.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.lefu.commons.utils.lang.AmountUtils;
import com.lefu.commons.utils.lang.StringUtils;
import com.lefu.hessian.exception.BusinessRuntimeException;
import com.yl.online.trade.hessian.OnlineTradeHessianService;
import com.yl.payinterface.core.service.InterfaceInfoService;
import com.yl.payinterface.core.service.InterfaceRequestReverseService;
import com.yl.payinterface.core.service.InterfaceRequestService;
import com.yl.payinterface.core.InterfaceResponseCodeChange;
import com.yl.payinterface.core.bean.WalletSalesQueryBean;
import com.yl.payinterface.core.bean.WalletSalesTradeBean;
import com.yl.payinterface.core.enums.BusinessCompleteType;
import com.yl.payinterface.core.enums.CardType;
import com.yl.payinterface.core.enums.InterfaceInfoStatus;
import com.yl.payinterface.core.enums.InterfaceRequestType;
import com.yl.payinterface.core.enums.InterfaceTradeStatus;
import com.yl.payinterface.core.exception.ExceptionMessages;
import com.yl.payinterface.core.handler.ChannelReverseHandler;
import com.yl.payinterface.core.handler.WalletPayHandler;
import com.yl.payinterface.core.hessian.WalletpayHessianService;
import com.yl.payinterface.core.model.InterfaceInfo;
import com.yl.payinterface.core.model.InterfaceRequest;
import com.yl.payinterface.core.utils.FeeUtils;

/**
 * 钱包支付处理接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年10月22日
 * @version V1.0.0
 */
@Service("walletpayHessianService")
public class WalletpayHessianServiceImpl implements WalletpayHessianService,ChannelReverseHandler {
	
	private Logger logger = LoggerFactory.getLogger(WalletpayHessianServiceImpl.class);
	
	@Resource
	private InterfaceInfoService interfaceInfoService;
	@Resource
	private Map<String, WalletPayHandler> walletPayHandlers;
	@Resource
	private InterfaceRequestService interfaceRequestService;
	@Resource
	private OnlineTradeHessianService onlineTradeHessianService;
	@Resource
	private InterfaceRequestReverseService interfaceRequestReverseService;

	@SuppressWarnings("deprecation")
	@Override
	public Map<String, String> pay(WalletSalesTradeBean walletSalesTradeBean) {
		logger.info("钱包支付交易业务请求参数:{}",walletSalesTradeBean);
		Map<String, String> resMap = null;
		WalletPayHandler walletPayHandler = walletPayHandlers.get(walletSalesTradeBean.getInterfaceCode());
		if(walletPayHandler == null){
			throw new BusinessRuntimeException(ExceptionMessages.TRADE_PARAMS_NOT_EXISTS);
		}
		
		// 记录接口请求
		InterfaceRequest interfaceRequest = new InterfaceRequest();
		interfaceRequest.setAmount(walletSalesTradeBean.getAmount());
		interfaceRequest.setBussinessOrderID(walletSalesTradeBean.getBussinessFlowID());
		interfaceRequest.setBussinessFlowID(walletSalesTradeBean.getBusinessOrderID());
		interfaceRequest.setClientIP(walletSalesTradeBean.getClientIp());
		interfaceRequest.setClientRefer(walletSalesTradeBean.getReferer());
		interfaceRequest.setInterfaceInfoCode(walletSalesTradeBean.getInterfaceCode());
		interfaceRequest.setInterfaceProviderCode(walletSalesTradeBean.getInterfaceProviderCode());
		interfaceRequest.setOwnerID(walletSalesTradeBean.getOwnerID());
		interfaceRequest.setPayInterfaceRequestType(InterfaceRequestType.PAY);
		interfaceRequest.setRequestTime(new Date());
		interfaceRequestService.save(interfaceRequest);
		
		// 获取接口交易配置
		InterfaceInfo interfaceInfo = interfaceInfoService.queryByCode(walletSalesTradeBean.getInterfaceCode());
		
		if(interfaceInfo.getStatus() == InterfaceInfoStatus.FALSE){
			throw new RuntimeException(ExceptionMessages.INTERFACE_DISABLE);
		}
		if(interfaceInfo.getSingleAmountLimit() != null && interfaceInfo.getSingleAmountLimit() > 0){
			if(!AmountUtils.leq(interfaceRequest.getAmount(), interfaceInfo.getSingleAmountLimit())){
				throw new RuntimeException(ExceptionMessages.EXCEED_LIMIT);
			}
		}
		if(interfaceInfo.getSingleAmountLimitSmall() != null && interfaceInfo.getSingleAmountLimitSmall() > 0){
			if(AmountUtils.less(interfaceRequest.getAmount(), interfaceInfo.getSingleAmountLimitSmall())){
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
		
		Map<String, String> payMap = new HashMap<>();
		payMap.put("amount", String.valueOf(walletSalesTradeBean.getAmount()));
		payMap.put("interfaceRequestID", interfaceRequest.getInterfaceRequestID());
		payMap.put("tradeConfigs", interfaceInfo.getTradeConfigs());
		payMap.put("ClientIp", walletSalesTradeBean.getClientIp());
		payMap.put("authCode", walletSalesTradeBean.getAuthCode());
		payMap.put("productName", walletSalesTradeBean.getProductName());
		payMap.put("interfaceInfoCode", walletSalesTradeBean.getInterfaceCode());
		payMap.put("interfaceType", interfaceInfo.getType().toString());
		payMap.put("orderTime", new SimpleDateFormat("yyyyMMddHHmmss").format(interfaceRequest.getCreateTime()));

		try {
			resMap = walletPayHandler.pay(payMap);
			if(StringUtils.notBlank(resMap.get("merchantNo"))){
				interfaceRequest = interfaceRequestService.queryByInterfaceRequestID(interfaceRequest.getInterfaceRequestID());
				interfaceRequest.setRemark(resMap.get("merchantNo"));
				if (null != resMap.get("interfaceOrderID")) {
                    interfaceRequest.setInterfaceOrderID(resMap.get("interfaceOrderID"));
                }
				interfaceRequestService.modify(interfaceRequest);
			}
			
			// 记录补单信息
			interfaceRequestReverseService.recordInterfaceRequestReverse(interfaceRequest, interfaceInfo.getType().toString());
			
			if (com.yl.payinterface.core.enums.InterfaceType.ALIPAYMICROPAY == interfaceInfo.getType()
					|| com.yl.payinterface.core.enums.InterfaceType.WXMICROPAY == interfaceInfo.getType()) {
				// 返回码转换
				Map<String, String> insideRespMap =InterfaceResponseCodeChange.responseCodeChange(walletSalesTradeBean.getInterfaceCode(), resMap.get("responseCode"));
				resMap.put("responseCode", insideRespMap.get("insideRespCode"));
				resMap.put("responseMessage", insideRespMap.get("insideRespDesc"));
				// 如果是微信刷卡和支付刷卡，则返回结果
				if (!"UNKNOWN".equals(resMap.get("tranStat"))) {
					this.complete(resMap);
				}
			}
		} catch (Exception e) {
			logger.error("interfaceRequestID:{} ,Wallet Pay exception:{}", interfaceRequest.getInterfaceRequestID(), e);
		}
		return resMap;
	}

	@Override
	public Map<String, String> query(WalletSalesQueryBean walletSalesQueryBean) {
		InterfaceRequest interfaceRequest = interfaceRequestService.queryByBusinessOrderID(walletSalesQueryBean.getBusinessOrderID());
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
		
		WalletPayHandler walletPayHandler = walletPayHandlers.get(interfaceRequest.getInterfaceInfoCode());
		// 获取接口交易配置
		InterfaceInfo interfaceInfo = interfaceInfoService.queryByCode(interfaceRequest.getInterfaceInfoCode());
		Map<String, String> map = new HashMap<>();
		map.put("interfaceRequestID", interfaceRequest.getInterfaceRequestID());
		map.put("tradeConfigs", interfaceInfo.getTradeConfigs());
		map.put("orderTime", new SimpleDateFormat("yyyyMMddHHmmss").format(interfaceRequest.getCreateTime()));
		map.put("interfaceType", interfaceInfo.getType().name());
		map.put("interfaceOrderId", interfaceRequest.getInterfaceOrderID());
		map = walletPayHandler.query(map);
		if(StringUtils.notBlank(map.get("tranStat")) && "SUCCESS".equals(map.get("tranStat")) ){
			map.put("businessCompleteType", BusinessCompleteType.AUTO_REPAIR.toString());
			map.put("interfaceCode", interfaceRequest.getInterfaceInfoCode());
			complete(map);
		}
		
		resMap.put("tranStat", map.get("tranStat"));
		resMap.put("interfaceRequestID", interfaceRequest.getInterfaceRequestID());
		resMap.put("responseCode", interfaceRequest.getResponseCode());
		resMap.put("responseMsg", interfaceRequest.getResponseMessage());
		resMap.put("merchantNo", interfaceRequest.getRemark());
		return resMap;
	}

	@Override
	public void complete(Map<String, String> completeMap) {
		logger.info("钱包完成支付:{}",completeMap);
		InterfaceInfo interfaceInfo = interfaceInfoService.queryByCode(completeMap.get("interfaceCode"));
		InterfaceRequest interfaceRequest = interfaceRequestService.queryByInterfaceRequestID(completeMap.get("interfaceRequestID"), completeMap.get("interfaceCode"));
		if(interfaceRequest.getStatus() != InterfaceTradeStatus.UNKNOWN){
			logger.warn("接口订单:{} 已处理", interfaceRequest.getInterfaceRequestID());
			return;
		}
		
		if(!AmountUtils.eq(interfaceRequest.getAmount(), Double.valueOf(completeMap.get("amount")))){
			throw new RuntimeException("接口订单:"+interfaceRequest.getInterfaceRequestID()+",完成时金额不一致");
		}
		
		interfaceRequest.setInterfaceOrderID(completeMap.get("interfaceOrderID"));
		interfaceRequest.setCompleteTime(new Date());
		if ("HFB100003-UNIONPAY_NATIVE".equals(completeMap.get("interfaceCode")) && AmountUtils.geq(interfaceRequest.getAmount(), 1000)) {
			interfaceInfo.setFee(0.0075D);
		}
		interfaceRequest.setFee(FeeUtils.computeFee(interfaceRequest.getAmount(), interfaceInfo.getFeeType(), interfaceInfo.getFee()));
		interfaceRequest.setResponseCode(completeMap.get("responseCode"));
		interfaceRequest.setResponseMessage(completeMap.get("responseMessage"));
		interfaceRequest.setStatus(InterfaceTradeStatus.valueOf(completeMap.get("tranStat")));
		interfaceRequest.setBusinessCompleteType(BusinessCompleteType.valueOf(completeMap.get("businessCompleteType").toString()));
		interfaceRequestService.modify(interfaceRequest);
		
		callBackOnlineTradeResult(interfaceRequest, interfaceInfo);
	}
	
	/**
	 * 支付结果通知
	 * @param interfaceRequest 提供方接口请求记录
	 * @param interfaceInfo 是否实时通知交易系统标记
	 */
	public void callBackOnlineTradeResult(InterfaceRequest interfaceRequest, InterfaceInfo interfaceInfo){

		// 组装通知交易系统参数
		Map<String, String> payResultParams = new LinkedHashMap<String, String>();
		payResultParams.put("interfaceOrderID", interfaceRequest.getInterfaceOrderID());
		payResultParams.put("interfaceRequestID", interfaceRequest.getInterfaceRequestID());
		payResultParams.put("interfaceProvider", interfaceRequest.getInterfaceProviderCode());
		payResultParams.put("cardType", StringUtils.isBlank(interfaceRequest.getCardType()) ? CardType.DEBIT_CARD.name() : interfaceRequest.getCardType());
		payResultParams.put("transStatus", interfaceRequest.getStatus().toString());
		payResultParams.put("responseMessage", interfaceRequest.getResponseMessage());
		payResultParams.put("responseCode", interfaceRequest.getResponseCode());
		payResultParams.put("businessOrderID", interfaceRequest.getBussinessOrderID());
		payResultParams.put("businessFlowID", interfaceRequest.getBussinessFlowID());
		payResultParams.put("amount", String.valueOf(interfaceRequest.getAmount()));
		payResultParams.put("interfaceFee", String.valueOf(interfaceRequest.getFee()));
		payResultParams.put("businessType", "SAILS");
		onlineTradeHessianService.complete(payResultParams);
	}

	@Override
	public Map<String, String> reverse(Map<String, String> params) {
		String interfaceRequestID = params.get("interfaceRequestID");
		InterfaceRequest interfaceRequest = interfaceRequestService.queryByInterfaceRequestID(interfaceRequestID);
		Map<String, String> resMap = new HashMap<>();
		if(interfaceRequest == null){
			resMap.put("tranStat", InterfaceTradeStatus.FAILED.toString());
			return resMap;
		}
		
		if(interfaceRequest.getStatus() != InterfaceTradeStatus.UNKNOWN){
			resMap.put("tranStat", interfaceRequest.getStatus().toString());
			return resMap;
		}
		
		WalletPayHandler walletPayHandler = walletPayHandlers.get(interfaceRequest.getInterfaceInfoCode());
		// 获取接口交易配置
		InterfaceInfo interfaceInfo = interfaceInfoService.queryByCode(interfaceRequest.getInterfaceInfoCode());
		Map<String, String> map = new HashMap<>();
		map.put("interfaceRequestID", interfaceRequest.getInterfaceRequestID());
		map.put("tradeConfigs", interfaceInfo.getTradeConfigs());
		map.put("orderTime", new SimpleDateFormat("yyyyMMddHHmmss").format(interfaceRequest.getCreateTime()));
		map.put("interfaceType", interfaceInfo.getType().name());
		map.put("interfaceOrderId", interfaceRequest.getInterfaceOrderID());
		map = walletPayHandler.query(map);
		if(StringUtils.notBlank(map.get("tranStat")) && "UNKNOWN".equals(map.get("tranStat"))){
			map.put("compType", BusinessCompleteType.AUTO_REPAIR.toString());
			map.put("interfaceCode", interfaceRequest.getInterfaceInfoCode());
			map.put("amount", params.get("amount"));
			complete(map);
			resMap.put("interfaceRequestID", interfaceRequestID);
			resMap.put("tranStat", String.valueOf(interfaceRequest.getStatus()));
			resMap.put("responseCode", interfaceRequest.getResponseCode());
			resMap.put("responseMsg", interfaceRequest.getResponseMessage());
			resMap.put("businessType", "SAILS");
		}else{
			resMap.put("tranStat", "UNKNOWN");
		}
		return resMap;
	}


}
