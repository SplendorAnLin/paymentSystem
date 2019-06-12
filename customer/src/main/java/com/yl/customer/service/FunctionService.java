package com.yl.customer.service;

import java.util.List;

import com.yl.customer.entity.Function;

/**
 * 功能 Service
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月20日
 * @version V1.0.0
 */
public interface FunctionService {
	
	public List<Function> findAll() ;
	
	public Function findById(Long id);
	
	public Function create(Function function);
	
	public Function update(Function function);
	
	public void delete(Long id);
	public List<Function> findShowAll();
}
