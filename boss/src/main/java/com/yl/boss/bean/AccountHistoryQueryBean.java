/**
 * 
 */
package com.yl.boss.bean;

import java.util.Date;

/**
 * 账户历史查询Bean
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月8日
 * @version V1.0.0
 */
public class AccountHistoryQueryBean {
	/** 用户编号 */
	private String userNo;
	/** 业务类型 */
	private String bussinessCode;
	/** 币种 */
	private String currency;
	/**角色类型**/
	private String userRole;
	/** 账号 */
	private String accountNo;
	/** 资金标示 */
	private String symbol;
	/** 系统编码 */
	private String systemCode;
	/** 交易流水 */
	private String transFlow;
	/** 交易时间 */
	private Date transTime;
	/** 交易金额 */
	private double transAmount;
	/** 交易开始日期 */
	private Date transStartTime;
	/** 交易结束日期 */
	private Date transEndTime;
	/** 创建开始日期 */
	private Date createStartTime;
	/** 创建结束日期 */
	private Date createEndTime;

	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	public String getBussinessCode() {
		return bussinessCode;
	}

	public void setBussinessCode(String bussinessCode) {
		this.bussinessCode = bussinessCode;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getSystemCode() {
		return systemCode;
	}

	public void setSystemCode(String systemCode) {
		this.systemCode = systemCode;
	}

	public String getTransFlow() {
		return transFlow;
	}

	public void setTransFlow(String transFlow) {
		this.transFlow = transFlow;
	}

	public Date getTransTime() {
		return transTime;
	}

	public void setTransTime(Date transTime) {
		this.transTime = transTime;
	}

	public double getTransAmount() {
		return transAmount;
	}

	public void setTransAmount(double transAmount) {
		this.transAmount = transAmount;
	}

	public Date getTransStartTime() {
		return transStartTime;
	}

	public void setTransStartTime(Date transStartTime) {
		this.transStartTime = transStartTime;
	}

	public Date getTransEndTime() {
		return transEndTime;
	}

	public void setTransEndTime(Date transEndTime) {
		this.transEndTime = transEndTime;
	}

	public Date getCreateStartTime() {
		return createStartTime;
	}

	public void setCreateStartTime(Date createStartTime) {
		this.createStartTime = createStartTime;
	}

	public Date getCreateEndTime() {
		return createEndTime;
	}

	public void setCreateEndTime(Date createEndTime) {
		this.createEndTime = createEndTime;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
/*
	public Map<String, Object> toMap() {
		Map<String, Object> map = new HashMap<String, Object>();
		if (StringUtils.isNotBlank(this.userNo))
			map.put("userNo", this.userNo);
		if (StringUtils.isNotBlank(this.bussinessCode))
			map.put("bussinessCode", this.bussinessCode);
		if (StringUtils.isNotBlank(this.accountNo))
			map.put("accountNo", this.accountNo);
		if (this.symbol != null)
			map.put("symbol", this.symbol);
		if (StringUtils.isNotBlank(this.systemCode))
			map.put("systemCode", this.systemCode);
		if (StringUtils.isNotBlank(this.transFlow))
			map.put("transFlow", this.transFlow);
		if (this.transStartTime != null)
			map.put("transStartTime", this.transStartTime);
		if (this.transEndTime != null)
			map.put("transEndTime", this.transEndTime);
		if (this.createStartTime != null)
			map.put("createStartTime", this.createStartTime);
		if (this.createEndTime != null)
			map.put("createEndTime", this.createEndTime);
		return map;
	}*/
}
