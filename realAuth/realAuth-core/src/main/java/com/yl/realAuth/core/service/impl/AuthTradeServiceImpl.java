package com.yl.realAuth.core.service.impl;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.lefu.commons.utils.lang.JsonUtils;
import com.lefu.commons.utils.web.HttpClientUtils;
import com.lefu.commons.utils.web.HttpClientUtils.Method;
import com.yl.account.api.bean.AccountBussinessInterfaceBean;
import com.yl.account.api.bean.request.AccountSpecialCompositeTally;
import com.yl.account.api.bean.request.SpecialTallyVoucher;
import com.yl.account.api.bean.response.AccountCompositeTallyResponse;
import com.yl.account.api.dubbo.AccountInterface;
import com.yl.account.api.enums.Currency;
import com.yl.account.api.enums.FundSymbol;
import com.yl.account.api.enums.HandlerResult;
import com.yl.account.api.enums.UserRole;
import com.yl.boss.api.bean.CustomerFee;
import com.yl.boss.api.enums.FeeType;
import com.yl.boss.api.enums.Status;
import com.yl.boss.api.interfaces.CustomerInterface;
import com.yl.payinterface.core.bean.AuthRequestBean;
import com.yl.payinterface.core.hessian.RealNameAuthHessianService;
import com.yl.realAuth.core.ExceptionMessages;
import com.yl.realAuth.core.InnerResponseCode;
import com.yl.realAuth.core.InnerResponseConvertHandle;
import com.yl.realAuth.core.service.AuthConfigService;
import com.yl.realAuth.core.service.AuthInfoManageService;
import com.yl.realAuth.core.service.AuthOrderService;
import com.yl.realAuth.core.service.AuthTradeService;
import com.yl.realAuth.core.service.ChannelInfoService;
import com.yl.realAuth.core.service.RoutingTemplateService;
import com.yl.realAuth.core.utils.FeeUtils;
import com.yl.realAuth.enums.AuthBusiType;
import com.yl.realAuth.enums.AuthOrderStatus;
import com.yl.realAuth.enums.AuthResult;
import com.yl.realAuth.enums.CardType;
import com.yl.realAuth.enums.ChannelStatus;
import com.yl.realAuth.enums.TrueFalse;
import com.yl.realAuth.hessian.bean.AuthResponseBean;
import com.yl.realAuth.hessian.bean.AuthResponseResult;
import com.yl.realAuth.hessian.bean.CreateOrderBean;
import com.yl.realAuth.hessian.exception.BusinessException;
import com.yl.realAuth.hessian.utils.CommonUtils;
import com.yl.realAuth.model.AuthConfigInfo;
import com.yl.realAuth.model.ChannelInfo;
import com.yl.realAuth.model.RealNameAuthOrder;
import com.yl.realAuth.model.RoutingRuleInfo;

@Service("authTradeService")
public class AuthTradeServiceImpl implements AuthTradeService {

	private static Logger logger = LoggerFactory.getLogger(AuthTradeServiceImpl.class);

	@Resource
	private AuthOrderService authOrderService;
	@Resource
	private AuthInfoManageService authInfoManageService;
	@Resource
	private AuthConfigService authConfigService;
	@Resource
	private RoutingTemplateService routingTemplateService;
	@Resource
	private ChannelInfoService channelInfoService;
	@Resource
	private RealNameAuthHessianService realNameAuthHessianService;
	@Resource
	private CustomerInterface customerInterface;
	@Resource
	private AccountInterface accountInterface;

	private static Properties prop = new Properties();
	static {
		try {
			prop.load(AuthTradeServiceImpl.class.getClassLoader().getResourceAsStream("serverHost.properties"));
		} catch (IOException e) {
			logger.error("", e);
		}
	}

