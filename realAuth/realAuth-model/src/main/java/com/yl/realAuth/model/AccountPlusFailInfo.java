package com.yl.realAuth.model;

import java.util.Date;

import com.yl.realAuth.enums.AccountPlusStatus;

/**
 * 入账结果信息
 * @author rui.wang
 * @since 2014年9月24日
 */
public class AccountPlusFailInfo extends AutoStringIDModel {

	private static final long serialVersionUID = 3789888027745893545L;
	/** 入账状态 */
	private AccountPlusStatus accountPlusStatus;;
	/** 入账次数 */
	private int accountCount;
	/** 订单号 */
	private String orderCode;
	/** 入账下次触发时间 */
	private Date nextFireTime;
	/** 手续费退回时间 */
	private Date accountFacadeTime;

	public AccountPlusStatus getAccountPlusStatus() {
		return accountPlusStatus;
	}

	public void setAccountPlusStatus(AccountPlusStatus accountPlusStatus) {
		this.accountPlusStatus = accountPlusStatus;
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

	public Date getNextFireTime() {
		return nextFireTime;
	}

	public void setNextFireTime(Date nextFireTime) {
		this.nextFireTime = nextFireTime;
	}

	public Date getAccountFacadeTime() {
		return accountFacadeTime;
	}

	public void setAccountFacadeTime(Date accountFacadeTime) {
		this.accountFacadeTime = accountFacadeTime;
	}
}
