package com.yl.boss.dao.impl;

import java.util.Date;
import java.util.List;

import com.yl.boss.dao.EntityDao;
import com.yl.boss.dao.SyncInfoDao;
import com.yl.boss.entity.SyncInfo;
import com.yl.boss.enums.Status;
import com.yl.boss.enums.SyncResult;
import com.yl.boss.enums.SyncSys;
import com.yl.boss.enums.SyncType;

public class SyncInfoDaoImpl implements SyncInfoDao{
	private EntityDao entityDao;

	public List<SyncInfo> getSyncInfo() {
		String hql = "from SyncInfo where isSync=? and result=?";
		List<SyncInfo> list = entityDao.find(hql,Status.FALSE,SyncResult.UNKNOWN);
		return list;
	}
	
	@Override
	public void syncInfoAdd(SyncInfo syncInfo){
		entityDao.persist(syncInfo);
	}
	
	@Override
	public void syncInfoUpdate(SyncInfo syncInfo) {
		entityDao.update(syncInfo);
	}
	
	@Override
	public SyncInfo syncInfoById(Long id) {
		String hql = "from SyncInfo where id = ?";
		List<SyncInfo> list = entityDao.find(hql,id);
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}
	
	public EntityDao getEntityDao() {
		return entityDao;
	}

	public void setEntityDao(EntityDao entityDao) {
		this.entityDao = entityDao;
	}

	@Override
	public void syncInfoAddFormPosp(SyncType syncType, String info, Status isCreate) {
		SyncInfo syncInfo = new SyncInfo();
		syncInfo.setSyncType(syncType);
		syncInfo.setInfo(info);
		syncInfo.setCreateTime(new Date());
		syncInfo.setUpdateTime(new Date());
		syncInfo.setIsSync(Status.FALSE);
		syncInfo.setIsCreate(isCreate);
		syncInfo.setResult(SyncResult.UNKNOWN);
		syncInfo.setSyncCount(0);
		syncInfo.setSyncSys(SyncSys.POSP);
		entityDao.persist(syncInfo);
	}
}
