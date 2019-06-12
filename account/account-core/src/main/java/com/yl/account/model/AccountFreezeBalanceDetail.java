package com.yl.account.model;

import java.util.Date;

import com.yl.account.enums.Currency;

/**
 * 账务冻结资金明细
 * 
 * @author 聚合支付有限公司
 * @since 2016年3月24日
 * @version V1.0.0
 */
public class AccountFreezeBalanceDetail extends AutoStringIDModel {

	private static final long serialVersionUID = 4542261934244078386L;

	/** 账号 */
	private String accountNo;
	/** 交易金额 */
	private double transAmount;
	/** 处理时间 */
	private Date handleTime;
	/** 系统编码 */
	private String systemCode;
	/** 系统交易流水 */
	private String transFlow;
	/** 币种 */
	private Currency currency;
	/** 冻结期限 */
	private Date freezeLimit;
	/** 冻结处理类型 */
	private String freezeHandleType;

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public double getTransAmount() {
		return transAmount;
	}

	public void setTransAmount(double transAmount) {
		this.transAmount = transAmount;
	}

	public Date getHandleTime() {
		return handleTime;
	}

	public void setHandleTime(Date handleTime) {
		this.handleTime = handleTime;
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

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	public Date getFreezeLimit() {
		return freezeLimit;
	}

	public void setFreezeLimit(Date freezeLimit) {
		this.freezeLimit = freezeLimit;
	}

	public String getFreezeHandleType() {
		return freezeHandleType;
	}

	public void setFreezeHandleType(String freezeHandleType) {
		this.freezeHandleType = freezeHandleType;
	}

	@Override
	public String toString() {
		return "AccountFreezeBalanceDetail [accountNo=" + accountNo + ", transAmount=" + transAmount + ", handleTime=" + handleTime + ", systemCode=" + systemCode
				+ ", transFlow=" + transFlow + ", currency=" + currency + ", freezeLimit=" + freezeLimit + ", freezeHandleType=" + freezeHandleType + "]";
	}

}
