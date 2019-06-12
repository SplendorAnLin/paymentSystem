package com.yl.online.trade.task;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.yl.online.model.enums.BusinessType;
import com.yl.online.model.enums.OrderStatus;
import com.yl.online.model.model.Order;
import com.yl.online.trade.service.TradeOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yl.online.model.enums.ReverseStatus;
import com.yl.online.model.model.SalesResultReverseOrder;
import com.yl.online.trade.ExceptionMessages;
import com.yl.online.trade.service.SalesResultReverseOrderService;
import com.yl.online.trade.service.SalesTradeCompleteHandler;
import com.yl.payinterface.core.hessian.ChannelReverseHessianService;

/**
 * 自动补单
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月5日
 * @version V1.0.0
 */
public class AutoSalesResultReverseOrderJob {

	private static final Logger logger = LoggerFactory.getLogger(AutoSalesResultReverseOrderJob.class);
	
	/** 最大补单次数 */
	private static final int MAX_COUNT = 20;
	/** 一批次补单量 */
	private static final int BATCH_REVERSE_COUNT = 200;
	/** 消费结果补单业务处理 */
	@Resource
	private SalesResultReverseOrderService salesResultReverseOrderService;
	/** 交易完成处理接口 */
	@Resource
	private SalesTradeCompleteHandler salesTradeCompleteHandler;
	/** 接口查询 **/
	@Resource
	private ChannelReverseHessianService channelReverseHessianService;
	@Resource
	private TradeOrderService tradeOrderService;
	
	public void execute() {
		long startTime = System.currentTimeMillis();
		logger.info("自动补单（在线消费交易补单） 开始");
		List<SalesResultReverseOrder> salesResultReverseOrders = salesResultReverseOrderService.querySalesResultReverseOrderBy(MAX_COUNT, BATCH_REVERSE_COUNT);
		if (salesResultReverseOrders != null && salesResultReverseOrders.size() != 0) {
			Iterator<SalesResultReverseOrder> iterator = salesResultReverseOrders.iterator();
			while (iterator.hasNext()) {
				SalesResultReverseOrder salesResultReverseOrder = iterator.next();
				try {
					Order order = tradeOrderService.queryByCode(salesResultReverseOrder.getBusinessOrderID());
					if(order.getStatus() == OrderStatus.SUCCESS){
						salesResultReverseOrder.setReverseStatus(ReverseStatus.SUCC_REVERSE);
						salesResultReverseOrder.setTransStatus("SUCCESS");
						salesResultReverseOrder.setResponseCode("0000");
						salesResultReverseOrder.setResponseMessage("交易成功");
						salesResultReverseOrder.setAmount(String.valueOf(order.getAmount()));
						salesResultReverseOrderService.updateSalesResultReverseOrder(salesResultReverseOrder);
						// 更新补单表状态
						salesResultReverseOrderService.updateSalesResultReverse(salesResultReverseOrder);
						continue;
					}
					Map<String, String> params = new HashMap<>();
					params.put("businessOrderID", salesResultReverseOrder.getBusinessOrderID());
					params.put("businessType", salesResultReverseOrder.getBusinessType());
					Map<String, String> resMap = channelReverseHessianService.reverse(params);
					if(resMap != null && !"UNKNOWN".equals(resMap.get("tranStat"))){
						resMap.put("businessType", BusinessType.SAILS.name());
						salesTradeCompleteHandler.complete(resMap);
						salesResultReverseOrder.setReverseStatus(ReverseStatus.SUCC_REVERSE);
						salesResultReverseOrder.setInterfaceRequestID(resMap.get("interfaceRequestID"));
						salesResultReverseOrder.setInterfaceOrderID(resMap.get("interfaceOrderID"));
						salesResultReverseOrder.setTransStatus(resMap.get("tranStat"));
						salesResultReverseOrder.setResponseCode(resMap.get("responseCode"));
						salesResultReverseOrder.setResponseMessage(resMap.get("responseMessage"));
						salesResultReverseOrder.setAmount(resMap.get("amount"));
						salesResultReverseOrder.setInterfaceFee(resMap.get("interfaceFee"));
						salesResultReverseOrderService.updateSalesResultReverseOrder(salesResultReverseOrder);
						// 更新补单表状态
						salesResultReverseOrderService.updateSalesResultReverse(salesResultReverseOrder);
					}else{
						salesResultReverseOrderService.updateSalesResultReverseOrderCount(salesResultReverseOrder);
					}
				} catch (Exception e) {
					/*if(e.getMessage().equals(ExceptionMessages.TRADE_ORDER_AREADY_SUCCESS)){
						salesResultReverseOrderService.updateSalesResultReverseOrder(salesResultReverseOrder);
						continue;
					}*/
					// 已支付订单 - 0111
					if (e.getMessage().indexOf(ExceptionMessages.TRADE_ORDER_AREADY_SUCCESS) > 0) {
						salesResultReverseOrderService.updateSalesResultReverseOrder(salesResultReverseOrder);
						salesResultReverseOrderService.updateSalesResultReverse(salesResultReverseOrder);
						continue;
					}
					logger.error("", e);
					// 更新补单次数
					salesResultReverseOrderService.updateSalesResultReverseOrderCount(salesResultReverseOrder);
				}
			}
		}
		logger.info("自动补单（在线消费交易补单） 结束  " + (System.currentTimeMillis() - startTime)+ "ms");
	}
}