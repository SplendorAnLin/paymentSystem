package com.yl.pay.pos.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yl.pay.pos.dao.OrganizationDao;
import com.yl.pay.pos.entity.Organization;
import com.yl.pay.pos.service.OrganizationService;


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

	public Organization findByCode(String code) {
		return organizationDao.findByCode(code);
	}
	
	public OrganizationDao getOrganizationDao() {
		return organizationDao;
	}
	public void setOrganizationDao(OrganizationDao organizationDao) {
		this.organizationDao = organizationDao;
	}
}
