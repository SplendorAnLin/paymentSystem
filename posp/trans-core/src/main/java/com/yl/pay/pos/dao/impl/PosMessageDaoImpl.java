package com.yl.pay.pos.dao.impl;

import com.yl.pay.pos.dao.PosMessageDao;
import com.yl.pay.pos.dao.impl.helper.EntityDao;
import com.yl.pay.pos.entity.PosMessage;

public class PosMessageDaoImpl implements PosMessageDao{
	public EntityDao entityDao;
	public EntityDao getEntityDao() {
		return entityDao;
	}

	public void setEntityDao(EntityDao entityDao) {
		this.entityDao = entityDao;
	}

	@Override
	public PosMessage findById(Long id) {
		return entityDao.findById(PosMessage.class, id);
	}

	@Override
	public PosMessage create(PosMessage posMessage) {
		entityDao.persist(posMessage);
		return posMessage;
	}

	@Override
	public PosMessage update(PosMessage posMessage) {
		return entityDao.merge(posMessage);
	}

	@Override
	public PosMessage findByPosCati(String posCati) {
		String hql = "from PosMessage p where p.posCati = ? and sysdate between p.effTime and p.expTime and p.status='TRUE'";
		return entityDao.findUnique(PosMessage.class, hql, posCati);
		
	}

	@Override
	public void delete(PosMessage posMessage) {
		entityDao.remove(posMessage);
	}

}
