package com.yl.customer.interfaces.impl;

import java.util.Map;

import org.codehaus.jackson.type.TypeReference;

import com.lefu.commons.utils.Page;
import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.customer.api.bean.Function;
import com.yl.customer.api.interfaces.CustFunctionInterface;
import com.yl.customer.service.FunctionService;
import com.yl.customer.valuelist.ValueListAction;
import com.yl.customer.valuelist.ValueListRemoteAction;

import net.mlw.vlh.ValueList;

/**
 * 商户功能远程操作接口处理
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月24日
 * @version V1.0.0
 */
public class CustFunctionInterfaceImpl  implements CustFunctionInterface{

	private FunctionService functionService;
	private ValueListRemoteAction valueListRemoteAction;
	
	/**
	 * 分页查询
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
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
		com.yl.customer.entity.Function fun= functionService.create(JsonUtils.toObject(JsonUtils.toJsonString(function), new TypeReference<com.yl.customer.entity.Function>(){}));
		return JsonUtils.toObject(JsonUtils.toJsonString(fun), new TypeReference<Function>(){});
	}
	
	/**
	 * 编辑
	 */
	@Override
	public Function modify(Function function) {
		com.yl.customer.entity.Function fun= functionService.update(JsonUtils.toObject(JsonUtils.toJsonString(function), new TypeReference<com.yl.customer.entity.Function>(){}));
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
		com.yl.customer.entity.Function fun= functionService.findById(id);
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