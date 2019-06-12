package com.yl.client.front.common;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ConverMap {
	/**
	 * 将一个 JavaBean 对象转化为一个 Map
	 * 
	 * @param bean
	 *            要转化的JavaBean 对象
	 * @return 转化出来的 Map 对象
	 * @throws IntrospectionException
	 *             如果分析类属性失败
	 * @throws IllegalAccessException
	 *             如果实例化 JavaBean 失败
	 * @throws InvocationTargetException
	 *             如果调用属性的 setter 方法失败
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map convertBean(Object bean)
			throws IntrospectionException, IllegalAccessException, InvocationTargetException {
		Class type = bean.getClass();
		Map returnMap = new HashMap();
		BeanInfo beanInfo = Introspector.getBeanInfo(type);
		PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
		for (int i = 0; i < propertyDescriptors.length; i++) {
			PropertyDescriptor descriptor = propertyDescriptors[i];
			String propertyName = descriptor.getName();
			if (!propertyName.equals("class")) {
				Method readMethod = descriptor.getReadMethod();
				Object result = readMethod.invoke(bean, new Object[0]);
				if (result != null) {
					returnMap.put(propertyName, result);
				} else {
					returnMap.put(propertyName, null);
				}
			}
		}
		return returnMap;
	}
	
	public static Map convertTime(Object bean) {
		Class type = bean.getClass();
		Map returnMap = new HashMap();
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(type);
			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
			for (int i = 0; i < propertyDescriptors.length; i++) {
				PropertyDescriptor descriptor = propertyDescriptors[i];
				String propertyName = descriptor.getName();
				if (!propertyName.equals("class")) {
					Method readMethod = descriptor.getReadMethod();
					Object result=readMethod.invoke(bean, new Object[0]);
					if( result instanceof Date){
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						result=sdf.format(result);
					}
					if (result != null) {
						returnMap.put(propertyName, result);
					} else {
						returnMap.put(propertyName, null);
					}
				}
			}
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException|IntrospectionException e ) {
			e.printStackTrace();
		}
		return returnMap;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map convertBean(Object bean,boolean isDate)
			throws IntrospectionException, IllegalAccessException, InvocationTargetException {
		Class type = bean.getClass();
		Map returnMap = new HashMap();
		BeanInfo beanInfo = Introspector.getBeanInfo(type);
		PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
		for (int i = 0; i < propertyDescriptors.length; i++) {
			PropertyDescriptor descriptor = propertyDescriptors[i];
			String propertyName = descriptor.getName();
			if (!propertyName.equals("class")) {
				Method readMethod = descriptor.getReadMethod();
				Object result = readMethod.invoke(bean, new Object[0]);
				if( result instanceof Date){
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					result=sdf.format(result);
				}
				if (result != null) {
					returnMap.put(propertyName, result);
				} else {
					returnMap.put(propertyName, null);
				}
			}
		}
		return returnMap;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List<Map<String, Object>> convertListBean(List<Map<String, Object>> bean,boolean isDate) throws IllegalAccessException, InvocationTargetException, IntrospectionException{
		List<Map<String, Object>> list=new ArrayList<>();
		for (Object info : (List)bean) {
			list.add(convertBean(info,true));
		}
		return list;
	}

	/**
	 * 将一个 Map 对象转化为一个 JavaBean
	 * 
	 * @param type
	 *            要转化的类型
	 * @param map
	 *            包含属性值的 map
	 * @return 转化出来的 JavaBean 对象
	 * @throws IntrospectionException
	 *             如果分析类属性失败
	 * @throws IllegalAccessException
	 *             如果实例化 JavaBean 失败
	 * @throws InstantiationException
	 *             如果实例化 JavaBean 失败
	 * @throws InvocationTargetException
	 *             如果调用属性的 setter 方法失败
	 */
	@SuppressWarnings("rawtypes")
	public static Object convertMap(Class type, Map map)
			throws IntrospectionException, IllegalAccessException, InstantiationException, InvocationTargetException {
		BeanInfo beanInfo = Introspector.getBeanInfo(type); // 获取类属性
		Object obj = type.newInstance(); // 创建 JavaBean 对象

		// 给 JavaBean 对象的属性赋值
		PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
		for (int i = 0; i < propertyDescriptors.length; i++) {
			PropertyDescriptor descriptor = propertyDescriptors[i];
			String propertyName = descriptor.getName();

			if (map.containsKey(propertyName)) {
				// 下面一句可以 try 起来，这样当一个属性赋值失败的时候就不会影响其他属性赋值。
				Object value = map.get(propertyName);

				Object[] args = new Object[1];
				args[0] = value;

				descriptor.getWriteMethod().invoke(obj, args);
			}
		}
		return obj;
	}
	/**
	 * 参数转换
	 */
	public static Map<String, Object> retrieveParams(Map<String, String[]> requestMap) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		for (String key : requestMap.keySet()) {
			String[] value = requestMap.get(key);
			if (value != null) {
				resultMap.put(key, Array.get(value, 0).toString().trim());
			}
		}
		return resultMap;
	}
	/**
	 * requestMap页面map转化常规MAP
	 * @param requestMap
	 * @param isEmpty
	 * @return
	 */
	public static Map<String, Object> retrieve(Map<String, String[]> requestMap,boolean isEmpty) {
		Map<String, Object> resultMap1 = new HashMap<String, Object>();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		for (String key : requestMap.keySet()) {
			String[] value = requestMap.get(key);
			if (value != null) {
				resultMap1.put(key, Array.get(value, 0).toString().trim());
			}
		}
		if (isEmpty) {
			Iterator iter = resultMap1.entrySet().iterator();
			while (iter.hasNext()) {
				Map.Entry entry = (Map.Entry) iter.next();
				Object key = entry.getKey();
				Object val = entry.getValue();
				if (!val.equals("")&&val!=null) {
					resultMap.put(key.toString(), val);
				}
			}
		}
		return resultMap;
	}
	
	/**
	 * requestMap页面map转化常规MAP
	 * @param m
	 * @return
	 */
	public static Map<String, Object> retrieve(Map m){
		// 参数Map
		Map properties = m;
		// 返回值Map
		Map returnMap = new HashMap();
		Iterator entries = properties.entrySet().iterator();
		Map.Entry entry;
		String name = "";
		String value = "";
		while (entries.hasNext()) {
			entry = (Map.Entry) entries.next();
			name = (String) entry.getKey();
			Object valueObj = entry.getValue();
			if(null == valueObj){
				value = "";
			}else if(valueObj instanceof String[]){
				String[] values = (String[])valueObj;
				for(int i=0;i<values.length;i++){
					value = values[i] + ",";
				}
				value = value.substring(0, value.length()-1);
			}else{
				value = valueObj.toString();
			}
			returnMap.put(name, value);
		}
		return returnMap;
	}
	
	
	@SuppressWarnings("unchecked")
	public static List<Map<String, Object>> convertListMap(List<Object> bean,boolean isDate) throws IllegalAccessException, InvocationTargetException, IntrospectionException {
		if (bean.size()>0) {
			List<Map<String, Object>> list=new ArrayList<>();
			for (Object object : bean) {
				if (isDate) {
					list.add(convertBean(object,isDate));
				}else {
					list.add(convertBean(object));
				}
			}
			return list;
		}
		return null;
	}
	@SuppressWarnings("unchecked")
	public static List<Map<String, Object>> convertListMapToDate(List<Map<String, Object>> bean,boolean isDate) throws IllegalAccessException, InvocationTargetException, IntrospectionException {
		if (bean.size()>0) {
			List<Map<String, Object>> list=new ArrayList<>();
			for (Object object : bean) {
				if (isDate) {
					list.add(convertBean(object,isDate));
				}else {
					list.add(convertBean(object));
				}
			}
			return list;
		}
		return null;
	}
	
	public static Map<String,Object> convertMapDateToStr(Map<String,Object> map) {
		for (String param : map.keySet()) {
			Object obj=map.get(param);
			if (obj instanceof Double||obj instanceof Integer) {
				map.put(param, obj+"");
			}else if (obj==null) {
				map.put(param,"");
			}
		}
		return map;
	}
}
