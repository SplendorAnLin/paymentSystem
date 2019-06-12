package com.yl.payinterface.front.utils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import com.lefu.commons.utils.lang.StringUtils;

/**
 * 工具类
 * 
 * @author 聚合支付有限公司
 * @since 2016年7月13日
 * @version V1.0.0
 */
public class ToolUtils {

	public static Map<String, Object> ObjectToMap(Object object) {
		Map<String, Object> map = new HashMap<>();
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(object.getClass());
			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
			for (PropertyDescriptor property : propertyDescriptors) {
				String key = property.getName();

				// 过滤class属性
				if (!key.equals("class")) {
					// 得到property对应的getter方法
					Method getter = property.getReadMethod();
					Object value = getter.invoke(object);
					if (value != null && StringUtils.notBlank(value.toString())) {
						map.put(key, value);
					}
				}

			}
		} catch (Exception e) {
		}
		return map;
	}
	
	public static Map<String, Object> retrieveParams(Map<String, String[]> requestMap) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		for (String key : requestMap.keySet()) {
			if (requestMap.get(key) != null) {
				resultMap.put(key, Array.get(requestMap.get(key), 0).toString().trim());
			}
		}
		return resultMap;
	}
	
}
