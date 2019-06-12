package com.yl.boss.dao.impl;

import java.util.List;

import com.yl.boss.dao.AppVersionDao;
import com.yl.boss.dao.EntityDao;
import com.yl.boss.entity.AppVersion;
import com.yl.boss.entity.CustomerCert;

/**
 * oem版客户端数据访问接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月11日
 * @version V1.0.0
 */
public class AppVersionDaoImpl implements AppVersionDao {

	private EntityDao entityDao;
	
	@SuppressWarnings("unchecked")
	public boolean exist(String type,String agentNo){
		String hql = "from AppVersion where type = ? and agentNo = ?";
		List<AppVersion> list = entityDao.find(hql,type,agentNo);
		if(list != null && list.size() > 0){
			return false;
		}
		return true;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public AppVersion findByType(String type, String oem) {
		String hql = "from AppVersion where type = ? and shortApp = ?";
		List<AppVersion> list = entityDao.find(hql,type,oem);
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}
	
	@Override
	public void create(AppVersion appVersion) {
		entityDao.persist(appVersion);
	}

	@Override
	public void update(AppVersion appVersion) {
		entityDao.update(appVersion);
	}

	@Override
	public AppVersion findById(Long id) {
		return entityDao.findById(AppVersion.class, id);
	}

	public EntityDao getEntityDao() {
		return entityDao;
	}

	public void setEntityDao(EntityDao entityDao) {
		this.entityDao = entityDao;
	}
	

}