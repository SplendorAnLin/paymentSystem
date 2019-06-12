package com.yl.pay.pos.dao.impl;

import com.yl.pay.pos.dao.TransRouteProfileDao;
import com.yl.pay.pos.dao.impl.helper.EntityDao;
import com.yl.pay.pos.entity.TransRouteProfile;
import com.yl.pay.pos.enums.Status;

import java.util.List;

/**
 * Title: 
 * Description: 
 * Copyright: Copyright (c)2012
 * Company: com.yl.pay
 * @author jun
 */

public class TransRouteProfileDaoImpl implements TransRouteProfileDao {
	private EntityDao entityDao;
	
	@Override
	public List<TransRouteProfile> findByTransRouteCode(String transRouteCode) {
		String hql="from TransRouteProfile p where p.transRouteCode=? and p.status=? ";
		return entityDao.find(hql, transRouteCode,Status.TRUE);
	}

	public EntityDao getEntityDao() {
		return entityDao;
	}

	public void setEntityDao(EntityDao entityDao) {
		this.entityDao = entityDao;
	}
	
}
