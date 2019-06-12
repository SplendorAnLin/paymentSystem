package com.yl.receive.core.entity;

import java.io.Serializable;

/**
 * 账户信息
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月25日
 * @version V1.0.0
 */
public class Account extends BaseEntity implements Serializable {

	private static final long serialVersionUID = -2479295952372288646L;
	// 商户编号
	private String customerNo;
	// 银行卡姓名
	private String accountName;
	// 开户行名称
	private String openBankName;
	// 身份证号掩码
	private String idCard;
	// 身份证号密文
	private String idCardEncrpty;
	// 银行卡号
	private String bankCard;
	// 银行卡密文
	private String bankCardEncrpty;
	// 是否有效
	private String valid;

	public String getCustomerNo() {
		return customerNo;
	}

	public String getAccountName() {
		return accountName;
	}

	public String getIdCard() {
		return idCard;
	}

	public String getIdCardEncrpty() {
		return idCardEncrpty;
	}

	public String getBankCard() {
		return bankCard;
	}

	public String getBankCardEncrpty() {
		return bankCardEncrpty;
	}

	public String getValid() {
		return valid;
	}

	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public void setIdCardEncrpty(String idCardEncrpty) {
		this.idCardEncrpty = idCardEncrpty;
	}

	public void setBankCard(String bankCard) {
		this.bankCard = bankCard;
	}

	public void setBankCardEncrpty(String bankCardEncrpty) {
		this.bankCardEncrpty = bankCardEncrpty;
	}

	public void setValid(String valid) {
		this.valid = valid;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getOpenBankName() {
		return openBankName;
	}

	public void setOpenBankName(String openBankName) {
		this.openBankName = openBankName;
	}

}
