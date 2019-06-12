package com.yl.payinterface.core.model;

import com.yl.payinterface.core.enums.InterfaceInfoStatus;

/**
 * @ClassName QuickPayFilingInfo
 * @Description TODO(这里用一句话描述这个类的作用)
 * @author Administrator
 * @date 2017年11月20日 下午3:28:50
 */
public class QuickPayFilingInfo extends AutoStringIDModel {
	private static final long serialVersionUID = -9057090848666296830L;

	/** 姓名 */
	private String name;
	/** 身份证 */
	private String idCardNo;
	private String phone;
	private String clearBankCode;
	private String bankName;
	/** 结算卡 */
	private String bankCardNo;
	private String remitFee;
	private String quickPayFee;
	private String interfaceInfoCode;
	/** 渠道商户号 */
	private String channelCustomerCode;
	/** 虚拟商户号 */
	private String customerCode;

	private String transCardNo;

	private String bankCode;

	private InterfaceInfoStatus status;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIdCardNo() {
		return idCardNo;
	}

	public void setIdCardNo(String idCardNo) {
		this.idCardNo = idCardNo;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getClearBankCode() {
		return clearBankCode;
	}

	public void setClearBankCode(String clearBankCode) {
		this.clearBankCode = clearBankCode;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankCardNo() {
		return bankCardNo;
	}

	public void setBankCardNo(String bankCardNo) {
		this.bankCardNo = bankCardNo;
	}

	public String getRemitFee() {
		return remitFee;
	}

	public void setRemitFee(String remitFee) {
		this.remitFee = remitFee;
	}

	public String getQuickPayFee() {
		return quickPayFee;
	}

	public void setQuickPayFee(String quickPayFee) {
		this.quickPayFee = quickPayFee;
	}

	public String getTransCardNo() {
		return transCardNo;
	}

	public void setTransCardNo(String transCardNo) {
		this.transCardNo = transCardNo;
	}

	public String getInterfaceInfoCode() {
		return interfaceInfoCode;
	}

	public void setInterfaceInfoCode(String interfaceInfoCode) {
		this.interfaceInfoCode = interfaceInfoCode;
	}

	public String getChannelCustomerCode() {
		return channelCustomerCode;
	}

	public void setChannelCustomerCode(String channelCustomerCode) {
		this.channelCustomerCode = channelCustomerCode;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public InterfaceInfoStatus getStatus() {
		return status;
	}

	public void setStatus(InterfaceInfoStatus status) {
		this.status = status;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

}
