package com.yl.boss.dao;

import java.util.List;

import com.yl.boss.entity.DeviceType;

/**
 * 设备类型数据接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月14日
 * @version V1.0.0
 */
public interface DeviceTypeDao {
	/**
	 * 设备类型列表
	 * @return
	 */
	List<DeviceType> find();
	/**
	 *查找设备类型
	 * @param id
	 * @return
	 */
	DeviceType findById(Long id);
	/**
	 * 创建设备类型
	 * @param deviceType
	 */
	void create(DeviceType deviceType);
	/**
	 * 更新设备类型
	 * @param deviceType
	 */
	void update(DeviceType deviceType);
	/**
	 * 查询父类编号为0的设备
	 */
	List<DeviceType> byParentId();
	/**
	 * 修改父类设备
	 */
	void updateParent(DeviceType deviceType);
}
