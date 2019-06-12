package com.yl.account.api.bean.request;

import com.yl.account.api.bean.AccountBussinessInterfaceBean;
import com.yl.account.api.enums.AccountType;
import com.yl.account.api.enums.UserRole;

/**
 * 账户冻结明细查询请求
 * 
 * @author 聚合支付有限公司
 * @since 2016年2月24日
 * @version V1.0.0
 */
public class AccountFreezeDetailQuery extends AccountBussinessInterfaceBean {

	private static final long serialVersionUID = 5514359318829224739L;

	/** 账号 */
	private String accountNo;
	/** 账户类型 */
	private AccountType accountType;
	/** 用户号 */
	private String userNo;
	/** 用户角色 */
	private UserRole userRole;

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

}
