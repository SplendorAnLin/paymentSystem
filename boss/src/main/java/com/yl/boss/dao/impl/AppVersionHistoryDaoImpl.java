package com.yl.boss.dao.impl;

import java.util.List;

import com.yl.boss.dao.AppVersionHistoryDao;
import com.yl.boss.dao.EntityDao;
import com.yl.boss.entity.AppVersionHistory;

/**
 * oem版客户端历史数据访问接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月11日
 * @version V1.0.0
 */
public class AppVersionHistoryDaoImpl implements AppVersionHistoryDao {
	
	private EntityDao entityDao;

	@Override
	public void create(AppVersionHistory appVersionHistory) {
		entityDao.persist(appVersionHistory);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AppVersionHistory> findByAgentNo(String agentNo) {
		String hql = "from AppVersionHistory where agentNo = ?";
		return entityDao.find(hql,agentNo);
	}

	public EntityDao getEntityDao() {
		return entityDao;
	}

	public void setEntityDao(EntityDao entityDao) {
		this.entityDao = entityDao;
	}

}
