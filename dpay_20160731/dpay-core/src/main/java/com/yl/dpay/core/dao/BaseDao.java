package com.yl.dpay.core.dao;

import org.apache.ibatis.annotations.Param;

import com.yl.dpay.core.entity.BaseEntity;

/**
 * BaseDao
 * 
 * @author 聚合支付有限公司
 * @since 2016年5月13日
 * @version V1.0.0
 */
public interface BaseDao<T extends BaseEntity> {
	
	/**
	 * @param t
	 */
	public void insert(T t);
	/**
	 * @param id
	 * @return
	 */
	public T findById(@Param("id")Long id);
	/**
	 * @param t
	 */
	public void update(T t);

}
