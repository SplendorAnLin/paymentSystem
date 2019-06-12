package com.yl.boss.dao;

import java.util.List;

import com.yl.boss.entity.AgentHistory;

/**
 * 服务商历史数据访问接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月11日
 * @version V1.0.0
 */
public interface AgentHistoryDao {
	
	/**
	 * 创建服务商历史
	 * @param agentHistory
	 */
	public void create(AgentHistory agentHistory);
	
	/**
	 * 根据服务商编查询
	 * @param agentNo
	 * @return
	 */
	public List<AgentHistory> findByAgentNo(String agentNo);

}
