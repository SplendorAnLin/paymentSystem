package com.yl.boss.service;

import java.util.List;
import java.util.Map;

import com.yl.boss.entity.AgentDeviceOrder;
import com.yl.boss.entity.Device;
import com.yl.boss.entity.DevicePurch;
import com.yl.boss.entity.DeviceType;

/**
 * 设备业务接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月13日
 * @version V1.0.0
 */
public interface DeviceService {
	
	
	/**
	 * 查询服务商设备订单
	 */
	AgentDeviceOrder findByOrderId(String orderId);
	
	/**
	 * 修改服务商设备订单
	 */
	void updateDeviceOrder(AgentDeviceOrder agentDeviceOrderBean);
	
	/**
	 * 根据状态查看
	 */
	 public Map findDecIvestate(Map<String, Object> params);
     
	/**
	 * 创建服务商设备采购订单
	 * @param agentDeviceOrder
	 */
	public void createAgentOrder(AgentDeviceOrder agentDeviceOrder,String oper);
	/**
	 * 判断是否有申请水牌的订单
	 * @return
	 */
	public int yOrNoOrder(AgentDeviceOrder agentDeviceOrder);
	/**
	 * 创建设备采购订单，并生成相应数量的设备
	 * @param devicePurch
	 * @param oper
	 */
	public void createDevicePurch(DevicePurch devicePurch,String oper);
	/**
	 * 更新生产设备订单
	 * @param devicePurch
	 * @param oper
	 */
	public void updateDevicePurch(DevicePurch devicePurch,String oper);
	/**
	 * 根据批次号查找设备采购订单
	 * @param batchNumber
	 * @return
	 */
	public DevicePurch findDevicePurchBy(String batchNumber);
	/**
	 * 更新服务商设备采购订单
	 * @param agentDeviceOrder
	 */
	public void updateAgentOrder(AgentDeviceOrder agentDeviceOrder, String oper);
	/**
	 * 根据采购单ID查询
	 * @param id
	 * @return
	 */
	public AgentDeviceOrder findByAgentOrder(String psNo);
	/**
	 * 根据采购流水号查询
	 * @param purchaseSerialNumber
	 * @return
	 */
	public List<Device> findDeviceByPsn(String purchaseSerialNumber);
	/**
	 * 设备类型
	 * @return
	 */
	public List<DeviceType> getDeviceType();
	/**
	 * 根据支付编号获取设备信息
	 * @param cardNo 商户支付编号
	 * @return
	 */
	public Device getDevice(String cardNo);
	/**
	 * 检查设备是否存在
	 * @param cardNo
	 * @param checkCode
	 * @return
	 */
	public Device checkDevice(String cardNo,String checkCode);
	/**
	 * 更新设备信息
	 * @param device
	 */
	public void upDevice(Device device);
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
	 * 查找设备类型
	 * @param id
	 * @return
	 */
	DeviceType findDeviceType(Long id);
	/**
	 * 商户号查找设备
	 * @param custNo
	 * @return
	 */
	List<Device> findDeviceByCustNo(String custNo);
	/**
	 * 返回二维码json，包含类型type，设备编号No，二维码data
	 * @param customerPayNo
	 * @return
	 */
	String getQRcode(String customerPayNo);
	/**
	 * 分配设备号
	 * @param quantity 数量
	 * @param deviceTypeId 设备类型
	 * @param purchaseSerialNumber 批次号
	 * @param No 服务商IDOR商户ID
	 * @param isAgent 是否服务商
	 */
	boolean allotDevice(Integer quantity,Long deviceTypeId,String purchaseSerialNumber,String No,boolean isAgent);
	/**
	 * 根据商户号查询设备
	 * @param customerNo
	 * @return
	 */
	Device findByCustomerNo(String customerNo);
	/**
	 * 查询父类编号为0的设备
	 */
	List<DeviceType> byParentId();
	/**
	 * 修改父类设备
	 */
	void updateParent(DeviceType deviceType);
	
	/**
	 * 获取配置密钥
	 */
	String getConfigKey();
	
	/**
	 * 随机获取一个水牌
	 */
	Device findDevice();
}