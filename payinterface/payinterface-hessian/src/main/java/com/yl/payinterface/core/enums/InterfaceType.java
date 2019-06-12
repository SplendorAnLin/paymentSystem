package com.yl.payinterface.core.enums;

/**
 * 接口类型
 * 
 * @author 聚合支付有限公司
 * @since 2016年4月24日
 * @version V1.0.0
 */
public enum InterfaceType {
	B2C, B2B, AUTHPAY, REMIT, WXJSAPI, WXNATIVE, ALIPAY, WXMICROPAY, ALIPAYMICROPAY, RECEIVE, REAL_AUTH,
	/** 快捷支付 */
	QUICKPAY, 
	/** 支付宝服务窗 */
	ALIPAYJSAPI,
	/**QQ主扫*/
	QQNATIVE,
	/**QQH5支付*/
	QQH5,
	/**银联主扫*/
	UNIONPAYNATIVE,
	/** 京东H5 */
	JDH5;
}
