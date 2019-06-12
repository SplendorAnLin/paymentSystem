package com.yl.pay.pos.entity;

import com.yl.pay.pos.enums.Status;

import javax.persistence.*;
import java.util.Date;

/**
 * Title: 路由模版表
 * Description: 
 * Copyright: Copyright (c)2012
 * Company: com.yl.pay
 * @author jun
 */

@Entity
@Table(name = "TRANS_ROUTE")
public class TransRoute extends BaseEntity{
	
	private String code;				//编号
	private String name;				//名称
	private Date createTime;			//创建时间
	private Status status;				//状态
	
	
	public TransRoute() {
		super();
	}
	
	@Column(name = "CODE", length = 20,unique = true)
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
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
	
	@Column(name = "NAME", length = 128)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
