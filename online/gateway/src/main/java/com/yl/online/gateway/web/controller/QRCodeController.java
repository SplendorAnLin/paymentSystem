package com.yl.online.gateway.web.controller;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lefu.commons.utils.lang.JsonUtils;
import com.lefu.commons.utils.lang.StringUtils;
import com.lefu.commons.utils.security.DigestUtils;
import com.lefu.commons.utils.web.HttpClientUtils;
import com.lefu.commons.utils.web.HttpClientUtils.Method;
import com.yl.boss.api.interfaces.CustomerInterface;
import com.yl.boss.api.interfaces.DeviceInterface;
import com.yl.boss.api.interfaces.ProtocolInterface;
import com.yl.boss.api.interfaces.QRCodeInterface;
import com.yl.online.gateway.bean.License;
import com.yl.online.gateway.context.RequestProxy;
import com.yl.online.gateway.service.LicenseService;
import com.yl.online.gateway.utils.CodeBuilder;
import com.yl.sms.SmsUtils;

/**
 * 水牌扫码支付
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月23日
 * @version V1.0.0
 */
@Controller
public class QRCodeController {

	private static final Logger logger = LoggerFactory.getLogger(QRCodeController.class);

	@Resource
	private LicenseService licenseService;

	@Resource
	private QRCodeInterface qRCodeInterface;

	@Resource
	private DeviceInterface deviceInterface;

	@Resource
	private CustomerInterface customerInterface;
	
	@Resource
	private ProtocolInterface protocolInterface;
	
	private static Properties prop = new Properties();
	static {
		try {
			prop.load(
					new InputStreamReader(QRCodeController.class.getClassLoader().getResourceAsStream("serverHost.properties")));
		} catch (IOException e) {
			
		}
	}
	
	private static final String SIGN_TYPE = "MD5";

