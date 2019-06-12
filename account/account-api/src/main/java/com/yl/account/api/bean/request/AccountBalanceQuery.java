package com.yl.account.api.bean.request;

import com.yl.account.api.bean.AccountBussinessInterfaceBean;
import com.yl.account.api.enums.AccountType;
import com.yl.account.api.enums.UserRole;

/**
 * 账户余额查询请求
 * 
 * @author 聚合支付有限公司
 * @since 2016年2月24日
 * @version V1.0.0
 */
public class AccountBalanceQuery extends AccountBussinessInterfaceBean {

	private static final long serialVersionUID = 3260758590845950885L;

	/** 账号 */
	private String accountNo;
	/** 用户号 */
	private String userNo;
	/** 用户角色 */
	private UserRole userRole;
	/** 账户类型 */
	private AccountType accountType;

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
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

	public AccountType getAccountType() {
		return accountType;
	}

	public void setAccountType(AccountType accountType) {
		this.accountType = accountType;
	}

	@Override
	public String toString() {
		return "AccountBalanceQuery [accountNo=" + accountNo + ", userNo=" + userNo + ", userRole=" + userRole + ", accountType=" + accountType + "]";
	}

}
