package com.yl.dpay.front.model;

import java.io.Serializable;

/**
 * 通知参数Bean
 * 
 * @author 聚合支付有限公司
 * @since 2016年5月22日
 * @version V1.0.0
 */
public class NotifyParams implements Serializable {

	private static final long serialVersionUID = 7339719065531274L;

	/**
	 * 商户订单号
	 */
	private String customerOrderId;
	/**
	 * 代付订单号
	 */
	private String dpayOrderId;
	/**
	 * 响应码
	 */
	private String responseCode;
	/**
	 * 响应信息
	 */
	private String responseMsg;
	/**
	 * 处理时间
	 */
	private String orderTime;
	/**
	 * 金额
	 */
	private String amount;
	/**
	 * 手续费
	 */
	private String fee;
	/**
	 * 商户参数
	 */
	private String customerParam;
	/**
	 * 签名信息
	 */
	private String signData;

	public String getSignData() {
		return signData;
	}

	public void setSignData(String signData) {
		this.signData = signData;
	}

	public String getCustomerParam() {
		return customerParam;
	}

	public void setCustomerParam(String customerParam) {
		this.customerParam = customerParam;
	}

	public String getCustomerOrderId() {
		return customerOrderId;
	}

	public void setCustomerOrderId(String customerOrderId) {
		this.customerOrderId = customerOrderId;
	}

	public String getDpayOrderId() {
		return dpayOrderId;
	}

	public void setDpayOrderId(String dpayOrderId) {
		this.dpayOrderId = dpayOrderId;
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

	public String getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getFee() {
		return fee;
	}

	public void setFee(String fee) {
		this.fee = fee;
	}

}
