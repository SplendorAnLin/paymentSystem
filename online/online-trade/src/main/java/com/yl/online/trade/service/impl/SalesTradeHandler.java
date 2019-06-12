package com.yl.online.trade.service.impl;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;

import com.yl.boss.api.bean.CustomerFee;
import com.yl.online.trade.utils.FeeUtils;
import com.yl.online.trade.utils.HolidayUtils;
import com.yl.online.trade.utils.PropertiesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.lefu.commons.utils.lang.AmountUtils;
import com.lefu.commons.utils.lang.DateFormatUtils;
import com.lefu.commons.utils.lang.DateUtils;
import com.lefu.commons.utils.lang.JsonUtils;
import com.lefu.commons.utils.lang.StringUtils;
import com.yl.account.api.bean.AccountBussinessInterfaceBean;
import com.yl.account.api.bean.request.AccountQuery;
import com.yl.account.api.bean.request.AccountSpecialCompositeTally;
import com.yl.account.api.bean.request.SpecialTallyVoucher;
import com.yl.account.api.bean.response.AccountCompositeTallyResponse;
import com.yl.account.api.bean.response.AccountQueryResponse;
import com.yl.account.api.dubbo.AccountInterface;
import com.yl.account.api.dubbo.AccountQueryInterface;
import com.yl.account.api.enums.AccountType;
import com.yl.account.api.enums.Currency;
import com.yl.account.api.enums.FundSymbol;
import com.yl.account.api.enums.HandlerResult;
import com.yl.account.api.enums.Status;
import com.yl.account.api.enums.UserRole;
import com.yl.boss.api.bean.ShareProfitBean;
import com.yl.boss.api.enums.ProductType;
import com.yl.boss.api.interfaces.CustomerInterface;
import com.yl.boss.api.interfaces.ShareProfitInterface;
import com.yl.dpay.hessian.beans.ServiceConfigBean;
import com.yl.dpay.hessian.enums.FireType;
import com.yl.dpay.hessian.service.ServiceConfigFacade;
import com.yl.mq.producer.client.ProducerClient;
import com.yl.mq.producer.client.ProducerMessage;
import com.yl.online.gateway.hessian.SalesNotifyHessianService;
import com.yl.online.model.bean.CreateOrderBean;
import com.yl.online.model.bean.CreatePaymentBean;
import com.yl.online.model.bean.CreateSalesOrderBean;
import com.yl.online.model.bean.PayResultBean;
import com.yl.online.model.bean.Payment;
import com.yl.online.model.bean.TradeContext;
import com.yl.online.model.enums.AccountFailStatus;
import com.yl.online.model.enums.OrderClearingStatus;
import com.yl.online.model.enums.OrderStatus;
import com.yl.online.model.enums.PayType;
import com.yl.online.model.enums.PaymentStatus;
import com.yl.online.model.enums.RepeatFlag;
import com.yl.online.model.enums.ReverseStatus;
import com.yl.online.model.model.AccountFailInfo;
import com.yl.online.model.model.CustomerConfig;
import com.yl.online.model.model.MerchantSalesResultReverseOrder;
import com.yl.online.model.model.Order;
import com.yl.online.model.model.SalesResultReverseOrder;
import com.yl.online.trade.Constants;
import com.yl.online.trade.ExceptionMessages;
import com.yl.online.trade.exception.BusinessException;
import com.yl.online.trade.router.ExecutorRouterService;
import com.yl.online.trade.service.AccountFailInfoService;
import com.yl.online.trade.service.CustomerConfigService;
import com.yl.online.trade.service.MerchantSalesResultReverseOrderService;
import com.yl.online.trade.service.PaymentService;
import com.yl.online.trade.service.SalesResultReverseOrderService;
import com.yl.online.trade.service.TradeHandler;
import com.yl.online.trade.service.TradeOrderService;
import com.yl.online.trade.utils.CodeBuilder;
import com.yl.payinterface.core.bean.AuthSaleBean;
import com.yl.payinterface.core.bean.InternetbankSalesTradeBean;
import com.yl.payinterface.core.bean.MobileInfoBean;
import com.yl.payinterface.core.bean.QuickPayBean;
import com.yl.payinterface.core.bean.WalletSalesTradeBean;
import com.yl.payinterface.core.hessian.AuthPayHessianService;
import com.yl.payinterface.core.hessian.InternetbankHessianService;
import com.yl.payinterface.core.hessian.QuickPayHessianService;
import com.yl.payinterface.core.hessian.WalletpayHessianService;
import com.yl.receive.hessian.enums.OwnerRole;

/**
 * 交易接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年7月27日
 * @version V1.0.0
 */
