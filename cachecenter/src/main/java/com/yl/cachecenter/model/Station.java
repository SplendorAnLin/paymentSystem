package com.yl.cachecenter.model;

import java.io.Serializable;

/**
 * 公交车站信息
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月14日
 * @version V1.0.0
 */
public class Station implements Serializable{

	private static final long serialVersionUID = 4834842337360272600L;
	
	/** 路线编号 */
	private String routeNo;
	
	/** 上下行标识 */
	private String flag;//N 上行,S 下行
	
	/** 始发站 */
	private String startStation;
	
	/** 终点站 */
	private String endStation;
	
	/** 开始时间 */
	private String startTime;
	
	/** 结束时间 */
	private String endTime;
	
	/** 站点编号 */
	private String stationNo;
	
	/** 车站编号 */
	private String code;
	
	/** 车站名称 */
	private String name;

	public String getRouteNo() {
		return routeNo;
	}

	public void setRouteNo(String routeNo) {
		this.routeNo = routeNo;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getStartStation() {
		return startStation;
	}

	public void setStartStation(String startStation) {
		this.startStation = startStation;
	}

	public String getEndStation() {
		return endStation;
	}

	public void setEndStation(String endStation) {
		this.endStation = endStation;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getStationNo() {
		return stationNo;
	}

	public void setStationNo(String stationNo) {
		this.stationNo = stationNo;
	}

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

}
