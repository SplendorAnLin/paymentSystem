package com.yl.boss.dao.impl;

import java.util.List;

import com.yl.boss.dao.AgentFeeHistoryDao;
import com.yl.boss.dao.EntityDao;
import com.yl.boss.entity.AgentFeeHistory;

/**
 * 服务商费率历史数据访问实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月14日
 * @version V1.0.0
 */
public class AgentFeeHistoryDaoImpl implements AgentFeeHistoryDao {
	
	private EntityDao entityDao;

	@Override
	public void create(AgentFeeHistory agentFeeHistory) {
		entityDao.persist(agentFeeHistory);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AgentFeeHistory> findByAgentNo(String agentNo) {
		String hql = "from AgentFeeHistory where agentNo = ?";
		return entityDao.find(hql,agentNo);
	}

	public EntityDao getEntityDao() {
		return entityDao;
	}

	public void setEntityDao(EntityDao entityDao) {
		this.entityDao = entityDao;
	}

}
