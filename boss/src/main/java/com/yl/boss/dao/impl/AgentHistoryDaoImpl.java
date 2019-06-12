package com.yl.boss.dao.impl;

import java.util.List;

import com.yl.boss.dao.AgentHistoryDao;
import com.yl.boss.dao.EntityDao;
import com.yl.boss.entity.AgentHistory;

/**
 * 服务商历史数据访问实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月14日
 * @version V1.0.0
 */
public class AgentHistoryDaoImpl implements AgentHistoryDao {
	
	private EntityDao entityDao;

	@Override
	public void create(AgentHistory agentHistory) {
		entityDao.persist(agentHistory);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AgentHistory> findByAgentNo(String agentNo) {
		String hql = "from AgentHistory where agentNo = ?";
		return entityDao.find(hql,agentNo);
	}

	public EntityDao getEntityDao() {
		return entityDao;
	}

	public void setEntityDao(EntityDao entityDao) {
		this.entityDao = entityDao;
	}

}
