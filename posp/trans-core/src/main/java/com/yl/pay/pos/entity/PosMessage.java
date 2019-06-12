package com.yl.pay.pos.entity;

import com.yl.pay.pos.enums.Status;

import javax.persistence.*;
import java.util.Date;

/**
 * Title: pos信息表
 * Description: 
 * Copyright: Copyright (c)2014
 * Company: com.yl.pay
 * @author yan
 */
@Entity
@Table(name = "POS_MESSAGE")
public class PosMessage extends BaseEntity{
	private String posCati;		//pos终端号
	private String message;		//提示信息
	private Status status;		//状态
	private Date createTime;	//创建时间
	private Date effTime;		//生效时间	
	private Date expTime;		//失效时间
	private Integer remindCount;//提醒次数
	@Column(name = "POS_CATI", length = 20)
	public String getPosCati() {
		return posCati;
	}
	public void setPosCati(String posCati) {
		this.posCati = posCati;
	}
	@Column(name = "MESSAGE")
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	@Enumerated(value = EnumType.STRING)
	@Column(name = "STATUS", columnDefinition = "VARCHAR(20)")
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	@Column(name = "CREATE_TIME", columnDefinition = "DATETIME")
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	@Column(name = "EFF_TIME", columnDefinition = "DATETIME")
	public Date getEffTime() {
		return effTime;
	}
	public void setEffTime(Date effTime) {
		this.effTime = effTime;
	}
	@Column(name = "EXP_TIME", columnDefinition = "DATETIME")
	public Date getExpTime() {
		return expTime;
	}
	public void setExpTime(Date expTime) {
		this.expTime = expTime;
	}
	@Column(name = "REMIND_COUNT",columnDefinition = "int", length = 11)
	public Integer getRemindCount() {
		return remindCount;
	}
	public void setRemindCount(Integer remindCount) {
		this.remindCount = remindCount;
	}
	
	
	
}