	@Override
	public AuthResponseResult createOrder(CreateOrderBean createOrderBean) throws BusinessException {
		// 实名认证响应实体
		AuthResponseResult authResponseResult = new AuthResponseResult();
		// 商编、商户订单号查询支付订单
		RealNameAuthOrder realNameAuthOrder = authOrderService.queryByRequestCode(createOrderBean.getCustomerNo(), createOrderBean.getRequestCode());
		// 当前系统时间
		Date currentDate = new Date();
		// 检查实名认证订单
		if (realNameAuthOrder != null)
			return checkRealNameAuthOrder(realNameAuthOrder, authResponseResult);

		// 创建支付订单
		realNameAuthOrder = this.generateOrder(createOrderBean, currentDate);
		authOrderService.insertOrder(realNameAuthOrder);

		logger.info("商户编号【" + realNameAuthOrder.getCustomerNo() + "】的商户订单号【" + realNameAuthOrder.getRequestCode() + "】的订单详细信息：{}", realNameAuthOrder);
		try {
			double payerFee = rateHandle(realNameAuthOrder);// 计算手续费处理
			realNameAuthOrder.setFee(payerFee);
			authOrderService.modifyOrderStatus(realNameAuthOrder);

			// 查询账户余额
			// 校验账户余额

			// 数据匹配(查询认证信息在库里是否存在)
			boolean isExist = authInfoManageService.findAuthInfoBy(realNameAuthOrder);
			RealNameAuthOrder realAuthOrder = authOrderService.queryAuthOrderByCode(realNameAuthOrder.getCode());

			logger.info("商户编号【" + realAuthOrder.getCustomerNo() + "】的商户订单号【" + realAuthOrder.getRequestCode() + "】的认证信息库匹配结果：{}", isExist);
			if (isExist) {
				return subtractFeeResultHandle(authResponseResult, realAuthOrder, payerFee, true);
			} else {
				// 支付接口返回参数
				Map<String, Object> returnParams = new HashMap<String, Object>();
				String interfaceInfoCode = "";// 支付接口编号
				String interfaceProviderCode = "";// 银行编码
				String cardType = "";// 卡类型
				// 渠道信息
				ChannelInfo channelInfo = new ChannelInfo();
				// 查询实名认证配置
				AuthConfigInfo authConfigInfo = authConfigService.queryAuthConfigByCustomerNo(realAuthOrder.getCustomerNo(), realAuthOrder.getBusiType().name());
				if (AuthBusiType.BINDCARD_AUTH.equals(realAuthOrder.getBusiType())) {
					interfaceProviderCode = findProviderCode(CommonUtils.deEncrypt(realAuthOrder.getBankCardNoEncrypt())).get("providerCode");
					cardType = realNameAuthOrder.getCardType().name();
				}
				// 获取接口编号
				Map<String, Object> channelInfoParams = new HashMap<String, Object>();
				try {
					channelInfoParams = getInterfaceCode(authConfigInfo, realAuthOrder, interfaceProviderCode, cardType, channelInfo);
				} catch (Exception e) {
					logger.error("商户编号【" + realNameAuthOrder.getCustomerNo() + "】的商户订单号【" + realNameAuthOrder.getRequestCode() + "】的订单获取路由异常：{}", e);
					InnerResponseCode innerResponseCode = InnerResponseCode.getHandlerResultCode(e.getMessage());
					return resultProcess(realNameAuthOrder, AuthOrderStatus.FAILED, innerResponseCode);
				}

				interfaceInfoCode = (String) channelInfoParams.get("interfaceInfoCode");
				channelInfo = (ChannelInfo) channelInfoParams.get("channelInfo");

				realAuthOrder.setInterfaceCode(interfaceInfoCode);
				// 银行编号
				realAuthOrder.setBankCode(interfaceProviderCode);
				logger.info("商户编号【" + realAuthOrder.getCustomerNo() + "】,商户订单号【" + realAuthOrder.getRequestCode() + "】接口编号：{}", interfaceInfoCode);

				// 调用账务扣除实名认证手续费(费用类接口)
				boolean accountStatus = accountHandle(realNameAuthOrder);
				if (!accountStatus) {
					throw new Exception("20011");
				}
				// 更新订单状态认证中
				realAuthOrder.setAuthOrderStatus(AuthOrderStatus.PROCESSING);
				authOrderService.modifyOrderStatus(realAuthOrder);

				// 封装实名认证请求
				AuthRequestBean authRequestBean = initAuthRequestBean(realAuthOrder, interfaceInfoCode, interfaceProviderCode);
				logger.info("商户编号【" + realAuthOrder.getCustomerNo() + "】,商户订单号【" + realAuthOrder.getRequestCode() + "】的实名认证交易请求数据：{}", authRequestBean);
				// 调用支付接口发送实名认证请求
				try {
					returnParams = (Map<String, Object>) realNameAuthHessianService.trade(authRequestBean);
				} catch (Exception e) {
					logger.info("实名认证调用支付接口出现异常,订单号：{},异常信息：{}", realAuthOrder.getCode(), e);
					// if (null != channelInfo.getTradeConfigs() && null !=
					// channelInfo.getTradeConfigs().getProperty("isQuery") &&
					// "FALSE".equals(channelInfo.getTradeConfigs().getProperty("isQuery")))
					// {
					// // 交易异常失败处理（不支持查询的接口调用失败之后直接失败,手续费退回）
					// // return
					// // tradeFailHandleForNotSupportQuery(authResponseResult,
					// // realAuthOrder, channelInfo);
					// }
					return authResponseResult;
				}

				logger.info("商户编号【" + realAuthOrder.getCustomerNo() + "】,商户订单号【" + realAuthOrder.getRequestCode() + "】的实名认证支付接口响应结果：{}", returnParams);
				// 接口响应处理
				authResponseResult = interfaceRespHandle(authResponseResult, realAuthOrder, returnParams);
			}
		} catch (Exception e) {
			logger.error("商户编号【" + realNameAuthOrder.getCustomerNo() + "】的商户订单号【" + realNameAuthOrder.getRequestCode() + "】的订单交易异常：{}", e);
			return resultProcess(realNameAuthOrder, AuthOrderStatus.FAILED, e.getMessage());
		}
		return authResponseResult;
	}

