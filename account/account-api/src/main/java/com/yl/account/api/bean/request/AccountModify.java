package com.yl.account.api.bean.request;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.yl.account.api.bean.AccountBussinessInterfaceBean;
import com.yl.account.api.enums.AccountStatus;

/**
 * 账务账户修改请求
 * 
 * @author 聚合支付有限公司
 * @since 2016年2月24日
 * @version V1.0.0
 */
public class AccountModify extends AccountBussinessInterfaceBean {

	private static final long serialVersionUID = 7960813604837352044L;

	/** 账户编号 */
	@NotBlank
	private String accountNo;
	/** 用户编号 */
	@NotBlank
	private String userNo;
	/** 账户状态 */
	@NotNull
	private AccountStatus accountStatus;
	/** 入账周期 */
 	private Integer cycle;

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

	public AccountStatus getAccountStatus() {
		return accountStatus;
	}

	public void setAccountStatus(AccountStatus accountStatus) {
		this.accountStatus = accountStatus;
	}
	

	

	public Integer getCycle() {
		return cycle;
	}

	public void setCycle(Integer cycle) {
		this.cycle = cycle;
	}

	@Override
	public String toString() {
		return "AccountModify [accountNo=" + accountNo + ", userNo=" + userNo
				+ ", accountStatus=" + accountStatus + ", cycle=" + cycle + "]";
	}

	

}
