package com.yl.client.front;

import java.util.Map;

import net.sf.json.JSONObject;

/**
 * 常量
 * 
 * @author 聚合支付有限公司
 * @since 2017年4月28日
 * @version V1.0.0
 */
public class Constant {
	
	public static final String OPERATOR_RESOUSE = "customer.operator.resource.login";
	
	public static final int OPERATOR_RESOURCE_CACHE_DB = 14;
	//字典类型
	public static final String DICTIONARY_TYPE = "DICTIONARY_TYPE.";
	//字典
	public static final String DICTIONARY = "DICTIONARY.";
	//安卓版本
	public static final String ANDROID_VERSION="app.version.android";
	//ios版本
	public static final String IOS_VERSION="app.version.ios";
	public static final String IOS_STORE_VERSION="app.version.iosStore";
	
	public static final int IP_DB=12;
	//字典
	public static Map<String,JSONObject> DICTS;
	public static final String SMS_VERIFY = "【聚合支付】您的验证码是：[%s]。";
}
