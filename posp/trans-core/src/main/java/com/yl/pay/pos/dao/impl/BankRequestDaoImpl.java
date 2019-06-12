package com.yl.pay.pos.dao.impl;

import com.yl.pay.pos.dao.BankRequestDao;
import com.yl.pay.pos.dao.impl.helper.EntityDao;
import com.yl.pay.pos.entity.BankRequest;

/**
 * Title: BankRequestDaoImpl
 * Description: 
 * Copyright: Copyright (c)2011
 * Company: com.yl.pay
 * @author haitao.liu
 */

public class BankRequestDaoImpl implements BankRequestDao{

	public EntityDao entityDao;
	
	public BankRequest create(BankRequest bankRequest) {
		entityDao.persist( bankRequest);
		return bankRequest;
	}

	public BankRequest update(BankRequest bankRequest) {
		return entityDao.merge(bankRequest);
	}
	
	public EntityDao getEntityDao() {
		return entityDao;
	}

	public void setEntityDao(EntityDao entityDao) {
		this.entityDao = entityDao;
	}
}
