package com.yl.payinterface.core.remote.hessian.impl;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

import javax.annotation.Resource;

import com.yl.payinterface.core.service.*;

import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.lefu.commons.utils.lang.AmountUtils;
import com.lefu.commons.utils.lang.DateFormatUtils;
import com.lefu.commons.utils.lang.DateUtils;
import com.lefu.commons.utils.lang.JsonUtils;
import com.lefu.commons.utils.lang.StringUtils;
import com.lefu.hessian.exception.BusinessRuntimeException;
import com.yl.account.api.bean.AccountBussinessInterfaceBean;
import com.yl.account.api.bean.request.AccountPreFreeze;
import com.yl.account.api.bean.response.AccountPreFreezeResponse;
import com.yl.account.api.dubbo.AccountInterface;
import com.yl.account.api.dubbo.AccountQueryInterface;
import com.yl.account.api.enums.HandlerResult;
import com.yl.boss.api.bean.TransCardBean;
import com.yl.mq.producer.client.ProducerClient;
import com.yl.mq.producer.client.ProducerMessage;
import com.yl.online.trade.hessian.OnlineTradeHessianService;
import com.yl.payinterface.core.C;
import com.yl.payinterface.core.bean.QuickPayBean;
import com.yl.payinterface.core.bean.TradeContext;
import com.yl.payinterface.core.enums.AuthPayBindCardInfoStatus;
import com.yl.payinterface.core.enums.BusinessCompleteType;
import com.yl.payinterface.core.enums.CardType;
import com.yl.payinterface.core.enums.InterfaceInfoStatus;
import com.yl.payinterface.core.enums.InterfaceRequestType;
import com.yl.payinterface.core.enums.InterfaceTradeStatus;
import com.yl.payinterface.core.exception.ExceptionMessages;
import com.yl.payinterface.core.handler.ChannelReverseHandler;
import com.yl.payinterface.core.handler.QuickPayHandler;
import com.yl.payinterface.core.hessian.QuickPayHessianService;
import com.yl.payinterface.core.model.BindCardInfo;
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
@Service("quickPayHessianService")
public class QuickPayHessianServiceImpl implements QuickPayHessianService ,ChannelReverseHandler{

	private Logger logger = LoggerFactory.getLogger(QuickPayHessianServiceImpl.class);
	
	@Resource
	private Map<String, QuickPayHandler> quickPayHandlers;
	@Resource
	private InterfaceInfoService interfaceInfoService;
	@Resource
	private InterfaceRequestService interfaceRequestService;
	@Resource
	private OnlineTradeHessianService onlineTradeHessianService;
	@Resource
	private AccountInterface accountInterface;
	@Resource
	private AccountQueryInterface accountQueryInterface;
	@Resource
	private QuickPayFeeService quickPayFeeService;
	@Resource
	private ProducerClient producerClient;
	@Resource
	private BindCardInfoService bindCardInfoService;
	@Resource
	private InterfaceRequestReverseService interfaceRequestReverseService;
	
	private static Properties prop = new Properties();
	static {
		try {
			prop.load(QuickPayHessianServiceImpl.class.getClassLoader().getResourceAsStream("producer-client.properties"));
		} catch (IOException e) {}
	}

