package com.yl.client.front.model;

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
	
}
