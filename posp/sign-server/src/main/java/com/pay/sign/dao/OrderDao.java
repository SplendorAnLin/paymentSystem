package com.pay.sign.dao;

import com.pay.sign.dbentity.Order;


public interface OrderDao {

	/**
	 * 根据订单号，获取一个订单
	 * @param orderNo
	 * @return
	 */
	public Order getOrder(String orderNo,int limit);

	/**
	 * 根据店铺ID，获取对应的店铺号
	 * @param shopId
	 * @return
	 */
	public String getShopNO(Long shopId);
	
}