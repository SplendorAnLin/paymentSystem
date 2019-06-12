package com.yl.boss.constant;

import java.util.HashMap;
import java.util.Map;

public class CategoryConstant {

	public static Map<String,String> gategoryMap = new HashMap<>();
	
	static{
		gategoryMap.put("1", "餐娱");
		gategoryMap.put("2", "批发");
		gategoryMap.put("3", "商超");
		gategoryMap.put("4", "公益");
		gategoryMap.put("5", "一般");
	}
}
