package com.yl.online.gateway.web.controller;

import com.lefu.commons.cache.util.CacheUtils;
import com.lefu.commons.utils.lang.AmountUtils;
import com.lefu.commons.utils.lang.JsonUtils;
import com.lefu.commons.utils.lang.StringUtils;
import com.lefu.commons.utils.security.DigestUtils;
import com.yl.boss.api.bean.Customer;
import com.yl.boss.api.bean.CustomerFee;
import com.yl.boss.api.bean.CustomerKey;
import com.yl.boss.api.bean.TransCardBean;
import com.yl.boss.api.enums.*;
import com.yl.boss.api.interfaces.CustomerInterface;
import com.yl.online.gateway.AuthExceptionMessages;
import com.yl.online.gateway.Constants;
import com.yl.online.gateway.ExceptionMessages;
import com.yl.online.gateway.bean.MobileInfoBean;
import com.yl.online.gateway.bean.PayParam;
import com.yl.online.gateway.exception.BusinessException;
import com.yl.online.gateway.exception.BusinessRuntimeException;
import com.yl.online.gateway.service.OemTradeHandler;
import com.yl.online.gateway.service.PartnerRequestService;
import com.yl.online.gateway.service.TradeHandler;
import com.yl.online.gateway.utils.*;
import com.yl.online.gateway.utils.kingpassUtils.DesUtils;
import com.yl.online.gateway.utils.kingpassUtils.KingPassUtils;
import com.yl.online.gateway.utils.kingpassUtils.RSASignUtil;
import com.yl.online.model.bean.PayResultBean;
import com.yl.online.model.enums.FeeType;
import com.yl.online.model.model.Order;
import com.yl.online.model.model.PartnerRequest;
import com.yl.online.trade.hessian.OnlinePartnerRouterHessianService;
import com.yl.online.trade.hessian.OnlineTradeHessianService;
import com.yl.online.trade.hessian.bean.InterfaceInfoForRouterBean;
import com.yl.online.trade.hessian.bean.InterfacePolicyProfileBean;
import com.yl.payinterface.core.bean.BindCardInfoBean;
import com.yl.payinterface.core.bean.QuickPayBean;
import com.yl.payinterface.core.bean.QuickPayOpenCardRequestBean;
import com.yl.payinterface.core.bean.QuickPayOpenCardResponseBean;
import com.yl.payinterface.core.enums.AuthPayBindCardInfoBeanStatus;
import com.yl.payinterface.core.hessian.*;
import com.yl.sms.SmsUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.RoundingMode;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 认证支付交易控制器
 *
 * @author 聚合支付有限公司
 * @version V1.0.0
 * @since 2016年9月23日
 */
@Controller
@RequestMapping("/quick")
public class AuthTradeController {
	private static final Logger logger = LoggerFactory.getLogger(AuthTradeController.class);

	private final static String SETTLE_TYPE_FEE = "FEE";
	private final static String SETTLE_TYPE_AMOUNT = "AMOUNT";
	@Resource
	private OnlineTradeHessianService onlineTradeHessianService;
	@Resource
	private OnlinePartnerRouterHessianService onlinePartnerRouterHessianService;
	@Resource
	private PartnerRequestService partnerRequestService;
	@Resource
	private Map<String, TradeHandler> tradeHandlers;
	@Resource
	private CustomerInterface customerInterface;
	@Resource
	private BindCardHessianService bindCardHessianService;
	@Resource
	private AuthPayHessianService authPayHessianService;
	@Resource
	private QuickPayHessianService quickPayHessianService;
	@Resource
	private OemTradeHandler oemTradeHandler;
	@Resource
	private BindCardInfoHessianService bindCardInfoHessianService;
	@Resource
	private QuickPayOpenCardHessianService quickPayOpenCardHessianService;

	/**
	 * 快捷支付
	 *
	 * @param request
	 *            请求信息
	 * @return
	 */
	@RequestMapping("/pay")
	public String pay(Model model, HttpServletRequest request) {
		// 请求参数
		Map<String, String> reqParams = null;
		try {
			reqParams = CommonUtils.commonPareseParams(request);
		} catch (Exception e) {
			logger.error("快捷支付 获取下单请求报文 : ", e);
		}
		logger.info("快捷支付 下单请求报文 : {}", reqParams);

		try {
			// 校验参数
			Customer customer = checkCommonParam(reqParams);
			signValidate(reqParams);
			CommonUtils.amountCheck(reqParams.get("amount"));

			// 获取有效资金通道信息
			Map<String, List<InterfacePolicyProfileBean>> interfacePolicyProfileBeans = onlinePartnerRouterHessianService.queryPartnerRouterBy("CUSTOMER", reqParams.get(Constants.PARAM_NAME_PARTNER));
			// 路由不存在
			if (interfacePolicyProfileBeans == null) {
				throw new BusinessException(ExceptionMessages.CUSTOMER_PARTNERROUTER);
			}
			// 如果是九派快捷，人脸认证
			String interfaceInfoCode = getInterfaceCode(interfacePolicyProfileBeans, reqParams.get(Constants.PAY_TYPE), reqParams.get(Constants.PARAM_INTERFACE_CODE));
			if (interfaceInfoCode != null && "QUICKPAY_KINGPASS-100001".equals(interfaceInfoCode)) {
				return kingPassFaceAuth(reqParams, model);
			}

			return payHandle(reqParams, customer, model, request);
		} catch (Exception e) {
			logger.info("快捷支付 下单请求错误 : {}", e);
			if (e instanceof BusinessException || e instanceof BusinessRuntimeException) {
				model.addAttribute("responseCode", e.getMessage());
				model.addAttribute("responseMsg", AuthExceptionMessages.getMessage(e.getMessage()));
			} else {
				model.addAttribute("responseCode", AuthExceptionMessages.SEND_SMS_FAILED.getCode());
				model.addAttribute("responseMsg", AuthExceptionMessages.getMessage(AuthExceptionMessages.SEND_SMS_FAILED.getCode()));
			}
			return "auth/payFail";
		}
	}

