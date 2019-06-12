package com.yl.pay.pos.entity;

import com.yl.pay.pos.enums.RouteType;
import com.yl.pay.pos.enums.Status;

import javax.persistence.*;
import java.util.Date;


/**
 * Title: 商户交易路由配置
 * Description: 
 * Copyright: Copyright (c)2012
 * Company: com.yl.pay
 * @author jun
 */

@Entity
@Table(name = "TRANS_ROUTE_CONFIG")
public class TransRouteConfig extends BaseEntity{
	
	private static final long serialVersionUID = -5463210604719102010L;
	private String customerNo;			//商户号
	private String transRouteCode;		//路由模版编号
	private Date createTime;			//创建时间
	private Status status;				//状态
	private String code;				//商户路由编号
	private RouteType routeType;			//路径类型
	
	
	@Column(name = "CREATE_TIME",columnDefinition = "DATETIME")
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "STATUS", columnDefinition = "VARCHAR(30)")
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	
	@Column(name = "CUSTOMER_NO", length = 30)
	public String getCustomerNo() {
		return customerNo;
	}
	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}
	
	@Column(name = "TRANS_ROUTE_CODE", length = 30)
	public String getTransRouteCode() {
		return transRouteCode;
	}
	public void setTransRouteCode(String transRouteCode) {
		this.transRouteCode = transRouteCode;
	}

	public TransRouteConfig() {
		super();
	}
	@Column(name = "CODE", length = 255)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "ROUTE_TYPE", columnDefinition = "VARCHAR(20)")
	public RouteType getRouteType() {
		return routeType;
	}
	public void setRouteType(RouteType routeType) {
		this.routeType = routeType;
	}
	
	
	
	
}