	@RequestMapping("QRpay.htm")
	public String QRCode(Model model, HttpServletRequest request, HttpServletResponse response, String cardNo,
			String checkCode) {
		if(checkCode == null || cardNo == null){
			request.setAttribute("status", "times-circle");
			request.setAttribute("message", "请扫码进入！");
			return "QRCode/message";
		}
		try {
			// 设置编码
			RequestProxy requestProxy = new RequestProxy(request);
			request = requestProxy;
			// 验证水牌是否存在
			String device = deviceInterface.checkLicense(cardNo, checkCode);
			if (device == null) {
				// 不存在提示水牌不存在
				request.setAttribute("status", "times-circle");
				request.setAttribute("message", "水牌不存在！<br/>采购请走官方指定渠道！");
				return "QRCode/message";
			}
			Map<String, Object> wapProtocol = protocolInterface.wapProtocol("REGISTER", "PACT");
			String title = wapProtocol.get("title").toString();
			String content = wapProtocol.get("content").toString().replaceAll("\n", "");
			// 验证水牌状态
			License license = licenseService.getInfo(cardNo, checkCode);
			if (device.equals("ALLOT")) {
				// 注册步骤检测
				if(license == null){
					// 未绑定跳转绑定页面
					request.setAttribute("cardNo", cardNo);
					request.setAttribute("checkCode", checkCode);
					request.setAttribute("title", title);
					request.setAttribute("content", content);
					return "QRCode/stepOne";
				} else if(license.getFullName() == null) {
					request.setAttribute("id", license.getId());
					return "QRCode/stepTwo";
				} else if(license.getAccNo() == null){
					request.setAttribute("id", license.getId());
					return "QRCode/stepThree";
				} 
			} else if (device.equals("ORDER_WAIT") || device.equals("ORDER_CONFIRM") || device.equals("ORDER_FAIL")
					|| device.equals("MAKING") || device.equals("OK")) {
				request.setAttribute("status", "exclamation-circle");
				request.setAttribute("message", "该码尚未初始化！<br/>请联系管理员处理！");
				return "QRCode/message";
			}
			// 商户状态校验
			String customerNo = deviceInterface.getCustomerNo(cardNo, checkCode);
			String status = customerInterface.getCustomerStatus(customerNo);
			// 审核拒绝 退回重新填写
			if(status.equals("AUDIT_REFUSE") || status.equals("AGENT_REFUSE")){
				request.setAttribute("id", license.getId());
				request.setAttribute("phone", license.getPhone());
				request.setAttribute("title", title);
				request.setAttribute("content", content);
				return "QRCode/stepAgain";
			}
			if (!status.equals("TRUE")) {
				request.setAttribute("status", "exclamation-circle");
				request.setAttribute("message", "系统审核中！<br/>请您耐心等待！");
				return "QRCode/message";
			}
			// 支付页面
			if (device.equals("BINDED")) {
				String agent = request.getHeader("user-agent");
				CharSequence wechat = "MicroMessenger";
				CharSequence alipay = "AlipayClient";
				String fullName = customerInterface.getCustomerFullName(customerNo);
				request.setAttribute("partnerNo", customerNo);
				request.setAttribute("fullName", fullName);
				// 来源判断
				if (agent.contains(alipay)) {
					// 限额获取
					request.setAttribute("payType", "ALIPAYJSAPI");
					return "QRCode/pay";
				} else if (agent.contains(wechat)) {
					// 限额获取
					request.setAttribute("payType", "WXJSAPI");
					return "QRCode/pay";
				} else {
					request.setAttribute("status", "exclamation-circle");
					request.setAttribute("message", "暂不支持此支付方式<br/>请使用微信或支付宝扫码！");
					return "QRCode/message";
				}
			}
		} catch (Exception e) {
			logger.error("QRCode ERROR:",e);
			request.setAttribute("status", "exclamation-circle");
			request.setAttribute("message", "数据异常！<br/>请联系管理员处理！");
			return "QRCode/message";
		}
		return null;
	}

	
	@RequestMapping(value = "one", method = RequestMethod.POST)
	public String one(Model model, HttpServletRequest request, HttpServletResponse response, String checkNum) {
		try {
			if (request.getSession().getAttribute("verifyCode").equals(checkNum)) {
				RequestProxy requestProxy = new RequestProxy(request);
				requestProxy.getParameterMap();
				request = requestProxy;
				Map<String, Object> info = retrieveParams(request.getParameterMap());
				info.remove("checkNum");
				info.remove("agree");
				License license = JsonUtils.toObject(JsonUtils.toJsonString(info), new TypeReference<License>() {
				});
				license.setCreateTime(new Date());
				license.setUpdateTime(new Date());
				licenseService.licenseLoginInfo(license);
				license = licenseService.checkPhone(license.getPhone());
				request.setAttribute("id", license.getId());
				return "QRCode/stepTwo";
			}
			request.setAttribute("status", "times-circle");
			request.setAttribute("message", "手机验证码错误！");
			return "QRCode/message";
		} catch (Exception e) {
			logger.error("one ERROR:", e);
			request.setAttribute("status", "times-circle");
			request.setAttribute("message", "小哥上门收款去啦~！<br/>请稍后重试！");
			return "QRCode/message";
		}
	}
	
	@RequestMapping(value = "again", method = RequestMethod.POST)
	public String again(Model model, HttpServletRequest request, HttpServletResponse response, String checkNum){
		try {
			if (request.getSession().getAttribute("verifyCode").equals(checkNum)) {
				RequestProxy requestProxy = new RequestProxy(request);
				requestProxy.getParameterMap();
				request = requestProxy;
				Map<String, Object> info = retrieveParams(request.getParameterMap());
				info.remove("checkNum");
				info.remove("agree");
				License license = JsonUtils.toObject(JsonUtils.toJsonString(info), new TypeReference<License>() {
				});
				license.setUpdateTime(new Date());
				licenseService.again(license);
				request.setAttribute("id", license.getId());
				return "QRCode/stepTwo";
			}
			request.setAttribute("status", "times-circle");
			request.setAttribute("message", "手机验证码错误！");
			return "QRCode/message";
		} catch (Exception e) {
			logger.error("again ERROR:",e);
			request.setAttribute("status", "times-circle");
			request.setAttribute("message", "小哥上门收款去啦~！<br/>请稍后重试！");
			return "QRCode/message";
		}
	}

