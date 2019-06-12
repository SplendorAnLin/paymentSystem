package com.pay.sign.dbservice;

import com.pay.sign.dbentity.Order;


public interface IOrderDBService {
	
	/**
	 * 根据订单号，获取一个订单
	 * @param orderNo
	 * @return 如果没有，返回null
	 */
	public Order getOrder(String orderNo);
	/**
	 * 根据店铺ID，获取对应的店铺号
	 * @param shopId
	 * @return 如果没有，返回null
	 */
	public String getShopNO(Long shopId);
}
