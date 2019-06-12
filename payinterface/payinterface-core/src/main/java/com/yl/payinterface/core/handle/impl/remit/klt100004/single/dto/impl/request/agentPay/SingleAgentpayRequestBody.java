package com.yl.payinterface.core.handle.impl.remit.klt100004.single.dto.impl.request.agentPay;

import com.yl.payinterface.core.handle.impl.remit.klt100004.single.dto.IBody;

/**
 * 开联通
 *
 * @author AnLin
 * @version V1.0.0
 * @since 2018/4/3
 */
public class SingleAgentpayRequestBody implements IBody {

	private String mchtId;
	
	private String mchtOrderNo;

	private String accountNo;

	private String accountName;

	private String accountType;

	private String bankNo;

	private String bankName;

	private String amt;

	private String purpose;

	private String remark;
	
	private String notifyUrl;
	
	private String orderDateTime;
	
	public String getMchtOrderNo() {
		return mchtOrderNo;
	}

	public void setMchtOrderNo(String mchtOrderNo) {
		this.mchtOrderNo = mchtOrderNo;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getBankNo() {
		return bankNo;
	}

	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getAmt() {
		return amt;
	}

	public void setAmt(String amt) {
		this.amt = amt;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getNotifyUrl() {
		return notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}

	public String getMchtId() {
		return mchtId;
	}

	public void setMchtId(String mchtId) {
		this.mchtId = mchtId;
	}

	public String getOrderDateTime() {
		return orderDateTime;
	}

	public void setOrderDateTime(String orderDateTime) {
		this.orderDateTime = orderDateTime;
	}
}