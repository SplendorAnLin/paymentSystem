package com.yl.pay.pos.dao.impl;

import com.yl.pay.pos.dao.BankChannelFeeDao;
import com.yl.pay.pos.dao.impl.helper.EntityDao;
import com.yl.pay.pos.entity.BankChannelFee;
import com.yl.pay.pos.enums.Status;

import java.util.List;


/**
 * Title: 
 * Description: 
 * Copyright: Copyright (c)2011
 * Company: com.yl.pay
 * @author jun
 */

public class BankChannelFeeDaoImpl implements BankChannelFeeDao {
	private EntityDao entityDao;
	
	@Override
	public BankChannelFee create(BankChannelFee bankChannelFee) {
		entityDao.persist(bankChannelFee);
		return bankChannelFee;
	}

	@Override
	public BankChannelFee findByBankChannelCode(String bankChannelCode) {
		String hql="from BankChannelFee b where b.bankChannelCode=? and b.status=? ";
		return entityDao.findUnique(BankChannelFee.class, hql, bankChannelCode,Status.TRUE);
	}

	@Override
	public List<BankChannelFee> findByChannelCode(String bankChannelCode) {
		String hql="from BankChannelFee b where b.bankChannelCode=? and b.status=? ";
		return entityDao.find(hql, bankChannelCode,Status.TRUE);
	}

	public void setEntityDao(EntityDao entityDao) {
		this.entityDao = entityDao;
	}

	
		
}
