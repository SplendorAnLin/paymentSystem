package com.yl.online.trade.dao.impl;

import javax.annotation.Resource;

import org.junit.Test;

import com.yl.online.trade.BaseTest;
import com.yl.online.trade.dao.PaymentDao;
import com.yl.online.trade.service.TradeOrderService;

/**
 * ֧����¼����ʵ�ֲ���
 * 
 * @author ����֧�����޹�˾
 * @since 2016��8��8��
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
