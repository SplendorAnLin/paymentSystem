package com.yl.payinterface.core.handle.impl.remit.klt100004.single.dto.impl.response.agentPay;

import com.yl.payinterface.core.handle.impl.remit.klt100004.single.dto.IBody;

/**
 * 开联通
 *
 * @author AnLin
 * @version V1.0.0
 * @since 2018/4/3
 */
public class NotifyBody implements IBody {
	private String responseCode;
	private String orgOrderNo;
	private String orderNo;
	private String status;
	private String orderAmount;
	private String accountType;
	private String merchantId;
	private String orderDatetime;
	private String statusDesc;
	
	public String getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	public String getOrgOrderNo() {
		return orgOrderNo;
	}

	public void setOrgOrderNo(String orgOrderNo) {
		this.orgOrderNo = orgOrderNo;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(String orderAmount) {
		this.orderAmount = orderAmount;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getOrderDatetime() {
		return orderDatetime;
	}

	public void setOrderDatetime(String orderDatetime) {
		this.orderDatetime = orderDatetime;
	}

	public String getStatusDesc() {
		return statusDesc;
	}

	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
	}

	public String toString(){
		StringBuilder builder=new StringBuilder("NotifyBody[");
		builder.append("responseCode="+responseCode);
		builder.append(";orgOrderNo="+orgOrderNo);
		builder.append("orderNo="+orderNo);
		builder.append(";status="+status);
		builder.append(";orderAmount="+orderAmount);
		builder.append(";accountType="+accountType);
		builder.append(";merchantId="+merchantId);
		builder.append(";orderDatetime="+orderDatetime);
		builder.append(";statusDesc="+statusDesc);
		return builder.toString();
	}
}