	/**
	 * 校验订单信息
	 * 
	 * @param reaNameAuthOrder
	 *            订单信息
	 * @param authResponseResult
	 *            校验结果
	 * @return AuthResponseResult
	 */
	private AuthResponseResult checkRealNameAuthOrder(RealNameAuthOrder realNameAuthOrder, AuthResponseResult authResponseResult) {
		authResponseResult.setRequestCode(realNameAuthOrder.getRequestCode());
		authResponseResult.setTradeOrderCode(realNameAuthOrder.getCode());
		if (AuthOrderStatus.SUCCESS.equals(realNameAuthOrder.getAuthOrderStatus()) && realNameAuthOrder.getAuthResult() != null) {
			authResponseResult.setResponseCode(InnerResponseCode.getHandlerResultCode(realNameAuthOrder.getAuthResult().name()).getMerchantCode());
			authResponseResult.setResponseMsg(InnerResponseCode.getHandlerResultCode(realNameAuthOrder.getAuthResult().name()).getMerchantMsg());
		} else if (AuthOrderStatus.FAILED.equals(realNameAuthOrder.getAuthOrderStatus())) {
			authResponseResult.setResponseCode(InnerResponseCode.IDENTITY_FAILED.getMerchantCode());
			authResponseResult.setResponseMsg(InnerResponseCode.IDENTITY_FAILED.getMerchantMsg());
		} else if (AuthOrderStatus.PROCESSING.equals(realNameAuthOrder.getAuthOrderStatus())) {
			authResponseResult.setResponseCode(InnerResponseCode.AUTH_PROCESSING.getMerchantCode());
			authResponseResult.setResponseMsg(InnerResponseCode.AUTH_PROCESSING.getMerchantMsg());
		}
		return authResponseResult;
	}

