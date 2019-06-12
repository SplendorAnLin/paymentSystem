package com.yl.payinterface.core.handle.impl.quick.pengrui310101.utils;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import com.lefu.commons.utils.lang.JsonUtils;
import com.lefu.commons.utils.web.HttpClientUtils;
import net.sf.json.JSONObject;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 全时优服 综合工具
 * @author 聚合支付有限公司
 * @version V1.0.0
 * @since 2017/12/6
 */
public class PengRui310101Utils {

	private static Logger logger = LoggerFactory.getLogger(PengRui310101Utils.class);

	public static JSONObject execute(String key, String url, String api, String dataJson, String partnerNo, String orderId) {
		logger.info("订单号：{}, api:{}, 请求报文:{}", orderId, api, dataJson);
		String signKey = key.substring(16);
		String dataKey = key.substring(0,16);
		String sign = DigestUtils.shaHex(dataJson + signKey);
		try {
			Map<String, String> params = new HashMap<String, String>();
			params.put("encryptData", encode(PengRui310101AESUtils.encode(dataJson, dataKey)));
			params.put("signData", sign);
			params.put("orderId", orderId);
			params.put("partnerNo", partnerNo);
			params.put("ext", "EZPZ");
			logger.info("请求报文:{}", JsonUtils.toJsonString(params));
			String respInfo = HttpClientUtils.send(HttpClientUtils.Method.POST, url + api, params, false, "UTF-8", 300000);
			logger.info("返回报文密文:{}", respInfo);
			JSONObject res = JSONObject.fromObject(respInfo);
			String result = PengRui310101AESUtils.decode(decode(res.getString("encryptData")), dataKey);
			logger.info("返回报文明文:{}", result);
			String signature = res.getString("signature");
			String reSign = DigestUtils.shaHex(result + signKey);
			if (signature != null && signature.equals(reSign)) {
				return JSONObject.fromObject(result);
			}
		} catch (Exception e) {
			logger.info("订单号：{},api:{},下单报错:{}", orderId, api, e);
		}
		return null;
	}

	public static JSONObject head(Date now, String orderId, String txnCode, String partnerNo) {
		// 报文头组装
		JSONObject head = new JSONObject();
		head.put("version", "1.0.0");
		head.put("charset", "UTF-8");
		head.put("partnerNo", partnerNo); // AS01JFD0
		head.put("txnCode", txnCode); // 交易消费 302001  入网 301001
		head.put("orderId", orderId);
		head.put("reqDate", formatDate(now, "yyyyMMdd"));
		head.put("reqTime", formatDate(now, "yyyyMMddHHmmss"));
		return head;
	}

	public static String formatDate(Date date, String format) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		return dateFormat.format(date);
	}

	public static String getOrderIdByUUId() {
		int machineId = 6;
		int hashCodeV = UUID.randomUUID().toString().hashCode();
		if(hashCodeV < 0) {
			hashCodeV = - hashCodeV;
		}
		return machineId + String.format("%011d", hashCodeV);
	}

	public static String getOrderIdByUUIdTwo(){
		String order = "";
		order = getOrderIdByUUId();
		order += getOrderIdByUUId();
		return order;
	}

	/**
	 * 使用Base64加密算法加密字符串 return
	 */
	public static String encode(byte [] plainBytes) {
		byte[] b = plainBytes;
		org.apache.commons.codec.binary.Base64 base64 = new org.apache.commons.codec.binary.Base64();
		b = base64.encode(b);
		String s = new String(b, StandardCharsets.UTF_8);
		return s;
	}

	/**
	 * 使用Base64解密算法解密字符串 return
	 */
	public static byte [] decode(String encodeStr) {
		byte[] b = encodeStr.getBytes(StandardCharsets.UTF_8);
		org.apache.commons.codec.binary.Base64 base64 = new org.apache.commons.codec.binary.Base64();
		b = base64.decode(b);
		return b;
	}
}