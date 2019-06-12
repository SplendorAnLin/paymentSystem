package com.yl.cachecenter.model;

import java.io.Serializable;

/**
 * 接口提供方
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月14日
 * @version V1.0.0
 */
public class InterfaceProvider implements Serializable {
	private static final long serialVersionUID = -1646748116499825649L;
	/** 编号 */
	private String code;
	/** 全称 */
	private String name;
	/** 简称 */
	private String nickname;
	/** 是否为银行 */
	private boolean isBank;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public boolean isBank() {
		return isBank;
	}

	public void setBank(boolean isBank) {
		this.isBank = isBank;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("InterfaceProvider [code=");
		builder.append(code);
		builder.append(", name=");
		builder.append(name);
		builder.append(", nickname=");
		builder.append(nickname);
		builder.append(", isBank=");
		builder.append(isBank);
		builder.append("]");
		return builder.toString();
	}

}
