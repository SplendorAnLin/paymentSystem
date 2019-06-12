package com.yl.boss.dao.impl;

import java.util.List;

import com.yl.boss.dao.AgentDeviceOrderHistoryDao;
import com.yl.boss.dao.EntityDao;
import com.yl.boss.entity.AgentDeviceOrderHistory;

/**
 * 服务商设备订单历史数据接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月11日
 * @version V1.0.0
 */
public class AgentDeviceOrderHistoryDaoImpl implements AgentDeviceOrderHistoryDao{
	private EntityDao entityDao;
	
	@Override
	public void create(AgentDeviceOrderHistory agentDeviceOrderHistory) {
		entityDao.persist(agentDeviceOrderHistory);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AgentDeviceOrderHistory> findByAgentNo(String agentNo) {
		String hql = "from AgentDeviceOrderHistory where agentNo = ?";
		return entityDao.find(hql,agentNo);
	}

	public EntityDao getEntityDao() {
		return entityDao;
	}

	public void setEntityDao(EntityDao entityDao) {
		this.entityDao = entityDao;
	}

	
}
