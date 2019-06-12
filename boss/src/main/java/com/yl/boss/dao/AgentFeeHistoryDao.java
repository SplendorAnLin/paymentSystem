package com.yl.boss.dao;

import java.util.List;

import com.yl.boss.entity.AgentFeeHistory;

/**
 * 服务商费率历史数据访问接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月11日
 * @version V1.0.0
 */
public interface AgentFeeHistoryDao {
	
	/**
	 * 创建服务商费率历史
	 * @param agentFeeHistory
	 */
	public void create(AgentFeeHistory agentFeeHistory);
	
	/**
	 * 根据服务商编查询
	 * @param agentNo
	 * @return
	 */
	public List<AgentFeeHistory> findByAgentNo(String agentNo);

}
