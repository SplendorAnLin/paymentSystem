package com.yl.pay.pos.dao.impl;

import com.yl.pay.pos.dao.IcParamsDao;
import com.yl.pay.pos.dao.impl.helper.EntityDao;
import com.yl.pay.pos.entity.IcParams;

import java.util.List;

public class IcParamsDaoImpl implements IcParamsDao {
	
	public EntityDao entityDao;
	
	@Override
	public List<IcParams> findAll() {
		String hql="from IcParams ic order by ic.seqIndex asc";
		return entityDao.find(hql);
	}
	
	@Override
	public IcParams findByRidAndIndex(String aid) {
		String hql="from IcParams ip where ip.aid=?";
		return entityDao.findUnique(IcParams.class, hql, aid);
	}

	public EntityDao getEntityDao() {
		return entityDao;
	}

	public void setEntityDao(EntityDao entityDao) {
		this.entityDao = entityDao;
	}



	
	
	

}
