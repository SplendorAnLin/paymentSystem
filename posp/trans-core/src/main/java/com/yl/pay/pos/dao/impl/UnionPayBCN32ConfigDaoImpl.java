package com.yl.pay.pos.dao.impl;

import com.yl.pay.pos.dao.UnionPayBCN32ConfigDao;
import com.yl.pay.pos.dao.impl.helper.EntityDao;
import com.yl.pay.pos.entity.UnionPayBCN32Config;
import com.yl.pay.pos.enums.Status;

/**
 * @author haitao.liu
 */
public class UnionPayBCN32ConfigDaoImpl implements UnionPayBCN32ConfigDao {
	private EntityDao entityDao;

	public void setEntityDao(EntityDao entityDao) {
		this.entityDao = entityDao;
	}

	/*
	 * (non-Javadoc)
	 * @see com.com.yl.pay.pos.dao.UnionPayBCN32ConfigDao#findEffectiveBy(java.lang.String)
	 */
	@Override
	public UnionPayBCN32Config findEffectiveBy(String bankCustomerNo) {
		String hql = "from UnionPayBCN32Config c where c.bankCustomerNo = ? and c.status = ?";
		return entityDao.findUnique(UnionPayBCN32Config.class, hql, bankCustomerNo, Status.TRUE);
	}

}
