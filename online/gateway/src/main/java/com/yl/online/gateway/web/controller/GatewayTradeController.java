package com.yl.online.gateway.web.controller;

import com.lefu.commons.cache.util.CacheUtils;
import com.lefu.commons.utils.lang.DateFormatUtils;
import com.lefu.commons.utils.lang.DateUtils;
import com.lefu.commons.utils.lang.JsonUtils;
import com.lefu.commons.utils.lang.StringUtils;
import com.lefu.commons.utils.security.DigestUtils;
import com.lefu.commons.utils.web.InetUtils;
import com.yl.boss.api.bean.Customer;
import com.yl.boss.api.bean.CustomerKey;
import com.yl.boss.api.enums.CustomerBeanType;
import com.yl.boss.api.enums.CustomerStatus;
import com.yl.boss.api.enums.KeyType;
import com.yl.boss.api.interfaces.CustomerInterface;
import com.yl.online.gateway.Constants;
import com.yl.online.gateway.ExceptionMessages;
import com.yl.online.gateway.bean.MobileInfoBean;
import com.yl.online.gateway.bean.OrderResultBean;
import com.yl.online.gateway.bean.PayParam;
import com.yl.online.gateway.context.RequestProxy;
import com.yl.online.gateway.enums.MerchantResponseCode;
import com.yl.online.gateway.enums.SignType;
import com.yl.online.gateway.exception.BusinessException;
import com.yl.online.gateway.exception.BusinessRuntimeException;
import com.yl.online.gateway.service.OemTradeHandler;
import com.yl.online.gateway.service.PartnerRequestService;
import com.yl.online.gateway.service.TradeHandler;
import com.yl.online.gateway.utils.Base64Utils;
import com.yl.online.gateway.utils.CodeBuilder;
import com.yl.online.gateway.utils.HttpUtils;
import com.yl.online.model.bean.PayResultBean;
import com.yl.online.model.bean.Payment;
import com.yl.online.model.bean.SalesTradeRequest;
import com.yl.online.model.enums.OrderStatus;
import com.yl.online.model.enums.PayType;
import com.yl.online.model.model.Order;
import com.yl.online.model.model.PartnerRequest;
import com.yl.online.trade.hessian.OnlinePartnerRouterHessianService;
import com.yl.online.trade.hessian.OnlineTradeHessianService;
import com.yl.online.trade.hessian.bean.InterfacePolicyProfileBean;
import com.yl.payinterface.core.bean.InterfaceInfoBean;
import com.yl.payinterface.core.enums.InterfaceType;
import com.yl.payinterface.core.hessian.PayInterfaceHessianService;
import net.sf.json.JSONObject;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 交易控制器
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月23日
 * @version V1.0.0
 */
@Controller
public class GatewayTradeController {
	private static final Logger logger = LoggerFactory.getLogger(GatewayTradeController.class);
	@Resource
	private OnlineTradeHessianService onlineTradeHessianService;
	@Resource
	private OnlinePartnerRouterHessianService onlinePartnerRouterHessianService;
	@Resource
	private PayInterfaceHessianService payInterfaceHessianService;
	@Resource
	private PartnerRequestService partnerRequestService;
	@Resource
	private Map<String, TradeHandler> tradeHandlers;
	@Resource
	private CustomerInterface customerInterface;
	@Resource
	private OemTradeHandler oemTradeHandler;

	private static Properties prop = new Properties();

	static {
		try {
			prop.load(GatewayTradeController.class.getClassLoader().getResourceAsStream("serverHost.properties"));
		} catch (IOException e) {
			logger.error("", e);
		}
	}


	@RequestMapping("toSubmit")
	public String toSubmit(Model model, HttpServletRequest request, String result, String params) {
		try {
			String payResultBeanInfo = URLDecoder.decode(CacheUtils.get(result, String.class), "UTF-8");
//			String paramInfo = URLDecoder.decode(CacheUtils.get(params, String.class), "UTF-8");
			PayResultBean payResultBean = JsonUtils.toObject(payResultBeanInfo, new TypeReference<PayResultBean>() {});
//			Map<String, String> param = JsonUtils.toObject(paramInfo, new TypeReference<Map<String, String>>() {});
//			model.addAttribute("auth_result", request.getAttribute("auth_result"));
//			model.addAttribute("params", param);
			model.addAttribute("result", payResultBean);
			return "submit";
		}catch (Exception e) {
			throw new BusinessRuntimeException(e);
		}
	}

	@RequestMapping("redirectPay")
    public void redirectPay(HttpServletRequest request, HttpServletResponse response, String url){
        String userAgent = request.getHeader("user-agent");
        CharSequence IOS = "iPhone";
        CharSequence Android = "Android";
        try {
            if (userAgent.contains(IOS) || userAgent.contains(Android)) {
                response.sendRedirect(url);
            } else {

            }
        } catch (Exception e) {
            logger.error("重定向异常!异常信息:{}", e);
        }
    }

