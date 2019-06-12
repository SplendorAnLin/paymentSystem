package com.yl.boss.dao;

import java.util.List;

import com.yl.boss.entity.AgentSettle;

/**
 * 服务商结算卡数据访问接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月11日
 * @version V1.0.0
 */
public interface AgentSettleDao {

	/**
	 * 创建服务商结算卡
	 * 
	 * @param agentSettle
	 */
	public void create(AgentSettle agentSettle);

	/**
	 * 更新服务商结算卡
	 * 
	 * @param agentSettle
	 */
	public void update(AgentSettle agentSettle);

	/**
	 * 根据服务商编查询
	 * 
	 * @param agentNo
	 * @return
	 */
	public AgentSettle findByAgentNo(String agentNo);

	/**
	 * 
	 * @Description 根据服务商编号，查询服务商结算卡信息
	 * @param agentNo
	 * @return
	 * @date 2016年10月29日
	 */
	public List<AgentSettle> findListByAgentNo(String agentNo);

}
