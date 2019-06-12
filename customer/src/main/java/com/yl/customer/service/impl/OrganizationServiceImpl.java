package com.yl.customer.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yl.customer.dao.OrganizationDao;
import com.yl.customer.entity.Organization;
import com.yl.customer.service.OrganizationService;

/**
 * 组织机构管理
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月20日
 * @version V1.0.0
 */
public class OrganizationServiceImpl implements OrganizationService{

	private Log log = LogFactory.getLog(OrganizationServiceImpl.class);
	private OrganizationDao organizationDao ;
	
	public List<Organization> findAllProvince() {
		return organizationDao.findProvince();
	}

	public List<Organization> findCityByCode(String code) {
		return organizationDao.findCityByCode(code);
	}
	
	public Organization findByName(String name) {
		return organizationDao.findByName(name);
	}
	
	public OrganizationDao getOrganizationDao() {
		return organizationDao;
	}
	public void setOrganizationDao(OrganizationDao organizationDao) {
		this.organizationDao = organizationDao;
	}
}
