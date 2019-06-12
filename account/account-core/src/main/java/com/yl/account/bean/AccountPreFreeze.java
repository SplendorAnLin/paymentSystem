package com.yl.account.bean;

import java.util.Date;

import javax.validation.constraints.Min;

/**
 * 账务预冻请求
 * 
 * @author 聚合支付有限公司
 * @since 2016年3月24日
 * @version V1.0.0
 */
public class AccountPreFreeze extends TradeVoucher {

	private static final long serialVersionUID = -2179858903190378133L;

	/** 账号 */
	private String accountNo;
	/** 预冻结金额 */
	@Min(value = 0)
	private double preFreezeAmount;
	/** 冻结期限 */
	private Date freezeLimit;

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public double getPreFreezeAmount() {
		return preFreezeAmount;
	}

	public void setPreFreezeAmount(double preFreezeAmount) {
		this.preFreezeAmount = preFreezeAmount;
	}

	public Date getFreezeLimit() {
		return freezeLimit;
	}

	public void setFreezeLimit(Date freezeLimit) {
		this.freezeLimit = freezeLimit;
	}

	@Override
	public String toString() {
		return "AccountPreFreeze [accountNo=" + accountNo + ", preFreezeAmount=" + preFreezeAmount + ", freezeLimit=" + freezeLimit + "]";
	}

}
