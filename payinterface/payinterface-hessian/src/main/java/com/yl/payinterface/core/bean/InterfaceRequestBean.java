package com.yl.payinterface.core.bean;

import java.io.Serializable;

/**
 * 提供方接口请求记录Bean
 * 
 * @author 聚合支付有限公司
 * @since 2016年4月24日
 * @version V1.0.0
 */
public class InterfaceRequestBean implements Serializable {

	private static final long serialVersionUID = 2646761554434696117L;
	/** 支付接口提供方编号 */
	private String interfaceProviderCode;
	/** 支付接口编号 */
	private String interfaceInfoCode;
	/** 业务请求订单号 */
	private String bussinessOrderID;
	/** 接口请求订单号 */
	private String interfaceRequestID;
	/** 接口订单号 */
	private String interfaceOrderID;
	/** 原接口请求订单号（针对退款） */
	private String originalInterfaceRequestID;
	/** 卡类型 */
	private String cardType;
	/** 币种 */
	private String currency;
	/** 状态 */
	private String status;
	/** 业务方式 */
	private String payInterfaceRequestType;
	/** 起始金额 */
	private String amountStart;
	/** 终止金额 */
	private String amountEnd;
	/** 起始创建时间 */
	private String createTimeStart;
	/** 终止创建时间 */
	private String createTimeEnd;
	/** 起始请求时间 */
	private String requestTimeStart;
	/** 终止请求时间 */
	private String requestTimeEnd;
	/** 起始完成时间 */
	private String completeTimeStart;
	/** 起始完成时间 */
	private String completeTimeEnd;
	/** 备注 */
	private String remark;

	public String getInterfaceProviderCode() {
		return interfaceProviderCode;
	}

	public void setInterfaceProviderCode(String interfaceProviderCode) {
		this.interfaceProviderCode = interfaceProviderCode;
	}

	public String getInterfaceInfoCode() {
		return interfaceInfoCode;
	}

	public void setInterfaceInfoCode(String interfaceInfoCode) {
		this.interfaceInfoCode = interfaceInfoCode;
	}

	public String getBussinessOrderID() {
		return bussinessOrderID;
	}

	public void setBussinessOrderID(String bussinessOrderID) {
		this.bussinessOrderID = bussinessOrderID;
	}

	public String getInterfaceRequestID() {
		return interfaceRequestID;
	}

	public void setInterfaceRequestID(String interfaceRequestID) {
		this.interfaceRequestID = interfaceRequestID;
	}

	public String getInterfaceOrderID() {
		return interfaceOrderID;
	}

	public void setInterfaceOrderID(String interfaceOrderID) {
		this.interfaceOrderID = interfaceOrderID;
	}

	public String getOriginalInterfaceRequestID() {
		return originalInterfaceRequestID;
	}

	public void setOriginalInterfaceRequestID(String originalInterfaceRequestID) {
		this.originalInterfaceRequestID = originalInterfaceRequestID;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAmountStart() {
		return amountStart;
	}

	public void setAmountStart(String amountStart) {
		this.amountStart = amountStart;
	}

	public String getAmountEnd() {
		return amountEnd;
	}

	public void setAmountEnd(String amountEnd) {
		this.amountEnd = amountEnd;
	}

	public String getCreateTimeStart() {
		return createTimeStart;
	}

	public void setCreateTimeStart(String createTimeStart) {
		this.createTimeStart = createTimeStart;
	}

	public String getCreateTimeEnd() {
		return createTimeEnd;
	}

	public void setCreateTimeEnd(String createTimeEnd) {
		this.createTimeEnd = createTimeEnd;
	}

	public String getRequestTimeStart() {
		return requestTimeStart;
	}

	public void setRequestTimeStart(String requestTimeStart) {
		this.requestTimeStart = requestTimeStart;
	}

	public String getRequestTimeEnd() {
		return requestTimeEnd;
	}

	public void setRequestTimeEnd(String requestTimeEnd) {
		this.requestTimeEnd = requestTimeEnd;
	}

	public String getCompleteTimeStart() {
		return completeTimeStart;
	}

	public void setCompleteTimeStart(String completeTimeStart) {
		this.completeTimeStart = completeTimeStart;
	}

	public String getCompleteTimeEnd() {
		return completeTimeEnd;
	}

	public void setCompleteTimeEnd(String completeTimeEnd) {
		this.completeTimeEnd = completeTimeEnd;
	}

	public String getPayInterfaceRequestType() {
		return payInterfaceRequestType;
	}

	public void setPayInterfaceRequestType(String payInterfaceRequestType) {
		this.payInterfaceRequestType = payInterfaceRequestType;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
