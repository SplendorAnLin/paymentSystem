package com.yl.agent.dao.impl;

import java.util.List;

import com.yl.agent.dao.DAOException;
import com.yl.agent.dao.EntityDao;
import com.yl.agent.dao.RoleDao;
import com.yl.agent.entity.Role;
import com.yl.agent.enums.Status;

/**
 * 角色操作
 * 
 * @author 聚合支付有限公司
 * @since 2016年7月8日
 * @version V1.0.0
 */
public class RoleDaoImpl implements RoleDao {
	private EntityDao entityDao;

	public Role create(Role role) throws DAOException {
		if(role == null){
			throw new DAOException("Role不能为空！");
		}
		entityDao.merge(role);
		return role;
	}

	public void delete(Role role) throws DAOException {
		if(role == null){
			throw new DAOException("Role不能为空！");
		}
		entityDao.remove(role);		
	}

	public Role findById(Long id) throws DAOException {
		return entityDao.findById(Role.class, id);
	}
	
	public Role update(Role role) throws DAOException {
		return entityDao.merge(role) ;
	}
	
	@SuppressWarnings("unchecked")
	public List<Role> findAll() throws DAOException {
		String hql=" from Role r where r.status=? " ;
		List<Role> roles = entityDao.find(hql,Status.TRUE);
		return entityDao.find(hql) ;
	}

	@SuppressWarnings("unchecked")
	public List<Role> findAllAvilable(String agentNo) throws DAOException {
		String hql=" from Role r where r.status=? and  r.agentNo = ? and r.roleType!='AGENT'" ;
		return entityDao.find(hql,Status.TRUE,agentNo) ;
	}
	@SuppressWarnings("unchecked")
	public List<Role> findByName(String name,String customerNo) throws DAOException {
		String hql=" from Role r where r.name=? and r.agentNo=?" ;
		return entityDao.find(hql,name,customerNo) ;
	}
	
	
	public EntityDao getEntityDao() {
		return entityDao;
	}
	public void setEntityDao(EntityDao entityDao) {
		this.entityDao = entityDao;
	}

	@SuppressWarnings("unchecked")
	public List<Role> findRoleByCustomerType(String roleType) {
		String hql=" from Role r where r.roleType in (?,?) and  r.status=? " ;
		return entityDao.find(hql,roleType,"PUBLIC",Status.TRUE) ;
	}

	@SuppressWarnings("unchecked")
	public Role findRoleByRoleType(String roleType) {
		String hql=" from Role r where r.roleType = ? and  r.status=? " ;
		List<Role> roles = entityDao.find(hql,roleType,Status.TRUE) ;
		if(roles != null && roles.size() > 0){
			return roles.get(0);
		}else{
			return null;
		}
		
	
	}
}
