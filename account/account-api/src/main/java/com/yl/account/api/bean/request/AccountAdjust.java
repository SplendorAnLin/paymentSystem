package com.yl.account.api.bean.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.yl.account.api.enums.ExpireOperate;
import com.yl.account.api.enums.FundSymbol;

/**
 * 账户调账
 * 
 * @author 聚合支付有限公司
 * @since 2016年3月24日
 * @version V1.0.0
 */
public class AccountAdjust extends TradeVoucher {

	private static final long serialVersionUID = -5435674280988824903L;

	/** 账户编码 */
	@NotBlank
	private String accountNo;
	/** 调账方向 */
	@NotNull
	private FundSymbol fundSymbol;
	/** 调账金额 */
	@NotNull
	@Min(value = 0)
	private double amount;
	/** 系统交易订单号 */
	@NotBlank
	private String transOrder;
	/** 调增流程实例id */
	private String processInstanceId;
	/** 过期日期 */
	private Integer expireTime;
	/** 过期操作方式 */
	private ExpireOperate expireOperate;
	/** 调账原因 */
	private String reason;

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public FundSymbol getFundSymbol() {
		return fundSymbol;
	}

	public void setFundSymbol(FundSymbol fundSymbol) {
		this.fundSymbol = fundSymbol;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getTransOrder() {
		return transOrder;
	}

	public void setTransOrder(String transOrder) {
		this.transOrder = transOrder;
	}

	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	public Integer getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(Integer expireTime) {
		this.expireTime = expireTime;
	}

	public ExpireOperate getExpireOperate() {
		return expireOperate;
	}

	public void setExpireOperate(ExpireOperate expireOperate) {
		this.expireOperate = expireOperate;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	@Override
	public String toString() {
		return "AccountAdjust [accountNo=" + accountNo + ", fundSymbol=" + fundSymbol + ", amount=" + amount + ", transOrder=" + transOrder + ", processInstanceId="
				+ processInstanceId + ", expireTime=" + expireTime + ", expireOperate=" + expireOperate + ", reason=" + reason + "]";
	}

}
