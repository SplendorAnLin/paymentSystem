package com.yl.boss.dao.impl;

import java.util.List;

import com.yl.boss.dao.AgentCertDao;
import com.yl.boss.dao.EntityDao;
import com.yl.boss.entity.AgentCert;

/**
 * 服务商证件数据访问实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月14日
 * @version V1.0.0
 */
public class AgentCertDaoImpl implements AgentCertDao {
	
	private EntityDao entityDao;

	@Override
	public void create(AgentCert agentCert) {
		entityDao.persist(agentCert);
	}

	@Override
	public void update(AgentCert agentCert) {
		entityDao.update(agentCert);
	}

	@SuppressWarnings("unchecked")
	@Override
	public AgentCert findByAgentNo(String agentNo) {
		String hql = "from AgentCert where agentNo = ?";
		List<AgentCert> list = entityDao.find(hql,agentNo);
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}

	public EntityDao getEntityDao() {
		return entityDao;
	}

	public void setEntityDao(EntityDao entityDao) {
		this.entityDao = entityDao;
	}

}
