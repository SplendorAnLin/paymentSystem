package com.yl.pay.pos.dao.impl;

import com.yl.pay.pos.dao.CardInfoDao;
import com.yl.pay.pos.dao.impl.helper.EntityDao;
import com.yl.pay.pos.entity.CardInfo;

import java.util.List;

/**
 * Title: CardInfoDaoImpl
 * Description: 
 * Copyright: Copyright (c)2011
 * Company: com.yl.pay
 * @author haitao.liu
 */


public class CardInfoDaoImpl implements CardInfoDao{

	public EntityDao entityDao;
	
	public CardInfo create(CardInfo cardInfo) {
		entityDao.persist(cardInfo);
		return cardInfo;
	}

	public CardInfo findById(Long id) {
		return entityDao.findById(CardInfo.class, id);
	}

	public CardInfo findByPan(String pan) {
		String hql = "from CardInfo c where c.pan = ?";
		List<CardInfo> cardInfos = entityDao.find(hql,pan);
		if(cardInfos == null || cardInfos.isEmpty()){
			return null;
		}else{
			return cardInfos.get(0);
		}
	}
	
	public CardInfo update(CardInfo cardInfo) {
		return entityDao.merge(cardInfo);
	}
	
	public EntityDao getEntityDao() {
		return entityDao;
	}

	public void setEntityDao(EntityDao entityDao) {
		this.entityDao = entityDao;
	}
}
