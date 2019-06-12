package com.yl.boss.dao.impl;

import java.util.List;

import com.yl.boss.dao.CustomerSettleDao;
import com.yl.boss.dao.EntityDao;
import com.yl.boss.entity.CustomerSettle;

/**
 * 商户结算卡数据访问接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月14日
 * @version V1.0.0
 */
public class CustomerSettleDaoImpl implements CustomerSettleDao {
	
	private EntityDao entityDao;

	@Override
	public void create(CustomerSettle customerSettle) {
		entityDao.persist(customerSettle);
	}

	@Override
	public void update(CustomerSettle customerSettle) {
		entityDao.update(customerSettle);
	}

	@SuppressWarnings("unchecked")
	@Override
	public CustomerSettle findByCustNo(String custNo) {
		String hql = "from CustomerSettle where customerNo = ?";
		List<CustomerSettle> list = entityDao.find(hql,custNo);
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
