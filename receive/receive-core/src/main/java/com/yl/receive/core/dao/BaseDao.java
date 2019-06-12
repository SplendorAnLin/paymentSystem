package com.yl.receive.core.dao;

import org.apache.ibatis.annotations.Param;

/**
 * BaseDao
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月25日
 * @version V1.0.0
 */
public interface BaseDao<T> {
	
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
