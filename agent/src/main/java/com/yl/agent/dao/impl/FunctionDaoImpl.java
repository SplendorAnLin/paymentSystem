package com.yl.agent.dao.impl;

import java.util.List;

import com.yl.agent.dao.DAOException;
import com.yl.agent.dao.EntityDao;
import com.yl.agent.dao.FunctionDao;
import com.yl.agent.entity.Function;
import com.yl.agent.enums.Status;

/**
 * 功能 操作
 * 
 * @author 聚合支付有限公司
 * @since 2016年7月5日
 * @version V1.0.0
 */
public class FunctionDaoImpl implements FunctionDao {
	
	private EntityDao entityDao;
	
	public Function create(Function function) throws DAOException {
		if(function == null){
			throw new DAOException("Function不能为空！");
		}
		entityDao.persist(function);
		return function;
	}

	public void delete(Function function) throws DAOException {
		if(function == null){
			throw new DAOException("Function不能为空！");
		}
		entityDao.remove(function);		
	}

	public Function findById(Long id) throws DAOException {
		return entityDao.findById(Function.class, id);
	}


	public Function update(Function function) throws DAOException {
		entityDao.update(function);
		return function;
	}
	
	public List<Function> findAll() throws DAOException {
		String queryString = "from Function";
		return (List<Function>)entityDao.find(queryString);
	}
	
	public List<Function> findAvailableFunction() throws DAOException{
		String queryString = "from Function f where f.status = ?";
		return (List<Function>)entityDao.find(queryString,Status.TRUE);
	}

	public EntityDao getEntityDao() {
		return entityDao;
	}

	public void setEntityDao(EntityDao entityDao) {
		this.entityDao = entityDao;
	}

}
