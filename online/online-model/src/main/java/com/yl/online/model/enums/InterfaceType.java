package com.yl.online.model.enums;

/**
 * 接口类型
 *
 * @author 聚合支付有限公司
 * @since 2016年7月13日
 * @version V1.0.0
 */
public enum InterfaceType {
	B2C, B2B, AUTHPAY, REMIT, WXJSAPI, WXNATIVE, ALIPAY, RECEIVE, WXMICROPAY, ALIPAYMICROPAY, ALIPAYJSAPI,
	/** 快捷支付 */
	QUICKPAY,
	/**QQ主扫*/
	QQNATIVE,
	/**QQH5支付*/
	QQH5,
	/**银联主扫*/
	UNIONPAYNATIVE,
	/**京东H5*/
	JDH5;
}
