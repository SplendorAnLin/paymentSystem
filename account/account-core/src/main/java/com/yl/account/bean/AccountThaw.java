package com.yl.account.bean;

/**
 * 账务解冻请求
 * 
 * @author 聚合支付有限公司
 * @since 2016年3月11日
 * @version V1.0.0
 */
public class AccountThaw extends TradeVoucher {

	private static final long serialVersionUID = 7751199991794980790L;

	/** 账号 */
	private String accountNo;
	/** 冻结编号 */
	private String freezeNo;

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getFreezeNo() {
		return freezeNo;
	}

	public void setFreezeNo(String freezeNo) {
		this.freezeNo = freezeNo;
	}

	@Override
	public String toString() {
		return "AccountThaw [accountNo=" + accountNo + ", freezeNo=" + freezeNo + "]";
	}

}