@Service("salesTradeHandler")
public class SalesTradeHandler implements TradeHandler {
	private static final Logger logger = LoggerFactory.getLogger(SalesTradeHandler.class);
	@Resource
	private TradeOrderService tradeOrderService;
	@Resource
	private PaymentService paymentService;
	@Resource
	private ExecutorRouterService executorRouterService;
	@Resource
	private AccountFailInfoService accountService;
	@Resource
	private AccountInterface accountInterface;
	@Resource
	private InternetbankHessianService internetbankHessianService;
	@Resource
	private AccountQueryInterface accountQueryInterface;
	@Resource
	private WalletpayHessianService walletpayHessianService;
	@Resource
	private ShareProfitInterface shareProfitInterface;
	@Resource
	private CustomerInterface customerInterface;
	@Resource
	private SalesNotifyHessianService salesNotifyHessianService;
	@Resource
	private AuthPayHessianService authPayHessianService;
	@Resource
	private QuickPayHessianService quickPayHessianService;
	@Resource
	private MerchantSalesResultReverseOrderService merchantSalesResultReverseOrderService;
	@Resource
	private CustomerConfigService customerConfigService;
	@Resource
	private ServiceConfigFacade serviceConfigFacade;
	@Resource
	private ProducerClient producerClient;
	@Resource
	private SalesResultReverseOrderService salesResultReverseOrderService;
	
	private static Properties prop = new Properties();
	static {
		try {
			prop.load(SalesTradeHandler.class.getClassLoader().getResourceAsStream("serverHost.properties"));
		} catch (IOException e) {
			logger.error("", e);
		}
	}

	@Override
	public Order createOrder(CreateOrderBean createOrderBean) throws BusinessException {
		logger.info("商户订单号:{}，开始创建订单。", createOrderBean.getOutOrderId());
		CreateSalesOrderBean orderBean = (CreateSalesOrderBean) createOrderBean;
		// 商编、商户订单号查询支付订单
		Order order = tradeOrderService.queryByRequestCode(orderBean.getReceiver(), orderBean.getOutOrderId());
		// 当前系统时间
		Date currentDate = new Date();
		if (order != null) {
			if (RepeatFlag.FALSE.equals(order.getRepeatFlag())) throw new BusinessException(ExceptionMessages.TRADE_ORDER_NOT_SUPPORT_REPEAT_PAY);
			if (OrderStatus.SUCCESS.equals(order.getStatus())) throw new BusinessException(ExceptionMessages.TRADE_ORDER_AREADY_SUCCESS);
			if (currentDate.compareTo(order.getTimeout()) >= 0) throw new BusinessException(ExceptionMessages.TRADE_ORDER_TIME_OUT);
			if (order.getCloseTime() != null && order.getCloseTime().compareTo(currentDate) <= 0) throw new BusinessException(
					ExceptionMessages.TRADE_ORDER_ALREADY_CLOSED);
			return order;
		}
		if(orderBean.getPayType() == null){
			orderBean.setPayType(PayType.UNKNOWN);
		}
		// 创建支付订单
		order = this.generateOrder(orderBean, currentDate);
		tradeOrderService.create(order);
		logger.info("商户订单号:{}，订单创建成功:{}。", createOrderBean.getOutOrderId(), JsonUtils.toJsonString(order));
		return order;
	}

	/**
	 * 新建支付订单
	 * @param orderBean 订单参数实体
	 * @param currentDate 当前系统时间
	 * @return Order
	 */
	private Order generateOrder(CreateSalesOrderBean orderBean, Date currentDate) {
		// 订单实体
		Order order = new Order();
		// 业务类型
		order.setBusinessType(orderBean.getBusinessType());
		// 业务标志1
		order.setBusinessFlag1(orderBean.getExtParam());
		// 商户订单号
		order.setRequestCode(orderBean.getOutOrderId());
		// 付款方角色
		order.setPayerRole(orderBean.getPayerRole());
		// 付款方
		//order.setPayer(orderBean.getPayer());
		// 收款方角色
		order.setReceiverRole(orderBean.getReceiverRole());
		// 收款方
		order.setReceiver(orderBean.getReceiver());
		// 订单重复支付标志
		order.setRepeatFlag(orderBean.getRetryFalg());
		// 币种
		order.setCurrency(StringUtils.isBlank(orderBean.getCurrency()) ? "CNY" : orderBean.getCurrency());
		// 订单金额
		order.setAmount(orderBean.getAmount());
		// 订单状态
		order.setStatus(OrderStatus.WAIT_PAY);
		// 下单时间
		order.setOrderTime(currentDate);
		// 订单超时时间
		order.setTimeout(orderBean.getTimeout());
		// 合作方页面通知地址
		order.setRedirectURL(orderBean.getRedirectURL());
		// 合作方后台通知地址
		order.setNotifyURL(orderBean.getNotifyURL());
		// 商品名稱
		order.setProducts(orderBean.getProducts());
		// 支付类型
		order.setPayType(orderBean.getPayType());
		return order;
	}

