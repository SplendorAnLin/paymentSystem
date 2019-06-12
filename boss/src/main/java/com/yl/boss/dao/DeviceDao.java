package com.yl.boss.dao;

import java.util.List;
import java.util.Map;

import com.yl.boss.entity.Device;

/**
 * 设备数据接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月14日
 * @version V1.0.0
 */
public interface DeviceDao {
	/**
	 * 根据状态查看
	 */
	 public Map findDecIvestate(Map<String, Object> params);
	/**
	 * 新增设备
	 * @param device
	 */
	public void create(Device device);
	/**
	 * 更新设备
	 * @param device
	 */
	public void update(Device device);
	/**
	 * 根据设备id查找设备
	 * @param id
	 * @return
	 */
	public Device findById(long id);
	/**
	 * 根据支付编号返回设备信息
	 * @param cardNo
	 * @return
	 */
	public Device findByPayId(String cardNo);
	/**
	 * 根据商户号查找设备集合
	 * @param cardNo
	 * @return
	 */
	public List<Device> findByCardNo(String cardNo);
	/**
	 * 按条件查找设备
	 * @param device
	 * @return
	 */
	public List<Device> findByBatchNo(String batchNo);
	/**
	 * 根据采购流水号查询
	 * @param purchaseSerialNumber
	 * @return
	 */
	public List<Device> findDeviceByPsn(String purchaseSerialNumber);
	/**
	 * 查找待分配数量的设备
	 * @param size
	 * @return
	 */
	public List<Device> findDeviceBySize(int size,Long type);
	/**
	 * 获取最大设备编号
	 * @return
	 */
	public String getMaxDeviceNo();
	
	/**
	 * 获取配置密钥
	 */
	String getConfigKey();
	
	/**
	 * 随机获取一个水牌
	 */
	Device findDevice();
}