package com.yl.payinterface.core.model;

/**
 * 移动支付卡信息
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月27日
 * @version V1.0.0
 */
public class MobileCardInfo extends AutoStringIDModel {

	private static final long serialVersionUID = -8439307500732793390L;
	/** 绑定手机号 */
	private String phoneNo;
	/** 卡号 */
	private String cardNo;
	/** CVN2码 */
	private String cvn2;
	/** 身份证号 */
	private String certNo;
	/** 有效期 */
	private String valiDate;
	/** 用户名 */
	private String username;
	/** 接口订单号 */
	private String interfaceRequestId;

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getCvn2() {
		return cvn2;
	}

	public void setCvn2(String cvn2) {
		this.cvn2 = cvn2;
	}

	public String getCertNo() {
		return certNo;
	}

	public void setCertNo(String certNo) {
		this.certNo = certNo;
	}

	public String getValiDate() {
		return valiDate;
	}

	public void setValiDate(String valiDate) {
		this.valiDate = valiDate;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getInterfaceRequestId() {
		return interfaceRequestId;
	}

	public void setInterfaceRequestId(String interfaceRequestId) {
		this.interfaceRequestId = interfaceRequestId;
	}

	@Override
	public String toString() {
		return "MobileCardInfo [phoneNo=" + phoneNo + ", cardNo=" + cardNo + ", cvn2=" + cvn2 + ", certNo=" + certNo + ", valiDate=" + valiDate + ", username="
				+ username + ", interfaceRequestId=" + interfaceRequestId + "]";
	}
}
