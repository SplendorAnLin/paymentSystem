package com.yl.online.gateway.web.controller;

import com.lefu.commons.cache.util.CacheUtils;
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
import com.yl.online.gateway.enums.ResponseCodeChange;
import com.yl.online.gateway.enums.SignType;
import com.yl.online.gateway.exception.BusinessException;
import com.yl.online.gateway.service.OemTradeHandler;
import com.yl.online.gateway.service.PartnerRequestService;
import com.yl.online.gateway.service.TradeHandler;
import com.yl.online.model.bean.PayResultBean;
import com.yl.online.model.bean.SalesTradeRequest;
import com.yl.online.model.enums.InterfaceType;
import com.yl.online.model.enums.PayType;
import com.yl.online.model.model.CustomerConfig;
import com.yl.online.model.model.Order;
import com.yl.online.model.model.PartnerRequest;
import com.yl.online.trade.hessian.OnlinePartnerRouterHessianService;
import com.yl.online.trade.hessian.OnlineTradeHessianService;
import com.yl.online.trade.hessian.bean.InterfacePolicyProfileBean;
import com.yl.payinterface.core.hessian.PayInterfaceHessianService;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.URLDecoder;
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
@RequestMapping("/interface")
public class InterfaceTradeController {
	private static final Logger logger = LoggerFactory.getLogger(InterfaceTradeController.class);
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

	
	/**
	 * 接口支付
	 * @param model
	 * @param request 请求信息
	 * @return
	 */
	@RequestMapping("pay")
	public void pay(Model model, HttpServletRequest request, HttpServletResponse response) {
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
			
			TradeHandler tradeHandler = tradeHandlers.get(partnerRequest.getApiCode());
			// 创建支付订单
			tradeOrderCode = tradeHandler.execute(partnerRequest);
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
			resultBean.setBuyDate(new Date());

			String interfaceCode = null;
			if (StringUtils.notBlank(tradeRequest.getInterfaceCode())) {
				interfaceCode = tradeRequest.getInterfaceCode();
			} else {
				// 获取有效资金通道信息
				Map<String, List<InterfacePolicyProfileBean>> interfacePolicyProfileBeans = onlinePartnerRouterHessianService.queryPartnerRouterBy("CUSTOMER",
						partnerRequest.getPartner());
				// 路由不存在
				if(interfacePolicyProfileBeans == null){
					throw new BusinessException(ExceptionMessages.CUSTOMER_PARTNERROUTER);
				}
				// 加载接口编码
				Map<String, List<String>> interfaceInfos = gatewayPageDestructor(interfacePolicyProfileBeans);
				interfaceCode = interfaceInfos.get(partnerRequest.getPayType()).get(0);
				resultBean.setInterfaceCodes(interfaceInfos);
			}
			
			PayParam payParam = new PayParam();
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
				} else if (PayType.valueOf(partnerRequest.getPayType()) == PayType.WXMICROPAY 
						|| PayType.valueOf(partnerRequest.getPayType()) == PayType.ALIPAYMICROPAY
                        || PayType.valueOf(partnerRequest.getPayType()) == PayType.ALIPAYJSAPI) {
					payParam.setOpenid(partnerRequest.getAuthCode());
				}
				bean = tradeHandler.pay(payParam);
				
				Map<String,String> params = new HashMap<>();
				params.put("orderCode", payParam.getTradeOrderCode());
				params.put("outOrderId", partnerRequest.getOutOrderId());
				params.put("apiCode", partnerRequest.getApiCode());
				params.put("inputCharset", partnerRequest.getInputCharset());
				params.put("partner", partnerRequest.getPartner());
				
				CustomerKey key = customerInterface.getCustomerKey(partnerRequest.getPartner(), KeyType.MD5);

