package com.yl.pay.pos.bean;

/**
 * Title: 请求IP地址信息Bean
 * Description: 
 * Copyright: Copyright (c)2011
 * Company: com.yl.pay
 * @author zhongxiang.ren
 */
public class IPAddressInfoBean { 
	private long id;			//id编号
	private long startIPNum;
	private long endIPNum;
	private long interval;
	private String startIP;		//起始IP
	private String endIP;		//终止IP
	private int IPType;
	private String country;		//国家
	private String province;	//省份
	private String provinceCode;//省份行政编号
	private String city;		//城市
	private String cityCode;	//城市行政编号
	private String district;
	private String isp;
	private String description;	//描述
	private Long createTime;//创建时间
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getStartIPNum() {
		return startIPNum;
	}
	public void setStartIPNum(long startIPNum) {
		this.startIPNum = startIPNum;
	}
	public long getEndIPNum() {
		return endIPNum;
	}
	public void setEndIPNum(long endIPNum) {
		this.endIPNum = endIPNum;
	}
	public long getInterval() {
		return interval;
	}
	public void setInterval(long interval) {
		this.interval = interval;
	}
	public String getStartIP() {
		return startIP;
	}
	public void setStartIP(String startIP) {
		this.startIP = startIP;
	}
	public String getEndIP() {
		return endIP;
	}
	public void setEndIP(String endIP) {
		this.endIP = endIP;
	}
	public int getIPType() {
		return IPType;
	}
	public void setIPType(int iPType) {
		IPType = iPType;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
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
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public String getIsp() {
		return isp;
	}
	public void setIsp(String isp) {
		this.isp = isp;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
}
