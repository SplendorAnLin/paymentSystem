package com.yl.pay.pos.dao.impl;

import com.yl.pay.pos.dao.CardAmountLimitDao;
import com.yl.pay.pos.dao.impl.helper.EntityDao;
import com.yl.pay.pos.entity.CardAmountLimit;
import com.yl.pay.pos.enums.Status;

public class CardAmountLimitDaoImpl implements CardAmountLimitDao {

	EntityDao entityDao;
	@Override
	public CardAmountLimit create(CardAmountLimit cardAmountLimit) {
		entityDao.persist(cardAmountLimit);
		return cardAmountLimit;
	}

	@Override
	public CardAmountLimit findByPan(String pan) {
		String hql="from CardAmountLimit where pan=? and status=? ";
		return entityDao.findUnique(CardAmountLimit.class, hql, pan,Status.TRUE);
	}

	@Override
	public CardAmountLimit update(CardAmountLimit cardAmountLimit) {
		return entityDao.merge(cardAmountLimit);
	}

	public void setEntityDao(EntityDao entityDao) {
		this.entityDao = entityDao;
	}

	
}
