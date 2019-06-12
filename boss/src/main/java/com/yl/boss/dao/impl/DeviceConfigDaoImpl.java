package com.yl.boss.dao.impl;

import java.util.List;

import com.yl.boss.dao.DeviceConfigDao;
import com.yl.boss.dao.EntityDao;
import com.yl.boss.entity.DeviceConfig;

/**
 * 设备配置数据接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月14日
 * @version V1.0.0
 */
public class DeviceConfigDaoImpl implements DeviceConfigDao {
	private EntityDao entityDao;
	@Override
	public int deviceConfigCount() {
		String hql="from DeviceConfig ";
		try {
			List<DeviceConfig> list=entityDao.find(hql);
			if(list.size()>0){
				return list.size();
			}else{
				return 0;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public DeviceConfig byId(DeviceConfig config) {
		String hql=" from DeviceConfig where id=?";
		try {
			List<DeviceConfig> list=entityDao.find(hql, config.getId());
			if(list.size()>0){
				return list.get(0);
			}else{
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	@Override
	
	public DeviceConfig select() {		
		String hql=" from DeviceConfig";
		try {
			List<DeviceConfig> list=entityDao.find(hql);
			if(list.size()>0){
				return list.get(0);
			}else{
			return null;
			}
		} catch (Exception e) {
		e.printStackTrace();
		return null;
		}
	}

	@Override
	public void update(DeviceConfig config) {
		entityDao.update(config);
		
	}

	public EntityDao getEntityDao() {
		return entityDao;
	}

	public void setEntityDao(EntityDao entityDao) {
		this.entityDao = entityDao;
	}

	@Override
	public void save(DeviceConfig config) {
		try {
			entityDao.save(config);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	
	
}
