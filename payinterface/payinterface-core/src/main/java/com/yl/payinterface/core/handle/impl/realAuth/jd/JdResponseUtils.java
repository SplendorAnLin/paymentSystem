package com.yl.payinterface.core.handle.impl.realAuth.jd;

import java.util.Map;

import com.lefu.commons.utils.lang.JsonUtils;

public class JdResponseUtils {

	@SuppressWarnings("unchecked")
	public static Map<String, Object> getReturnInfo(String info) {
		Map<String, Object> map = JsonUtils.toObject(info, Map.class);
		return map;
	}

	public static void main(String[] args) {
		try {
			String info = "{\"code\":\"10000\",\"charge\":false,\"msg\":\"查询成功\",\"result\":{\"success\":\"true\",\"respMsg\":\"验证通过\",\"comfrom\":\"jd_query\",\"respCode\":\"000000\",\"serialNo\":\"201708102356556654174770178331\"}}";
			Map map = JdResponseUtils.getReturnInfo(info);
			System.out.println(((Map)map.get("result")).get("success"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