	/**
	 * web支付
	 * @param model
	 * @param request 请求信息
	 * @return
	 */
	@RequestMapping("pay")
	public String trade(Model model, HttpServletRequest request, HttpServletResponse response) {
		try {
			// 设置编码
			RequestProxy requestProxy = new RequestProxy(request);
			request = requestProxy;

			// 初始化合作方请求
			PartnerRequest partnerRequest = this.initialTradeRequest(request);

			// 判断网关支付类型
			SalesTradeRequest tradeRequest = null;
			// 支付订单号
			String tradeOrderCode = "";
			// 校验参数
			signValidate(partnerRequest, request);
			
			// 兼容模式 --！
			/*if(!partnerRequest.getAccMode().equals("GATEWAY")){
				if (StringUtils.notBlank(partnerRequest.getPayType())) {
					if (PayType.valueOf(partnerRequest.getPayType()) == PayType.AUTHPAY || PayType.valueOf(partnerRequest.getPayType()) == PayType.B2C) {
						if (org.apache.commons.lang3.StringUtils.isBlank(partnerRequest.getBankCardNo())) {
							CustomerKey customerKey = customerInterface.getCustomerKey(partnerRequest.getPartner(), KeyType.MD5);
							model.addAttribute("partnerRequest", partnerRequest);
							model.addAttribute("key", customerKey.getKey());
							model.addAttribute("retryFalg", request.getParameter("retryFalg"));
							return "supplement/addPhone";
						}
					}
				}
			}*/
			
			
			logger.info("商户订单号：{}验签结束，创建订单。", partnerRequest.getOutOrderId());
			TradeHandler tradeHandler = tradeHandlers.get(partnerRequest.getApiCode());
			// 创建支付订单
			tradeOrderCode = tradeHandler.execute(partnerRequest);
			logger.info("商户订单号：{}，创建订单成功。", partnerRequest.getOutOrderId());
			partnerRequestService.save(partnerRequest);
			tradeRequest = JsonUtils.toObject(partnerRequest.getInfo(), new TypeReference<SalesTradeRequest>() {});

			// 查询商户名称
			Customer customer = customerInterface.getCustomer(partnerRequest.getPartner());
			String partnerName = customer.getShortName();
			
			// 查询路由返回可用银行列表
			OrderResultBean resultBean = new OrderResultBean();
			resultBean.setApiCode(partnerRequest.getApiCode());
			resultBean.setOutOrderId(partnerRequest.getOutOrderId());
			resultBean.setTradeOrderCode(tradeOrderCode);
			resultBean.setAmount(new BigDecimal(partnerRequest.getAmount()));
			resultBean.setProductName(tradeRequest.getProductName());
			resultBean.setProductDesc(tradeRequest.getProductDesc());
			resultBean.setPartnerName(partnerName);
			resultBean.setBuyDate(DateUtils.parseDateStrictly(tradeRequest.getSubmitTime(), "yyyyMMddHHmmss"));

			String interfaceCode = null;
			if(StringUtils.notBlank(tradeRequest.getInterfaceCode())){
				interfaceCode = tradeRequest.getInterfaceCode();
			}else{
				// 获取有效资金通道信息
				Map<String, List<InterfacePolicyProfileBean>> interfacePolicyProfileBeans = onlinePartnerRouterHessianService.queryPartnerRouterBy("CUSTOMER",
						partnerRequest.getPartner());
				// 网关显示银行列表
				Map<String, List<String>> interfaceInfos = gatewayPageDestructor(tradeRequest.getPaymentType(), interfacePolicyProfileBeans);
				if (StringUtils.notBlank(partnerRequest.getPayType())) {
					interfaceCode = interfaceInfos.get(partnerRequest.getPayType()).get(0);
				}
				resultBean.setInterfaceCodes(interfaceInfos);
			}
			
			PayParam payParam = new PayParam();
			if(!partnerRequest.getAccMode().equals("GATEWAY") || (StringUtils.notBlank(partnerRequest.getPayType()) && PayType.valueOf(partnerRequest.getPayType()) == PayType.ALIPAYJSAPI)){
				
				PayResultBean bean = null;
				try {
					payParam.setTradeOrderCode(tradeOrderCode);
					payParam.setInterfaceCode(interfaceCode);
					payParam.setClientIP(InetUtils.getRealIpAddr(request));

					if(PayType.valueOf(partnerRequest.getPayType()) == PayType.AUTHPAY){
						MobileInfoBean mobileInfoBean = new MobileInfoBean();
						mobileInfoBean.setBankCardNo(partnerRequest.getBankCardNo());
						mobileInfoBean.setCertNo(partnerRequest.getCertNo());
						mobileInfoBean.setCvv(partnerRequest.getCvv());
						mobileInfoBean.setValidity(partnerRequest.getValDate());
						mobileInfoBean.setPayerMobNo(partnerRequest.getPayerMobNo());
						mobileInfoBean.setPayerName(partnerRequest.getPayerName());
						payParam.setMobileInfoBean(mobileInfoBean);
						logger.info("认证支付请求交易数据:{}", JsonUtils.toJsonString(bean));
					}
					if(PayType.valueOf(partnerRequest.getPayType()) == PayType.B2C){
						if (StringUtils.isNotBlank(partnerRequest.getBankCardNo())) {
							MobileInfoBean mobileInfoBean = new MobileInfoBean();
							mobileInfoBean.setBankCardNo(partnerRequest.getBankCardNo());
							payParam.setMobileInfoBean(mobileInfoBean);
						}
						payParam.setClientIP(partnerRequest.getIp());
						payParam.setPageNotifyUrl(partnerRequest.getPageNotifyUrl());
					}
					
					bean = tradeHandler.pay(payParam);
					if(PayType.valueOf(partnerRequest.getPayType()) == PayType.B2C){
						model.addAttribute("params", bean.getParams());
						model.addAttribute("result", bean);
					}
					
					logger.info("请求交易返回数据:{}", JsonUtils.toJsonString(bean));
					if(PayType.valueOf(partnerRequest.getPayType()) == PayType.AUTHPAY){
						model.addAttribute("auth_result", bean.getParams());
						model.addAttribute("params", bean.getParams());
						model.addAttribute("result", bean);
					}
					if (bean.getUrl() != null && bean.getUrl().contains("redirect")) {
						// 换域名跳转情况用
						String key = CodeBuilder.getOrderIdByUUId();
						// 定义缓存Key
						String resultKey = "result" + key;
						String paramsKey = "params" + key;
						try {
							// urlEncode 防止中文乱码
							CacheUtils.setEx(resultKey, URLEncoder.encode(JsonUtils.toJsonString(bean), "UTF-8"), 60 * 60);
							CacheUtils.setEx(paramsKey, URLEncoder.encode(JsonUtils.toJsonString(bean.getParams()), "UTF-8"), 60 * 60);
							String newUrl = bean.getUrl() + "?result=" + resultKey + "&params=" + paramsKey;
							bean.setUrl(newUrl);
						} catch (Exception e) {
							logger.info("缓存数据报错：{}", e);
						}
					}
					if(PayType.valueOf(partnerRequest.getPayType()) == PayType.ALIPAYJSAPI || PayType.valueOf(partnerRequest.getPayType()) == PayType.ALIPAY 
							|| PayType.valueOf(partnerRequest.getPayType()) == PayType.WXNATIVE){
						
						if (customer.getCustomerType() == CustomerBeanType.OEM && PayType.valueOf(partnerRequest.getPayType()) == PayType.ALIPAYJSAPI) {
							partnerName = partnerRequest.getProduct();
						}
						
						model.addAttribute("result", bean);
						model.addAttribute("customerName", partnerName);
						model.addAttribute("customerNo", partnerRequest.getPartner());
						model.addAttribute("amount", partnerRequest.getAmount());
						model.addAttribute("outOrderId",partnerRequest.getOutOrderId());
						model.addAttribute("orderCode",tradeOrderCode);
						if (payParam.getInterfaceCode().contains("WX")) {
							model.addAttribute("typeFlag", "WX");
						} else {
							model.addAttribute("typeFlag", "ALIPAY");
						}
					}
					return bean.getUrl();
				} catch (Exception e) {
					logger.error("", e);
					this.transferredErrorMsg(request, e);
					return "error/error";
				}
			}
			
			if(StringUtils.notBlank(partnerRequest.getPayType()) && PayType.valueOf(partnerRequest.getPayType()) == PayType.WXJSAPI){
//				payParam.setOpenid(partnerRequest.getOpenId());
				
				//获取openid
				//XXX 获取接口编号
				InterfaceInfoBean infoBean = payInterfaceHessianService.interfaceInfoQueryByCode("CIB330000-WX_JSAPI");
				Properties properties = JsonUtils.toObject(infoBean.getTradeConfigs(), new TypeReference<Properties>() {});
				if (customer.getCustomerType() == CustomerBeanType.OEM && PayType.valueOf(partnerRequest.getPayType()) == PayType.ALIPAYJSAPI) {
					partnerName = partnerRequest.getProduct();
				}
				Map<String, String> wxParam = new HashMap<>();
				wxParam.put("apiCode", partnerRequest.getApiCode());
				wxParam.put("tradeOrderCode", tradeOrderCode);
				wxParam.put("interfaceCode", interfaceCode);
				wxParam.put("customerName", partnerName);
				wxParam.put("amount", partnerRequest.getAmount());
				wxParam.put("secret", properties.getProperty("secret"));
				wxParam.put("appid", properties.getProperty("appid"));
				
				@SuppressWarnings("deprecation")
				String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid="+
						properties.getProperty("appid")+ "&redirect_uri="+ 
						URLEncoder.encode((properties.getProperty("redirect_uri"))+ "?wxParams="+URLEncoder.encode(Base64Utils.encodeFilter(JsonUtils.toJsonString(wxParam).getBytes("UTF-8")),"UTF-8"))
						+ "&response_type=code&scope=snsapi_base#wechat_redirect";
				model.addAttribute("result", url);
				return "wxJsapiSubmit";
			}
			
			request.setAttribute("result", resultBean);
			return "gateway";
		} catch (Exception e) {
			logger.error("", e);
			this.transferredErrorMsg(request, e);
			return "error/error";
		}
	}
	
