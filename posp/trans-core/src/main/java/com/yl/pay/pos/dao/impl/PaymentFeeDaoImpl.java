package com.yl.pay.pos.dao.impl;

import com.yl.pay.pos.dao.PaymentFeeDao;
import com.yl.pay.pos.dao.impl.helper.EntityDao;
import com.yl.pay.pos.entity.PaymentFee;


/**
 * Title: 
 * Description: 
 * Copyright: Copyright (c)2011
 * Company: com.yl.pay
 * @author jun
 */

public class PaymentFeeDaoImpl implements PaymentFeeDao {
	private EntityDao entityDao;

	@Override
	public PaymentFee create(PaymentFee paymentFee) {
		entityDao.persist(paymentFee);
		return paymentFee;
	}

	@Override
	public PaymentFee findById(Long id) {
		return entityDao.findById(PaymentFee.class, id);
	}

	public void setEntityDao(EntityDao entityDao) {
		this.entityDao = entityDao;
	}

	@Override
	public PaymentFee update(PaymentFee paymentFee) {
		return entityDao.merge(paymentFee);
	}
	
}
