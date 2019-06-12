package com.yl.boss.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 字典Bean
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月11日
 * @version V1.0.0
 */
public class Dictionary implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -834913083441913095L;
	/**
	 * 字典key
	 */
	private String dictId;	
	/**
	 * 字典value
	 */
	private String dictName;
	/**
	 * 字典类型
	 */	
	private DictionaryType dictType;	
	/**
	 * 特权值
	 */	
	private String privilege;
	/**
	 * 状态
	 */	
	private String status;
	
	/**
	 * 备注信息
	 */
	private String remark;
	
	private Long displayOrder;
	
	public String getDictId() {
		return dictId;
	}
	public void setDictId(String dictId) {
		this.dictId = dictId;
	}
	
	public String getDictName() {
		return dictName;
	}
	public void setDictName(String dictName) {
		this.dictName = dictName;
	}
	
	public DictionaryType getDictType() {
		return dictType;
	}
	public void setDictType(DictionaryType dictType) {
		this.dictType = dictType;
	}
	
	public String getPrivilege() {
		return privilege;
	}
	public void setPrivilege(String privilege) {
		this.privilege = privilege;
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	} 
	
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Long getDisplayOrder() {
		return displayOrder;
	}
	public void setDisplayOrder(Long displayOrder) {
		this.displayOrder = displayOrder;
	}
	
}