	/**
	 * 
	 * @Description 网银支付
	 * @param payParam
	 * @param model
	 * @param request
	 * @return
	 * @date 2017年1月15日
	 */
	@RequestMapping("toPay")
	public String toPay(PayParam payParam, Model model, HttpServletRequest request){
		PayResultBean bean = null;
		try {
			payParam.setClientIP(InetUtils.getRealIpAddr(request));
			TradeHandler tradeHandler = tradeHandlers.get(payParam.getApiCode());
			bean = tradeHandler.pay(payParam);
		} catch (Exception e) {
			if (e.getMessage().contains("riskResponse")) {
				String[] respOriArr = e.getMessage().split("=");
				String[] respArr = respOriArr[1].split("-");
				request.setAttribute("errorMsg", respArr[1]);
				request.setAttribute("orderId", payParam.getTradeOrderCode());
				request.setAttribute("amount", payParam.getAmount());
				return "gatewayerror";
			}
			logger.error("订单号为【" + payParam.getTradeOrderCode() + "】的订单,支付异常信息为：{}", e);
			this.transferredErrorMsg(request, e);
			return "error/error";
		}
		logger.info("交易返回数据:{}", JsonUtils.toJsonString(bean));
		model.addAttribute("result", bean);
		model.addAttribute("params", bean.getParams());
		if (bean.getUrl() != null && bean.getUrl().contains("redirect")) {
			// 换域名跳转情况用
			String key = CodeBuilder.getOrderIdByUUId();
			String resultKey = "result" + key;
			String paramsKey = "params" + key;
			try {

				CacheUtils.setEx(resultKey, URLEncoder.encode(JsonUtils.toJsonString(bean), "UTF-8"), 60 * 60);
				CacheUtils.setEx(paramsKey, URLEncoder.encode(JsonUtils.toJsonString(bean.getParams()), "UTF-8"), 60 * 60);
			} catch (Exception e) {
				logger.info("", e);
			}
			String newUrl = bean.getUrl() + "?result=" + resultKey + "&params=" + paramsKey;
			bean.setUrl(newUrl);
		}
		if (payParam.getInterfaceCode() != null && (payParam.getInterfaceCode().contains("WX") || 
				payParam.getInterfaceCode().contains("ALIPAY")|| 
				payParam.getInterfaceCode().contains("AUTHPAY"))) {
			Order order = onlineTradeHessianService.queryOrderByCode(payParam.getTradeOrderCode());
			Customer customer = customerInterface.getCustomer(order.getReceiver());
			String partnerName = customer.getShortName();
			model.addAttribute("customerName", partnerName);
			model.addAttribute("amount", order.getAmount());
			model.addAttribute("outOrderId",order.getRequestCode());
			model.addAttribute("customerNo",order.getReceiver());
			model.addAttribute("orderCode",order.getCode());
			model.addAttribute("auth_result", bean.getParams());
			if (payParam.getInterfaceCode().contains("WX")) {
				model.addAttribute("typeFlag", "WX");
			} else {
				model.addAttribute("typeFlag", "ALIPAY");
			}
			return bean.getUrl();
		}
		if(StringUtils.notBlank(bean.getUrl())){
			return bean.getUrl();
		}
		return "submit";
	}
	
