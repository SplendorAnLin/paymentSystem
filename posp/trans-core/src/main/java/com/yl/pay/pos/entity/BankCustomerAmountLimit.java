package com.yl.pay.pos.entity;


import com.yl.pay.pos.enums.Status;
import com.yl.pay.pos.enums.YesNo;

import javax.persistence.*;

/**
 * Title: 银行商户信息
 * Description: 
 * Copyright: Copyright (c)2012
 * Company: com.yl.pay
 * @author jun
 */
@Entity
@Table(name = "BANK_CUSTOMER_AMOUNT_LIMIT")
public class BankCustomerAmountLimit extends BaseEntity{
	
	private static final long serialVersionUID = -2132088990094015805L;
	private String bankInterfaceCode;   			//银行接口编号
	private String bankCustomerNo;					//银行商户号
	private String provinCode;					    //业务所属地区
	private String mccCategory;						//MCC行业类别
	private String bankMccCategory;					//bankMcc行业类别
	private YesNo isReally;							//是否真实
	private Double transAmount;						//交易金额
	private Status status;							//状态
	
	
	public BankCustomerAmountLimit() {
		super();
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
	@Column(name = "PROVIN_CODE", length = 30)
	public String getProvinCode() {
		return provinCode;
	}

	public void setProvinCode(String provinCode) {
		this.provinCode = provinCode;
	}

	@Column(name = "bank_Mcc_Category", length = 30)
	public String getBankMccCategory() {
		return bankMccCategory;
	}

	public void setBankMccCategory(String bankMccCategory) {
		this.bankMccCategory = bankMccCategory;
	}
	@Enumerated(value = EnumType.STRING)
	@Column(name = "IS_REALLY", columnDefinition = "VARCHAR(30)")
	public YesNo getIsReally() {
		return isReally;
	}

	public void setIsReally(YesNo isReally) {
		this.isReally = isReally;
	}
	@Column(name = "TRANS_AMOUNT")
	public Double getTransAmount() {
		return transAmount;
	}

	public void setTransAmount(Double transAmount) {
		this.transAmount = transAmount;
	}
	
	
	
	
}
