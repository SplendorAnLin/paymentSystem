package com.yl.customer.dao.impl;

import java.util.List;

import com.yl.customer.dao.DAOException;
import com.yl.customer.dao.EntityDao;
import com.yl.customer.dao.RoleDao;
import com.yl.customer.entity.Role;
import com.yl.customer.enums.Status;

/**
 * 角色操作实现接口处理
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月11日
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
		String hql=" from Role r where  r.status=?" ;
		List<Role> roles = entityDao.find(hql,Status.TRUE);
		return entityDao.find(hql) ;
	}

	@SuppressWarnings("unchecked")
	public List<Role> findAllAvilable(String customerNo) throws DAOException {
		String hql=" from Role r where r.customerNo = ? and r.status=? and r.roleType!='CUSTOMER'" ;
		return entityDao.find(hql,customerNo,Status.TRUE) ;
	}
	@SuppressWarnings("unchecked")
	public List<Role> findByName(String name,String customerNo) throws DAOException {
		String hql=" from Role r where r.name=? and r.customerNo=?" ;
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
