package com.yl.agent.api.interfaces;

import java.util.Map;

import com.lefu.commons.utils.Page;
import com.yl.agent.api.bean.Function;

/**
 * 服务商功能远程操作接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年7月22日
 * @version V1.0.0
 */
public interface AgentFunctionInterface {
	
	/**
	 * 分页查询
	 * @param param
	 * @return
	 */
	public Page findAll(Map<String, Object> param);
	
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
}