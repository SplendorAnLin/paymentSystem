package com.yl.payinterface.core;

/**
 * 异常码定义
 * 
 * @author 聚合支付有限公司
 * @since 2016年11月13日
 * @version V1.0.0
 */
public class ExceptionMessages {
	// 参数校验相关00开头
	/** 请求参数不存在 */
	public static final String TRADE_PARAMS_NOT_EXISTS = "0001";
	/** 请求参数错误 */
	public static final String TRADE_PARAMS_ERROR = "0002";
	/** 请求参数存在重复 */
	public static final String TRADE_PARAMS_EXISTS = "0004";

	// 业务处理相关01开头
	/** 请求参数不存在 */
	public static final String BUSINESS_REQUEST_ALREADY_EXISTS = "0101";
	/** 业务处理实体不存在 */
	public static final String BUSINESS_HANDLER_ENTITY_NULL = "0102";
	/** 接口已经成功处理 */
	public static final String INTERFACE_REQUEST_NOT_INIT = "0103";
	/** 支付金额不一致 */
	public static final String PAY_AMOUNT_NOT_ACCORDANCE = "0104";
	/** 请求接口信息不存在 */
	public static final String INTERFACE_INFO_NOT_EXISTS = "0105";
	/** 响应参数为空 */
	public static final String RESPONSE_MESSAGE_IS_NULL = "0106";
	/** 手机支付下订单失败 */
	public static final String CREATE_ORDER_FAIL = "0107";
	/** 通道方订单不存在 */
	public static final String ORDER_IS_NULL = "0108";

	/** 未知错误异常 */
	public static final String UNKNOWN_ERROR = "9999";
	
	/** 通道状态不可用 */
	public static final String INTERFACE_DISABLE = "I101";
	/** 超过通道设置限额 */
	public static final String EXCEED_LIMIT = "I102";
	/** 低于通道设置限额 */
	public static final String LOWER_LIMIT = "I103";

}
