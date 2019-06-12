package com.yl.realAuth.model;

import java.util.Date;

/**
 * 绑卡支付存储
 * @author Shark
 * @since 2015年6月2日
 */
public class BindCardInfo extends AutoStringIDModel {

	private static final long serialVersionUID = -384530866481826927L;
	/** 姓名 */
	private String payerName;
	/** 身份证号 */
	private String certNo;
	/** 身份证密文 */
	private String certNoEncrypt;
	/** 手机号 */
	private String payerMobNo;
	/** 银行卡号 */
	private String bankCardNo;
	/** 银行卡号,中间对应的密文 */
	private String bankCardNoEncrypt;
	/** 修改时间 */
	private Date lastUpdateTime;

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

	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public String getCertNoEncrypt() {
		return certNoEncrypt;
	}

	public void setCertNoEncrypt(String certNoEncrypt) {
		this.certNoEncrypt = certNoEncrypt;
	}

	public String getBankCardNoEncrypt() {
		return bankCardNoEncrypt;
	}

	public void setBankCardNoEncrypt(String bankCardNoEncrypt) {
		this.bankCardNoEncrypt = bankCardNoEncrypt;
	}

	@Override
	public String toString() {
		return "BindCardInfo [payerName=" + payerName + ", certNo=" + certNo + ", certNoEncrypt=" + certNoEncrypt + ", payerMobNo=" + payerMobNo + ", bankCardNo="
				+ bankCardNo + ", bankCardNoEncrypt=" + bankCardNoEncrypt + ", lastUpdateTime=" + lastUpdateTime + "]";
	}

}
