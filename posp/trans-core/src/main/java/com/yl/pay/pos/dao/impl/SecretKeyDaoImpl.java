package com.yl.pay.pos.dao.impl;


import com.yl.pay.pos.dao.SecretKeyDao;
import com.yl.pay.pos.dao.impl.helper.EntityDao;
import com.yl.pay.pos.entity.SecretKey;

/**
 * Title: BankDaoImpl
 * Description: 
 * Copyright: Copyright (c)2011
 * Company: com.yl.pay
 * @author haitao.liu
 */
public class SecretKeyDaoImpl implements SecretKeyDao{

	public EntityDao entityDao;
	
	public SecretKey create(SecretKey secretKey) {
		entityDao.persist(secretKey);
		return secretKey;
	}

	public SecretKey findByKey(String keyName) {
		String hql = "from SecretKey where keyName = ?";		
		return entityDao.findUnique(SecretKey.class, hql, keyName);
	}

	public SecretKey update(SecretKey secretKey) {
		return entityDao.merge(secretKey);
	}
	
	public EntityDao getEntityDao() {
		return entityDao;
	}

	public void setEntityDao(EntityDao entityDao) {
		this.entityDao = entityDao;
	}

}
