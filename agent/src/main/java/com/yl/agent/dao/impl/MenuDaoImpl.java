package com.yl.agent.dao.impl;

import java.util.List;

import com.yl.agent.dao.DAOException;
import com.yl.agent.dao.EntityDao;
import com.yl.agent.dao.MenuDao;
import com.yl.agent.entity.Function;
import com.yl.agent.entity.Menu;
import com.yl.agent.service.ServiceException;

/**
 * 菜单实现接口处理
 * 
 * @author 聚合支付有限公司
 * @since 2016年7月5日
 * @version V1.0.0
 */
public class MenuDaoImpl implements MenuDao{
	
	private EntityDao entityDao;
	
	public Menu create(Menu menu) throws DAOException {
		if(menu == null){
			throw new DAOException("Menu不能为空！");
		}
		entityDao.persist(menu);
		
		return menu;
	}

	public void delete(Menu menu) throws DAOException {
		if(menu == null){
			throw new DAOException("Menu不能为空！");
		}
		entityDao.remove(menu);
	}

	public Menu findById(Long id) throws DAOException {
		return entityDao.findById(Menu.class, id);
	}

	public Menu update(Menu menu) throws DAOException {
		entityDao.merge(menu);
		return menu;
	}

	@SuppressWarnings("unchecked")
	public List<Menu> findAll() throws ServiceException {
		return entityDao.find(" from Menu") ;
	}
	
	public List<Function> queryAll() throws ServiceException {
		String hql="from Function";
		return entityDao.find(hql);
	}

	public List<Menu> findByParentId(Long parentId) throws ServiceException {
		String hql="from Menu m where m.parentId = ? " ;
		return entityDao.find(hql,parentId) ;
	}

	public List<Menu> findByLevel(String level) throws ServiceException {
		String hql="from Menu as m where m.level = ?" ;
		return entityDao.find(hql,level) ;
	}
	
	public EntityDao getEntityDao() {
		return entityDao;
	}
	
	public void setEntityDao(EntityDao entityDao) {
		this.entityDao = entityDao;
	}
	@Override
	public List<Menu> findMenuByName(String name) throws ServiceException {
		String hql="from Menu as m where m.level>1 and m.name like ?" ;
		return entityDao.find(hql, "%"+name+"%");
	}

	@Override
	public List<Menu> findMenuName(String name) throws DAOException {
		String hql="from Menu as m where m.level>1 and m.name=?" ;
		return  entityDao.find(hql,name) ;
	}
	public List<Menu> findShowAll() throws ServiceException {
		return entityDao.find("from Menu where status='TRUE' order by displayOrder asc") ;
	}
}