package com.yl.agent.utils;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.List;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.JavaType;

/**
 * Json工具类
 * 
 * @author 聚合支付有限公司
 * @since 2016年7月13日
 * @version V1.0.0
 */
public class JsonUtils {

	private static final ObjectMapper om = new ObjectMapper();

	// java对象转换成json字符串
	public static String toJson(Object o) throws JsonGenerationException, JsonMappingException, IOException {
		Writer w = new StringWriter();
		String json = null;
		om.writeValue(w, o);
		json = w.toString();
		w.close();
		return json;
	}

	public static <T> T toObject(String json, Class<T> c) throws JsonParseException, JsonMappingException, IOException {
		T o = null;
		o = om.readValue(json, c);
		return o;
	}

	/**
	 * 获取泛型的Collection Type
	 * @param collectionClass 泛型的Collection
	 * @param elementClasses 元素类
	 * @return JavaType Java类型
	 * @since 1.0
	 */
	public static JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {
		return om.getTypeFactory().constructParametricType(collectionClass, elementClasses);
	}

	@SuppressWarnings("unchecked")
	public static <T> List<T> toCollectionObject(String json, Class<?> collectionClass, Class<T> c) throws Exception {
		JavaType javaType = getCollectionType(collectionClass, c);
		List<T> list = (List<T>) om.readValue(json, javaType);
		return list;

	}

}