	/**
	 * 新建支付订单
	 * 
	 * @param orderBean
	 *            订单参数实体
	 * @param currentDate
	 *            当前系统时间
	 * @return Order
	 */
	private RealNameAuthOrder generateOrder(CreateOrderBean orderBean, Date currentDate) {
		// 订单实体
		RealNameAuthOrder order = new RealNameAuthOrder();
		// 业务类型
		order.setBusiType(AuthBusiType.valueOf(orderBean.getBusiType()));
		// 业务标志1
		order.setBusinessFlag1(orderBean.getBusinessFlag1());
		// 商户订单号
		order.setRequestCode(orderBean.getRequestCode());
		// 收款方
		order.setCustomerNo(orderBean.getCustomerNo());
		// 订单状态
		order.setAuthOrderStatus(AuthOrderStatus.WAIT);
		// 下单时间
		order.setCreateTime(new Date());
		// 银行卡号
		order.setBankCardNo(orderBean.getBankCardNo());
		// 密文
		order.setBankCardNoEncrypt(orderBean.getBankCardNoEncrypt());
		// 身份证号
		order.setCertNo(orderBean.getCertNo());
		// 身份证密文
		order.setCertNoEncrypt(orderBean.getCertNoEncrypt());
		// 商户编号
		order.setCustomerNo(orderBean.getCustomerNo());
		// 手机号
		order.setPayerMobNo(orderBean.getPayerMobNo());
		// 账户名
		order.setPayerName(orderBean.getPayerName());
		// 合作方后台通知地址
		order.setNotifyURL(orderBean.getNotifyURL());

		// 卡类型
		if (StringUtils.isNotBlank(order.getBankCardNoEncrypt())) {
			String cardType = findProviderCode(CommonUtils.deEncrypt(order.getBankCardNoEncrypt())).get("cardType");
			if ("DEBIT".equals(cardType)) {
				order.setCardType(CardType.DEBIT_CARD);
			} else if ("CREDIT".equals(cardType)) {
				order.setCardType(CardType.CREDIT_CARD);
			} else if ("SEMI_CREDIT".equals(cardType)) {
				order.setCardType(CardType.SEMI_CREDIT_CARD);
			} else {
				order.setCardType(CardType.PREPAID_CARD);
			}
		} else {
			order.setCardType(CardType.DEBIT_CARD);
		}
		return order;
	}

	/**
	 * 卡识别
	 * 
	 * @param cardNo
	 *            卡号
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<String, String> findProviderCode(String cardNo) {
		Map<String, String> params = new HashMap<String, String>();
		try {
			String words = "&cardNo=" + cardNo;
			String url = (String) prop.get("cachecenter.service.url") + "/iin/recognition.htm?fields=cardType" + words;

			String resData = HttpClientUtils.send(Method.GET, url, null, true, Charset.forName("UTF-8").name(), 6000);

			params = JsonUtils.toObject(resData, Map.class);
			logger.info("卡号为【" + cardNo + "】的实名认证卡识别结果为：{}", params);
		} catch (Exception e) {
			logger.error("实名认证卡识别异常：{}", e);
		}
		return params;
	}

	/**
	 * 扣除手续费后续操作
	 * 
	 * @param authResponseResult
	 *            响应结果
	 * @param reaNameAuthOrder
	 *            订单信息
	 * @param accountBalanceQueryResponse
	 *            账户查询响应结果
	 * @param tradeAmount
	 *            认证费用
	 * @param isStoreFlag
	 *            库存标志
	 * @return AuthResponseResult
	 */
	private AuthResponseResult subtractFeeResultHandle(AuthResponseResult authResponseResult, RealNameAuthOrder realNameAuthOrder, double tradeAmount, boolean isStoreFlag) {
		/** 扣除手续费处理 */
		try {
			realNameAuthOrder.setAuthResult(AuthResult.AUTH_SUCCESS);
			realNameAuthOrder.setAuthOrderStatus(AuthOrderStatus.SUCCESS);
			realNameAuthOrder.setCost(0d);
			realNameAuthOrder.setBusinessFlag1("SUBTRACT_FEE_SUCCESS");
			realNameAuthOrder.setClearTime(new Date());
			realNameAuthOrder.setCompleteTime(new Date());
			// 存在白名单中,虚拟通道编号
			realNameAuthOrder.setInterfaceCode("REALAUTH_YL_100001");
			authOrderService.modifyOrderStatus(realNameAuthOrder);
			// 调用账务扣除实名认证手续费(费用类接口)
			boolean accountStatus = accountHandle(realNameAuthOrder);
			
			if (!accountStatus) {
				throw new Exception(ExceptionMessages.ACCOUNT_VALUEABLE_BALANCE_NOT_ENOUGH);
			}


			// 同步结果
			authResponseResult.setRequestCode(realNameAuthOrder.getRequestCode());
			authResponseResult.setTradeOrderCode(realNameAuthOrder.getCode());
			authResponseResult.setResponseCode(InnerResponseCode.AUTH_RIGHT.getMerchantCode());
			authResponseResult.setResponseMsg(InnerResponseCode.AUTH_RIGHT.getMerchantMsg());
		} catch (Exception e) {
			// 扣除失败异常处理
			authResponseResult = subtractFeeFailHandle(authResponseResult, realNameAuthOrder, e);
		}
		return authResponseResult;
	}

