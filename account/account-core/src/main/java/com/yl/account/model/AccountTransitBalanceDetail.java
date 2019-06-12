package com.yl.account.model;

import java.util.Date;

import com.yl.account.enums.Currency;
import com.yl.account.enums.FundSymbol;

/**
 * 账务在途资金记录
 * 
 * @author 聚合支付有限公司
 * @since 2016年3月24日
 * @version V1.0.0
 */
public class AccountTransitBalanceDetail extends AutoStringIDModel {

	private static final long serialVersionUID = 8335808280764381059L;

	/** 账号 */
	private String accountNo;
	/** 资金标示 */
	private FundSymbol symbol;
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
	/** 业务类型 */
	private String bussinessCode;
	/** 待入账日期 */
	private Date waitAccountDate;

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public FundSymbol getSymbol() {
		return symbol;
	}

	public void setSymbol(FundSymbol symbol) {
		this.symbol = symbol;
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

	public String getBussinessCode() {
		return bussinessCode;
	}

	public void setBussinessCode(String bussinessCode) {
		this.bussinessCode = bussinessCode;
	}

	public Date getWaitAccountDate() {
		return waitAccountDate;
	}

	public void setWaitAccountDate(Date waitAccountDate) {
		this.waitAccountDate = waitAccountDate;
	}

	@Override
	public String toString() {
		return "AccountTransitBalanceDetail [accountNo=" + accountNo + ", symbol=" + symbol + ", transAmount=" + transAmount + ", handleTime=" + handleTime
				+ ", systemCode=" + systemCode + ", transFlow=" + transFlow + ", currency=" + currency + ", bussinessCode=" + bussinessCode + ", waitAccountDate="
				+ waitAccountDate + "]";
	}

}
