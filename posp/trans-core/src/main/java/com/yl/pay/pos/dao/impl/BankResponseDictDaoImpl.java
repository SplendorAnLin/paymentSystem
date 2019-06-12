package com.yl.pay.pos.dao.impl;

import com.yl.pay.pos.dao.BankResponseDictDao;
import com.yl.pay.pos.dao.impl.helper.EntityDao;
import com.yl.pay.pos.entity.BankResponseDict;

/**
 * Title: BankResponseDictDao Dao
 * Description: 
 * Copyright: Copyright (c)2011
 * Company: com.yl.pay
 * @author haitao.liu
 */
public class BankResponseDictDaoImpl implements BankResponseDictDao{
	
	private EntityDao entityDao;

	//根据银行编码，返回码查询
	public BankResponseDict findByCode(String interfaceCode, String responseCode){
		
		String hql="from BankResponseDict b where b.interfaceCode=? and b.respCode = ?  ";
		return entityDao.findUnique(BankResponseDict.class, hql, interfaceCode, responseCode);
	}

	public EntityDao getEntityDao() {
		return entityDao;
	}
	public void setEntityDao(EntityDao entityDao) {
		this.entityDao = entityDao;
	}
}
