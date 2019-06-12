/**
 * 
 */
package com.yl.pay.pos.dao.impl;

import com.yl.pay.pos.dao.LngAndLatDao;
import com.yl.pay.pos.dao.impl.helper.EntityDao;
import com.yl.pay.pos.entity.LngAndLat;

import java.util.Date;

public class LngAndLatDaoImpl implements LngAndLatDao {
	public EntityDao entityDao;

	@Override
	public LngAndLat create(LngAndLat lngAndLat) {
		lngAndLat.setCreateTime(new Date());
		entityDao.persist(lngAndLat);
		return lngAndLat;
	}

	@Override
	public LngAndLat findByPosCati(String posCati) {
		String hql="from LngAndLat where posCati=?";
		return entityDao.findUnique(LngAndLat.class, hql, posCati);
	}

	@Override
	public LngAndLat update(LngAndLat lngAndLat) {
		return entityDao.merge(lngAndLat);
	}

	public void setEntityDao(EntityDao entityDao) {
		this.entityDao = entityDao;
	}

}
