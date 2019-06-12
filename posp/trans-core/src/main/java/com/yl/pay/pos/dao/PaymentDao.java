package com.yl.pay.pos.dao;

import java.util.List;

import com.yl.pay.pos.entity.Order;
import com.yl.pay.pos.entity.Payment;
import com.yl.pay.pos.enums.TransStatus;
import com.yl.pay.pos.enums.TransType;

/**
 * Title: Payment Dao
 * Description: 
 * Copyright: Copyright (c)2011
 * Company: com.yl.pay
 * @author haitao.liu
 */
public interface PaymentDao {

	//根据ID查询
	public Payment findById(Long id);
	//根据订单ID查询
	public List<Payment> findByOrder(Order order,String currentPage); 

	//创建
	public Payment create(Payment payment);
	
	//更新
	public Payment update(Payment payment);	
	
	//根据订单、状态、交易类型查找
	public Payment findSourcePayment(Order order, TransStatus status, TransType transType);

	//根据POS终端号、批次号、流水号、交易类型查找
	public Payment findSourcePayment(String posCati, String posBatch, String posRequestId, TransType transType);

	/**
	 * 根据订单、状态、交易类型查找
	 * @author haitao.liu
	 */
	public Payment findLastSourcePayment(Order order, TransStatus status, TransType transType);

	/**根据订单、状态、交易类型查找
	 * @param order
	 * @param success
	 * @param preAuthCompVoid
	 * @param preAuthCompReversal
	 * @return
	 */
	public Payment findLastSourcePayment(Order order, TransStatus status,
                                         TransType transType1, TransType transType2);

}
