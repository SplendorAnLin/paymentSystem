package com.yl.boss.entity;

import java.io.Serializable;

import javax.persistence.Version;

/**
 * 字典类型Bean
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月11日
 * @version V1.0.0
 */
public class DictionaryType implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6622633801086749663L;
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

	public String getDictTypeName() {
		return dictTypeName;
	}

	public void setDictTypeName(String dictTypeName) {
		this.dictTypeName = dictTypeName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
