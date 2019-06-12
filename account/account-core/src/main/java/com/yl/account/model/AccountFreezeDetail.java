package com.yl.account.model;

import java.util.Date;

import com.yl.account.enums.FreezeStatus;

/**
 * 冻结明细
 * 
 * @author 聚合支付有限公司
 * @since 2016年3月24日
 * @version V1.0.0
 */
public class AccountFreezeDetail extends AutoStringIDModel {

	private static final long serialVersionUID = -4861182303057902332L;

	/** 系统编码 */
	private String systemCode;
	/** 业务编码 */
	private String bussinessCode;
	/** 系统交易流水 */
	private String transFlow;
	/** 冻结编码 */
	private String freezeNo;
	/** 冻结日期 */
	private Date freezeDate;
	/** 账号 */
	private String accountNo;
	/** 预冻金额 */
	private Double preFreezeBalance;
	/** 预冻日期 */
	private Date preFreezeDate;
	/** 冻结金额 */
	private double freezeBalance;
	/** 请款金额 */
	private Double requestBalance;
	/** 请款次数 */
	private Integer requestBalanceCount;
	/** 冻结状态 */
	private FreezeStatus status;
	/** 冻结期限 */
	private Date freezeLimit;

	public String getSystemCode() {
		return systemCode;
	}

	public void setSystemCode(String systemCode) {
		this.systemCode = systemCode;
	}

	public String getBussinessCode() {
		return bussinessCode;
	}

	public void setBussinessCode(String bussinessCode) {
		this.bussinessCode = bussinessCode;
	}

	public String getTransFlow() {
		return transFlow;
	}

	public void setTransFlow(String transFlow) {
		this.transFlow = transFlow;
	}

	public String getFreezeNo() {
		return freezeNo;
	}

	public void setFreezeNo(String freezeNo) {
		this.freezeNo = freezeNo;
	}

	public Date getFreezeDate() {
		return freezeDate;
	}

	public void setFreezeDate(Date freezeDate) {
		this.freezeDate = freezeDate;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public Double getPreFreezeBalance() {
		return preFreezeBalance;
	}

	public void setPreFreezeBalance(Double preFreezeBalance) {
		this.preFreezeBalance = preFreezeBalance;
	}

	public Date getPreFreezeDate() {
		return preFreezeDate;
	}

	public void setPreFreezeDate(Date preFreezeDate) {
		this.preFreezeDate = preFreezeDate;
	}

	public double getFreezeBalance() {
		return freezeBalance;
	}

	public void setFreezeBalance(double freezeBalance) {
		this.freezeBalance = freezeBalance;
	}

	public Double getRequestBalance() {
		return requestBalance;
	}

	public void setRequestBalance(Double requestBalance) {
		this.requestBalance = requestBalance;
	}

	public Integer getRequestBalanceCount() {
		return requestBalanceCount;
	}

	public void setRequestBalanceCount(Integer requestBalanceCount) {
		this.requestBalanceCount = requestBalanceCount;
	}

	public FreezeStatus getStatus() {
		return status;
	}

	public void setStatus(FreezeStatus status) {
		this.status = status;
	}

	public Date getFreezeLimit() {
		return freezeLimit;
	}

	public void setFreezeLimit(Date freezeLimit) {
		this.freezeLimit = freezeLimit;
	}

	@Override
	public String toString() {
		return "AccountFreezeDetail [systemCode=" + systemCode + ", bussinessCode=" + bussinessCode + ", transFlow=" + transFlow + ", freezeNo=" + freezeNo
				+ ", freezeDate=" + freezeDate + ", accountNo=" + accountNo + ", preFreezeBalance=" + preFreezeBalance + ", preFreezeDate=" + preFreezeDate
				+ ", freezeBalance=" + freezeBalance + ", requestBalance=" + requestBalance + ", requestBalanceCount=" + requestBalanceCount + ", status=" + status
				+ ", freezeLimit=" + freezeLimit + "]";
	}

}
