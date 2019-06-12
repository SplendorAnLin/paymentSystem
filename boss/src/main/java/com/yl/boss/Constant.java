package com.yl.boss;

import java.util.Map;
import net.sf.json.JSONObject;

/**
 * 常量
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月24日
 * @version V1.0.0
 */
public class Constant {
	
	public static final String CUSTOMERSERVICE = "boss";
	public static final String BOOLEAN_TRUE = "TRUE";
	public static final String BOOLEAN_FALSE = "FALSE";		
	public static final String PROPERTIES_SYS = "system";	
	
	
	
	public static final String SESSION_AUTH = "auth";
	public static final String SESSION_CODE = "code";		
	public static final String SESSION_CODE_STATUS = "codestatus";
	
	//存放数据字典的Cache Region名称和存储Key
	public static final String DICT_REGION = "pos_dict_cache";
	public static final String CACHE_DICTS = "dicts";
	public static final String DEFAULT_REGION = "default";

	//临时
	public static final String NOPERMIT="noPermit";  //返回无操作此权限
	
	public static final String OPERATOR_RESOUSE = "boss.operator.resource";
	
	public static final int OPERATOR_RESOURCE_CACHE_DB = 1;
	public static final int DICTS_RESOURCE_DB = 14;
	public static final int IP_DB=12;
	
	public static final String SMS_AUDIT_TYPE0 = "【千付宝】您的验证码是：[%s]，您正在进行付款审核操作，请勿泄露。";
	public static final String SMS_OPEN_TYPE1 = "【千付宝】您的验证码是：[%s]，您正在开通手机验证码付款审核功能，请勿泄露，感谢您的使用。";
	public static final String SMS_CLOSE_TYPE2 = "【千付宝】您的验证码是：[%s]，您正在关闭手机验证码付款审核功能，请勿泄露，感谢您的使用。";
	
	public static final String SMS_CUST_OPEN = "【千付宝】尊敬的[%s]，您的商户已开通，请登录pay.feiyijj.com/customer进行自助服务，"
			+ "您的登录帐号是：[%s]，密码是：[%s]，代付审核密码是：[%s] ";
	public static final String SMS_QUICK_CUST_OPEN = "【千付宝】尊敬的：[%s]您好，您的商户已开通， 您的登录帐号是：[%s]";
	public static final String SMS_QUICK_AUDIT_PASS = "【千付宝】尊敬的：[%s]您好，商户已通过审核， 您的登录帐号是：[%s]，提现密码是：[%s]";
	public static final String SMS_AGENT_OPEN = "【千付宝】尊敬的：[%s]您好，您的服务商已开通，请登录pay.feiyijj.com/agent进行自助服务，"
			+ "您的登录帐号是：[%s]，密码是：[%s]，提现密码是：[%s]。";
	public static final String SMS_DF_OPEN ="【千付宝】尊敬的：[%s]您好，您的帐号代付已开通，提现密码是：[%s]，请妥善保管，切勿泄露。";
	public static final String AGENT_RESETPASSWORD = "【千付宝】您登录密码已重置为：[%s]请妥善保管，切勿泄露。";
	public static final String SMS_OPER_OPEN="【千付宝】尊敬的：[%s]您好，您的登陆密码是：[%s]，请登录pay.feiyijj.com/boss进行操作!";
	public static final String BOSS_AUDIT_REFUSE = "【千付宝】尊敬的服务商：您账户下编号为[%s]商户审核未能通过，原因：[%s]，请尽快修改后再次提交，以便下次审核。";
	public static final String AGENT_AUDIT_URGING = "【千付宝】尊敬的服务商：您账户下有等待审核的商户，请尽快完成审核。";
	public static final String AGENT_AGENT_REFUSE = "【千付宝】尊敬的商户：您账户编号为[%s]未能审核通过，原因：[%s]，请认真修改后重新提交，以便下次审核顺利通过！";
	public static final String LockPrefix0 = "DPAY-LOCK:";
	public static final String LockPrefix1 = "BOSS-LOCK:";
	public static final String LockPrefix2 = "RECON-LOCK:";
	
	//字典管理
	public static final String DICTIONARY = "DICTIONARY.";
	//字典类型
	public static final String DICTIONARY_TYPE = "DICTIONARY_TYPE.";
	//银行类型
	public static final String INTERFACE_PROVIDER = "INTERFACE_PROVIDER";
	//账户类型
	public static final String ACCOUNT_TYPE = "ACCOUNT_TYPE";
	//卡类型
	public static final String CARD_TYPE = "CARD_TYPE";
	//认证类型
	public static final String CER_TYPE = "CER_TYPE";
	//安卓版本
	public static final String ANDROID_VERSION="app.version.android";
	//ios版本
	public static final String IOS_VERSION="app.version.ios";
	//ios_store版本
	public static final String IOS_STORE_VERSION="app.version.iosStore";
	//字典
	public static Map<String,JSONObject> DICTS;
}