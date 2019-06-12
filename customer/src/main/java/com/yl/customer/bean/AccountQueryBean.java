/**
 *
 */
package com.yl.customer.bean;

import java.util.Date;

import com.yl.account.api.enums.AccountStatus;
import com.yl.account.api.enums.AccountType;
import com.yl.account.api.enums.UserRole;

/**
 * 账户查询Bean
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月3日
 * @version V1.0.0
 */
public class AccountQueryBean {
	/** 账号 */
	private String accountNo;
	/** 账户类型 */
	private AccountType accountType;
	/** 账户状态 */
	private AccountStatus status;
	/** 用户号 */
	private String userNo;
	/** 用户角色 */
	private UserRole userRole;

	private Date openStartDate;

	private Date openEndDate;

	private String startBalance;

	private String endBanlance;

	private String startTransitBalance;

	private String endTransitBalance;

	private String startFreezeBalance;

	private String endFreezeBalance;

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

	public AccountStatus getStatus() {
		return status;
	}

	public void setStatus(AccountStatus status) {
		this.status = status;
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

	public Date getOpenStartDate() {
		return openStartDate;
	}

	public void setOpenStartDate(Date openStartDate) {
		this.openStartDate = openStartDate;
	}

	public Date getOpenEndDate() {
		return openEndDate;
	}

	public void setOpenEndDate(Date openEndDate) {
		this.openEndDate = openEndDate;
	}

	public String getStartBalance() {
		return startBalance;
	}

	public void setStartBalance(String startBalance) {
		this.startBalance = startBalance;
	}

	public String getEndBanlance() {
		return endBanlance;
	}

	public void setEndBanlance(String endBanlance) {
		this.endBanlance = endBanlance;
	}

	public String getStartTransitBalance() {
		return startTransitBalance;
	}

	public void setStartTransitBalance(String startTransitBalance) {
		this.startTransitBalance = startTransitBalance;
	}

	public String getEndTransitBalance() {
		return endTransitBalance;
	}

	public void setEndTransitBalance(String endTransitBalance) {
		this.endTransitBalance = endTransitBalance;
	}

	public String getStartFreezeBalance() {
		return startFreezeBalance;
	}

	public void setStartFreezeBalance(String startFreezeBalance) {
		this.startFreezeBalance = startFreezeBalance;
	}

	public String getEndFreezeBalance() {
		return endFreezeBalance;
	}

	public void setEndFreezeBalance(String endFreezeBalance) {
		this.endFreezeBalance = endFreezeBalance;
	}

}