	/**
	 * 微信公众号支付
	 * @param model
	 * @param request 请求信息
	 * @return
	 */
	@RequestMapping("jsapiPay")
	public String jsapiPay(Model model, HttpServletRequest request) {
		PayResultBean bean = null;
		try {
			String wxParams = request.getParameter("wxParams");
			String code = request.getParameter("code");
			Map<String, String> params = JsonUtils.toObject(new String(Base64Utils.decode(wxParams), "UTF-8"), new TypeReference<Map<String,String>>(){});
			logger.info(params.toString());
			String requestUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="+ params.get("appid")
					+"&secret=" + params.get("secret") + "&code=" + code + "&grant_type=authorization_code";
		        
	        JSONObject jsonObject = HttpUtils.sendReqJson(requestUrl,"" , "POST");
			System.out.println(jsonObject.toString());
	        
			TradeHandler tradeHandler = tradeHandlers.get(params.get("apiCode"));
			PayParam payParam = new PayParam();
			payParam.setTradeOrderCode(params.get("tradeOrderCode"));
			payParam.setInterfaceCode(params.get("interfaceCode"));
			payParam.setClientIP(InetUtils.getRealIpAddr(request));
			payParam.setOpenid(jsonObject.getString("openid"));
			bean = tradeHandler.pay(payParam);
			model.addAttribute("result", bean);
			model.addAttribute("customerName", params.get("customerName"));
			model.addAttribute("amount", params.get("amount"));
			return bean.getUrl();
		} catch (Exception e) {
			logger.error("", e);
			this.transferredErrorMsg(request, e);
			return "error/error";
		}
	}
	
	/**
	 * 记录合作方请求信息
	 * @param request 请求实体
	 * @return PartnerRequest
	 */
	@SuppressWarnings("deprecation")
	private PartnerRequest initialTradeRequest(HttpServletRequest request) {
		Map<String, String[]> originalRequest = request.getParameterMap();
		originalRequest = JsonUtils.toObject(URLDecoder.decode(JsonUtils.toJsonString(originalRequest)), new TypeReference<Map<String, String[]>>() {});
		logger.info("原始请求信息："+ JsonUtils.toJsonString(originalRequest));
		PartnerRequest partnerRequest = new PartnerRequest();
		// 记录原始请求信息
		partnerRequest.setOriginalRequest(JsonUtils.toJsonString(originalRequest));
		try {
			// 记录当前的服务器信息
			InetAddress localHost = InetAddress.getLocalHost();
			String host = localHost.getHostName();
			String ip = localHost.getHostAddress();
			partnerRequest.setIp(ip);
			partnerRequest.setReferer(host);
		} catch (UnknownHostException e) {
			logger.error("", e);
		}
		return partnerRequest;
	}

	/**
	 * 错误码转义
	 * @param request 请求实体
	 * @param e 异常实体
	 */
	private void transferredErrorMsg(HttpServletRequest request, Exception e) {
		String errorMsg = "";
		MerchantResponseCode merchantResponseCode = MerchantResponseCode.UNKOWN_ERROR;
		if (e.getMessage() != null) {
			if (e.getClass().isAssignableFrom(RuntimeException.class)) {
				errorMsg = e.getMessage().substring(e.getMessage().lastIndexOf(":") + 2, e.getMessage().length());
			} else if (e.getClass().isAssignableFrom(BusinessException.class)) {
				errorMsg = e.getMessage();
			} 
			merchantResponseCode = MerchantResponseCode.getMerchantCode(errorMsg);
			request.setAttribute("merchantCode", merchantResponseCode.getMerchantCode());
			request.setAttribute("responseMessage", merchantResponseCode.getResponseMessage());
		}else{
			request.setAttribute("merchantCode", merchantResponseCode.getMerchantCode());
			request.setAttribute("responseMessage", merchantResponseCode.getResponseMessage());
		}
	}

