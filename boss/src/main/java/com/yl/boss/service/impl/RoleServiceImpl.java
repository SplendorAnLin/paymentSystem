package com.yl.boss.service.impl;

import java.util.List;

import com.yl.boss.dao.FunctionDao;
import com.yl.boss.dao.MenuDao;
import com.yl.boss.dao.OperatorDao;
import com.yl.boss.dao.RoleDao;
import com.yl.boss.entity.Role;
import com.yl.boss.service.RoleService;

/**
 * 角色业务接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月13日
 * @version V1.0.0
 */
public class RoleServiceImpl implements RoleService {
	
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
	public List<Role> findAllAvilable(){
		return roleDao.findAllAvilable();
	}
	public void delete(Role role) {
		roleDao.delete(role);
	}
	public List<Role> findByName(String name){
		return roleDao.findByName(name);
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