	public AuthResponseResult subtractFeeFailHandle(AuthResponseResult authResponseResult, RealNameAuthOrder reaNameAuthOrder, Exception e) {

		logger.info("商户编号【" + reaNameAuthOrder.getCustomerNo() + "】,商户订单号为【" + reaNameAuthOrder.getRequestCode() + "】的订单实名认证扣除手续费异常：{}", e);
		String responseMsg = "";
		// 应答商户结果
		InnerResponseCode innerResponseCode = InnerResponseCode.getHandlerResultCode(e.getMessage());
		authResponseResult.setRequestCode(reaNameAuthOrder.getRequestCode());
		authResponseResult.setTradeOrderCode(reaNameAuthOrder.getCode());
		authResponseResult.setResponseCode(innerResponseCode.getMerchantCode());
		authResponseResult.setResponseMsg(innerResponseCode.getMerchantMsg());
		responseMsg = innerResponseCode.getErrorMsg();

		// 更新订单状态为失败
		reaNameAuthOrder.setResponseMsg(responseMsg);
		reaNameAuthOrder.setAuthOrderStatus(AuthOrderStatus.FAILED);
		reaNameAuthOrder.setAuthResult(AuthResult.AUTH_FAILED);
		reaNameAuthOrder.setBusinessFlag1("SUBTRACT_FEE_FAILED");

		// 订单失败,返回内部错误码与内部错误描述
		reaNameAuthOrder.setInnerErrorCode(innerResponseCode.getErrorCode());
		reaNameAuthOrder.setInnerErrorMsg(innerResponseCode.getErrorMsg());

		authOrderService.modifyOrderStatus(reaNameAuthOrder);

		return authResponseResult;
	}

	/**
	 * 路由获取接口编号
	 * 
	 * @param authConfigInfo
	 *            实名认证配置信息
	 * @param realAuthOrder
	 *            订单信息
	 * @param interfaceProviderCode
	 *            银行编码
	 * @param cardType
	 *            卡类型
	 * @param channelInfo
	 *            渠道信息
	 * @return Map<String, Object>
	 * @throws BusinessException
	 */
	public Map<String, Object> getInterfaceCode(AuthConfigInfo authConfigInfo, RealNameAuthOrder realAuthOrder, String interfaceProviderCode, String cardType, ChannelInfo channelInfo) throws BusinessException {
		String interfaceInfoCode = "";
		// XXX
		interfaceProviderCode = "ICBC";
		// 根据业务类型查询路由模板
		List<RoutingRuleInfo> ruleInfos = routingTemplateService.findRoutingRuleByTemplateCode(authConfigInfo.getRoutingTemplateCode(), interfaceProviderCode, cardType);
		logger.info("商户编号【" + realAuthOrder.getCustomerNo() + "】,商户订单号【" + realAuthOrder.getRequestCode() + "】路由模板信息：{}", ruleInfos);
		// 获取路由规则
		if (ruleInfos == null || ruleInfos.size() == 0) {
			logger.info("商户编号【" + realAuthOrder.getCustomerNo() + "】,商户订单号【" + realAuthOrder.getRequestCode() + "】未匹配到路由：OVER");
			throw new BusinessException(InnerResponseCode.INTERFACE_ROUTER_NOT_EXIST.getErrorCode());
		}
		// 循环选择路由,获取渠道编码
		for (RoutingRuleInfo routingRuleInfo : ruleInfos) {
			// 获取渠道信息
			channelInfo = channelInfoService.findByChannelCode(routingRuleInfo.getChannelCode());
			if (channelInfo == null || channelInfo.getInterfaceInfoCode() == null)
				continue;
			// 判断渠道信息是否符合（4要素匹配） （身份认证不用校验）
			if (!AuthBusiType.IDENTITY_AUTH.name().equals(realAuthOrder.getBusiType() + "") && !checkBindCardParam(realAuthOrder, channelInfo))
				continue;
			// 1、渠道状态是有效，则选择当前路由规则 否则使用下一个路由
			if (ChannelStatus.TRUE.name().equals(channelInfo.getStatus().name())) {
				interfaceInfoCode = channelInfo.getInterfaceInfoCode();
			} else {
				continue;
			}
			// 保证只执行一次
			if (StringUtils.isNotBlank(interfaceInfoCode))
				break;
		}
		// 如果没有找到对应的路由，则返回错误
		if (StringUtils.isBlank(interfaceInfoCode)) {
			logger.info("商户编号【" + realAuthOrder.getCustomerNo() + "】,商户订单号【" + realAuthOrder.getRequestCode() + "】未找到路由对应规则：OVER");
			throw new BusinessException(InnerResponseCode.INTERFACE_ROUTER_NOT_EXIST.getErrorCode());
		}

		Map<String, Object> channelInfoParam = new HashMap<String, Object>();
		channelInfoParam.put("interfaceInfoCode", interfaceInfoCode);
		channelInfoParam.put("channelInfo", channelInfo);

		return channelInfoParam;
	}

