package com.yl.pay.pos.dao.impl;

import com.yl.pay.pos.dao.BankInterfaceSwitchConfigDao;
import com.yl.pay.pos.dao.impl.helper.EntityDao;
import com.yl.pay.pos.entity.BankInterfaceSwitchConfig;
import com.yl.pay.pos.enums.Status;

import java.util.List;

/**
 * Title: 
 * Description: 
 * Copyright: Copyright (c)2011
 * Company: com.yl.pay
 * @author jun
 */

public class BankInterfaceSwitchConfigDaoImpl implements BankInterfaceSwitchConfigDao{
	private EntityDao entityDao;

	@Override
	public BankInterfaceSwitchConfig create(
			BankInterfaceSwitchConfig bankInterfaceSwitchConfig) {
		entityDao.persist(bankInterfaceSwitchConfig);
		return bankInterfaceSwitchConfig;
	}

	@Override
	public BankInterfaceSwitchConfig findById(Long id) {
		return entityDao.findById(BankInterfaceSwitchConfig.class, id);
	}

	@Override
	public List<BankInterfaceSwitchConfig> findByInterfaceCodeAndRespCode(
			String interfaceCode, String respCode) {
		String hql="from BankInterfaceSwitchConfig c where c.interfaceCode=? and c.respCode=? and c.status=? ";
		return entityDao.find(hql, interfaceCode,respCode,Status.TRUE);
	}

	@Override
	public BankInterfaceSwitchConfig update(
			BankInterfaceSwitchConfig bankInterfaceSwitchConfig) {
		return entityDao.merge(bankInterfaceSwitchConfig);
	}

	public EntityDao getEntityDao() {
		return entityDao;
	}

	public void setEntityDao(EntityDao entityDao) {
		this.entityDao = entityDao;
	}
	
	
	
}
