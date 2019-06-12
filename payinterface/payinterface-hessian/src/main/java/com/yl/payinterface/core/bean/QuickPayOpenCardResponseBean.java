package com.yl.payinterface.core.bean;

import java.io.Serializable;

/**
 * @ClassName QuickPayOpenCardResponseBean
 * @Description 快捷开卡响应
 * @author Administrator
 * @date 2017年11月19日 下午6:35:13
 */
public class QuickPayOpenCardResponseBean implements Serializable {

	private static final long serialVersionUID = -3819585526783272949L;

	private String status;

	private String responseCode;

	private String responseMessage;

	private String token;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	public String getResponseMessage() {
		return responseMessage;
	}

	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
