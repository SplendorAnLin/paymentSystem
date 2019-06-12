package com.yl.payinterface.core.handle.impl.wallet.cbhb120000.wechat.utils;

import java.util.*;

/**
 * 微信sign
 * 
 * @author 聚合支付有限公司
 * @since 2016年11月18日
 * @version V1.0.0
 */
public class WxSign {
	private static String characterEncoding = "UTF-8";

	@SuppressWarnings("rawtypes")
	public static String createSign(SortedMap<Object, Object> parameters, String key) {
		StringBuffer sb = new StringBuffer();
		Set es = parameters.entrySet();// 所有参与传参的参数按照accsii排序（升序）
		Iterator it = es.iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String k = (String) entry.getKey();
			Object v = entry.getValue();
			if (null != v && !"".equals(v) && !"sign".equals(k) && !"key".equals(k)) {
				sb.append(k + "=" + v + "&");
			}
		}
		sb.append("key=" + key);
		System.out.println(sb.toString());
		String sign = MD5Util.MD5Encode(sb.toString(), characterEncoding).toUpperCase();
		return sign;
	}
	
	@SuppressWarnings("rawtypes")
	public static String createYlSign(SortedMap<Object, Object> parameters, String key) {
		StringBuffer sb = new StringBuffer();
		Set es = parameters.entrySet();// 所有参与传参的参数按照accsii排序（升序）
		Iterator it = es.iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String k = (String) entry.getKey();
			Object v = entry.getValue();
			if (null != v && !"".equals(v) && !"sign".equals(k) && !"key".equals(k)) {
				sb.append(k + "=>" + v + "&");
			}
		}
		sb.append("key=" + key);
		System.out.println(sb.toString());
		String sign = MD5Util.MD5Encode(sb.toString(), characterEncoding).toUpperCase();
		return sign;
	}

	public static String getNonceStr() {
		Random random = new Random();
		return MD5Util.MD5Encode(String.valueOf(random.nextInt(10000)), "UTF-8");
	}

	public static String getTimeStamp() {
		return String.valueOf(System.currentTimeMillis() / 1000);
	}
}
