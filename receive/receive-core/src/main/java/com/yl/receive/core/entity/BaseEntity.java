package com.yl.receive.core.entity;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * BaseEntity
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月25日
 * @version V1.0.0
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class BaseEntity {
	/**
	 * id
	 */
	private Long id;
	/**
	 * 版本号
	 */
	private int optimistic;
	/**
	 * 创建时间
	 */
	private Date createDate;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public int getOptimistic() {
		return optimistic;
	}

	public void setOptimistic(int optimistic) {
		this.optimistic = optimistic;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
}
