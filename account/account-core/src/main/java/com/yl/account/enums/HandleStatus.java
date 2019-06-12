package com.yl.account.enums;

/**
 * 调账处理状态
 * 
 * @author 聚合支付有限公司
 * @since 2016年3月24日
 * @version V1.0.0
 */
public enum HandleStatus {
	/** 未处理 */
	UN_HANDLE,
	/** 已处理 */
	HANDLED,
	/** 已过期 */
	EXPIRED,
	/** 审核通过 */
	AUDIT_PASS,
	/** 审核中 */
	AUDIT_ING,
	/** 审核拒绝 */
	AUDIT_REJECT,
	/** 作废 */
	CANCEL,
	/** 挂起 */
	SUSPEND
}
