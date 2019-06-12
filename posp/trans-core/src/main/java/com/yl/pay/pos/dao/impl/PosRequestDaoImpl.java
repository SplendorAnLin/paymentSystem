package com.yl.pay.pos.dao.impl;

import com.yl.pay.pos.dao.PosRequestDao;
import com.yl.pay.pos.dao.impl.helper.EntityDao;
import com.yl.pay.pos.entity.PosRequest;
import com.yl.pay.pos.exception.TransExceptionConstant;
import com.yl.pay.pos.exception.TransRunTimeException;

/**
 * Title: PosRequestDaoImpl
 * Description: 
 * Copyright: Copyright (c)2011
 * Company: com.yl.pay
 * @author haitao.liu
 */

public class PosRequestDaoImpl implements PosRequestDao{
	public EntityDao entityDao;
	
	public PosRequest create(PosRequest posRequest) {
		if(posRequest==null){
			throw new TransRunTimeException(TransExceptionConstant.POSREQUEST_IS_NULL);
		}
		entityDao.persist(posRequest);
		return posRequest;
	}

	public PosRequest findById(Long id) {
		return entityDao.findById(PosRequest.class, id);
	}

	public PosRequest update(PosRequest posRequest) {
		return entityDao.merge(posRequest);
	}

	public EntityDao getEntityDao() {
		return entityDao;
	}

	public void setEntityDao(EntityDao entityDao) {
		this.entityDao = entityDao;
	}
}
