package com.yl.receive.core.enums;

/**
 * 交易订单状态
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月25日
 * @version V1.0.0
 */
public enum OrderStatus {
	/** 待审核 */
	WAIT,
	/** 审核拒绝 */
	REJECT,
	/** 处理中 */
	PROCESS,
	/** 失败 */
	FAILED,
	/** 成功 */
	SUCCESS;

}
