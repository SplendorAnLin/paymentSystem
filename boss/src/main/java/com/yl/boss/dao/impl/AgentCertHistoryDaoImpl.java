package com.yl.boss.dao.impl;

import java.util.List;

import com.yl.boss.dao.AgentCertHistoryDao;
import com.yl.boss.dao.EntityDao;
import com.yl.boss.entity.AgentCertHistory;

/**
 * 服务商证件历史数据访问实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月14日
 * @version V1.0.0
 */
public class AgentCertHistoryDaoImpl implements AgentCertHistoryDao {
	
	private EntityDao entityDao;

	@Override
	public void create(AgentCertHistory agentCertHistory) {
		entityDao.persist(agentCertHistory);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AgentCertHistory> findByAgentNo(String agentNo) {
		String hql = "from AgentCertHistory where agentNo = ?";
		return entityDao.find(hql,agentNo);
	}

	public EntityDao getEntityDao() {
		return entityDao;
	}

	public void setEntityDao(EntityDao entityDao) {
		this.entityDao = entityDao;
	}

}
