package com.yl.agent.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 字典
 * 
 * @author 聚合支付有限公司
 * @since 2016年7月8日
 * @version V1.0.0
 */
@Entity
@Table(name = "DICTIONARY")
public class Dictionary extends AutoIDEntity {
	
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
	
	@Column(length = 50, name = "DICT_ID")	
	public String getDictId() {
		return dictId;
	}
	public void setDictId(String dictId) {
		this.dictId = dictId;
	}
	
	@Column(length = 50, name = "DICT_NAME")	
	public String getDictName() {
		return dictName;
	}
	public void setDictName(String dictName) {
		this.dictName = dictName;
	}
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = { 
			CascadeType.MERGE, CascadeType.REFRESH })
	@JoinColumn(name = "DICT_TYPE_ID")
	public DictionaryType getDictType() {
		return dictType;
	}
	public void setDictType(DictionaryType dictType) {
		this.dictType = dictType;
	}
	
	@Column(length = 50, name = "PRIVILEGE")	
	public String getPrivilege() {
		return privilege;
	}
	public void setPrivilege(String privilege) {
		this.privilege = privilege;
	}
	
	@Column(length = 30, name = "STATUS")	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	} 
	
	@Column(length = 100, name = "REMARK")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Column(length = 100, name = "DISPLAY_ORDER",columnDefinition = "NUMBER")
	public Long getDisplayOrder() {
		return displayOrder;
	}
	public void setDisplayOrder(Long displayOrder) {
		this.displayOrder = displayOrder;
	}
	
}
