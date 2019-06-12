package com.yl.agent.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yl.agent.dao.OrganizationDao;
import com.yl.agent.entity.Organization;
import com.yl.agent.service.OrganizationService;

/**
 * 组织机构管理
 * 
 * @author 聚合支付有限公司
 * @since 2016年7月13日
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
