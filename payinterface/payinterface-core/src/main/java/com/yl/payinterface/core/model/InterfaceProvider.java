package com.yl.payinterface.core.model;

import java.util.Date;

/**
 * 接口提供方
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月27日
 * @version V1.0.0
 */
public class InterfaceProvider extends AutoStringIDModel {

	private static final long serialVersionUID = -1646748116499825649L;
	/** 全称 */
	private String fullName;
	/** 简称 */
	private String shortName;
	/** 最后修改时间 */
	private Date lastModifyTime;

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

	public Date getLastModifyTime() {
		return lastModifyTime;
	}

	public void setLastModifyTime(Date lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}

}
