package com.yl.payinterface.core;

/**
 * 支付接口全局常量
 * 
 * @author 聚合支付有限公司
 * @since 2016年11月13日
 * @version V1.0.0
 */
public class C {
	public static final String APP_NAME = "payinterface-core";
	/** Hessian密钥 */
	public static final String HESSIAN_KEY = "payinterface";
	/** MQ分隔符*/
	public static final String DELIMITER = ":";
	
	public static final String SMS_BIND_CARD_VERIFYCODE = "不要告诉任何人！付款验证码[%s]。尾号[%s]的银行卡付款[%s]元。";
	
	public static final String SMS_BIND_CARD_VERIFYCODE_CORE_KEY = "SMS_BIND_CARD_CORE_VERIFYCODE";
	
	/** 快捷支付结算类型--费率 */
	public final static String SETTLE_TYPE_FEE = "FEE";
	/** 快捷支付结算类型--金额 */
	public final static String SETTLE_TYPE_AMOUNT = "AMOUNT";
}
