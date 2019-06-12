package com.yl.pay.pos.dao;

import com.yl.pay.pos.entity.PaymentFee;


/**
 * Title: 
 * Description: 
 * Copyright: Copyright (c)2011
 * Company: com.yl.pay
 * @author jun
 */

public interface PaymentFeeDao {
	
	public PaymentFee findById(Long id);
	
	public PaymentFee create(PaymentFee paymentFee);

	public PaymentFee update(PaymentFee paymentFee);
}
