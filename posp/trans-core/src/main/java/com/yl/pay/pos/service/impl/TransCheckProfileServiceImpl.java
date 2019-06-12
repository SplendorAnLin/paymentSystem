package com.yl.pay.pos.service.impl;

import com.yl.pay.pos.dao.TransCheckProfileDao;
import com.yl.pay.pos.entity.TransCheckProfile;
import com.yl.pay.pos.service.ITransCheckProfileService;

import java.util.List;

/**
 * Title: 
 * Description: 
 * Copyright: Copyright (c)2014
 * Company: com.yl.pay
 * @author jun
 */
public class TransCheckProfileServiceImpl implements ITransCheckProfileService{
	private TransCheckProfileDao transCheckProfileDao;
	
	/* (non-Javadoc)
	 * @see com.com.yl.pay.pos.service.ITransCheckProfile#create(com.com.yl.pay.pos.entity.TransCheckProfile)
	 */
	@Override
	public TransCheckProfile create(TransCheckProfile transCheckProfile) {
		// TODO Auto-generated method stub
		return transCheckProfileDao.create(transCheckProfile);
	}

	/* (non-Javadoc)
	 * @see com.com.yl.pay.pos.service.ITransCheckProfile#update(com.com.yl.pay.pos.entity.TransCheckProfile)
	 */
	@Override
	public TransCheckProfile update(TransCheckProfile transCheckProfile) {
		// TODO Auto-generated method stub
		return transCheckProfileDao.update(transCheckProfile);
	}

	/* (non-Javadoc)
	 * @see com.com.yl.pay.pos.service.ITransCheckProfile#findById(java.lang.Long)
	 */
	@Override
	public TransCheckProfile findById(Long id) {
		// TODO Auto-generated method stub
		return transCheckProfileDao.findById(id);
	}

	/* (non-Javadoc)
	 * @see com.com.yl.pay.pos.service.ITransCheckProfile#findByBizTypeAndProfileTypeAndProfileValue(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public List<TransCheckProfile> findByBizTypeAndProfileTypeAndProfileValue(
			String bizType, String profileType, String profileValue) {
		// TODO Auto-generated method stub
		return transCheckProfileDao.findByBizTypeAndProfileTypeAndProfileValue(bizType, profileType, profileValue);
	}

	public void setTransCheckProfileDao(TransCheckProfileDao transCheckProfileDao) {
		this.transCheckProfileDao = transCheckProfileDao;
	}

	
}
