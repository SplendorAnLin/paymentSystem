package com.yl.online.trade.service.impl;

import javax.annotation.Resource;

import org.junit.Test;

import com.yl.online.model.model.Order;
import com.yl.online.trade.BaseTest;
import com.yl.online.trade.service.TradeOrderService;

/**
 * ���׶�������ʵ�ֲ���
 * 
 * @author ����֧�����޹�˾
 * @since 2016��8��8��
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
