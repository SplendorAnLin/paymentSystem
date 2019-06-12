package com.yl.boss.dao.impl;

import java.util.List;

import com.yl.boss.dao.DeviceTypeDao;
import com.yl.boss.dao.EntityDao;
import com.yl.boss.entity.DeviceType;

/**
 * 设备类型数据接口是实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月14日
 * @version V1.0.0
 */
public class DeviceTypeDaoIpml implements DeviceTypeDao {
	private EntityDao entityDao;

	@SuppressWarnings("unchecked")
	public List<DeviceType> find() {
		String hql = "from DeviceType where STATUS = ?";
		return entityDao.find(hql,"TRUE");
	}
	
	@Override
	public DeviceType findById(Long id) {
		return entityDao.findById(DeviceType.class,id);
	}

	@Override
	public void create(DeviceType deviceType) {
		entityDao.persist(deviceType);
	}

	@Override
	public void update(DeviceType deviceType) {
		entityDao.update(deviceType);
	}

	public EntityDao getEntityDao() {
		return entityDao;
	}

	public void setEntityDao(EntityDao entityDao) {
		this.entityDao = entityDao;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DeviceType> byParentId() {
		String hql="from DeviceType where parentId is null";
		return entityDao.find(hql);
	}

	@Override
	public void updateParent(DeviceType deviceType) {
		entityDao.update(deviceType);
	}

	
}
