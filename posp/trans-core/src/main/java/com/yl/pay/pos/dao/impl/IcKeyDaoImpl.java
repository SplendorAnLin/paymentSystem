package com.yl.pay.pos.dao.impl;

import com.yl.pay.pos.dao.IcKeyDao;
import com.yl.pay.pos.dao.impl.helper.EntityDao;
import com.yl.pay.pos.entity.IcKey;

import java.util.List;

public class IcKeyDaoImpl implements IcKeyDao {
	public EntityDao entityDao;
	@Override
	public List<IcKey> findAll() {
		String hql=" from IcKey ik order by ik.seqIndex asc";
		return entityDao.find(hql);
	}
	@Override
	public IcKey findByRidAndIndex(String rid, String keyIndex) {
		String hql="from IcKey ik where ik.rid=? and ik.keyIndex=?";
		
		return entityDao.findUnique(IcKey.class, hql, rid,keyIndex);
	}
	
	public EntityDao getEntityDao() {
		return entityDao;
	}
	public void setEntityDao(EntityDao entityDao) {
		this.entityDao = entityDao;
	}



	
}
