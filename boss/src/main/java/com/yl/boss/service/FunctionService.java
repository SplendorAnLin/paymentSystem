package com.yl.boss.service;

import java.util.List;

import com.yl.boss.entity.Function;

/**
 * 功能业务接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月13日
 * @version V1.0.0
 */
public interface FunctionService {
	
	public List<Function> findAll() ;
	public List<Function> findShowAll();
	public Function findById(Long id);
	
	public Function create(Function function);
	
	public Function update(Function function);
	
	public void delete(Long id);
	
}
