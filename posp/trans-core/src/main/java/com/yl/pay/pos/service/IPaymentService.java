package com.yl.pay.pos.service;

import java.util.List;

import com.yl.pay.pos.bean.ExtendPayBean;
import com.yl.pay.pos.entity.Order;
import com.yl.pay.pos.entity.Payment;
import com.yl.pay.pos.enums.TransType;

/**
 * Title: 订单交易 服务
 * Description:  
 * Copyright: Copyright (c)2011
 * Company: com.yl.pay
 * @author haitao.liu
 */

public interface IPaymentService {
	
	public ExtendPayBean create(ExtendPayBean extendPayBean) ;
	
	public ExtendPayBean create(ExtendPayBean extendPayBean, TransType transType);
	
	public ExtendPayBean complete(ExtendPayBean extendPayBean) ;
	
	public List<Payment> findByOrder(Order order,String currentPage);
}

