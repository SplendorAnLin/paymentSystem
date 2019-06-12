package com.yl.agent.service.impl;

import java.util.List;

import org.apache.log4j.Logger;

import com.yl.agent.dao.FunctionDao;
import com.yl.agent.dao.MenuDao;
import com.yl.agent.dao.OperatorDao;
import com.yl.agent.dao.RoleDao;
import com.yl.agent.entity.Role;
import com.yl.agent.service.RoleService;

/**
 * 角色serviceImpl
 * 
 * @author 聚合支付有限公司
 * @since 2016年7月13日
 * @version V1.0.0
 */
public class RoleServiceImpl implements RoleService {
	
	private static final Logger logger = Logger.getLogger(RoleServiceImpl.class) ;
	
	private RoleDao roleDao;
	private FunctionDao functionDao;
	private MenuDao menuDao;
	private OperatorDao operatorDao;

	public List<Role> findAll() {
		return roleDao.findAll();
	}
	public Role findById(Long id) {
		return roleDao.findById(id);
	}
	public Role update(Role roles) {
		return roleDao.update(roles);
	}
	public Role create(Role role) {
		return roleDao.create(role);
	}
	public void delete(Role role) {
		roleDao.delete(role);
	}
	
	public RoleDao getRoleDao() {
		return roleDao;
	}
	public void setRoleDao(RoleDao roleDao) {
		this.roleDao = roleDao;
	}

	public FunctionDao getFunctionDao() {
		return functionDao;
	}
	public void setFunctionDao(FunctionDao functionDao) {
		this.functionDao = functionDao;
	}
	
	public MenuDao getMenuDao() {
		return menuDao;
	}
	public void setMenuDao(MenuDao menuDao) {
		this.menuDao = menuDao;
	}

	public OperatorDao getOperatorDao() {
		return operatorDao;
	}
	public void setOperatorDao(OperatorDao operatorDao) {
		this.operatorDao = operatorDao;
	}
	@Override
	public List<Role> findAllAvilable(String customerNo) {
		return roleDao.findAllAvilable(customerNo);
	}
	public List<Role> findByName(String name,String customerNo) {
		return roleDao.findByName(name,customerNo);
	}
}