	@RequestMapping(value = "two", method = RequestMethod.POST)
	public String two(HttpServletRequest request, HttpServletResponse response, Model model) {
		try {
			RequestProxy requestProxy = new RequestProxy(request);
			requestProxy.getParameterMap();
			request = requestProxy;
			License license = JsonUtils.toObject(JsonUtils.toJsonString(retrieveParams(request.getParameterMap())),
					new TypeReference<License>() {
					});
			license.setUpdateTime(new Date());
			licenseService.licenseBaseInfo(license);
			request.setAttribute("id", license.getId());
			return "QRCode/stepThree";
		} catch (Exception e) {
			logger.error("two ERROR:", e);
			request.setAttribute("status", "times-circle");
			request.setAttribute("message", "小哥上门收款去啦~！<br/>请稍后重试！");
			return "QRCode/message";
		}
	}

	@RequestMapping(value = "three", method = RequestMethod.POST)
	public String three(License license, HttpServletRequest request, HttpServletResponse response, Model model ,int hidcount, int hbankcount) {
		try {
			license.setUpdateTime(new Date());
			licenseService.licenseBankCard(license);
			License li = licenseService.findById(license.getId());
			StringBuffer id = new StringBuffer();
			StringBuffer bank = new StringBuffer();
			if(hidcount != 0 && hbankcount != 0){
				for (int i = 0; i <= hidcount; i++) {
					id.append(request.getParameter("hid_"+i));
				}
				
				for (int i = 0; i <= hbankcount; i++) {
					bank.append(request.getParameter("hbank_"+i));
				}
			} else {
				request.setAttribute("status", "times-circle");
				request.setAttribute("message", "暂无图片数据~！<br/>请稍后重试！");
				return "QRCode/message";
			}
			
			li.setHandheldBank(bank.toString());
			li.setHandheldId(id.toString());
			boolean isNaN = qRCodeInterface.sweepTheNetwork(
					JsonUtils.toObject(JsonUtils.toJsonString(li), new TypeReference<com.yl.boss.api.bean.License>() {
					}));
			if (isNaN) {
				request.setAttribute("status", "check-circle");
				request.setAttribute("message", "提交成功！系统正在审核中......");
				return "QRCode/message";
			}else {
				request.setAttribute("status", "times-circle");
				request.setAttribute("message", "小哥上门收款去啦~！<br/>请稍后重试！");
				return "QRCode/message";
			}
		} catch (Exception e) {
			logger.error("three ERROR:",e);
			request.setAttribute("status", "times-circle");
			request.setAttribute("message", "小哥上门收款去啦~！<br/>请稍后重试！");
			return "QRCode/message";
		}
	}

