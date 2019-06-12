package com.yl.online.gateway.enums;

import com.lefu.hessian.HessianConstants;
import com.yl.online.gateway.ExceptionMessages;

/**
 * 商户响应码
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月8日
 * @version V1.0.0
 */
public enum MerchantResponseCode {

	/** 系统级错误信息 */
	HESSIAN_INVOKE_SYSTEM_IS_BLANK(HessianConstants.HESSIAN_INVOKE_SYSTEM_IS_BLANK, "9999", "系统未知错误"),
	HESSIAN_REQUEST_TIMEOUT(HessianConstants.HESSIAN_REQUEST_TIMEOUT, "9999", "系统未知错误"),
	PARAM_NOT_EXISTS(ExceptionMessages.PARAM_NOT_EXISTS, "0001", "参数不存在"),
	PARAM_ERROR(ExceptionMessages.PARAM_ERROR, "0002", "参数错误"),
	SIGN_ERROR(ExceptionMessages.SIGN_ERROR, "0003", "签名错误"),
	PAY_TYPE_ERROR(ExceptionMessages.PAY_TYPE_ERROR, "0004", "支付方式错误"),
	RECEIVER_NOT_EXISTS(ExceptionMessages.RECEIVER_NOT_EXISTS, "0006", "商户不存在"),
	RECEIVER_STATUS_ERROR(ExceptionMessages.RECEIVER_STATUS_ERROR, "0007", "商户状态异常"),
	RATE_NOT_EXISTS(ExceptionMessages.RATE_NOT_EXISTS, "0007", "商户状态异常"),
	RECEIVER_NOT_OPEN_ONLINE_SERVICE(ExceptionMessages.RECEIVER_NOT_OPEN_ONLINE_SERVICE, "0008", "商户未开通此业务"),
	RECEIVER_KEY_NOT_EXISTS(ExceptionMessages.RECEIVER_KEY_NOT_EXISTS, "0009", "商户密钥不存在"),
	SIGN_ALGORITHM_NOT_SUPPORT(ExceptionMessages.SIGN_ALGORITHM_NOT_SUPPORT, "0010", "不支持此签名方式"),
	
	
	/**商户交易配置校验*/
	CUSTOMER_CONFIG_DATA(ExceptionMessages.CUSTOMER_CONFIG_DATA, "0201", "商户交易不在可用时间段"),
	CUSTOMER_CONFIG_MAX(ExceptionMessages.CUSTOMER_CONFIG_MAX, "0202", "商户交易单笔金额超过上限"),
	CUSTOMER_CONFIG_MIN(ExceptionMessages.CUSTOMER_CONFIG_MIN, "0203", "商户交易单笔金额不足"),
	CUSTOMER_CONFIG_DAY_MAX(ExceptionMessages.CUSTOMER_CONFIG_DAY_MAX, "0204", "商户当日交易金额超过上限"),
	CUSTOMER_CONFIG_AMOUNT(ExceptionMessages.CUSTOMER_CONFIG_AMOUNT, "0205", "商户交易金额不符合设置值"),
	CUSTOMER_NO(ExceptionMessages.CUSTOMER_NO, "0206", "商户号不存在"),
	CUSTOMER_PARTNERROUTER(ExceptionMessages.CUSTOMER_PARTNERROUTER, "0207", "路由不存在"),
	
	/** 订单级错误信息 */
	TRADE_ORDER_NOT_EXISTS(ExceptionMessages.TRADE_ORDER_NOT_EXISTS, "0011", "支付订单不存在"),
//	TRADE_ORDER_AREADY_SUCCESS(ExceptionMessages.TRADE_ORDER_AREADY_SUCCESS, "0101", "订单已成功支付"),
//	PAYMENT_STATUS_NOT_INIT(ExceptionMessages.PAYMENT_STATUS_NOT_INIT, "0101", "订单已成功支付"),
//	TRADE_ORDER_NOT_SUPPORT_REPEAT_PAY(ExceptionMessages.TRADE_ORDER_NOT_SUPPORT_REPEAT_PAY, "0102", "订单不支持重复支付"),
	
	
	INTERFACE_DISABLE(ExceptionMessages.INTERFACE_DISABLE,"0013","通道状态不可用"),
	EXCEED_LIMIT(ExceptionMessages.EXCEED_LIMIT,"0014","超过通道设置限额"),
	AMOUNT_FEE_ERROR(ExceptionMessages.INTERFACE_DISABLE,"0015","订单金额小于通道手续费"),
	LOWER_LIMIT(ExceptionMessages.LOWER_LIMIT,"0016","低于通道设置限额"),
	NOT_CHENNEL_TIME(ExceptionMessages.NOT_CHENNEL_TIME,"0017","非通道开放时间"),
	
	
	/** 参数错误 */
	APICODE_NULL(ExceptionMessages.APICODE_NULL,"0301","apiCode 不能为空  "),
	INPUTCHARSET_NULL(ExceptionMessages.INPUTCHARSET_NULL,"0302","inputCharset 不能为空  "),
	SINGTYPE_NULL(ExceptionMessages.SINGTYPE_NULL,"0303","signType 不能为空  "),
	PARTNERCODE_NULL(ExceptionMessages.PARTNERCODE_NULL,"0304","partner 不能为空  "),
	OUTORDERID_NULL(ExceptionMessages.OUTORDERID_NULL,"0305","outOrderId 不能为空  "),
	AMOUNT_NULL(ExceptionMessages.AMOUNT_NULL,"0306","amount 不能为空  "),
	PAYTYPE_NULL(ExceptionMessages.PAYTYPE_NULL,"0307","payType 不能为空  "),
	NOTIFYURL_NULL(ExceptionMessages.NOTIFYURL_NULL,"0308","notifyUrl 不能为空  "),
	SIGN_NULL(ExceptionMessages.SIGN_NULL,"0309","sign 不能为空  "),
	AUTH_CODE_NULL(ExceptionMessages.AUTH_CODE_NULL,"0310","authCode 不能为空  "),
	
	
	TRADE_EXIST_FISHING_RISK(ExceptionMessages.TRADE_EXIST_FISHING_RISK, "9999", "系统未知错误"),
	
	/** 未知错误信息 */
	UNKOWN_ERROR(ExceptionMessages.UNKOWN_ERROR, "9999", "系统未知错误");

	/** 交易系统内部码 */
	private String gatewayCode;

	/** 商户响应码 */
	private String merchantCode;

	/** 商户响应信息 */
	private String responseMessage;

	private MerchantResponseCode(String gatewayCode, String merchantCode, String responseMessage) {
		this.gatewayCode = gatewayCode;
		this.merchantCode = merchantCode;
		this.responseMessage = responseMessage;
	}

	/**
	 * 根据系统内部码获取对应的商户响应码
	 * @param gatewayCode 交易系统内部码
	 * @return MerchantResponseCode
	 */
	public static MerchantResponseCode getMerchantCode(String gatewayCode) {
		MerchantResponseCode[] merchantResponseCodes = MerchantResponseCode.values();
		for (MerchantResponseCode merchantResponseCode : merchantResponseCodes) {
			if (merchantResponseCode.getGatewayCode().equalsIgnoreCase(gatewayCode)) {
				return merchantResponseCode;
			}
		}
		return UNKOWN_ERROR;
	}

	public String getGatewayCode() {
		return gatewayCode;
	}

	public void setGatewayCode(String gatewayCode) {
		this.gatewayCode = gatewayCode;
	}

	public String getMerchantCode() {
		return merchantCode;
	}

	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}

	public String getResponseMessage() {
		return responseMessage;
	}

	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}

}
