package com.yl.boss.entity;

import com.yl.boss.enums.Status;

import javax.persistence.*;
import java.util.Date;

/**
 * 网点信息
 *
 * @author 聚合支付有限公司
 * @since 2016年8月11日
 * @version V1.0.0
 */

@Entity
@Table(name = "SHOP")
public class Shop extends AutoIDEntity{

	private String shopNo;			//网点编号
	private String shopName;		//网点名称	
	private String printName;		//小票打印名称	
	private String bindPhoneNo;		//拨号POS绑定号码
	private Date createTime;		//创建时间	
	private Customer customer;		//所属商户	
	private Status status;			//状态
	
	@Column(name = "SHOP_NO", length = 30)
	public String getShopNo() {
		return shopNo;
	}
	public void setShopNo(String shopNo) {
		this.shopNo = shopNo;
	}
	
	@Column(name = "SHOP_NAME", length = 60)
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	@Column(name = "PRINT_NAME", length = 60)
	public String getPrintName() {
		return printName;
	}
	public void setPrintName(String printName) {
		this.printName = printName;
	}
	
	@Column(name = "BIND_PHONE_NO", length = 128)
	public String getBindPhoneNo() {
		return bindPhoneNo;
	}
	public void setBindPhoneNo(String bindPhoneNo) {
		this.bindPhoneNo = bindPhoneNo;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="CUSTOMER_NO", referencedColumnName = "CUSTOMER_NO", columnDefinition = "VARCHAR(30)")
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "STATUS", columnDefinition = "VARCHAR(20)")
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	@Column(name = "CREATE_TIME", columnDefinition = "DATETIME")
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
}
