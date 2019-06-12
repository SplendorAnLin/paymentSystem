package com.yl.payinterface.core.bean;

import java.io.Serializable;

/**
 * 认证移动支付信息Bean
 * 
 * @author 聚合支付有限公司
 * @since 2016年4月24日
 * @version V1.0.0
 */
public class MobileInfoBean implements Serializable {
	
	private static final long serialVersionUID = -6445320505633373629L;
	/** 银行卡号 */
	private String bankCardNo;
	/** 有效期 */
	private String validity;
	/** 安全码 */
	private String cvv;
	/** 姓名 */
	private String payerName;
	/** 身份证号 */
	private String certNo;
	/** 绑定手机号 */
	private String payerMobNo;
	/** 短信验证码 */
	private String verifycode;
	/** 支付订单号 */
	private String merOrderId;
	/** 支付金额 */
	private double amount;

	public String getBankCardNo() {
		return bankCardNo;
	}

	public void setBankCardNo(String bankCardNo) {
		this.bankCardNo = bankCardNo;
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

	public String getVerifycode() {
		return verifycode;
	}

	public void setVerifycode(String verifycode) {
		this.verifycode = verifycode;
	}

	public String getMerOrderId() {
		return merOrderId;
	}

	public void setMerOrderId(String merOrderId) {
		this.merOrderId = merOrderId;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getValidity() {
		return validity;
	}

	public void setValidity(String validity) {
		this.validity = validity;
	}

	public String getCvv() {
		return cvv;
	}

	public void setCvv(String cvv) {
		this.cvv = cvv;
	}

	@Override
	public String toString() {
		return "MobileInfoBean [bankCardNo=" + bankCardNo + ", validity="
				+ validity + ", cvv=" + cvv + ", payerName=" + payerName
				+ ", certNo=" + certNo + ", payerMobNo=" + payerMobNo
				+ ", verifycode=" + verifycode + ", merOrderId=" + merOrderId
				+ ", amount=" + amount + "]";
	}

}