	/**
	 * 校验绑卡信息
	 * 
	 * @param realAuthOrder
	 *            订单信息
	 * @param channelInfo
	 *            渠道信息
	 * @return
	 */
	private boolean checkBindCardParam(RealNameAuthOrder realAuthOrder, ChannelInfo channelInfo) {
		if (realAuthOrder == null)
			return false;
		String payerName = realAuthOrder.getPayerName();
		String payerMobNo = realAuthOrder.getPayerMobNo();
		String certNo = realAuthOrder.getCertNo();
		boolean check = true;
		// 第一次校验：校验是否支持
		if (StringUtils.isNotBlank(payerName)) {
			TrueFalse a = channelInfo.getSupportName();
			if (a == TrueFalse.FALSE)
				check = false;
		}
		if (StringUtils.isNotBlank(payerMobNo)) {
			TrueFalse a = channelInfo.getSupportMobNo();
			if (a == TrueFalse.FALSE)
				check = false;
		}
		if (StringUtils.isNotBlank(certNo)) {
			TrueFalse a = channelInfo.getSupportCertNo();
			if (a == TrueFalse.FALSE)
				check = false;
		}
		if (check) {
			if (!StringUtils.isNotBlank(payerName)) {
				TrueFalse a = channelInfo.getMustName();
				if (a == TrueFalse.TRUE)
					check = false;
			}
			if (!StringUtils.isNotBlank(payerMobNo)) {
				TrueFalse a = channelInfo.getMustMobNo();
				if (a == TrueFalse.TRUE)
					check = false;
			}
			if (!StringUtils.isNotBlank(certNo)) {
				TrueFalse a = channelInfo.getMustCertNo();
				if (a == TrueFalse.TRUE)
					check = false;
			}
		}
		return check;
	}

	// 修改订单状态，返回上层处理结果
	private AuthResponseResult resultProcess(RealNameAuthOrder realNameOrder, AuthOrderStatus status, String errorCode) {
		errorCode = InnerResponseConvertHandle.getInnerResponseCode(errorCode);
		InnerResponseCode innerResponseCode = InnerResponseCode.getHandlerResultCode(errorCode);
		return resultProcess(realNameOrder, status, innerResponseCode);
	}

	// 修改订单状态，返回上层处理结果
	private AuthResponseResult resultProcess(RealNameAuthOrder realNameOrder, AuthOrderStatus status, InnerResponseCode innerResponseCode) {
		AuthResponseResult authResponseResult = new AuthResponseResult();

		realNameOrder.setAuthOrderStatus(status);
		realNameOrder.setResponseMsg(innerResponseCode.getErrorMsg());
		realNameOrder.setCompleteTime(new Date());

		// 订单失败,返回内部错误码与内部错误描述
		if (null != status && AuthOrderStatus.FAILED.name().equals(status.toString())) {
			realNameOrder.setInnerErrorCode(innerResponseCode.getErrorCode());
			realNameOrder.setInnerErrorMsg(innerResponseCode.getErrorMsg());
		}

		authOrderService.modifyOrderStatus(realNameOrder);

		authResponseResult.setRequestCode(realNameOrder.getRequestCode());
		authResponseResult.setTradeOrderCode(realNameOrder.getCode());
		authResponseResult.setResponseCode(innerResponseCode.getMerchantCode());
		authResponseResult.setResponseMsg(innerResponseCode.getMerchantMsg());

		return authResponseResult;
	}

