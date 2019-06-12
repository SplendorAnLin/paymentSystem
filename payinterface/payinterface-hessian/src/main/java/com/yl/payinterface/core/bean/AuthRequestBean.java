package com.yl.payinterface.core.bean;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;

public class AuthRequestBean implements Serializable {

	private static final long serialVersionUID = -6858371694507315522L;
	/** 业务请求的支付接口编号 */
	@NotNull
	private String interfaceCode;
	/** 业务请求的支付接口提供方编号 */
	private String interfaceProviderCode;
	/** 业务系统订单编号 */
	@NotNull
	private String businessOrderID;
	/** 卡类型 */
	private String cardType;
	/** 卡号 */
	private String accountNo;
	/** 开户名 */
	private String accountName;
	/** 证件类型 */
	private String certificatesType;
	/** 证件号码 */
	private String certificatesCode;
	/** 手机号 */
	private String phoneNo;
	/** 省份 */
	private String province;
	/** 备注 */
	private String remark;
	/** 业务时间 */
	private Date businessOrderTime;
	/** 拥有者角色 */
	private String ownerRole;
	/** 拥有者ID */
	private String ownerID;

	/**
	 * 接口成本 add Liu.Meng 20151209
	 */
	private double interfaceFee;

	public double getInterfaceFee() {
		return interfaceFee;
	}

	public void setInterfaceFee(double interfaceFee) {
		this.interfaceFee = interfaceFee;
	}

	public String getInterfaceCode() {
		return interfaceCode;
	}

	public void setInterfaceCode(String interfaceCode) {
		this.interfaceCode = interfaceCode;
	}

	public String getInterfaceProviderCode() {
		return interfaceProviderCode;
	}

	public void setInterfaceProviderCode(String interfaceProviderCode) {
		this.interfaceProviderCode = interfaceProviderCode;
	}

	public String getBusinessOrderID() {
		return businessOrderID;
	}

	public void setBusinessOrderID(String businessOrderID) {
		this.businessOrderID = businessOrderID;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
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

	public String getCertificatesType() {
		return certificatesType;
	}

	public void setCertificatesType(String certificatesType) {
		this.certificatesType = certificatesType;
	}

	public String getCertificatesCode() {
		return certificatesCode;
	}

	public void setCertificatesCode(String certificatesCode) {
		this.certificatesCode = certificatesCode;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getBusinessOrderTime() {
		return businessOrderTime;
	}

	public void setBusinessOrderTime(Date businessOrderTime) {
		this.businessOrderTime = businessOrderTime;
	}

	public String getOwnerRole() {
		return ownerRole;
	}

	public void setOwnerRole(String ownerRole) {
		this.ownerRole = ownerRole;
	}

	public String getOwnerID() {
		return ownerID;
	}

	public void setOwnerID(String ownerID) {
		this.ownerID = ownerID;
	}

	@Override
	public String toString() {
		return "AuthRequestBean [interfaceCode=" + interfaceCode + ", interfaceProviderCode=" + interfaceProviderCode + ", businessOrderID=" + businessOrderID + ", cardType=" + cardType + ", accountNo=" + accountNo + ", accountName=" + accountName + ", certificatesType=" + certificatesType
				+ ", certificatesCode=" + certificatesCode + ", phoneNo=" + phoneNo + ", province=" + province + ", remark=" + remark + ", businessOrderTime=" + businessOrderTime + ", ownerRole=" + ownerRole + ", ownerID=" + ownerID + ", interfaceFee=" + interfaceFee + "]";
	}

}
