package com.yl.payinterface.core.remote.hessian.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.Resource;

import com.lefu.commons.utils.lang.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.lefu.hessian.exception.BusinessException;
import com.lefu.hessian.exception.BusinessRuntimeException;
import com.yl.online.trade.hessian.OnlineTradeHessianService;
import com.yl.payinterface.core.service.AuthSaleInfoService;
import com.yl.payinterface.core.service.InterfaceInfoService;
import com.yl.payinterface.core.service.InterfaceRequestService;
import com.yl.payinterface.core.bean.AuthSaleBean;
import com.yl.payinterface.core.bean.TradeContext;
import com.yl.payinterface.core.enums.BusinessCompleteType;
import com.yl.payinterface.core.enums.CardType;
import com.yl.payinterface.core.enums.InterfaceInfoStatus;
import com.yl.payinterface.core.enums.InterfaceRequestType;
import com.yl.payinterface.core.enums.InterfaceTradeStatus;
import com.yl.payinterface.core.exception.ExceptionMessages;
import com.yl.payinterface.core.handler.AuthPayHandler;
import com.yl.payinterface.core.handler.ChannelReverseHandler;
import com.yl.payinterface.core.handler.InternetbankHandler;
import com.yl.payinterface.core.hessian.AuthPayHessianService;
import com.yl.payinterface.core.model.InterfaceInfo;
import com.yl.payinterface.core.model.InterfaceRequest;
import com.yl.payinterface.core.utils.FeeUtils;

/**
 * 认证支付处理接口实现
 *
 * @author 聚合支付有限公司
 * @since 2016年8月27日
 * @version V1.0.0
 */
@Service("authPayHessianService")
public class AuthPayHessianServiceImpl implements AuthPayHessianService ,ChannelReverseHandler{

	private Logger logger = LoggerFactory.getLogger(AuthPayHessianServiceImpl.class);
	@Resource
	private Map<String, AuthPayHandler> authPayHandlers;
	@Resource
	private Map<String, InternetbankHandler> internetbankHandlers;
	@Resource
	private InterfaceInfoService interfaceInfoService;
	@Resource
	private InterfaceRequestService interfaceRequestService;
	@Resource
	private AuthSaleInfoService authSaleInfoService;
	@Resource
	private OnlineTradeHessianService onlineTradeHessianService;

	@Override
	public Map<String, String> sendVerifyCode(Map<String, String> params) {
		AuthPayHandler tradeHandler = authPayHandlers.get(params.get("interfaceCode"));
		if (tradeHandler == null){
			throw new BusinessRuntimeException(ExceptionMessages.TRADE_PARAMS_NOT_EXISTS);
		}
		// 获取交易配置
		InterfaceInfo interfaceInfo = interfaceInfoService.queryByCode(params.get("interfaceCode"));
		params.put("tradeConfigs", interfaceInfo.getTradeConfigs());
		return tradeHandler.sendVerifyCode(params);
	}

	@SuppressWarnings("deprecation")
	@Override
	public Map<String, String> pay(AuthSaleBean authSaleBean) {
		logger.info("认证支付 支付流程开始:{}", JsonUtils.toJsonString(authSaleBean));
		AuthPayHandler tradeHandler = authPayHandlers.get(authSaleBean.getInterfaceCode());
		if (tradeHandler == null){
			throw new BusinessRuntimeException(ExceptionMessages.TRADE_PARAMS_NOT_EXISTS);
		}

		// 记录接口请求
		InterfaceRequest interfaceRequest = new InterfaceRequest();
		interfaceRequest.setAmount(authSaleBean.getAmount());
		interfaceRequest.setBussinessOrderID(authSaleBean.getOrderCode());
		interfaceRequest.setBussinessFlowID(authSaleBean.getPaymentCode());
		interfaceRequest.setInterfaceInfoCode(authSaleBean.getInterfaceCode());
		interfaceRequest.setInterfaceProviderCode(authSaleBean.getProviderCode());
		interfaceRequest.setOwnerID(authSaleBean.getOwnerId());
		interfaceRequest.setPayInterfaceRequestType(InterfaceRequestType.PAY);
		interfaceRequest.setRequestTime(new Date());
		interfaceRequestService.save(interfaceRequest);

		// 获取交易配置
		InterfaceInfo interfaceInfo = interfaceInfoService.queryByCode(authSaleBean.getInterfaceCode());

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

		// 记录交易信息
		//authSaleInfoService.save(authSaleBean);

		Map<String, String> map = new HashMap<>();
		map.put("tradeConfigs", interfaceInfo.getTradeConfigs());
		map.put("interfaceRequestID", interfaceRequest.getInterfaceRequestID());
		map.put("amount", String.valueOf(authSaleBean.getAmount()));
		map.put("cardNo", authSaleBean.getBankCardNo());
		map.put("bankCardNo", authSaleBean.getBankCardNo());
		map.put("payerName", authSaleBean.getPayerName());
		map.put("interfaceCode", authSaleBean.getInterfaceCode());
		map.put("productName", authSaleBean.getProductName());
		map.put("certNo", authSaleBean.getCertNo());
		map.put("payerMobNo", authSaleBean.getPayerMobNo());
		map.put("merOrderId", authSaleBean.getMerOrderId());
		map.put("providerCode", authSaleBean.getProviderCode());
		map.put("orderTime", DateFormatUtils.format(interfaceRequest.getCreateTime(), "yyyyMMddHHMMss"));
		map.put("ownerId", authSaleBean.getOwnerId());
		map.put("token", authSaleBean.getToken());
		return tradeHandler.authPay(map);
	}

