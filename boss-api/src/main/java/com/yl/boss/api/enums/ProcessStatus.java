package com.yl.boss.api.enums;

/**
 * 流程状态
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月16日
 * @version V1.0.0
 */
public enum ProcessStatus {
	/**
	 * 初始化
	 */
	ORDER_WAIT,
	/**
	 * 已接单
	 */
	ORDER_CONFIRM,
	/**
	 * 采购失败OR拒绝订单
	 */
	ORDER_FAIL,
	/**
	 * 制作中
	 */
	MAKING,
	/**
	 * 已绑定
	 */
	BINDED,
	/**
	 * 已分配
	 */
	ALLOT,
	/**
	 * 已入库
	 */
	OK,
	/**
	 * 锁定
	 */
	LOCKING;
}