package com.yl.online.trade.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;

import com.yl.online.trade.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.yl.boss.api.bean.CustomerFee;
import com.yl.boss.api.enums.Status;
import com.yl.boss.api.interfaces.CustomerInterface;
import com.yl.mq.producer.client.ProducerClient;
import com.yl.online.model.bean.Payment;
import com.yl.online.model.bean.TradeContext;
import com.yl.online.model.enums.PaymentStatus;
import com.yl.online.model.model.Order;
import com.yl.online.trade.ExceptionMessages;
import com.yl.online.trade.dao.PaymentDao;
import com.yl.online.trade.exception.BusinessRuntimeException;
import com.yl.online.trade.service.PaymentService;
import com.yl.online.trade.service.TradeOrderService;
import com.yl.online.trade.utils.FeeUtils;
import com.yl.online.trade.utils.PropertiesUtil;

/**
 * 支付记录服务接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年7月27日
 * @version V1.0.0
 */
@Service("paymentService")
public class PaymentServiceImpl implements PaymentService {

	private static final Logger logger = LoggerFactory.getLogger(PaymentServiceImpl.class);

	@Resource
	private PaymentDao paymentDao;

	@Resource
	private CustomerInterface customerInterface;
	
	@Resource
	private TradeOrderService tradeOrderService;
	
	@Resource
	private ProducerClient producerClient;
	
	@Override
	public void complete(TradeContext tradeContext) {
		Map<String, String> params = tradeContext.getRequestParameters();
		// 支付流水号
		String paymentCode = params.get("businessFlowID");
		Payment payment = paymentDao.findByCode(paymentCode);
		if (payment == null) throw new BusinessRuntimeException(ExceptionMessages.PAYMENT_NOT_EXISTS);
		if (payment.getStatus().equals(PaymentStatus.SUCCESS)) throw new BusinessRuntimeException(ExceptionMessages.PAYMENT_STATUS_NOT_INIT);

		// 根据订单号查询订单信息
		Order order = tradeContext.getOrder();
		// 交易状态
		String interfaceStatus = params.get("transStatus");
		// 系统完成时间
		Date completeTradeTime = new Date();
		
		payment.setCode(paymentCode);
		payment.setPayinterfaceOrder(params.get("interfaceOrderID"));
		payment.setResponseCode(params.get("responseCode"));
		payment.setResponseInfo(params.get("responseMessage"));
		payment.setPayinterfaceFee(Double.valueOf(params.get("interfaceFee")));
		payment.setPayinterfaceRequestId(params.get("interfaceRequestID"));
		payment.setCompleteTime(completeTradeTime);
		payment.setReturnTradeTime(completeTradeTime);

		String ownerCode = order.getReceiver();
		Double amount = payment.getAmount();
		try {
			// 判断支付结果
			if (PaymentStatus.SUCCESS.name().equals(interfaceStatus)) {
				payment.setStatus(PaymentStatus.SUCCESS);

				// 计算手续费
				CustomerFee customerFee = customerInterface.getCustomerFee(ownerCode, payment.getPayType().toString());
				if(customerFee == null || customerFee.getStatus() == Status.FALSE){
					throw new BusinessRuntimeException("order "+order.getCode()+ "customerFee error");
				}
				if (Constants.QUICKPAY_INTERFACEINFO_FEN.equals(payment.getPayinterface())) {
					String[] feeInfo = PropertiesUtil.getQuickPayFen(ownerCode, payment.getPayinterface());
					double fee = FeeUtils.computeFee(amount.doubleValue(), com.yl.online.model.enums.FeeType.RATIO, Double.valueOf(feeInfo[0]), 
							Double.valueOf(feeInfo[1]), Double.valueOf(feeInfo[2]));
					payment.setReceiverFee(fee);
				} else {
					double fee = FeeUtils.computeFee(amount.doubleValue(), com.yl.online.model.enums.FeeType.valueOf(customerFee.getFeeType().toString()),
							customerFee.getFee(), customerFee.getMinFee(), customerFee.getMaxFee());
					payment.setReceiverFee(fee);
				}
			} else if (PaymentStatus.FAILED.name().equals(interfaceStatus)) {
				payment.setStatus(PaymentStatus.FAILED);
			}
			paymentDao.update(payment);
			
			tradeContext.setPayment(payment);
			try {
				SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				//没有执行catch就保存数据
				Map<String, Object> map=new HashMap<String, Object>();
				map.put("id", payment.getId());
				map.put("code", payment.getCode());
				map.put("orderCode", payment.getOrderCode());
				map.put("payType", payment.getPayType());
				if(payment.getPayTime()!=null){
					map.put("payTime", df.format(payment.getPayTime()).toString());
				}
				map.put("status", payment.getStatus());
				map.put("amount", payment.getAmount());
				map.put("receiverFee", payment.getReceiverFee());
				map.put("payinterface", payment.getPayinterface());
				map.put("payinterfaceRequestId", payment.getPayinterfaceRequestId());
				map.put("payinterfaceOrder", payment.getPayinterfaceOrder());
				map.put("payinterfaceFee", payment.getPayinterfaceFee());
				if(payment.getCompleteTime()!=null){
					map.put("completeTime", df.format(payment.getCompleteTime()).toString());
				}
				if(payment.getReturnTradeTime()!=null){
					map.put("returnTradeTime",df.format( payment.getReturnTradeTime()).toString());
				}
				map.put("responseCode", payment.getResponseCode());
				map.put("responseInfo", payment.getResponseInfo());
				//推送消息到数据中心
//				producerClient.sendMessage(new ProducerMessage("yl-data-topic","online_payment_tag",JsonUtils.toJsonString(map).getBytes()));
//				logger.info("推送主题 yl-data-topic,标签：支付记录(修改)成功，交易单号{}，消息体{}",payment.getCode(),map);
			} catch (Exception e2) {
				logger.error("推送支付记录(修改)到数据中心失败，错误原因{}，错误订单号{}",e2, payment.getOrderCode());
			}
		} catch (Exception e) {
			logger.error("", e);
			throw new BusinessRuntimeException(e.getMessage());
		}
	}

