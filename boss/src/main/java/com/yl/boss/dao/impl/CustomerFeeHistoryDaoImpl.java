package com.yl.boss.dao.impl;

import java.util.List;

import com.yl.boss.dao.CustomerFeeHistoryDao;
import com.yl.boss.dao.EntityDao;
import com.yl.boss.entity.CustomerFeeHistory;

/**
 * 商户费率历史数据访问接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月11日
 * @version V1.0.0
 */
public class CustomerFeeHistoryDaoImpl implements CustomerFeeHistoryDao {
	
	private EntityDao entityDao;

	@Override
	public void create(CustomerFeeHistory customerFeeHistory) {
		entityDao.persist(customerFeeHistory);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CustomerFeeHistory> findByCustNo(String custNo) {
		String hql = "from CustomerFeeHistorty where customerNo = ?";
		return entityDao.find(hql,custNo);
	}

	public EntityDao getEntityDao() {
		return entityDao;
	}

	public void setEntityDao(EntityDao entityDao) {
		this.entityDao = entityDao;
	}

}
