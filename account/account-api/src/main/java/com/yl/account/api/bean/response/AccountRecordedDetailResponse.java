package com.yl.account.api.bean.response;

import java.util.Date;

import com.yl.account.api.bean.BussinessResponse;
import com.yl.account.api.enums.AccountType;
import com.yl.account.api.enums.Currency;
import com.yl.account.api.enums.FundSymbol;

/**
 * 账务入账明细
 * 
 * @author 聚合支付有限公司
 * @since 2016年5月22日
 * @version V1.0.0
 */
public class AccountRecordedDetailResponse extends BussinessResponse {

	private static final long serialVersionUID = -448266181523250727L;

	/** 币种 */
	private Currency currency;
	/** 账号 */
	private String accountNo;
	/** 账户类型 */
	private AccountType accountType;
	/** 资金标示 */
	private FundSymbol symbol;
	/** 系统编码 */
	private String systemCode;
	/** 交易流水 */
	private String transFlow;
	/** 交易时间 */
	private Date transTime;
	/** 交易金额 */
	private String transAmount;
	/** 通道编号 */
	private String channelNo;
	/** 用户号 */
	private String userNo;

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public AccountType getAccountType() {
		return accountType;
	}

	public void setAccountType(AccountType accountType) {
		this.accountType = accountType;
	}

	public FundSymbol getSymbol() {
		return symbol;
	}

	public void setSymbol(FundSymbol symbol) {
		this.symbol = symbol;
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

	public String getTransAmount() {
		return transAmount;
	}

	public void setTransAmount(String transAmount) {
		this.transAmount = transAmount;
	}

	public String getChannelNo() {
		return channelNo;
	}

	public void setChannelNo(String channelNo) {
		this.channelNo = channelNo;
	}

	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	@Override
	public String toString() {
		return "AccountRecordedDetailResponse [currency=" + currency + ", accountNo=" + accountNo + ", accountType=" + accountType + ", symbol=" + symbol
				+ ", systemCode=" + systemCode + ", transFlow=" + transFlow + ", transTime=" + transTime + ", transAmount=" + transAmount + ", channelNo="
				+ channelNo + ", userNo=" + userNo + "]";
	}

}
