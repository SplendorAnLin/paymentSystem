package com.yl.boss.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;

/**
 * 组织机构Bean
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月11日
 * @version V1.0.0
 */
@Entity
@Table(name = "ORGANIZATION")
public class Organization implements java.io.Serializable{
	
	private static final long serialVersionUID = 1589754587167484295L;
	/**
	 * 机构代码
	 */
	private String code;
	/**
	 * 版本标识
	 */
	private Integer optimistic;
	
	/**
	 * 机构名称
	 */
	private String name;
	/**
	 * 上级机构代码
	 */
	private String parentCode;
	
	/**
	 * 是否目录节点
	 */
	private boolean isParent;
	
	
	@Id
	@Column(length = 20)
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	@Version
	public Integer getOptimistic() {
		return optimistic;
	}

	public void setOptimistic(Integer optimistic) {
		this.optimistic = optimistic;
	}
	
	@Column(name="NAME",length = 50)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name="PARENT_CODE",length = 20)
	public String getParentCode() {
		return parentCode;
	}
	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}
	@Transient
	public boolean getIsParent() {
		return isParent;
	}

	public void setIsParent(boolean isParent) {
		this.isParent = isParent;
	}

}

