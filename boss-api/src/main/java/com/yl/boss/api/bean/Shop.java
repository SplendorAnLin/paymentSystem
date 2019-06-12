package com.yl.boss.api.bean;

import java.io.Serializable;
import java.util.Date;

import com.yl.boss.api.enums.Status;

/**
 * 网点信息
 *
 * @author 聚合支付有限公司
 * @since 2017年6月27日
 * @version V1.0.0
 */
public class Shop extends BaseBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String shopNo;			//网点编号
	private String shopName;		//网点名称	
	private String printName;		//小票打印名称	
	private String bindPhoneNo;		//拨号POS绑定号码
	private Date createTime;		//创建时间	
	private Customer customer;		//所属商户	
	private Status status;			//状态
	
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
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	
}
