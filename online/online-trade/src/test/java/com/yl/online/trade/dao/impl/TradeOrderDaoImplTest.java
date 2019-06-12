package com.yl.online.trade.dao.impl;

import static org.junit.Assert.assertNotNull;

import javax.annotation.Resource;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yl.online.model.model.Order;
import com.yl.online.trade.BaseTest;
import com.yl.online.trade.dao.TradeOrderDao;

/**
 * 交易订单数据实现测试
 * 
 * @author 央联支付有限公司
 * @since 2016年8月8日
 * @version V1.0.0
 */
public class TradeOrderDaoImplTest extends BaseTest {
	private static final Logger logger = LoggerFactory.getLogger(TradeOrderDaoImplTest.class);
	@Resource
	private TradeOrderDao tradeOrderDao;
	
	@Test
	public void testFindByCode() {
		assertNotNull(tradeOrderDao);
		Order order = tradeOrderDao.findByCode("610884b1-6cf8-4677-bfe0-5ceac537a11e");
		assertNotNull(order);
		logger.info(order.toString());
	}

}
