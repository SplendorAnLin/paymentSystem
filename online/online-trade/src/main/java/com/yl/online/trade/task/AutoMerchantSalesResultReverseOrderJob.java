package com.yl.online.trade.task;

import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lefu.commons.web.spring.ApplicationContextUtils;
import com.yl.online.gateway.hessian.SalesNotifyHessianService;
import com.yl.online.model.enums.ReverseStatus;
import com.yl.online.model.model.MerchantSalesResultReverseOrder;
import com.yl.online.trade.service.MerchantSalesResultReverseOrderService;

/**
 * 自动补单-通知网关
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月5日
 * @version V1.0.0
 */
public class AutoMerchantSalesResultReverseOrderJob {

	private static final Logger logger = LoggerFactory.getLogger(AutoMerchantSalesResultReverseOrderJob.class);
	
	/** 最大补单次数 */
	private static final int MAX_COUNT = 20;
	/** 一批次补单量 */
	private static final int BATCH_REVERSE_COUNT = 200;
	/** 商户消费结果补单业务处理 */
	private MerchantSalesResultReverseOrderService merchantSalesResultReverseOrderService = (MerchantSalesResultReverseOrderService) ApplicationContextUtils.getApplicationContext().getBean("merchantSalesResultReverseOrderService");
	/** 通知网关业务处理接口 */
	private SalesNotifyHessianService salesNotifyHessianService = (SalesNotifyHessianService) ApplicationContextUtils.getApplicationContext().getBean("salesNotifyHessianService");
	
	public void execute() {
		if (logger.isDebugEnabled()) logger.debug("自动补单（商户消费结果消息补单）");
		List<MerchantSalesResultReverseOrder> merchantSalesResultReverseOrders = merchantSalesResultReverseOrderService.queryBy(MAX_COUNT, BATCH_REVERSE_COUNT);
		if (merchantSalesResultReverseOrders != null && merchantSalesResultReverseOrders.size() != 0) {
			Iterator<MerchantSalesResultReverseOrder> iterator = merchantSalesResultReverseOrders.iterator();
			while (iterator.hasNext()) {
				MerchantSalesResultReverseOrder merchantSalesResultReverseOrder = iterator.next();
				logger.info("交易系统-网银支付结果通知网关自动补单数据：{} ", merchantSalesResultReverseOrder);
				try {
					salesNotifyHessianService.notify(merchantSalesResultReverseOrder.getOrderCode(), String.valueOf(merchantSalesResultReverseOrder.getStatus()), merchantSalesResultReverseOrder.getRequestCode(), 
							merchantSalesResultReverseOrder.getReceiver());
					merchantSalesResultReverseOrder.setReverseStatus(ReverseStatus.SUCC_REVERSE);
					merchantSalesResultReverseOrderService.updateMerchantSalesResultReverseOrder(merchantSalesResultReverseOrder);
				} catch (Exception e) {
					logger.error("", e);
					// 更新补单次数
					merchantSalesResultReverseOrderService.updateMerchantSalesResultReverseOrderCount(merchantSalesResultReverseOrder);
				}
			}
		}
	}

}
