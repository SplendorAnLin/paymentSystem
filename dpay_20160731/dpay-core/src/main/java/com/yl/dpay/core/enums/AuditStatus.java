package com.yl.dpay.core.enums;

/**
 * 审核状态
 * 
 * @author 聚合支付有限公司
 * @since 2016年5月12日
 * @version V1.0.0
 */
public enum AuditStatus {
	/**
	 * 未处理
	 */
	WAIT,
	/**
	 * BOSS处理
	 */
	BOSS_WAIT,
	/**
	 * 等待付款处理
	 */
	REMIT_WAIT,
	/**
	 * 拒绝
	 */
	REFUSE,
	/**
	 * 付款拒绝
	 */
	REMIT_REFUSE,
	/**
	 * 通过
	 */
	PASS;
}

