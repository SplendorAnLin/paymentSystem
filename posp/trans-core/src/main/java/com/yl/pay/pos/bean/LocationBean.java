package com.yl.pay.pos.bean;

import java.math.BigDecimal;

/**
 * Title: 地址查询参数 
 * Description: 
 * Copyright: Copyright (c)2014
 * Company: com.yl.pay
 * @author geling.sun
 */
public class LocationBean {
	private String country;   	//国家
	private String conntryCode;	//国家编号
	private String province;	//省份
	private String provinceCode;//省份编号
	private String city;		//城市
	private String cityCode;	//城市编号
	private String des;			//地址
	private BigDecimal lng;     //经度
	private BigDecimal lat;     //维度 
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getConntryCode() {
		return conntryCode;
	}
	public void setConntryCode(String conntryCode) {
		this.conntryCode = conntryCode;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getProvinceCode() {
		return provinceCode;
	}
	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCityCode() {
		return cityCode;
	}
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	public String getDes() {
		return des;
	}
	public void setDes(String des) {
		this.des = des;
	}
	/**
	 * 经度
	 */
	public BigDecimal getLng() {
		return lng;
	}
	public void setLng(BigDecimal lng) {
		this.lng = lng;
	}
	/**
	 * 维度
	 */
	public BigDecimal getLat() {
		return lat;
	}
	public void setLat(BigDecimal lat) {
		this.lat = lat;
	}

}
