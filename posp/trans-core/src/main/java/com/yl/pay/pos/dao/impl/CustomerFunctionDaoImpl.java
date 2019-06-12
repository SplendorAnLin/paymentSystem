package com.yl.pay.pos.dao.impl;

import com.yl.pay.pos.dao.CustomerFunctionDao;
import com.yl.pay.pos.dao.impl.helper.EntityDao;
import com.yl.pay.pos.entity.CustomerFunction;

import java.util.List;

/**
 * Title: CustomerFunctionDaoImpl
 * Description: 
 * Copyright: Copyright (c)2011
 * Company: com.yl.pay
 * @author haitao.liu
 */

public class CustomerFunctionDaoImpl implements CustomerFunctionDao{

	public EntityDao entityDao;

	public void setEntityDao(EntityDao entityDao) {
		this.entityDao = entityDao;
	}
	
	@Override
	public CustomerFunction create(CustomerFunction customerFunction) {
		entityDao.persist(customerFunction);
		return customerFunction;
	}

	@SuppressWarnings("unchecked")
	public List<CustomerFunction> findByCustomerId(Long customerId) {		
		String hql = "from CustomerFunction where customer.id = ?";		
		return entityDao.find(hql, customerId);
	}

	/* (non-Javadoc)
	 * @see com.com.yl.pay.pos.dao.CustomerFunctionDao#delete(java.lang.Long)
	 */
	@Override
	public void delete(Long id) {
		CustomerFunction function = entityDao.findById(CustomerFunction.class, id);
		if(function != null){
			entityDao.remove(function);
		}
		return;
	}

	/* (non-Javadoc)
	 * @see com.com.yl.pay.pos.dao.CustomerFunctionDao#findBy(java.lang.Long, java.lang.String, java.lang.String)
	 */
	@Override
	public CustomerFunction findBy(Long customerId, String trsanType, String cardType) {
		String hql = "from CustomerFunction where customer.id = ? and trsanType = ? and cardType = ?";		
		return entityDao.findUnique(CustomerFunction.class, hql, customerId, trsanType, cardType);
	}

	/* (non-Javadoc)
	 * @see com.com.yl.pay.pos.dao.CustomerFunctionDao#update(com.com.yl.pay.pos.entity.CustomerFunction)
	 */
	@Override
	public CustomerFunction update(CustomerFunction customerFunction) {
		return entityDao.merge(customerFunction);
	}

}
