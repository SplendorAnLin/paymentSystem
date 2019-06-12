package com.pay.sign.dao.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.pay.sign.dao.TransResponseDictDao;
import com.pay.sign.dao.util.EntityDao;
import com.pay.sign.dbentity.TransResponseDict;

/**
 * Title: 
 * Description: 
 * Copyright: Copyright (c)2011
 * Company: pay
 * @author yujun
 */
@Component
public class TransResponseDictDaoImpl implements TransResponseDictDao {
	@Resource
	public EntityDao entityDao;
	
	public TransResponseDict create(TransResponseDict transResponseDict){
		entityDao.save(transResponseDict);
		return transResponseDict;
	}
	
	public TransResponseDict findByExceptionCode(String exceptionCode){
		String hql = "from TransResponseDict where exceptionCode = ?";
		return entityDao.findUnique(TransResponseDict.class, hql, exceptionCode);
	}
	
	
	public EntityDao getEntityDao() {
		return entityDao;
	}
	public void setEntityDao(EntityDao entityDao) {
		this.entityDao = entityDao;
	}
}

