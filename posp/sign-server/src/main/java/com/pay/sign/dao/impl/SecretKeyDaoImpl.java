package com.pay.sign.dao.impl;


import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.pay.sign.dao.SecretKeyDao;
import com.pay.sign.dao.util.EntityDao;
import com.pay.sign.dbentity.SecretKey;

/**
 * Title: BankDaoImpl
 * Description: 
 * Copyright: Copyright (c)2011
 * Company: pay
 * @author haitao.liu
 */
@Component("secretKeyDao")
public class SecretKeyDaoImpl implements SecretKeyDao{
	@Resource
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
	

}
