package com.yl.online.gateway.task;

import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.annotation.Resource;

import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lefu.commons.utils.lang.DateFormatUtils;
import com.lefu.commons.utils.lang.JsonUtils;
import com.lefu.commons.utils.lang.StringUtils;
import com.lefu.commons.utils.web.HttpClientUtils;
import com.lefu.commons.utils.web.HttpClientUtils.Method;
import com.yl.online.gateway.ExceptionMessages;
import com.yl.online.gateway.exception.BusinessRuntimeException;
import com.yl.online.gateway.quartz.context.QuartzContext;
import com.yl.online.gateway.quartz.enums.QuartzStrategyType;
import com.yl.online.gateway.quartz.model.VectorConstant;
import com.yl.online.gateway.service.MerchantSalesResultNotifyOrderService;
import com.yl.online.gateway.service.PartnerRequestService;
import com.yl.online.gateway.service.TradeHandler;
import com.yl.online.model.enums.NotifyStatus;
import com.yl.online.model.model.MerchantSalesResultNotifyOrder;
import com.yl.online.model.model.Order;
import com.yl.online.model.model.PartnerRequest;
import com.yl.online.trade.hessian.OnlineTradeQueryHessianService;

/**
 * 自动补单（商户消费结果通知定时器）
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月17日
 * @version V1.0.0
 */
public class MerchantSalesResultNotifyJob {

	private Logger logger = LoggerFactory.getLogger(MerchantSalesResultNotifyJob.class);
	/** 最大补单次数 */
	private static final int MAX_COUNT = 10;
	/** 一批次补单量 */
	private static final int BATCH_REVERSE_COUNT = 200;
	/** 线程池控制并发 */
	private static Executor executor = Executors.newFixedThreadPool(10);
	/** 网关交易处理接口 */
	@Resource
	private TradeHandler salesTradeHandler;
	/** 合作方请求信息业务 */
	@Resource
	private PartnerRequestService partnerRequestService;
	/** 交易查询系统业务处理*/
	@Resource
	private OnlineTradeQueryHessianService onlineTradeQueryHessianService;
	/** 商户消费结果通知订单业务处理 */
	@Resource
	private MerchantSalesResultNotifyOrderService merchantSalesResultNotifyOrderService;
	/** 定时策略上下文 */
	@Resource
	private QuartzContext quartzContext;

	public void execute() {
		logger.info("自动补单（商户消费结果通知定时器）");
		List<MerchantSalesResultNotifyOrder> merchantSalesResultNotifyOrders = merchantSalesResultNotifyOrderService.queryBy(MAX_COUNT, BATCH_REVERSE_COUNT);
		for (int idx = 0; idx < merchantSalesResultNotifyOrders.size(); idx++) {
			MerchantReverseTask task = new MerchantReverseTask();
			task.setMerchantSalesResultNotifyOrder(merchantSalesResultNotifyOrders.get(idx));
			executor.execute(task);
		}
	}

	private class MerchantReverseTask implements Runnable {
		/** 私有数据 */
		private MerchantSalesResultNotifyOrder merchantSalesResultNotifyOrder;

		@Override
		public void run() {
			// 通知结果
			String notifyResult = "FAILED";
			String outOrderCode = "";
			String notifyUrl = null;
			// 当前时间是否匹配
			try {
				Order order = merchantSalesResultNotifyOrder.parsetToOrderBean();
				notifyUrl = order.getNotifyURL();
				// 查询交易订单
				order = JsonUtils.toObject(JsonUtils.toJsonString(onlineTradeQueryHessianService.findOrderByCode(order.getCode())), new TypeReference<Order>() {});
				if (order == null) throw new BusinessRuntimeException(ExceptionMessages.TRADE_ORDER_NOT_EXISTS);
				order.setNotifyURL(notifyUrl);
				PartnerRequest partnerRequest = partnerRequestService.queryPartnerRequestByOutOrderId(order.getRequestCode(),order.getReceiver());
				if (StringUtils.isNotBlank(order.getNotifyURL())) {
					String handlerResultMsg = salesTradeHandler.createResponse(order, partnerRequest, false);
					logger.info("网关【定时器】通知合作方支付结果：{}", handlerResultMsg);
					// 通知商户交易结果
					notifyResult = HttpClientUtils.send(Method.POST, handlerResultMsg.split("\\?")[0], handlerResultMsg.split("\\?")[1], true, "UTF-8", 3000);
					outOrderCode = order.getRequestCode();
					logger.info("【商户订单号：{}，合作方回传处理结果：{}】", order.getRequestCode(), notifyResult);
					if(notifyResult.toUpperCase().startsWith("SUCCESS")){
						merchantSalesResultNotifyOrder.setNotifyResult(NotifyStatus.SUCCESS);
						merchantSalesResultNotifyOrderService.updateMerchantSalesResultNotifyOrder(merchantSalesResultNotifyOrder);
					}else{
						merchantSalesResultNotifyOrder.setNotifyCount(merchantSalesResultNotifyOrder.getNotifyCount() + 1);
						VectorConstant vectorConstant = new VectorConstant();
						vectorConstant.setVector(merchantSalesResultNotifyOrder.getNotifyCount());
						vectorConstant.setBaseLine(2);
						Date nextFireTime = quartzContext.getNextFireTime(QuartzStrategyType.QuartzTriggerIncrease, vectorConstant);
						merchantSalesResultNotifyOrder.setNextFireTimes(DateFormatUtils.format(nextFireTime, "yyyy-MM-dd HH:mm:ss")); // 记录补单时间线
						merchantSalesResultNotifyOrder.setNextFireTime(nextFireTime); // 下次触发时间
						merchantSalesResultNotifyOrder.setNotifyCount(merchantSalesResultNotifyOrder.getNotifyCount()+1);
						merchantSalesResultNotifyOrder.setNotifyResult(NotifyStatus.FAILED);
						merchantSalesResultNotifyOrderService.updateNotifyCount(merchantSalesResultNotifyOrder);
					}
				}
			} catch (Exception e) {
				logger.error("【商户订单号：{}，通知失败：{}】", outOrderCode, e);
				merchantSalesResultNotifyOrder.setNotifyCount(merchantSalesResultNotifyOrder.getNotifyCount() + 1);
				VectorConstant vectorConstant = new VectorConstant();
				vectorConstant.setVector(merchantSalesResultNotifyOrder.getNotifyCount());
				vectorConstant.setBaseLine(2);
				Date nextFireTime = quartzContext.getNextFireTime(QuartzStrategyType.QuartzTriggerIncrease, vectorConstant);
				merchantSalesResultNotifyOrder.setNextFireTimes(DateFormatUtils.format(nextFireTime, "yyyy-MM-dd HH:mm:ss")); // 记录补单时间线
				merchantSalesResultNotifyOrder.setNextFireTime(nextFireTime); // 下次触发时间
				merchantSalesResultNotifyOrder.setNotifyCount(merchantSalesResultNotifyOrder.getNotifyCount()+1);
				// 更新次数
				merchantSalesResultNotifyOrderService.updateNotifyCount(merchantSalesResultNotifyOrder);
			}
		}

		public void setMerchantSalesResultNotifyOrder(MerchantSalesResultNotifyOrder merchantSalesResultNotifyOrder) {
			this.merchantSalesResultNotifyOrder = merchantSalesResultNotifyOrder;
		}

	}

}
