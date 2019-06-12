package com.yl.realAuth.model;

import java.util.Date;

/**
 * 身份认证信息存储
 * @author Shark
 * @since 2015年6月2日
 */
public class IdentityInfo extends AutoStringIDModel {

	private static final long serialVersionUID = 1654797577491135901L;
	/** 姓名 */
	private String payerName;
	/** 身份证号 */
	private String certNo;
	/** 身份证密文 */
	private String certNoEncrypt;
	/** 更新时间 */
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


	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public void setCertNo(String certNo) {
		this.certNo = certNo;
	}

	public String getCertNoEncrypt() {
		return certNoEncrypt;
	}

	public void setCertNoEncrypt(String certNoEncrypt) {
		this.certNoEncrypt = certNoEncrypt;
	}

	@Override
	public String toString() {
		return "IdentityInfo [payerName=" + payerName + ", certNo=" + certNo + ", certNoEncrypt=" + certNoEncrypt + ", lastUpdateTime=" + lastUpdateTime + "]";
	}

}
