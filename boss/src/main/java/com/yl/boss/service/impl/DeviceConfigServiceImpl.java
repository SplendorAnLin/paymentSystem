package com.yl.boss.service.impl;

import com.yl.boss.dao.DeviceConfigDao;
import com.yl.boss.entity.DeviceConfig;
import com.yl.boss.service.DeviceConfigService;

/**
 * 设备配置业务接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月13日
 * @version V1.0.0
 */
public class DeviceConfigServiceImpl implements DeviceConfigService {
	private DeviceConfigDao deviceConfigDao;

	@Override
	public int deviceConfigCount() {
		return deviceConfigDao.deviceConfigCount();
	}

	@Override
	public DeviceConfig byId(DeviceConfig config) {
		return deviceConfigDao.byId(config);
	}

	@Override
	public void update(DeviceConfig config) {
		deviceConfigDao.update(config);	
	}

	public DeviceConfigDao getDeviceConfigDao() {
		return deviceConfigDao;
	}

	public void setDeviceConfigDao(DeviceConfigDao deviceConfigDao) {
		this.deviceConfigDao = deviceConfigDao;
	}

	@Override
	public DeviceConfig select() {
		return deviceConfigDao.select();
	}

	@Override
	public void save(DeviceConfig config) {
		deviceConfigDao.save(config);
		
	}
	
}
