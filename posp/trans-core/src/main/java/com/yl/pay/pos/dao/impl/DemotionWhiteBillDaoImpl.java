package com.yl.pay.pos.dao.impl;

import com.yl.pay.pos.dao.DemotionWhiteBillDao;
import com.yl.pay.pos.dao.impl.helper.EntityDao;
import com.yl.pay.pos.entity.DemotionWhiteBill;

public class DemotionWhiteBillDaoImpl implements DemotionWhiteBillDao {
	private EntityDao entityDao;
	@Override
	public DemotionWhiteBill findByCodeAndLength(String bankCode,
			String verifyCode,Integer panLength,Integer verifyLength) {
		String hql ="FROM DemotionWhiteBill WHERE bankCode=? and verifyCode=? and panLength=? and verifyLength=?";
		return entityDao.findUnique(DemotionWhiteBill.class, hql, bankCode,verifyCode,panLength,verifyLength);
	}
	public EntityDao getEntityDao() {
		return entityDao;
	}
	public void setEntityDao(EntityDao entityDao) {
		this.entityDao = entityDao;
	}

}