	@SuppressWarnings("unchecked")
	@Override
	public PayResultBean createPayment(CreatePaymentBean createPaymentBean) throws BusinessException {
		try {
			logger.info("订单号：{}，创建支付流水参数：{}", createPaymentBean.getTradeOrderCode(), createPaymentBean);
			// 支付接口编码
			String interfaceCode = createPaymentBean.getInterfaceCode();
			if (StringUtils.isBlank(interfaceCode)) throw new BusinessException(ExceptionMessages.PARAM_NOT_EXISTS);
			// 支付订单
			Order order = tradeOrderService.queryByCode(createPaymentBean.getTradeOrderCode());
			if (order == null) throw new BusinessException(ExceptionMessages.TRADE_ORDER_NOT_EXISTS);
			// 判断订单状态
			if (OrderStatus.SUCCESS.equals(order.getStatus())) throw new BusinessException(ExceptionMessages.TRADE_ORDER_AREADY_SUCCESS);

			// 页面银行编码（B2C_ICBC-DEBIT_CARD）
			String[] pageInterfaceCode = interfaceCode.split("-");
			String[] interfaceTypeAndInterfaceProvider = pageInterfaceCode[0].split("_");
			// 接口类型
			String interfaceType = interfaceTypeAndInterfaceProvider[0];
			// 接口提供方编码
			String interfaceProvider = interfaceTypeAndInterfaceProvider[1];
			// 卡种
			String cardType = "DEBIT_CARD";
			// 映射路由
			interfaceCode = executorRouterService.routeSpecifiedDestination(order, interfaceType, cardType, interfaceProvider);

			// 创建支付流水
			Payment payment = new Payment();
			String perfix = StringUtils.concatToSB("PA", order.getBusinessType().name().substring(0, 2)).toString();
			payment.setCode(CodeBuilder.build(perfix, "yyyyMMdd", 6));
			payment.setOrderCode(order.getCode());
			payment.setPayType(PayType.valueOf(interfaceType));
			payment.setPayTime(new Date());
			payment.setStatus(PaymentStatus.INIT);
			payment.setAmount(order.getAmount());
			payment.setPayinterface(interfaceCode);
			if (createPaymentBean.getRemitFee() != null && createPaymentBean.getQuickPayFee() != null) {
				// 计算用户手续费
				double payerFee = FeeUtils.computeFee(payment.getAmount(), createPaymentBean.getQuickPayFee());
				payment.setPayerFee(payerFee);
				payment.setRemitFee(createPaymentBean.getRemitFee());
			}
			checkAmount(new Date(), order.getReceiver(), payment.getPayType().name(), order.getAmount());
			paymentService.create(payment);

			Object returnMsg = null;
			// 组装钱包支付参数 (微信公众号支付、微信原生支付、支付宝支付)
			if("WXJSAPI".equals(interfaceType) || "WXNATIVE".equals(interfaceType) || "ALIPAY".equals(interfaceType)
					|| "WXMICROPAY".equals(interfaceType) || "ALIPAYMICROPAY".equals(interfaceType) || "ALIPAYJSAPI".equals(interfaceType)
					|| "QQNATIVE".equals(interfaceType) || "UNIONPAYNATIVE".equals(interfaceType) || "QQH5".equals(interfaceType)
					|| "JDH5".equals(interfaceType)) {
				WalletSalesTradeBean walletSalesTradeBean = new WalletSalesTradeBean();
				walletSalesTradeBean.setAmount(payment.getAmount());
				walletSalesTradeBean.setAuthCode(createPaymentBean.getAuthCode());
				walletSalesTradeBean.setBusinessOrderID(payment.getCode());
				walletSalesTradeBean.setBussinessFlowID(order.getCode());
				walletSalesTradeBean.setBusinessOrderTime(payment.getPayTime());
				walletSalesTradeBean.setClientIp(createPaymentBean.getClientIP());
				walletSalesTradeBean.setInterfaceCode(interfaceCode);
				walletSalesTradeBean.setInterfaceProviderCode(interfaceProvider);
				walletSalesTradeBean.setOwnerID(order.getReceiver());
				walletSalesTradeBean.setOwnerRole(order.getReceiverRole().name());
				if (StringUtils.notBlank(order.getProducts())) {
					walletSalesTradeBean.setProductName(order.getProducts());
				} else {
					walletSalesTradeBean.setProductName(customerInterface.getCustomer(order.getReceiver()).getShortName());
				}
				walletSalesTradeBean.setTradeType("PAY");
				if ("WXJSAPI".equals(interfaceType) || "WXMICROPAY".equals(interfaceType) || "ALIPAYMICROPAY".equals(interfaceType) || "ALIPAYJSAPI".equals(interfaceType)) {
					walletSalesTradeBean.setAuthCode(createPaymentBean.getAuthCode());
				}
				returnMsg = walletpayHessianService.pay(walletSalesTradeBean);
			} else if("AUTHPAY".equals(interfaceType) || "MOBILE".equals(interfaceType)){
				AuthSaleBean authSaleBean = new AuthSaleBean();
				authSaleBean.setAmount(payment.getAmount());
				authSaleBean.setBankCardNo(createPaymentBean.getBankCardNo());
				authSaleBean.setCertNo(createPaymentBean.getCertNo());
				authSaleBean.setCvv(createPaymentBean.getCvv());
				authSaleBean.setInterfaceCode(interfaceCode);
				authSaleBean.setOrderCode(order.getCode());
				authSaleBean.setOwnerId(order.getReceiver());
				authSaleBean.setPayerMobNo(createPaymentBean.getPayerMobNo());
				authSaleBean.setPayerName(createPaymentBean.getPayerName());
				authSaleBean.setPaymentCode(payment.getCode());
				authSaleBean.setProviderCode(interfaceProvider);
				authSaleBean.setValidityDay(createPaymentBean.getValidity());
				authSaleBean.setCreateTime(payment.getPayTime());
				authSaleBean.setToken(createPaymentBean.getVerifycode());
				if(StringUtils.notBlank(order.getProducts())){
					authSaleBean.setProductName(order.getProducts());
				}else{
					authSaleBean.setProductName(customerInterface.getCustomer(order.getReceiver()).getShortName());
				}
				returnMsg = authPayHessianService.pay(authSaleBean);
			} else if("QUICKPAY".equals(interfaceType)){
				QuickPayBean quickPayBean = new QuickPayBean();
				quickPayBean.setAmount(payment.getAmount());
				quickPayBean.setBankCardNo(createPaymentBean.getBankCardNo());
				quickPayBean.setCertNo(createPaymentBean.getCertNo());
				quickPayBean.setCvv(createPaymentBean.getCvv());
				quickPayBean.setInterfaceCode(interfaceCode);
				quickPayBean.setOrderCode(order.getCode());
				quickPayBean.setOwnerId(order.getReceiver());
				quickPayBean.setPayerMobNo(createPaymentBean.getPayerMobNo());
				quickPayBean.setPayerName(createPaymentBean.getPayerName());
				quickPayBean.setPaymentCode(payment.getCode());
				quickPayBean.setProviderCode(interfaceProvider);
				quickPayBean.setValidityDay(createPaymentBean.getValidity());
				quickPayBean.setCreateTime(payment.getPayTime());
				quickPayBean.setToken(createPaymentBean.getVerifycode());
                quickPayBean.setSettleAmount(createPaymentBean.getSettleAmount());
                quickPayBean.setSettleType(createPaymentBean.getSettleType());
                quickPayBean.setQuickPayFee(createPaymentBean.getQuickPayFee());
                quickPayBean.setRemitFee(createPaymentBean.getRemitFee());

                // 判断传入费率是否小于用户手续费
				CustomerFee quickPayFee = customerInterface.getCustomerFee(order.getReceiver(), interfaceType);
				CustomerFee remitFee = null;
				if (HolidayUtils.isHoliday()) {
					remitFee = customerInterface.getCustomerFee(order.getReceiver(), ProductType.HOLIDAY_REMIT.toString());
				} else {
					remitFee = customerInterface.getCustomerFee(order.getReceiver(), ProductType.REMIT.toString());
				}
				double quickFee = quickPayFee.getFee();


                if (Constants.QUICKPAY_INTERFACEINFO_FEN.equals(interfaceCode) ) {
					String[] feeInfo = PropertiesUtil.getQuickPayFen(order.getReceiver(), payment.getPayinterface());
					quickFee = Double.valueOf(feeInfo[0]);
					if (StringUtils.isBlank(quickPayBean.getSettleType())) {
						quickPayBean.setSettleType("FEE");
						quickPayBean.setQuickPayFee(quickFee);
						quickPayBean.setRemitFee(remitFee.getFee());
					}
				}

				if (quickPayBean.getQuickPayFee() != null && quickPayBean.getQuickPayFee() < quickFee) {
					throw new BusinessException(ExceptionMessages.CUSTOMER_FEE_LIMIT);
				}
				if (quickPayBean.getRemitFee() != null && quickPayBean.getRemitFee() < remitFee.getFee()) {
					throw new BusinessException(ExceptionMessages.CUSTOMER_FEE_LIMIT);
				}


				if(StringUtils.notBlank(order.getProducts())){
					quickPayBean.setProductName(order.getProducts());
				}else{
					quickPayBean.setProductName(customerInterface.getCustomer(order.getReceiver()).getShortName());
				}
				returnMsg = quickPayHessianService.pay(quickPayBean);
			} else {
				// 组装消费交易实体(网银支付)
				InternetbankSalesTradeBean internetbankSalesTradeBean = new InternetbankSalesTradeBean();
				
				// 兼容模式（认证支付非要当网银） --！
				if (StringUtils.isNotBlank(createPaymentBean.getBankCardNo())) {
					MobileInfoBean mobileInfoBean = new MobileInfoBean();
					mobileInfoBean.setBankCardNo(createPaymentBean.getBankCardNo());
					internetbankSalesTradeBean.setMobileInfoBean(mobileInfoBean);
				}
				
				internetbankSalesTradeBean.setAmount(payment.getAmount());
				internetbankSalesTradeBean.setCurrency(order.getCurrency());
				internetbankSalesTradeBean.setCardType(cardType);
				internetbankSalesTradeBean.setBusinessCode("ONLINE");
				internetbankSalesTradeBean.setBusinessOrderID(payment.getCode());
				internetbankSalesTradeBean.setBusinessOrderTime(payment.getPayTime());
				internetbankSalesTradeBean.setInterfaceCode(interfaceCode);
				internetbankSalesTradeBean.setInterfaceProviderCode(interfaceProvider);
				internetbankSalesTradeBean.setTradeType("PAY");
				internetbankSalesTradeBean.setInstallmentTimes("1");
				internetbankSalesTradeBean.setOwnerRole(order.getReceiverRole().name());
				internetbankSalesTradeBean.setOwnerID(order.getReceiver());
				internetbankSalesTradeBean.setBussinessFlowID(order.getCode());
				internetbankSalesTradeBean.setClientIp(createPaymentBean.getClientIP());
				internetbankSalesTradeBean.setOrderBackUrl(createPaymentBean.getPageNotifyUrl());
				if(StringUtils.notBlank(order.getProducts())){
					internetbankSalesTradeBean.setProductName(order.getProducts());
				}else{
					internetbankSalesTradeBean.setProductName(customerInterface.getCustomer(order.getReceiver()).getShortName());
				}
				returnMsg = internetbankHessianService.trade(internetbankSalesTradeBean);
			}
			if (returnMsg == null) throw new BusinessException(ExceptionMessages.UNKOWN_ERROR);
			Map<String,String> result = (Map<String,String>) returnMsg;
			// 记录支付接口流水
			if (result.get("interfaceRequestID") == null) throw new BusinessException(ExceptionMessages.PARAM_NOT_EXISTS);
			// tradeOrderService.modifyOrder(order);
			
			payment.setCode(payment.getCode());
			payment.setPayinterfaceRequestId(result.get("interfaceRequestID"));
			paymentService.update(payment);

			// 重定向地址
			String redirectURL = result.get("gateway");
			PayResultBean bean = new PayResultBean();
			bean.setType("SUBMIT");
			bean.setUrl(redirectURL);
			bean.setParams(result);

			// 推送至交易补单
			SalesResultReverseOrder srro = new SalesResultReverseOrder();
			srro.setAmount(order.getAmount().toString());
			srro.setBusinessOrderID(order.getCode());
			srro.setInterfaceRequestID(result.get("interfaceRequestID"));
			//srro.setInterfaceOrderID(interfaceOrderID);
			srro.setInterfaceProvider(interfaceProvider);
			srro.setCardType(cardType);
			//srro.setTransStatus(transStatus);
			//srro.setResponseCode(responseCode);
			//srro.setResponseMessage(responseMessage);
			//srro.setInterfaceFee(interfaceFee);
			srro.setReverseCount(0);
			salesResultReverseOrderService.recordSalesResultReverseOrder(srro);
			return bean;
		} catch (Exception e) {
			logger.error("", e);
			throw new BusinessException(e.getMessage());
		}
	}

