package com.yl.online.gateway.remote.hessian.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import com.lefu.commons.utils.lang.DateUtils;
import com.yl.online.gateway.utils.NotifyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.lefu.commons.utils.lang.DateFormatUtils;
import com.lefu.commons.utils.lang.StringUtils;
import com.lefu.commons.utils.web.HttpClientUtils;
import com.lefu.commons.utils.web.HttpClientUtils.Method;
import com.yl.online.gateway.hessian.SalesNotifyHessianService;
import com.yl.online.gateway.quartz.context.QuartzContext;
import com.yl.online.gateway.quartz.enums.QuartzStrategyType;
import com.yl.online.gateway.quartz.model.VectorConstant;
import com.yl.online.gateway.service.MerchantSalesResultNotifyOrderService;
import com.yl.online.gateway.service.PartnerRequestService;
import com.yl.online.gateway.service.TradeHandler;
import com.yl.online.model.enums.NotifyStatus;
import com.yl.online.model.enums.OrderStatus;
import com.yl.online.model.model.MerchantSalesResultNotifyOrder;
import com.yl.online.model.model.Order;
import com.yl.online.model.model.PartnerRequest;

/**
 * 网关商户通知远程接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年10月17日
 * @version V1.0.0
 */
@Service("salesNotifyHessianService")
public class SalesNotifyHessianServiceImpl implements SalesNotifyHessianService{
	
	private Logger logger = LoggerFactory.getLogger(SalesNotifyHessianServiceImpl.class);
	@Resource
	private MerchantSalesResultNotifyOrderService merchantSalesResultNotifyOrderService;
	@Resource
	private PartnerRequestService partnerRequestService;
	@Resource
	private TradeHandler salesTradeHandler;
	@Resource
	private QuartzContext quartzContext;

	@Override
	public void notify(String orderCode, String orderStatus, String requestCode, String receiver) {
		PartnerRequest partnerRequest = partnerRequestService.queryPartnerRequestByOutOrderId(requestCode, receiver);
		if(partnerRequest != null && StringUtils.notBlank(partnerRequest.getNotifyUrl())){
			MerchantSalesResultNotifyOrder merchantSalesResultNotifyOrder;
			merchantSalesResultNotifyOrder = merchantSalesResultNotifyOrderService.queryByOrderCode(orderCode);
			if(merchantSalesResultNotifyOrder == null){
				merchantSalesResultNotifyOrder = new MerchantSalesResultNotifyOrder();
				merchantSalesResultNotifyOrder.setOrderCode(orderCode);
				merchantSalesResultNotifyOrder.setOrderStatus(OrderStatus.valueOf(orderStatus));
				merchantSalesResultNotifyOrder.setRequestCode(requestCode);
				merchantSalesResultNotifyOrder.setNotifyCount(0);
				merchantSalesResultNotifyOrder.setNotifyURL(partnerRequest.getNotifyUrl());
				merchantSalesResultNotifyOrder.setNotifyResult(NotifyStatus.FAILED);
				merchantSalesResultNotifyOrder.setNextFireTime(new Date());
				merchantSalesResultNotifyOrderService.recordFaildOrder(merchantSalesResultNotifyOrder);
			}
			
			Order order = merchantSalesResultNotifyOrder.parsetToOrderBean();
			try {
				String handlerResultMsg = salesTradeHandler.createResponse(order, partnerRequest, false);
				logger.info("网关通知合作方支付结果：{}", handlerResultMsg);
				// 通知商户交易结果
				String notifyResult = "FAILED";
				notifyResult = HttpClientUtils.send(Method.POST, handlerResultMsg.split("\\?")[0], handlerResultMsg.split("\\?")[1], true, "UTF-8", 10000);
				logger.info("网关通知【商户编号：{}，商户订单号：{}，合作方回传处理结果：{}】", receiver, order.getRequestCode(), notifyResult);
				if(notifyResult.toUpperCase().startsWith("SUCCESS")){
					merchantSalesResultNotifyOrder.setNotifyResult(NotifyStatus.SUCCESS);
				} else {
					merchantSalesResultNotifyOrder.setNotifyResult(NotifyStatus.FAILED);
				}
                if (receiver.equals("C100048") || receiver.equals("C100058") || receiver.equals("C100059")) {
                    NotifyUtils.send(order, partnerRequest);
                }
				merchantSalesResultNotifyOrderService.updateMerchantSalesResultNotifyOrder(merchantSalesResultNotifyOrder);
			} catch (Exception e) {
				logger.error("【商户编号：{}，商户订单号：{}，通知失败：{}】", receiver, order.getRequestCode(),e);
				merchantSalesResultNotifyOrder.setNotifyCount(merchantSalesResultNotifyOrder.getNotifyCount() + 1);
				VectorConstant vectorConstant = new VectorConstant();
				vectorConstant.setVector(merchantSalesResultNotifyOrder.getNotifyCount());
				vectorConstant.setBaseLine(2);
				Date nextFireTime = quartzContext.getNextFireTime(QuartzStrategyType.QuartzTriggerIncrease, vectorConstant);
				merchantSalesResultNotifyOrder.setNextFireTimes(DateFormatUtils.format(nextFireTime, "yyyy-MM-dd HH:mm:ss")); // 记录补单时间线
				merchantSalesResultNotifyOrder.setNextFireTime(nextFireTime); // 下次触发时间
				// 更新次数
				merchantSalesResultNotifyOrderService.updateNotifyCount(merchantSalesResultNotifyOrder);
			}
			
		}
	}

	@Override
	public Map<String, String> findNotifyInfo(String orderCode) {
		Map<String, String> resPrams = new HashMap<>();
		MerchantSalesResultNotifyOrder merchantSalesResultNotifyOrder = merchantSalesResultNotifyOrderService.queryByOrderCode(orderCode);
		if(merchantSalesResultNotifyOrder != null){
			if(merchantSalesResultNotifyOrder.getNotifyResult() != null){
				resPrams.put("notifyStatus", merchantSalesResultNotifyOrder.getNotifyResult().name());
			}else{
				resPrams.put("notifyStatus", NotifyStatus.FAILED.name());
			}
			resPrams.put("notifyUrl", merchantSalesResultNotifyOrder.getNotifyURL());
			resPrams.put("notifyTime", DateFormatUtils.format(merchantSalesResultNotifyOrder.getNextFireTime(), "yyyy-MM-dd HH:mm:ss"));
			resPrams.put("notifyCount", String.valueOf(merchantSalesResultNotifyOrder.getNotifyCount()));
		}else{
			resPrams.put("notifyStatus", NotifyStatus.FAILED.name());
			resPrams.put("notifyTime", "");
			resPrams.put("notifyCount", "");
		}
		return resPrams;
	}

}
