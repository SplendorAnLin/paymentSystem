package com.yl.boss.api.bean;

import java.io.Serializable;

/**
 * 基础bean
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月16日
 * @version V1.0.0
 */
public class BaseBean implements Serializable{
	
	private static final long serialVersionUID = -3615414233066618304L;
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
