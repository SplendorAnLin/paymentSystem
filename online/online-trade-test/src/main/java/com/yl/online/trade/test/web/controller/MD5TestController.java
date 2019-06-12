package com.yl.online.trade.test.web.controller;

import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lefu.commons.utils.lang.StringUtils;
import com.lefu.commons.utils.security.DigestUtils;

/**
 * MD5测试控制器
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月22日
 * @version V1.0.0
 */
@Controller
public class MD5TestController {
	private static final Logger logger = LoggerFactory.getLogger(MD5TestController.class);

	@RequestMapping("MD5Test")
	public void forward1(HttpServletRequest request, HttpServletResponse response) {
		Map<String, String[]> map = request.getParameterMap();
		ArrayList<String> paramNames = new ArrayList<>(map.keySet());
		Collections.sort(paramNames);
		// 组织待签名信息
		StringBuilder signSource = new StringBuilder();
		Iterator<String> iterator = paramNames.iterator();
		while (iterator.hasNext()) {
			String paramName = iterator.next();
			if (!"sign".equals(paramName) && !"gatewayUrl".equals(paramName) && !"url".equals(paramName) && map.get(paramName)[0] != "" && !"key".equals(paramName)) {
				signSource.append(paramName).append("=").append(map.get(paramName)[0]);
				if (iterator.hasNext()) signSource.append("&");
			}
		}

		String partnerString = signSource.toString();
		partnerString += request.getParameter("key");
		logger.info("签名串：{}", partnerString);
		try {
			if (StringUtils.notBlank(request.getParameter("inputCharset"))) {
				partnerString = DigestUtils.md5DigestAsHex(partnerString.getBytes(request.getParameter("inputCharset")));
			} else {
				partnerString = DigestUtils.md5DigestAsHex(partnerString.getBytes());
			}
			response.getWriter().print(partnerString);
		} catch (Exception e) {
			logger.error("MD5出异常了！！", e);
		}
	}

	@RequestMapping({"toPayFast"})
	public String toPayFast(HttpServletRequest request, HttpServletResponse response, String amount) {
		Map<String, String> map = new HashMap();
		map.put("apiCode", "YL-PAY");
		map.put("inputCharset", "UTF-8");
		map.put("partner", "C100009");
		map.put("outOrderId", "TD" + System.currentTimeMillis());
		map.put("amount", amount);
		map.put("currency", "CNY");
		map.put("payType", "ALL");
		map.put("interfaceCode", "");
		map.put("retryFalg", "TRUE");
		map.put("submitTime", (new SimpleDateFormat("yyyyMMddHHmmss")).format(new Date()));
		map.put("timeout", "1D");
		map.put("product", "在线充值");
		map.put("redirectUrl", "http://tlecaipay.com");
		map.put("notifyUrl", "http://tlecaipay.com");
		map.put("accMode", "GATEWAY");
		map.put("signType", "MD5");
		ArrayList<String> paramNames = new ArrayList(map.keySet());
		Collections.sort(paramNames);
		StringBuilder signSource = new StringBuilder();
		Iterator iterator = paramNames.iterator();

		String partnerString;
		while(iterator.hasNext()) {
			partnerString = (String)iterator.next();
			if (!"sign".equals(partnerString) && !"gatewayUrl".equals(partnerString) && !"url".equals(partnerString) && map.get(partnerString) != "" && !"key".equals(partnerString)) {
				signSource.append(partnerString).append("=").append((String)map.get(partnerString));
				if (iterator.hasNext()) {
					signSource.append("&");
				}
			}
		}

		partnerString = signSource.toString();
		partnerString = partnerString + "9c6ed2f7f9e2d01c6c557f44b4edba6a";
		logger.info("签名串：{}", partnerString);

		try {
			if (StringUtils.notBlank(request.getParameter("inputCharset"))) {
				partnerString = DigestUtils.md5DigestAsHex(partnerString.getBytes(request.getParameter("inputCharset")));
			} else {
				partnerString = DigestUtils.md5DigestAsHex(partnerString.getBytes());
			}

			map.put("sign", partnerString);
		} catch (Exception var10) {
			logger.error("MD5出异常了！！", var10);
		}

		map.put("url", "http://pay.feiyijj.com/gateway/pay");
		Map<String, Object> result = new HashMap();
		result.put("params", map);
		request.setAttribute("result", result);
		return "/trade/submit";
	}

	public static String getUrlParamsByMap(Map<String, String> map) throws Exception {
		if (map == null) {
			return "";
		} else {
			StringBuffer sb = new StringBuffer();
			Iterator i$ = map.entrySet().iterator();

			while(i$.hasNext()) {
				Map.Entry<String, String> entry = (Map.Entry)i$.next();
				sb.append((String)entry.getKey() + "=" + (String)entry.getValue());
				sb.append("&");
			}

			String s = sb.toString();
			if (s.endsWith("&")) {
				s = org.apache.commons.lang3.StringUtils.substringBeforeLast(s, "&");
			}

			return s;
		}
	}
}