	@Override
	public void complete(TradeContext tradeContext) throws BusinessException {
		Map<String, String> params = tradeContext.getRequestParameters();
		// 交易状态
		String interfaceStatus = params.get("transStatus");
		// 支付订单号
		String businessOrderID = params.get("businessOrderID");
		// 银行返回交易金额
		String bankMoneyString = params.get("amount");

		// 参数校验
		if (StringUtils.isBlank(interfaceStatus)) throw new BusinessException(StringUtils.concatToSB(ExceptionMessages.PARAM_NOT_EXISTS, "-", "transStatus")
				.toString());
		if (StringUtils.isBlank(businessOrderID)) throw new BusinessException(StringUtils.concatToSB(ExceptionMessages.PARAM_NOT_EXISTS, "-", "businessOrderID")
				.toString());
		if (StringUtils.isBlank(bankMoneyString)) throw new BusinessException(StringUtils.concatToSB(ExceptionMessages.PARAM_NOT_EXISTS, "-", "amount").toString());

		// 查询支付订单记录
		Order order = tradeOrderService.queryByCode(businessOrderID);
		logger.info("交易订单 完成处理 : {} " ,JsonUtils.toJsonString(order));
		if (order == null) throw new BusinessException(ExceptionMessages.TRADE_ORDER_NOT_EXISTS);
		if (!order.getStatus().equals(OrderStatus.WAIT_PAY)) throw new BusinessException(ExceptionMessages.TRADE_ORDER_AREADY_SUCCESS);

		// 校验支付金额和银行返回的金额是否一致
		if (!AmountUtils.eq(Double.valueOf(bankMoneyString), order.getAmount())) {
			throw new BusinessException(StringUtils.concatToSB(ExceptionMessages.TRADE_ORDER_PAY_MONEY_UNACCORDANCE, "-", "amount").toString());
		}
		tradeContext.setOrder(order);
		// 更新支付流水信息
		paymentService.complete(tradeContext);
		// 支付时间
		order.setSuccessPayTime(tradeContext.getPayment().getCompleteTime());

		Payment payment = paymentService.findByCode(tradeContext.getPayment().getCode());
		Date waitAccountDate = null;
		int cycle = 0;
		
		try {
			if (PaymentStatus.SUCCESS.equals(tradeContext.getPayment().getStatus())) {

				AccountBussinessInterfaceBean accountBussinessInterfaceBean = new AccountBussinessInterfaceBean();
				accountBussinessInterfaceBean.setBussinessCode("ONLINE_CREDIT");
				accountBussinessInterfaceBean.setOperator("ONLINE");
				accountBussinessInterfaceBean.setRemark("在线入账");
				accountBussinessInterfaceBean.setRequestTime(new Date());
				accountBussinessInterfaceBean.setSystemCode("ONLINE");
				accountBussinessInterfaceBean.setSystemFlowId(order.getCode());

				AccountSpecialCompositeTally accountCompositeTally = new AccountSpecialCompositeTally();

				// 交易金额
				SpecialTallyVoucher amount = new SpecialTallyVoucher();
				amount.setAccountType(AccountType.CASH);
				amount.setUserNo(order.getReceiver());
				amount.setUserRole(UserRole.CUSTOMER);
				amount.setAmount(payment.getAmount());
				amount.setSymbol(FundSymbol.PLUS);
				amount.setCurrency(Currency.RMB);
				amount.setFee(payment.getReceiverFee());
				amount.setFeeSymbol(FundSymbol.SUBTRACT);
				amount.setTransDate(order.getSuccessPayTime());
				amount.setTransOrder(order.getCode());
				
				AccountQuery accountQuery = new AccountQuery();
				accountQuery.setUserNo(order.getReceiver());
				accountQuery.setUserRole(UserRole.CUSTOMER);
				accountQuery.setAccountType(AccountType.CASH);
				AccountQueryResponse accountQueryResponse = accountQueryInterface.findAccountBy(accountQuery);
				
				if (payment.getPayType() == PayType.QUICKPAY || accountQueryResponse.getAccountBeans().get(0).getCycle() == 0) {
					amount.setWaitAccountDate(new Date());
				} else {
					cycle = accountQueryResponse.getAccountBeans().get(0).getCycle();
					amount.setWaitAccountDate(DateUtils.addDays(new Date(), accountQueryResponse.getAccountBeans().get(0).getCycle()));
				}

				waitAccountDate = amount.getWaitAccountDate();
				List<SpecialTallyVoucher> tallyVouchers = new ArrayList<SpecialTallyVoucher>();
				tallyVouchers.add(amount);

				accountCompositeTally.setSpecialTallyVouchers(tallyVouchers);
				accountBussinessInterfaceBean.setTradeVoucher(accountCompositeTally);

				AccountCompositeTallyResponse accountCompositeTallyResponse = accountInterface.specialCompositeTally(accountBussinessInterfaceBean);
				Status status = accountCompositeTallyResponse.getStatus();
				HandlerResult handleResult = accountCompositeTallyResponse.getResult();
				if (!(Status.SUCCESS.equals(status) && HandlerResult.SUCCESS.equals(handleResult))) throw new BusinessException("在线支付调用新账务系统,入账失败!");

				// 入账成功
				order.setClearingStatus(OrderClearingStatus.CLEARING_SUCCESS);
				order.setClearingFinishTime(new Date());
				tradeContext.setOrder(order);
			}
		} catch (Exception e) {
			logger.error("account failed accounting !... [tradeOrder:{}]", order.getCode(), e);
			// 入账失败
			order.setClearingStatus(OrderClearingStatus.CLEARING_FAILED);

			// 入账失败记录入账信息
			AccountFailInfo accountFacadeInfo = new AccountFailInfo();
			accountFacadeInfo.setAccountStatus(AccountFailStatus.FAILED);
			accountFacadeInfo.setAmount(order.getAmount());
			accountFacadeInfo.setOrderCode(order.getCode());
			accountService.createAccountInfo(accountFacadeInfo);
		}
		
		// 更新支付订单信息
		tradeOrderService.complete(tradeContext);
		
		// 分润
		try {
			if (PaymentStatus.SUCCESS.equals(tradeContext.getPayment().getStatus())) {
				ShareProfitBean shareProfit = new ShareProfitBean();
				shareProfit.setCustomerNo(order.getReceiver());
				shareProfit.setFee(order.getReceiverFee());
				shareProfit.setChannelCost(payment.getPayinterfaceFee());
				shareProfit.setAmount(payment.getAmount());
				shareProfit.setInterfaceCode(payment.getPayinterface());
				shareProfit.setOrderCode(order.getCode());
				shareProfit.setOrderTime(order.getSuccessPayTime());
				shareProfit.setProductType(ProductType.valueOf(payment.getPayType().toString()));
				shareProfit.setSource("ONLINE_TRADE");
				shareProfitInterface.createShareProfit(shareProfit);
//				producerClient.sendMessage(new ProducerMessage(Constants.SHARE_TOPIC, JsonUtils.toJsonString(shareProfit).getBytes()));
			}
		} catch (Exception e) {
			logger.error("shareProfit failed!... [tradeOrder:{}]", order.getCode(), e);
		}
		
		// 通知网关系统
		try {
			MerchantSalesResultReverseOrder merchantSalesResultReverseOrder = new MerchantSalesResultReverseOrder();
			merchantSalesResultReverseOrder.setReceiver(order.getReceiver());
			merchantSalesResultReverseOrder.setOrderCode(order.getCode());
			merchantSalesResultReverseOrder.setRequestCode(order.getRequestCode());
			merchantSalesResultReverseOrder.setReverseCount(1);
			merchantSalesResultReverseOrder.setReverseStatus(ReverseStatus.WAIT_REVERSE);
			merchantSalesResultReverseOrder.setStatus(order.getStatus());
			merchantSalesResultReverseOrderService.createMerchantSalesResultReverseOrder(merchantSalesResultReverseOrder);
			
			salesNotifyHessianService.notify(order.getCode(), order.getStatus().toString(), order.getRequestCode(), order.getReceiver());
			
			merchantSalesResultReverseOrder.setReverseStatus(ReverseStatus.SUCC_REVERSE);
			merchantSalesResultReverseOrderService.updateMerchantSalesResultReverseOrder(merchantSalesResultReverseOrder);
		} catch (Exception e) {
			logger.error("notify failed!... [tradeOrder:{}]", order.getCode(), e);
		}
		try {
			if (PaymentStatus.SUCCESS.equals(tradeContext.getPayment().getStatus())) {
				ServiceConfigBean configBean = serviceConfigFacade.findServiceConfigById(order.getReceiver());
				if(FireType.AUTO.name().equals(configBean.getFireType()) && cycle > 0){
					params = new HashMap<>();
					params.put("orderCode", order.getCode());
					params.put("ownerId", order.getReceiver());
					params.put("ownerRole", OwnerRole.CUSTOMER.name());
					params.put("amount", String.valueOf(AmountUtils.subtract(order.getAmount(), order.getReceiverFee())));
					params.put("accountDate", DateFormatUtils.format(waitAccountDate, "yyyyMMddHHmmss"));
					params.put("operator", Constants.APP_NAME);
					producerClient.sendMessage(new ProducerMessage(Constants.DPAY_TOPIC, JsonUtils.toJsonString(params).getBytes()));
				}
			}
		} catch (Exception e) {
			logger.error("auto dpay failed!... [tradeOrder:{}]", order.getCode(), e);
		}

		// todo 快捷支付记录卡交易信息

//		try {
//			SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//			Map<String, Object> share=shareProfitInterface.findByOrderCode(order.getCode());
//			Order ord=tradeOrderService.queryByCode(order.getCode());
//			Map<String, Object> map=new HashMap<String, Object>();
//			map.put("code", order.getCode());
//			if(ord.getCreateTime()!=null){
//				map.put("createTime", df.format(ord.getCreateTime()).toString());
//			}
//			map.put("requestCode", ord.getRequestCode());
//			map.put("payerRole", ord.getPayerRole());
//			map.put("receiverRole", ord.getReceiverRole());
//			map.put("receiver", ord.getReceiver());
//			map.put("amount", ord.getAmount());
//			map.put("paidAmount", ord.getPaidAmount());
//			map.put("payerFee", ord.getPayerFee());
//			map.put("receiverFee", ord.getReceiverFee());
//			map.put("cost", ord.getCost());
//			map.put("status", ord.getStatus());
//			map.put("platformProfit", share.get("platformProfit"));
//			map.put("clearingStatus", ord.getClearingStatus());
//			if(ord.getClearingFinishTime()!=null){
//				map.put("clearingFinishTime", df.format(ord.getClearingFinishTime()).toString());
//			}
//			if(ord.getOrderTime()!=null){
//				map.put("orderTime", df.format(ord.getOrderTime()).toString());
//			}
//			if(ord.getSuccessPayTime()!=null){
//				map.put("successPayTime", df.format(ord.getSuccessPayTime()).toString());
//			}
//			map.put("payType", ord.getPayType());
//			producerClient.sendMessage(new ProducerMessage(Constants.DATA_CENTER_TOPIC,Constants.ORDER_TAG,JsonUtils.toJsonString(map).getBytes()));
//		} catch (Exception e) {
//			logger.error("推送交易订单到数据中心失败, 错误消息{},请求单号{}",e,order.getRequestCode());
//		}
	}
	
