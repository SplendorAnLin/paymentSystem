package com.yl.boss.service;

import java.util.List;

import com.yl.boss.entity.AgentCert;
import com.yl.boss.entity.AgentCertHistory;

/**
 * 服务商证件业务访问接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月13日
 * @version V1.0.0
 */
public interface AgentCertService {
	
	/**
	 * 创建服务商证件信息
	 * @param agentCert
	 * @param oper
	 */
	public void create(AgentCert agentCert, String oper);
	
	/**
	 * 更新服务商证件信息
	 * @param agentCert
	 * @param oper
	 */
	public void update(AgentCert agentCert, String oper);
	
	/**
	 * 根据服务商编号查询
	 * @param agentNo
	 * @return
	 */
	public AgentCert findByAgentNo(String agentNo);
	
	/**
	 * 根据服务商编号查询历史
	 * @param agentNo
	 * @return
	 */
	public List<AgentCertHistory> findHistByAgentNo(String agentNo);

}
