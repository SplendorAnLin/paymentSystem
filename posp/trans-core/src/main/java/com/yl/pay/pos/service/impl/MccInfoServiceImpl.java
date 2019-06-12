package com.yl.pay.pos.service.impl;

import com.yl.pay.pos.dao.MccInfoDao;
import com.yl.pay.pos.entity.MccInfo;
import com.yl.pay.pos.service.MccInfoService;

public class MccInfoServiceImpl implements MccInfoService{

	MccInfoDao mccInfoDao;
	
	@Override
	public MccInfo create(MccInfo mccInfo) {
		return mccInfoDao.create(mccInfo);
	}

	@Override
	public MccInfo findByMcc(String mcc) {
		return mccInfoDao.findByMcc(mcc);
	}
	
	@Override
	public MccInfo update(MccInfo mccInfo) {
		return mccInfoDao.update(mccInfo);
	}

	public MccInfoDao getMccInfoDao() {
		return mccInfoDao;
	}

	public void setMccInfoDao(MccInfoDao mccInfoDao) {
		this.mccInfoDao = mccInfoDao;
	}

}
