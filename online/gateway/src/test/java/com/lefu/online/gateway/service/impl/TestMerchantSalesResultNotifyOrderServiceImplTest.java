package com.lefu.online.gateway.service.impl;

import static org.junit.Assert.fail;

import java.util.Date;

import javax.annotation.Resource;

import org.junit.Test;

import com.lefu.commons.utils.lang.DateFormatUtils;
import com.lefu.online.gateway.BaseTest;
import com.yl.online.gateway.service.MerchantSalesResultNotifyOrderService;
import com.yl.online.model.model.MerchantSalesResultNotifyOrder;

/**
 * 商户消费结果的订单测试
 * 
 * @author 聚合支付有限公司
 * @since 2016年10月13日
 * @version V1.0.0
 */
public class TestMerchantSalesResultNotifyOrderServiceImplTest extends BaseTest {

	@Resource
	private MerchantSalesResultNotifyOrderService merchantSalesResultNotifyOrderService;
	
	@Test
	public void testRecordFaildOrder() {
		fail("Not yet implemented");
	}

	@Test
	public void testQueryByOrderCode() {
		fail("Not yet implemented");
	}

	@Test
	public void testQueryBy() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdateMerchantSalesResultNotifyOrder() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdateNotifyCount() {
		MerchantSalesResultNotifyOrder merchantSalesResultNotifyOrder = merchantSalesResultNotifyOrderService.queryByOrderCode("TOSA201405082073600");
		merchantSalesResultNotifyOrder.setNotifyCount(merchantSalesResultNotifyOrder.getNotifyCount() + 1);
		merchantSalesResultNotifyOrder.setNextFireTimes(DateFormatUtils.format(new Date(), "yyyyMMddHHmmss"));
		merchantSalesResultNotifyOrderService.updateNotifyCount(merchantSalesResultNotifyOrder);
	}

}
