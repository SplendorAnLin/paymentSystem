package com.yl.account.model;

import java.util.Date;

import com.yl.account.enums.AccountStatus;
import com.yl.account.enums.AccountType;
import com.yl.account.enums.Currency;
import com.yl.account.enums.UserRole;

/**
 * 账户信息
 * 
 * @author 聚合支付有限公司
 * @since 2016年3月24日
 * @version V1.0.0
 */
public class Account extends AutoStringIDModel {

	private static final long serialVersionUID = 6425703607336780630L;

	/** 账户类型 */
	private AccountType type;
	/** 用户编号 */
	private String userNo;
	/** 用户角色 */
	private UserRole userRole;
	/** 币种 */
	private Currency currency;
	/** 总余额 */
	private double balance;
	/** 在途金额 */
	private double transitBalance;
	/** 冻结金额 */
	private double freezeBalance;
	/** 状态 */
	private AccountStatus status;
	/** 开户日期 */
	private Date openDate;
	/** 备注 */
	private String remark;
	/** 转可用标识 **/
	private Date ableDate = new Date();
	/** 入账周期 **/
	private int cycle;

	public AccountType getType() {
		return type;
	}

	public void setType(AccountType type) {
		this.type = type;
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

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public double getTransitBalance() {
		return transitBalance;
	}

	public void setTransitBalance(double transitBalance) {
		this.transitBalance = transitBalance;
	}

	public double getFreezeBalance() {
		return freezeBalance;
	}

	public void setFreezeBalance(double freezeBalance) {
		this.freezeBalance = freezeBalance;
	}

	public AccountStatus getStatus() {
		return status;
	}

	public void setStatus(AccountStatus status) {
		this.status = status;
	}

	public Date getOpenDate() {
		return openDate;
	}

	public void setOpenDate(Date openDate) {
		this.openDate = openDate;
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
		return "Account [type=" + type + ", userNo=" + userNo + ", userRole="
				+ userRole + ", currency=" + currency + ", balance=" + balance
				+ ", transitBalance=" + transitBalance + ", freezeBalance="
				+ freezeBalance + ", status=" + status + ", openDate="
				+ openDate + ", remark=" + remark + ", ableDate=" + ableDate
				+ ", cycle=" + cycle + "]";
	}

}
