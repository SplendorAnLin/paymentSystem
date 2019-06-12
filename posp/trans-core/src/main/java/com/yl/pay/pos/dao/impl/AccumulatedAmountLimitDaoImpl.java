package com.yl.pay.pos.dao.impl;

import com.pay.common.util.SequenceUtils;
import com.yl.pay.pos.dao.AccumulatedAmountLimitDao;
import com.yl.pay.pos.dao.impl.helper.EntityDao;
import com.yl.pay.pos.entity.AccumulatedAmountLimit;
import com.yl.pay.pos.enums.LimitAmountControlRole;
import com.yl.pay.pos.enums.Status;

import java.util.Date;
import java.util.List;


/**
 * Title: 
 * Description: 
 * Copyright: Copyright (c)2011
 * Company: com.yl.pay
 * @author jun
 */

public class AccumulatedAmountLimitDaoImpl implements AccumulatedAmountLimitDao {
	private EntityDao entityDao;
	
	@Override
	public AccumulatedAmountLimit create(
			AccumulatedAmountLimit accumulatedAmountLimit) {
		entityDao.save(accumulatedAmountLimit);
		String code = SequenceUtils.createSequence(accumulatedAmountLimit.getId(), new int[] {1,6,3,7,4,8,5,6,0,2}, new int[] {2,11}, new int[] {5,8}, new int[] {3,2}, new int[] {7,9});
		accumulatedAmountLimit.setCode(code);
		return accumulatedAmountLimit;
	}

	@Override
	public AccumulatedAmountLimit findByCode(String code) {
		String hql="from AccumulatedAmountLimit a where a.code=? ";
		return entityDao.findUnique(AccumulatedAmountLimit.class, hql, code);
	}

	@Override
	public AccumulatedAmountLimit findById(Long id) {
		return entityDao.findById(AccumulatedAmountLimit.class, id);
	}

	@Override
	public AccumulatedAmountLimit update(
			AccumulatedAmountLimit accumulatedAmountLimit) {
		return entityDao.merge(accumulatedAmountLimit);
	}
	
	@Override
	public List<AccumulatedAmountLimit> findByOwnerIdAndControlRoleAndStatus(
			String ownerId, LimitAmountControlRole controlRole) {
		String hql="from AccumulatedAmountLimit s where s.ownerId=? and s.controlRole=? and s.status=? and s.effectTime<?";
		return entityDao.find(hql, ownerId,controlRole,Status.TRUE,new Date());
	}

	@Override
	public void delete(AccumulatedAmountLimit accumulatedAmountLimit) {		
		entityDao.remove(accumulatedAmountLimit);
	}

	public EntityDao getEntityDao() {
		return entityDao;
	}

	public void setEntityDao(EntityDao entityDao) {
		this.entityDao = entityDao;
	}

	

	
	
	
}