	/**
	 * 构建网关页面展示银行列表
	 * @param paymentType 支付类型
	 * @param interfacePolicyProfileBeans 商户路由信息
	 * @return Map<String, List<String>>
	 */
	private Map<String, List<String>> gatewayPageDestructor(String paymentType, Map<String, List<InterfacePolicyProfileBean>> interfacePolicyProfileBeans) {
		Map<String, List<String>> interfaceInfos = new HashMap<String, List<String>>();
		// 银行编码
		String supportProvider = "";
		// 支付工具分类银行列表
		List<String> bankCodes = null;
		for (Map.Entry<String, List<InterfacePolicyProfileBean>> entry : interfacePolicyProfileBeans.entrySet()) {
			// B2B不支持借贷分离
			/*if (InterfaceType.B2B.name().equals(entry.getKey()) && (paymentType.equals(InterfaceType.B2B.name()) || paymentType.equals("ALL"))) {
				for (InterfacePolicyProfileBean interfacePolicyProfileBean : entry.getValue()) {
					supportProvider = StringUtils.concatToSB(InterfaceType.B2B.name(), "_", interfacePolicyProfileBean.getInterfaceProviderCode()).toString();
					if (interfaceInfos.containsKey(entry.getKey())) {
						if (interfaceInfos.get(entry.getKey()).contains(supportProvider)) continue;
						interfaceInfos.get(entry.getKey()).add(supportProvider);
					} else {
						bankCodes = new ArrayList<String>();
						bankCodes.add(supportProvider);
						interfaceInfos.put(InterfaceType.B2B.name(), bankCodes);
					}
				}
			}*/
			// B2C、QUICKPAY支持借贷分离
			if (InterfaceType.B2C.name().equals(entry.getKey()) && (paymentType.contains(InterfaceType.B2C.name()) || paymentType.equals("ALL"))) {
				for (InterfacePolicyProfileBean interfacePolicyProfileBean : entry.getValue()) {
					if (paymentType.contains("-")) {
						String cardType = paymentType.split("-")[1];
						if (!interfacePolicyProfileBean.getCardType().contains(cardType)) continue;
					}
					supportProvider = StringUtils.concatToSB(entry.getKey(), "_", interfacePolicyProfileBean.getInterfaceProviderCode(), "-",
							interfacePolicyProfileBean.getCardType()).toString();
					if (interfaceInfos.containsKey(interfacePolicyProfileBean.getCardType())) {
						if (interfaceInfos.get(interfacePolicyProfileBean.getCardType()).contains(supportProvider)) continue;
						interfaceInfos.get(interfacePolicyProfileBean.getCardType()).add(supportProvider);
					} else {
						bankCodes = new ArrayList<String>();
						bankCodes.add(supportProvider);
						interfaceInfos.put(interfacePolicyProfileBean.getCardType(), bankCodes);
					}
					supportProvider = "";
				}
			}
			// 认证支付
			if (InterfaceType.AUTHPAY.name().equals(entry.getKey()) && (paymentType.equals(InterfaceType.AUTHPAY.name()) || paymentType.equals("ALL"))) {
				for (InterfacePolicyProfileBean interfacePolicyProfileBean : entry.getValue()) {
					supportProvider = StringUtils.concatToSB(InterfaceType.AUTHPAY.name(), "_", interfacePolicyProfileBean.getInterfaceProviderCode()).toString();
					if (interfaceInfos.containsKey(entry.getKey())) {
						if (interfaceInfos.get(entry.getKey()).contains(supportProvider)) continue;
						interfaceInfos.get(entry.getKey()).add(supportProvider);
					} else {
						bankCodes = new ArrayList<String>();
						bankCodes.add(supportProvider);
						interfaceInfos.put(InterfaceType.AUTHPAY.name(), bankCodes);
					}
				}
			}
			
			// 微信二维码支付
			/*if (InterfaceType.WXNATIVE.name().equals(entry.getKey()) && (paymentType.equals(InterfaceType.WXNATIVE.name()) || paymentType.equals("ALL"))) {
				for (InterfacePolicyProfileBean interfacePolicyProfileBean : entry.getValue()) {
					supportProvider = StringUtils.concatToSB(InterfaceType.WXNATIVE.name(), "_", interfacePolicyProfileBean.getInterfaceProviderCode()).toString();
					if (interfaceInfos.containsKey(entry.getKey())) {
						if (interfaceInfos.get(entry.getKey()).contains(supportProvider)) continue;
						interfaceInfos.get(entry.getKey()).add(supportProvider);
					} else {
						bankCodes = new ArrayList<String>();
						bankCodes.add(supportProvider);
						interfaceInfos.put(InterfaceType.WXNATIVE.name(), bankCodes);
					}
				}
			} */
			
			// 微信公众号支付
			if (InterfaceType.WXJSAPI.name().equals(entry.getKey()) && (paymentType.equals(InterfaceType.WXJSAPI.name()) || paymentType.equals("ALL"))) {
				for (InterfacePolicyProfileBean interfacePolicyProfileBean : entry.getValue()) {
					supportProvider = StringUtils.concatToSB(InterfaceType.WXJSAPI.name(), "_", interfacePolicyProfileBean.getInterfaceProviderCode()).toString();
					if (interfaceInfos.containsKey(entry.getKey())) {
						if (interfaceInfos.get(entry.getKey()).contains(supportProvider)) continue;
						interfaceInfos.get(entry.getKey()).add(supportProvider);
					} else {
						bankCodes = new ArrayList<String>();
						bankCodes.add(supportProvider);
						interfaceInfos.put(InterfaceType.WXJSAPI.name(), bankCodes);
					}
				}
			}

			// 支付宝支付
			if (InterfaceType.ALIPAY.name().equals(entry.getKey()) && (paymentType.equals(InterfaceType.ALIPAY.name()) || paymentType.equals("ALL"))) {
				for (InterfacePolicyProfileBean interfacePolicyProfileBean : entry.getValue()) {
					supportProvider = StringUtils.concatToSB(InterfaceType.ALIPAY.name(), "_", interfacePolicyProfileBean.getInterfaceProviderCode()).toString();
					if (interfaceInfos.containsKey(entry.getKey())) {
						if (interfaceInfos.get(entry.getKey()).contains(supportProvider)) continue;
						interfaceInfos.get(entry.getKey()).add(supportProvider);
					} else {
						bankCodes = new ArrayList<String>();
						bankCodes.add(supportProvider);
						interfaceInfos.put(InterfaceType.ALIPAY.name(), bankCodes);
					}
				}
			}
			// 
			if (InterfaceType.ALIPAYJSAPI.name().equals(entry.getKey()) && (paymentType.equals(InterfaceType.ALIPAYJSAPI.name()) || paymentType.equals("ALL"))) {
				for (InterfacePolicyProfileBean interfacePolicyProfileBean : entry.getValue()) {
					supportProvider = StringUtils.concatToSB(InterfaceType.ALIPAYJSAPI.name(), "_", interfacePolicyProfileBean.getInterfaceProviderCode()).toString();
					if (interfaceInfos.containsKey(entry.getKey())) {
						if (interfaceInfos.get(entry.getKey()).contains(supportProvider)) continue;
						interfaceInfos.get(entry.getKey()).add(supportProvider);
					} else {
						bankCodes = new ArrayList<String>();
						bankCodes.add(supportProvider);
						interfaceInfos.put(InterfaceType.ALIPAYJSAPI.name(), bankCodes);
					}
				}
			}

            if (InterfaceType.JDH5.name().equals(entry.getKey()) && (paymentType.equals(InterfaceType.JDH5.name()) || paymentType.equals("ALL"))) {
                for (InterfacePolicyProfileBean interfacePolicyProfileBean : entry.getValue()) {
                    supportProvider = StringUtils.concatToSB(InterfaceType.JDH5.name(), "_", interfacePolicyProfileBean.getInterfaceProviderCode()).toString();
                    if (interfaceInfos.containsKey(entry.getKey())) {
                        if (interfaceInfos.get(entry.getKey()).contains(supportProvider)) continue;
                        interfaceInfos.get(entry.getKey()).add(supportProvider);
                    } else {
                        bankCodes = new ArrayList<String>();
                        bankCodes.add(supportProvider);
                        interfaceInfos.put(InterfaceType.JDH5.name(), bankCodes);
                    }
                }
            }

            if (InterfaceType.QQNATIVE.name().equals(entry.getKey()) && (paymentType.equals(InterfaceType.QQNATIVE.name()) || paymentType.equals("ALL"))) {
                for (InterfacePolicyProfileBean interfacePolicyProfileBean : entry.getValue()) {
                    supportProvider = StringUtils.concatToSB(InterfaceType.QQNATIVE.name(), "_", interfacePolicyProfileBean.getInterfaceProviderCode()).toString();
                    if (interfaceInfos.containsKey(entry.getKey())) {
                        if (interfaceInfos.get(entry.getKey()).contains(supportProvider)) continue;
                        interfaceInfos.get(entry.getKey()).add(supportProvider);
                    } else {
                        bankCodes = new ArrayList<String>();
                        bankCodes.add(supportProvider);
                        interfaceInfos.put(InterfaceType.QQNATIVE.name(), bankCodes);
                    }
                }
            }

            if (InterfaceType.QQH5.name().equals(entry.getKey()) && (paymentType.equals(InterfaceType.QQH5.name()) || paymentType.equals("ALL"))) {
                for (InterfacePolicyProfileBean interfacePolicyProfileBean : entry.getValue()) {
                    supportProvider = StringUtils.concatToSB(InterfaceType.QQH5.name(), "_", interfacePolicyProfileBean.getInterfaceProviderCode()).toString();
                    if (interfaceInfos.containsKey(entry.getKey())) {
                        if (interfaceInfos.get(entry.getKey()).contains(supportProvider)) continue;
                        interfaceInfos.get(entry.getKey()).add(supportProvider);
                    } else {
                        bankCodes = new ArrayList<String>();
                        bankCodes.add(supportProvider);
                        interfaceInfos.put(InterfaceType.QQH5.name(), bankCodes);
                    }
                }
            }

            if (InterfaceType.UNIONPAYNATIVE.name().equals(entry.getKey()) && (paymentType.equals(InterfaceType.UNIONPAYNATIVE.name()) || paymentType.equals("ALL"))) {
                for (InterfacePolicyProfileBean interfacePolicyProfileBean : entry.getValue()) {
                    supportProvider = StringUtils.concatToSB(InterfaceType.UNIONPAYNATIVE.name(), "_", interfacePolicyProfileBean.getInterfaceProviderCode()).toString();
                    if (interfaceInfos.containsKey(entry.getKey())) {
                        if (interfaceInfos.get(entry.getKey()).contains(supportProvider)) continue;
                        interfaceInfos.get(entry.getKey()).add(supportProvider);
                    } else {
                        bankCodes = new ArrayList<String>();
                        bankCodes.add(supportProvider);
                        interfaceInfos.put(InterfaceType.UNIONPAYNATIVE.name(), bankCodes);
                    }
                }
            }
		}

		return interfaceInfos;
	}