	@SuppressWarnings("unused")
	@RequestMapping(value = "QRAutoPay", method = RequestMethod.POST)
	public String QRAutoPay(HttpServletRequest request, HttpServletResponse response, String amount, String partnerNo,
			String payType) {
		if ("WXJSAPI".equals(payType) || "ALIPAYJSAPI".equals(payType)) {
			String sign = "";
			String orderId = CodeBuilder.build("CO", "yyyyMMdd");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			Date date = new Date();
			String extParam = request.getParameter("extParam");
			if (StringUtils.isBlank(extParam)) {
				extParam = "";
			}
			String productName = request.getParameter("productName");
			if (StringUtils.isBlank(productName)) {
				productName = "自助支付";
			} else {
				try {
					productName = URLDecoder.decode(productName, "UTF-8");
				} catch (UnsupportedEncodingException e) {
					logger.info("URL解码失败：{}", e);
				}
			}
			Map<String, String> params = new HashMap<>();
			params.put("apiCode", "YL-PAY");
			params.put("inputCharset", "UTF-8");
			params.put("partner", partnerNo);
			params.put("outOrderId", orderId);
			params.put("amount", String.valueOf(amount));
			params.put("currency", "CNY");
			params.put("payType", payType);
			params.put("interfaceCode", "");
			params.put("retryFalg", "TRUE");
			params.put("submitTime", sdf.format(date));
			params.put("timeout", "1D");
			params.put("extParam", extParam);
			params.put("returnParam", "");
			params.put("productName", productName);
			params.put("redirectUrl", prop.getProperty("redirectUrl"));
			params.put("notifyUrl", prop.getProperty("notifyUrl"));
			params.put("bankCardNo", "");
			params.put("accMode", "GATEWAY");
			params.put("wxNativeType", "PAGE");
			params.put("clientIP", "");
			params.put("signType", SIGN_TYPE);
			ArrayList<String> paramNames = new ArrayList<>(params.keySet());
			Collections.sort(paramNames);
			// 组织待签名信息
			StringBuilder signSource = new StringBuilder();
			Iterator<String> iterator = paramNames.iterator();
			while (iterator.hasNext()) {
				String paramName = iterator.next();
				if (!"sign".equals(paramName) && !"gatewayUrl".equals(paramName) && !"url".equals(paramName)
						&& params.get(paramName).toString() != "" && !"key".equals(paramName)) {
					signSource.append(paramName).append("=").append(params.get(paramName).toString());
					if (iterator.hasNext())
						signSource.append("&");
				}
			}
			String partnerString = signSource.toString();
			// 商户密钥
			String publickey = customerInterface.getCustomerMD5Key(partnerNo);
			partnerString += publickey;
			logger.info("签名串：{}", partnerString);
			try {
				if (StringUtils.notBlank("UTF-8")) {
					partnerString = DigestUtils
							.md5DigestAsHex(partnerString.getBytes("UTF-8"));
				} else {
					partnerString = DigestUtils.md5DigestAsHex(partnerString.getBytes());
				}
				request.setAttribute("partner", partnerNo);
				request.setAttribute("outOrderId", orderId);
				request.setAttribute("amount", params.get("amount"));
				request.setAttribute("payType", payType);
				request.setAttribute("submitTime", params.get("submitTime"));
				request.setAttribute("partnerString", partnerString);
				request.setAttribute("productName", params.get("productName"));
				request.setAttribute("redirectUrl", params.get("redirectUrl"));
				request.setAttribute("notifyUrl", params.get("notifyUrl"));
				request.setAttribute("payUrl", prop.getProperty("payUrl"));
				request.setAttribute("extParam", extParam);
				return "QRCode/redirect";
			} catch (Exception e) {
				logger.error("MD5出异常了！！", e);
				request.setAttribute("status", "times-circle");
				request.setAttribute("message", "小哥上门收款去啦~！<br/>请稍后重试！");
				return "QRCode/message";
			}
		}
//		if(payType.equals("ALIPAY")){
//			request.setAttribute("status", "check-circle");
//			request.setAttribute("message", "支付宝开通中...");
//			return "QRCode/message";
//		}
		return null;
	}
	
