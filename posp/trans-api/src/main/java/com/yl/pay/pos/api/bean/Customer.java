package com.yl.pay.pos.api.bean;

import java.util.Date;

/**
 * 商户
 * @author
 *
 */
public class Customer extends BaseEntity{
	private static final long serialVersionUID = 1L;
	
	private String customerNo;			//商户编号
	private String shortName;			//商户简称
	private String fullName;			//商户全称
	private String mcc;					//MCC
	private String organization;		//所在地区
	private String status;				//状态
	private Date createTime;			//创建时间
	private String useCustomPermission;	//使用定制权限
	private String bankMcc;				//bankMcc
	private String customerType;		//商户类型
	private Date openTime ;           //开通时间 
	
	public String getCustomerNo() {
		return customerNo;
	}
	public String getShortName() {
		return shortName;
	}
	public String getFullName() {
		return fullName;
	}
	public String getMcc() {
		return mcc;
	}
	public String getOrganization() {
		return organization;
	}
	public String getStatus() {
		return status;
	}
	public String getUseCustomPermission() {
		return useCustomPermission;
	}
	public String getBankMcc() {
		return bankMcc;
	}
	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public void setMcc(String mcc) {
		this.mcc = mcc;
	}
	public void setOrganization(String organization) {
		this.organization = organization;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public void setUseCustomPermission(String useCustomPermission) {
		this.useCustomPermission = useCustomPermission;
	}
	public void setBankMcc(String bankMcc) {
		this.bankMcc = bankMcc;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public Date getOpenTime() {
		return openTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public void setOpenTime(Date openTime) {
		this.openTime = openTime;
	}

	public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}
}
