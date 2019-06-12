package com.yl.boss.dao.impl;

import java.util.List;

import com.yl.boss.dao.CustomerSettleHistoryDao;
import com.yl.boss.dao.EntityDao;
import com.yl.boss.entity.CustomerSettleHistory;

/**
 * 商户结算卡历史数据访问接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月14日
 * @version V1.0.0
 */
public class CustomerSettleHistoryDaoImpl implements CustomerSettleHistoryDao {
	
	private EntityDao entityDao;

	@Override
	public void create(CustomerSettleHistory customerSettleHistory) {
		entityDao.persist(customerSettleHistory);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CustomerSettleHistory> findByCustNo(String custNo) {
		String hql = "from CustomerSettleHistory where customerNo = ?";
		return entityDao.find(hql,custNo);
	}

	public EntityDao getEntityDao() {
		return entityDao;
	}

	public void setEntityDao(EntityDao entityDao) {
		this.entityDao = entityDao;
	}

}
