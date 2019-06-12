package com.yl.boss.dao;

import com.yl.boss.entity.DeviceConfigHistory;

/**
 * 设备配置历史数据接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月14日
 * @version V1.0.0
 */
public interface DeviceConfigHistoryDao {
	/**
	 * 新增deviceconfig历史
	 */
	void insert(DeviceConfigHistory history);
}
