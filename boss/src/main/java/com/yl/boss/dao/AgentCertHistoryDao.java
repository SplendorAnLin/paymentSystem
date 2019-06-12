package com.yl.boss.dao;

import java.util.List;

import com.yl.boss.entity.AgentCertHistory;

/**
 * 服务商证件历史数据访问接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月11日
 * @version V1.0.0
 */
public interface AgentCertHistoryDao {
	
	/**
	 * 创建服务商证件历史信息
	 * @param agentCertHistory
	 */
	public void create(AgentCertHistory agentCertHistory);
	
	/**
	 * 根据服务商编号查询
	 * @param agentNo
	 * @return
	 */
	public List<AgentCertHistory> findByAgentNo(String agentNo);

}
