package com.yl.boss.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import com.lefu.commons.utils.Page;
import com.yl.boss.dao.AgentSettleHistoryDao;
import com.yl.boss.dao.EntityDao;
import com.yl.boss.entity.AgentSettleHistory;
import com.yl.boss.entity.Bulletin;

/**
 * 服务商结算卡历史数据访问接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月11日
 * @version V1.0.0
 */
public class AgentSettleHistoryDaoImpl implements AgentSettleHistoryDao {
	
	private EntityDao entityDao;

	@Override
	public void create(AgentSettleHistory agentSettleHistory) {
		try {
			entityDao.persist(agentSettleHistory);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AgentSettleHistory> findByAgentNo(String agentNo,Page page) {
		List<AgentSettleHistory> list=new ArrayList<AgentSettleHistory>();
		DetachedCriteria dc=DetachedCriteria.forClass(AgentSettleHistory.class);
		String hql = "from AgentSettleHistory where agentNo = ?";
		int count=entityDao.find(hql,agentNo).size();
		page.setTotalResult(count);
		dc.add(Restrictions.eq("agentNo", agentNo));
		if(page!=null){
			dc.addOrder(Order.desc("id"));
			list =entityDao.findByCriteria(dc,(page.getCurrentPage()-1)*page.getShowCount(),page.getCurrentPage()*page.getShowCount());
		}
		return list;
	}

	public EntityDao getEntityDao() {
		return entityDao;
	}

	public void setEntityDao(EntityDao entityDao) {
		this.entityDao = entityDao;
	}

}
