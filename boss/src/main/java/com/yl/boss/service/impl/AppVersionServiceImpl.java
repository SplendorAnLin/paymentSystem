package com.yl.boss.service.impl;

import java.util.Date;
import java.util.List;
import com.yl.boss.dao.AppVersionDao;
import com.yl.boss.dao.AppVersionHistoryDao;
import com.yl.boss.entity.AppVersion;
import com.yl.boss.entity.AppVersionHistory;
import com.yl.boss.enums.Status;
import com.yl.boss.service.AppVersionService;

/**
 * oemAPP服务接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月13日
 * @version V1.0.0
 */
public class AppVersionServiceImpl implements AppVersionService {
	
	private AppVersionDao appVersionDao;
	private AppVersionHistoryDao appVersionHistoryDao;

	@Override
	public void create(AppVersion appVersion, String oper) {
		appVersion.setCreateTime(new Date());
		appVersion.setAppAuditStatus(Status.FALSE);
		appVersionDao.create(appVersion);
		AppVersionHistory appVersionHistory =  new AppVersionHistory(appVersion, oper);
		appVersionHistoryDao.create(appVersionHistory);
	}
	
	public boolean exist(String type,String agentNo){
		return exist(type,agentNo);
	}

	@Override
	public AppVersion findByType(String type, String oem) {
		return appVersionDao.findByType(type,oem);
	}

	@Override
	public void update(AppVersion appVersion, String oper) {
		AppVersion appVersionOld = appVersionDao.findById(appVersion.getId());
		if(appVersionOld != null){
			appVersionOld.setAppInfo(appVersion.getAppInfo());
			appVersionOld.setRemark(appVersion.getRemark());
			appVersionOld.setVersion(appVersion.getVersion());
			appVersionOld.setUrl(appVersion.getUrl());
			appVersionDao.update(appVersionOld);
			
			AppVersionHistory appVersionHistory = new AppVersionHistory(appVersionOld, oper);
			appVersionHistoryDao.create(appVersionHistory);
		}
	}

	@Override
	public AppVersion findById(long id) {
		return appVersionDao.findById(id);
	}

	@Override
	public List<AppVersionHistory> findHistByAgentNo(String agentNo) {
		return appVersionHistoryDao.findByAgentNo(agentNo);
	}

	public AppVersionDao getAppVersionDao() {
		return appVersionDao;
	}

	public AppVersionHistoryDao getAppVersionHistoryDao() {
		return appVersionHistoryDao;
	}

	public void setAppVersionDao(AppVersionDao appVersionDao) {
		this.appVersionDao = appVersionDao;
	}

	public void setAppVersionHistoryDao(AppVersionHistoryDao appVersionHistoryDao) {
		this.appVersionHistoryDao = appVersionHistoryDao;
	}
	
}