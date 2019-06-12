package com.yl.pay.pos.dao.impl;

import com.yl.pay.pos.dao.TransRouteDao;
import com.yl.pay.pos.dao.impl.helper.EntityDao;
import com.yl.pay.pos.entity.TransRoute;
import com.yl.pay.pos.enums.Status;

/**
 * Title: 
 * Description: 
 * Copyright: Copyright (c)2012
 * Company: com.yl.pay
 * @author jun
 */

public class TransRouteDaoImpl implements TransRouteDao {
	private EntityDao entityDao;
	
	@Override
	public TransRoute findByCode(String code) {
		String hql="from TransRoute r where r.code=? and r.status=? ";
		return entityDao.findUnique(TransRoute.class, hql, code,Status.TRUE);
	}

	public EntityDao getEntityDao() {
		return entityDao;
	}

	public void setEntityDao(EntityDao entityDao) {
		this.entityDao = entityDao;
	}

	@Override
	public void save(TransRoute transRoute) {
		entityDao.save(transRoute);
	}
}