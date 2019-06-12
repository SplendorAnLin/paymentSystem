package com.yl.customer.dao.impl;


import java.util.List;

import com.yl.customer.dao.DAOException;
import com.yl.customer.dao.EntityDao;
import com.yl.customer.dao.OperatorDao;
import com.yl.customer.entity.Operator;

/**
 * 操作员实现接口处理
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月11日
 * @version V1.0.0
 */
public class OperatorDaoImpl implements OperatorDao {

	private EntityDao entityDao ;
	

	public Operator create(Operator operator) {
		entityDao.persist(operator);
		return operator;
	}

	public Operator findById(Long id) {
		return entityDao.findById(Operator.class,id);
	}
	public Operator findByUsername(String username) {
		try {
			String hql = "from Operator where username= ?";
			Operator operator = this.entityDao.findUnique(Operator.class, hql, username);
			return operator;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	@SuppressWarnings("unchecked")
	public List<Operator> findByUsernames(String username) {
		String hql = "from Operator where username= ?"; 
		return entityDao.find(hql, username);
	}
	public Operator findByUsernameAndCustomerno(String custmoerno,
			String username) {
		String hql = "from Operator where username= ? and customer_no= ?";
		Operator operator = this.entityDao.findUnique(Operator.class, hql, username,custmoerno);
		return operator;
	}
	@SuppressWarnings("unchecked")
	public List<Operator> findAll(){
		List<Operator> operators = (List<Operator>)entityDao.find("from Operator");
		return operators;
	}
	public Operator update(Operator operator) {
		if (operator == null) {
			throw new DAOException("Operator update error operatoris null");
		}
		entityDao.update(operator);
		return operator;
	}
	public void delete(Operator operator){
		entityDao.remove(operator);
	}
	
	public Operator findByCustomerNo(String customerNo) {
		String hql = "from Operator where customerNo= ? and roleId = ?";
		Operator operator = this.entityDao.findUnique(Operator.class, hql,customerNo, 1L);
		return operator;
	}
	
	public Operator findByPhone(String phone,String customerNo) {
		String hql = "from Operator where customerNo= ? and username=?";
		Operator operator = this.entityDao.findUnique(Operator.class, hql,customerNo,phone);
		return operator;
	}
	public EntityDao getEntityDao() {
		return entityDao;
	}

	public void setEntityDao(EntityDao entityDao) {
		this.entityDao = entityDao;
	}

	@SuppressWarnings("rawtypes")
	public List findBySql( final String sql) {
		return entityDao.queryListInSession(sql);
	}

	@SuppressWarnings("unchecked")
	public List<Operator> findByHql(String sql) {
		return entityDao.find(sql);
	}

	@Override
	public void createRole(Long oid, Long rid) {
		String sql = "insert into operator_role values("+rid+","+oid+")";
		entityDao.executeSQLUpdate(sql, null);
	}

	@Override
	public void deleteRole(Long oid) {
		String sql = "delete from operator_role where operator_id = '" +oid+ "'";
		entityDao.executeSQLUpdate(sql, null);
	}
	
}
