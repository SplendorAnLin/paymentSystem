package com.yl.payinterface.core.handle.impl.remit.klt100004.single.dto.impl.query.response;

import com.yl.payinterface.core.handle.impl.remit.klt100004.single.dto.IBody;

/**
 * 开联通
 *
 * @author AnLin
 * @version V1.0.0
 * @since 2018/4/3
 */
public class SingleAgentpayQueryResponseBody implements IBody {
	private String responseCode;
	private String responseMsg;
	private String status;
	private String orderDesc;
	private String mchtId;
	private String mchtOrderNo;
	private Long amount;
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getOrderDesc() {
		return orderDesc;
	}
	public void setOrderDesc(String orderDesc) {
		this.orderDesc = orderDesc;
	}
	
	public Long getAmount() {
		return amount;
	}
	public void setAmount(Long amount) {
		this.amount = amount;
	}
	
	public String getMchtOrderNo() {
		return mchtOrderNo;
	}
	public void setMchtOrderNo(String mchtOrderNo) {
		this.mchtOrderNo = mchtOrderNo;
	}
	
	public String getMchtId() {
		return mchtId;
	}
	public void setMchtId(String mchtId) {
		this.mchtId = mchtId;
	}

	@Override
	public String toString() {
		return "responseCode=" + responseCode + "&responseMsg=" + responseMsg
				+ "&status=" + status + "&orderDesc=" + orderDesc + "&mchtId="
				+ mchtId + "&mchtOrderNo=" + mchtOrderNo + "&amount=" + amount;
	}
}