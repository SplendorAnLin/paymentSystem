package com.yl.pay.pos.dao.impl;

import com.yl.pay.pos.dao.BankReversalTaskDao;
import com.yl.pay.pos.dao.impl.helper.EntityDao;
import com.yl.pay.pos.entity.BankReversalTask;
import com.yl.pay.pos.enums.BankReversalTaskStatus;
import com.yl.pay.pos.enums.YesNo;

import java.util.List;

/**
 * Title: 
 * Description: 
 * Copyright: Copyright (c)2012
 * Company: com.yl.pay
 * @author jun
 */

public class BankReversalTaskDaoImpl implements BankReversalTaskDao{
	private EntityDao entityDao;
	
	@Override
	public BankReversalTask create(BankReversalTask bankReversalTask) {
		entityDao.persist(bankReversalTask);
		return bankReversalTask;
	}

	@Override
	public BankReversalTask update(BankReversalTask bankReversalTask) {
		return entityDao.merge(bankReversalTask);
	}

	@Override
	public BankReversalTask findById(Long id) {
		return entityDao.findById(BankReversalTask.class, id);
	}

	@Override
	public List<BankReversalTask> findByClaimAndStatus(YesNo claim,
			BankReversalTaskStatus status) {
		String hql="from BankReversalTask t where t.claim=? and t.status=? ";
		return entityDao.find(hql, claim,status);
	}

	public EntityDao getEntityDao() {
		return entityDao;
	}

	public void setEntityDao(EntityDao entityDao) {
		this.entityDao = entityDao;
	}
	
}
