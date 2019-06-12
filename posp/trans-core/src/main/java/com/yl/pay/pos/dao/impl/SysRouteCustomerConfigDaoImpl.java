package com.yl.pay.pos.dao.impl;

import com.yl.pay.pos.dao.SysRouteCustomerConfigDao;
import com.yl.pay.pos.dao.impl.helper.EntityDao;
import com.yl.pay.pos.entity.SysRouteCustomerConfig;
import com.yl.pay.pos.enums.Status;

import java.util.Date;

/**
 * Title: 
 * Description: 
 * Copyright: Copyright (c)2014
 * Company: com.yl.pay
 * @author jun
 */
public class SysRouteCustomerConfigDaoImpl implements SysRouteCustomerConfigDao {
	private EntityDao entityDao;
	
	@Override
	public SysRouteCustomerConfig findByBankInterfaceAndBankCustNoAndStatus(
			String bankInterface, String bankCustomerNo, Status status) {
		String hql="from SysRouteCustomerConfig c where c.bankInterface=? and c.bankCustomerNo=? and c.status=? ";
		return entityDao.findUnique(SysRouteCustomerConfig.class, hql, bankInterface,bankCustomerNo,status);
	}

	@Override
	public SysRouteCustomerConfig create(SysRouteCustomerConfig sysRouteCustomerConfig) {
		sysRouteCustomerConfig.setCreateTime(new Date());
		entityDao.persist(sysRouteCustomerConfig);
		return sysRouteCustomerConfig;
	}
	
	@Override
	public SysRouteCustomerConfig update(SysRouteCustomerConfig config) {
		return entityDao.merge(config);
	}
	@Override
	public SysRouteCustomerConfig findById(Long id) {
		String hql="from SysRouteCustomerConfig c where c.id=? ";
		return entityDao.findUnique(SysRouteCustomerConfig.class, hql, id);
	}
	
	public EntityDao getEntityDao() {
		return entityDao;
	}

	public void setEntityDao(EntityDao entityDao) {
		this.entityDao = entityDao;
	}

	

	

	
}
