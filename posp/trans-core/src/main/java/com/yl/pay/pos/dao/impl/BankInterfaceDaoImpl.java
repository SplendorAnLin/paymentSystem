package com.yl.pay.pos.dao.impl;

import com.yl.pay.pos.dao.BankInterfaceDao;
import com.yl.pay.pos.dao.impl.helper.EntityDao;
import com.yl.pay.pos.entity.BankInterface;
import com.yl.pay.pos.enums.Status;

/**
 * Title: BankInterfaceDaoImpl
 * Description: 
 * Copyright: Copyright (c)2011
 * Company: com.yl.pay
 * @author haitao.liu
 */

public class BankInterfaceDaoImpl implements BankInterfaceDao{

	public EntityDao entityDao;
	
	public BankInterface create(BankInterface bankInterface) {
		entityDao.persist(bankInterface );
		return bankInterface;
	}

	@Override
	public BankInterface findByCode(String code) {
		String hql="from BankInterface b where b.code=?  ";
		return entityDao.findUnique(BankInterface.class, hql, code);
	}

	@Override
	public BankInterface findByCodeAndStatus(String code, Status status) {
		String hql="from BankInterface b where b.code=? and b.status=? ";
		return entityDao.findUnique(BankInterface.class, hql, code,status);
	}

	public BankInterface findById(Long id) {
		return entityDao.findById(BankInterface.class, id);
	}

	public BankInterface update(BankInterface bankInterface) {
		return entityDao.merge(bankInterface);
	}	
	
	public EntityDao getEntityDao() {
		return entityDao;
	}

	public void setEntityDao(EntityDao entityDao) {
		this.entityDao = entityDao;
	}
}
