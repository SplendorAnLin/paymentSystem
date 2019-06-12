package com.yl.payinterface.core.bean;

import java.io.Serializable;

/**
 * 完成交易Bean
 * 
 * @author 聚合支付有限公司
 * @since 2016年4月24日
 * @version V1.0.0
 */
public class CompleteTradeBean implements Serializable {

	private static final long serialVersionUID = -8757868378370578003L;
	/** 接口请求订单号 */
	private String interfaceRequestID;
	/** 接口订单号 */
	private String interfaceOrderID;
	/** 订单金额 */
	private double amount;
	/** 币种 */
	private String currency;
	/** 返回码 */
	private String responseCode;
	/** 返回信息 */
	private String responseMessage;
	/** 接口返回日期 */
	private String interfaceDate;
	/** 接口返回时间 */
	private String interfaceTime;

	protected String getInterfaceRequestID() {
		return interfaceRequestID;
	}

	protected void setInterfaceRequestID(String interfaceRequestID) {
		this.interfaceRequestID = interfaceRequestID;
	}

	protected String getInterfaceOrderID() {
		return interfaceOrderID;
	}

	protected void setInterfaceOrderID(String interfaceOrderID) {
		this.interfaceOrderID = interfaceOrderID;
	}

	protected double getAmount() {
		return amount;
	}

	protected void setAmount(double amount) {
		this.amount = amount;
	}

	protected String getCurrency() {
		return currency;
	}

	protected void setCurrency(String currency) {
		this.currency = currency;
	}

	protected String getResponseCode() {
		return responseCode;
	}

	protected void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	protected String getResponseMessage() {
		return responseMessage;
	}

	protected void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}

	protected String getInterfaceDate() {
		return interfaceDate;
	}

	protected void setInterfaceDate(String interfaceDate) {
		this.interfaceDate = interfaceDate;
	}

	protected String getInterfaceTime() {
		return interfaceTime;
	}

	protected void setInterfaceTime(String interfaceTime) {
		this.interfaceTime = interfaceTime;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CompleteTradeBean [interfaceRequestID=");
		builder.append(interfaceRequestID);
		builder.append(", interfaceOrderID=");
		builder.append(interfaceOrderID);
		builder.append(", amount=");
		builder.append(amount);
		builder.append(", currency=");
		builder.append(currency);
		builder.append(", responseCode=");
		builder.append(responseCode);
		builder.append(", responseMessage=");
		builder.append(responseMessage);
		builder.append(", interfaceDate=");
		builder.append(interfaceDate);
		builder.append(", interfaceTime=");
		builder.append(interfaceTime);
		builder.append("]");
		return builder.toString();
	}

}
