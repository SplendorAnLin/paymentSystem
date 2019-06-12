package com.yl.payinterface.core.bean;

import java.util.Map;

import com.lefu.hessian.bean.JsonBean;

/**
 * 资金通道代收响应结果Bean
 * 
 * @author 聚合支付有限公司
 * @since 2016年4月24日
 * @version V1.0.0
 */
public class ReceiveResponseBean implements JsonBean {

	private static final long serialVersionUID = -2308194332194320921L;

	/** 资金通道编码 */
	private String interfaceCode;
	/** 资金通道响应参数 */
	private Map<String, Object> receiveResponseMsg;

	public String getInterfaceCode() {
		return interfaceCode;
	}

	public void setInterfaceCode(String interfaceCode) {
		this.interfaceCode = interfaceCode;
	}

	public Map<String, Object> getReceiveResponseMsg() {
		return receiveResponseMsg;
	}

	public void setReceiveResponseMsg(Map<String, Object> receiveResponseMsg) {
		this.receiveResponseMsg = receiveResponseMsg;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ReceiveResponseBean [interfaceCode=");
		builder.append(interfaceCode);
		builder.append(", receiveResponseMsg=");
		builder.append(receiveResponseMsg);
		builder.append("]");
		return builder.toString();
	}

}
