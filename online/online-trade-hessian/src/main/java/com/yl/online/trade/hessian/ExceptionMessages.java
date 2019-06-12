package com.yl.online.trade.hessian;

/**
 * 异常码定义
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月11日
 * @version V1.0.0
 */
public class ExceptionMessages {
	// 参数校验相关00开头
	/** 参数不存在 */
	public static final String PARAM_NOT_EXISTS = "0001";
	/** 参数错误 */
	public static final String PARAM_ERROR = "0002";

	// 业务校验相关01开头
	/** 费率不存在 */
	public static final String RATE_NOT_EXISTS = "0101";
	/** 费率信息不完整 */
	public static final String RATE_INFO_ERROR = "0102";
	/** 支付订单不存在 */
	public static final String TRADE_ORDER_NOT_EXISTS = "0103";
	/** 合作方信息不存在*/
	public static final String PARTNER_INFO_NOT_EXISTS = "0104";
	/** 合作方已冻结 */
	public static final String PARTNER_ALREADY_FREEZED = "0105";
	/** 订单支付禁用 */
	public static final String WITHOUT_ORDER_PAY_FORBIDDEN = "0106";
	/** 不支持订单支付 */
	public static final String NOT_SUPPORT_ORDER_PAY = "0107";
	/** MCC信息错误*/
	public static final String PARTNER_MCC_ERROR = "0108";
	/** 线下合作信息不存在 */
	public static final String OFFLINE_PARTNER_NOT_EXISTS = "0109";
	/** 验签错误 */
	public static final String SIGN_ERROR = "0110";
	/** 订单超期 */
	public static final String TRADE_ORDER_TIME_OUT = "0111";
	/** 订单已成功支付 */
	public static final String TRADE_ORDER_SUCCESS_PAY = "0112";
}
