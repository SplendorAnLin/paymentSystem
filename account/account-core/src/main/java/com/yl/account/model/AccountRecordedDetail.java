package com.yl.account.model;

import java.util.Date;

import com.yl.account.enums.Currency;
import com.yl.account.enums.FundSymbol;
import com.yl.account.enums.UserRole;

/**
 * 账务入账明细
 * 
 * @author 聚合支付有限公司
 * @since 2016年3月24日
 * @version V1.0.0
 */
public class AccountRecordedDetail extends AutoStringIDModel {

	private static final long serialVersionUID = -448266181523250727L;

	/** 用户编号 */
	private String userNo;
	/** 用户角色 */
	private UserRole userRole;
	/** 系统请求流水号 */
	private String systemFlowId;
	/** 业务类型 */
	private String bussinessCode;
	/** 币种 */
	private Currency currency;
	/** 账号 */
	private String accountNo;
	/** 资金标示 */
	private FundSymbol symbol;
	/** 系统编码 */
	private String systemCode;
	/** 交易流水 */
	private String transFlow;
	/** 交易时间 */
	private Date transTime;
	/** 待入账日期 */
	private Date waitAccountDate;
	/** 交易金额 */
	private double transAmount;
	/** 账户余额 */
	private double remainBalance;

	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	public UserRole getUserRole() {
		return userRole;
	}

	public void setUserRole(UserRole userRole) {
		this.userRole = userRole;
	}

	public String getSystemFlowId() {
		return systemFlowId;
	}

	public void setSystemFlowId(String systemFlowId) {
		this.systemFlowId = systemFlowId;
	}

	public String getBussinessCode() {
		return bussinessCode;
	}

	public void setBussinessCode(String bussinessCode) {
		this.bussinessCode = bussinessCode;
	}

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

	public Date getWaitAccountDate() {
		return waitAccountDate;
	}

	public void setWaitAccountDate(Date waitAccountDate) {
		this.waitAccountDate = waitAccountDate;
	}

	public double getTransAmount() {
		return transAmount;
	}

	public void setTransAmount(double transAmount) {
		this.transAmount = transAmount;
	}

	public double getRemainBalance() {
		return remainBalance;
	}

	public void setRemainBalance(double remainBalance) {
		this.remainBalance = remainBalance;
	}

	@Override
	public String toString() {
		return "AccountRecordedDetail [userNo=" + userNo + ", userRole=" + userRole + ", systemFlowId=" + systemFlowId + ", bussinessCode=" + bussinessCode
				+ ", currency=" + currency + ", accountNo=" + accountNo + ", symbol=" + symbol + ", systemCode=" + systemCode + ", transFlow=" + transFlow
				+ ", transTime=" + transTime + ", waitAccountDate=" + waitAccountDate + ", transAmount=" + transAmount + ", remainBalance=" + remainBalance + "]";
	}

}
