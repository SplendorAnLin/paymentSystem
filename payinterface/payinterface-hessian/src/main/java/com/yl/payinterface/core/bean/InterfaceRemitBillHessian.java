package com.yl.payinterface.core.bean;

import java.io.Serializable;

/**
 * 付款单hessian实体
 * 
 * @author 聚合支付有限公司
 * @since 2016年4月24日
 * @version V1.0.0
 */
public class InterfaceRemitBillHessian implements Serializable {

	private static final long serialVersionUID = 8677980529056572086L;

	/** 付款单编码 */
	private String billCode;
	/**
	 * 持有者ID
	 */
	private String ownerId;
	/**
	 * 账户名称
	 */
	private String accountName;
	/**
	 * 账户编号
	 */
	private String accountNo;
	/**
	 * 账户类型
	 */
	private String accountType;
	/**
	 * 证件类型
	 */
	private String cerType;
	/**
	 * 证件编号
	 */
	private String cerNo;
	/**
	 * 卡类型
	 */
	private String cardType;
	/**
	 * 银行编号
	 */
	private String bankCode;
	/**
	 * 有效期
	 */
	private String validity;
	/**
	 * CVV2
	 */
	private String cvv;
	/**
	 * 接口編號
	 */
	private String interfaceCode;
	/** 銀行名稱 */
	private String bankName;
	/** 金額 */
	private double amount;
	/** 付款描述 */
	private String remark;
	/** 订单付交易单号 */
	private String orderPayCode;

	public String getBillCode() {
		return billCode;
	}

	public void setBillCode(String billCode) {
		this.billCode = billCode;
	}

	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getCerType() {
		return cerType;
	}

	public void setCerType(String cerType) {
		this.cerType = cerType;
	}

	public String getCerNo() {
		return cerNo;
	}

	public void setCerNo(String cerNo) {
		this.cerNo = cerNo;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getValidity() {
		return validity;
	}

	public void setValidity(String validity) {
		this.validity = validity;
	}

	public String getCvv() {
		return cvv;
	}

	public void setCvv(String cvv) {
		this.cvv = cvv;
	}

	public String getInterfaceCode() {
		return interfaceCode;
	}

	public void setInterfaceCode(String interfaceCode) {
		this.interfaceCode = interfaceCode;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getOrderPayCode() {
		return orderPayCode;
	}

	public void setOrderPayCode(String orderPayCode) {
		this.orderPayCode = orderPayCode;
	}

	@Override
	public String toString() {
		return "InterfaceRemitBillHessian [billCode=" + billCode + ", ownerId=" + ownerId + ", accountName=" + accountName + ", accountNo=" + accountNo + ", accountType=" + accountType + ", cerType=" + cerType + ", cerNo=" + cerNo + ", cardType=" + cardType + ", bankCode=" + bankCode
				+ ", validity=" + validity + ", cvv=" + cvv + ", interfaceCode=" + interfaceCode + ", bankName=" + bankName + ", amount=" + amount + ", remark=" + remark + ", orderPayCode=" + orderPayCode + "]";
	}

}
