package com.yl.online.trade.test.web.controller;

import java.io.PrintWriter;
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

/**
 * 合伙人查询请求控制器
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月22日
 * @version V1.0.0
 */
@Controller
@RequestMapping("/partner")
public class PartnerQueryRequestController {
	private static final Logger logger = LoggerFactory.getLogger(PartnerQueryRequestController.class);

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("query")
	public void receivePayRequest(HttpServletRequest request, HttpServletResponse response) {
		logger.info("**************** 模拟商户查询支付结果 ****************");
		// 查询标示码
		String queryCode = request.getParameter("queryCode");//"directSingleQuery-1.0";
		// 字符集
		String inputCharset = request.getParameter("inputCharset");//"UTF-8";
		// 合作方编号
		String partner = request.getParameter("partner");//"8619117161";
		// 合作方订单编号
		String outOrderId = request.getParameter("outOrderId");//"8803298132405020";
		// 签名方式
		String signType = request.getParameter("signType");//"MD5";
		// 签名串
		String sign = request.getParameter("sign");
		// 网关请求地址
		String gatewayUrl = request.getParameter("gatewayUrl");
		String key = request.getParameter("key");
		
		
		// 发起订单查询请求
		Map responseMsg = partnerQueryRequest(queryCode, inputCharset, partner, outOrderId, signType, sign, gatewayUrl);
		logger.info("支付系统响应信息：{}", responseMsg);
		try {
			PrintWriter writer = response.getWriter();
			response.setCharacterEncoding("utf-8");         
		    response.setContentType("text/html; charset=utf-8"); 
		    if (responseMsg != null && responseMsg.size() > 0) {
		    	// 响应码
		    	String responseCode = responseMsg.get("responseCode").toString();
		    	if (responseCode.equals("0000")) { // 处理成功
	    		// 进行数据验签, 按参数名排序
	    		ArrayList<String> paramNames = new ArrayList<>(responseMsg.keySet());
	    		Collections.sort(paramNames);
	    		// 组织待签名信息
	    		StringBuilder signSource = new StringBuilder();
	    		Iterator<String> iterator = paramNames.iterator();
	    		while (iterator.hasNext()) {
	    			String paramName = iterator.next();
	    			if (!"sign".equals(paramName) && StringUtils.notBlank(responseMsg.get(paramName).toString())) {
	    				signSource.append(paramName).append("=").append(responseMsg.get(paramName));
	    				if (iterator.hasNext()) signSource.append("&");
	    			}
	    		}

	    		// 计算签名
	    		String calSign = null;
	    		if (request.getParameter("signType").equals(responseMsg.get("signType"))) {
	    			signSource.append(key);
	    			try {
	    				logger.info("current inputCharset is {}, signSource is {}", inputCharset, signSource);
	    				calSign = DigestUtils.md5DigestAsHex(signSource.toString().getBytes(inputCharset));
	    			} catch (UnsupportedEncodingException e) {
	    				logger.error("{}", e);
	    			}
	    		}
	    		if (!responseMsg.get("sign").equals(calSign)) {
	    			logger.info("sign check error\\r\nsign={}\r\ncalSign={}", responseMsg.get("sign"), calSign);
	    			logger.error("验签出错...");
	    		}
		    	// 响应到客户端
				writer.print("<html>" + 
								"<head>" + 
								"<meta http-equiv=\"Content-Type\" content=\"text/html; charset=ISO-8859-1\">" + 
								"<title>Insert title here</title>" + 
								"</head>" + 
								"<body>" + 
									"<h1>订单查询结果</h1>" +
									"响应码：" + responseCode + "<br>" +
									"响应描述：" + responseMsg.get("responseMsg") + "<br>" +
									"商户编码："+ responseMsg.get("partner") + "<br>" +
									"订单状态："+ responseMsg.get("partnerOrderStatus") + "<br>" +
									"实付金额："+ responseMsg.get("paidAmount") + "<br>" +
									"支付系统订单号："+ responseMsg.get("orderCode") + "<br>" +
									"签名方式 ："+ responseMsg.get("signType") + "<br>" +
									"签名串："+ responseMsg.get("sign") + "<br>" +
								"</body>" + 
								"</html>");
		    	} else { // 响应失败
		    		// 响应到客户端
					writer.print("<html>" + 
									"<head>" + 
									"<meta http-equiv=\"Content-Type\" content=\"text/html; charset=ISO-8859-1\">" + 
									"<title>Insert title here</title>" + 
									"</head>" + 
									"<body>" + 
										"<h1>订单查询结果</h1>" +
										"响应码：" + responseCode + "<br>" +
										"响应描述：" + responseMsg.get("responseMsg") + "<br>" +
									"</body>" + 
									"</html>");
		    	}
		    } else {
		    	writer.print("<html>" + 
						"<head>" + 
						"<meta http-equiv=\"Content-Type\" content=\"text/html; charset=ISO-8859-1\">" + 
						"<title>Insert title here</title>" + 
						"</head>" + 
						"<body>" + 
							"<h1>订单查询出现异常，无法获取结果</h1>" +
						"</body>" + 
						"</html>");
		    }
		} catch (Exception e) {
			logger.error("{}", e);
		}
		
	}


	@SuppressWarnings("rawtypes")
	private Map partnerQueryRequest(String queryCode, String inputCharset, String partner, String outOrderId, String signType, String sign, String gatewayUrl) {
		
		Map<String, String> params = new HashMap<>();
		params.put("queryCode", queryCode);
		params.put("inputCharset", inputCharset);
		params.put("partner", partner);
		params.put("outOrderId", outOrderId);
		params.put("signType", signType);

		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("queryCode=").append(queryCode).append("&");
		stringBuilder.append("inputCharset=").append(inputCharset).append("&");
		stringBuilder.append("partner=").append(partner).append("&");
		stringBuilder.append("outOrderId=").append(outOrderId).append("&");
		stringBuilder.append("signType=").append(signType).append("&");
		stringBuilder.append("sign=").append(sign);
		String responseMsg = "";
		try {
			responseMsg = HttpClientUtils.send(Method.POST, gatewayUrl, stringBuilder.toString(), true, "UTF-8", 6000);
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
