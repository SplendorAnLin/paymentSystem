package com.yl.pay.pos.dao.impl;

import com.yl.pay.pos.dao.TransRouteConfigDao;
import com.yl.pay.pos.dao.impl.helper.EntityDao;
import com.yl.pay.pos.entity.TransRouteConfig;
import com.yl.pay.pos.enums.RouteType;
import com.yl.pay.pos.enums.Status;

/**
 * Title: 
 * Description: 
 * Copyright: Copyright (c)2012
 * Company: com.yl.pay
 * @author jun
 */

public class TransRouteConfigDaoImpl implements TransRouteConfigDao {
	private EntityDao entityDao;
	
	
	@Override
	public TransRouteConfig create(TransRouteConfig config) {
		entityDao.persist(config);
		return config;
	}

	@Override
	public TransRouteConfig findByCustomerNo(String customerNo) {
		String hql="from TransRouteConfig g where g.customerNo=? and g.status=? ";
		return entityDao.findUnique(TransRouteConfig.class, hql, customerNo,Status.TRUE);
	}
	
	@Override
	public TransRouteConfig findByCustomerNoAndRouteType(String customerNo,RouteType routeType) {
		String hql="from TransRouteConfig g where g.customerNo=? and routeType=? and g.status=? ";
		return entityDao.findUnique(TransRouteConfig.class, hql, customerNo,routeType,Status.TRUE);
	}

	public EntityDao getEntityDao() {
		return entityDao;
	}

	public void setEntityDao(EntityDao entityDao) {
		this.entityDao = entityDao;
	}

}
