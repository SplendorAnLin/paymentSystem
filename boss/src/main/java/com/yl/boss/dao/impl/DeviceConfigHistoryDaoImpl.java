package com.yl.boss.dao.impl;

import com.yl.boss.dao.DeviceConfigHistoryDao;
import com.yl.boss.dao.EntityDao;
import com.yl.boss.entity.DeviceConfigHistory;

/**
 * 设备配置历史数据接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月14日
 * @version V1.0.0
 */
public class DeviceConfigHistoryDaoImpl implements DeviceConfigHistoryDao {
	private EntityDao entityDao;
	@Override
	public void insert(DeviceConfigHistory history) {
		try {
			entityDao.save(history);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public EntityDao getEntityDao() {
		return entityDao;
	}

	public void setEntityDao(EntityDao entityDao) {
		this.entityDao = entityDao;
	}


	
}
