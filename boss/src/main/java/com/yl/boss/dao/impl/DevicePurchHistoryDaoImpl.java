package com.yl.boss.dao.impl;

import java.util.List;

import com.yl.boss.dao.DevicePurchHistoryDao;
import com.yl.boss.dao.EntityDao;
import com.yl.boss.entity.DevicePurchHistory;

/**
 * 设备订单历史数据接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月14日
 * @version V1.0.0
 */
public class DevicePurchHistoryDaoImpl implements DevicePurchHistoryDao{
	private EntityDao entityDao;
	
	@Override
	public void create(DevicePurchHistory devicePurchHistory) {
		entityDao.persist(devicePurchHistory);
	}

	@Override
	public List<DevicePurchHistory> find(DevicePurchHistory devicePurchHistory) {
		return null;
	}

	public EntityDao getEntityDao() {
		return entityDao;
	}

	public void setEntityDao(EntityDao entityDao) {
		this.entityDao = entityDao;
	}

}