	/**
	 * 快捷支付
	 *
	 * @param request
	 *            请求信息
	 * @return
	 */
	@RequestMapping("/interface/pay")
	public String interfacePay(Model model, HttpServletRequest request) {
		// 请求参数
		Map<String, String> reqParams = null;
		try {
			reqParams = CommonUtils.commonPareseParams(request);
		} catch (Exception e) {
			logger.error("快捷支付 获取下单请求报文 : ", e);
		}
		logger.info("快捷支付 下单请求报文 : {}", reqParams);

		try {
			// 校验参数
			Customer customer = checkCommonParam(reqParams);
			signValidate(reqParams);
			CommonUtils.amountCheck(reqParams.get(Constants.PARAM_NAME_AMOUNT));
			CommonUtils.amountCheck(reqParams.get(Constants.SETTLE_AMOUNT));
			checkSettleAmount(customer.getCustomerNo(), reqParams.get(Constants.SETTLE_AMOUNT), reqParams.get(Constants.PARAM_NAME_AMOUNT));
			return payHandle(reqParams, customer, model, request);
		} catch (Exception e) {
			logger.error("快捷支付 接口下单处理异常 ", e);
			if (e instanceof BusinessException || e instanceof BusinessRuntimeException) {
				model.addAttribute("responseCode", e.getMessage());
				model.addAttribute("responseMsg", AuthExceptionMessages.getMessage(e.getMessage()));
			} else {
				model.addAttribute("responseCode", AuthExceptionMessages.SEND_SMS_FAILED.getCode());
				model.addAttribute("responseMsg", AuthExceptionMessages.getMessage(AuthExceptionMessages.SEND_SMS_FAILED.getCode()));
			}
			return "auth/payFail";
		}
	}

