package com.yl.customer.api.bean;

import java.io.Serializable;

/**
 * 基础Bean
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月24日
 * @version V1.0.0
 */
public class BeseBean implements Serializable {

	private static final long serialVersionUID = -1656274115865237922L;
	private Long id;
	private Integer optimistic;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getOptimistic() {
		return optimistic;
	}

	public void setOptimistic(Integer optimistic) {
		this.optimistic = optimistic;
	}

}
