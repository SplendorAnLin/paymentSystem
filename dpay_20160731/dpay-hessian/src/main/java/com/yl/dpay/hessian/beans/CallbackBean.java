package com.yl.dpay.hessian.beans;

import java.io.Serializable;

/**
 * 付款回调参数Bean
 * 
 * @author 聚合支付有限公司
 * @since 2016年5月15日
 * @version V1.0.0
 */
public class CallbackBean implements Serializable{

	private static final long serialVersionUID = 3946594633940904199L;
	/**
	 * 代付流水号
	 */
	private String flowNo;
	/**
	 * 接口订单号
	 */
	private String interfaceRequestId;
	/**
	 * 接口编号
	 */
	private String interfaceCode;
	/**
	 * 状态
	 */
	private String status;
	/**
	 * 响应码
	 */
	private String responseCode;
	/**
	 * 响应信息
	 */
	private String responseMsg;
	/**
	 * 金额
	 */
	private double amount;
	/**
	 * 接口成本
	 */
	private double fee;
	
	public String getFlowNo() {
		return flowNo;
	}
	public void setFlowNo(String flowNo) {
		this.flowNo = flowNo;
	}
	public String getInterfaceRequestId() {
		return interfaceRequestId;
	}
	public void setInterfaceRequestId(String interfaceRequestId) {
		this.interfaceRequestId = interfaceRequestId;
	}
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
	public String getResponseMsg() {
		return responseMsg;
	}
	public void setResponseMsg(String responseMsg) {
		this.responseMsg = responseMsg;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getInterfaceCode() {
		return interfaceCode;
	}
	public void setInterfaceCode(String interfaceCode) {
		this.interfaceCode = interfaceCode;
	}
	public double getFee() {
		return fee;
	}
	public void setFee(double fee) {
		this.fee = fee;
	}

}
