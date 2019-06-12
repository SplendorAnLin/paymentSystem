package com.yl.pay.pos.entity;

import com.yl.pay.pos.enums.Status;
import com.yl.pay.pos.enums.SysRouteCustConfDetailUseStatus;

import javax.persistence.*;
import java.util.Date;


/**
 * Title: 系统路由商编配置明细  / 系统路由商编配置的字表
 * Description: 用于指定银行商户编号的交易分流，为主商编衍生出来的银行商编
 * Copyright: Copyright (c)2014
 * Company: com.yl.pay
 * @author jun
 */

@Entity
@Table(name = "SYS_ROUTE_CUST_CONFIG_DETAIL")
public class SysRouteCustomerConfigDetail extends BaseEntity{
	/**
	 * 系统路由商编配置
	 * 父表
	 */
	private SysRouteCustomerConfig sysRouteCustomerConfig;
	/**
	 * 所属银行接口
	 */
	private String bankInterface; 
	/**
	 * 银行商户号
	 */
	private String bankCustomerNo; 
	/**
	 * 银行商户名
	 */
	private String bankCustomerName;
	/**
	 * 当日累计金额
	 */
	private Double accumulatedAmount;
	/**
	 * 限制金额
	 */
	private Double limitAmount;
	/**
	 * 最后交易时间
	 */
	private Date lastTransTime;	
	/**
	 * 使用状态
	 */
	private SysRouteCustConfDetailUseStatus useStatus;
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
	
	@Column(name = "BANK_CUSTOMER_NAME", length = 128)
	public String getBankCustomerName() {
		return bankCustomerName;
	}
	
	public void setBankCustomerName(String bankCustomerName) {
		this.bankCustomerName = bankCustomerName;
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

	@Column(name = "ACCUMULATED_AMOUNT",columnDefinition = "double", length = 11, scale = 2)
	public Double getAccumulatedAmount() {
		return accumulatedAmount;
	}

	public void setAccumulatedAmount(Double accumulatedAmount) {
		this.accumulatedAmount = accumulatedAmount;
	}
	
	@Column(name = "LIMIT_AMOUNT",columnDefinition = "double", length = 11, scale = 2)
	public Double getLimitAmount() {
		return limitAmount;
	}
	
	public void setLimitAmount(Double limitAmount) {
		this.limitAmount = limitAmount;
	}
	
	@ManyToOne(fetch = FetchType.LAZY )
	@JoinColumn(name = "SYS_ROUTE_Id")
	public SysRouteCustomerConfig getSysRouteCustomerConfig() {
		return sysRouteCustomerConfig;
	}
	public void setSysRouteCustomerConfig(
			SysRouteCustomerConfig sysRouteCustomerConfig) {
		this.sysRouteCustomerConfig = sysRouteCustomerConfig;
	}
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "USE_STATUS", columnDefinition = "VARCHAR(50)")
	public SysRouteCustConfDetailUseStatus getUseStatus() {
		return useStatus;
	}
	public void setUseStatus(SysRouteCustConfDetailUseStatus useStatus) {
		this.useStatus = useStatus;
	}
	
	@Column(name = "LAST_TRANS_TIME",columnDefinition = "DATETIME")
	public Date getLastTransTime() {
		return lastTransTime;
	}
	public void setLastTransTime(Date lastTransTime) {
		this.lastTransTime = lastTransTime;
	}
	
	
	
}
