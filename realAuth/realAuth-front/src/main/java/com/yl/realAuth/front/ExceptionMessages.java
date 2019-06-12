package com.yl.realAuth.front;

/**
 * 异常码定义
 * @author congxiang.bai
 * @since 2016年1月11日
 */
public class ExceptionMessages {
	/** 参数不存在 */
	public static final String PARAM_NOT_EXISTS = "S0001";
	/** 参数错误 */
	public static final String PARAM_ERROR = "S0002";
	/** 签名校验错误 */
	public static final String SIGN_ERROR = "S0003";
	/** 签名算法不支持 */
	public static final String SIGN_ALGORITHM_NOT_SUPPORT = "S0004";
	/** 实名认证商户不存在 */
	public static final String RECEIVER_NOT_EXISTS = "S0005";
	/** 商户状态错误 */
	public static final String RECEIVER_STATUS_ERROR = "S0006";
	/** 商户尚未开通实名认证服务 */
	public static final String RECEIVER_NOT_OPEN_SERVICE = "S0007";
	/** 订单已成功 */
	public static final String TRADE_ORDER_AREADY_SUCCESS = "S0008";
	/** 订单已关闭 */
	public static final String TRADE_ORDER_ALREADY_CLOSED = "S0009";
	/** 系统错误 */
	public static final String UNKOWN_ERROR = "S0013";
	/** 无此订单 */
	public static final String TRADE_ORDER_NOT_EXISTS = "S0022";

}
