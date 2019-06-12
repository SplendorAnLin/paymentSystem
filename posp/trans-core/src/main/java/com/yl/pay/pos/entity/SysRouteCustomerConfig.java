package com.yl.pay.pos.entity;

import com.yl.pay.pos.enums.Status;

import javax.persistence.*;
import java.util.Date;


/**
 * Title: 系统路由商编配置
 * Description: 用于指定银行商户编号的交易分流
 * Copyright: Copyright (c)2014
 * Company: com.yl.pay
 * @author jun
 */

@Entity
@Table(name = "SYS_ROUTE_CUST_CONFIG")
public class SysRouteCustomerConfig extends BaseEntity{
	/**
	 * 所属银行接口
	 */
	private String bankInterface; 
	/**
	 * 银行商户号
	 */
	private String bankCustomerNo; 
	/**
	 * 创建时间
	 */
	private Date createTime;  
	/**
	 * 状态
	 */
	private Status status;
	
	@Column(name = "BANK_INTERFACE", length = 30)
	public String getBankInterface() {
		return bankInterface;
	}
	
	public void setBankInterface(String bankInterface) {
		this.bankInterface = bankInterface;
	}
	@Column(name = "BANK_CUSTOMER_NO", length = 50)
	public String getBankCustomerNo() {
		return bankCustomerNo;
	}
	
	public void setBankCustomerNo(String bankCustomerNo) {
		this.bankCustomerNo = bankCustomerNo;
	}
	
	@Column(name = "CREATE_TIME",columnDefinition = "DATETIME")
	public Date getCreateTime() {
		return createTime;
	}
	
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Enumerated(value = EnumType.STRING)
	@Column(name = "STATUS", columnDefinition = "VARCHAR(30)")
	public Status getStatus() {
		return status;
	}
	
	public void setStatus(Status status) {
		this.status = status;
	}  
	
	
	
	
}
