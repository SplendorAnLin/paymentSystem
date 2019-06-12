package com.yl.pay.pos.dao.impl;

import com.yl.pay.pos.dao.TransResponseDictDao;
import com.yl.pay.pos.dao.impl.helper.EntityDao;
import com.yl.pay.pos.entity.TransResponseDict;

/**
 * Title: 
 * Description: 
 * Copyright: Copyright (c)2011
 * Company: com.yl.pay
 * @author haitao.liu
 */

public class TransResponseDictDaoImpl implements TransResponseDictDao {
	
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