				if ((PayType.valueOf(partnerRequest.getPayType()) == PayType.WXNATIVE ||
						PayType.valueOf(partnerRequest.getPayType()) == PayType.ALIPAY ||
						PayType.valueOf(partnerRequest.getPayType()) == PayType.QQNATIVE ||
						PayType.valueOf(partnerRequest.getPayType()) == PayType.UNIONPAYNATIVE ||
						PayType.valueOf(partnerRequest.getPayType()) == PayType.JDH5 ||
						PayType.valueOf(partnerRequest.getPayType()) == PayType.QQH5 ||
                        PayType.valueOf(partnerRequest.getPayType()) == PayType.ALIPAYJSAPI) &&
						"URL".equals(partnerRequest.getWxNativeType())) {
                    String qrCodeUrl=bean.getParams().get("code_url");
                    String qrCodeImgUrl=bean.getParams().get("code_img_url");
                    params.put("qrCodeUrl", qrCodeUrl!=null?qrCodeUrl:"");
                    params.put("qrCodeImgUrl", qrCodeImgUrl!=null?qrCodeImgUrl:"");
					params.put("responseCode", "0000");
					params.put("responseMsg", "SUCCESS");
					StringBuffer sb = new StringBuffer();
					sb.append("apiCode=");sb.append(params.get("apiCode"));
					sb.append("&inputCharset=");sb.append(params.get("inputCharset"));
					sb.append("&orderCode=");sb.append(params.get("orderCode"));
					sb.append("&outOrderId=");sb.append(params.get("outOrderId"));
					sb.append("&partner=");sb.append(partnerRequest.getPartner());
					sb.append("&qrCodeImgUrl=");sb.append(params.get("qrCodeImgUrl"));
					sb.append("&qrCodeUrl=");sb.append(params.get("qrCodeUrl"));
					sb.append("&responseCode=");sb.append(params.get("responseCode"));
					sb.append("&responseMsg=");sb.append(params.get("responseMsg"));
					
					String sign = DigestUtils.md5DigestAsHex((sb.toString()+key.getKey()).getBytes());
					params.put("sign", sign);

                    logger.info("响应信息:" + params);
					response.getWriter().write(JsonUtils.toJsonString(params));
					response.getWriter().close();
				} else if(PayType.valueOf(partnerRequest.getPayType()) == PayType.AUTHPAY){
					params.put("payUrl", bean.getParams().get("payUrl"));
					params.put("responseCode", "0000");
					params.put("responseMsg", "SUCCESS");
					
					StringBuffer sb = new StringBuffer();
					sb.append("apiCode=");sb.append(params.get("apiCode"));
					sb.append("&inputCharset=");sb.append(params.get("inputCharset"));
					sb.append("&orderCode=");sb.append(params.get("orderCode"));
					sb.append("&outOrderId=");sb.append(params.get("outOrderId"));
					sb.append("&partner=");sb.append(partnerRequest.getPartner());
					sb.append("&payUrl=");sb.append(params.get("payUrl"));
					sb.append("&responseCode=");sb.append(params.get("responseCode"));
					sb.append("&responseMsg=");sb.append(params.get("responseMsg"));
					
					String sign = DigestUtils.md5DigestAsHex((sb.toString()+key.getKey()).getBytes());
					params.put("sign", sign);

                    logger.info("响应信息:" + params);
					response.getWriter().write(JsonUtils.toJsonString(params));
					response.getWriter().close();
				} else if (PayType.valueOf(partnerRequest.getPayType()) == PayType.ALIPAYMICROPAY
						|| PayType.valueOf(partnerRequest.getPayType()) == PayType.WXMICROPAY) {
					ResponseCodeChange change = ResponseCodeChange.getOutsideRespCode(bean.getParams().get("responseCode"));
					params.put("responseCode", change.getOutsideRespCode());
					params.put("responseMsg", change.getOutsideRespMsg());
					
					StringBuffer sb = new StringBuffer();
					sb.append("apiCode=");sb.append(params.get("apiCode"));
					sb.append("&inputCharset=");sb.append(params.get("inputCharset"));
					sb.append("&orderCode=");sb.append(params.get("orderCode"));
					sb.append("&outOrderId=");sb.append(params.get("outOrderId"));
					sb.append("&partner=");sb.append(partnerRequest.getPartner());
					sb.append("&responseCode=");sb.append(params.get("responseCode"));
					sb.append("&responseMsg=");sb.append(params.get("responseMsg"));
					
					String sign = DigestUtils.md5DigestAsHex((sb.toString()+key.getKey()).getBytes());
					params.put("sign", sign);

                    logger.info("响应信息:" + params);
					response.getWriter().write(JsonUtils.toJsonString(params));
					response.getWriter().close();
				}
			} catch (Exception e) {
				logger.error("", e);
				exception(e, response);
			}
		} catch (Exception e) {
			logger.error("", e);
			exception(e, response);
		}
	}
	
	public void exception(Exception e, HttpServletResponse response){
		if(e instanceof BusinessException){
			String errorMsg = "";
			MerchantResponseCode merchantResponseCode = MerchantResponseCode.UNKOWN_ERROR;
			if (e.getMessage() != null) {
				if (e.getClass().isAssignableFrom(RuntimeException.class)) {
					errorMsg = e.getMessage().substring(e.getMessage().lastIndexOf(":") + 2, e.getMessage().length());
				} else if (e.getClass().isAssignableFrom(BusinessException.class)) {
					if(e.getMessage().indexOf("-")>-1){
						errorMsg = e.getMessage().split("-")[0];
					}else{
						errorMsg = e.getMessage();
					}
				}
				merchantResponseCode = MerchantResponseCode.getMerchantCode(errorMsg);
			}
			response(merchantResponseCode.getMerchantCode(), merchantResponseCode.getResponseMessage(), response);
		}else{
			response("9999", "系统异常", response);
		}
	}
	
	public void response(String responseCode, String responseMsg, HttpServletResponse response){
		Map<String, Object> resParams = new HashMap<>();
		resParams.put("responseCode", responseCode);
		resParams.put("responseMsg", responseMsg);
		try {
			response.getWriter().write(JsonUtils.toJsonString(resParams));
			response.getWriter().close();
		} catch (IOException e) {
			logger.error("", e);
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
	 * 构建网关页面展示银行列表
	 * @param interfacePolicyProfileBeans 商户路由信息
	 * @return Map<String, List<String>>
	 */
	private Map<String, List<String>> gatewayPageDestructor(Map<String, List<InterfacePolicyProfileBean>> interfacePolicyProfileBeans) {
		Map<String, List<String>> interfaceInfos = new HashMap<String, List<String>>();
		// 银行编码
		String supportProvider = "";
		// 支付工具分类银行列表
		List<String> bankCodes = null;
		for (Map.Entry<String, List<InterfacePolicyProfileBean>> entry : interfacePolicyProfileBeans.entrySet()) {
			// 认证支付
			if (InterfaceType.AUTHPAY.name().equals(entry.getKey())) {
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
			if (InterfaceType.WXNATIVE.name().equals(entry.getKey())) {
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
			}
			
			// 微信公众号支付
			if (InterfaceType.WXJSAPI.name().equals(entry.getKey())) {
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
			if (InterfaceType.ALIPAY.name().equals(entry.getKey())) {
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
			if (InterfaceType.ALIPAYMICROPAY.name().equals(entry.getKey())) {
				for (InterfacePolicyProfileBean interfacePolicyProfileBean : entry.getValue()) {
					supportProvider = StringUtils.concatToSB(InterfaceType.ALIPAYMICROPAY.name(), "_", interfacePolicyProfileBean.getInterfaceProviderCode()).toString();
					if (interfaceInfos.containsKey(entry.getKey())) {
						if (interfaceInfos.get(entry.getKey()).contains(supportProvider)) continue;
						interfaceInfos.get(entry.getKey()).add(supportProvider);
					} else {
						bankCodes = new ArrayList<String>();
						bankCodes.add(supportProvider);
						interfaceInfos.put(InterfaceType.ALIPAYMICROPAY.name(), bankCodes);
					}
				}
			}
			if (InterfaceType.WXMICROPAY.name().equals(entry.getKey())) {
				for (InterfacePolicyProfileBean interfacePolicyProfileBean : entry.getValue()) {
					supportProvider = StringUtils.concatToSB(InterfaceType.WXMICROPAY.name(), "_", interfacePolicyProfileBean.getInterfaceProviderCode()).toString();
					if (interfaceInfos.containsKey(entry.getKey())) {
						if (interfaceInfos.get(entry.getKey()).contains(supportProvider)) continue;
						interfaceInfos.get(entry.getKey()).add(supportProvider);
					} else {
						bankCodes = new ArrayList<String>();
						bankCodes.add(supportProvider);
						interfaceInfos.put(InterfaceType.WXMICROPAY.name(), bankCodes);
					}
				}
			}
			if (InterfaceType.QQNATIVE.name().equals(entry.getKey())) {
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
			if (InterfaceType.QQH5.name().equals(entry.getKey())) {
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
			if (InterfaceType.UNIONPAYNATIVE.name().equals(entry.getKey())) {
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
			if (InterfaceType.JDH5.name().equals(entry.getKey())) {
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
            if (InterfaceType.ALIPAYJSAPI.name().equals(entry.getKey())) {
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
		Map<String, String> params = new HashMap<>();
		
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader((ServletInputStream) request.getInputStream()));
			String line = null;
			StringBuilder sb = new StringBuilder();
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			
			if(sb.toString().equals("")){
				Map<String, String[]> OriginalParams = JsonUtils.toObject(partnerRequest.getOriginalRequest(), new TypeReference<Map<String, String[]>>() {});
				for (String key : OriginalParams.keySet()) {
					params.put(key, OriginalParams.get(key)[0]);
				}
			}
			
			if(sb.toString().equals("") && params.size() == 0){
				throw new BusinessException(StringUtils.concatToSB(ExceptionMessages.PARAM_NOT_EXISTS, "-", Constants.PARAM_NAME_API_CODE).toString());
			}
			if(params.size() == 0){
				params = JsonUtils.toObject(sb.toString(), new TypeReference<Map<String, String>>(){});
			}
		} catch (IOException e) {
			logger.error("", e);
			throw new BusinessException(StringUtils.concatToSB(ExceptionMessages.PARAM_NOT_EXISTS, "-", Constants.PARAM_NAME_API_CODE).toString());
		}
		logger.info("原始请求信息:"+params);
		
		// 检查接口名称
		String apiCode = params.get(Constants.PARAM_NAME_API_CODE);
		if(apiCode == null || apiCode == ""){
			throw new BusinessException(ExceptionMessages.APICODE_NULL);
		}
		if (StringUtils.isBlank(apiCode)) {
			throw new BusinessException(StringUtils.concatToSB(ExceptionMessages.PARAM_NOT_EXISTS, "-", Constants.PARAM_NAME_API_CODE).toString());
		}
		partnerRequest.setApiCode(apiCode);
		// 检查输入字符集
		String inputCharset = params.get(Constants.PARAM_NAME_INPUT_CHARSET);
		if(inputCharset == null || inputCharset == ""){
			throw new BusinessException(ExceptionMessages.INPUTCHARSET_NULL);
		}
		if (StringUtils.isBlank(inputCharset)) {
			throw new BusinessException(StringUtils.concatToSB(ExceptionMessages.PARAM_NOT_EXISTS, "-", Constants.PARAM_NAME_INPUT_CHARSET).toString());
		}
		partnerRequest.setInputCharset(inputCharset);
		// 检查合作方
		final String partnerCode = params.get(Constants.PARAM_NAME_PARTNER);
		if(partnerCode == null || partnerCode == ""){
			throw new BusinessException(ExceptionMessages.PARTNERCODE_NULL);
		}
		if (StringUtils.isBlank(partnerCode)) {
			throw new BusinessException(StringUtils.concatToSB(ExceptionMessages.PARAM_NOT_EXISTS, "-", Constants.PARAM_NAME_PARTNER).toString());
		}
		
		partnerRequest.setPartner(partnerCode);
		// 检查签名方式
		String signType = params.get(Constants.PARAM_NAME_SIGN_TYPE);
		if(signType == null || signType == ""){
			throw new BusinessException(ExceptionMessages.SINGTYPE_NULL);
		}
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
		if(sign == null || sign == ""){
			throw new BusinessException(ExceptionMessages.SIGN_NULL);
		}
		if (StringUtils.isBlank(sign)) {
			throw new BusinessException(StringUtils.concatToSB(ExceptionMessages.PARAM_NOT_EXISTS, "-", Constants.PARAM_NAME_SIGN).toString());
		}
		partnerRequest.setSign(sign);

		// 检查商户订单号
		String outOrderId = params.get(Constants.PARAM_NAME_OUT_ORDER_ID);
		if(outOrderId == null || outOrderId == ""){
			throw new BusinessException(ExceptionMessages.OUTORDERID_NULL);
		}
		if (StringUtils.isBlank(outOrderId)) {
			throw new BusinessException(StringUtils.concatToSB(ExceptionMessages.PARAM_NOT_EXISTS, "-", Constants.PARAM_NAME_OUT_ORDER_ID).toString());
		}
		partnerRequest.setOutOrderId(outOrderId);

		// 回传参数
		String returnParam = params.get(Constants.PARAM_NAME_RETURNPARAM);
		if (StringUtils.notBlank(returnParam)) {
			partnerRequest.setReturnParam(returnParam);
		}
		
		// 签名摘要
		String mdsign = params.get(Constants.PARAM_NAME_SIGN);
		if(mdsign == null || mdsign == ""){
			throw new BusinessException(ExceptionMessages.SIGN_NULL);
		}

		// 查询卖方信息
		Customer customer = customerInterface.getCustomer(partnerRequest.getPartner());
		// 商户不存在
		if (customer == null) throw new BusinessException(ExceptionMessages.RECEIVER_NOT_EXISTS);

		if (customer.getStatus() == null || !customer.getStatus().equals(CustomerStatus.TRUE)) {
			throw new BusinessException(ExceptionMessages.RECEIVER_STATUS_ERROR);
		}
		
		
		CustomerKey customerKey = customerInterface.getCustomerKey(partnerRequest.getPartner(), KeyType.MD5);
		
		if(customerKey == null) throw new BusinessException(ExceptionMessages.RECEIVER_KEY_NOT_EXISTS);
		
		// 金额
		String amount = params.get(Constants.PARAM_NAME_AMOUNT);
		if(amount == null || amount == ""){
			throw new BusinessException(ExceptionMessages.AMOUNT_NULL);
		}
		checkAmount(amount, partnerCode, params.get("payType"));
		if(StringUtils.isBlank(amount)){
			throw new BusinessException(StringUtils.concatToSB(ExceptionMessages.PARAM_ERROR).toString());
		}
		
		try{
			Double.valueOf(amount);
		}catch(Exception e){
			throw new BusinessException(StringUtils.concatToSB(ExceptionMessages.PARAM_ERROR).toString());
		}
		
		if(amount.indexOf(".")>0){
			if(amount.split("\\.")[1].length() > 2){
				throw new BusinessException(StringUtils.concatToSB(ExceptionMessages.PARAM_ERROR).toString());
			}
		}
		
		// 支付方式验证
		String payType = params.get("payType");
		if(payType == null || payType == ""){
			throw new BusinessException(ExceptionMessages.PAYTYPE_NULL);
		}
		
		
		
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
                if (iterator.hasNext()) signSource.append("&");
			}
		}

		// 计算签名
		String calSign = null;
		if (SignType.MD5.name().equals(signType)) {
			signSource.append(cipherKey);
			try {
				logger.info("current inputCharset is {}", inputCharset);
				calSign = DigestUtils.md5DigestAsHex(signSource.toString().getBytes(inputCharset));
			} catch (UnsupportedEncodingException e) {
				throw new BusinessException(StringUtils.concatToSB(ExceptionMessages.PARAM_ERROR, "-", Constants.PARAM_NAME_INPUT_CHARSET).toString());
			}
		}

		if (!sign.equals(calSign)) {
			logger.info("sign check error,outOrderId="+outOrderId+",sign="+sign+",calSign="+calSign+"");
			throw new BusinessException(StringUtils.concatToSB(ExceptionMessages.SIGN_ERROR).toString());
		}

		partnerRequest.setAmount(params.get(Constants.PARAM_NAME_AMOUNT));
		
		//接入方式
		partnerRequest.setAccMode("INTERFACE");
		
		//接口类型
		try{
			PayType.valueOf(params.get("payType"));
			partnerRequest.setPayType(params.get("payType"));
		}catch(Exception e){
			throw new BusinessException(ExceptionMessages.PAY_TYPE_ERROR);
		}
		
		//openid
		//partnerRequest.setOpenId(params.get("openId"));
			
		partnerRequest.setWxNativeType("URL");
		
		// 银行卡号
		if(PayType.AUTHPAY.toString().equals(params.get("payType"))){
			partnerRequest.setBankCardNo(params.get("bankCardNo"));
		// 如果微信条码和支付宝条码则把authCode传给opendID
		} else if (PayType.ALIPAYMICROPAY.toString().equals(params.get("payType")) || PayType.WXMICROPAY.toString().equals(params.get("payType"))
                || PayType.ALIPAYJSAPI.toString().equals(params.get("payType"))) {
			if (StringUtils.isBlank(params.get("authCode"))) {
				throw new BusinessException(ExceptionMessages.AUTH_CODE_NULL);
			}
			partnerRequest.setAuthCode(params.get("authCode"));
		}
		
		partnerRequest.setProduct(params.get("product"));
		if(params.get("notifyUrl") == null || params.get("notifyUrl") == ""){
			throw new BusinessException(ExceptionMessages.NOTIFYURL_NULL);
		}
		// 通知地址
		if(StringUtils.isBlank(params.get("notifyUrl"))){
			throw new BusinessException(StringUtils.concatToSB(ExceptionMessages.PARAM_ERROR).toString());
		}
		partnerRequest.setNotifyUrl(params.get("notifyUrl"));
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
	
	public void checkAmount(String amount, String partnerCode, String payType) throws BusinessException{
		//交易限额
		double sumAmount = 0;
		CustomerConfig config = null;
		Date currentDate = new Date();
		sumAmount = onlineTradeHessianService.orderAmountSum(currentDate, partnerCode, payType);
		config = onlineTradeHessianService.queryConfig(partnerCode, payType);
		if(config != null){
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
			String str = sdf.format(currentDate);
			String statr = config.getStartTime();//配置开始时间
			String end = config.getEndTime();//配置结束时间
			double maxAmount = config.getMaxAmount();//最大金额
			double minAmount = config.getMinAmount();//最小金额
			//开始校验是否在交易时间段
			if(Integer.parseInt(str.substring(0, 2)) >= Integer.parseInt(statr.substring(0, 2)) && Integer.parseInt(str.substring(0, 2)) <= Integer.parseInt(end.substring(0, 2))){
				//开始  时相同   检测分
				try {
					if(Integer.parseInt(str.substring(0, 2)) == Integer.parseInt(statr.substring(0, 2))){
						if(Integer.parseInt(str.substring(3, 5)) >= Integer.parseInt(statr.substring(3, 5))){
						}else{
							throw new BusinessException(ExceptionMessages.CUSTOMER_CONFIG_DATA);  //0201商户交易不在可用时间段
						}
					}
				} catch (Exception e) {
					logger.error("时间校验失败！", e);
				}
				// 结束 时相同 检测分
				try {
					if(Integer.parseInt(str.substring(0, 2)) == Integer.parseInt(end.substring(0, 2))){
						if(Integer.parseInt(str.substring(3, 5)) <= Integer.parseInt(end.substring(3, 5))){
						}else{
							throw new BusinessException(ExceptionMessages.CUSTOMER_CONFIG_DATA);  //0201商户交易不在可用时间段
						}
					}
				} catch (Exception e) {
					logger.error("商户交易时间段校验失败！", e);
				}
				//开始校验支付金额是否超过或者不满足设置值
				try {
					if(Double.parseDouble(amount) <= maxAmount && Double.parseDouble(amount) >= minAmount){
					}else{
						throw new BusinessException(ExceptionMessages.CUSTOMER_CONFIG_AMOUNT);  //0205商户交易金额非法
					}
				} catch (Exception e) {
					logger.error("商户交易金额校验失败！", e);
				}
				//开始校验是否超过日上限
				try {
					if(sumAmount+Double.parseDouble(amount) <= config.getDayMax()){
					}else{
						throw new BusinessException(ExceptionMessages.CUSTOMER_CONFIG_DAY_MAX);  //0204商户当日交易金额超过上限
					}
				} catch (Exception e) {
					logger.error("商户当日交易金额校验失败！", e);
				}
			}else{
				throw new BusinessException(ExceptionMessages.CUSTOMER_CONFIG_DATA);  //0201商户交易不在可用时间段
			}
		}
	}

	@RequestMapping("htmlSubmit")
    public String htmlSubmit(HttpServletRequest request, HttpServletResponse response, String interfaceRequestID) {
        Map<String, String> params = new HashMap<>();
        params.put("html", CacheUtils.get(interfaceRequestID, String.class));
        request.setAttribute("params", params);
	    return "htmlSubmit";
    }

    @RequestMapping("submit")
    public String submit(HttpServletRequest request, HttpServletResponse response, String interfaceRequestID) {
        Map<String, String> params = JsonUtils.toObject(CacheUtils.get(interfaceRequestID, String.class), new TypeReference<Map<String, String>>() {
        });
        Map<String, Object> result = new HashMap<>();
        result.put("params", params);
        request.setAttribute("result", result);
        return "submit";
    }

    @RequestMapping("qrCode")
    public String qrCode(HttpServletRequest request, String requestId){
	    try {
	        String res = CacheUtils.get(requestId, String.class);
            Map<String, String> retMap = JsonUtils.toObject(res, new TypeReference<Map<String, String>>() {
            });
            request.setAttribute("amount", retMap.get("amount"));
            request.setAttribute("outOrderId", retMap.get("interfaceRequestID"));
            request.setAttribute("code_url", retMap.get("code_img_url"));
        } catch (Exception e) {
            logger.error("异常!异常信息:{}", e);
        }
	    return "qrCodePay";
    }

	@RequestMapping("qrCodePay")
	public String qrCodePay(HttpServletRequest request, String requestId){
        String res = CacheUtils.get("ALIPAYCODE:" + requestId, String.class);
        if (StringUtils.isBlank(res)){
            request.setAttribute("status", "exclamation-circle");
            request.setAttribute("message", "二维码已过期<br/>请重新下单！");
            return "QRCode/message";
        }
        Map<String, String> retMap = JsonUtils.toObject(res, new TypeReference<Map<String, String>>() {
        });
        String agent = request.getHeader("user-agent");
        CharSequence android = "Android";
        CharSequence ios = "iPhone";
        try {
            if (agent.contains(android) || agent.contains(ios)) {
                return "redirect:" + retMap.get("code_url");
            }
            Order order = onlineTradeHessianService.queryOrderBy(retMap.get("interfaceRequestID"));
            if (null == order) {
                request.setAttribute("status", "exclamation-circle");
                request.setAttribute("message", "不存在该订单<br/>请重新下单！");
                return "QRCode/message";
            }
            request.setAttribute("amount", retMap.get("amount"));
            request.setAttribute("customerNo", order.getReceiver());
            request.setAttribute("orderCode", order.getCode());
			request.setAttribute("outOrderId", order.getRequestCode());
			request.setAttribute("code_url", "http://pay.feiyijj.com/gateway/interface/qrCodePay?requestId=" + requestId);
		} catch (Exception e) {
			logger.error("异常!异常信息:{}", e);
		}
		return "qrCodePay";
	}
}