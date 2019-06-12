package com.yl.boss.enums;

/**
 * 交易订单清算状态
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月11日
 * @version V1.0.0
 */
public enum TradeOrderClearingStatus {
	/** 未清算 */
	UN_CLEARING,
	/** 清算成功 */
	CLEARING_SUCCESS,
	/** 清算失败 */
	CLEARING_FAILED;
}
