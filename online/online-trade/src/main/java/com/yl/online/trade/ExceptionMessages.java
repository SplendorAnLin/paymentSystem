package com.yl.online.trade;

/**
 * 异常码定义
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月3日
 * @version V1.0.0
 */
public class ExceptionMessages {
	
	// 参数校验相关00开头
	/** 参数不存在 */
	public static final String PARAM_NOT_EXISTS = "0001";
	/** 参数错误 */
	public static final String PARAM_ERROR = "0002";
	/** 签名校验错误 */
	public static final String SIGN_ERROR = "0003";

	// 业务校验相关01开头
	/** 结果不止一个 */
	public static final String RESULT_MORE_THAN_ONE = "1000";

	/** 收款方不存在 */
	public static final String RECEIVER_NOT_EXISTS = "0100";
	/** 收款方状态错误 */
	public static final String RECEIVER_STATUS_ERROR = "0101";
	/** 收款方未开通在线服务 */
	public static final String RECEIVER_NOT_OPEN_ONLINE_SERVICE = "0102";
	/** 收款方密钥不存在 */
	public static final String RECEIVER_KEY_NOT_EXISTS = "0103";

	/** TradeOrder不存在 */
	public static final String TRADE_ORDER_NOT_EXISTS = "0110";
	/** TradeOrder已支付成功 */
	public static final String TRADE_ORDER_AREADY_SUCCESS = "0111";
	/** TradeOrder不支持重复支付 */
	public static final String TRADE_ORDER_NOT_SUPPORT_REPEAT_PAY = "0112";
	/** TradeOrder订单超时 */
	public static final String TRADE_ORDER_TIME_OUT = "0113";
	/** TradeOrder订单已关闭 */
	public static final String TRADE_ORDER_ALREADY_CLOSED = "0114";
	/** TradeOrder前后支付金额不一致 */
	public static final String TRADE_ORDER_PAY_MONEY_UNACCORDANCE = "0115";
	/** TradeOrder不支持部分退款 */
	public static final String TRADE_ORDER_NOT_SUPPORT_PART_REFUND = "0116";
	/** TradeOrder 退款金额大于可退金额 */
	public static final String TRADE_ORDER_REFUNDMONEY_GRATERTHAN_REFUNDABLEMONEY = "0117";
	/** TradeOrder 支付金额小于应收手续费金额 */
	public static final String TRADE_ORDER_MONEY_LESSTHAN_FEE = "0118";

	/** Payment不存在 */
	public static final String PAYMENT_NOT_EXISTS = "0120";
	/** Payment已支付过 */
	public static final String PAYMENT_STATUS_NOT_INIT = "0121";

	/** Refundment已经被锁定 */
	public static final String REFUNDMENT_AUDIT_ALREADY_LOCKED = "0130";
	/** Refundment非待审核状态 */
	public static final String REFUNDMENT_STATUS_NOT_INIT = "0131";
	/** Refundment不存在 */
	public static final String REFUNDMENT_NOT_EXISTS = "0132";
	/** Refundment不需要审核 */
	public static final String REFUNDMENT_NOT_NEED_AUDIT = "0133";
	/** Refundment次数超限 */
	public static final String REFUNDMENT_COUNT_OVER_VALIDATE = "0134";
	/** Refundment退款金额不等于订单支付金额 */
	public static final String REFUNDMENT_REFUNDMONEY_UNEQUAL_ORDERPAIDMONEY = "0135";

	/** 计费信息不存在 */
	public static final String RATE_NOT_EXISTS = "0140";
	
	/** 合作方路由不存在 */
	public static final String PARTNER_ROUTER_NOT_EXISTS = "0150";
	/** 接口策略配置 */
	public static final String INTERFACE_POLICY_PROFILE_NOT_EXISTS = "0151";
	/** 路由映射错误 */
	public static final String ROUTER_MAPPER_ERROR = "0152";
	
	//商户交易配置校验02开头
	/** 商户交易不在可用时间段 */
	public static final String CUSTOMER_CONFIG_DATA ="0201";
	
	/** 商户交易单笔金额超过上限 */
	public static final String CUSTOMER_CONFIG_MAX ="0202";
	
	/** 商户交易单笔金额不足 */
	public static final String CUSTOMER_CONFIG_MIN ="0203";
	
	/** 商户当日交易金额超过上限 */
	public static final String CUSTOMER_CONFIG_DAY_MAX ="0204";
	
	/** 商户交易金额非法*/
	public static final String CUSTOMER_CONFIG_AMOUNT ="0205";
	/** 商户交易手续费小于最低限制*/
	public static final String CUSTOMER_FEE_LIMIT ="0206";

	/** 错误信息未知 */
	public static final String UNKOWN_ERROR = "9999";
}
