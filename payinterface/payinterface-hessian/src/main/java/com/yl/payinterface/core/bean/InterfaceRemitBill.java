package com.yl.payinterface.core.bean;

import java.io.Serializable;

import javax.validation.constraints.Digits;

import org.codehaus.jackson.annotate.JsonTypeInfo;
import org.codehaus.jackson.annotate.JsonTypeInfo.Id;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 付款单交易bean
 * 
 * @author 聚合支付有限公司
 * @since 2016年4月24日
 * @version V1.0.0
 */
@JsonTypeInfo(use = Id.MINIMAL_CLASS, property = "class")
public class InterfaceRemitBill implements Serializable {

	private static final long serialVersionUID = -1780512614745352917L;

	/** 付款单编码 */
	@NotBlank
	private String billCode;
	/** 付款请求序列号 */
	@NotBlank
	private String requestSeriaNum;
	/** 拥有者角色 */
	@NotBlank
	private String ownerRole;
	/** 拥有者ID */
	@NotBlank
	private String ownerID;
	/** 产品 */
	@NotBlank
	private String product;
	/** 收款银行开户名称,商户公司名称或法人姓名 */
	@NotBlank
	private String accountName;
	/** 收款银行帐号 */
	@NotBlank
	private String accountNo;
	/** 收款银行编号 */
	@NotBlank
	private String bankCode;
	/** 收款开户行名称 */
	@NotBlank
	private String bankName;
	/** 打款金额 */
	@Digits(integer = 10, fraction = 2)
	private double amount;
	/** 手续费 */
	@Digits(integer = 10, fraction = 2)
	private double fee;
	/** 打款用途/摘要 */
	@NotBlank
	private String use;
	/** 打款备注 */
	private String memo;
	/** 省份 */
	private String province;
	/** 地市 */
	private String city;
	/** 联行号 */
	private String cnapsCode;
	/** 清分行号 */
	private String sabkCode;
	/** 分行号 */
	private String ownBankCode;

	public String getBillCode() {
		return billCode;
	}

	public void setBillCode(String billCode) {
		this.billCode = billCode;
	}

	public String getRequestSeriaNum() {
		return requestSeriaNum;
	}

	public void setRequestSeriaNum(String requestSeriaNum) {
		this.requestSeriaNum = requestSeriaNum;
	}

	public String getOwnerRole() {
		return ownerRole;
	}

	public void setOwnerRole(String ownerRole) {
		this.ownerRole = ownerRole;
	}

	public String getOwnerID() {
		return ownerID;
	}

	public void setOwnerID(String ownerID) {
		this.ownerID = ownerID;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
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

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public double getFee() {
		return fee;
	}

	public void setFee(double fee) {
		this.fee = fee;
	}

	public String getUse() {
		return use;
	}

	public void setUse(String use) {
		this.use = use;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCnapsCode() {
		return cnapsCode;
	}

	public void setCnapsCode(String cnapsCode) {
		this.cnapsCode = cnapsCode;
	}

	public String getSabkCode() {
		return sabkCode;
	}

	public void setSabkCode(String sabkCode) {
		this.sabkCode = sabkCode;
	}

	public String getOwnBankCode() {
		return ownBankCode;
	}

	public void setOwnBankCode(String ownBankCode) {
		this.ownBankCode = ownBankCode;
	}

}
