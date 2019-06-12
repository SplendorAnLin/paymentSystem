package com.yl.payinterface.core.bean;

import java.util.Map;

import com.lefu.hessian.bean.JsonBean;

public class AuthRequestResponseBean implements JsonBean  {

	private static final long serialVersionUID = 5810954349299938184L;
	/** 资金通道编码 */
	private String interfaceCode;
	/** 资金通道响应参数 */
	private Map<String, Object> authRequestResponseMsg;

	public String getInterfaceCode() {
		return interfaceCode;
	}

	public void setInterfaceCode(String interfaceCode) {
		this.interfaceCode = interfaceCode;
	}

	public Map<String, Object> getAuthRequestResponseMsg() {
		return authRequestResponseMsg;
	}

	public void setAuthRequestResponseMsg(Map<String, Object> authRequestResponseMsg) {
		this.authRequestResponseMsg = authRequestResponseMsg;
	}

	@Override
	public String toString() {
		return "AuthRequestResponseBean [interfaceCode=" + interfaceCode + ", authRequestResponseMsg=" + authRequestResponseMsg + "]";
	}

}
