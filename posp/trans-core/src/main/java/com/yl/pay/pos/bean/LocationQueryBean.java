package com.yl.pay.pos.bean;

/**
 * Title: 地址查询参数 
 * Description: 
 * Copyright: Copyright (c)2014
 * Company: com.yl.pay
 * @author jun
 */
public class LocationQueryBean {
	/**
	 * 基站信息
	 */
	private String locationInfo;
	/**
	 * IP地址
	 */
	private String ip;
	
	/**
	 * @param locationInfo
	 * @param ip
	 */
	public LocationQueryBean(String locationInfo, String ip) {
		super();
		this.locationInfo = locationInfo;
		this.ip = ip;
	}
	
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getLocationInfo() {
		return locationInfo;
	}
	public void setLocationInfo(String locationInfo) {
		this.locationInfo = locationInfo;
	}
	
}
