package com.yl.customer.enums;

/**
 * 订单退款状态枚举
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月15日
 * @version V1.0.0
 */
public enum RefundStatus {
	/** 未退款 */
	NOT_REFUND,
	/** 以发起退款 */
	START_REFUND,
	/** 部分退款 */
	REFUND_PART,
	/** 全部退款 */
	REFUND_ALL;
}
