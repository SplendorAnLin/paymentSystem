package com.yl.pay.pos.dao.impl;

import com.yl.pay.pos.dao.TransCheckProfileDao;
import com.yl.pay.pos.dao.impl.helper.EntityDao;
import com.yl.pay.pos.entity.TransCheckProfile;
import com.yl.pay.pos.enums.Status;

import java.util.List;

/**
 * Title: 
 * Description: 
 * Copyright: Copyright (c)2014
 * Company: com.yl.pay
 * @author jun
 */
public class TransCheckProfileDaoImpl implements TransCheckProfileDao{
	private EntityDao entityDao;
	
	/* (non-Javadoc)
	 * @see com.com.yl.pay.pos.dao.TransCheckProfileDao#create(com.com.yl.pay.pos.entity.OrderSyn)
	 */
	@Override
	public TransCheckProfile create(TransCheckProfile transCheckProfile) {
		// TODO Auto-generated method stub
		entityDao.persist(transCheckProfile);
		return transCheckProfile;
	}

	/* (non-Javadoc)
	 * @see com.com.yl.pay.pos.dao.TransCheckProfileDao#update(com.com.yl.pay.pos.entity.OrderSyn)
	 */
	@Override
	public TransCheckProfile update(TransCheckProfile transCheckProfile) {
		// TODO Auto-generated method stub
		return entityDao.merge(transCheckProfile);
	}

	/* (non-Javadoc)
	 * @see com.com.yl.pay.pos.dao.TransCheckProfileDao#findById(java.lang.Long)
	 */
	@Override
	public TransCheckProfile findById(Long id) {
		// TODO Auto-generated method stub
		return entityDao.findById(TransCheckProfile.class, id);
	}

	/* (non-Javadoc)
	 * @see com.com.yl.pay.pos.dao.TransCheckProfileDao#findByBizTypeAndProfileTypeAndProfileValue(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public List<TransCheckProfile> findByBizTypeAndProfileTypeAndProfileValue(
			String bizType, String profileType, String profileValue) {
		// TODO Auto-generated method stub
		String hql="from TransCheckProfile t where t.bizType=? and t.profileType=? and t.profileValue=? and t.status=?";
		return entityDao.find(hql, bizType,profileType,profileValue,Status.TRUE);
	}
	
	@Override
	public List<TransCheckProfile> findByTypeAndValue(String bizType,
			String profileType, String profileValue) {
		String hql="from TransCheckProfile t where t.bizType=? and t.profileType=? and t.profileValue=?";
		return entityDao.find(hql, bizType,profileType,profileValue);
	}
	
	public void setEntityDao(EntityDao entityDao) {
		this.entityDao = entityDao;
	}
}
