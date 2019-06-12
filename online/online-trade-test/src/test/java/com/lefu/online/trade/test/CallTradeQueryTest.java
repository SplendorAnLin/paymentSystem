package com.lefu.online.trade.test;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.Map;

import org.codehaus.jackson.type.TypeReference;

import com.lefu.commons.utils.lang.JsonUtils;
import com.lefu.commons.utils.lang.StringUtils;
import com.lefu.commons.utils.security.DigestUtils;
import com.lefu.commons.utils.web.HttpClientUtils;
import com.lefu.commons.utils.web.HttpClientUtils.Method;

/**
 * 访问订单查询测试
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月19日
 * @version V1.0.0
 */
public class CallTradeQueryTest {
	private static final String SIGN_KEY = "1da3dd9e82524e80a764cd615f37112d";
	
	public static void main(String[] args) {
//		callPayinterface();
		callTradeQuery();
	}
	
	public static void callTradeQuery() {
		String terminalCode = "60895548";
		String interfaceRequestID = "20140527172800";
		String signMsg = DigestUtils.hmacSign(DigestUtils.hmacSign(
				StringUtils.concatToSB(terminalCode, interfaceRequestID).toString(), ""), SIGN_KEY);
		String params = StringUtils.concatToSB("terminalCode=", terminalCode, "&", 
				"interfaceRequestID=", interfaceRequestID, "&", "signMsg=", signMsg).toString();
		try {
			String responseMsg = HttpClientUtils.send(Method.POST, "http://10.10.110.2:6084/online-trade-query/willingPay/orderPay.htm", params, true, "UTF-8", 3000);
			responseMsg = URLDecoder.decode(responseMsg, "UTF-8");
			Map<String, Object> responseMap = JsonUtils.toObject(responseMsg, new TypeReference<Map<String, Object>>() {});
			System.out.println(responseMap);
			signMsg = responseMap.get("signMsg").toString();
			responseMap.put("signMsg", null);
			System.out.println(DigestUtils.hmacVerify(DigestUtils.hmacSign(JsonUtils.toJsonString(responseMap), ""), SIGN_KEY, signMsg));
		} catch (IOException e) {
			System.out.println(e);
		}
	}
	
	public static void callPayinterface() {
		String tradeResult = "SUCCESS";
		String amount = "0.01";
		String orderId = "20140526691200";
		String posId = "111";
		String plainText = StringUtils.concatToSB(tradeResult, String.valueOf(amount), orderId, posId).toString();
		String signMsg = DigestUtils.hmacSign(DigestUtils.hmacSign(plainText, ""), SIGN_KEY);
		String params = StringUtils.concatToSB("tradeResult=", tradeResult, 
				"&", "amount=", amount, "&", "orderId=", orderId, "&", "posId=", posId, "&", "signMsg=", signMsg).toString();
		try {
			String responseMsg = HttpClientUtils.send(Method.POST, "http://10.10.110.2:6084/payinterface-front/OFFLINE100001PayNotice/completeTrade.htm", params, true, "UTF-8", 3000);
			responseMsg = URLDecoder.decode(responseMsg, "UTF-8");
			System.out.println(responseMsg);
		} catch (IOException e) {
			System.out.println(e);
		}
	}

}
