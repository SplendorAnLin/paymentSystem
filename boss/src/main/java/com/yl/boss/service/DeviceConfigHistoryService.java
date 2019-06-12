package com.yl.boss.service;

import com.yl.boss.entity.DeviceConfigHistory;

/**
 * 设备配置历史业务接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月13日
 * @version V1.0.0
 */
public interface DeviceConfigHistoryService {
	/**
	 * 新增deviceconfig历史
	 */
	void insert(DeviceConfigHistory history);
}
