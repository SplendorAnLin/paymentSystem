package com.yl.boss.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yl.boss.dao.OrganizationDao;
import com.yl.boss.entity.Organization;
import com.yl.boss.service.OrganizationService;

/**
 * 用户管理类业务接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月13日
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
	
	public List<Organization> findByParentCode(String code) {
		return organizationDao.findByParentCode(code);
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
