package com.yl.account.api.bean.request;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.yl.account.api.enums.DelayType;

/**
 * 账务延迟入账请求
 * 
 * @author 聚合支付有限公司
 * @since 2016年2月24日
 * @version V1.0.0
 */
public class AccountDelayTally extends TradeVoucher {

	private static final long serialVersionUID = 8549860075371137817L;

	/** 账号 */
	@NotBlank
	private String accountNo;
	/** 业务系统原流水号 */
	@NotBlank
	private String origTransOrder;
	/** 业务系统原业务类型 */
	@NotBlank
	private String origBussinessCode;
	/** 待入账日期 */
	private Date waitAccountDate;
	/** 延迟记账类型 */
	@NotNull
	private DelayType delayType;

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getOrigTransOrder() {
		return origTransOrder;
	}

	public void setOrigTransOrder(String origTransOrder) {
		this.origTransOrder = origTransOrder;
	}

	public String getOrigBussinessCode() {
		return origBussinessCode;
	}

	public void setOrigBussinessCode(String origBussinessCode) {
		this.origBussinessCode = origBussinessCode;
	}

	public Date getWaitAccountDate() {
		return waitAccountDate;
	}

	public void setWaitAccountDate(Date waitAccountDate) {
		this.waitAccountDate = waitAccountDate;
	}

	public DelayType getDelayType() {
		return delayType;
	}

	public void setDelayType(DelayType delayType) {
		this.delayType = delayType;
	}

	@Override
	public String toString() {
		return "AccountDelayTally [accountNo=" + accountNo + ", origTransOrder=" + origTransOrder + ", origBussinessCode=" + origBussinessCode
				+ ", waitAccountDate=" + waitAccountDate + ", delayType=" + delayType + "]";
	}

}
