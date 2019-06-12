package com.yl.agent.dao;

import java.util.List;

import com.yl.agent.entity.Role;

/**
 * 角色操作 
 * 
 * @author 聚合支付有限公司
 * @since 2016年7月1日
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
	 * 查找所有
	 * @return
	 * @throws DAOException
	 */
	public List<Role> findAll() throws DAOException;
	/**
	 * 查找当前代理商可用
	 * @param agentNo
	 * @return
	 * @throws DAOException
	 */
	public List<Role> findAllAvilable(String agentNo) throws DAOException;
	/**
	 * 角色重名检查
	 * @param name
	 * @param customerNo
	 * @return
	 * @throws DAOException
	 */
	public List<Role> findByName(String name,String customerNo)throws DAOException;
	
}
