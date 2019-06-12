package com.yl.client.front.model;

import java.io.Serializable;

/**
 * 客户端信息
 * 
 * @author 聚合支付有限公司
 * @since 2017年4月28日
 * @version V1.0.0
 */
public class ClientInfo implements Serializable{
	private static final long serialVersionUID = -22752562072372153L;
	
	private String address;//地址
	private String lat;//纬度
	private String lng;//经度
	private String clientType;//设备类型
	private String clientVersion;//客户端版本号
	private String oem;//运营厂家
	private String sysVersion;//系统版本
	private String requestTime;//请求时间
	private String phone;//手机号
	private String netType;//请求网络类型
	private String msg;//验证信息
	
	public String getNetType() {
		return netType;
	}
	public void setNetType(String netType) {
		this.netType = netType;
	}
	public String getOem() {
		return oem;
	}
	public void setOem(String oem) {
		this.oem = oem;
	}
	public String getClientVersion() {
		return clientVersion;
	}
	public void setClientVersion(String clientVersion) {
		this.clientVersion = clientVersion;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAddress() {
		return address;
	}
	public String getLat() {
		return lat;
	}
	public String getLng() {
		return lng;
	}
	public String getSysVersion() {
		return sysVersion;
	}
	public String getMsg() {
		return msg;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public void setLat(String lat) {
		this.lat = lat;
	}
	public void setLng(String lng) {
		this.lng = lng;
	}
	public void setSysVersion(String sysVersion) {
		this.sysVersion = sysVersion;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getClientType() {
		return clientType;
	}
	public void setClientType(String clientType) {
		this.clientType = clientType;
	}
	public String getRequestTime() {
		return requestTime;
	}
	public void setRequestTime(String requestTime) {
		this.requestTime = requestTime;
	}
	
}
