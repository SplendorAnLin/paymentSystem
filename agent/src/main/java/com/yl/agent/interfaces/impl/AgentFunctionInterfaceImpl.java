package com.yl.agent.interfaces.impl;

import java.util.Map;

import org.codehaus.jackson.type.TypeReference;

import com.lefu.commons.utils.Page;
import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.agent.api.bean.Function;
import com.yl.agent.api.interfaces.AgentFunctionInterface;
import com.yl.agent.service.FunctionService;
import com.yl.agent.valuelist.ValueListRemoteAction;

import net.mlw.vlh.ValueList;

/**
 * 服务商功能远程操作接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年7月11日
 * @version V1.0.0
 */
public class AgentFunctionInterfaceImpl  implements AgentFunctionInterface{

	private FunctionService functionService;
	private ValueListRemoteAction valueListRemoteAction;
	
	/**
	 * 分页查询
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Page findAll(Map<String, Object> param) {
		ValueList vl = valueListRemoteAction.execute("function", param).get("function");
		Page page=new Page<>();
		page.setCurrentPage(vl.getValueListInfo().getPagingPage());
		page.setTotalResult(vl.getValueListInfo().getTotalNumberOfEntries());
		page.setObject(vl.getList());
		return page;
	}
	
	/**
	 * 新增
	 */
	@Override
	public Function addFunction(Function function) {
		com.yl.agent.entity.Function fun= functionService.create(JsonUtils.toObject(JsonUtils.toJsonString(function), new TypeReference<com.yl.agent.entity.Function>(){}));
		return JsonUtils.toObject(JsonUtils.toJsonString(fun), new TypeReference<Function>(){});
	}

	/**
	 * 编辑
	 */
	@Override
	public Function modify(Function function) {
		com.yl.agent.entity.Function fun= functionService.update(JsonUtils.toObject(JsonUtils.toJsonString(function), new TypeReference<com.yl.agent.entity.Function>(){}));
		return JsonUtils.toObject(JsonUtils.toJsonString(fun), new TypeReference<Function>(){});
	}

	/**
	 * 删除
	 */
	@Override
	public void delete(Long id) {
		functionService.delete(id);
	}

	/**
	 * 根据ID查询
	 */
	@Override
	public Function findById(Long id) {
		com.yl.agent.entity.Function fun= functionService.findById(id);
		return JsonUtils.toObject(JsonUtils.toJsonString(fun), new TypeReference<Function>(){});
	}
	
	public FunctionService getFunctionService() {
		return functionService;
	}

	public void setFunctionService(FunctionService functionService) {
		this.functionService = functionService;
	}

	public ValueListRemoteAction getValueListRemoteAction() {
		return valueListRemoteAction;
	}

	public void setValueListRemoteAction(ValueListRemoteAction valueListRemoteAction) {
		this.valueListRemoteAction = valueListRemoteAction;
	}
}