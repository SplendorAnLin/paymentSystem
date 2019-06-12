package com.yl.pay.pos.dao.impl;

import com.yl.pay.pos.dao.BankIdNumberDao;
import com.yl.pay.pos.dao.impl.helper.EntityDao;
import com.yl.pay.pos.entity.BankIdNumber;

import java.util.List;

/**
 * Title: BankIdNumberDaoImpl
 * Description: 
 * Copyright: Copyright (c)2011
 * Company: com.yl.pay
 * @author haitao.liu
 */

public class BankIdNumberDaoImpl implements BankIdNumberDao{

	public EntityDao entityDao;

	public List<BankIdNumber> findByPan(String pan, int panLength, String prefix){	
		
		String hql = "from BankIdNumber bankIdNumber where bankIdNumber.panLength = ? and bankIdNumber.verifyCode like ?";
		return entityDao.find(hql, panLength, prefix+"%");		
	}
	
	public List<BankIdNumber> findbyPanLengthAndVerifyCode(int panLength,
			String verifyCode) {
		String hql="from BankIdNumber bin where bin.panLength =? and bin.verifyCode=?";
		return entityDao.find(hql,panLength,verifyCode);
	}
	
	@Override
	public List<BankIdNumber> findAll() {
		String hql="from BankIdNumber ";
		return entityDao.find(hql);
	}

	public EntityDao getEntityDao() {
		return entityDao;
	}

	public void setEntityDao(EntityDao entityDao) {
		this.entityDao = entityDao;
	}
}
