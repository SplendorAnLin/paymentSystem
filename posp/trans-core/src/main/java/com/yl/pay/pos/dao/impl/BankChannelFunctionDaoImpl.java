package com.yl.pay.pos.dao.impl;

import com.yl.pay.pos.dao.BankChannelFunctionDao;
import com.yl.pay.pos.dao.impl.helper.EntityDao;
import com.yl.pay.pos.entity.BankChannelFunction;
import com.yl.pay.pos.enums.Status;
import com.yl.pay.pos.enums.TransType;


/**
 * Title: 
 * Description: 
 * Copyright: Copyright (c)2011
 * Company: com.yl.pay
 * @author jun
 */

public class BankChannelFunctionDaoImpl implements BankChannelFunctionDao {
	private EntityDao entityDao;
	
	@Override
	public BankChannelFunction create(BankChannelFunction bankChannelFunction) {
		entityDao.persist(bankChannelFunction);
		return null;
	}

	@Override
	public BankChannelFunction findBySortCodeAndTransTypeAndStatus(String sortCode, TransType transType, Status status) {
		String hql="from BankChannelFunction b where b.sortCode=? and transType=? and status=? ";
		return entityDao.findUnique(BankChannelFunction.class, hql, sortCode,transType,status);
	}

	public void setEntityDao(EntityDao entityDao) {
		this.entityDao = entityDao;
	}
	
	

}
