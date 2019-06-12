package com.yl.boss.api.enums;

/**
 * 交易订单状态
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月16日
 * @version V1.0.0
 */
public enum OrderStatus {
	/** 等待付款 */
	WAIT_PAY,
	/** 成功 */
	SUCCESS,
	/** 关闭 */
	CLOSED,
	/** 失败 */
	FAILED;
}