package com.yl.agent.service.impl;

import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.yl.agent.dao.DAOException;
import com.yl.agent.dao.MenuDao;
import com.yl.agent.dao.RoleDao;
import com.yl.agent.entity.Menu;
import com.yl.agent.service.MenuService;
import com.yl.agent.service.ServiceException;

/**
 * 菜单管理
 * 
 * @author 聚合支付有限公司
 * @since 2016年7月13日
 * @version V1.0.0
 */
public class MenuServiceImpl implements MenuService {
	
	private static final Logger logger = Logger.getLogger(MenuServiceImpl.class) ;
	private MenuDao menuDao;
	private RoleDao roleDao;
		
	
	public MenuDao getMenuDao() {
		return menuDao;
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
	public List<Menu> findShowAll() throws ServiceException {
		return menuDao.findShowAll();
	}
}