	@SuppressWarnings("unused")
	@RequestMapping(value = "oemQRAutoPay", method = RequestMethod.POST)
	public String oemQRAutoPay(HttpServletRequest request, HttpServletResponse response, String amount, String partnerNo,
			String payType, String extParam) {
		if (payType.equals("WXJSAPI")) {
			String sign = "";
			String orderId = CodeBuilder.build("CO", "yyyyMMdd");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			Date date = new Date();
			Map<String, String> params = new HashMap<>();
			params.put("apiCode", "YL-PAY");
			params.put("inputCharset", "UTF-8");
			params.put("partner", partnerNo);
			params.put("outOrderId", orderId);
			params.put("amount", String.valueOf(amount));
			params.put("currency", "CNY");
			params.put("payType", "WXJSAPI");
			params.put("interfaceCode", "");
			params.put("retryFalg", "TRUE");
			params.put("submitTime", sdf.format(date));
			params.put("timeout", "1D");
			params.put("extParam", extParam);
			params.put("returnParam", "");
			params.put("productName", "自助支付");
			params.put("redirectUrl", prop.getProperty("redirectUrl"));
			params.put("notifyUrl", prop.getProperty("notifyUrl"));
			params.put("bankCardNo", "");
			params.put("accMode", "GATEWAY");
			params.put("wxNativeType", "PAGE");
			params.put("clientIP", "");
			params.put("signType", SIGN_TYPE);
			ArrayList<String> paramNames = new ArrayList<>(params.keySet());
			Collections.sort(paramNames);
			// 组织待签名信息
			StringBuilder signSource = new StringBuilder();
			Iterator<String> iterator = paramNames.iterator();
			while (iterator.hasNext()) {
				String paramName = iterator.next();
				if (!"sign".equals(paramName) && !"gatewayUrl".equals(paramName) && !"url".equals(paramName)
						&& params.get(paramName).toString() != "" && !"key".equals(paramName)) {
					signSource.append(paramName).append("=").append(params.get(paramName).toString());
					if (iterator.hasNext())
						signSource.append("&");
				}
			}
			String partnerString = signSource.toString();
			// 商户密钥
			String publickey = customerInterface.getCustomerMD5Key(partnerNo);
			partnerString += publickey;
			logger.info("签名串：{}", partnerString);
			try {
				if (StringUtils.notBlank("UTF-8")) {
					partnerString = DigestUtils
							.md5DigestAsHex(partnerString.getBytes("UTF-8"));
				} else {
					partnerString = DigestUtils.md5DigestAsHex(partnerString.getBytes());
				}
				request.setAttribute("partner", partnerNo);
				request.setAttribute("outOrderId", orderId);
				request.setAttribute("amount", params.get("amount"));
				request.setAttribute("payType", "WXJSAPI");
				request.setAttribute("submitTime", params.get("submitTime"));
				request.setAttribute("partnerString", partnerString);
				request.setAttribute("productName", params.get("productName"));
				request.setAttribute("redirectUrl", params.get("redirectUrl"));
				request.setAttribute("notifyUrl", params.get("notifyUrl"));
				request.setAttribute("payUrl", prop.getProperty("payUrl"));
				request.setAttribute("extParam", params.get("extParam"));
				return "QRCode/redirect";
			} catch (Exception e) {
				logger.error("MD5出异常了！！", e);
				request.setAttribute("status", "times-circle");
				request.setAttribute("message", "小哥上门收款去啦~！<br/>请稍后重试！");
				return "QRCode/message";
			}
		}
		if(payType.equals("ALIPAY")){
			request.setAttribute("status", "check-circle");
			request.setAttribute("message", "支付宝开通中...");
			return "QRCode/message";
		}
		return null;
	}

	@RequestMapping(value = "checkPhone", method = RequestMethod.POST)
	public void checkPhong(String phone, Model model, HttpServletResponse response) throws IOException {
		response.setHeader("Access-Control-Allow-Methods", "POST");
		PrintWriter writer = response.getWriter();
		writer.print(customerInterface.checkPhone(phone));
	}

	@RequestMapping(value = "checkFullName")
	public void checkFullName(String fullName, Model model, HttpServletResponse response, HttpServletRequest request, PrintWriter writer)
			throws IOException {
		writer.print(customerInterface.checkFullName(fullName));
	}

