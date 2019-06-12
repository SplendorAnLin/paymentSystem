package com.yl.boss.api.bean;

import java.io.Serializable;

public class OrganizationBean implements Serializable{
	/**
	 * 机构代码
	 */
	private String code;
	
	/**
	 * 机构名称
	 */
	private String name;
	/**
	 * 上级机构代码
	 */
	private String parentCode;
	
	
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
	public String getParentCode() {
		return parentCode;
	}
	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}
	
	
	
}
