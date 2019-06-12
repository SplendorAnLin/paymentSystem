package com.pay.sign.dao.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.pay.sign.dao.PosRequestDao;
import com.pay.sign.dao.util.EntityDao;
import com.pay.sign.dbentity.PosRequest;
import com.pay.sign.exception.TransExceptionConstant;
import com.pay.sign.exception.TransRunTimeException;

/**
 * Title: PosRequestDaoImpl
 * Description: 
 * Copyright: Copyright (c)2015
 * Company: pay
 * @author zhongxiang.ren
 */
@Component
public class PosRequestDaoImpl implements PosRequestDao{
	@Resource
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
