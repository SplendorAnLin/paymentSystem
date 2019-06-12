package com.yl.dpay.hessian.beans;

import java.io.Serializable;
import java.util.Date;

/**
 * 基础bean
 * 
 * @author 聚合支付有限公司
 * @since 2016年5月15日
 * @version V1.0.0
 */
public class BaseBean implements Serializable{
	
	private static final long serialVersionUID = -3615414233066618304L;
	private Long id;
    private Integer optimistic;
    private Date createDate;		//创建时间
    
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
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
