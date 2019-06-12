package com.yl.account.model;

import java.util.Date;

import com.yl.account.enums.AccountStatus;
import com.yl.account.enums.AccountType;
import com.yl.account.enums.FundSymbol;
import com.yl.account.enums.UserRole;

/**
 * 账务变更明细
 * 
 * @author 聚合支付有限公司
 * @since 2016年6月21日
 * @version V1.0.0
 */
public class AccountChangeDetail extends AutoStringIDModel {

	private static final long serialVersionUID = 7583187678966431369L;

	/** 账户编号 */
	private String accountNo;
	/** 账户类型 */
	private AccountType accountType;
	/** 账户状态 */
	private AccountStatus accountStatus;
	/** 系统编码 */
	private String systemCode;
	/** 系统流水号-唯一 */
	private String systemFlow;
	/** 业务类型码 */
	private String bussinessCode;
	/** 用户编号 */
	private String userNo;
	/** 用户角色 */
	private UserRole userRole;
	/** 账户余额 */
	private Double balance;
	/** 在途金额 */
	private Double transitBalance;
	/** 冻结金额 */
	private Double freezeBalance;
	/** 资金标识 */
	private FundSymbol symbol;
	/** 交易金额 */
	private Double amount;
	/** 转可用标识 **/
	private Date ableDate = new Date();
	/** 入账周期 **/
	private int cycle;
	/** 请求日期 */
	private Date requestTime;
	/** 操作人 */
	private String operator;
	/** 变更原因 */
	private String changeReason;
	/** 备注 */
	private String remark;

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

	public AccountStatus getAccountStatus() {
		return accountStatus;
	}

	public void setAccountStatus(AccountStatus accountStatus) {
		this.accountStatus = accountStatus;
	}

	public String getSystemCode() {
		return systemCode;
	}

	public void setSystemCode(String systemCode) {
		this.systemCode = systemCode;
	}

	public String getSystemFlow() {
		return systemFlow;
	}

	public void setSystemFlow(String systemFlow) {
		this.systemFlow = systemFlow;
	}

	public String getBussinessCode() {
		return bussinessCode;
	}

	public void setBussinessCode(String bussinessCode) {
		this.bussinessCode = bussinessCode;
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

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public Double getTransitBalance() {
		return transitBalance;
	}

	public void setTransitBalance(Double transitBalance) {
		this.transitBalance = transitBalance;
	}

	public Double getFreezeBalance() {
		return freezeBalance;
	}

	public void setFreezeBalance(Double freezeBalance) {
		this.freezeBalance = freezeBalance;
	}

	public FundSymbol getSymbol() {
		return symbol;
	}

	public void setSymbol(FundSymbol symbol) {
		this.symbol = symbol;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Date getRequestTime() {
		return requestTime;
	}

	public void setRequestTime(Date requestTime) {
		this.requestTime = requestTime;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getChangeReason() {
		return changeReason;
	}

	public void setChangeReason(String changeReason) {
		this.changeReason = changeReason;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getAbleDate() {
		return ableDate;
	}

	public void setAbleDate(Date ableDate) {
		this.ableDate = ableDate;
	}

	public int getCycle() {
		return cycle;
	}

	public void setCycle(int cycle) {
		this.cycle = cycle;
	}

	@Override
	public String toString() {
		return "AccountChangeDetail [accountNo=" + accountNo + ", accountType="
				+ accountType + ", accountStatus=" + accountStatus
				+ ", systemCode=" + systemCode + ", systemFlow=" + systemFlow
				+ ", bussinessCode=" + bussinessCode + ", userNo=" + userNo
				+ ", userRole=" + userRole + ", balance=" + balance
				+ ", transitBalance=" + transitBalance + ", freezeBalance="
				+ freezeBalance + ", symbol=" + symbol + ", amount=" + amount
				+ ", ableDate=" + ableDate + ", cycle=" + cycle
				+ ", requestTime=" + requestTime + ", operator=" + operator
				+ ", changeReason=" + changeReason + ", remark=" + remark + "]";
	}

}
