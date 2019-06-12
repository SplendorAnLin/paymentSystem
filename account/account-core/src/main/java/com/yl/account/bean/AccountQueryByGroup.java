package com.yl.account.bean;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.yl.account.enums.AccountType;
import com.yl.account.enums.Currency;
import com.yl.account.enums.UserRole;

/**
 * 账户信息查询请求-组合
 * 
 * @author 聚合支付有限公司
 * @since 2016年3月24日
 * @version V1.0.0
 */
public class AccountQueryByGroup extends TradeVoucher {

	private static final long serialVersionUID = -4229523702122862861L;

	/** 账户类型 */
	@NotNull
	private AccountType accountType = AccountType.CASH;
	/** 用户号 */
	@NotBlank
	private String userNo;
	/** 用户角色 */
	@NotNull
	private UserRole userRole;
	/** 币种 */
	@NotNull
	private Currency currency = Currency.RMB;

	public AccountType getAccountType() {
		return accountType;
	}

	public void setAccountType(AccountType accountType) {
		this.accountType = accountType;
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

}
