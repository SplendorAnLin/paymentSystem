package com.yl.payinterface.core.handle.impl.wallet.mock;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.yl.payinterface.core.utils.CodeBuilder;
import com.yl.payinterface.core.utils.HttpUtils;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.lefu.commons.utils.lang.AmountUtils;
import com.lefu.commons.utils.lang.JsonUtils;
import com.lefu.commons.utils.lang.StringUtils;
import com.lefu.commons.utils.security.DigestUtils;
import com.yl.payinterface.core.handler.ChannelReverseHandler;
import com.yl.payinterface.core.handler.WalletPayHandler;
import com.yl.payinterface.core.model.InterfaceRequest;

/**
 * mock
 * 
 * @author 聚合支付有限公司
 * @since 2016年12月14日
 * @version V1.0.0
 */
@Service("mockWalletPayHandler")
public class MockWalletPayHandler implements WalletPayHandler,ChannelReverseHandler {

	private static Logger logger = LoggerFactory.getLogger(MockWalletPayHandler.class);

	private static final String QUERY_CODE = "YL-QUERY";
	private static final String API_CODE = "YL-PAY";
	private static final String INPUT_CHARSET = "UTF-8";
	private static final String SIGN_TYPE = "MD5";
	private static String payUrl = "http://pay.feiyijj.com/gateway/interface/pay.htm";
	private static String queryUrl = "http://pay.feiyijj.com/gateway/query.htm";
	private static String notifyUrl="http://m.bank-pay.com:8888/payinterface-front/pay/callback";
	private static String partnerNo = "C100009";
	private static String key = "713120d0cea3c08a751f1d8fb38aa301";
	
	@Override
	public Map<String, String> pay(Map<String, String> params) {
		Map<String, String> req = new HashMap<>();
		req.put("apiCode", API_CODE);
		req.put("inputCharset", INPUT_CHARSET);
		req.put("partner", partnerNo);
		String orderId = params.get("interfaceRequestID");
		req.put("outOrderId", orderId);
		req.put("amount", params.get("amount"));
		if ("YLZFTest-WX_NATIVE".equals(params.get("interfaceInfoCode"))) {
			req.put("returnParam", "YLZFTest-WX_NATIVE");
			req.put("payType", "WXNATIVE");
		}else if("YLZFTest-WX_ALIPAY".equals(params.get("interfaceInfoCode"))){
			req.put("returnParam", "YLZFTest-WX_ALIPAY");
			req.put("payType", "ALIPAY");
		}
		req.put("notifyUrl", notifyUrl);
		req.put("signType", SIGN_TYPE);
		req.put("product", params.get("productName"));
		String sign = "";
		ArrayList<String> paramNames = new ArrayList<>(req.keySet());
		Collections.sort(paramNames);
		Iterator<String> iterator = paramNames.iterator();
		StringBuffer signSource = new StringBuffer(50);
		while (iterator.hasNext()) {
			String paramName = iterator.next();
			if (StringUtils.notBlank(String.valueOf(req.get(paramName)))) {
				signSource.append(paramName).append("=").append(req.get(paramName));
				if (iterator.hasNext())
					signSource.append("&");
			}
		}
		signSource.append(key);
		Map<String, String> resParams = new HashMap<String, String>();
		try {
			sign = DigestUtils.md5DigestAsHex(signSource.toString().getBytes(INPUT_CHARSET)).toUpperCase();
			req.put("sign", sign);
			logger.info("发往生产支付通道信息：{}", JsonUtils.toJsonString(req));
			String resStr = HttpUtils.sendReq(payUrl, JsonUtils.toJsonString(req), "POST");
			logger.info("生产支付通道返回信息：{}", resStr);
			Map<String, String> resParam = JsonUtils.toObject(resStr, new TypeReference<Map<String, String>>() {
			});
			resParam.remove("sign");

			resParams.put("code_url", resParam.get("qrCodeUrl"));
			resParams.put("code_img_url", resParam.get("qrCodeImgUrl"));
		} catch (Exception e) {
			logger.info("生产下单响应明文  接口编号:[{}],订单号:[{}],下单异常", params.get("interfaceRequestID"), e);
			throw new RuntimeException(e);
		}
		resParams.put("interfaceInfoCode", params.get("interfaceInfoCode"));
		resParams.put("interfaceRequestID", params.get("interfaceRequestID"));
		resParams.put("gateway", "native");
		return resParams;
	}

	@Override
	public Map<String, String> query(Map<String, String> map) {
		Map<String,String> resParams = new HashMap<String, String>();
		//resParams.put(key, value);
		return resParams;
	}