	/**
	 * 下单处理
	 *
	 * @param reqParams
	 * @param customer
	 * @param model
	 * @param request
	 * @return
	 * @throws BusinessException
	 */
	private String payHandle(Map<String, String> reqParams, Customer customer, Model model, HttpServletRequest request) throws BusinessException {
		// 支付订单号
		String tradeOrderCode;
		PartnerRequest partnerRequest = new PartnerRequest();
		// 卡号校验
		CommonUtils.checkCardNo(reqParams.get(Constants.PARAM_NAME_CARD_NO));
		// 交易卡校验
		checkTransCard(reqParams.get(Constants.PARAM_NAME_PARTNER), reqParams.get(Constants.PARAM_NAME_CARD_NO));
		partnerRequest.setAmount(reqParams.get(Constants.PARAM_NAME_AMOUNT));
		partnerRequest.setApiCode(reqParams.get(Constants.PARAM_NAME_API_CODE));
		partnerRequest.setInputCharset(reqParams.get(Constants.PARAM_NAME_INPUT_CHARSET));
		partnerRequest.setOutOrderId(reqParams.get(Constants.PARAM_NAME_OUT_ORDER_ID));
		partnerRequest.setPartner(reqParams.get(Constants.PARAM_NAME_PARTNER));
		partnerRequest.setSign(reqParams.get(Constants.PARAM_NAME_SIGN));
		partnerRequest.setSignType(reqParams.get(Constants.PARAM_NAME_SIGN_TYPE));
		partnerRequest.setBankCardNo(reqParams.get(Constants.PARAM_NAME_CARD_NO));
		partnerRequest.setSettleCardNo(reqParams.get(Constants.SETTLE_CAED_NO));
		partnerRequest.setSettleName(reqParams.get(Constants.SETTLE_BANK_NAME));
		partnerRequest.setPayType(reqParams.get(Constants.PAY_TYPE));
		partnerRequest.setSettleAmount(reqParams.get(Constants.SETTLE_AMOUNT));
		partnerRequest.setOriginalRequest(JsonUtils.toJsonString(JsonUtils.toObject(URLDecoder.decode(JsonUtils.toJsonString(request.getParameterMap())), new TypeReference<Map<String, String[]>>() {
		})));
		partnerRequest.setNotifyUrl(reqParams.get(Constants.PARAM_NAME_NOTIFYURL));
		partnerRequest.setReturnParam(reqParams.get(Constants.PARAM_NAME_RETURNPARAM));
		partnerRequest.setExtParam(reqParams.get(Constants.PARAM_NAME_EXTPARAM));
		partnerRequest.setInterfaceCode(reqParams.get(Constants.PARAM_INTERFACE_CODE));
		partnerRequest.setProduct(reqParams.get(Constants.PRODUCT_NAME));
		partnerRequest.setQuickPayFee(reqParams.get(Constants.QUICK_PAY_FEE));
		partnerRequest.setRemitFee(reqParams.get(Constants.REMIT_FEE));
		partnerRequest.setSettleType(reqParams.get(Constants.SETTLE_TYPE));

		if (customer.getCustomerType() == CustomerBeanType.OEM) {
			// 调用相关OEM系统。
			String oemResult = oemTradeHandler.requestOme(partnerRequest);
			if (!"SUCCESS".equals(oemResult)) {
				throw new BusinessException(oemResult);
			} else {
				if (SETTLE_TYPE_FEE.equals(partnerRequest.getSettleType())) {
					reqParams.put(Constants.REMIT_FEE, partnerRequest.getRemitFee());
					reqParams.put(Constants.QUICK_PAY_FEE, partnerRequest.getQuickPayFee());
				}
			}
		}
		partnerRequestService.save(partnerRequest);

		TradeHandler tradeHandler = tradeHandlers.get(partnerRequest.getApiCode());
		// 创建支付订单
		tradeOrderCode = tradeHandler.execute(partnerRequest);

		// 获取有效资金通道信息
		Map<String, List<InterfacePolicyProfileBean>> interfacePolicyProfileBeans = onlinePartnerRouterHessianService.queryPartnerRouterBy("CUSTOMER", partnerRequest.getPartner());
		// 路由不存在
		if (interfacePolicyProfileBeans == null) {
			throw new BusinessException(ExceptionMessages.CUSTOMER_PARTNERROUTER);
		}
		reqParams.put("interfaceCode", getInterfaceCode(interfacePolicyProfileBeans, reqParams.get(Constants.PAY_TYPE), partnerRequest.getInterfaceCode()));
		reqParams.put("cardNo", reqParams.get(Constants.PARAM_NAME_CARD_NO));
		Map<String, String> resParams = bindCardHessianService.queryBindCard(reqParams);
		if ("00".equals(resParams.get("responseCode"))) {
			// 加载接口编码
			String interfaceCode = "";
			if (StringUtils.notBlank(partnerRequest.getInterfaceCode())) {
				interfaceCode = partnerRequest.getInterfaceCode();
			} else {
				Map<String, List<String>> interfaceInfos = gatewayPageDestructor(interfacePolicyProfileBeans);
				interfaceCode = interfaceInfos.get(partnerRequest.getPayType()).get(0);
			}
			PayParam payParam = new PayParam();
			payParam.setTradeOrderCode(tradeOrderCode);
			payParam.setInterfaceCode(interfaceCode);
			MobileInfoBean mobileInfoBean = new MobileInfoBean();
			mobileInfoBean.setBankCardNo(partnerRequest.getBankCardNo());
			mobileInfoBean.setCertNo(partnerRequest.getCertNo());
			mobileInfoBean.setCvv(partnerRequest.getCvv());
			mobileInfoBean.setValidity(partnerRequest.getValDate());
			mobileInfoBean.setPayerMobNo(partnerRequest.getPayerMobNo());
			mobileInfoBean.setPayerName(partnerRequest.getPayerName());
			// 如果selletType不为空，则判断结算类型
			if (StringUtils.notBlank(partnerRequest.getSettleType())) {
				mobileInfoBean.setSettleType(partnerRequest.getSettleType());
				// 如果是费率
				if (SETTLE_TYPE_FEE.equals(partnerRequest.getSettleType())) {
					mobileInfoBean.setQuickPayFee(Double.valueOf(partnerRequest.getQuickPayFee()));
					mobileInfoBean.setRemitFee(Double.valueOf(partnerRequest.getRemitFee()));
					// 如果是金额
				} else if (SETTLE_TYPE_AMOUNT.equals(partnerRequest.getSettleType()) && StringUtils.notBlank(partnerRequest.getSettleAmount())) {
					mobileInfoBean.setSettleAmount(Double.valueOf(partnerRequest.getSettleAmount()));
				} else {
					model.addAttribute("responseCode", AuthExceptionMessages.SETTLE_TYPE_ERROR.getCode());
					model.addAttribute("responseMsg", AuthExceptionMessages.SETTLE_TYPE_ERROR.getMessage());
					return "auth/payFail";
				}
			}
			mobileInfoBean.setVerifycode(resParams.get("token"));
			payParam.setMobileInfoBean(mobileInfoBean);
			PayResultBean bean = tradeHandler.pay(payParam);
			bean.getParams().put("customerName", customer.getShortName());
			bean.getParams().put("customerNo", customer.getCustomerNo());
			bean.getParams().put("outOrderId", partnerRequest.getOutOrderId());
			model.addAttribute("params", bean.getParams());
			model.addAttribute("result", bean);
			model.addAttribute("responseCode", bean.getParams().get("responseCode"));
			model.addAttribute("responseMsg", AuthExceptionMessages.getMessage(bean.getParams().get("responseCode")));
			if ("77".equals(bean.getParams().get("responseCode"))) {
				model.addAttribute("customerName", customer.getShortName());
				model.addAttribute("amount", partnerRequest.getAmount());
				model.addAttribute("bankCardNo", partnerRequest.getBankCardNo());
				model.addAttribute("tradeOrderCode", tradeOrderCode);
				model.addAttribute("interfaceCode", reqParams.get("interfaceCode"));
				model.addAttribute("smsCodeType", bean.getParams().get("smsCodeType"));
				return bean.getParams().get("gateway");
			} else if (!"00".equals(bean.getParams().get("responseCode"))) {
				return "auth/payFail";
			}
			if (StringUtils.notBlank(bean.getParams().get("payPage")) && "INTERFACE".equals(bean.getParams().get("payPage"))) {
				model.addAttribute("params", bean.getParams());
				return bean.getParams().get("gateway");
			}
			return "auth/pay";
		} else {
			reqParams.put("tradeOrderCode", tradeOrderCode);
			resParams = bindCardHessianService.bindCard(reqParams);
			if ("00".equals(resParams.get("responseCode"))) {
				if ("00".equals(resParams.get("result"))) {
					model.addAttribute("customerName", customer.getShortName());
					model.addAttribute("amount", partnerRequest.getAmount());
					model.addAttribute("bankCardNo", partnerRequest.getBankCardNo());
					model.addAttribute("tradeOrderCode", tradeOrderCode);
					model.addAttribute("interfaceCode", reqParams.get("interfaceCode"));
					model.addAttribute("smsCodeType", resParams.get("smsCodeType"));
				}
				model.addAttribute("url", resParams.get("url"));
				model.addAttribute("params", resParams);
				return resParams.get("gateway");
			}
			model.addAttribute("responseCode", resParams.get("responseCode"));
			model.addAttribute("responseMsg", AuthExceptionMessages.getMessage(resParams.get("responseCode")));
			return "auth/payFail";
		}
	}

