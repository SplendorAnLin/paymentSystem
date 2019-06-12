package com.yl.customer.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

/**
 * 字典类型Bean
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月15日
 * @version V1.0.0
 */
@Entity
@Table(name = "DICTIONARY_TYPE")
public class DictionaryType implements Serializable{
	/**
	 * 字典类型编码
	 */
	private String dictTypeId;
	/**
	 * 版本标识
	 */
	private Integer optimistic;
	/**
	 * 字典类型名称
	 */
	private String dictTypeName;
	/**
	 * 备注信息
	 */
	private String remark;
	
	@Id
	@Column(name="DICT_TYPE_ID",length = 50)
	public String getDictTypeId() {
		return dictTypeId;
	}

	public void setDictTypeId(String dictTypeId) {
		this.dictTypeId = dictTypeId;
	}

	@Version
	public Integer getOptimistic() {
		return optimistic;
	}

	public void setOptimistic(Integer optimistic) {
		this.optimistic = optimistic;
	}

	@Column(length = 50, name = "DICT_TYPE_NAME")
	public String getDictTypeName() {
		return dictTypeName;
	}

	public void setDictTypeName(String dictTypeName) {
		this.dictTypeName = dictTypeName;
	}

	@Column(length = 100, name = "REMARK")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
