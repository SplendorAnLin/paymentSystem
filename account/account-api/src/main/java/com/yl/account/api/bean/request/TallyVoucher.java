/**
 * 
 */
package com.yl.account.api.bean.request;

import java.util.Date;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.yl.account.api.enums.Currency;
import com.yl.account.api.enums.FundSymbol;

/**
 * 复合记账父类
 * 
 * @author 聚合支付有限公司
 * @since 2016年2月24日
 * @version V1.0.0
 */
public class TallyVoucher extends TradeVoucher {

	private static final long serialVersionUID = -8485010024628084727L;
	/** 账号 */
	@NotBlank
	private String accountNo;
	/** 币种 */
	@NotNull
	private Currency currency;
	/** 系统交易订单号 */
	@NotBlank
	private String transOrder;
	/** 交易日期 */
	private Date transDate;
	/** 交易金额 */
	@Min(value = 0)
	private double amount;
	/** 资金标识 */
	private FundSymbol symbol;
	/** 手续费 */
	@Min(value = 0)
	private Double fee;
	/** 手续费方向 */
	private FundSymbol feeSymbol;
	/** 待入账日期 */
	private Date waitAccountDate;

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
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
		return "TallyVoucher [accountNo=" + accountNo + ", currency=" + currency + ", transOrder=" + transOrder + ", transDate=" + transDate + ", amount=" + amount
				+ ", symbol=" + symbol + ", fee=" + fee + ", feeSymbol=" + feeSymbol + ", waitAccountDate=" + waitAccountDate + "]";
	}

}
