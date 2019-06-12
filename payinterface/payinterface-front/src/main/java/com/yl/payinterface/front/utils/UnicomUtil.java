/**
 * 
 */
package com.yl.payinterface.front.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 联通沃支付工具类
 * 
 * @author 聚合支付有限公司
 * @since 2016年3月17日
 * @version V1.0.0
 */
public class UnicomUtil {
	/** 
	 * 得到签名源数据
	 * @param sArray 要签名的参数数组
	 * @param key 签名密码
	 * @return 签名源字符串
	 */
	public static String getSignSourMsg(Map<String, Object> params, String key) {
		params = doFilterParam(params);
		String signSource = createLinkString(params);
		if (key != null && key.length() > 0) {//证书签名时签名密码为空
			signSource = signSource + "|key=" + key;
		}
		return signSource;

	}
	
	
	/** 
	 * 处理数组中的null值和除去非签名参数
	 * @param sArray 签名参数组
	 * @return 去掉空值与签名参数后的新签名参数组
	 */
	private static Map<String, Object> doFilterParam(Map<String, Object> params) {
		List<String> keys = new ArrayList<String>(params.keySet());
		Map<String, Object> newParam = new HashMap<String, Object>();

		for (int i = 0; i < keys.size(); i++) {
			String key = keys.get(i);
			String value = (String) params.get(key);
			
			if ("hmac".equalsIgnoreCase(key) || "signMsg".equalsIgnoreCase(key)
					|| "cert".equalsIgnoreCase(key)) {
				continue;
			}
			if (value == null||value.length()==0) {
				continue;
			}
			newParam.put(key, value);
		}

		return newParam;
	}
	
	/** 
	 * 把数组所有元素排序，并按照“参数=参数值”的模式用“|”字符拼接成字符串
	 * @param params 需要排序并参与字符拼接的参数组
	 * @return 拼接后字符串
	 */
	private static String createLinkString(Map<String, Object> params) {
		List<String> keys = new ArrayList<String>(params.keySet());
		Collections.sort(keys);

		StringBuffer prestr = new StringBuffer("");

		for (int i = 0; i < keys.size(); i++) {
			String key = keys.get(i);
			String value = (String) params.get(key);

			if (i == keys.size() - 1) {// 拼接时，不包括最后一个|字符
				prestr.append(key).append("=").append(value);
			} else {
				prestr.append(key).append("=").append(value).append("|");
			}
		}
		return prestr.toString();
	}
	
	@SuppressWarnings("rawtypes")
	public static String generateQueryMsg(Map<String,Object> map,String charset) throws UnsupportedEncodingException {
		StringBuilder sb = new StringBuilder();
		Iterator it = map.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pairs = (Map.Entry) it.next();
			sb.append(pairs.getKey()).append("=").append(
					URLEncoder.encode(pairs.getValue().toString(), charset)).append("&");
		}
		if (sb.length() > 0)// delete last & char
		{
			sb.setLength(sb.length() - 1);
		}
		return sb.toString();
	}
	
	/**
	 * 封装查询结果信息
	 * @param respondData
	 * @return
	 */
	public static Map<String, Object> queryRespondMap(String respondData) {
		//拆分时进行排序，以便于读取数据
		Map<String, Object> resMap = new LinkedHashMap<String, Object>();
		for (String str : respondData.split("\\n")) {
			resMap.put(str.substring(0, str.indexOf("=")), str.substring(str.indexOf("=") + 1));
		}
		return resMap;
	}
	
}
