package com.yl.dpay.core.entity;

import java.io.Serializable;
import java.util.Date;

import com.yl.dpay.core.enums.AccountType;
import com.yl.dpay.core.enums.AutoRequestStatus;
import com.yl.dpay.core.enums.OwnerRole;

/**
 * 自动代付请求
 *
 * @author AnLin
 * @since 2017年4月21日
 */
public class AutoRequest extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 4428381793883663174L;
	/**
	 * 持有者ID
	 */
	private String ownerId;
	/**
	 * 持有者角色
	 */
	private OwnerRole ownerRole;
	/**
	 * 金额
	 */
	private double amount;
	/**
	 * 操作来源
	 */
	private String operator;
	/**
	 * 银行编号
	 */
	private String bankCode;
	/**
	 * 银行名称
	 */
	private String bankName;
	/**
	 * 账户名称
	 */
	private String accountName;
	/**
	 * 账户编号
	 */
	private String accountNo;
	/**
	 * 账户类型
	 */
	private AccountType accountType;
	/**
	 * 发起状态
	 */
	private AutoRequestStatus autoRequestStatus;
	/**
	 * 订单号
	 */
	private String orderCode;
	/**
	 * 代付流水号
	 */
	private String flowNo;
	/**
	 * 编号
	 */
	private String code;
	/**
	 * 入账时间
	 */
	private Date accountDate;
	/**
	 * 发起时间
	 */
	private Date applyDate;

	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}

	public OwnerRole getOwnerRole() {
		return ownerRole;
	}

	public void setOwnerRole(OwnerRole ownerRole) {
		this.ownerRole = ownerRole;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public AccountType getAccountType() {
		return accountType;
	}

	public void setAccountType(AccountType accountType) {
		this.accountType = accountType;
	}

	public AutoRequestStatus getAutoRequestStatus() {
		return autoRequestStatus;
	}

	public void setAutoRequestStatus(AutoRequestStatus autoRequestStatus) {
		this.autoRequestStatus = autoRequestStatus;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public String getFlowNo() {
		return flowNo;
	}

	public void setFlowNo(String flowNo) {
		this.flowNo = flowNo;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Date getAccountDate() {
		return accountDate;
	}

	public void setAccountDate(Date accountDate) {
		this.accountDate = accountDate;
	}

	public Date getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}

}