	/**
	 * 封装实名认证接口请求
	 * 
	 * @param realAuthOrder
	 *            订单信息
	 * @param interfaceInfoCode
	 *            渠道编码
	 * @param interfaceProviderCode
	 *            银行编码
	 * @return AuthRequestBean
	 */
	private AuthRequestBean initAuthRequestBean(RealNameAuthOrder realAuthOrder, String interfaceInfoCode, String interfaceProviderCode) {
		AuthRequestBean authRequestBean = new AuthRequestBean();
		if (StringUtils.isNotBlank(realAuthOrder.getCertNoEncrypt()))
			authRequestBean.setCertificatesCode(CommonUtils.deEncrypt(realAuthOrder.getCertNoEncrypt()));
		if (StringUtils.isNotBlank(realAuthOrder.getBankCardNoEncrypt()))
			authRequestBean.setAccountNo(CommonUtils.deEncrypt(realAuthOrder.getBankCardNoEncrypt()));

		authRequestBean.setAccountName(realAuthOrder.getPayerName());
		authRequestBean.setBusinessOrderID(realAuthOrder.getCode());
		authRequestBean.setBusinessOrderTime(realAuthOrder.getCreateTime());
		authRequestBean.setCardType(realAuthOrder.getCardType().name());
		authRequestBean.setInterfaceCode(interfaceInfoCode);
		authRequestBean.setInterfaceProviderCode(interfaceProviderCode);
		authRequestBean.setOwnerID(realAuthOrder.getCustomerNo());
		authRequestBean.setOwnerRole("CUSTOMER");
		authRequestBean.setPhoneNo(realAuthOrder.getPayerMobNo());
		return authRequestBean;
	}

	/**
	 * 调用支付接口响应处理
	 * 
	 * @param authResponseResult
	 *            商户响应结果
	 * @param realAuthOrder
	 *            订单信息
	 * @param returnParams
	 *            接口返回参数
	 * @return AuthResponseResult
	 */
	private AuthResponseResult interfaceRespHandle(AuthResponseResult authResponseResult, RealNameAuthOrder realAuthOrder, Map<String, Object> returnParams) {
		// 设置商户响应结果
		authResponseResult.setRequestCode(realAuthOrder.getRequestCode());
		authResponseResult.setTradeOrderCode(realAuthOrder.getCode());
		if (returnParams.size() != 0) {
			if (returnParams.get("reqSerialNo") != null)
				realAuthOrder.setInterfaceRequestId(returnParams.get("reqSerialNo").toString());
			AuthResponseBean authResponseBean = null;
			try {
				String authResponseBeanStr = (String) returnParams.get("authResponseBean");
				if (StringUtils.isNotBlank(authResponseBeanStr))
					authResponseBean = JsonUtils.toObject(authResponseBeanStr, AuthResponseBean.class);
			} catch (Exception e1) {
				logger.info("商户编号【" + realAuthOrder.getCustomerNo() + "】,商户订单号为【" + realAuthOrder.getRequestCode() + "】的交易解析接口返回信息出错：{}", e1);
			}

			realAuthOrder.setInterfaceCode(returnParams.get("interfaceInfoCode").toString());
			if (authResponseBean != null && authResponseBean.getAuthOrderStatus() != null) {
				if (AuthOrderStatus.SUCCESS.toString().equals(authResponseBean.getAuthOrderStatus().toString())) {
					if (AuthResult.AUTH_SUCCESS.toString().equals(authResponseBean.getAuthResult().toString())) {
						authResponseResult.setResponseCode(InnerResponseCode.AUTH_RIGHT.getMerchantCode());
						authResponseResult.setResponseMsg(InnerResponseCode.AUTH_RIGHT.getMerchantMsg());
						return authResponseResult;
					} else if (AuthResult.AUTH_FAILED.toString().equals(authResponseBean.getAuthResult().toString())) {
						authResponseResult.setResponseCode(InnerResponseCode.AUTH_ERROR.getMerchantCode());
						authResponseResult.setResponseMsg(InnerResponseCode.AUTH_ERROR.getMerchantMsg());
						return authResponseResult;
					}
				} else if (AuthOrderStatus.FAILED.toString().equals(authResponseBean.getAuthOrderStatus().toString())) {
					if (returnParams.get("insideRespCode") != null) {
						String errorCode = (String) returnParams.get("insideRespCode");
						errorCode = InnerResponseConvertHandle.getInnerResponseCode(errorCode);
						authResponseResult.setResponseCode(InnerResponseCode.getHandlerResultCode(errorCode).getMerchantCode());
						authResponseResult.setResponseMsg(InnerResponseCode.getHandlerResultCode(errorCode).getMerchantMsg());
					} else {
						authResponseResult.setResponseCode(InnerResponseCode.UNKOWN_ERROR.getMerchantCode());
						authResponseResult.setResponseMsg(InnerResponseCode.UNKOWN_ERROR.getMerchantMsg());
					}

					// 计数回滚
					// rollbackCompute(realAuthOrder.getInterfaceCode(),
					// realAuthOrder);

					return authResponseResult;
				}
			}
		}
		authResponseResult.setResponseCode(InnerResponseCode.AUTH_PROCESSING.getMerchantCode());
		authResponseResult.setResponseMsg(InnerResponseCode.AUTH_PROCESSING.getMerchantMsg());

		logger.info("商户编号【" + realAuthOrder.getCustomerNo() + "】,商户订单号【" + realAuthOrder.getRequestCode() + "】的实名认证响应商户结果：{}", authResponseResult);
		return authResponseResult;
	}

