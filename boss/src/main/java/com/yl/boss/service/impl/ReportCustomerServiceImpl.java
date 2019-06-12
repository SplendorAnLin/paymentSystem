package com.yl.boss.service.impl;

import java.util.Date;

import com.yl.boss.dao.ReportCustomerDao;
import com.yl.boss.entity.ReportCustomer;
import com.yl.boss.service.ReportCustomerService;

public class ReportCustomerServiceImpl implements ReportCustomerService{

	ReportCustomerDao reportCustomerDao;
	
	@Override
	public void create(ReportCustomer reportCustomer) {
		reportCustomer.setCreateTime(new Date());
		reportCustomerDao.create(reportCustomer);
	}

	@Override
	public void update(ReportCustomer reportCustomer) {
		ReportCustomer reportCustomerOld=reportCustomerDao.findById(reportCustomer.getId());
		reportCustomerOld.setAppId(reportCustomer.getAppId());
		reportCustomerOld.setAttre(reportCustomer.getAttre());
		reportCustomerOld.setIndustry(reportCustomer.getIndustry());
		reportCustomerOld.setInterfaceNo(reportCustomer.getInterfaceNo());
		reportCustomerOld.setInterfaceType(reportCustomer.getInterfaceType());
		reportCustomerOld.setRemark(reportCustomer.getRemark());
		reportCustomerOld.setStatus(reportCustomer.getStatus());
	}

	@Override
	public ReportCustomer findById(long id) {
		return reportCustomerDao.findById(id);
	}

	public ReportCustomerDao getReportCustomerDao() {
		return reportCustomerDao;
	}

	public void setReportCustomerDao(ReportCustomerDao reportCustomerDao) {
		this.reportCustomerDao = reportCustomerDao;
	}

}
