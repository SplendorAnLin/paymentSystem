package com.yl.customer.dao;

import java.util.List;

import com.yl.customer.entity.Role;

/**
 * 角色操作 
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月11日
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
	 * 查找所有有效的
	 * @return
	 * @throws DAOException
	 */
	public List<Role> findAllAvilable(String customerNo) throws DAOException;

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
	 * 角色重名检查
	 * @param name
	 * @param customerNo
	 * @return
	 * @throws DAOException
	 */
	public List<Role> findByName(String name,String customerNo)throws DAOException;
	
}
