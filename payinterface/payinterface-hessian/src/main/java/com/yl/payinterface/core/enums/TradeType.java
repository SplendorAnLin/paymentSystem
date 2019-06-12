package com.yl.payinterface.core.enums;

/**
 * 交易类型
 * 
 * @author 聚合支付有限公司
 * @since 2016年4月24日
 * @version V1.0.0
 */
public enum TradeType {
	/** 消费 */
	PURCHASE,
	/** 消费冲正 */
	PURCHASE_REVERSAL,
	/** 消费撤销 */
	PURCHASE_VOID,
	/** 消费撤销冲正 */
	PURCHASE_VOID_REVERSAL,
	/** 退货 */
	REFUND,

	/** 绑定 */
	BIND,
	/** 绑定查询 */
	BIND_QUERY,
	/** 验证 */
	VERIFY,
	
	/** 余额查询 */
	BALANCE_QUERY,
	/** 可用余额查询 */
	AVAILABLE_BALANCE_QUERY,

	/** 签到 */
	SIGN_IN,
	/** 签退 */
	SIGN_OFF,
	/** 结算 */
	SETTLE,
	/** 批上送 */
	BATCH_UP,

	/** 预授权 */
	PRE_AUTH,
	/** 预授权冲正 */
	PRE_AUTH_REVERSAL,
	/** 预授权撤销 */
	PRE_AUTH_VOID,
	/** 预授权撤销冲正 */
	PRE_AUTH_VOID_REVERSAL,
	/** 预授权完成 */
	PRE_AUTH_COMP,
	/** 预授权完成撤销 */
	PRE_AUTH_COMP_VOID,
	/** 预授权完成冲正 */
	PRE_AUTH_COMP_REVERSAL,
	/** 预授权完成撤销冲正 */
	PRE_AUTH_COMP_VOID_REVERSAL;
}
