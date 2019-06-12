package com.yl.pay.pos.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Title: 银行
 * Copyright: Copyright (c)2011
 * Company: lepay
 * @author haitao.liu
 */

@Entity
@Table(name = "BANK")
public class Bank implements Serializable{

	private Integer optimistic;		//版本标识
	private String code;			//编号
	private String shortName;		//简称
	private String fullName;		//全称	
	
	@Id
	@Column(name = "CODE", length = 30)
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}

	@Version
	@Column(columnDefinition = "int", length = 11)
	public Integer getOptimistic() {
		return optimistic;
	}
	public void setOptimistic(Integer optimistic) {
		this.optimistic = optimistic;
	}
	
	@Column(name = "SHORT_NAME", length = 50)
	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	
	@Column(name = "FULL_NAME", length = 100)
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}	
}
