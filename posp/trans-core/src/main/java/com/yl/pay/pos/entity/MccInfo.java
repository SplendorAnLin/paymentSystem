package com.yl.pay.pos.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Title: 行业代码 
 * Description:
 * Copyright: Copyright (c)2011
 * Company: com.yl.pay
 * @author haitao.liu
 */

@Entity
@Table(name = "MCC_INFO")
public class MccInfo extends BaseEntity{

	private String mcc;				//MCC
	private String category;		//类别
	private String rate;			//标准费率
	private String name;			//简称
	private String description;		//描述
	
	@Column(name = "MCC", length = 10)
	public String getMcc() {
		return mcc;
	}
	public void setMcc(String mcc) {
		this.mcc = mcc;
	}
	
	@Column(name = "CATEGORY", length = 30)
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	
	@Column(name = "RATE", length = 20)
	public String getRate() {
		return rate;
	}
	public void setRate(String rate) {
		this.rate = rate;
	}
	
	@Column(name = "NAME", length = 100)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name = "DESCRIPTION", length = 1024)
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}			
}
