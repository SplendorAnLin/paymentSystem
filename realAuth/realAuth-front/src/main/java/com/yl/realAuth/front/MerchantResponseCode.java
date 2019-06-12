package com.yl.realAuth.front;

import java.util.HashMap;
import java.util.Map;

import com.lefu.commons.utils.lang.StringUtils;

/**
 * 认证支付商户响应码
 * @author congxiang.bai
 * @since 2015年6月5日
 */
public enum MerchantResponseCode {

	/** 系统级错误信息 */
	PARAM_NOT_EXISTS(ExceptionMessages.PARAM_NOT_EXISTS, "2001", "必要参数不完整"), PARAM_ERROR(ExceptionMessages.PARAM_ERROR, "2002", "参数格式错误"), SIGN_ERROR(
			ExceptionMessages.SIGN_ERROR, "2003", "验签失败"), RECEIVER_STATUS_ERROR(ExceptionMessages.RECEIVER_STATUS_ERROR, "2004", "商户状态异常"), RECEIVER_NOT_OPEN_ONLINE_SERVICE(
			ExceptionMessages.RECEIVER_NOT_OPEN_SERVICE, "2005", "商户尚未开通此项业务"), RECEIVER_NOT_EXISTS(ExceptionMessages.RECEIVER_NOT_EXISTS, "2005", "商户尚未开通此项业务"), TRADE_ORDER_NOT_EXISTS(
			ExceptionMessages.TRADE_ORDER_NOT_EXISTS, "2011", "无此订单"),

	/** 未知错误信息 */
	UNKOWN_ERROR(ExceptionMessages.UNKOWN_ERROR, "9999", "系统错误");

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
	public static Map<String, String> getMerchantCode(String errorCode) {
		Map<String, String> merchantResponseCodeMap = new HashMap<String, String>();
		if (errorCode.contains("-")) {
			// 获取内部响应码
			String[] responseCodes = errorCode.split("-");
			String gatewayCode = responseCodes[0];
			String responseMsg = responseCodes[1];
			MerchantResponseCode[] merchantResponseCodes = MerchantResponseCode.values();
			for (MerchantResponseCode merchantResponseCode : merchantResponseCodes) {
				if (merchantResponseCode.getGatewayCode().equalsIgnoreCase(gatewayCode)) {
					responseMsg = StringUtils.concatToSB(responseMsg, merchantResponseCode.getResponseMessage()).toString();

					merchantResponseCodeMap.put("responseCode", merchantResponseCode.getMerchantCode());
					merchantResponseCodeMap.put("responseMsg", responseMsg);
					return merchantResponseCodeMap;
				}
			}
		} else {
			MerchantResponseCode[] merchantResponseCodes = MerchantResponseCode.values();
			for (MerchantResponseCode merchantResponseCode : merchantResponseCodes) {
				if (merchantResponseCode.getGatewayCode().equalsIgnoreCase(errorCode)) {

					merchantResponseCodeMap.put("responseCode", merchantResponseCode.getMerchantCode());
					merchantResponseCodeMap.put("responseMsg", merchantResponseCode.getResponseMessage());
					return merchantResponseCodeMap;
				}
			}
		}

		merchantResponseCodeMap.put("responseCode", UNKOWN_ERROR.getGatewayCode());
		merchantResponseCodeMap.put("responseMsg", UNKOWN_ERROR.getResponseMessage());
		return merchantResponseCodeMap;
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
