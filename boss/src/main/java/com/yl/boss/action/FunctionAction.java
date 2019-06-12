package com.yl.boss.action;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.yl.boss.entity.Function;
import com.yl.boss.entity.Role;
import com.yl.boss.service.FunctionService;
import com.yl.boss.service.RoleService;

/**
 * 功能控制器
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月28日
 * @version V1.0.0
 */
public class FunctionAction extends Struts2ActionSupport {
	
	private static final long serialVersionUID = 3435216337803134593L;
	private FunctionService functionService;
	private Function function;
	private List<Function> functionList;
	private RoleService roleService;
	
	/**
	 * 新增功能
	 * @return
	 */
	public String addFunction(){
		function = functionService.create(function);
		Role role=roleService.findById(Long.valueOf(1));
		Set<Function> functions = new HashSet<Function>();
		for (Function functionOld : functions) {
			functions.add(functionOld);
		}
		functions.add(function);
		role.setFunctions(functions);
		roleService.update(role);
		return SUCCESS;
	}
	
	/**
	 * 修改
	 * @return
	 */
	public String modify(){
		Function functionDb = functionService.findById(function.getId());
		functionDb.setName(function.getName());
		functionDb.setAction(function.getAction());
		functionDb.setIsCheck(function.getIsCheck());
		functionDb.setStatus(function.getStatus());
		functionDb.setRemark(function.getRemark());
		functionDb.setMenuId(function.getMenuId());
		functionService.update(functionDb);
		return SUCCESS;
	}
	
	
	
	/**
	 * 删除
	 * @return
	 */
	public String delete(){
		String functionId = getHttpRequest().getParameter("Id");
		Long id = Long.parseLong(functionId);
		functionService.delete(id);
		return SUCCESS;
	}
	
	public FunctionService getFunctionService() {
		return functionService;
	}
	
	public void setFunctionService(FunctionService functionService) {
		this.functionService = functionService;
	}
	
	public Function getFunction() {
		return function;
	}
	
	public void setFunction(Function function) {
		this.function = function;
	}

	public List<Function> getFunctionList() {
		return functionList;
	}

	public void setFunctionList(List<Function> functionList) {
		this.functionList = functionList;
	}
}