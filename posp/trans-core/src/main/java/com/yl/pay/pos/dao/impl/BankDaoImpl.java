package com.yl.pay.pos.dao.impl;


import com.yl.pay.pos.dao.BankDao;
import com.yl.pay.pos.dao.impl.helper.EntityDao;
import com.yl.pay.pos.entity.Bank;

/**
 * Title: BankDaoImpl
 * Description: 
 * Copyright: Copyright (c)2011
 * Company: com.yl.pay
 * @author haitao.liu
 */
public class BankDaoImpl implements BankDao{

	public EntityDao entityDao;
	
	public Bank create(Bank bank) {
		entityDao.persist(bank);
		return bank;
	}

	public Bank findByCode(String code) {
		String hql = "from Bank where code = ?";		
		return entityDao.findUnique(Bank.class, hql, code);
	}

	public EntityDao getEntityDao() {
		return entityDao;
	}

	public void setEntityDao(EntityDao entityDao) {
		this.entityDao = entityDao;
	}

}
