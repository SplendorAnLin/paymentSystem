package com.yl.online.gateway;

/**
 * 通用异常码定义
 * 
 * @author 聚合支付有限公司
 * @since 2016年10月3日
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
	/** 接入方式错误 */
	public static final String ACC_MODE_ERROR = "0006";
	/** 支付方式错误 */
	public static final String PAY_TYPE_ERROR = "0004";
	/** 金额错误 */
	public static final String AMOUNT_ERROR = "0005";

	// 业务校验相关01开头
	/** 结果不止一个 */
	public static final String RESULT_MORE_THAN_ONE = "1000";
	/** 签名算法不支持 */
	public static final String SIGN_ALGORITHM_NOT_SUPPORT = "1001";

	/** 收款方不存在 */
	public static final String RECEIVER_NOT_EXISTS = "0100";
	/** 收款方状态错误 */
	public static final String RECEIVER_STATUS_ERROR = "0101";
	/** 收款方未开通在线服务 */
	public static final String RECEIVER_NOT_OPEN_ONLINE_SERVICE = "0102";
	/** 收款方密钥不存在 */
	public static final String RECEIVER_KEY_NOT_EXISTS = "0103";
	/** 交易存在钓鱼风险 */
	public static final String TRADE_EXIST_FISHING_RISK = "0104";

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
	/** TradeOrder不支持部分退款金额 */
	public static final String TRADE_ORDER_NOT_SUPPORT_PART_REFUND = "0116";
	/** TradeOrder 退款金额大于可退金额 */
	public static final String TRADE_ORDER_REFUNDMONEY_GRATERTHAN_REFUNDABLEMONEY = "0117";

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
	/** 错误支付方式 */
	public static final String ERROR_INTERFACE_TYPE = "0153";

	/** 错误信息未知 */
	public static final String UNKOWN_ERROR = "9999";

	// 认证支付响应码
	/** 支付成功（无验证码接口） */
	public static final String ORDER_SUCCESS = "000000";
	/** 交易订单不存在 */
	public static final String TRADEORDER_NOT_EXISTS = "0108";
	/** 订单不存在 */
	public static final String ORDER_NOT_EXISTS = "000014";
	/** 订单已失效，请重新下单 */
	public static final String ORDER_FAILURE = "000015";
	/** 下单异常 */
	public static final String SALE_ERROR = "000016";
	/** 创建订单异常 */
	public static final String CREATE_ORDER_ERROR = "000017";
	/** 初始化数据异常 */
	public static final String INIT_DATA_ERROR = "000018";
	/** 验证码发送失败 */
	public static final String VERIFYCODE_SEND_ERROR = "000019";
	/** 订单查询异常 */
	public static final String QUERY_ORDER_ERROR = "000020";
	/** 订单支付异常 */
	public static final String ORDER_PAY_ERROR = "000021";
	/** 下单成功，验证码已发送 */
	public static final String ORDER_SALE_SUCCESS = "000022";
	/** 金额输入有误或金额太小 */
	public static final String AMOUNR_ERROR = "000023";
	/** 输入信息有误或验证码已失效 */
	public static final String PAY_ERROR = "000024";

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

	// 商户交易配置校验02开头
	/** 商户交易不在可用时间段 */
	public static final String CUSTOMER_CONFIG_DATA = "0201";

	/** 商户交易单笔金额超过上限 */
	public static final String CUSTOMER_CONFIG_MAX = "0202";

	/** 商户交易单笔金额不足 */
	public static final String CUSTOMER_CONFIG_MIN = "0203";

	/** 商户当日交易金额超过上限 */
	public static final String CUSTOMER_CONFIG_DAY_MAX = "0204";

	/** 商户交易金额非法 */
	public static final String CUSTOMER_CONFIG_AMOUNT = "0205";

	/** 商户不存在 */
	public static final String CUSTOMER_NO = "0206";

	/** 路由不存在 */
	public static final String CUSTOMER_PARTNERROUTER = "0207";

	/** 接口编码 - apiCode 不能为空 */
	public static final String APICODE_NULL = "0301";

	/** 字符集 - inputCharset 不能为空 */
	public static final String INPUTCHARSET_NULL = "0302";

	/** 签名方式 - signType 不能为空 */
	public static final String SINGTYPE_NULL = "0303";

	/** 商户编号 - partner 不能为空 */
	public static final String PARTNERCODE_NULL = "0304";

	/** 商户订单号 - outOrderId 不能为空 */
	public static final String OUTORDERID_NULL = "0305";

	/** 订单金额 - amount 不能为空 */
	public static final String AMOUNT_NULL = "0306";

	/** 支付类型 - payType 不能为空 */
	public static final String PAYTYPE_NULL = "0307";

	/** 通知地址 - notifyUrl 不能为空 */
	public static final String NOTIFYURL_NULL = "0308";

	/** 签名摘要 - sign 不能为空 */
	public static final String SIGN_NULL = "0309";
	/** authcode 不能为空 */
	public static final String AUTH_CODE_NULL = "0310";

	/** 设备号不能为空 **/
	public static final String DEVICE_ID_NULL = "0311";

	/* 微信支付宝刷卡支付，04开头 **/
	/** 用户正在支付中 */
	public static final String USERPAYING = "0401";
	/** 买家余额不足 */
	public static final String NOTENOUGH = "0402";
	/** 请刷新条码或更换其他方式支付 */
	public static final String AUTH_CODE_INVALID = "0403";

}