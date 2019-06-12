package com.yl.boss.bean;

import java.io.Serializable;

public class BankCustomerBean implements Serializable{

	/** 银行商户名称 */
	private String bankCustomerName;
	
	/** 银行商户编号*/
	private String bankCustomerNo;
	
	/**地区码**/
	private String organization;
	
	private String category;
	private String province;
	private String city;
	

	public String getBankCustomerName() {
		return bankCustomerName;
	}

	public void setBankCustomerName(String bankCustomerName) {
		this.bankCustomerName = bankCustomerName;
	}

	public String getBankCustomerNo() {
		return bankCustomerNo;
	}

	public void setBankCustomerNo(String bankCustomerNo) {
		this.bankCustomerNo = bankCustomerNo;
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
	
	
}
