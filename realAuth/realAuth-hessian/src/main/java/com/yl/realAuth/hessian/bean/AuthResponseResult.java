package com.yl.realAuth.hessian.bean;

import java.io.Serializable;

/**
 * 实名认证响应结果
 * @author Shark
 * @since 2015年6月3日
 */
public class AuthResponseResult implements Serializable {

	private static final long serialVersionUID = 7212494629868718279L;
	/** 响应码 */
	private String responseCode;
	/** 响应描述 */
	private String responseMsg;
	/** 商户订单号 */
	private String requestCode;
	/** 乐富交易订单号 */
	private String tradeOrderCode;

	public String getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	public String getResponseMsg() {
		return responseMsg;
	}

	public void setResponseMsg(String responseMsg) {
		this.responseMsg = responseMsg;
	}

	public String getRequestCode() {
		return requestCode;
	}

	public void setRequestCode(String requestCode) {
		this.requestCode = requestCode;
	}

	public String getTradeOrderCode() {
		return tradeOrderCode;
	}

	public void setTradeOrderCode(String tradeOrderCode) {
		this.tradeOrderCode = tradeOrderCode;
	}

	@Override
	public String toString() {
		return "AuthResponseResult [responseCode=" + responseCode + ", responseMsg=" + responseMsg + ", requestCode=" + requestCode + ", tradeOrderCode="
				+ tradeOrderCode + "]";
	}

}
