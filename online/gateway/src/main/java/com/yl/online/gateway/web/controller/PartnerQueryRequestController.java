package com.yl.online.gateway.web.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lefu.commons.utils.lang.StringUtils;
import com.lefu.commons.utils.security.DigestUtils;
import com.lefu.commons.utils.web.HttpClientUtils;
import com.lefu.commons.utils.web.HttpClientUtils.Method;
import com.yl.online.gateway.Constants;
import com.yl.online.gateway.enums.SignType;

/**
 * 模拟商户支付结果查询
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月23日
 * @version V1.0.0
 */
@Controller
@RequestMapping("/partner")
public class PartnerQueryRequestController {
	private static final Logger logger = LoggerFactory.getLogger(PartnerQueryRequestController.class);
	
	@SuppressWarnings("rawtypes")
	@RequestMapping("query")
	public void receivePayRequest(HttpServletRequest request, HttpServletResponse response) {
		logger.info("**************** 模拟商户查询支付结果 ****************");
		Map responseMsg = partnerQueryRequest();
		logger.info("支付系统的响应信息：{}", responseMsg);
		logger.info("响应码：{}，响应描述：{}，订单状态：{}", responseMsg.get("responseCode"),
				responseMsg.get("responseMsg"), responseMsg.get("partnerOrderStatus"));
	}
	
	/**
	 * 中移动
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private static Map partnerQueryRequest() {
		// 查询标示码
		String queryCode = "directSingleQuery-1.0";
		// 字符集
		String inputCharset = "UTF-8";
		// 合作方编号
		String partner = "8619117161";
		// 合作方订单编号
		String outOrderId = "8803298132405020";
		// 签名方式
		String signType = "MD5";
		Map<String, String> params = new HashMap<>();
		params.put("queryCode", queryCode);
		params.put("inputCharset", inputCharset);
		params.put("partner", partner);
		params.put("outOrderId", outOrderId);
		params.put("signType", signType);
		// 按参数名排序
		ArrayList<String> paramNames = new ArrayList<>(params.keySet());
		Collections.sort(paramNames);
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
		if (SignType.MD5.name().equals("MD5")) {
			signSource.append("c1b45b8ddc8041b48440955934f916d7");
			try {
				logger.info("current inputCharset is {}", "UTF-8");
				calSign = DigestUtils.md5DigestAsHex(signSource.toString().getBytes("UTF-8"));
			} catch (UnsupportedEncodingException e) {
			}
		}
		// 合作方签名串
		String sign = calSign;
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("queryCode=").append(queryCode).append("&");
		stringBuilder.append("inputCharset=").append(inputCharset).append("&");
		stringBuilder.append("partner=").append(partner).append("&");
		stringBuilder.append("outOrderId=").append(outOrderId).append("&");
		stringBuilder.append("signType=").append(signType).append("&");
		stringBuilder.append("sign=").append(sign);
		String responseMsg = "";
		try {
			responseMsg = HttpClientUtils.send(Method.POST,
					"http://10.10.110.2:6084/gateway/query.htm", stringBuilder.toString(), true, "UTF-8", 6000);
			responseMsg = URLDecoder.decode(responseMsg, "UTF-8");
		} catch (Exception e) {
			logger.error("{}", e);
		}
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			Map map = objectMapper.readValue(responseMsg, Map.class);
			logger.info("转以后的响应结果：{}", map);
			return map;
		} catch (Exception e) {
			logger.error("", e);
		}
		return null;
	}
}