	@RequestMapping("/sale")
	public void sale(HttpServletRequest request, HttpServletResponse response) {
		Map<String, String[]> OriginalParams = request.getParameterMap();
		Map<String, String> params = new HashMap<>();
		for (String key : OriginalParams.keySet()) {
			params.put(key, OriginalParams.get(key)[0]);
		}
		QuickPayBean quickPayBean = new QuickPayBean();
		quickPayBean.setMerOrderId(params.get("interfaceRequestID"));
		quickPayBean.setInterfaceCode(params.get("interfaceCode"));
		quickPayBean.setToken(params.get("token"));
		quickPayBean.setVerifyCode(params.get("smsCode"));
		quickPayBean.setBankCardNo(params.get("payCardNo"));
		quickPayBean.setOwnerId(params.get("customerNo"));
		Map<String, String> resParams = quickPayHessianService.sale(quickPayBean);
		params.put("responseCode", resParams.get("responseCode"));
		if (!"00".equals(resParams.get("responseCode"))) {
			params.put("responseMsg", resParams.get("responseMsg"));
			if ("77".equals(resParams.get("responseCode"))) {
				params.put("smsCodeType", resParams.get("smsCodeType"));
			}
		}
		try {
			response.getWriter().print(JsonUtils.toJsonString(params));
		} catch (Exception e) {
			logger.error("快捷支付 消费结果:{} 响应异常:{}", params, e);
		}
	}

	/**
	 * 加载路由
	 *
	 * @param interfacePolicyProfileBeans
	 * @return
	 */
	private Map<String, List<String>> gatewayPageDestructor(Map<String, List<InterfacePolicyProfileBean>> interfacePolicyProfileBeans) {
		Map<String, List<String>> interfaceInfos = new HashMap<String, List<String>>();
		// 银行编码
		String supportProvider;
		// 支付工具分类银行列表
		List<String> bankCodes;
		for (Map.Entry<String, List<InterfacePolicyProfileBean>> entry : interfacePolicyProfileBeans.entrySet()) {
			if (com.yl.online.model.enums.InterfaceType.QUICKPAY.name().equals(entry.getKey())) {
				for (InterfacePolicyProfileBean interfacePolicyProfileBean : entry.getValue()) {
					supportProvider = StringUtils.concatToSB(com.yl.online.model.enums.InterfaceType.QUICKPAY.name(), "_", interfacePolicyProfileBean.getInterfaceProviderCode()).toString();
					if (interfaceInfos.containsKey(entry.getKey())) {
						if (interfaceInfos.get(entry.getKey()).contains(supportProvider))
							continue;
						interfaceInfos.get(entry.getKey()).add(supportProvider);
					} else {
						bankCodes = new ArrayList<String>();
						bankCodes.add(supportProvider);
						interfaceInfos.put(com.yl.online.model.enums.InterfaceType.QUICKPAY.name(), bankCodes);
					}
				}
			}
		}
		return interfaceInfos;
	}

	/**
	 * 获取接口编号
	 *
	 * @param interfacePolicyProfileBeans
	 * @return
	 */
	private String getInterfaceCode(Map<String, List<InterfacePolicyProfileBean>> interfacePolicyProfileBeans, String payType, String pageInterfaceCode) {
		String interfaceCode = "";
		if (StringUtils.notBlank(pageInterfaceCode)) {
			String[] pageInterfaceCodes = pageInterfaceCode.split("_");
			int index = 99;
			for (Map.Entry<String, List<InterfacePolicyProfileBean>> entry : interfacePolicyProfileBeans.entrySet()) {
				if (payType.equals(entry.getKey())) {
					for (InterfacePolicyProfileBean interfacePolicyProfileBean : entry.getValue()) {
						if (pageInterfaceCodes[1].equals(interfacePolicyProfileBean.getInterfaceProviderCode())) {
							List<InterfaceInfoForRouterBean> routeBeans = interfacePolicyProfileBean.getInterfaceInfos();
							for (InterfaceInfoForRouterBean route : routeBeans) {
								if (route.getScale() < index) {
									index = route.getScale();
									interfaceCode = route.getInterfaceCode();
								}
							}
						}
					}
				}
			}
		} else {
			int index = 99;
			for (Map.Entry<String, List<InterfacePolicyProfileBean>> entry : interfacePolicyProfileBeans.entrySet()) {
				if (payType.equals(entry.getKey())) {
					for (InterfacePolicyProfileBean interfacePolicyProfileBean : entry.getValue()) {
						List<InterfaceInfoForRouterBean> routeBeans = interfacePolicyProfileBean.getInterfaceInfos();
						for (InterfaceInfoForRouterBean route : routeBeans) {
							if (route.getScale() < index) {
								index = route.getScale();
								interfaceCode = route.getInterfaceCode();
							}
						}
					}
				}
			}
		}

		return interfaceCode;
	}

