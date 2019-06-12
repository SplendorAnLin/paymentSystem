package com.yl.pay.pos.service;

import com.yl.pay.pos.entity.Order;

/**
 * 分润业务类
 * @author sihe.li
 *
 */
public interface ShareProfitService {

	/**
	 * 分润
	 * @param order
	 */
	public void createShareProfit(Order order);
}
