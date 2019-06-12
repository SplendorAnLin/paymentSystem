package com.yl.boss.dao;

import java.util.List;

import com.yl.boss.entity.AgentDeviceOrderHistory;

/**
 * 服务商设备订单历史数据接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月11日
 * @version V1.0.0
 */
public interface AgentDeviceOrderHistoryDao {
	/**
	 * 创建服务商费率历史
	 * @param agentFeeHistory
	 */
	public void create(AgentDeviceOrderHistory agentDeviceOrderHistory);
	
	/**
	 * 根据服务商编查询
	 * @param agentNo
	 * @return
	 */
	public List<AgentDeviceOrderHistory> findByAgentNo(String agentNo);
}
