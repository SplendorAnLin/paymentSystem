package com.yl.pay.pos.service;

import com.yl.pay.pos.entity.Order;
import com.yl.pay.pos.entity.ProxyPayInfo;

/**
 * 账号业务类
 * @author sihe.li
 *
 */
public interface AccountBussinessService {

	/**
	 * 订单成功后入账
	 * @param order
	 */
	public void specialCompositeTally(Order order);
	
	/**
	 * 代付前入账
	 * @param order
	 */
	public void specialCompositeTally(ProxyPayInfo proxyPayInfo);
}
