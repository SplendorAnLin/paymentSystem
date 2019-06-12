package com.yl.pay.pos.dao.impl;

import com.yl.pay.pos.dao.MccInfoDao;
import com.yl.pay.pos.dao.impl.helper.EntityDao;
import com.yl.pay.pos.entity.MccInfo;

/**
 * Title: 
 * Description: 
 * Copyright: Copyright (c)2011
 * Company: com.yl.pay
 * @author jun
 */
public class MccInfoDaoImpl implements MccInfoDao {
	private EntityDao entityDao;

	@Override
	public MccInfo create(MccInfo mccInfo) {
		entityDao.persist(mccInfo);
		return mccInfo;
	}

	@Override
	public MccInfo findByMcc(String mcc) {
		String hql="from MccInfo m where m.mcc=? ";
		return entityDao.findUnique(MccInfo.class, hql, mcc);
	}
	
	public MccInfo update(MccInfo mccInfo){
		return entityDao.merge(mccInfo);
	}

	public void setEntityDao(EntityDao entityDao) {
		this.entityDao = entityDao;
	}

	
	
}
