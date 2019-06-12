package com.yl.pay.pos.dao.impl;

import com.pay.common.util.SequenceUtils;
import com.yl.pay.pos.dao.SingleAmountLimitDao;
import com.yl.pay.pos.dao.impl.helper.EntityDao;
import com.yl.pay.pos.entity.SingleAmountLimit;
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

public class SingleAmountLimitDaoImpl implements SingleAmountLimitDao {
	private EntityDao entityDao;

	@Override
	public SingleAmountLimit create(SingleAmountLimit singleAmountLimit) {
		entityDao.save(singleAmountLimit);
		String code = SequenceUtils.createSequence(singleAmountLimit.getId(), new int[] {1,6,3,3,4,8,5,6,0,5}, new int[] {2,12}, new int[] {5,9}, new int[] {3,2}, new int[] {7,9});
		singleAmountLimit.setCode(code);
		return singleAmountLimit;
	}

	@Override
	public SingleAmountLimit findByCode(String code) {
		String hql="from SingleAmountLimit s where s.code=? ";
		return entityDao.findUnique(SingleAmountLimit.class, hql, code);
	}

	@Override
	public SingleAmountLimit findById(Long id) {
		return entityDao.findById(SingleAmountLimit.class, id);
	}

	@Override
	public SingleAmountLimit update(SingleAmountLimit singleAmountLimit) {
		return entityDao.merge(singleAmountLimit);
	}

	@Override
	public List<SingleAmountLimit> findByOwnerIdAndControlRoleAndStatus(
			String ownerId, LimitAmountControlRole controlRole) {
		String hql="from SingleAmountLimit s where s.ownerId=? and s.controlRole=? and s.status=? and s.effectTime<?";
		return entityDao.find(hql, ownerId,controlRole,Status.TRUE,new Date());
	}
	@Override
	public void delete(SingleAmountLimit singleAmountLimit) {
		
		entityDao.remove(singleAmountLimit);
	}

	public EntityDao getEntityDao() {
		return entityDao;
	}

	public void setEntityDao(EntityDao entityDao) {
		this.entityDao = entityDao;
	}

	



	
	
	
}
