package com.yl.pay.pos.dao.impl;

import com.yl.pay.pos.dao.IndustryFunctionDao;
import com.yl.pay.pos.dao.impl.helper.EntityDao;
import com.yl.pay.pos.entity.IndustryFunction;

import java.util.List;

/**
 * Title: IndustryFunctionDaoImpl
 * Description: 
 * Copyright: Copyright (c)2011
 * Company: com.yl.pay
 * @author haitao.liu
 */

public class IndustryFunctionDaoImpl implements IndustryFunctionDao{

	public EntityDao entityDao;

	public List<IndustryFunction> findByMcc(String mcc){
		String hql = "from IndustryFunction where mcc = ?";		
		return entityDao.find(hql, mcc);
	}

	public EntityDao getEntityDao() {
		return entityDao;
	}
	public void setEntityDao(EntityDao entityDao) {
		this.entityDao = entityDao;
	}

}
