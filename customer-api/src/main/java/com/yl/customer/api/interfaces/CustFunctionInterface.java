package com.yl.customer.api.interfaces;

import java.util.Map;

import com.lefu.commons.utils.Page;
import com.yl.customer.api.bean.Function;

/**
 * 商户功能远程操作
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月24日
 * @version V1.0.0
 */
public interface CustFunctionInterface {
	/**
	 * 新增
	 * @param function
	 * @return
	 */
	public Function addFunction(Function function);
	
	/**
	 * 编辑
	 * @param function
	 * @return
	 */
	public Function modify(Function function);
	
	/**
	 * 删除
	 * @param id
	 */
	public void delete(Long id);
	
	/**
	 * 根据ID查询
	 * @param id
	 * @return
	 */
	public Function findById(Long id);
	
	/**
	 * 分页查询
	 * @return
	 */
	public Page findAll(Map<String, Object> param);
}