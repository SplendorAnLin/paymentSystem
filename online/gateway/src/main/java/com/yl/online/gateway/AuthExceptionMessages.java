package com.yl.online.gateway;

/**
 * 快捷支付异常码
 * 
 * @author 聚合支付有限公司
 * @since 2017年08月19日
 * @version V1.0.0
 */
public enum AuthExceptionMessages {

    /** 成功 */
    SUCCESS("0000", "成功"),
    /** 需要开卡 */
    NEED_OPEN_CARD("77", "需要开卡"),
    /** 失败 */
    FAILED("9999", "处理失败"),

	/** 短信验证码 发送失败 */
	SEND_SMS_ERR("2001", "短信验证码发送失败"),

	/** 未配置结算卡信息 */
	SETTLE_CARD_ERR("3001","未配置结算卡信息"),
	/** 不支持对公结算卡 */
	SETTLE_TYPE_ERR("3002","不支持对公结算卡"),
	/** 未配置身份信息 */
	CERT_NO_ERR("3003","未配置身份信息"),
	/** 未开通结算,代付费率未配置 */
	SETTLE_STATUS_ERR("3004","未开通结算"),
	/** 未开通假日结算,代付假日费率未配置 */
	HOLIYDAY_SETTLE_STATUS_ERR("3005","未开通假日结算"),
	/** 未配置快捷支付，认证、快捷费率未配置 */
	AUTHPAY_STATUS_ERR("3006","未配置快捷支付"),
	/** 交易金额小于结算费率 */
	AMOUNT_LE_FEE("3007","交易金额小于结算费率"),
	/** 该交易卡已禁用 */
	TRNS_CARD_UNABLE("3008","该交易卡已禁用"),
	/** 该交易卡未绑定（APP） */
	TRNS_CARD_UNBIND("3009","该交易卡未绑定"),
    /** 该交易卡已绑定（APP） */
    TRNS_CARD_BINDED("3010","该交易卡已绑定"),
    /** 该交易卡已绑定（APP） */
    AUTH_FAIL("3011","实名认证失败"),
    /** 交易金额小于结算金额 */
    TRANS_AMT_LE_SETTLET_AMT("3012","交易金额小于结算金额"),
    /** 交易金额小于结算金额加手续费 */
    TRANS_AMT_LE_SETTLET_AMT_TRANS_FEE("3013","交易金额小于结算金额加手续费"),

	// 绑卡
	/** 交易卡校验失败 */
	TRNS_CARD_CHK_ERR("4001","交易卡校验失败"),

	// 交易
	/** 交易下单失败 */
	PAYMENT_FAILED("9001","下单失败"),
	/** 发送验证码失败 */
	SEND_SMS_FAILED("9002","下单失败"),
	/** 支付失败 */
	PAY_FAILED("9003","支付失败"),
	/** 绑卡失败 */
	BIND_FAILED("9004","绑卡失败"),
    /** 解绑失败 */
    UNBIND_FAILED("9005","解绑失败"),
    /** 暂不支持该交易卡 */
    UNSUPPORT_TRANS("9006","暂不支持该交易卡"),
    /** 暂不支持该结算卡 */
    UNSUPPORT_SETTLE("9007","暂不支持该结算卡"),
    /** 暂不支持该结算卡 */
    VERIFYCODE_CORE_ERROR("9008", "验证码输入错误"),
    /** 结算方式传入错误或对应结算参数错误 */
    SETTLE_TYPE_ERROR("9010", "结算方式传入错误或对应结算参数错误"),

	CUSTOMER_FEE_LIMIT("0206", "商户交易手续费小于最低限制");

	private final String code;
	private final String message;

	AuthExceptionMessages(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public static String getMessage(String code) {
		for (AuthExceptionMessages t : AuthExceptionMessages.values()) {
			if (code.equals(t.getCode())) {
				return t.getMessage();
			}
		}
		return null;
	}

	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	public static String getErrMsg(String code) {
		return code + ":" + getMessage(code);
	}

}