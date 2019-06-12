package com.yl.customer.service.impl;

import java.util.List;

import org.apache.log4j.Logger;

import com.yl.customer.dao.FunctionDao;
import com.yl.customer.dao.MenuDao;
import com.yl.customer.dao.OperatorDao;
import com.yl.customer.dao.RoleDao;
import com.yl.customer.entity.Role;
import com.yl.customer.service.RoleService;

/**
 * 角色serviceImpl
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月20日
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
	public List<Role> findByName(String name,String customerNo) {
		return roleDao.findByName(name,customerNo);
	}
	public List<Role> findAllAvilable(String customerNo){
		return roleDao.findAllAvilable(customerNo);
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

}
