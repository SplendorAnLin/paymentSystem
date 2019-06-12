package com.yl.online.trade.task;

import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lefu.commons.web.spring.ApplicationContextUtils;
import com.yl.online.model.model.Order;
import com.yl.online.trade.service.TradeOrderService;

/**
 * 交易订单超时关闭定时处理
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月5日
 * @version V1.0.0
 */
public class CloseTimeoutOrderJob {
	
	private Logger logger = LoggerFactory.getLogger(CloseTimeoutOrderJob.class);
	/** 一批次闭单量 */
	private static final int BATCH_CLOSE_COUNT = 500;
	/** 交易订单业务处理 */
	private TradeOrderService tradeOrderService = (TradeOrderService) ApplicationContextUtils.getApplicationContext().getBean("tradeOrderService");
			
	public void execute() {
		if (logger.isDebugEnabled()) logger.debug("交易订单超时关闭定时处理...");
		List<Order> orders = tradeOrderService.queryWaitCloseOrderBy(BATCH_CLOSE_COUNT);
		if (orders != null && orders.size() != 0) {
			Iterator<Order> iterator = orders.iterator();
			while (iterator.hasNext()) {
				tradeOrderService.closeTimeoutOrder(iterator.next());
			}
		}
	}

}
