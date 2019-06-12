package com.yl.receive.front.model;

import java.io.Serializable;

import com.yl.receive.front.common.ReceiveCodeEnum;
import com.yl.receive.front.common.ReceiveException;

/**
 * 代收查询响应信息
 * 
 * @author 聚合支付有限公司
 * @since 2016年10月17日
 * @version V1.0.0
 */
public class QueryResponse implements Serializable {

	private static final long serialVersionUID = -7431746712092627660L;

	/** 响应码 */
	private String responseCode;

	/** 响应信息 */
	private String ResponseMsg;

	/** 金额 */
	private double amount;
	
	/** 商家订单号 */
	private String customerOrderCode;
	
	/** 代收单号 */
	private String orderCode;
	
	/** 商户编号 */
	private String customerNo;
	
	public String getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	public String getResponseMsg() {
		return ResponseMsg;
	}

	public void setResponseMsg(String responseMsg) {
		ResponseMsg = responseMsg;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getCustomerOrderCode() {
		return customerOrderCode;
	}

	public void setCustomerOrderCode(String customerOrderCode) {
		this.customerOrderCode = customerOrderCode;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public String getCustomerNo() {
		return customerNo;
	}

	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}

	@Override
	public String toString() {
		return "QueryResponse [responseCode=" + responseCode + ", ResponseMsg="
				+ ResponseMsg + ", amount=" + amount + ", customerOrderCode="
				+ customerOrderCode + ", orderCode=" + orderCode
				+ ", customerNo=" + customerNo + "]";
	}

	public QueryResponse convertResCode(Exception e, QueryResponse queryResponse) {
		if (e instanceof ReceiveException) {
			queryResponse.setResponseCode(((ReceiveException) e).getCode());
			queryResponse.setResponseMsg(ReceiveCodeEnum.getMessage(queryResponse.getResponseCode()));
		} else {
			queryResponse.setResponseCode(ReceiveCodeEnum.SYS_ERROR.getCode());
			queryResponse.setResponseMsg(ReceiveCodeEnum.SYS_ERROR.getMessage());
		}
		return queryResponse;
	}
}
