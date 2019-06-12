package com.yl.boss.dao.impl;

import java.util.List;

import com.yl.boss.dao.AgentSettleDao;
import com.yl.boss.dao.EntityDao;
import com.yl.boss.entity.AgentSettle;

/**
 * 服务商结算卡数据访问接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月11日
 * @version V1.0.0
 */
public class AgentSettleDaoImpl implements AgentSettleDao {
	
	private EntityDao entityDao;

	@Override
	public void create(AgentSettle agentSettle) {
		entityDao.persist(agentSettle);
	}

	@Override
	public void update(AgentSettle agentSettle) {
		entityDao.update(agentSettle);
	}

	@SuppressWarnings("unchecked")
	@Override
	public AgentSettle findByAgentNo(String agentNo) {
		String hql = "from AgentSettle where agentNo = ?";
		List<AgentSettle> list = entityDao.find(hql,agentNo);
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<AgentSettle> findListByAgentNo(String agentNo) {
		String hql = "from AgentSettle where agentNo = ?";
		List<AgentSettle> list = entityDao.find(hql,agentNo);
		return list;
	}

	public EntityDao getEntityDao() {
		return entityDao;
	}

	public void setEntityDao(EntityDao entityDao) {
		this.entityDao = entityDao;
	}

}
