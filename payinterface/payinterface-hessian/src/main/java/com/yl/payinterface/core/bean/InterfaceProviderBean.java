package com.yl.payinterface.core.bean;

import com.lefu.hessian.bean.JsonBean;

/**
 * 接口提供方Bean
 * 
 * @author 聚合支付有限公司
 * @since 2016年4月24日
 * @version V1.0.0
 */
public class InterfaceProviderBean implements JsonBean{

	private static final long serialVersionUID = -1646748116499825649L;
	/** 编号 */
	private String code;
	/** 全称 */
	private String fullName;
	/** 简称 */
	private String shortName;

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("InterfaceProviderBean [code=");
		builder.append(code);
		builder.append(", fullName=");
		builder.append(fullName);
		builder.append(", shortName=");
		builder.append(shortName);
		builder.append("]");
		return builder.toString();
	}

}
