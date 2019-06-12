package com.yl.pay.pos.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yl.pay.pos.dao.CustomerDao;
import com.yl.pay.pos.entity.Customer;
import com.yl.pay.pos.enums.Status;
import com.yl.pay.pos.enums.YesNo;
import com.yl.pay.pos.service.CustomerService;

public class CustomerServiceImpl implements CustomerService{
	private static final Log log = LogFactory.getLog(CustomerServiceImpl.class);
	CustomerDao customerDao;
	
	@Override
	public boolean create(Customer customer) {
		try {
			customerDao.create(customer);
			return true;
		} catch (Exception e) {
			log.error(e);
			return false;
		}
	}

	@Override
	public boolean update(Customer customer) {
		try {
			customerDao.update(customer);
			return true;
		} catch (Exception e) {
			log.error(e);
			return false;
		}
	}

	@Override
	public Customer findByCustomerNo(String customerNo) {
		return customerDao.findByCustomerNo(customerNo);
	}

	public CustomerDao getCustomerDao() {
		return customerDao;
	}

	public void setCustomerDao(CustomerDao customerDao) {
		this.customerDao = customerDao;
	}
	
}
