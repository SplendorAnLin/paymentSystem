package com.yl.boss.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.yl.boss.enums.Status;

/**
 * 行业类别
 *
 * @author 聚合支付有限公司
 * @since 2016年8月11日
 * @version V1.0.0
 */

@Entity
@Table(name = "MCC_CATEGORY")
public class MccCategory extends AutoIDEntity{

	private String category;		//类别
	private String name;			//简称
	
	@Column(name = "CATEGORY", length = 30)
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	
	@Column(name = "NAME", length = 100)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
}
