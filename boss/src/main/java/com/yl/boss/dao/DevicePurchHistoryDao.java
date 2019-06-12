package com.yl.boss.dao;

import java.util.List;

import com.yl.boss.entity.DevicePurchHistory;

/**
 * 设备订单历史数据接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月14日
 * @version V1.0.0
 */
public interface DevicePurchHistoryDao {
	/**
	 * 创建服务商费率历史
	 * @param agentFeeHistory
	 */
	public void create(DevicePurchHistory devicePurchHistory);
	
	/**
	 * 根据服务商编查询
	 * @param agentNo
	 * @return
	 */
	public List<DevicePurchHistory> find(DevicePurchHistory devicePurchHistory);
}
