package com.yl.account.api.bean.request;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.yl.account.api.bean.AccountBussinessInterfaceBean;
import com.yl.account.api.enums.AccountStatus;
import com.yl.account.api.enums.AccountType;
import com.yl.account.api.enums.Currency;
import com.yl.account.api.enums.UserRole;

/**
 * 账务开户请求
 * 
 * @author 聚合支付有限公司
 * @since 2016年2月24日
 * @version V1.0.0
 */
public class AccountOpen extends AccountBussinessInterfaceBean {

	private static final long serialVersionUID = 5804305860326178132L;

	/** 账户类型 */
	@NotNull
	private AccountType accountType;
	/** 账户状态 */
	@NotNull
	private AccountStatus status;
	/** 用户号 */
	@NotBlank
	private String userNo;
	/** 用户角色 */
	@NotNull
	private UserRole userRole;
	/** 币种 */
	@NotNull
	private Currency currency;
	/** 转可用标识 **/
	private Date ableDate;
	/** 入账周期 **/
	private int cycle;

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

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
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
		return "AccountOpen [accountType=" + accountType + ", status=" + status + ", userNo=" + userNo + ", userRole=" + userRole + ", currency=" + currency + "]";
	}

}