	@Override
	public Map<String,String> sale(AuthSaleBean authSaleBean) {
		AuthPayHandler tradeHandler = authPayHandlers.get(authSaleBean.getInterfaceCode());
		if (tradeHandler == null){
			throw new BusinessRuntimeException(ExceptionMessages.TRADE_PARAMS_NOT_EXISTS);
		}
		// 获取交易配置
		InterfaceInfo interfaceInfo = interfaceInfoService.queryByCode(authSaleBean.getInterfaceCode());
		InterfaceRequest interfaceRequest = interfaceRequestService.queryByInterfaceRequestID(authSaleBean.getMerOrderId());
		authSaleBean.setAmount(interfaceRequest.getAmount());

		// 记录持卡人信息
		//authSaleInfoService.save(authSaleBean);

		Map<String, String> map = new HashMap<>();
		map.put("tradeConfigs", interfaceInfo.getTradeConfigs());
		map.put("interfaceRequestID", authSaleBean.getMerOrderId());
		map.put("token", authSaleBean.getToken());
		map.put("smsCode", authSaleBean.getVerifyCode());
		map.put("interfaceCode", authSaleBean.getInterfaceCode());
		map.put("interfaceRequest", JsonUtils.toJsonString(interfaceRequest));
		map.put("cardNo", authSaleBean.getBankCardNo());
		return tradeHandler.sale(map);
	}


	@SuppressWarnings("unchecked")
	@Override
	public Map<String, String> queryOrder(String merOrderId,String interfaceCode) {
		InternetbankHandler internetbankHandler = internetbankHandlers.get(interfaceCode);
		if (internetbankHandler == null){
			throw new BusinessRuntimeException(ExceptionMessages.TRADE_PARAMS_NOT_EXISTS);
		}
		TradeContext tradeContext = new TradeContext();
		InterfaceRequest queryInterfaceRequest = new InterfaceRequest();
		try {
			queryInterfaceRequest.setInterfaceInfoCode(interfaceCode);
			queryInterfaceRequest.setOriginalInterfaceRequestID(merOrderId);
			tradeContext.setInterfaceRequest(queryInterfaceRequest);

			Map<String, String> payParams = new HashMap<String, String>();
			payParams.put("businessCompleteType", "");
			tradeContext.setRequestParameters(payParams);
			Object[] o = internetbankHandler.query(tradeContext);
			return (Map<String, String>) o[0];
		} catch (BusinessException e) {
			throw new BusinessRuntimeException(ExceptionMessages.UNKNOWN_ERROR);
		}
	}

	@Override
	public void complete(Map<String, String> completeMap) {
		InterfaceInfo interfaceInfo = interfaceInfoService.queryByCode(completeMap.get("interfaceCode"));
		InterfaceRequest interfaceRequest = interfaceRequestService.queryByInterfaceRequestID(completeMap.get("interfaceRequestID"), completeMap.get("interfaceCode"));

		if(!AmountUtils.eq(interfaceRequest.getAmount(), Double.valueOf(completeMap.get("amount")))){
			throw new RuntimeException("接口订单:"+interfaceRequest.getInterfaceRequestID()+",完成时金额不一致");
		}

		if(interfaceRequest.getStatus() != InterfaceTradeStatus.UNKNOWN){
			// 米刷带卡支付 付款结果处理 --特殊流程
			if("MiPay100001_AUTHPAY".equals(interfaceRequest.getInterfaceInfoCode()) ||
					"MiPay100002_AUTHPAY".equals(interfaceRequest.getInterfaceInfoCode())){
				interfaceRequest.setResponseCode(completeMap.get("responseCode"));
				interfaceRequest.setResponseMessage(completeMap.get("responseMessage"));
				interfaceRequestService.modify(interfaceRequest);
				return;
			// 恒信通认证支付流程处理
			} else if ("AUTHPAY_HXT-100001".equals(interfaceRequest.getInterfaceInfoCode())) {
				if (!"9001".equals(completeMap.get("responseCode"))) {
					interfaceRequest.setResponseCode(completeMap.get("responseCode"));
					interfaceRequest.setResponseMessage(completeMap.get("responseMessage"));
					interfaceRequestService.modify(interfaceRequest);
				}
				return;
			} else{
				logger.warn("接口订单:{} 已处理", interfaceRequest.getInterfaceRequestID());
			}
			return;
		}
		interfaceRequest.setInterfaceOrderID(completeMap.get("interfaceOrderID"));
		interfaceRequest.setCompleteTime(new Date());
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

		AuthPayHandler authPayHandler = authPayHandlers.get(interfaceRequest.getInterfaceInfoCode());
		// 获取接口交易配置
		InterfaceInfo interfaceInfo = interfaceInfoService.queryByCode(interfaceRequest.getInterfaceInfoCode());
		Map<String, String> map = new HashMap<>();
		map.put("requestNo", interfaceRequest.getInterfaceRequestID());
		map.put("tradeConfigs", interfaceInfo.getTradeConfigs());
		map = authPayHandler.query(map);
		if(StringUtils.notBlank(map.get("tranStat")) && !"UNKNOWN".equals(map.get("tranStat"))){
			map.put("compType", BusinessCompleteType.AUTO_REPAIR.toString());
			map.put("interfaceCode", interfaceRequest.getInterfaceInfoCode());
			map.put("amount", params.get("amount"));
			complete(map);
			resMap.put("interfaceRequestID", interfaceRequestID);
			resMap.put("tranStat", map.get("tranStat"));
			resMap.put("responseCode", map.get("responseCode"));
			resMap.put("responseMsg", map.get("responseMessage"));
			resMap.put("businessType", "SAILS");
		}else{
			resMap.put("tranStat", "UNKNOWN");
		}
		return resMap;
	}


}
