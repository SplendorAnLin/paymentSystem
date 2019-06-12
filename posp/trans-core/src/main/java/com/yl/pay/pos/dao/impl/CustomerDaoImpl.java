package com.yl.pay.pos.dao.impl;

import com.yl.pay.pos.dao.CustomerDao;
import com.yl.pay.pos.dao.impl.helper.EntityDao;
import com.yl.pay.pos.entity.Customer;

import java.util.List;

/**
 * Title: CustomerDaoImpl
 * Description: 
 * Copyright: Copyright (c)2011
 * Company: com.yl.pay
 * @author haitao.liu
 */

public class CustomerDaoImpl implements CustomerDao{

	public EntityDao entityDao;
	
	public Customer create(Customer customer) {
		entityDao.persist(customer);
		return customer;
	}

	public Customer findByCustomerNo(String customerNo) {
		String hql="from Customer c where c.customerNo=? ";
		return entityDao.findUnique(Customer.class, hql, customerNo);
	}
	
	public List<Customer> findCustomers() {
		String hql="from Customer c ";
		return entityDao.find(hql);
	}

	public Customer findById(Long id) {
		return entityDao.findById(Customer.class, id);
	}

	@Override
	public void delete(Customer customer) {
		entityDao.remove(customer);
	}

	public Customer update(Customer customer) {
		return entityDao.merge(customer);
	}

	public EntityDao getEntityDao() {
		return entityDao;
	}
	public void setEntityDao(EntityDao entityDao) {
		this.entityDao = entityDao;
	}

}
