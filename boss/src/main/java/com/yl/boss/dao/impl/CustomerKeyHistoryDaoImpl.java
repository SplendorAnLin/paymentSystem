package com.yl.boss.dao.impl;

import java.util.List;

import com.yl.boss.dao.CustomerKeyHistoryDao;
import com.yl.boss.dao.EntityDao;
import com.yl.boss.entity.CustomerKeyHistory;

/**
 * 商户密钥历史数据访问接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月14日
 * @version V1.0.0
 */
public class CustomerKeyHistoryDaoImpl implements CustomerKeyHistoryDao {
	
	private EntityDao entityDao;

	@Override
	public void create(CustomerKeyHistory customerKeyHistory) {
		entityDao.persist(customerKeyHistory);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CustomerKeyHistory> findByCustNo(String custNo) {
		String hql = "from CustomerKeyHistory where customerNo = ?";
		return entityDao.find(hql,custNo);
	}

	public EntityDao getEntityDao() {
		return entityDao;
	}

	public void setEntityDao(EntityDao entityDao) {
		this.entityDao = entityDao;
	}

}
