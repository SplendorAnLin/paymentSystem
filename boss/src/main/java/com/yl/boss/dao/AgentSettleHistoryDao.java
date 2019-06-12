package com.yl.boss.dao;

import java.util.List;
import com.lefu.commons.utils.Page;
import com.yl.boss.entity.AgentSettleHistory;

/**
 * 服务商结算卡历史数据访问接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月11日
 * @version V1.0.0
 */
public interface AgentSettleHistoryDao {
	
	/**
	 * 创建服务商结算卡历史
	 * @param agentSettleHistory
	 */
	public void create(AgentSettleHistory agentSettleHistory);
	
	/**
	 * 根据服务商编查询
	 * @param agentNo
	 * @return
	 */
	public List<AgentSettleHistory> findByAgentNo(String agentNo,Page page);

}
