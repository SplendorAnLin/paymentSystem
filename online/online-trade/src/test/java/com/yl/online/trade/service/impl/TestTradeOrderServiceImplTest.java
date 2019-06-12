package com.yl.online.trade.service.impl;

import javax.annotation.Resource;

import org.junit.Test;

import com.yl.online.model.model.Order;
import com.yl.online.trade.BaseTest;
import com.yl.online.trade.service.TradeOrderService;

/**
 * 交易订单服务实现测试
 * 
 * @author 央联支付有限公司
 * @since 2016年8月8日
 * @version V1.0.0
 */
public class TestTradeOrderServiceImplTest extends BaseTest {

	@Resource
	private TradeOrderService tradeOrderService;
	@Test
	public void testModifyOrder() {//20140626009378
		Order order = tradeOrderService.queryByCode("TOSA20140626000335");
		System.out.println(order);
		tradeOrderService.modifyOrder(order);
	}

}
