package com.yl.online.gateway;

/**
 * 在线交易网关系统全局常量
 * 
 * @author 聚合支付有限公司
 * @since 2016年10月3日
 * @version V1.0.0
 */
public class Constants {

	/** 应用名称，用于各服务间通信 */
	public static final String APP_NAME = "online-gateway";
	/** MQ分隔符*/
	public static final String DELIMITER = ":";
	/** NODE分隔符*/
	public static final String NODE_SEPATOR = ".";
	
	/** 合作方缓存前缀 */
	public static final String PARTNER_CACHE_PERFIX = "PARTNER:";
	/** 合作方密鈅缓存前缀 */
	public static final String PARTNER_CIPHER_CACHE_PERFIX = "CIPHER:";
	/** 在线可用支付接口信息缓存Key前缀 */
	public static final String USEABLE_INTERFACE_INFO_CACHE_PREFIX = "online.interface.useable.";
	/** 即时交易接口版本号 */
	public static final String DIRECT_PAY_CODE = "directPay";
	
	/** 参数名-接口编号 */
	public static final String PARAM_NAME_API_CODE = "apiCode";
	/** 参数名-版本号 */
	public static final String PARAM_NAME_VERSION_CODE = "versionCode";
	/** 参数名-字符集 */
	public static final String PARAM_NAME_INPUT_CHARSET = "inputCharset";
	/** 参数名-查询标示符 */
	public static final String PARAM_NAME_QUERY_CODE = "queryCode";
	/** 参数名-字符集 */
	public static final String PARAM_NAME_CHARSET = "charSet";
	/** 参数名-签名类型 */
	public static final String PARAM_NAME_SIGN_TYPE = "signType";
	/** 参数名-签名 */
	public static final String PARAM_NAME_SIGN = "sign";
	/** 参数名-合作方 */
	public static final String PARAM_NAME_PARTNER = "partner";
	/** 参数名-合作方订单号 */
	public static final String PARAM_NAME_OUT_ORDER_ID = "outOrderId";
	/** 参数名-币种 */
	public static final String PARAM_NAME_CURRENCY = "currency";
	/** 参数名-金额 */
	public static final String PARAM_NAME_AMOUNT = "amount";
	/** 参数名-页面重定向URL */
	public static final String PARAM_NAME_REDIRECTURL = "redirectUrl";
	/** 参数名-后台通知URL */
	public static final String PARAM_NAME_NOTIFYURL = "notifyUrl";
	/** 参数名-回传参数 */
	public static final String PARAM_NAME_RETURNPARAM = "returnParam";
	/** 参数名-扩展参数 */
	public static final String PARAM_NAME_EXTPARAM = "extParam";
	/** 参数名-接口编号 */
	public static final String PARAM_INTERFACE_CODE = "interfaceCode";
	/** 参数名-产品名称 */
	public static final String PRODUCT_NAME = "productName";
	/** 参数名-银行卡号 */
	public static final String PARAM_NAME_CARD_NO = "bankCardNo";
	/** 参数名-支付类型 */
	public static final String PAY_TYPE = "payType";
    /** 参数名-结算金额 */
    public static final String SETTLE_AMOUNT = "settleAmount";
    /** 参数名-结算卡卡号 */
    public static final String SETTLE_CAED_NO = "settleCardNo";
    /** 参数名-结算卡开户行 */
    public static final String SETTLE_BANK_NAME = "settleBankName";
    /** 参数名-持卡人姓名 */
    public static final String USER_NAME = "userName";
    /** 参数名-身份证号 */
    public static final String ID_CARD_NO = "idCardNo";
    /** 参数名-手机号 */
    public static final String PHONE = "phone";
    /** 参数名-快捷手续费 */
    public static final String QUICK_PAY_FEE = "quickPayFee";
    /** 参数名-代付手续费 */
    public static final String REMIT_FEE = "remitFee";
    /** 参数名-结算类型 */
    public static final String SETTLE_TYPE = "settleType";

	public static final String SMS_QROPEN_ACC = "尊敬的商户：您正在扫码入网注册，验证码为[%s]，请勿将验证码告知他人.";
	
	public static final String SMS_BIND_CARD_VERIFYCODE = "不要告诉任何人！开通快捷支付验证码[%s]。";
	public static final String SMS_BIND_CARD_VERIFYCODE_KEY = "SMS_BIND_CARD_VERIFYCODE";
	/** 快捷支付发送验证码类型-- 非聚合发送 */
	public static final String SMS_CODE_TYPE = "NO_YLZF_CODE";
	/** 无短信绑卡 **/
	public static final String NO_SMS_CODE_BIND = "NO_SMS_CODE_BIND";
}
