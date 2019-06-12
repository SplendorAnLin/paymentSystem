package com.yl.pay.pos.entity;

import com.yl.pay.pos.enums.CustomerType;
import com.yl.pay.pos.enums.Status;
import com.yl.pay.pos.enums.YesNo;

import javax.persistence.*;
import java.util.Date;

/**
 * Title: 商户
 * Description:  
 * Copyright: Copyright (c)2011
 * Company: com.yl.pay
 * @author haitao.liu
 */

@Entity
@Table(name = "CUSTOMER")
public class Customer extends BaseEntity{

	private String customerNo;			//商户编号	
	private String shortName;			//商户简称
	private String fullName;			//商户全称
	private String mcc;					//MCC
	private Organization organization;	//所在地区
	private Status status;				//状态
	private Date createTime;			//创建时间
	private YesNo useCustomPermission;	//使用定制权限
	private String bankMcc;				//bankMcc
	private Date openTime ;              //开通时间
	private CustomerType customerType;		//商户类型
	
	@Column(name = "SHORT_NAME", length = 40)
	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	
	@Column(name = "FULL_NAME", length = 100)
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "STATUS", columnDefinition = "VARCHAR(20)")
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	
	@ManyToOne(fetch = FetchType.EAGER )
	public Organization getOrganization() {
		return organization;
	}
	public void setOrganization(Organization organization) {
		this.organization = organization;
	}
	@Column(name = "CUSTOMER_NO", length = 30)
	public String getCustomerNo() {
		return customerNo;
	}
	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}
		
	@Column(name = "MCC", length = 10)
	public String getMcc() {
		return mcc;
	}
	public void setMcc(String mcc) {
		this.mcc = mcc;
	}
	
	@Column(name = "CREATE_TIME", columnDefinition = "DATETIME")
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "USE_CUSTOM_PERMISSION", columnDefinition = "VARCHAR(10)")
	public YesNo getUseCustomPermission() {
		return useCustomPermission;
	}
	public void setUseCustomPermission(YesNo useCustomPermission) {
		this.useCustomPermission = useCustomPermission;
	}
	
	@Column(name = "BANK_MCC", length = 10)
	public String getBankMcc() {
		return bankMcc;
	}
	public void setBankMcc(String bankMcc) {
		this.bankMcc = bankMcc;
	}
	@Column(name = "OPEN_TIME", columnDefinition = "DATETIME")
	public Date getOpenTime() {
		return openTime;
	}
	public void setOpenTime(Date openTime) {
		this.openTime = openTime;
	}
	@Enumerated(value = EnumType.STRING)
	@Column(name = "CUSTOMER_TYPE", columnDefinition = "VARCHAR(20)")
	public CustomerType getCustomerType() {
		return customerType;
	}

	public void setCustomerType(CustomerType customerType) {
		this.customerType = customerType;
	}
}
