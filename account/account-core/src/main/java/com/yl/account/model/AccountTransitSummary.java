package com.yl.account.model;

import java.util.Date;

import com.yl.account.enums.SummaryStatus;

/**
 * 账务非入账周期汇总
 * 
 * @author 聚合支付有限公司
 * @since 2016年6月21日
 * @version V1.0.0
 */
public class AccountTransitSummary extends AutoStringIDModel {

	private static final long serialVersionUID = 7852802967733884827L;

	/** 账号 */
	private String accountNo;
	/** 待入账日期 */
	private Date waitAccountDate;
	/** 待入账金额 */
	private Double waitAccountAmount;
	/** 汇总入账状态 */
	private SummaryStatus status;

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public Date getWaitAccountDate() {
		return waitAccountDate;
	}

	public void setWaitAccountDate(Date waitAccountDate) {
		this.waitAccountDate = waitAccountDate;
	}

	public Double getWaitAccountAmount() {
		return waitAccountAmount;
	}

	public void setWaitAccountAmount(Double waitAccountAmount) {
		this.waitAccountAmount = waitAccountAmount;
	}

	public SummaryStatus getStatus() {
		return status;
	}

	public void setStatus(SummaryStatus status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "AccountTransitSummary [accountNo=" + accountNo + ", waitAccountDate=" + waitAccountDate + ", waitAccountAmount=" + waitAccountAmount + ", status="
				+ status + "]";
	}

	@Override
	public AccountTransitSummary clone() throws CloneNotSupportedException {
		return (AccountTransitSummary) super.clone();
	}

}
