/**   
 * @Package com.yl.payinterface.core.bean 
 * @Description TODO(用一句话描述该文件做什么) 
 * @author Administrator   
 * @date 2017年11月19日 下午6:34:51 
 */
package com.yl.payinterface.core.bean;

import java.io.Serializable;

/**
 * @ClassName QuickPayOpenCardRequestBean
 * @Description TODO(这里用一句话描述这个类的作用)
 * @author Administrator
 * @date 2017年11月19日 下午6:34:51
 */
public class QuickPayOpenCardRequestBean implements Serializable {

	private static final long serialVersionUID = 2980509426866734242L;

	private String interfaceInfoCode;

	private String name;

	private String cardNo;

	private String phone;

	private String expireDate;

	private String cvn;

	private String smsCode;
	
	private String customerNo;

	public String getInterfaceInfoCode() {
		return interfaceInfoCode;
	}

	public void setInterfaceInfoCode(String interfaceInfoCode) {
		this.interfaceInfoCode = interfaceInfoCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(String expireDate) {
		this.expireDate = expireDate;
	}

	public String getCvn() {
		return cvn;
	}

	public void setCvn(String cvn) {
		this.cvn = cvn;
	}

	public String getSmsCode() {
		return smsCode;
	}

	public void setSmsCode(String smsCode) {
		this.smsCode = smsCode;
	}
	

	public String getCustomerNo() {
		return customerNo;
	}

	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}

	@Override
	public String toString() {
		return "QuickPayOpenCardRequestBean [interfaceInfoCode=" + interfaceInfoCode + ", name=" + name + ", cardNo=" + cardNo + ", phone=" + phone + ", expireDate=" + expireDate + ", cvn=" + cvn + ", smsCode=" + smsCode + ", customerNo=" + customerNo + "]";
	}

}
