package com.yl.dpay.hessian.beans;

import java.io.Serializable;

/**
 * 代付响应bean
 * 
 * @author 聚合支付有限公司
 * @since 2016年5月15日
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
	private String requestNo;
	/**
	 * 描述
	 */
	private String description;
	/**
	 * 流水号
	 */
	private String flowNo;
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

	public String getRequestNo() {
		return requestNo;
	}

	public void setRequestNo(String requestNo) {
		this.requestNo = requestNo;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ResponseBean() {
		super();
	}

	public String getFlowNo() {
		return flowNo;
	}

	public void setFlowNo(String flowNo) {
		this.flowNo = flowNo;
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

	@Override
	public String toString() {
		return "ResponseBean [requestStatus=" + requestStatus + ", requestNo="
				+ requestNo + ", description=" + description + ", flowNo="
				+ flowNo + ", amount=" + amount + ", fee=" + fee
				+ ", responseCode=" + responseCode + ", responseMsg="
				+ responseMsg + ", handleTime=" + handleTime + "]";
	}

}
