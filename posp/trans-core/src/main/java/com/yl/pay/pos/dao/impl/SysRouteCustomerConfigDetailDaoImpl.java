package com.yl.pay.pos.dao.impl;

import com.yl.pay.pos.dao.SysRouteCustomerConfigDetailDao;
import com.yl.pay.pos.dao.impl.helper.EntityDao;
import com.yl.pay.pos.entity.SysRouteCustomerConfig;
import com.yl.pay.pos.entity.SysRouteCustomerConfigDetail;
import com.yl.pay.pos.enums.Status;
import com.yl.pay.pos.enums.SysRouteCustConfDetailUseStatus;

import java.util.Date;
import java.util.List;

/**
 * Title: 
 * Description: 
 * Copyright: Copyright (c)2014
 * Company: com.yl.pay
 * @author jun
 */
public class SysRouteCustomerConfigDetailDaoImpl implements
		SysRouteCustomerConfigDetailDao {
	private EntityDao entityDao;
	
	@Override
	public SysRouteCustomerConfigDetail create(
			SysRouteCustomerConfigDetail sysRouteCustomerConfigDetail) {
		sysRouteCustomerConfigDetail.setCreateTime(new Date());
		entityDao.persist(sysRouteCustomerConfigDetail);
		return sysRouteCustomerConfigDetail;
	}

	@Override
	public SysRouteCustomerConfigDetail update(
			SysRouteCustomerConfigDetail sysRouteCustomerConfigDetail) {
		return entityDao.merge(sysRouteCustomerConfigDetail);
	}

	@Override
	public List<SysRouteCustomerConfigDetail> findBySysRouteCustConf(SysRouteCustomerConfig sysRouteCustomerConfig) {
		String hql="from SysRouteCustomerConfigDetail s where s.sysRouteCustomerConfig=? and s.status=? and s.useStatus=? ";
		return entityDao.find(hql, sysRouteCustomerConfig,Status.TRUE,SysRouteCustConfDetailUseStatus.UN_CLAIM);
	}

	@Override
	public SysRouteCustomerConfigDetail findById(Long id) {
		return entityDao.findById(SysRouteCustomerConfigDetail.class, id);
	}
	
	@Override
	public SysRouteCustomerConfigDetail findByBankInterfaceAndBankCustomerNoAndStatus(String bankInterface, String bankCustomerNo, Status status) {
		String hql="from SysRouteCustomerConfigDetail s where s.bankInterface=? and s.bankCustomerNo=? and s.status=? ";
		return entityDao.findUnique(SysRouteCustomerConfigDetail.class, hql, bankInterface,bankCustomerNo,status);
	}

	public EntityDao getEntityDao() {
		return entityDao;
	}

	public void setEntityDao(EntityDao entityDao) {
		this.entityDao = entityDao;
	}

	

	
	
}