	@Override
	public Map<String, String> sendVerifyCode(Map<String, String> params) {
		QuickPayHandler tradeHandler = quickPayHandlers.get(params.get("interfaceCode"));
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
	public Map<String, String> pay(QuickPayBean quickPayBean) {
		logger.info("认证支付 支付流程开始:{}", JsonUtils.toJsonString(quickPayBean));
		QuickPayHandler tradeHandler = quickPayHandlers.get(quickPayBean.getInterfaceCode());
		if (tradeHandler == null){
			throw new BusinessRuntimeException(ExceptionMessages.TRADE_PARAMS_NOT_EXISTS);
		}

		// 记录接口请求
		InterfaceRequest interfaceRequest = new InterfaceRequest();
		interfaceRequest.setAmount(quickPayBean.getAmount());
		interfaceRequest.setBussinessOrderID(quickPayBean.getOrderCode());
		interfaceRequest.setBussinessFlowID(quickPayBean.getPaymentCode());
		interfaceRequest.setInterfaceInfoCode(quickPayBean.getInterfaceCode());
		interfaceRequest.setInterfaceProviderCode(quickPayBean.getProviderCode());
		interfaceRequest.setOwnerID(quickPayBean.getOwnerId());
		interfaceRequest.setPayInterfaceRequestType(InterfaceRequestType.PAY);
		interfaceRequest.setRequestTime(new Date());
		// 如果selletType不为空，则判断结算类型
        if (StringUtils.notBlank(quickPayBean.getSettleType())) {
        	Map<String, Object> map = new HashMap<String, Object>();
        	map.put("settleType", quickPayBean.getSettleType());
        	// 如果是费率
        	if (C.SETTLE_TYPE_FEE.equals(quickPayBean.getSettleType())) {
        		map.put("quickPayFee", quickPayBean.getQuickPayFee());
        		map.put("remitFee", quickPayBean.getRemitFee());
        		// 如果是金额
        	} else if (C.SETTLE_TYPE_AMOUNT.equals(quickPayBean.getSettleType())) {
        		map.put("settleAmount", quickPayBean.getSettleAmount());
        	}
        	interfaceRequest.setRemark(JsonUtils.toJsonString(map));
        }
//		if (quickPayBean.getSettleAmount() != null) {
//			interfaceRequest.setRemark(Double.toString(quickPayBean.getSettleAmount()));
//		}
		interfaceRequestService.save(interfaceRequest);

		// 获取交易配置
		InterfaceInfo interfaceInfo = interfaceInfoService.queryByCode(quickPayBean.getInterfaceCode());

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
		//authSaleInfoService.save(quickPayBean);

		if("QUICKPAY_KINGPASS-100001".equals(interfaceInfo.getCode())){
			// 记录补单信息
			interfaceRequestReverseService.recordInterfaceRequestReverse(interfaceRequest, interfaceInfo.getType().toString());
		}

		Map<String, String> map = new HashMap<>();
		map.put("tradeConfigs", interfaceInfo.getTradeConfigs());
		map.put("interfaceRequestID", interfaceRequest.getInterfaceRequestID());
		map.put("amount", String.valueOf(quickPayBean.getAmount()));
		map.put("cardNo", quickPayBean.getBankCardNo());
		map.put("bankCardNo", quickPayBean.getBankCardNo());
		map.put("payerName", quickPayBean.getPayerName());
		map.put("interfaceCode", quickPayBean.getInterfaceCode());
		map.put("productName", quickPayBean.getProductName());
		map.put("certNo", quickPayBean.getCertNo());
		map.put("payerMobNo", quickPayBean.getPayerMobNo());
		map.put("merOrderId", quickPayBean.getMerOrderId());
		map.put("providerCode", quickPayBean.getProviderCode());
		map.put("orderTime", DateFormatUtils.format(interfaceRequest.getCreateTime(), "yyyyMMddHHMMss"));
		map.put("ownerId", quickPayBean.getOwnerId());
		map.put("token", quickPayBean.getToken());
		if (StringUtils.notBlank(quickPayBean.getSettleType())) {
			map.put("settleType", quickPayBean.getSettleType());
			// 如果是费率
			if (C.SETTLE_TYPE_FEE.equals(quickPayBean.getSettleType())) {
				map.put("quickPayFee", Double.toString(quickPayBean.getQuickPayFee()));
				map.put("remitFee", Double.toString(quickPayBean.getRemitFee()));
				// 如果是金额
			} else if (C.SETTLE_TYPE_AMOUNT.equals(quickPayBean.getSettleType())) {
				map.put("settleAmount", Double.toString(quickPayBean.getSettleAmount()));
			}
		}
		return tradeHandler.authPay(map);
	}

	@Override
	public Map<String,String> sale(QuickPayBean quickPayBean) {
		QuickPayHandler tradeHandler = quickPayHandlers.get(quickPayBean.getInterfaceCode());
		if (tradeHandler == null){
			throw new BusinessRuntimeException(ExceptionMessages.TRADE_PARAMS_NOT_EXISTS);
		}
		// 获取交易配置
		InterfaceInfo interfaceInfo = interfaceInfoService.queryByCode(quickPayBean.getInterfaceCode());
		InterfaceRequest interfaceRequest = interfaceRequestService.queryByInterfaceRequestID(quickPayBean.getMerOrderId());
		quickPayBean.setAmount(interfaceRequest.getAmount());

		// 记录持卡人信息
		//authSaleInfoService.save(quickPayBean);

		Map<String, String> map = new HashMap<>();
		map.put("tradeConfigs", interfaceInfo.getTradeConfigs());
		map.put("interfaceRequestID", quickPayBean.getMerOrderId());
		map.put("token", quickPayBean.getToken());
		map.put("smsCode", quickPayBean.getVerifyCode());
		map.put("interfaceCode", quickPayBean.getInterfaceCode());
		map.put("interfaceRequest", JsonUtils.toJsonString(interfaceRequest));
		map.put("cardNo", quickPayBean.getBankCardNo());
		map.put("ownerId", quickPayBean.getOwnerId());
		map.put("amount", String.valueOf(interfaceRequest.getAmount()));
		return tradeHandler.sale(map);
	}


	@Override
	public Map<String, String> queryOrder(String merOrderId,String interfaceCode) {
		InterfaceInfo interfaceInfo = interfaceInfoService.queryByCode(interfaceCode);
		QuickPayHandler quickPayHandler = quickPayHandlers.get(interfaceCode);
		if (quickPayHandler == null){
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
			payParams.put("interfaceRequestID", merOrderId);
			payParams.put("interfaceInfoCode", interfaceCode);
			payParams.put("tradeConfigs", interfaceInfo.getTradeConfigs());
			Map<String, String> queryMap = quickPayHandler.query(payParams);
			if ("SUCCESS".equals(queryMap.get("tranStat"))) {
				complete(queryMap);
			}
			return queryMap;
		} catch (Exception e) {
			logger.info("订单号码：{}，接口编号：{}。查询通道报错：{}", merOrderId, interfaceCode, e);
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
			if("MiPay100001_QUICKPAY".equals(interfaceRequest.getInterfaceInfoCode()) ||
					"MiPay100002_QUICKPAY".equals(interfaceRequest.getInterfaceInfoCode())){
				interfaceRequest.setResponseCode(completeMap.get("responseCode"));
				interfaceRequest.setResponseMessage(completeMap.get("responseMessage"));
				interfaceRequestService.modify(interfaceRequest);
				return;
			// 恒信通认证支付流程处理
			} else if("QUICKPAY_MiPay-100002".equals(interfaceRequest.getInterfaceInfoCode())){
				interfaceRequest.setResponseCode(completeMap.get("responseCode"));
				interfaceRequest.setResponseMessage(completeMap.get("responseMessage"));
				interfaceRequestService.modify(interfaceRequest);
				return;
			// 恒信通认证支付流程处理
			} else if ("QUICKPAY_HXT-100001".equals(interfaceRequest.getInterfaceInfoCode())) {
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
		// 如果订单状态为成功，则发起欲冻，发送生成代付订单的mq
		Properties properties = JsonUtils.toObject(interfaceInfo.getTradeConfigs(), new TypeReference<Properties>() {});
		Map<String, String> mqMap = new HashMap<>();
		if (InterfaceTradeStatus.SUCCESS == interfaceRequest.getStatus() && "TRUE".equals(properties.get("autoDpay"))) {
			// 查询账户信息
			Map<String, Object> map = new HashMap<>(); 
			map.put("customerNo", interfaceRequest.getOwnerID());
			Map<String, Object> repMap = accountQueryInterface.queryAccountBalance(map);
			if (repMap.get("accountNo") == null) {
				throw new RuntimeException("接口请求号：" + interfaceRequest.getInterfaceRequestID() + ", 根据商户编号未查询到账户信息。");
			}
			String accountNo = repMap.get("accountNo").toString();
			String ownerId = interfaceRequest.getOwnerID();
			Double amount = interfaceRequest.getAmount();
			Double preFreezeAmount = quickPayFeeService.getPreFreezeAmount(ownerId, amount, interfaceRequest.getRemark());
			// 发起欲冻
			logger.info("【预冻处理】************");
			AccountBussinessInterfaceBean accountBussinessInterfaceBean = new AccountBussinessInterfaceBean();
			accountBussinessInterfaceBean.setBussinessCode("PREFREEZE");
			accountBussinessInterfaceBean.setOperator("PAYINTERFCE");
			accountBussinessInterfaceBean.setRequestTime(new Date());
			accountBussinessInterfaceBean.setSystemCode("PAYINTERFACE-CORE");
			accountBussinessInterfaceBean.setSystemFlowId(interfaceRequest.getInterfaceRequestID());
			accountBussinessInterfaceBean.setRemark("快捷支付发起欲冻");
			AccountPreFreeze accountPreFreeze = new AccountPreFreeze();
			accountPreFreeze.setAccountNo(accountNo);
			accountPreFreeze.setFreezeLimit(DateUtils.addDays(new Date(), 365 * 10));
			accountPreFreeze.setPreFreezeAmount(preFreezeAmount);
			accountBussinessInterfaceBean.setTradeVoucher(accountPreFreeze);
			
			AccountPreFreezeResponse accountPreFreezeResponse = accountInterface.preFreeze(accountBussinessInterfaceBean);
			logger.info("预冻返回数据：{}", JsonUtils.toJsonString(accountPreFreezeResponse));
			// 如果预冻成功
			if (accountPreFreezeResponse.getResult() == HandlerResult.SUCCESS) {
				// 发MQ，生成代付记录
				mqMap.put("ownerId", interfaceRequest.getOwnerID());
				
				mqMap.put("freezeNo", accountPreFreezeResponse.getFreezeNo());
				mqMap.put("orderCode", interfaceRequest.getInterfaceRequestID());
				mqMap.put("ownerRole", "CUSTOMER");
				// 代付金额
				String dapyFee = quickPayFeeService.getRemitFee(ownerId, amount);
				// 获取代付金额
				String dapyAmount = String.valueOf(AmountUtils.subtract(preFreezeAmount, Double.valueOf(dapyFee)));
				
				mqMap.put("dapyAmount", dapyAmount);
				mqMap.put("dapyFee", dapyFee);
				mqMap.put("operator", "SYSTEM");
				
				TransCardBean transCardBean = quickPayFeeService.getSettleInfoByInterfaceRequestId(interfaceRequest.getInterfaceRequestID());
				mqMap.put("accountNo", transCardBean.getAccountNo());
				mqMap.put("accountName", transCardBean.getAccountName());
				mqMap.put("accountType", "INDIVIDUAL");
				mqMap.put("bankCode", transCardBean.getBankCode());
				mqMap.put("openBankName", transCardBean.getBankName());
				mqMap.put("interfaceInfoCode", interfaceRequest.getInterfaceInfoCode());
				mqMap.put("remitInterfaceInfoCode", properties.getProperty("remitInterfaceInfoCode"));
				mqMap.put("quickPayRemitType", properties.getProperty("quickPayRemitType"));
				mqMap.put("applyDate", new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
			}
			
		}
		
		interfaceRequestService.modify(interfaceRequest);
		
		callBackOnlineTradeResult(interfaceRequest, interfaceInfo);
		
		if (InterfaceTradeStatus.SUCCESS == interfaceRequest.getStatus() && "TRUE".equals(properties.get("autoDpay")) && mqMap.size() != 0) {
			// 发起mq
			producerClient.sendMessage(new ProducerMessage(prop.getProperty("QUICKPAY_AUTO_DPAY_TOPIC"), JsonUtils.toJsonString(mqMap).getBytes(), interfaceRequest.getInterfaceRequestID()));
		}
		// 更新绑卡信息
		if ("QUICKPAY_ZhongMao-100001".equals(interfaceRequest.getInterfaceInfoCode()) || "QUICKPAY_YinSheng-584001".equals(interfaceRequest.getInterfaceInfoCode())) {
			TransCardBean transCard = quickPayFeeService.getTransCardInfoByInterfaceRequestId(interfaceRequest.getInterfaceRequestID());
			if (transCard != null) {
				updateBindCardInfo(transCard.getAccountNo(), completeMap.get("interfaceCode"));
			}
		}
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

		QuickPayHandler quickPayHandler = quickPayHandlers.get(interfaceRequest.getInterfaceInfoCode());
		// 获取接口交易配置
		InterfaceInfo interfaceInfo = interfaceInfoService.queryByCode(interfaceRequest.getInterfaceInfoCode());
		Map<String, String> map = new HashMap<>();
		map.put("requestNo", interfaceRequest.getInterfaceRequestID());
		map.put("tradeConfigs", interfaceInfo.getTradeConfigs());
		map.put("interfaceInfoCode", interfaceRequest.getInterfaceInfoCode());
		map.put("interfaceRequestID", interfaceRequest.getInterfaceRequestID());
		map = quickPayHandler.query(map);
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
		} else{
			resMap.put("tranStat", "UNKNOWN");
		}
		return resMap;
	}
	
	/**
	 * 
	 * @Description 更新绑卡信息
	 * @param cardNo
	 * @param interfaceCode
	 * @date 2017年11月3日
	 */
	private void updateBindCardInfo(String cardNo, String interfaceCode) {
		if (StringUtils.notBlank(cardNo) && StringUtils.notBlank(interfaceCode)) {
			// 如果绑卡信息不是成功就更新成功
			BindCardInfo bindCardInfo = bindCardInfoService.find(cardNo, interfaceCode);
			if (bindCardInfo.getStatus() != AuthPayBindCardInfoStatus.SUCCESS) {
				bindCardInfo.setStatus(AuthPayBindCardInfoStatus.SUCCESS);
				bindCardInfoService.update(bindCardInfo);
			}
		}
	}


}
