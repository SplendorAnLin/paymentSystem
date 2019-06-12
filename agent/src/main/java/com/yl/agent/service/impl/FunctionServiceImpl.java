package com.yl.agent.service.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.lefu.commons.utils.Page;
import com.yl.agent.dao.FunctionDao;
import com.yl.agent.dao.RoleDao;
import com.yl.agent.entity.Function;
import com.yl.agent.entity.Role;
import com.yl.agent.service.FunctionService;

/**
 * 功能serviceImpl
 * 
 * @author 聚合支付有限公司
 * @since 2016年7月13日
 * @version V1.0.0
 */
public class FunctionServiceImpl implements FunctionService {
	
	private FunctionDao functionDao;	
	private RoleDao roleDao;

	public Page findPageAll(Map<String, Object> param) {
		return null;
	}
	
	public FunctionDao getFunctionDao() {
		return functionDao;
	}
	public void setFunctionDao(FunctionDao functionDao) {
		this.functionDao = functionDao;
	}
	public RoleDao getRoleDao() {
		return roleDao;
	}
	public void setRoleDao(RoleDao roleDao) {
		this.roleDao = roleDao;
	}
	public List<Function> findAll() {
		return functionDao.findAll();
	}
	public Function findById(Long id) {
		return functionDao.findById(id);
	}
	public Function create(Function function) {
		return functionDao.create(function);
	}
	public Function update(Function function) {
		return functionDao.update(function);
	}
	public void delete(Long id) {
		Function fun = functionDao.findById(id);
		Set<Role> roles = fun.getRoles();
		for (Iterator<Role> iterator = roles.iterator(); iterator.hasNext();) {
			Role role = (Role) iterator.next();
			role.getFunctions().remove(fun);
			roleDao.update(role);
		}
		functionDao.delete(fun);
	}
	@Override
	public List<Function> findShowAll() {
		return functionDao.findAvailableFunction();
	}
}