	public void checkAmount(Date currentDate, String rec, String payType, double amount) throws BusinessException{
		CustomerConfig config = null;
		double sumAmount = tradeOrderService.orderAmountSum(currentDate, rec, payType);
		config = customerConfigService.findByCustomerNo(rec, payType);
		if(config != null){
			logger.info("商户号:{}，交易配置{}。", rec, JsonUtils.toJsonString(config));
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
			String str = sdf.format(currentDate);
			String statr = config.getStartTime();//配置开始时间
			String end = config.getEndTime();//配置结束时间
			double maxAmount = config.getMaxAmount();//最大金额
			double minAmount = config.getMinAmount();//最小金额
			//开始校验是否在交易时间段
			if(Integer.parseInt(str.split(":")[0]) >= Integer.parseInt(statr.split(":")[0]) && Integer.parseInt(str.split(":")[0]) <= Integer.parseInt(end.split(":")[0])){
				//开始  时相同   检测分
				if(Integer.parseInt(str.split(":")[0]) == Integer.parseInt(statr.split(":")[0])){
					if(Integer.parseInt(str.split(":")[1]) >= Integer.parseInt(statr.split(":")[1])){
					}else{
						throw new BusinessException(ExceptionMessages.CUSTOMER_CONFIG_DATA);  //0201商户交易不在可用时间段
					}
				}
				// 结束 时相同 检测分
				if(Integer.parseInt(str.split(":")[0]) == Integer.parseInt(end.split(":")[0])){
					if(Integer.parseInt(str.split(":")[1]) <= Integer.parseInt(end.split(":")[1])){
					}else{
						throw new BusinessException(ExceptionMessages.CUSTOMER_CONFIG_DATA);  //0201商户交易不在可用时间段
					}
				}
				//开始校验支付金额是否超过或者不满足设置值
				if(amount <= maxAmount && amount >= minAmount){
				}else{
					throw new BusinessException(ExceptionMessages.CUSTOMER_CONFIG_AMOUNT);  //0205商户交易金额非法
				}
				//开始校验是否超过日上限
				if(sumAmount + amount <= config.getDayMax()){
				}else{
					throw new BusinessException(ExceptionMessages.CUSTOMER_CONFIG_DAY_MAX);  //0204商户当日交易金额超过上限
				}
			}else{
				throw new BusinessException(ExceptionMessages.CUSTOMER_CONFIG_DATA);  //0201商户交易不在可用时间段
			}
		}
	}
}