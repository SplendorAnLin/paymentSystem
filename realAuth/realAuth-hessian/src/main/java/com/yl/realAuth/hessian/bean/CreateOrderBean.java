package com.yl.realAuth.hessian.bean;

import java.io.Serializable;

/**
 * 创建交易订单Bean
 * @author Shark
 * @since 2015年6月3日
 */
public class CreateOrderBean implements Serializable {

	private static final long serialVersionUID = -8599656053076214258L;
	/** 业务类型 */
	private String busiType;
	/** 业务标志1 */
	private String businessFlag1;
	/** 合作方唯一订单号 */
	private String requestCode;
	/** 合作方编号 */
	private String customerNo;
	/** 异步通知地址 */
	private String notifyURL;
	/** 是否实时 */
	private String isActual;
	/** 姓名 */
	private String payerName;
	/** 手机号 */
	private String payerMobNo;
	/** 银行卡号 */
	private String bankCardNo;
	/** 银行卡号,中间对应的密文 */
	private String bankCardNoEncrypt;
	/** 身份证号 */
	private String certNo;
	/** 身份证密文 */
	private String certNoEncrypt;

	public String getBusiType() {
		return busiType;
	}

	public void setBusiType(String busiType) {
		this.busiType = busiType;
	}

	public String getBusinessFlag1() {
		return businessFlag1;
	}

	public void setBusinessFlag1(String businessFlag1) {
		this.businessFlag1 = businessFlag1;
	}

	public String getRequestCode() {
		return requestCode;
	}

	public void setRequestCode(String requestCode) {
		this.requestCode = requestCode;
	}

	public String getCustomerNo() {
		return customerNo;
	}

	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}

	public String getNotifyURL() {
		return notifyURL;
	}

	public void setNotifyURL(String notifyURL) {
		this.notifyURL = notifyURL;
	}

	public String getIsActual() {
		return isActual;
	}

	public void setIsActual(String isActual) {
		this.isActual = isActual;
	}

	public String getPayerName() {
		return payerName;
	}

	public void setPayerName(String payerName) {
		this.payerName = payerName;
	}

	public String getCertNo() {
		return certNo;
	}

	public void setCertNo(String certNo) {
		this.certNo = certNo;
	}

	public String getPayerMobNo() {
		return payerMobNo;
	}

	public void setPayerMobNo(String payerMobNo) {
		this.payerMobNo = payerMobNo;
	}

	public String getBankCardNo() {
		return bankCardNo;
	}

	public void setBankCardNo(String bankCardNo) {
		this.bankCardNo = bankCardNo;
	}

	public String getBankCardNoEncrypt() {
		return bankCardNoEncrypt;
	}

	public void setBankCardNoEncrypt(String bankCardNoEncrypt) {
		this.bankCardNoEncrypt = bankCardNoEncrypt;
	}

	public String getCertNoEncrypt() {
		return certNoEncrypt;
	}

	public void setCertNoEncrypt(String certNoEncrypt) {
		this.certNoEncrypt = certNoEncrypt;
	}

	@Override
	public String toString() {
		return "CreateOrderBean [busiType=" + busiType + ", businessFlag1=" + businessFlag1 + ", requestCode=" + requestCode + ", customerNo=" + customerNo
				+ ", notifyURL=" + notifyURL + ", isActual=" + isActual + ", payerName=" + payerName + ", payerMobNo=" + payerMobNo + ", bankCardNo=" + bankCardNo
				+ ", bankCardNoEncrypt=" + bankCardNoEncrypt + ", certNo=" + certNo + ", certNoEncrypt=" + certNoEncrypt + "]";
	}

}
