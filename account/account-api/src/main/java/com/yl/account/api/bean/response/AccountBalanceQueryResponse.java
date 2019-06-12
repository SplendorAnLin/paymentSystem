package com.yl.account.api.bean.response;

import com.yl.account.api.bean.BussinessResponse;
import com.yl.account.api.enums.AccountStatus;

/**
 * 账户余额查询响应
 * 
 * @author 聚合支付有限公司
 * @since 2016年3月24日
 * @version V1.0.0
 */
public class AccountBalanceQueryResponse extends BussinessResponse {

	private static final long serialVersionUID = -1994542022308653607L;
	/** 账号 */
	private String accountNo;
	/** 账户状态 */
	private AccountStatus status;
	/** 余额 */
	private double balance;
	/** 可用余额 */
	private double availavleBalance;
	/** 在途金额 */
	private double transitBalance;
	/** 在途可用金额 */
	private Double availableTransitBalance;
	/** 冻结金额 */
	private double freezeBalance;

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public AccountStatus getStatus() {
		return status;
	}

	public void setStatus(AccountStatus status) {
		this.status = status;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public double getAvailavleBalance() {
		return availavleBalance;
	}

	public void setAvailavleBalance(double availavleBalance) {
		this.availavleBalance = availavleBalance;
	}

	public double getTransitBalance() {
		return transitBalance;
	}

	public void setTransitBalance(double transitBalance) {
		this.transitBalance = transitBalance;
	}

	public Double getAvailableTransitBalance() {
		return availableTransitBalance;
	}

	public void setAvailableTransitBalance(Double availableTransitBalance) {
		this.availableTransitBalance = availableTransitBalance;
	}

	public double getFreezeBalance() {
		return freezeBalance;
	}

	public void setFreezeBalance(double freezeBalance) {
		this.freezeBalance = freezeBalance;
	}

	@Override
	public String toString() {
		return "AccountBalanceQueryResponse [accountNo=" + accountNo + ", status=" + status + ", balance=" + balance + ", availavleBalance=" + availavleBalance
				+ ", transitBalance=" + transitBalance + ", availableTransitBalance=" + availableTransitBalance + ", freezeBalance=" + freezeBalance + "]";
	}

}