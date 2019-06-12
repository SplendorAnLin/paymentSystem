package com.yl.online.trade.dao.impl;

import javax.annotation.Resource;

import org.junit.Test;

import com.yl.online.trade.BaseTest;
import com.yl.online.trade.dao.PaymentDao;
import com.yl.online.trade.service.TradeOrderService;

/**
 * 支付记录数据实现测试
 * 
 * @author 央联支付有限公司
 * @since 2016年8月8日
 * @version V1.0.0
 */
public class TestPaymentDaoImplTest extends BaseTest {
	
	@Resource
	private TradeOrderService tradeOrderService;
	@Resource
	private PaymentDao paymentDao;
	
	@Test
	public void test() {
//		Order order = tradeOrderService.queryByCode("TOSA20140626000335");
//		System.out.println(order);
//		Payment payment = order.getPayments().get(1);
//		payment.setPayinterfaceRequestId("20140626009378");
//		payment.setPayinterfaceOrder("123");
//		paymentDao.updateByCode(payment, order);
	}
	
}
