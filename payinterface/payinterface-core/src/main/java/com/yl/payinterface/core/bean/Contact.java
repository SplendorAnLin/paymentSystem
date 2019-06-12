package com.yl.payinterface.core.bean;

import java.io.Serializable;

/**
 * 联系人信息
 * 
 * @author 聚合支付有限公司
 * @since 2016年11月11日
 * @version V1.0.0
 */
public class Contact implements Serializable {
	private static final long serialVersionUID = -658393221975783941L;
	/** 姓名 */
	private String name;
	/** 电话 */
	private String telephone;
	/** 手机 */
	private String mobilephone;
	/** 邮箱 */
	private String email;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getMobilephone() {
		return mobilephone;
	}

	public void setMobilephone(String mobilephone) {
		this.mobilephone = mobilephone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Contact [name=");
		builder.append(name);
		builder.append(", telephone=");
		builder.append(telephone);
		builder.append(", mobilephone=");
		builder.append(mobilephone);
		builder.append(", email=");
		builder.append(email);
		builder.append("]");
		return builder.toString();
	}

}