	@Override
	public void create(Payment payment) {
		//声明变量boolean类型
		boolean bool=false;
		try {
			paymentDao.create(payment);
			//新增类型成功修改为true
			bool=true;
		} catch (Exception e) {
			logger.info("新增支付记录失败，原因{}",e);
			bool=false;
		}finally{
			//无论catch与否都执行finally的内容，判断是否执行过catch
			if(bool==true){
				try {
					SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					//没有执行catch就保存数据
					Map<String, Object> map=new HashMap<String, Object>();
					map.put("id", payment.getId());
					map.put("code", payment.getCode());
					map.put("orderCode", payment.getOrderCode());
					map.put("payType", payment.getPayType());
					if(payment.getPayTime()!=null){
						map.put("payTime", df.format(payment.getPayTime()).toString());
					}
					map.put("status", payment.getStatus());
					map.put("amount", payment.getAmount());
					map.put("receiverFee", payment.getReceiverFee());
					map.put("payinterface", payment.getPayinterface());
					map.put("payinterfaceRequestId", payment.getPayinterfaceRequestId());
					map.put("payinterfaceOrder", payment.getPayinterfaceOrder());
					map.put("payinterfaceFee", payment.getPayinterfaceFee());
					if(payment.getCompleteTime()!=null){
						map.put("completeTime", df.format(payment.getCompleteTime()).toString());
					}
					if(payment.getReturnTradeTime()!=null){
						map.put("returnTradeTime",df.format( payment.getReturnTradeTime()).toString());
					}
					map.put("responseCode", payment.getResponseCode());
					map.put("responseInfo", payment.getResponseInfo());
					//推送消息到数据中心
//					producerClient.sendMessage(new ProducerMessage("yl-data-topic","online_payment_tag",JsonUtils.toJsonString(map).getBytes()));
//					logger.info("推送主题 yl-data-topic,标签：支付记录 (创建) 成功，交易单号{}，消息体{}",payment.getCode(),map);
				} catch (Exception e2) {
					logger.error("推送支付记录(创建)到数据中心失败，错误原因{}，错误订单号{}",e2, payment.getOrderCode());
				}
			}
		}
	}

	@Override
	public List<Payment> findByOrderCode(String orderCode) {
		return paymentDao.findByOrderCode(orderCode);
	}

	@Override
	public Payment findByCode(String code) {
		return paymentDao.findByCode(code);
	}

	@Override
	public void update(Payment payment) {
		Payment pm = paymentDao.findByCode(payment.getCode());
		if(pm != null){
			pm.setPayinterfaceRequestId(payment.getPayinterfaceRequestId());
			paymentDao.update(pm);
		}
	}

	@Override
	public Payment queryLastPayment(String orderCode) {
		return paymentDao.queryLastPayment(orderCode);
	}

}
