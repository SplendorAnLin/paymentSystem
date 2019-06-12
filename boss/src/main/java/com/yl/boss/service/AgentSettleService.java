package com.yl.boss.service;

import java.util.List;
import com.lefu.commons.utils.Page;
import com.yl.boss.entity.AgentSettle;
import com.yl.boss.entity.AgentSettleHistory;

/**
 * 服务商结算卡业务访问接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月13日
 * @version V1.0.0
 */
public interface AgentSettleService {
	
	/**
	 * 创建服务商结算卡
	 * @param agentSettle
	 * @param oper
	 */
	public void create(AgentSettle agentSettle, String oper);
	
	/**
	 * 更新服务商结算卡
	 * @param agentSettle
	 * @param oper
	 */
	public void update(AgentSettle agentSettle, String oper);
	
	/**
	 * 根据服务商编查询
	 * @param agentNo
	 * @return
	 */
	public AgentSettle findByAgentNo(String agentNo);
	
	/**
	 * 根据服务商编号查询历史
	 * @param agentNo
	 * @return
	 */
	public List<AgentSettleHistory> findHistByAgentNo(String agentNo,Page page);
	
	/**
	 * 
	 * @Description 查询结算卡信息
	 * @param agentNo
	 * @return
	 * @date 2016年10月29日
	 */
	public List<AgentSettle> findListByAgentNo(String agentNo);

}
