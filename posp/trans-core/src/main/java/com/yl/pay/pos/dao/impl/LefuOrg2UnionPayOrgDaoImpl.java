package com.yl.pay.pos.dao.impl;

import com.yl.pay.pos.dao.LefuOrg2UnionPayOrgDao;
import com.yl.pay.pos.dao.impl.helper.EntityDao;
import com.yl.pay.pos.enums.Status;

/**
 * @author haitao.liu
 */
public class LefuOrg2UnionPayOrgDaoImpl implements LefuOrg2UnionPayOrgDao {
	private EntityDao entityDao;

	public void setEntityDao(EntityDao entityDao) {
		this.entityDao = entityDao;
	}

	/*
	 * (non-Javadoc)
	 * @see com.com.yl.pay.pos.dao.LefuOrg2UnionPayOrgDao#findUnionPayOrgCodeBy(java.lang.String)
	 */
	@Override
	public String findEffectiveUnionPayCodeBy(String lefuCode) {
		String hql = "select l.unionPayCode from LefuOrg2UnionPayOrg l where l.lefuCode = ? and l.status = ?";
		return entityDao.findUnique(String.class, hql, lefuCode, Status.TRUE);
	}

}
