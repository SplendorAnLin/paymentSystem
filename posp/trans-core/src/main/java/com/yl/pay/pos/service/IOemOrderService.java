package com.yl.pay.pos.service;

import java.util.Map;

import com.yl.pay.pos.entity.Order;

/**
 * Title: 订单 服务
 * Description:  
 * Copyright: Copyright (c)2011
 * Company: com.yl.pay
 * @author haitao.liu
 */

public interface IOemOrderService {
	/**
	 * 创建OEM订单
	 * @param extendPayBean
	 * @return
	 */
	public Map<String,String> create(Order order);
	
	/**
	 * 完成OEM订单
	 * @param extendPayBean
	 * @return
	 */
	public Map<String,String> complete(Order order) ;	
	
	
}

