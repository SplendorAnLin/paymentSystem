package com.yl.pay.pos.dao.impl;

import com.yl.pay.pos.dao.CustomerFeeDao;
import com.yl.pay.pos.dao.impl.helper.EntityDao;
import com.yl.pay.pos.entity.CustomerFee;
import com.yl.pay.pos.enums.Status;

import java.util.Date;
import java.util.List;


/**
 * Title: 
 * Description: 
 * Copyright: Copyright (c)2011
 * Company: com.yl.pay
 * @author haitao.liu
 */

public class CustomerFeeDaoImpl implements CustomerFeeDao {
	private EntityDao entityDao;
	
	@Override
	public CustomerFee create(CustomerFee customerFee) {
		entityDao.persist(customerFee);
		return customerFee;
	}

	@Override
	public List<CustomerFee> findByCustomerNoAndMccCode(String customerNo,String mcc) {
		String hql="from CustomerFee where customerNo=? and mcc=? and effectTime<? and status=?";
		return entityDao.find(hql, customerNo,mcc,new Date(),Status.TRUE);
	}
	
	@Override
	public CustomerFee findBycustNo(String customerNo) {
		String hql="from CustomerFee where customerNo=?";
		List<CustomerFee> list=entityDao.find(hql, customerNo);
		if (list.size()>0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public CustomerFee findById(Long id) {
		return entityDao.findById(CustomerFee.class, id);
	}

	@Override
	public void delete(CustomerFee customerFee) {
		entityDao.remove(customerFee);
	}

	@Override
	public CustomerFee findByCode(String code) {
		String hql="from CustomerFee f where f.code=? ";
		return entityDao.findUnique(CustomerFee.class, hql, code);
	}

	@Override
	public CustomerFee update(CustomerFee customerFee) {
		return entityDao.merge(customerFee);
	}

	public void setEntityDao(EntityDao entityDao) {
		this.entityDao = entityDao;
	}

	
}
