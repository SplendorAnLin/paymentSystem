package com.yl.pay.pos.service.impl;

import com.yl.pay.pos.dao.TransRouteConfigDao;
import com.yl.pay.pos.dao.TransRouteDao;
import com.yl.pay.pos.entity.TransRoute;
import com.yl.pay.pos.entity.TransRouteConfig;
import com.yl.pay.pos.enums.RouteType;
import com.yl.pay.pos.service.ITransRouteConfigService;

/**
 * Title: 
 * Description: 
 * Copyright: Copyright (c)2012
 * Company: com.yl.pay
 * @author jun
 */

public class TransRouteConfigServiceImpl implements ITransRouteConfigService {
	private TransRouteConfigDao transRouteConfigDao;
	private TransRouteDao transRouteDao;
	
	@Override
	public TransRouteConfig findByCustomerNo(String customerNo) {
		TransRouteConfig config=transRouteConfigDao.findByCustomerNo(customerNo);
		if(config==null){
			return null;
		}
		TransRoute transRoute=transRouteDao.findByCode(config.getTransRouteCode());
		if(transRoute==null){
			return null;
		}
//		Date effectTime=config.getEffectTime();
//		Date expireTime=config.getExpireTime();
//		if(config!=null){
//			if (effectTime!=null&&new Date().compareTo(effectTime) < 0) {
//				return null;
//			}
//			if (expireTime!=null&&new Date().compareTo(expireTime) > 0) {
//				return null;
//			}
//		}
		return config;
	}
	
	@Override
	public TransRouteConfig findByCustomerNoAndRouteType(String customerNo,RouteType routeType) {
		TransRouteConfig config=transRouteConfigDao.findByCustomerNoAndRouteType(customerNo,routeType);
		if(config==null){
			return null;
		}
		TransRoute transRoute=transRouteDao.findByCode(config.getTransRouteCode());
		if(transRoute==null){
			return null;
		}
		return config;
	}

	public TransRouteConfigDao getTransRouteConfigDao() {
		return transRouteConfigDao;
	}

	public void setTransRouteConfigDao(TransRouteConfigDao transRouteConfigDao) {
		this.transRouteConfigDao = transRouteConfigDao;
	}

	public TransRouteDao getTransRouteDao() {
		return transRouteDao;
	}

	public void setTransRouteDao(TransRouteDao transRouteDao) {
		this.transRouteDao = transRouteDao;
	}

	@Override
	public void save(TransRouteConfig transRouteConfig) {
		transRouteConfigDao.create(transRouteConfig);
	}

}