	/**
	 * 实名认证计费处理
	 * 
	 * @param realNameAuthOrder
	 *            订单信息
	 * @return payerFee 手续费
	 * @throws BusinessException
	 */
	private double rateHandle(RealNameAuthOrder realNameAuthOrder) throws BusinessException {

		CustomerFee customerFee = customerInterface.getCustomerFee(realNameAuthOrder.getCustomerNo(), realNameAuthOrder.getBusiType().name());
		if (customerFee == null || customerFee.getStatus() == Status.FALSE) {
			throw new BusinessException(InnerResponseCode.RATE_NOT_EXISTS.getErrorCode());
		}
		double fee = FeeUtils.computeFee(1, FeeType.valueOf(customerFee.getFeeType().toString()), customerFee.getFee());
		return fee;
	}

	/**
	 * 代付扣款
	 * 
	 * @param request
	 * @return boolean
	 */
	private boolean accountHandle(RealNameAuthOrder realNameAuthOrder) {
		AccountBussinessInterfaceBean accountBussinessInterfaceBean = new AccountBussinessInterfaceBean();
		accountBussinessInterfaceBean.setBussinessCode("REAL_AUTH_FEE");
		accountBussinessInterfaceBean.setOperator("real-auth");
		accountBussinessInterfaceBean.setRemark("实名认证手续费");
		accountBussinessInterfaceBean.setRequestTime(new Date());
		accountBussinessInterfaceBean.setSystemCode("REALAUTH");
		accountBussinessInterfaceBean.setSystemFlowId(realNameAuthOrder.getCode());

		AccountSpecialCompositeTally accountCompositeTally = new AccountSpecialCompositeTally();

		// 交易金额
		SpecialTallyVoucher amount = new SpecialTallyVoucher();
		amount.setAccountType(com.yl.account.api.enums.AccountType.CASH);
		amount.setUserNo(realNameAuthOrder.getCustomerNo());
		amount.setUserRole(UserRole.CUSTOMER);
		amount.setAmount(realNameAuthOrder.getFee());
		amount.setSymbol(FundSymbol.SUBTRACT);
		amount.setCurrency(Currency.RMB);
		// amount.setFee(realNameAuthOrder.getFee());
		// amount.setFeeSymbol(FundSymbol.SUBTRACT);
		amount.setTransDate(realNameAuthOrder.getCreateTime());
		amount.setTransOrder(realNameAuthOrder.getCode());
		amount.setWaitAccountDate(new Date());

		List<SpecialTallyVoucher> tallyVouchers = new ArrayList<SpecialTallyVoucher>();
		tallyVouchers.add(amount);

		accountCompositeTally.setSpecialTallyVouchers(tallyVouchers);
		accountBussinessInterfaceBean.setTradeVoucher(accountCompositeTally);

		AccountCompositeTallyResponse accountDebitResponse = accountInterface.specialCompositeTally(accountBussinessInterfaceBean);
		logger.info("代付订单号:{}, 扣款返回信息：{}", realNameAuthOrder.getCode(), accountDebitResponse);
		if (accountDebitResponse.getStatus() != com.yl.account.api.enums.Status.SUCCESS) {
			return false;
		}

		if (accountDebitResponse.getResult() != HandlerResult.SUCCESS) {
			return false;
		}

		return true;
	}
}
