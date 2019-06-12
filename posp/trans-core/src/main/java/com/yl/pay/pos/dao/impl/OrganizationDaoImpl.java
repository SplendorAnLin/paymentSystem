package com.yl.pay.pos.dao.impl;

import com.yl.pay.pos.dao.OrganizationDao;
import com.yl.pay.pos.dao.impl.helper.EntityDao;
import com.yl.pay.pos.entity.Organization;


/**
 * Title: 
 * Description: 
 * Copyright: Copyright (c)2011
 * Company: com.yl.pay
 * @author jun
 */

public class OrganizationDaoImpl implements OrganizationDao{
	private EntityDao entityDao; 

	@Override
	public Organization findByCode(String code) {
		String hql="from Organization o where o.code=? ";
		return entityDao.findUnique(Organization.class, hql, code);
	}

	public EntityDao getEntityDao() {
		return entityDao;
	}

	public void setEntityDao(EntityDao entityDao) {
		this.entityDao = entityDao;
	}
	
	
}

