package com.yl.account.api.bean;

import java.io.Serializable;
import java.util.Date;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.yl.account.api.enums.Currency;
import com.yl.account.api.enums.FundSymbol;
import com.yl.account.api.enums.UserRole;

/**
 * 账务入账明细
 * 
 * @author 聚合支付有限公司
 * @since 2016年6月15日
 * @version V1.0.0
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountRecordedDetailBean implements Serializable {

	private static final long serialVersionUID = -7900825496254292839L;

	/** 明细编码 */
	private String code;
	/** 用户编号 */
	private String userNo;
	/** 用户角色 */
	private UserRole userRole;
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
	/** 创建日期 */
	private Date createTime;
	/** 账户余额 */
	private double remainBalance;
	/** 支付方式 */
	private String payType;

	/** my first code */
	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public double getRemainBalance() {
		return remainBalance;
	}

	public void setRemainBalance(double remainBalance) {
		this.remainBalance = remainBalance;
	}

	@Override
	public String toString() {
		return "AccountRecordedDetailBean [code=" + code + ", userNo=" + userNo + ", userRole=" + userRole + ", bussinessCode=" + bussinessCode + ", currency=" + currency + ", accountNo=" + accountNo + ", symbol=" + symbol + ", systemCode=" + systemCode + ", transFlow=" + transFlow + ", transTime="
				+ transTime + ", waitAccountDate=" + waitAccountDate + ", transAmount=" + transAmount + ", createTime=" + createTime + ", remainBalance=" + remainBalance + "]";
	}

}
