/******************************************************************************
 *                                                                             
 *                      Woodare PROPRIETARY INFORMATION                        
 *                                                                             
 *          The information contained herein is proprietary to Woodare         
 *           and shall not be reproduced or disclosed in whole or in part      
 *                    or used for any design or manufacture                    
 *              without direct written authorization from FengDa.              
 *                                                                             
 *            Copyright (c) 2013 by Woodare.  All rights reserved.             
 *                                                                             
 *****************************************************************************/
package com.yl.payinterface.core.handle.impl.auth.mipay.utils;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * ClassName: JsonUtils
 * 
 * @description
 * @author framework
 * @Date 2013-4-1
 * 
 */
public class JsonUtils {

	private static Map<Class<?>, Object> SERIALIZER;
	private static Map<Class<?>, Object> DESERIALIZER;

	static {
		DESERIALIZER = new HashMap<Class<?>, Object>();
		DESERIALIZER.put(Date.class, new JsonDeserializer<Date>() {
			@Override
			public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

				return json == null ? null : new Date(json.getAsLong());
			}
		});
		DESERIALIZER.put(java.sql.Date.class, new JsonDeserializer<Date>() {
			@Override
			public java.sql.Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

				return json == null ? null : new java.sql.Date(json.getAsLong());
			}
		});

		SERIALIZER = new HashMap<Class<?>, Object>();
		SERIALIZER.put(Date.class, new JsonSerializer<Date>() {
			@Override
			public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {
				return src == null ? null : new JsonPrimitive(src.getTime());
			}
		});
		SERIALIZER.put(java.sql.Date.class, new JsonSerializer<java.sql.Date>() {
			@Override
			public JsonElement serialize(java.sql.Date src, Type typeOfSrc, JsonSerializationContext context) {
				return src == null ? null : new JsonPrimitive(src.getTime());
			}
		});
	}

	public static <T> T fromJson(String json, Class<T> classOfT) {
		return fromJson(json, classOfT, DESERIALIZER);
	}

	public static <T> T fromJson(String json, Type type) {
		return fromJson(json, type, DESERIALIZER);
	}

	public static <T> T fromJson(String json, Type type, Map<Class<?>, Object> adapters) {
		if (json == null || json.equals("")) {
			return null;
		}
		Gson gson = getGson(adapters);
		return gson.fromJson(json, type);
	}

	public static <T> T fromJson(String json, Class<T> classOfT, Map<Class<?>, Object> adapters) {
		Gson gson = getGson(adapters);
		return gson.fromJson(json, classOfT);
	}

	public static String toJson(Object jsonElement) {
		return toJson(jsonElement, SERIALIZER);
	}

	public static String toJson(Object jsonElement, Type type) {
		Gson gson = getGson(SERIALIZER);
		return gson.toJson(jsonElement, type);
	}

	public static String toJson(Object jsonElement, Map<Class<?>, Object> adapters) {
		Gson gson = getGson(adapters);
		return gson.toJson(jsonElement);
	}

	public static Gson getGson(Map<Class<?>, Object> adapters) {
		Gson gson = null;
		if (adapters != null) {
			GsonBuilder gsonBuilder = new GsonBuilder();
			for (Map.Entry<Class<?>, Object> entry : adapters.entrySet()) {
				gsonBuilder.registerTypeAdapter(entry.getKey(), entry.getValue());
			}
			gson = gsonBuilder.create();
		} else {
			gson = new GsonBuilder().create(); // .serializeNulls()
		}
		return gson;
	}

}