	@RequestMapping(value = "sendVerifyCode", method = RequestMethod.POST)
	public void sendVerifyCode(HttpServletRequest request, HttpServletResponse response, Model model, String phone)
			throws IOException {
		if (request.getSession() == null) {
			HttpSession session = request.getSession(true);
			session.getAttribute("AUTH");
		}
		Random random = new Random();
		// 生成验证码6位随机数字
		String verifyCode = "" + random.nextInt(10) + random.nextInt(10) + random.nextInt(10) + random.nextInt(10)
				+ random.nextInt(10) + random.nextInt(10);
		// 放到session中
		request.getSession().setAttribute("verifyCode", verifyCode);
		SmsUtils.sendCustom(String.format(com.yl.online.gateway.Constants.SMS_QROPEN_ACC, verifyCode), phone);
/*		response.setContentType("application/json");
		response.setHeader("Access-Control-Allow-Methods", "POST");
		PrintWriter writer = response.getWriter();
		writer.println(verifyCode);*/
	}

	/**
	 * 城市信息加载
	 */
	@RequestMapping(value = "cityInformation")
	public void cityInformation(HttpServletRequest request, HttpServletResponse response) {
		try {
			String code = request.getParameterMap().get("code")[0];
			code = customerInterface.ajaxQueryDictionaryByTypeCode(code);
			response.setContentType("application/json");
			response.setHeader("Access-Control-Allow-Methods", "POST");
			PrintWriter writer = response.getWriter();
			writer.print(code);
		} catch (Exception e) {
			logger.error("系统异常:", e);
		}
	}
	
	/**
	 * 卡识别
	 */
	@RequestMapping(value = "toCachecenterIin", method = RequestMethod.POST)
	public void toCachecenterIin(HttpServletRequest request, HttpServletResponse response) {
		String res = "";
		try {
			res = res + "cardNo=" + request.getParameterMap().get("cardNo")[0];
			String url = (String) prop.getProperty("cachecenter.service.url") + "/iin/recognition.htm?" + res;
			String resData = HttpClientUtils.send(Method.POST, url, "", true, Charset.forName("UTF-8").name(), 6000);
			response.setContentType("application/json");
			response.setHeader("Access-Control-Allow-Methods", "POST");
			PrintWriter writer = response.getWriter();
			writer.print(resData);
		} catch (IOException e) {
			logger.error("系统异常:", e);
		}
	}

	@RequestMapping(value = "toCachecenterCnaps", method = RequestMethod.POST)
	public void toCachecenterCnaps(HttpServletRequest request, HttpServletResponse response) {
		String res = "";
		try {
			res = res + "word=" + request.getParameterMap().get("word")[0];
			if (StringUtils.notBlank(request.getParameterMap().get("providerCode")[0])) {
				res = res + "&providerCode=" + request.getParameterMap().get("providerCode")[0];
			}
			if (StringUtils.notBlank(request.getParameterMap().get("clearBankLevel")[0])) {
				res = res + "&clearBankLevel=" + request.getParameterMap().get("clearBankLevel")[0];
			}
			String url = (String) prop.getProperty("cachecenter.service.url")
					+ "/cnaps/suggestSearch.htm?fields=providerCode&fields=name&fields=clearingBankCode&fields=providerCode";
			String resData = HttpClientUtils.send(Method.POST, url, res, true, Charset.forName("UTF-8").name(), 6000);
			response.setContentType("application/json");
			response.setHeader("Access-Control-Allow-Methods", "POST");
			PrintWriter writer = response.getWriter();
			writer.print(resData);
		} catch (IOException e) {
			logger.error("系统异常:", e);
		}
	}

	/**
	 * 参数转换
	 */
	public static Map<String, Object> retrieveParams(Map<String, String[]> requestMap) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		for (String key : requestMap.keySet()) {
			String[] value = requestMap.get(key);
			if (value != null) {
				resultMap.put(key, Array.get(value, 0).toString().trim());
			}
		}
		return resultMap;
	}

	
	public static Properties getProp() {
		return prop;
	}

	public static void setProp(Properties prop) {
		QRCodeController.prop = prop;
	}
	
}