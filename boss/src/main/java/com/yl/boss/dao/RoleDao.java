package com.yl.boss.dao;

import java.util.List;

import com.yl.boss.entity.Role;

/**
 * 角色操作接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月14日
 * @version V1.0.0
 */
public interface RoleDao {
	
	/**
	 * 根据ID查询角色
	 * @param id
	 * @return
	 * @throws DAOException
	 */
	public Role findById(Long id) throws DAOException;
	
	/**
	 * 创建角色
	 * @param role
	 * @return
	 * @throws DAOException
	 */
	public Role create(Role role) throws DAOException;
	
	/**
	 * 更新角色
	 * @param role
	 * @return
	 * @throws DAOException
	 */
	public Role update(Role roles) throws DAOException;
	
	/**
	 * 删除角色
	 * @param role
	 * @throws DAOException
	 */
	public void delete(Role role) throws DAOException;
	
	/**
	 * 查找所有
	 * @return
	 * @throws DAOException
	 */
	public List<Role> findAll() throws DAOException;
	
	/**
	 * 查找所有
	 * @return
	 * @throws DAOException
	 */
	public List<Role> findAllAvilable() throws DAOException;

	/**
	 * 按照商户类型查询角色
	 * @param roleType
	 * @return
	 */
	public List<Role> findRoleByCustomerType(String roleType);

	/**
	 * 按照角色类型查询角色
	 * @param string
	 * @return
	 */
	public Role findRoleByRoleType(String roleType);
	/**
	 * 按照角色名称检索
	 * @param name
	 * @return
	 */
	public List<Role> findByName(String name);
	
}
