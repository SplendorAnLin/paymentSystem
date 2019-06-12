package com.yl.boss.service;

import com.yl.boss.entity.DeviceConfig;

/**
 * 设备配置业务接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月13日
 * @version V1.0.0
 */
public interface DeviceConfigService {
	/**
	 *查询deviceconfig表中是否有数据
	 */
	int deviceConfigCount();
	/**
	 * 查询
	 */
	DeviceConfig byId(DeviceConfig config);
	/**
	 * 修改数据
	 */
	void update(DeviceConfig config);
	/**
	 * 不带条件查询
	 */
	DeviceConfig select();
	/**
	 * 新增数据
	 */
	void save(DeviceConfig config);
}
