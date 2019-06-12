package com.yl.pay.pos.bean;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * Title: Bean 工具类
 * Description: 
 * Copyright: Copyright (c)2011
 * Company: com.yl.pay
 * @author haitao.liu
 */
public class BeanUtils {

	public static void copyProperties(Object source, Object target) {
		Class s = source.getClass();
		Class t = target.getClass();

		Method[] ms = s.getDeclaredMethods();
		Method[] mt = t.getDeclaredMethods();

		HashMap<String, Method> targetMap = new HashMap<String, Method>();
		HashMap<String, Method> sourceMap = new HashMap<String, Method>();

		for (Method m : ms) {
			sourceMap.put(m.getName(), m);
		}
		for (Method m : mt) {
			targetMap.put(m.getName(), m);
		}
		Field[] ft = t.getDeclaredFields();
		Field[] fs = s.getDeclaredFields();
		for (Field f : fs) {
			String name = f.getName();
			String getMethodName = "get" + name.substring(0, 1).toUpperCase()
					+ name.substring(1);
			Method getMethod = sourceMap.get(getMethodName);
			if(getMethod==null){
				continue;
			}

			String setMethodName = "set" + name.substring(0, 1).toUpperCase()
					+ name.substring(1);
			Method setMethod = targetMap.get(setMethodName);
			if(setMethod==null){
				continue;
			}
			try {
				Object value = getMethod.invoke(source, null);
				setMethod.invoke(target, value);
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}
	
}
