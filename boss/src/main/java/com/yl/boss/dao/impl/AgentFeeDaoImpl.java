package com.yl.boss.dao.impl;

import java.util.List;

import com.yl.boss.api.enums.ProductType;
import com.yl.boss.dao.AgentFeeDao;
import com.yl.boss.dao.EntityDao;
import com.yl.boss.entity.AgentFee;

/**
 * 服务商费率数据访问实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月14日
 * @version V1.0.0
 */
public class AgentFeeDaoImpl implements AgentFeeDao {
	
	private EntityDao entityDao;

	@Override
	public void create(AgentFee agentFee) {
		entityDao.persist(agentFee);
	}

	@Override
	public void update(AgentFee agentFee) {
		entityDao.update(agentFee);
	}

	@SuppressWarnings("unchecked")
	@Override
	public AgentFee findBy(String agentNo, ProductType productType) {
		String hql = "from AgentFee where agentNo = ? and productType = ?";
		List<AgentFee> list = entityDao.find(hql,agentNo,productType);
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AgentFee> findByAgentNo(String agentNo) {
		String hql = "from AgentFee where agentNo = ?";
		return entityDao.find(hql,agentNo);
	}

	@Override
	public AgentFee findById(long id) {
		return entityDao.findById(AgentFee.class, id);
	}

	public EntityDao getEntityDao() {
		return entityDao;
	}

	public void setEntityDao(EntityDao entityDao) {
		this.entityDao = entityDao;
	}

	@Override
	public void delect(AgentFee agentFee) {
		entityDao.delete(agentFee);
	}
}