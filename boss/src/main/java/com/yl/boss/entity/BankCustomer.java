package com.yl.boss.entity;


import javax.persistence.*;

import com.yl.boss.enums.Status;

/**
 * Title: 银行商户信息
 * Description: 
 * Copyright: Copyright (c)2012
 * Company: com.yl.pay
 * @author jun
 */
@Entity
@Table(name = "BANK_CUSTOMER")
public class BankCustomer extends AutoIDEntity{
	
	private static final long serialVersionUID = -2132088990094015805L;
	private String bankInterfaceCode;   			//银行接口编号
	private String bankCustomerNo;					//银行商户号
	private String bankCustomerName;				//银行商户名
	private String mcc;								//MCC
	private String organization;					//业务所属地区
	private Status status;							//状态
	private String mccCategory;						//MCC行业类别
	
	
	
	public BankCustomer() {
		super();
	}
	/**
	 * @param bankInterfaceCode2
	 * @param no
	 * @param string
	 * @param string2
	 */
	public BankCustomer(String bankInterfaceCode, String bankCustomerNo, String bankCustomerName, String mcc) {
		this.bankInterfaceCode = bankInterfaceCode;
		this.bankCustomerNo=bankCustomerNo;
		this.bankCustomerName = bankCustomerName;
		this.mcc = mcc;
	}

	@Column(name = "BANK_INTERFACE_CODE", length = 30)
	public String getBankInterfaceCode() {
		return bankInterfaceCode;
	}
	public void setBankInterfaceCode(String bankInterfaceCode) {
		this.bankInterfaceCode = bankInterfaceCode;
	}
	
	@Column(name = "BANK_CUSTOMER_NO", length = 50)
	public String getBankCustomerNo() {
		return bankCustomerNo;
	}
	public void setBankCustomerNo(String bankCustomerNo) {
		this.bankCustomerNo = bankCustomerNo;
	}
	
	@Column(name = "BANK_CUSTOMER_NAME", length = 128)
	public String getBankCustomerName() {
		return bankCustomerName;
	}
	public void setBankCustomerName(String bankCustomerName) {
		this.bankCustomerName = bankCustomerName;
	}
	
	@Column(name = "MCC", length = 20)
	public String getMcc() {
		return mcc;
	}
	public void setMcc(String mcc) {
		this.mcc = mcc;
	}
	
	
	@Column(name = "ORGANIZATION", length = 30)
	public String getOrganization() {
		return organization;
	}
	public void setOrganization(String organization) {
		this.organization = organization;
	}
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "STATUS", columnDefinition = "VARCHAR(30)")
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	@Column(name = "mcc_Category", length = 30)
	public String getMccCategory() {
		return mccCategory;
	}
	public void setMccCategory(String mccCategory) {
		this.mccCategory = mccCategory;
	}
	
	
}
