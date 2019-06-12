package com.yl.boss.dao.impl;

import java.util.List;

import com.yl.boss.dao.DevicePurchDao;
import com.yl.boss.dao.EntityDao;
import com.yl.boss.entity.DevicePurch;

/**
 * 设备订单数据接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月14日
 * @version V1.0.0
 */
public class DevicePurchDaoImpl implements DevicePurchDao {
	private EntityDao entityDao;
	
	@Override
	public void create(DevicePurch devicePurch) {
		entityDao.persist(devicePurch);
	}

	@Override
	public void update(DevicePurch devicePurch) {
		entityDao.update(devicePurch);
	}

	@Override
	public DevicePurch findById(long id) {
		return entityDao.findById(DevicePurch.class,id);
	}

	public EntityDao getEntityDao() {
		return entityDao;
	}

	public void setEntityDao(EntityDao entityDao) {
		this.entityDao = entityDao;
	}

	@SuppressWarnings("unchecked")
	@Override
	public DevicePurch findDevicePurchBy(String batchNumber) {
		String hql = "from DevicePurch where batchNumber = ?";
		List<DevicePurch> list = entityDao.find(hql,batchNumber);
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}

	
}
