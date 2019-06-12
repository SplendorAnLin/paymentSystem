package com.yl.boss.service.impl;

import java.util.List;

import com.yl.boss.dao.SyncInfoDao;
import com.yl.boss.entity.SyncInfo;
import com.yl.boss.enums.Status;
import com.yl.boss.enums.SyncType;
import com.yl.boss.service.SyncInfoService;

public class SyncInfoServiceImpl implements SyncInfoService {

	SyncInfoDao syncInfoDao;
	
	@Override
	public List<SyncInfo> getSyncInfo() {
		return syncInfoDao.getSyncInfo();
	}

	@Override
	public void syncInfoAdd(SyncInfo syncInfo) {
		syncInfoDao.syncInfoAdd(syncInfo);
	}

	@Override
	public void syncInfoUpdate(SyncInfo syncInfo) {
		syncInfoDao.syncInfoUpdate(syncInfo);
	}

	@Override
	public SyncInfo syncInfoById(Long id) {
		return syncInfoDao.syncInfoById(id);
	}

	@Override
	public void syncInfoAddFormPosp(SyncType syncType,String info,Status isCreate) {
		syncInfoDao.syncInfoAddFormPosp(syncType, info, isCreate);
	}

	public SyncInfoDao getSyncInfoDao() {
		return syncInfoDao;
	}

	public void setSyncInfoDao(SyncInfoDao syncInfoDao) {
		this.syncInfoDao = syncInfoDao;
	}
	
}
