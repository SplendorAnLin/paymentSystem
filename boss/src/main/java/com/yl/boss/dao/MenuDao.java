package com.yl.boss.dao;

import java.util.List;

import com.yl.boss.entity.Menu;
import com.yl.boss.service.ServiceException;

/**
 * 菜单操作接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月14日
 * @version V1.0.0
 */
public interface MenuDao {
	
	/**
	 * 新建菜单
	 * @param menu
	 * @return
	 * @throws DAOException
	 */
	public Menu create(Menu menu) throws DAOException;
	
	/**
	 * 删除菜单
	 * @param menu
	 * @return
	 * @throws DAOException
	 */
	public void delete(Menu menu) throws DAOException;
	
	/**
	 * 更新菜单
	 * @param menu
	 * @throws DAOException
	 */
	public Menu update(Menu menu) throws DAOException;
	
	/**
	 * 根据ID查找唯一菜单
	 * @param id
	 * @return
	 * @throws DAOException
	 */
	public Menu findById(Long id) throws DAOException;
	/**
	 * 根据菜单名模糊查询菜单
	 * @param name
	 * @return
	 * @throws DAOException
	 */
	public List<Menu> findMenuByName(String name) throws DAOException;
	/**
	 * 根据菜单名模糊查询菜单
	 * @param name
	 * @return
	 * @throws DAOException
	 */
	public List<Menu> findMenuName(String name) throws DAOException;
	
	/**
	 * 查找所有功能
	 * @param role
	 * @return
	 * @throws ServiceException
	 */
	public List<Menu> findAll() throws ServiceException;
	/**
	 * 查找所有有效的目录
	 * @return
	 * @throws ServiceException
	 */
	public List<Menu> findShowAll() throws ServiceException;
	/**
	 * 根据父菜单的ID进行查询
	 * @return
	 * @throws ServiceException
	 */
	public List<Menu> findByParentId(Long parentId) throws ServiceException;
	/**
	 * 根据菜单级别进行查询
	 * @return
	 * @throws ServiceException
	 */
	public List<Menu> findByLevel(String level) throws ServiceException;
}
