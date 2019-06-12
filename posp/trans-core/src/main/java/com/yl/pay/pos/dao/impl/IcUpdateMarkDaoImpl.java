package com.yl.pay.pos.dao.impl;

import com.yl.pay.pos.dao.IcUpdateMarkDao;
import com.yl.pay.pos.dao.impl.helper.EntityDao;
import com.yl.pay.pos.entity.IcUpdateMark;

public class IcUpdateMarkDaoImpl implements IcUpdateMarkDao{

	private EntityDao entityDao;
	@Override
	public IcUpdateMark findByPosCati(String posCati,String updateKey) {
	    String hql="from IcUpdateMark ium where ium.posCati=? and ium.updateType=?";
		return entityDao.findUnique(IcUpdateMark.class,hql, posCati,updateKey);
	}
	public EntityDao getEntityDao() {
		return entityDao;
	}
	public void setEntityDao(EntityDao entityDao) {
		this.entityDao = entityDao;
	}
	@Override
	public void updateIcMark(IcUpdateMark icUpdateMark) {
		entityDao.update(icUpdateMark);
	}
	@Override
	public IcUpdateMark createIcMark(IcUpdateMark icUpdateMark) {
		entityDao.persist(icUpdateMark);
		return icUpdateMark;
	}
	@Override
	public IcUpdateMark findByType(String updateType) {
		 String hql="from IcUpdateMark ium where ium.updateType=?";
		 return entityDao.findUnique(IcUpdateMark.class,hql,updateType);
	}
	
	

}
