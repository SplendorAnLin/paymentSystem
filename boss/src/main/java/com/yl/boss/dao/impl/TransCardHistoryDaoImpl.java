package com.yl.boss.dao.impl;

import java.util.List;
import javax.annotation.Resource;
import com.yl.boss.dao.EntityDao;
import com.yl.boss.dao.TransCardHistoryDao;
import com.yl.boss.entity.TransCardHistory;

public class TransCardHistoryDaoImpl implements TransCardHistoryDao {

	@Resource
	EntityDao entityDao;
	
	@Override
	public void addTransCardHistory(TransCardHistory transCardHistory) {
		entityDao.save(transCardHistory);
	}

	@Override
	public void updateTransCardHistory(TransCardHistory transCardHistory) {
		entityDao.update(transCardHistory);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TransCardHistory> findTransCardHistoryByCustAndAcc(String customerNo, String accountNo) {
		StringBuffer hql = new StringBuffer(" from TransCardHistory where customerNo = ? AND accountNo = ?");
		List<TransCardHistory> list = entityDao.find(hql.toString(), customerNo, accountNo);
		if (list != null && list.size() > 0) {
			return list;
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TransCardHistory> findByInterfaceRequestId(String interfaceRequestId) {
		StringBuffer hql = new StringBuffer(" from TransCardHistory where interfaceRequestId = ?");
		List<TransCardHistory> list = entityDao.find(hql.toString(), interfaceRequestId);
		if (list != null && list.size() > 0) {
			return list;
		}
		return null;
	}
}