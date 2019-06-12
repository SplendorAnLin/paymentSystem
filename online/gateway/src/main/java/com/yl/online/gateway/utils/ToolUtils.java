package com.yl.online.gateway.utils;

import java.util.Map;

/**
 * 工具类
 * 
 * @author 聚合支付有限公司
 * @since 2017年4月28日
 * @version V1.0.0
 */
public class ToolUtils {

	@SuppressWarnings("rawtypes")
	public static String mapToString(Map map) {
		StringBuffer str = new StringBuffer();
		if (map == null || map.size() == 0) {
			return "";
		}
		for (Object key : map.keySet()) {
			str.append(key + "=" + map.get(key) + "&");
		}
		return str.toString().substring(0, str.length() - 1);
	}
}
