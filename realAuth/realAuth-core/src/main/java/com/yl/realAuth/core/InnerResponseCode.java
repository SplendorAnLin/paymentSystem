package com.yl.realAuth.core;

public enum InnerResponseCode {

	UNKOWN_ERROR(ExceptionMessages.UNKOWN_ERROR, "系统错误", "9999", "系统错误"), 
	ACCOUNT_STATUS_NOT_NORMAL(ExceptionMessages.ACCOUNT_STATUS_NOT_NORMAL, "账户状态不正常", "2006","商户账户状态异常"), 
	ACCOUNT_VALUEABLE_BALANCE_NOT_ENOUGH(ExceptionMessages.ACCOUNT_VALUEABLE_BALANCE_NOT_ENOUGH, "商户账户可用余额资金不足", "2007", "账户余额不足"),
	RATE_NOT_EXISTS(ExceptionMessages.RATE_NOT_EXISTS, "费率不存在", "9999", "系统错误"), 
	INTERFACE_ROUTER_NOT_EXIST(ExceptionMessages.INTERFACE_ROUTER_NOT_EXIST, "无可用通道", "2009","暂不支持该银行卡"), 
	AUTH_RIGHT(ExceptionMessages.AUTH_RIGHT, "实名认证信息正确", "0001", "认证成功，结果正确"), 
	AUTH_ERROR(ExceptionMessages.AUTH_ERROR, "实名认证信息错误", "0002","认证成功，结果错误"), 
	AUTH_PROCESSING(ExceptionMessages.AUTH_PROCESSING, "实名认证认证中", "0004", "实名认证认证中"), 
	IDENTITY_FAILED(ExceptionMessages.IDENTITY_FAILED,"实名认证认证失败", "0003", "实名认证认证失败"), 
	BANK_INFO_ERROR(ExceptionMessages.BANK_INFO_ERROR, "银行卡信息有误", "2008", "银行卡信息有误，请核实"), 
	BANK_CARD_NOT_SUPPORT(ExceptionMessages.BANK_CARD_NOT_SUPPORT, "暂不支持该银行卡", "2009", "暂不支持该银行卡"), 
	CHANNEL_NOT_SUPPORT_BANK(ExceptionMessages.CHANNEL_NOT_SUPPORT_BANK,"通道不支持该笔交易", "2010", "通道不支持该笔交易"), 
	AUTH_ORDER_NOT_EXISTS(ExceptionMessages.AUTH_ORDER_NOT_EXISTS, "实名认证订单不存在", "2013", "商户订单不存在");

	/** 交易内部码 */
	private String errorCode;
	/** 交易系统内部响应信息 */
	private String errorMsg;
	/** 商户响应码 */
	private String merchantCode;
	/** 商户响应信息 */
	private String merchantMsg;

	private InnerResponseCode(String errorCode, String errorMsg, String merchantCode, String merchantMsg) {
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
		this.merchantCode = merchantCode;
		this.merchantMsg = merchantMsg;
	}

	/**
	 * 根据系统内部码获取对应的商户响应码
	 * @param errorCode
	 *            交易系统内部码
	 * @return MerchantResponseCode
	 */
	public static InnerResponseCode getHandlerResultCode(String errorCode) {
		InnerResponseCode[] handlerResultCodes = InnerResponseCode.values();
		for (InnerResponseCode handlerResultCode : handlerResultCodes) {
			if (handlerResultCode.getErrorCode().equalsIgnoreCase(errorCode)) {
				return handlerResultCode;
			}
		}
		return UNKOWN_ERROR;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String getMerchantCode() {
		return merchantCode;
	}

	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}

	public String getMerchantMsg() {
		return merchantMsg;
	}

	public void setMerchantMsg(String merchantMsg) {
		this.merchantMsg = merchantMsg;
	}

}
