package com.yl.receive.core.entity;

import java.io.Serializable;
import java.util.Date;

import com.yl.receive.core.enums.Status;

/**
 * 代付路由配置历史纪录
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月25日
 * @version V1.0.0
 */
public class RouteConfigHistory extends BaseEntity implements Serializable{

	private static final long serialVersionUID = 6024720889286175100L;
	
	/**
	 * 路由编号
	 */
	private String code;
	/**
	 * 路由名称
	 */
	private String name;
	/**
	 * 路由配置
	 */
	private String routeInfo;
	/**
	 * 状态
	 */
	private Status status;
	/**
	 * 操作人
	 */
	private String oper;
	
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
	public String getRouteInfo() {
		return routeInfo;
	}
	public void setRouteInfo(String routeInfo) {
		this.routeInfo = routeInfo;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public String getOper() {
		return oper;
	}
	public void setOper(String oper) {
		this.oper = oper;
	}
	
	public RouteConfigHistory() {
		super();
	}
	
	public RouteConfigHistory(RouteConfig routeConfig,String oper) {
		super();
		this.code = routeConfig.getCode();
		this.name = routeConfig.getName();
		this.routeInfo = routeConfig.getRouteInfo();
		this.status = routeConfig.getStatus();
		this.oper = oper;
		super.setCreateDate(new Date());
	}
	
}
