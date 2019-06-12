package com.yl.boss.dao;

import java.util.List;

import com.yl.boss.api.bean.AgentDeviceOrderBean;
import com.yl.boss.entity.AgentDeviceOrder;

/**
 * 服务商设备采购订单接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月11日
 * @version V1.0.0
 */
public interface AgentDeviceOrderDao {
	/**
	 * 创建服务商设备采购订单
	 * @param agentDeviceOrder
	 */
	public void create(AgentDeviceOrder agentDeviceOrder);
	/**
	 * 更新服务商设备采购订单
	 * @param agentDeviceOrder
	 */
	public void update(AgentDeviceOrder agentDeviceOrder);
	/**
	 * 根据采购单ID查询
	 * @param id
	 * @return
	 */
	public AgentDeviceOrder findByPsNo(String psNo);
	/**
	 * 根据服务商编查询
	 * @param agentNo
	 * @return
	 */
	public List<AgentDeviceOrder> findByAgentNo(String agentNo);
	/**
	 * 判断是否有申请水牌的订单
	 * @return
	 */
	public int yOrNoOrder(AgentDeviceOrder agentDeviceOrder);
	
	/**
	 * 查询服务商设备订单
	 */
	AgentDeviceOrder findByOrderId(String orderId);
	
	/**
	 * 修改服务商设备订单
	 */
	void updateDeviceOrder(AgentDeviceOrder agentDeviceOrderBean);
	
}