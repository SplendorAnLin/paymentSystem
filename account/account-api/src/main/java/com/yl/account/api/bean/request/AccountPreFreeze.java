package com.yl.account.api.bean.request;

import java.util.Date;

import javax.validation.constraints.Future;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

/**
 * 账务预冻请求
 * 
 * @author 聚合支付有限公司
 * @since 2016年2月24日
 * @version V1.0.0
 */
public class AccountPreFreeze extends TradeVoucher {

	private static final long serialVersionUID = -2179858903190378133L;

	/** 账号 */
	@NotBlank
	private String accountNo;
	/** 预冻结金额 */
	@NotNull
	@Min(value = 0)
	private double preFreezeAmount;
	/** 冻结期限 */
	@NotNull
	@Future
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

}
