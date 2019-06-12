package com.yl.pay.pos.dao.impl;

import com.yl.pay.pos.dao.PaymentDao;
import com.yl.pay.pos.dao.impl.helper.EntityDao;
import com.yl.pay.pos.entity.Order;
import com.yl.pay.pos.entity.Payment;
import com.yl.pay.pos.enums.TransStatus;
import com.yl.pay.pos.enums.TransType;
import com.yl.pay.pos.exception.TransExceptionConstant;
import com.yl.pay.pos.exception.TransRunTimeException;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

/**
 * Title: PaymentDaoImpl
 * Description: 
 * Copyright: Copyright (c)2011
 * Company: com.yl.pay
 * @author haitao.liu
 */
public class PaymentDaoImpl implements PaymentDao{

	public EntityDao entityDao;
	
	public Payment create(Payment payment) {
		if(payment==null){
			throw new TransRunTimeException(TransExceptionConstant.PAYMENT_IS_NULL);
		}
		entityDao.persist(payment);
		return payment;
	}

	public Payment findById(Long id) {
		return entityDao.findById(Payment.class, id);
	}

	public Payment update(Payment payment) {
		if(payment==null){
			throw new TransRunTimeException(TransExceptionConstant.PAYMENT_IS_NULL);
		}
		return entityDao.merge(payment);
	}
	
	@Override
	public Payment findSourcePayment(Order order,TransStatus status, TransType transType) {
		String hql = "from Payment p where p.order = ? and p.status = ? and p.transType = ?";
		
		return entityDao.findUnique(Payment.class,hql,order,status,transType);
	}

	@Override
	public Payment findSourcePayment(String posCati, String posBatch,String posRequestId, TransType transType) {
		String hql="from Payment p where p.posCati=? and p.posBatch=? and p.posRequestId=? and p.transType=? and status=? ";
		return entityDao.findUnique(Payment.class, hql, posCati,posBatch,posRequestId,transType,TransStatus.SUCCESS);
	}
	

	/* (non-Javadoc)
	 * @see com.com.yl.pay.pos.dao.PaymentDao#findLastSourcePayment(com.com.yl.pay.pos.entity.Order, com.com.yl.pay.pos.enums.TransStatus, com.com.yl.pay.pos.enums.TransType)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Payment findLastSourcePayment(Order order, TransStatus status, TransType transType) {
		String hql = "from Payment p where p.order = ? and p.status = ? and p.transType = ? order by p.createTime desc";
		List<Payment> payments = entityDao.find(hql,order,status,transType);
		if(payments == null || payments.isEmpty()){
			return null;
		}else{
			return payments.get(0);
		}
	}
	@Override
	public Payment findLastSourcePayment(Order order, TransStatus status,
			TransType transType1, TransType transType2) {
		String hql = "from Payment p where p.order = ? and p.status = ? and  (p.transType = ? or p.transType=?)order by p.createTime desc";
		List<Payment> payments = entityDao.find(hql,order,status,transType1,transType2);
		if(payments == null || payments.isEmpty()){
			return null;
		}else{
			return payments.get(0);
		}
	}
	public void setEntityDao(EntityDao entityDao) {
		this.entityDao = entityDao;
	}

	@Override
	public List<Payment> findByOrder(Order order,String currentPage) {
		String hql = "from Payment p where p.order = ? order by p.createTime desc ";
		return entityDao.find(hql,order);
	}

	
	
	
}