	/**
	 * 发送验证码 并组装支付页面参数
	 *
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/toPay")
	public String toPay(Model model, HttpServletRequest request, HttpServletResponse response) {
		Map<String, String> reqParams;
		try {
			String bindCardInfo = CacheUtils.get("BINDCARD:" + request.getParameter("key"), String.class);
			reqParams = JsonUtils.toObject(bindCardInfo, new TypeReference<Map<String, String>>() {
			});
			if (reqParams == null) {
				throw new BusinessException("绑卡通知报文为空");
			}
		} catch (Exception e) {
			logger.error("绑卡失败 : ", e);
			model.addAttribute("responseCode", AuthExceptionMessages.BIND_FAILED.getCode());
			model.addAttribute("responseMsg", AuthExceptionMessages.getMessage(AuthExceptionMessages.BIND_FAILED.getCode()));
			return "auth/payFail";
		}

		try {
			TradeHandler tradeHandler = tradeHandlers.get("YL-PAY");
			Order order = onlineTradeHessianService.queryOrderByCode(reqParams.get("tradeOrderCode"));
			PartnerRequest partnerRequest = partnerRequestService.queryPartnerRequestByOutOrderId(order.getRequestCode(), order.getReceiver());
			// 发送验证码 并组装页面展示信息
			PayParam payParam = new PayParam();
			payParam.setTradeOrderCode(reqParams.get("tradeOrderCode"));
			MobileInfoBean mobileInfoBean = new MobileInfoBean();
			payParam.setInterfaceCode(reqParams.get("interfaceCode"));
			mobileInfoBean.setBankCardNo(reqParams.get("cardNo"));
			mobileInfoBean.setAmount(Double.valueOf(reqParams.get("amount")));
			if (StringUtils.isBlank(reqParams.get("bindCardStatus")) || !"SUCCESS".equals(reqParams.get("bindCardStatus"))) {
				Map<String, String> resParams = bindCardHessianService.queryBindCard(reqParams);
				if ("00".equals(resParams.get("responseCode"))) {
					reqParams.put("token", resParams.get("token"));
					reqParams.put("respCode", "00");
				} else {
					logger.error("绑卡失败 : ", resParams.get("responseCode"));
					model.addAttribute("responseCode", AuthExceptionMessages.BIND_FAILED.getCode());
					model.addAttribute("responseMsg", AuthExceptionMessages.getMessage(AuthExceptionMessages.BIND_FAILED.getCode()));
					return "auth/payFail";
				}
			}
			mobileInfoBean.setVerifycode(reqParams.get("token"));
			// 如果selletType不为空，则判断结算类型
			if (StringUtils.notBlank(partnerRequest.getSettleType())) {
				mobileInfoBean.setSettleType(partnerRequest.getSettleType());
				// 如果是费率
				if (SETTLE_TYPE_FEE.equals(partnerRequest.getSettleType())) {
					mobileInfoBean.setQuickPayFee(Double.valueOf(partnerRequest.getQuickPayFee()));
					mobileInfoBean.setRemitFee(Double.valueOf(partnerRequest.getRemitFee()));
					// 如果是金额
				} else if (SETTLE_TYPE_AMOUNT.equals(partnerRequest.getSettleType()) && StringUtils.notBlank(partnerRequest.getSettleAmount())) {
					mobileInfoBean.setSettleAmount(Double.valueOf(partnerRequest.getSettleAmount()));
				} else {
					model.addAttribute("responseCode", AuthExceptionMessages.SETTLE_TYPE_ERROR.getCode());
					model.addAttribute("responseMsg", AuthExceptionMessages.SETTLE_TYPE_ERROR.getMessage());
					return "auth/payFail";
				}
			}
			payParam.setMobileInfoBean(mobileInfoBean);
			PayResultBean bean = tradeHandler.pay(payParam);
			if ("00".equals(bean.getParams().get("responseCode"))) {
				if (StringUtils.notBlank(bean.getParams().get("payPage")) && "INTERFACE".equals(bean.getParams().get("payPage"))) {
					model.addAttribute("params", bean.getParams());
					return bean.getParams().get("gateway");
				}
				bean.getParams().put("outOrderId", order.getRequestCode());
				Customer customer = customerInterface.getCustomer(order.getReceiver());
				bean.getParams().put("customerNo", order.getReceiver());
				bean.getParams().put("customerName", customer.getShortName());
				model.addAttribute("params", bean.getParams());
				model.addAttribute("result", bean);
				return "auth/pay";
			} else {
				model.addAttribute("responseCode", bean.getParams().get("responseCode"));
				model.addAttribute("responseMsg", AuthExceptionMessages.getMessage(bean.getParams().get("responseCode")));
				return "auth/payFail";
			}
		} catch (Exception e) {
			logger.error("绑卡通知处理报文接收异常 : ", e);
			model.addAttribute("responseCode", AuthExceptionMessages.BIND_FAILED.getCode());
			model.addAttribute("responseMsg", AuthExceptionMessages.getMessage(AuthExceptionMessages.BIND_FAILED.getCode()));
			return "auth/payFail";
		}
	}

	/**
	 * 重新发送验证码
	 *
	 * @param request
	 * @param response
	 */
	@RequestMapping("reSend")
	public void reSend(HttpServletRequest request, HttpServletResponse response) {
		Map<String, String> params;
		try {
			String sendSmsInfo = CacheUtils.get("AUTH-SENDSMS:" + request.getParameter("key"), String.class);
			if (StringUtils.isBlank(sendSmsInfo)) {
				response.getWriter().write("01");
				return;
			}
			params = JsonUtils.toObject(sendSmsInfo, new TypeReference<Map<String, String>>() {
			});
			if (params == null || params.size() == 0) {
				response.getWriter().write("01");
				return;
			}
			params = authPayHessianService.sendVerifyCode(params);
			if ("00".equals(params.get("responseCode"))) {
				response.getWriter().write("00");
			} else {
				response.getWriter().write("01");
			}
		} catch (Exception e) {
			logger.error("重发验证码处理异常 : ", e);
			try {
				response.getWriter().write("01");
			} catch (Exception e1) {
				logger.error("重发验证码处理异常 响应页面异常 : ", e);
			}
		}
	}

