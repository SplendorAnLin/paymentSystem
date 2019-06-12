package com.yl.payinterface.core.bean;

import java.util.Map;

import com.yl.payinterface.core.model.InterfaceRequest;

/**
 * 接口交易上下文
 * 
 * @author 聚合支付有限公司
 * @since 2016年11月11日
 * @version V1.0.0
 */
public class TradeContext {
	private Map<String, String> requestParameters;
	private InterfaceRequest interfaceRequest;

	public Map<String, String> getRequestParameters() {
		return requestParameters;
	}

	public void setRequestParameters(Map<String, String> requestParameters) {
		this.requestParameters = requestParameters;
	}

	public InterfaceRequest getInterfaceRequest() {
		return interfaceRequest;
	}

	public void setInterfaceRequest(InterfaceRequest interfaceRequest) {
		this.interfaceRequest = interfaceRequest;
	}

}
