package com.yl.boss.dao;

import com.yl.boss.entity.AgentCert;

/**
 * 服务商证件数据访问接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月11日
 * @version V1.0.0
 */
public interface AgentCertDao {
	
	/**
	 * 创建服务商证件信息
	 * @param agentCert
	 */
	public void create(AgentCert agentCert);
	
	/**
	 * 更新服务商证件信息
	 * @param agentCert
	 */
	public void update(AgentCert agentCert);
	
	/**
	 * 根据服务商编号查询
	 * @param agentNo
	 * @return
	 */
	public AgentCert findByAgentNo(String agentNo);

}