	/**
	 * 合作方签名检验
	 * @param partnerRequest 合作方请求信息
	 * @param request 原请求信息
	 * @throws BusinessException
	 */
	private void signValidate(PartnerRequest partnerRequest, HttpServletRequest request) throws BusinessException {
		Map<String, String[]> OriginalParams = JsonUtils.toObject(partnerRequest.getOriginalRequest(), new TypeReference<Map<String, String[]>>() {});
		Map<String, String> params = new HashMap<>();
		for (String key : OriginalParams.keySet()) {
			params.put(key, OriginalParams.get(key)[0]);
		}
		logger.info("原始请求信息："+ params);
		// 检查接口名称
		String apiCode = params.get(Constants.PARAM_NAME_API_CODE);
		if (StringUtils.isBlank(apiCode)) {
			throw new BusinessException(StringUtils.concatToSB(ExceptionMessages.PARAM_NOT_EXISTS, "-", Constants.PARAM_NAME_API_CODE).toString());
		}
		partnerRequest.setApiCode(apiCode);
		// 检查输入字符集
		String inputCharset = params.get(Constants.PARAM_NAME_INPUT_CHARSET);
		if (StringUtils.isBlank(inputCharset)) {
			throw new BusinessException(StringUtils.concatToSB(ExceptionMessages.PARAM_NOT_EXISTS, "-", Constants.PARAM_NAME_INPUT_CHARSET).toString());
		}
		partnerRequest.setInputCharset(inputCharset);
		// 检查合作方
		final String partnerCode = params.get(Constants.PARAM_NAME_PARTNER);
		if (StringUtils.isBlank(partnerCode)) {
			throw new BusinessException(StringUtils.concatToSB(ExceptionMessages.PARAM_NOT_EXISTS, "-", Constants.PARAM_NAME_PARTNER).toString());
		}
		partnerRequest.setPartner(partnerCode);
		// 检查签名方式
		String signType = params.get(Constants.PARAM_NAME_SIGN_TYPE);
		if (StringUtils.isBlank(signType)) {
			throw new BusinessException(StringUtils.concatToSB(ExceptionMessages.PARAM_NOT_EXISTS, "-", Constants.PARAM_NAME_SIGN_TYPE).toString());
		}
		boolean supportSignType = false;
		for (SignType _signType : SignType.values()) {
			if (_signType.name().equals(signType)) {
				supportSignType = true;
				break;
			}
		}
		if (!supportSignType) {
			throw new BusinessException(StringUtils.concatToSB(ExceptionMessages.PARAM_ERROR, "-", Constants.PARAM_NAME_SIGN_TYPE).toString());
		}
		partnerRequest.setSignType(signType);

		// 检查签名
		String sign = params.get(Constants.PARAM_NAME_SIGN);
		if (StringUtils.isBlank(sign)) {
			throw new BusinessException(StringUtils.concatToSB(ExceptionMessages.PARAM_NOT_EXISTS, "-", Constants.PARAM_NAME_SIGN).toString());
		}
		partnerRequest.setSign(sign);

		// 检查商户订单号
		String outOrderId = params.get(Constants.PARAM_NAME_OUT_ORDER_ID);
		if (StringUtils.isBlank(outOrderId)) {
			throw new BusinessException(StringUtils.concatToSB(ExceptionMessages.PARAM_NOT_EXISTS, "-", Constants.PARAM_NAME_OUT_ORDER_ID).toString());
		}
		partnerRequest.setOutOrderId(outOrderId);

		// 回传参数
		String returnParam = params.get(Constants.PARAM_NAME_RETURNPARAM);
		if (StringUtils.notBlank(returnParam)) {
			partnerRequest.setReturnParam(returnParam);
		}

		// 查询卖方信息
		Customer customer = customerInterface.getCustomer(partnerRequest.getPartner());

		if (customer == null) throw new BusinessException(ExceptionMessages.RECEIVER_NOT_EXISTS);

		if (customer.getStatus() == null || !customer.getStatus().equals(CustomerStatus.TRUE)) {
			throw new BusinessException(ExceptionMessages.RECEIVER_STATUS_ERROR);
		}
		
		CustomerKey customerKey = customerInterface.getCustomerKey(partnerRequest.getPartner(), KeyType.MD5);
		
		if(customerKey == null) throw new BusinessException(ExceptionMessages.RECEIVER_KEY_NOT_EXISTS);

		// 开通相关业务
//		if (!partner.getInterfaceType().contains("ONLINE")) throw new BusinessException(ExceptionMessages.RECEIVER_NOT_OPEN_ONLINE_SERVICE);
		// 防钓鱼
//		if (partner.getAntiPhishingFlag()) {
//			logger.info("商户浏览器Referer:{}  ------ IP：{}", InetUtils.getReferer(request), InetUtils.getRealIpAddr(request));
//			// 防钓鱼方式
//			if (partner.getAntiPhishingType().equals(AntiPhishingType.IP) && !partner.getIp().contains(InetUtils.getRealIpAddr(request))) throw new BusinessRuntimeException(
//					ExceptionMessages.TRADE_EXIST_FISHING_RISK);
//			else if (!InetUtils.getReferer(request).startsWith(partner.getReferer())) throw new BusinessException(ExceptionMessages.TRADE_EXIST_FISHING_RISK);
//		}

		// 查询密钥信息
		final String cipherKey = customerKey.getKey();

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
				signSource.append("&");
			}
		}

		// 计算签名
		String calSign = null;
		if (SignType.MD5.name().equals(signType)) {
			try {
				logger.info("current inputCharset is {}", inputCharset);
				calSign = DigestUtils.md5DigestAsHex((signSource.substring(0, signSource.length()-1) + cipherKey).getBytes(inputCharset));
				logger.info("商户订单号：{}，签名原文串【{}】", partnerRequest.getOutOrderId(), signSource.substring(0, signSource.length()-1) + cipherKey);
			} catch (UnsupportedEncodingException e) {
				throw new BusinessException(StringUtils.concatToSB(ExceptionMessages.PARAM_ERROR, "-", Constants.PARAM_NAME_INPUT_CHARSET).toString());
			}
		}

		if (!sign.equals(calSign)) {
			logger.info("sign check error\r\nseller={}\r\nsign={}\r\ncalSign={}", partnerCode, sign, calSign);
			throw new BusinessException(StringUtils.concatToSB(ExceptionMessages.SIGN_ERROR).toString());
		}
		// 金额
		String amount = params.get(Constants.PARAM_NAME_AMOUNT);
		if(StringUtils.isBlank(amount)){
			throw new BusinessException(StringUtils.concatToSB(ExceptionMessages.AMOUNT_ERROR).toString());
		}
		
		try{
			Double.valueOf(amount);
		}catch(Exception e){
			throw new BusinessException(StringUtils.concatToSB(ExceptionMessages.AMOUNT_ERROR).toString());
		}
		
		if(amount.indexOf(".")>0){
			if(amount.split("\\.")[1].length() > 2){
				throw new BusinessException(StringUtils.concatToSB(ExceptionMessages.AMOUNT_ERROR).toString());
			}
		}

		partnerRequest.setAmount(params.get(Constants.PARAM_NAME_AMOUNT));
		
		//接入方式
		if(params.get("accMode") == null || "".equals(params.get("accMode"))){
			partnerRequest.setAccMode("INTERFACE");
		}else{
			if("INTERFACE".equals(params.get("accMode"))||"GATEWAY".equals(params.get("accMode"))){
				partnerRequest.setAccMode(params.get("accMode"));
			}else{
				throw new BusinessException(StringUtils.concatToSB(ExceptionMessages.ACC_MODE_ERROR).toString());
			}
		}
		
		//接口类型
		try{
			if (StringUtils.notBlank(params.get("payType"))) {
				if("ALL".equals(params.get("payType"))){
					params.put("paymentType", "ALL");
				}else{
					PayType.valueOf(params.get("payType"));
					partnerRequest.setPayType(params.get("payType"));
				}
			}
		}catch(Exception e){
			throw new BusinessException(StringUtils.concatToSB(ExceptionMessages.PAY_TYPE_ERROR).toString());
		}
		
		//openid
		partnerRequest.setOpenId(params.get("openId"));
		
		//微信二维码方式
		if(StringUtils.isBlank(params.get("wxNativeType")) && PayType.WXNATIVE.toString().equals(params.get("payType"))){
			partnerRequest.setWxNativeType("URL");
		}else{
			partnerRequest.setWxNativeType(params.get("wxNativeType"));
		}
		
		// 银行卡号
		partnerRequest.setBankCardNo(params.get("bankCardNo"));
		// 回传参数
		partnerRequest.setReturnParam(params.get("returnParam"));
		// 商品名称
		partnerRequest.setProduct(params.get("productName"));
		// 通知地址
		if(StringUtils.notBlank(params.get("notifyUrl"))){
			partnerRequest.setNotifyUrl(params.get("notifyUrl"));
		}
		// 用户ip
		partnerRequest.setIp(params.get("clientIP"));
		// 页面通知参数
		partnerRequest.setPageNotifyUrl(params.get("redirectUrl"));
		
		// 扩展参数
		partnerRequest.setExtParam(params.get("extParam"));
		partnerRequest.setInterfaceCode(params.get("interfaceCode"));
		logger.info("商户请求信息:"+ partnerRequest);
		if (customer.getCustomerType() == CustomerBeanType.OEM) {
			// 调用相关OEM系统。
			String oemResult = oemTradeHandler.requestOme(partnerRequest);
			if (!"SUCCESS".equals(oemResult)) {
				throw new BusinessException(oemResult);
			}
		}
	}

	public static void main(String[] args) {
		Long max = Long.valueOf(new SimpleDateFormat("yyyyMMddhhmmss").format(DateUtils.addMinutes(new Date(), 10)));
        Long now = Long.valueOf(new SimpleDateFormat("yyyyMMddhhmmss").format(new Date()));
        System.out.println(max - now);
	}

	@RequestMapping("queryWalletOrder")
	public void queryWalletOrder(HttpServletResponse response, String partnerCode, String requestCode) {
		Order order = onlineTradeHessianService.queryOrderBy(partnerCode, requestCode);
		try {
		    Long orderTime = Long.valueOf(new SimpleDateFormat("yyyyMMddhhmmss").format(order.getOrderTime()));
            Long nowTime = Long.valueOf(new SimpleDateFormat("yyyyMMddhhmmss").format(new Date()));
            if ((nowTime - orderTime) > 1000) {
                Map<String, String > map = new HashMap<>();
                map.put("message", OrderStatus.FAILED.name());
                map.put("code", "99");
                response.getWriter().write(JsonUtils.toJsonString(map));
            }
			if (order.getStatus() == OrderStatus.SUCCESS) {
				Map<String, String > map = new HashMap<String, String>();
				map.put("message", OrderStatus.SUCCESS.name());
				map.put("code", "00");
				response.getWriter().write(JsonUtils.toJsonString(map));
			} else if (order.getStatus() == OrderStatus.FAILED) {
				Payment payment = onlineTradeHessianService.queryLastPayment(order.getCode());
				Map<String, String > map = new HashMap<String, String>();
				map.put("message", payment.getResponseInfo());
				map.put("code", payment.getResponseCode());
				response.getWriter().write(JsonUtils.toJsonString(map));
			}
		} catch (Exception e) {
			logger.info("查询微信支付宝订单状态异常：{}", e);
		}
	}
	
	@RequestMapping("pagePayComplete")
	public String pagePayComplete(Model model, String orderCode) {
		Order order = onlineTradeHessianService.queryOrderByCode(orderCode);
		Customer customer = customerInterface.getCustomer(order.getReceiver());
		try {
			if (order.getStatus() == OrderStatus.SUCCESS) {
				model.addAttribute("customerNo", order.getReceiver());
				model.addAttribute("customerName", customer.getShortName());
				model.addAttribute("amount", order.getAmount());
				model.addAttribute("outOrderId", order.getAmount());
				return "success";
			}
		} catch (Exception e) {
			logger.info("网银支付页面回调异常：{}", e);
			return "error/error";
		}
		return "error/error";
	}
}
