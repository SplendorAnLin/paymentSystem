package com.yl.pay.pos.dao.impl;

import com.yl.pay.pos.dao.SysRespCustomerNoConfigDao;
import com.yl.pay.pos.dao.impl.helper.EntityDao;
import com.yl.pay.pos.entity.SysRespCustomerNoConfig;
import com.yl.pay.pos.enums.Status;

import java.util.Date;

/**
 * Title: 
 * Description: 
 * Copyright: Copyright (c)2014
 * Company: com.yl.pay
 * @author jun
 */
public class SysRespCustomerNoConfigDaoImpl implements SysRespCustomerNoConfigDao{
	private EntityDao entityDao;
	
	@Override
	public SysRespCustomerNoConfig create(
			SysRespCustomerNoConfig sysRespCustomerNoConfig) {
		// TODO Auto-generated method stub
		sysRespCustomerNoConfig.setCreateTime(new Date());
		entityDao.persist(sysRespCustomerNoConfig);
		return sysRespCustomerNoConfig;
	}

	@Override
	public SysRespCustomerNoConfig findByCustomerNo(String customerNo) {
		// TODO Auto-generated method stub
		String hql="from SysRespCustomerNoConfig s where s.status=? and s.customerNo=? ";
		return entityDao.findUnique(SysRespCustomerNoConfig.class, hql, Status.TRUE,customerNo);
	}

	public EntityDao getEntityDao() {
		return entityDao;
	}

	public void setEntityDao(EntityDao entityDao) {
		this.entityDao = entityDao;
	}
	
}
