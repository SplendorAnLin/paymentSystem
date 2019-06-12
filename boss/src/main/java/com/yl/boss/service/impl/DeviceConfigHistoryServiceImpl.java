package com.yl.boss.service.impl;

import com.yl.boss.dao.DeviceConfigHistoryDao;
import com.yl.boss.entity.DeviceConfigHistory;
import com.yl.boss.service.DeviceConfigHistoryService;

/**
 * 设备配置历史业务接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月13日
 * @version V1.0.0
 */
public class DeviceConfigHistoryServiceImpl implements DeviceConfigHistoryService{

		private DeviceConfigHistoryDao deviceConfigHistoryDao;
	@Override
	public void insert(DeviceConfigHistory history) {
		deviceConfigHistoryDao.insert(history);
	}
	public DeviceConfigHistoryDao getDeviceConfigHistoryDao() {
		return deviceConfigHistoryDao;
	}
	public void setDeviceConfigHistoryDao(DeviceConfigHistoryDao deviceConfigHistoryDao) {
		this.deviceConfigHistoryDao = deviceConfigHistoryDao;
	}
	

}
