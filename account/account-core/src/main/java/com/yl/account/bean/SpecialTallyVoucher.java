/**
 * 
 */
package com.yl.account.bean;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.yl.account.enums.AccountType;
import com.yl.account.enums.Currency;
import com.yl.account.enums.FundSymbol;
import com.yl.account.enums.UserRole;

/**
 * 记账、入账父类
 * 
 * @author 聚合支付有限公司
 * @since 2016年3月24日
 * @version V1.0.0
 */
public class SpecialTallyVoucher extends TradeVoucher {

	private static final long serialVersionUID = -8205720117442699993L;

	/** 用户号 */
	@NotBlank
	private String userNo;
	/** 用户角色 */
	@NotNull
	private UserRole userRole;
	/** 账户类型 */
	@NotNull
	private AccountType accountType;
	/** 币种 */
	@NotNull
	private Currency currency;
	/** 系统交易订单号 */
	@NotBlank
	private String transOrder;
	/** 交易日期 */
	private Date transDate;
	/** 交易金额 */
	@NotNull
	private double amount;
	/** 资金标识 */
	@NotNull
	private FundSymbol symbol;
	/** 手续费 */
	private Double fee;
	/** 手续费方向 */
	private FundSymbol feeSymbol;
	/** 待入账日期 */
	private Date waitAccountDate;

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

	public AccountType getAccountType() {
		return accountType;
	}

	public void setAccountType(AccountType accountType) {
		this.accountType = accountType;
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	public String getTransOrder() {
		return transOrder;
	}

	public void setTransOrder(String transOrder) {
		this.transOrder = transOrder;
	}

	public Date getTransDate() {
		return transDate;
	}

	public void setTransDate(Date transDate) {
		this.transDate = transDate;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public FundSymbol getSymbol() {
		return symbol;
	}

	public void setSymbol(FundSymbol symbol) {
		this.symbol = symbol;
	}

	public Double getFee() {
		return fee;
	}

	public void setFee(Double fee) {
		this.fee = fee;
	}

	public FundSymbol getFeeSymbol() {
		return feeSymbol;
	}

	public void setFeeSymbol(FundSymbol feeSymbol) {
		this.feeSymbol = feeSymbol;
	}

	public Date getWaitAccountDate() {
		return waitAccountDate;
	}

	public void setWaitAccountDate(Date waitAccountDate) {
		this.waitAccountDate = waitAccountDate;
	}

	@Override
	public String toString() {
		return "SpecialTallyVoucher [userNo=" + userNo + ", userRole=" + userRole + ", accountType=" + accountType + ", currency=" + currency + ", transOrder="
				+ transOrder + ", transDate=" + transDate + ", amount=" + amount + ", symbol=" + symbol + ", fee=" + fee + ", feeSymbol=" + feeSymbol
				+ ", waitAccountDate=" + waitAccountDate + "]";
	}

}