	/**
	 * 签名检验
	 *
	 * @param params
	 *            原请求信息
	 * @throws BusinessException
	 */
	private void signValidate(Map<String, String> params) throws BusinessException {
		CustomerKey customerKey = customerInterface.getCustomerKey(params.get("partner"), KeyType.MD5);
		// 按参数名排序
		ArrayList<String> paramNames = new ArrayList<>(params.keySet());
		Collections.sort(paramNames);
		// 组织待签名信息
		StringBuilder signSource = new StringBuilder();
		Iterator<String> iterator = paramNames.iterator();
		while (iterator.hasNext()) {
			String paramName = iterator.next();
			if (!Constants.PARAM_NAME_SIGN.equals(paramName) && StringUtils.notBlank(params.get(paramName))) {
				signSource.append(paramName).append("=").append(params.get(paramName));
				if (iterator.hasNext())
					signSource.append("&");
			}
		}
		// 计算签名
		String calSign;
		signSource.append(customerKey.getKey());
		try {
			calSign = DigestUtils.md5DigestAsHex(signSource.toString().getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			throw new BusinessException(ExceptionMessages.PARAM_ERROR);
		}

		if (!params.get("sign").equals(calSign)) {
			logger.info("sign check error partnerCode={},sign={},serverSign={}", params.get("partnerCode"), params.get("sign"), calSign);
			throw new BusinessException(StringUtils.concatToSB(ExceptionMessages.SIGN_ERROR).toString());
		}
	}

	private Customer checkCommonParam(Map<String, String> reqParams) throws BusinessException {
		// 检查接口名称
		if (StringUtils.isBlank(reqParams.get(Constants.PARAM_NAME_API_CODE)) || "YL-PAY".equals(StringUtils.isBlank(reqParams.get(Constants.PARAM_NAME_API_CODE)))) {
			throw new BusinessException(ExceptionMessages.PARAM_NOT_EXISTS);
		}
		// 检查输入字符集
		if (StringUtils.isBlank(reqParams.get(Constants.PARAM_NAME_INPUT_CHARSET))) {
			throw new BusinessException(ExceptionMessages.PARAM_NOT_EXISTS);
		}
		// 检查合作方
		if (StringUtils.isBlank(reqParams.get(Constants.PARAM_NAME_PARTNER))) {
			throw new BusinessException(ExceptionMessages.RECEIVER_NOT_EXISTS);
		}
		// 检查签名方式
		if (StringUtils.isBlank(reqParams.get(Constants.PARAM_NAME_SIGN_TYPE)) || !"MD5".equals(reqParams.get(Constants.PARAM_NAME_SIGN_TYPE))) {
			throw new BusinessException(ExceptionMessages.PARAM_NOT_EXISTS);
		}
		// 检查签名
		if (StringUtils.isBlank(reqParams.get(Constants.PARAM_NAME_SIGN))) {
			throw new BusinessException(ExceptionMessages.PARAM_NOT_EXISTS);
		}
		// 检查支付类型
		if (StringUtils.isBlank(reqParams.get(Constants.PAY_TYPE))) {
			throw new BusinessException(ExceptionMessages.PARAM_NOT_EXISTS);
		} else {
			if (!"QUICKPAY".equals(reqParams.get(Constants.PAY_TYPE))) {
				throw new BusinessException(ExceptionMessages.PARAM_ERROR);
			}
		}
		// 检查商户
		Customer customer = customerInterface.getCustomer(reqParams.get(Constants.PARAM_NAME_PARTNER));
		if (customer == null) {
			throw new BusinessException(ExceptionMessages.RECEIVER_NOT_EXISTS);
		}
		// 检查商户状态
		if (customer.getStatus() != CustomerStatus.TRUE) {
			throw new BusinessException(ExceptionMessages.RECEIVER_STATUS_ERROR);
		}
		// 检查商户费率
		CustomerFee customerFee = customerInterface.getCustomerFee(customer.getCustomerNo(), ProductType.QUICKPAY.name());
		if (customerFee == null) {
			throw new BusinessException(ExceptionMessages.RECEIVER_NOT_OPEN_ONLINE_SERVICE);
		}
		return customer;
	}

	/**
	 * 交易卡校验
	 *
	 * @param customerNo
	 * @param cardNo
	 * @throws BusinessException
	 */
	private void checkTransCard(String customerNo, String cardNo) throws BusinessException {
		TransCardBean transCardBean = customerInterface.findTransCardByAccNo(customerNo, cardNo, CardAttr.TRANS_CARD);
		if (transCardBean == null || transCardBean.getCardStatus() == CardStatus.UNBUNDLED) {
			throw new BusinessException(AuthExceptionMessages.TRNS_CARD_UNBIND.getCode());
		} else if (transCardBean.getCardStatus() == CardStatus.DISABLED) {
			throw new BusinessException(AuthExceptionMessages.TRNS_CARD_UNABLE.getCode());
		}
	}

	/**
	 * 校验结算金额是否合法
	 *
	 * @param customerNo
	 * @param settleAmount
	 * @param transAmount
	 * @throws BusinessException
	 */
	private void checkSettleAmount(String customerNo, String settleAmount, String transAmount) throws Exception {
		double settleAmt = Double.valueOf(settleAmount);
		double transAmt = Double.valueOf(transAmount);

		// 校验 交易金额是否大于结算金额
		if (AmountUtils.leq(transAmt, settleAmt)) {
			throw new BusinessException(AuthExceptionMessages.TRANS_AMT_LE_SETTLET_AMT.getCode());
		}
		// 获取交易手续费
		CustomerFee quickPayFee = customerInterface.getCustomerFee(customerNo, "QUICKPAY");
		double transFee = FeeUtils.computeFee(transAmt, FeeType.valueOf(quickPayFee.getFeeType().name()), quickPayFee.getFee(), quickPayFee.getMinFee());
		double accAmount = AmountUtils.round(AmountUtils.subtract(transAmt, transFee), 2, RoundingMode.HALF_UP);

		// 校验 交易金额扣除交易手续费后是否大于结算金额
		if (AmountUtils.less(accAmount, settleAmt)) {
			throw new BusinessException(AuthExceptionMessages.TRANS_AMT_LE_SETTLET_AMT_TRANS_FEE.getCode());
		}

		// 获取结算手续费
		CustomerFee remitFee;
		// 是否假日结算
		if (HolidayUtils.isHoliday()) {
			remitFee = customerInterface.getCustomerFee(customerNo, "HOLIDAY_REMIT");
			if (remitFee == null) {
				throw new BusinessRuntimeException(AuthExceptionMessages.HOLIYDAY_SETTLE_STATUS_ERR.getCode());
			}
		} else {
			remitFee = customerInterface.getCustomerFee(customerNo, "REMIT");
			if (remitFee == null) {
				throw new BusinessRuntimeException(AuthExceptionMessages.SETTLE_STATUS_ERR.getCode());
			}
		}
		double settleFee = FeeUtils.computeFee(transAmt, FeeType.valueOf(remitFee.getFeeType().name()), remitFee.getFee(), remitFee.getMinFee());

		// 校验 交易金额扣除交易后是否大于结算金额
		if (AmountUtils.less(AmountUtils.round(AmountUtils.subtract(transAmt, settleFee), 2, RoundingMode.HALF_UP), settleAmt)) {
			throw new BusinessException(AuthExceptionMessages.TRANS_AMT_LE_SETTLET_AMT_TRANS_FEE.getCode());
		}
	}

	/**
	 * @Description 发送绑卡验证码
	 * @param request
	 * @param response
	 * @param orderCode
	 * @date 2017年11月1日 0001 参数为空 0002 订单为空 0003 卡片信息错误 0004 发送失败
	 */
	@RequestMapping("sendOpenCardVerifyCode")
	public void sendOpenCardVerifyCode(HttpServletRequest request, PrintWriter writer, HttpServletResponse response, String orderCode, String phone, String cardNo, String interfaceInfoCode, String smsCodeType) {
		try {
			if (StringUtils.isBlank(orderCode) || StringUtils.isBlank(phone) || StringUtils.isBlank(cardNo) || StringUtils.isBlank(interfaceInfoCode)) {
				writer.write("0001");
				return;
			}

			Order order = onlineTradeHessianService.queryOrderByCode(orderCode);
			if (order == null) {
				writer.write("0002");
			} else {
				TransCardBean transCardBean = customerInterface.findTransCardByAccNo(order.getReceiver(), cardNo, CardAttr.TRANS_CARD);
				if (transCardBean == null || !phone.equals(transCardBean.getPhone())) {
					writer.write("0003");
				} else {
					if (!Constants.SMS_CODE_TYPE.equals(smsCodeType)) {
						Random random = new Random();
						// 生成验证码6位随机数字
						String verifyCode = "" + random.nextInt(10) + random.nextInt(10) + random.nextInt(10) + random.nextInt(10) + random.nextInt(10) + random.nextInt(10);
						// 放到session中
						request.getSession().setAttribute("verifyCode", verifyCode);
						// 发送短信
						SmsUtils.sendCustom(String.format(Constants.SMS_BIND_CARD_VERIFYCODE, verifyCode), phone);
						CacheUtils.setEx(Constants.SMS_BIND_CARD_VERIFYCODE_KEY + orderCode, verifyCode, 900);
						writer.write("0000");
					} else {
						QuickPayOpenCardRequestBean requestBean = new QuickPayOpenCardRequestBean();
						requestBean.setInterfaceInfoCode(interfaceInfoCode);
						requestBean.setCardNo(cardNo);
						requestBean.setName(transCardBean.getAccountName());
						requestBean.setPhone(phone);
						requestBean.setCustomerNo(order.getReceiver());
						QuickPayOpenCardResponseBean bean = quickPayOpenCardHessianService.sendOpenCardVerifyCode(requestBean);
						if ("SUCCESS".equals(bean.getStatus())) {
							writer.write("0000");
						} else {
							writer.write("0004");
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error("订单号：{}发验证码处理异常 : ", orderCode, e);
			writer.write("0004");
		}
	}

	/**
	 *
	 * @Description 报存绑卡信息
	 * @param writer
	 * @param bindCardInfoBean
	 * @param orderCode
	 * @param verifyCode
	 * @date 2017年11月1日
	 */
	@RequestMapping("saveOpenCardInfo")
	public void saveOpenCardInfo(PrintWriter writer, BindCardInfoBean bindCardInfoBean, String orderCode, String verifyCode, String smsCodeType) {
		try {
			Order order = onlineTradeHessianService.queryOrderByCode(orderCode);
			if (order != null) {
				boolean flag = false;
				if (smsCodeType.equals(Constants.SMS_CODE_TYPE) || smsCodeType.equals(Constants.NO_SMS_CODE_BIND)) {
					TransCardBean transCardBean = customerInterface.findTransCardByAccNo(order.getReceiver(), bindCardInfoBean.getCardNo(), CardAttr.TRANS_CARD);
					QuickPayOpenCardRequestBean requestBean = new QuickPayOpenCardRequestBean();
					requestBean.setInterfaceInfoCode(bindCardInfoBean.getInterfaceInfoCode());
					requestBean.setCardNo(bindCardInfoBean.getCardNo());
					requestBean.setName(transCardBean.getAccountName());
					requestBean.setPhone(transCardBean.getPhone());
					requestBean.setCvn(bindCardInfoBean.getCvv());
					requestBean.setExpireDate(bindCardInfoBean.getValidityYear() + bindCardInfoBean.getValidityMonth());
					requestBean.setSmsCode(verifyCode);
					requestBean.setCustomerNo(order.getReceiver());
					QuickPayOpenCardResponseBean bean = quickPayOpenCardHessianService.openCardInfo(requestBean);
					if ("SUCCESS".equals(bean.getStatus())) {
						bindCardInfoBean.setToken(bean.getToken());
						flag = true;
					}
				} else {
					// String check =
					// CacheUtils.get(com.yl.online.gateway.Constants.SMS_BIND_CARD_VERIFYCODE_KEY
					// + orderCode, String.class);
					// flag = (StringUtils.notBlank(check) &&
					// check.endsWith(verifyCode));
					flag = true;
				}
				if (flag) {
					if (smsCodeType.equals(Constants.SMS_CODE_TYPE) || smsCodeType.equals(Constants.NO_SMS_CODE_BIND)) {
						bindCardInfoBean.setStatus(AuthPayBindCardInfoBeanStatus.SUCCESS);
					} else {
						bindCardInfoBean.setStatus(AuthPayBindCardInfoBeanStatus.FAILED);
					}
					bindCardInfoBean.setEffectiveDate(new Date());
					bindCardInfoBean.setExpiryDate(DateUtils.addDays(new Date(), 120));
					bindCardInfoHessianService.saveOrUpdate(bindCardInfoBean);
					Map<String, String> retParam = new HashMap<>();
					retParam.put("tradeOrderCode", orderCode);
					retParam.put("amount", Double.toString(order.getAmount()));
					retParam.put("interfaceCode", bindCardInfoBean.getInterfaceInfoCode());
					retParam.put("cardNo", bindCardInfoBean.getCardNo());
					retParam.put("bindCardStatus", "SUCCESS");
					CacheUtils.setEx("BINDCARD:" + bindCardInfoBean.getCardNo(), JsonUtils.toJsonString(retParam), 900);
					writer.write("0000");
				} else {
					// 验证码错误
					writer.write("0001");
				}
			}
			// response.sendRedirect("toPay.htm?key=" +
			// bindCardInfoBean.getCardNo());
		} catch (Exception e) {
			logger.error("订单号：{}报存绑卡信息处理异常 : ", orderCode, e);
			writer.write("0002");
		}
	}

	@RequestMapping("openCard")
	public String openCard(Model model, String orderCode, String bankCardNo, String interfaceCode, String smsCodeType) {

		Order order = onlineTradeHessianService.queryOrderByCode(orderCode);
		Customer customer = customerInterface.getCustomer(order.getReceiver());
		model.addAttribute("customerName", customer.getShortName());
		model.addAttribute("amount", order.getAmount());
		model.addAttribute("bankCardNo", bankCardNo);
		model.addAttribute("tradeOrderCode", orderCode);
		model.addAttribute("interfaceCode", interfaceCode);
		model.addAttribute("smsCodeType", smsCodeType);
		return "quickPay/openCard";
	}

	/**
	 * 九派人脸认证
	 * @return
	 */
    private String kingPassFaceAuth(Map<String, String> reqParams, Model model) {
        // 保存原来请求
        String outOrderId = reqParams.get(Constants.PARAM_NAME_OUT_ORDER_ID);
        String partner = reqParams.get(Constants.PARAM_NAME_PARTNER);
        String accountNo = reqParams.get(Constants.PARAM_NAME_CARD_NO);
        CacheUtils.setEx("check" + outOrderId, JsonUtils.toJsonString(reqParams), 600);

        TransCardBean transCardBean = customerInterface.findTransCardByAccNo(partner, accountNo, CardAttr.TRANS_CARD);
        // 组装刷脸报文
        Map<String, String> map = new TreeMap<>();
        map.put("charset", "02");
        map.put("version", "1.0");
        map.put("merchantId", "800001500020041");
        map.put("requestTime", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
        map.put("requestId", CodeBuilder.getOrderIdByUUId());
        map.put("service", "faceEventRegister");
        map.put("signType", "RSA256");
        map.put("idNo", DesUtils.desEnCode(transCardBean.getIdNumber()));
        map.put("realName", transCardBean.getAccountName());


        try {
            String url = "http://pay.feiyijj.com/gateway/quick/kingPassPay?outOrderId=" + outOrderId;
            map.put("returnUrl", Base64Utils.encode(url.getBytes("UTF-8")));
            String buf = KingPassUtils.mapToStr(map);
            RSASignUtil rsaSignUtil = new RSASignUtil("F:\\pay\\800001500020041.p12", "ZMoRpB");
            rsaSignUtil.setRootCertPath("F:\\pay\\rootca.cer");
            String sign = rsaSignUtil.sign(buf, "UTF-8");
            String cert = rsaSignUtil.getCertInfo();
            buf = buf + "&merchantCert=" + cert + "&merchantSign=" + sign;
            logger.info("九派刷脸支付：{}，报文：{}", outOrderId, buf);
            String respInfo = HttpUtils.sendReq2("https://jd.jiupaipay.com/paygateway/mpsGate/mpsTransaction", buf, "POST");
            logger.info("九派刷脸支付：{}，返回报文：{}", outOrderId, respInfo);
            Map<String, String> respMap = KingPassUtils.strToMap(respInfo);
            // 保存刷脸ID
			CacheUtils.setEx("eventId" + accountNo, respMap.get("eventId"), 600);
			// 组装报文，拉起刷脸
            Map<String, String> retMap = new HashMap<>();
            retMap.put("url", new String(Base64Utils.decode(respMap.get("h5Url"))));
            model.addAttribute("result", retMap);
        } catch (Exception e) {
            logger.info("九派刷脸支付：{}，请求报错：{}", outOrderId, e);
        }
        return "authPaySubmit";
    }

    @RequestMapping("kingPassPay")
    public String kingPassPay(Model model,HttpServletRequest request, String outOrderId) {
        try {
            String info = CacheUtils.get("check" + outOrderId, String.class);
            Map<String, String> map = JsonUtils.toObject(info, new TypeReference<Map<String, String>>() {});

            String partner = map.get(Constants.PARAM_NAME_PARTNER);
            Customer customer = customerInterface.getCustomer(partner);

            return payHandle(map, customer, model, request);
        } catch (Exception e) {
            logger.info("快捷支付 下单请求错误 : {}", e);
            if (e instanceof BusinessException || e instanceof BusinessRuntimeException) {
                model.addAttribute("responseCode", e.getMessage());
                model.addAttribute("responseMsg", AuthExceptionMessages.getMessage(e.getMessage()));
            } else {
                model.addAttribute("responseCode", AuthExceptionMessages.SEND_SMS_FAILED.getCode());
                model.addAttribute("responseMsg", AuthExceptionMessages.getMessage(AuthExceptionMessages.SEND_SMS_FAILED.getCode()));
            }
            return "auth/payFail";
        }
    }
}