	@Override
	public InterfaceRequest complete(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, String> reverse(Map<String, String> parfams) {
		// 测试方法，查询到的所有订单均为已支付状态
		String sign = "";
		Map<String, String> params = new HashMap<>();
		params.put("queryCode", QUERY_CODE);
		params.put("inputCharset", INPUT_CHARSET);
		params.put("partner", partnerNo);
		params.put("outOrderId", parfams.get("interfaceRequestID"));
		params.put("signType", SIGN_TYPE);
		ArrayList<String> paramNames = new ArrayList<>(params.keySet());
		Collections.sort(paramNames);
		Iterator<String> iterator = paramNames.iterator();
		StringBuffer signSource = new StringBuffer(50);
		while (iterator.hasNext()) {
			String paramName = iterator.next();
			if (StringUtils.notBlank(String.valueOf(params.get(paramName)))) {
				signSource.append(paramName).append("=").append(params.get(paramName));
				if (iterator.hasNext()) signSource.append("&");
			}
		}
		signSource.append(key);
		try {
			sign = DigestUtils.md5DigestAsHex(signSource.toString().getBytes(INPUT_CHARSET));
			params.put("sign", sign);
			String resStr = sendReq(JsonUtils.toJsonString(params), queryUrl, INPUT_CHARSET);
			params = JsonUtils.toObject(resStr, new TypeReference<Map<String, String>>(){});
			String serverSign = params.get("sign");
			params.remove("sign");
			paramNames = new ArrayList<>(params.keySet());
			Collections.sort(paramNames);
			signSource = new StringBuffer();
			iterator = paramNames.iterator();
			while (iterator.hasNext()) {
				String paramName = iterator.next();
				if (!"sign".equals(paramName) && StringUtils.notBlank(params.get(paramName))) {
					signSource.append(paramName).append("=").append(params.get(paramName));
					if (iterator.hasNext()){ 
						signSource.append("&");
					}
				}
			}
			String calSign = null;
			signSource.append(key);
			calSign = DigestUtils.md5DigestAsHex(signSource.toString().getBytes(INPUT_CHARSET));
			if (!serverSign.equals(calSign)) {
				return null;
			}else{
				Map<String,String> resParams = new HashMap<String, String>();
				resParams.put("interfaceRequestID", parfams.get("interfaceRequestID"));
				resParams.put("interfaceOrderID", params.get("orderCode"));
				resParams.put("transStatus", "SUCCESS");
				resParams.put("responseCode", params.get("responseCode"));
				resParams.put("responseMessage", "补单请求成功");
				resParams.put("amount", String.valueOf(AmountUtils.round(Double.parseDouble(params.get("amount")), 2, RoundingMode.HALF_UP)));
				resParams.put("interfaceFee", "0.2");
				resParams.put("tranStat", "SUCCESS");
				resParams.put("businessType", "SAILS");
				resParams.put("businessOrderID", parfams.get("businessOrderID"));
				resParams.put("businessFlowID", parfams.get("bussinessFlowID"));
				return resParams;
			}
		} catch (Exception e) {
			
		}
		return null;
	}
	
	public static String sendReq(String paramsStr, String url, String charset) throws Exception {
		java.net.HttpURLConnection urlConnection = null;
		BufferedOutputStream out;
		StringBuffer respContent = new StringBuffer();
		java.net.URL aURL = new java.net.URL(url);
		urlConnection = (java.net.HttpURLConnection) aURL.openConnection();
		urlConnection.setRequestMethod("POST");
		urlConnection.setDoInput(true);
		urlConnection.setDoOutput(true);
		urlConnection.setUseCaches(false);
		urlConnection.setRequestProperty("Connection", "close");
		urlConnection.setRequestProperty("Content-Length", String.valueOf(paramsStr.getBytes(charset).length));
		urlConnection.setRequestProperty("Content-Type", "text/html");
		urlConnection.setConnectTimeout(60000);
		urlConnection.setReadTimeout(60000);
		out = new BufferedOutputStream(urlConnection.getOutputStream());
		out.write(paramsStr.getBytes(charset));
		out.flush();
		out.close();
		int responseCode = urlConnection.getResponseCode();
		if (responseCode != 200) {
			throw new Exception("请求失败");
		}
		BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "utf-8"));
		String line;

		while ((line = reader.readLine()) != null) {
			respContent.append(line);
		}
		urlConnection.disconnect();
		return respContent.toString();
	}
}