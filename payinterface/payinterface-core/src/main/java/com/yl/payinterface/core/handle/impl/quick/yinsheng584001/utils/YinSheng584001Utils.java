package com.yl.payinterface.core.handle.impl.quick.yinsheng584001.utils;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;

import com.yl.payinterface.core.utils.MD5Util;


/**
 * @ClassName YinSheng584001Utils
 * @Description TODO(这里用一句话描述这个类的作用)
 * @author Administrator
 * @date 2017年12月20日 下午4:49:08
 */
public class YinSheng584001Utils {
	
	@SuppressWarnings("rawtypes")
	public static String createSign(SortedMap<String, String> parameters, String key) {
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
		String sign = MD5Util.getSHA256Str(sb.toString(), "UTF-8");
		return sign;
	}
}
