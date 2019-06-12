package com.yl.payinterface.core.exception;

/**
 * 异常码定义
 * 
 * @author 聚合支付有限公司
 * @since 2016年5月6日
 * @version V1.0.0
 */
public class ExceptionMessages {
	/** 不允许实例化 */
	private ExceptionMessages() {
		super();
	}

	// 参数校验相关00开头
	/** 请求参数不存在 */
	public static final String TRADE_PARAMS_NOT_EXISTS = "0001";
	/** 请求参数错误 */
	public static final String TRADE_PARAMS_ERROR = "0002";
	/** 签名校验错误 */
	public static final String SIGN_ERROR = "0003";
	/** 请求批次存在重复 */
	public static final String TRADE_PARAMS_BATCH_REPEAT = "0004";
	/** 请求付款单存在重复 */
	public static final String TRADE_PARAMS_BILL_REPEAT = "0005";

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
	/** 请求付款账户信息不存在 */
	public static final String FUNACCOUNT_INFO_NOT_EXISTS = "0107";
	/** 交易网络不通 */
	public static final String NETWORK_CONNECTION_FAILED = "0108";
	/** 预检查失败 */
	public static final String PRECHECK_FAILED = "0110";
	/** 数据校验失败 */
	public static final String DATA_VALIDATE_NOT_PASS = "0111";

	/** 未知错误异常 */
	public static final String UNKNOWN_ERROR = "9999";
	
	/** 通道状态不可用 */
	public static final String INTERFACE_DISABLE = "0212";
	/** 超过通道设置限额 */
	public static final String EXCEED_LIMIT = "0213";
	/** 费率类型错误 */
	public static final String FEE_TYPE_ERROR = "0214";
	/** 订单金额小于手续费 */
	public static final String AMOUNT_FEE_ERROR = "0215";
	/** 低于通道设置限额 */
	public static final String LOWER_LIMIT = "0216";
	/** 非通道开放时间 */
	public static final String NOT_CHENNEL_TIME = "0217";

}
