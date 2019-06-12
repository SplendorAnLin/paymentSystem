package com.yl.boss.dao.impl;


import java.util.List;

import com.yl.boss.dao.DAOException;
import com.yl.boss.dao.EntityDao;
import com.yl.boss.dao.OperatorDao;
import com.yl.boss.entity.Operator;

/**
 * 操作员数据接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月14日
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
		String hql = "from Operator where username= ?";
		Operator operator = this.entityDao.findUnique(Operator.class, hql, username);
		return operator;
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
		String hql = "from Operator where customerNo= ?";
		Operator operator = this.entityDao.findUnique(Operator.class, hql,customerNo);
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
	
}
