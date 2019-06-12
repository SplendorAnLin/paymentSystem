package com.yl.dpay.core.enums;

/**
 * 请求类型
 * 
 * @author 聚合支付有限公司
 * @since 2016年5月12日
 * @version V1.0.0
 */
public enum RequestType {
	/**
	 * 商户后台代付
	 */
	PAGE,
	/**
	 * 接口代付
	 */
	INTERFACE,
	/**
	 * 商户提现
	 */
	DRAWCASH,
	/**
	 * 自动结算
	 */
	AUTO_DRAWCASH
}
