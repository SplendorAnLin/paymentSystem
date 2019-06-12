package com.yl.cachecenter.model;

import java.io.Serializable;

/**
 * 行政区划
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月14日
 * @version V1.0.0
 */
public class AdministrativeDivision implements Serializable{

	private static final long serialVersionUID = 8092454459336004785L;
	
	/** 行政区划码 */
	private String adCode;
	/** 行政区划名称 */
	private String adName;
	
	public String getAdCode() {
		return adCode;
	}
	public void setAdCode(String adCode) {
		this.adCode = adCode;
	}
	public String getAdName() {
		return adName;
	}
	public void setAdName(String adName) {
		this.adName = adName;
	}
	
	public AdministrativeDivision(String adCode, String adName) {
		super();
		this.adCode = adCode;
		this.adName = adName;
	}

	public AdministrativeDivision() {}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AdministrativeDivision [adCode=");
		builder.append(adCode);
		builder.append(", adName=");
		builder.append(adName);
		builder.append("]");
		return builder.toString();
	}
	
}
