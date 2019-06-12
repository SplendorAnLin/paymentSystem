package com.yl.dpay.core.entity;

import java.util.Date;

/**
 * BaseEntity
 * 
 * @author 聚合支付有限公司
 * @since 2016年5月12日
 * @version V1.0.0
 */
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
