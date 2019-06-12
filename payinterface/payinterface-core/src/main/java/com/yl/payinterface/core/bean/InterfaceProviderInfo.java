package com.yl.payinterface.core.bean;

import java.io.Serializable;

/**
 * 接口提供方信息
 * 
 * @author 聚合支付有限公司
 * @since 2016年11月11日
 * @version V1.0.0
 */
public class InterfaceProviderInfo implements Serializable {
	private static final long serialVersionUID = 2544150608236456273L;
	/** 编号 */
	private String code;
	/** 全称 */
	private String fullName;
	/** 简称 */
	private String shortName;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

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

}
