package com.yl.dpay.hessian.beans;

import java.io.Serializable;
import java.util.Date;

import com.yl.dpay.hessian.enums.AccountType;

/**
 * 收款人Bean
 * 
 * @author 聚合支付有限公司
 * @since 2016年5月15日
 * @version V1.0.0
 */
public class PayeeBean implements Serializable{
	
	private static final long serialVersionUID = -3267545492863288333L;
	/**
	 * 编号
	 */
	private String ownerId;
	/**
	 * 账户编号
	 */
	private String accountNo;
	/**
	 * 账户名称
	 */
	private String accountName;
	/**
	 * 账户类型
	 */
	private AccountType accountType;
	/**
	 * 银行代码
	 */
	private String bankCode;
	/**
	 * 清分行号
	 */
	private String sabkCode;
	/**
	 * 清分行名称
	 */
	private String sabkName;
	/**
	 * 联行号
	 */
	private String cnapsCode;
	/**
	 * 联行名称
	 */
	private String cnapsName;
	/**
	 * 开户银行
	 */
	private String openBankName;
	/**
	 * 创建时间
	 */
	private Date createDate;
	/**
	 * id
	 */
	private Long id;
	/**
	 * 版本号
	 */
	private int optimistic;

	/**
	 * 身份证号码
	 */
	private String idCardNo;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public int getOptimistic() {
		return optimistic;
	}
	public void setOptimistic(int optimistic) {
		this.optimistic = optimistic;
	}
	public String getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}
	public String getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public AccountType getAccountType() {
		return accountType;
	}
	public void setAccountType(AccountType accountType) {
		this.accountType = accountType;
	}
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	public String getSabkCode() {
		return sabkCode;
	}
	public void setSabkCode(String sabkCode) {
		this.sabkCode = sabkCode;
	}
	public String getSabkName() {
		return sabkName;
	}
	public void setSabkName(String sabkName) {
		this.sabkName = sabkName;
	}
	public String getCnapsCode() {
		return cnapsCode;
	}
	public void setCnapsCode(String cnapsCode) {
		this.cnapsCode = cnapsCode;
	}
	public String getCnapsName() {
		return cnapsName;
	}
	public void setCnapsName(String cnapsName) {
		this.cnapsName = cnapsName;
	}
	public String getOpenBankName() {
		return openBankName;
	}
	public void setOpenBankName(String openBankName) {
		this.openBankName = openBankName;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getIdCardNo() {
		return idCardNo;
	}

	public void setIdCardNo(String idCardNo) {
		this.idCardNo = idCardNo;
	}
}
