package com.yl.boss.dao.impl;

import java.util.List;

import com.yl.boss.dao.CustomerCertHistoryDao;
import com.yl.boss.dao.EntityDao;
import com.yl.boss.entity.CustomerCertHistory;

/**
 * 商户证件历史数据访问接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月11日
 * @version V1.0.0
 */
public class CustomerCertHistoryDaoImpl implements CustomerCertHistoryDao {
	
	private EntityDao entityDao;

	@Override
	public void create(CustomerCertHistory customerCertHistorty) {
		entityDao.persist(customerCertHistorty);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CustomerCertHistory> findByCustNo(String custNo) {
		String hql = "from CustomerCertHistory where customerNo = ?";
		return entityDao.find(hql,custNo);
	}

	public EntityDao getEntityDao() {
		return entityDao;
	}

	public void setEntityDao(EntityDao entityDao) {
		this.entityDao = entityDao;
	}

}
