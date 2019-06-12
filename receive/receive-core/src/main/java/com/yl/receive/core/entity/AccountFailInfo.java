package com.yl.receive.core.entity;

import java.io.Serializable;
import java.util.Date;

import com.yl.receive.core.enums.AccountFailStatus;

/**
 * 入账结果信息
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月25日
 * @version V1.0.0
 */
public class AccountFailInfo extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 3789888027745893545L;
	/** 入账状态 */
	private AccountFailStatus accountStatus;;
	/** 入账次数 */
	private int accountCount;
	/** 订单号 */
	private String orderCode;
	/** 入账金额 */
	private double amount;
	/** 入账时间 */
	private Date accountFacadeTime;
	/** 入账下次触发时间 */
	private Date nextFireTime;

	public AccountFailStatus getAccountStatus() {
		return accountStatus;
	}

	public void setAccountStatus(AccountFailStatus accountStatus) {
		this.accountStatus = accountStatus;
	}

	public int getAccountCount() {
		return accountCount;
	}

	public void setAccountCount(int accountCount) {
		this.accountCount = accountCount;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Date getAccountFacadeTime() {
		return accountFacadeTime;
	}

	public void setAccountFacadeTime(Date accountFacadeTime) {
		this.accountFacadeTime = accountFacadeTime;
	}

	public Date getNextFireTime() {
		return nextFireTime;
	}

	public void setNextFireTime(Date nextFireTime) {
		this.nextFireTime = nextFireTime;
	}

	@Override
	public String toString() {
		return "AccountFailInfo [accountStatus=" + accountStatus + ", accountCount=" + accountCount + ", orderCode=" + orderCode + ", amount=" + amount
				+ ", accountFacadeTime=" + accountFacadeTime + ", nextFireTime=" + nextFireTime + "]";
	}

}
