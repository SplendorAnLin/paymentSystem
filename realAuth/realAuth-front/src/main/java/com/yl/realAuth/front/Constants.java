package com.yl.realAuth.front;

/**
 * 实名认证前置常量
 * @author congxiang.bai
 * @since 2015年6月3日
 */
public class Constants {
	/** 应用名称，用于各服务间通信 */
	public static final String APP_NAME = "auth-front";
	/** MQ分隔符 */
	public static final String DELIMITER = ":";
	/** NODE分隔符 */
	public static final String NODE_SEPATOR = ".";

	/** 合作方缓存前缀 */
	public static final String PARTNER_CACHE_PERFIX = "PARTNER:";
	/** 合作方密鈅缓存前缀 */
	public static final String PARTNER_CIPHER_CACHE_PERFIX = "CIPHER:";

	/** 参数名-接口编号 */
	public static final String PARAM_NAME_API_CODE = "apiCode";
	/** 参数名-版本号 */
	public static final String PARAM_NAME_VERSION_CODE = "versionCode";
	/** 参数名-字符集 */
	public static final String PARAM_NAME_INPUT_CHARSET = "inputCharset";
	/** 参数名-字符集 */
	public static final String PARAM_NAME_CHARSET = "charSet";
	/** 参数名-签名类型 */
	public static final String PARAM_NAME_SIGN_TYPE = "signType";
	/** 参数名-签名 */
	public static final String PARAM_NAME_SIGN = "sign";
	/** 参数名-合作方 */
	public static final String PARAM_NAME_PARTNER = "partner";
	/** 参数名-合作方订单号 */
	public static final String PARAM_NAME_OUT_ORDER_ID = "requestCode";
	/** 参数名- 业务类型 */
	public static final String PARAM_NAME_BUSITYPE = "busiType";
	/** 参数名-后台通知URL */
	public static final String PARAM_NAME_NOTIFYURL = "notifyURL";
	/** 参数名-回传参数 */
	public static final String PARAM_NAME_RETURNPARAM = "returnParam";
	/** 参数名-是否实时 */
	public static final String PARAM_NAME_IS_ACTUAL = "isActual";
	/** 参数名-身份证号 */
	public static final String PARAM_NAME_CERTNO = "certNo";
	/** 参数名-卡号 */
	public static final String PARAM_NAME_BANK_CARD_NO = "bankCardNo";
	/** 参数名-手机号 */
	public static final String PARAM_NAME_MOBILE_NO = "payerMobNo";
	/** 参数名-姓名 */
	public static final String PARAM_NAME_PAYER_NAME = "payerName";
}
