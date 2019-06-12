package com.yl.pay.pos.api.bean;

import java.util.Date;

/**
 * Title: POS SHOP
 * Description:  POS网点
 * Copyright: Copyright (c)2011
 * Company: com.yl.pay
 * @author haitao.liu
 */
public class Shop extends BaseEntity{

	private String shopNo;			//网点编号
	private String shopName;		//网点名称	
	private String printName;		//小票打印名称	
	private String bindPhoneNo;		//拨号POS绑定号码
	private Date createTime;		//创建时间	
	private String customerNo;		//所属商户	
	private String status;			//状态			
	
	public String getShopNo() {
		return shopNo;
	}
	public void setShopNo(String shopNo) {
		this.shopNo = shopNo;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public String getPrintName() {
		return printName;
	}
	public void setPrintName(String printName) {
		this.printName = printName;
	}
	public String getBindPhoneNo() {
		return bindPhoneNo;
	}
	public void setBindPhoneNo(String bindPhoneNo) {
		this.bindPhoneNo = bindPhoneNo;
	}
	public String getCustomerNo() {
		return customerNo;
	}
	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
