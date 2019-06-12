package com.yl.boss.dao.impl;

import com.yl.boss.dao.EntityDao;
import com.yl.boss.dao.ReportCustomerDao;
import com.yl.boss.entity.ReportCustomer;

public class ReportCustomerDaoImpl implements ReportCustomerDao{

	private EntityDao entityDao;
	
	@Override
	public void create(ReportCustomer reportCustomer) {
		entityDao.persist(reportCustomer);
	}

	@Override
	public void update(ReportCustomer reportCustomer) {
		entityDao.update(reportCustomer);
	}

	@Override
	public ReportCustomer findById(long id) {
		return entityDao.findById(ReportCustomer.class, id);
	}

	public EntityDao getEntityDao() {
		return entityDao;
	}

	public void setEntityDao(EntityDao entityDao) {
		this.entityDao = entityDao;
	}

}
