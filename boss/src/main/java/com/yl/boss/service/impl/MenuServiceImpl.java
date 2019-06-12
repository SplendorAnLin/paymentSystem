package com.yl.boss.service.impl;

import java.util.List;
import java.util.Set;

import com.yl.boss.dao.DAOException;
import com.yl.boss.dao.MenuDao;
import com.yl.boss.dao.RoleDao;
import com.yl.boss.entity.Menu;
import com.yl.boss.service.MenuService;
import com.yl.boss.service.ServiceException;

/**
 * 菜单业务接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月13日
 * @version V1.0.0
 */
public class MenuServiceImpl implements MenuService {
	
	private MenuDao menuDao;
	private RoleDao roleDao;
		
	
	public MenuDao getMenuDao() {
		return menuDao;
	}
	public void setMenuDao(MenuDao menuDao) {
		this.menuDao = menuDao;
	}
	public RoleDao getRoleDao() {
		return roleDao;
	}
	public void setRoleDao(RoleDao roleDao) {
		this.roleDao = roleDao;
	}
	public Menu create(Menu menu) throws DAOException {
		return menuDao.create(menu);
	}
	public void delete(Menu menu) throws DAOException {
		menuDao.delete(menu);
	}
	public Menu update(Menu menu) throws DAOException {
		return menuDao.update(menu);
	}
	public Menu findById(Long id) throws DAOException {
		return menuDao.findById(id);
	}
	public List<Menu> findAll() throws ServiceException {
		return menuDao.findAll();
	}
	public List<Menu> findByParentId(Long parentId) throws ServiceException {
		return menuDao.findByParentId(parentId);
	}
	public List<Menu> findByLevel(String level) throws ServiceException {
		return menuDao.findByLevel(level);
	}
	public List<Menu> findAllByRoleId(Long roleId) throws ServiceException {
		return null;
	}
	public Menu findMenuInTree() throws ServiceException {
		return null;
	}
	public Menu toTree(Set<Menu> menus) {
		return null;
	}
	@Override
	public List<Menu> findMenuByName(String name) throws DAOException {
		return menuDao.findMenuByName(name);
	}
	@Override
	public Menu findMenuName(String name) throws DAOException {
		// TODO Auto-generated method stub
		return menuDao.findMenuName(name).get(0);
	}
	@Override
	public List<Menu> findShowAll() throws ServiceException {
		return menuDao.findShowAll();
	}
}