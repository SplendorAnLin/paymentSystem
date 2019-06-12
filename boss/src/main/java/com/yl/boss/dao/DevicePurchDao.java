package com.yl.boss.dao;

import com.yl.boss.entity.DevicePurch;

/**
 * 设备订单数据接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月14日
 * @version V1.0.0
 */
public interface DevicePurchDao {
	/**
	 * 新增设备订单
	 * @param device
	 */
	public void create(DevicePurch devicePurch);
	/**
	 * 更新设备订单
	 * @param device
	 */
	public void update(DevicePurch devicePurch);
	/**
	 * 根据批次号查找订单
	 * @param batchNumber
	 * @return
	 */
	public DevicePurch findDevicePurchBy(String batchNumber);
	/**
	 * 根据设备id查找设备订单
	 * @param id
	 * @return
	 */
	public DevicePurch findById(long id);
	
}
