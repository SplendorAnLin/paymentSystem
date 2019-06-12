package com.yl.boss.dao.impl;

import java.util.List;

import com.yl.boss.api.enums.CustomerStatus;
import com.yl.boss.dao.CustomerHistoryDao;
import com.yl.boss.dao.EntityDao;
import com.yl.boss.entity.CustomerHistory;

/**
 * 商户历史数据访问接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月11日
 * @version V1.0.0
 */
public class CustomerHistoryDaoImpl implements CustomerHistoryDao {
	
	private EntityDao entityDao;

	@Override
	public void create(CustomerHistory customerHistory) {
		entityDao.persist(customerHistory);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CustomerHistory> findByCustNo(String custNo) {
		String hql = "from CustomerHistory where customerNo = ?";
		return entityDao.find(hql,custNo);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<CustomerHistory> findByCustNoAndOper(String custNo, String oper) {
		String hql = "from CustomerHistory where customerNo = ? and oper= ? ";
		return entityDao.find(hql, custNo, oper);
	}

	public EntityDao getEntityDao() {
		return entityDao;
	}

	public void setEntityDao(EntityDao entityDao) {
		this.entityDao = entityDao;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String getLastInfo(String customerNo) {
		String hql = "from CustomerHistory where customerNo = ? AND status = ?" + 
		"ORDER BY CREATE_TIME DESC LIMIT 0,1";
		List<CustomerHistory> list = entityDao.find(hql,customerNo,CustomerStatus.AUDIT_REFUSE);
		if(list != null && list.size() > 0){
			return list.get(0).getMsg();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void deleteLastInfo(String customerNo) {
		String hql = "from CustomerHistory where customerNo = ? AND STATUS = ?" + 
		"ORDER BY CREATE_TIME DESC";
		List<CustomerHistory> list = entityDao.find(hql,customerNo,CustomerStatus.AUDIT_REFUSE);
		if(list != null && list.size() > 0){
			CustomerHistory db = list.get(0);
			db.setMsg("");
			entityDao.update(db);
		}
	}
}