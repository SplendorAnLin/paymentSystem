package com.yl.receive.hessian.bean;

import java.io.Serializable;

/**
 * 代收响应bean
 * 
 * @author 聚合支付有限公司
 * @since 2016年11月13日
 * @version V1.0.0
 */
public class ResponseBean implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 请求状态
	 */
	private String requestStatus;
	/**
	 * 请求编号
	 */
	private String customerOrderCode;
	/**
	 * 描述
	 */
	private String remark;
	/**
	 * 流水号
	 */
	private String receiveId;
	/**
	 * 金额
	 */
	private Double amount;
	/**
	 * 金额
	 */
	private Double fee;
	/**
	 * 响应编码
	 */
	private String responseCode;
	/**
	 * 响应信息
	 */
	private String responseMsg;

	/**
	 * 订单处理时间
	 */
	private String handleTime;

	public String getRequestStatus() {
		return requestStatus;
	}

	public void setRequestStatus(String requestStatus) {
		this.requestStatus = requestStatus;
	}

	public String getResponseMsg() {
		return responseMsg;
	}

	public void setResponseMsg(String responseMsg) {
		this.responseMsg = responseMsg;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	public String getHandleTime() {
		return handleTime;
	}

	public void setHandleTime(String handleTime) {
		this.handleTime = handleTime;
	}

	public Double getFee() {
		return fee;
	}

	public void setFee(Double fee) {
		this.fee = fee;
	}

	public String getCustomerOrderCode() {
		return customerOrderCode;
	}

	public void setCustomerOrderCode(String customerOrderCode) {
		this.customerOrderCode = customerOrderCode;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getReceiveId() {
		return receiveId;
	}

	public void setReceiveId(String receiveId) {
		this.receiveId = receiveId;
	}

	@Override
	public String toString() {
		return "ResponseBean [requestStatus=" + requestStatus
				+ ", customerOrderCode=" + customerOrderCode + ", remark="
				+ remark + ", receiveId=" + receiveId + ", amount=" + amount
				+ ", fee=" + fee + ", responseCode=" + responseCode
				+ ", responseMsg=" + responseMsg + ", handleTime=" + handleTime
				+ "]";
	}
	
}
