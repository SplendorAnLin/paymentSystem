package com.yl.pay.pos.dao.impl;

import com.yl.pay.pos.dao.ParameterDao;
import com.yl.pay.pos.dao.impl.helper.EntityDao;
import com.yl.pay.pos.entity.Parameter;
import com.yl.pay.pos.enums.Status;

import java.util.Date;
import java.util.List;


/**
 * Title: POS参数信息
 * Description:  POS参数信息
 * Copyright: Copyright (c)2010
 * Company: AFC
 * @author Administrator
 */

public class ParameterDaoImpl implements ParameterDao{

	public EntityDao entityDao;
	
	public List<Parameter> findByPosCatiAndVersion(String posCATI, String version){		
		String hql = "from Parameter p where posCATI = ? and p.paramVersion > ? " +
				"and p.status = ? and p.expTime >? and p.effTime < ?";
		return entityDao.find(hql, posCATI, version, Status.TRUE, new Date(), new Date());
	}
	
	public EntityDao getEntityDao() {
		return entityDao;
	}
	
	public void setEntityDao(EntityDao entityDao) {
		this.entityDao = entityDao;
	}

	
}
