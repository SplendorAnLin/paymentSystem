package com.yl.account.api.bean.response;

import com.yl.account.api.bean.BussinessResponse;
import com.yl.account.api.enums.AccountType;

/**
 * 账务开户响应
 * 
 * @author 聚合支付有限公司
 * @since 2016年5月22日
 * @version V1.0.0
 */
public class AccountOpenResponse extends BussinessResponse {

	private static final long serialVersionUID = -7082203712117965240L;

	/** 账户编号 */
	private String accountNo;
	/** 账户类型 */
	private AccountType accountType;
	/** 用户号 */
	private String userNo;

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

	@Override
	public String toString() {
		return "AccountOpenResponse [accountNo=" + accountNo + ", accountType=" + accountType + ", userNo=" + userNo + "]";
	}

}
