package com.yl.account.model;

import com.yl.account.enums.AccountType;

/**
 * 账户行为
 * 
 * @author 聚合支付有限公司
 * @since 2016年3月24日
 * @version V1.0.0
 */
public class AccountBehavior extends AutoStringIDModel {

	private static final long serialVersionUID = -9025319249575664658L;

	/** 账户编号 */
	private String accountNo;
	/** 账户编号 */
	private AccountType accountType;
	/** 预冻结次数 */
	private int preFreezeNum;

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

	public int getPreFreezeCount() {
		return preFreezeNum;
	}

	public void setPreFreezeCount(int preFreezeCount) {
		this.preFreezeNum = preFreezeCount;
	}

	@Override
	public String toString() {
		return "AccountBehavior [accountNo=" + accountNo + ", accountType=" + accountType + ", preFreezeNum=" + preFreezeNum + "]";
	}

}
