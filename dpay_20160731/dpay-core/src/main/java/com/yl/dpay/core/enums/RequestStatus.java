package com.yl.dpay.core.enums;

/**
 * 请求状态
 * 
 * @author 聚合支付有限公司
 * @since 2016年5月12日
 * @version V1.0.0
 */
public enum RequestStatus {
	/**
	 * 等待
	 */
	WAIT,
	/**
	 * 未知
	 */
	UNKNOWN,
	/**
	 * 处理中
	 */
	HANDLEDING,
	/**
	 * 代付成功
	 */
	SUCCESS,
	/**
	 * 代付失败
	 */
	FAILED;
}
