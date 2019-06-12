package com.yl.online.trade.service.impl;

import com.lefu.commons.utils.Page;
import com.lefu.commons.utils.lang.AmountUtils;
import com.lefu.commons.utils.lang.DateUtils;
import com.lefu.commons.utils.lang.JsonUtils;
import com.lefu.commons.utils.lang.StringUtils;
import com.yl.boss.api.interfaces.CustomerInterface;
import com.yl.online.model.bean.Payment;
import com.yl.online.model.bean.TradeContext;
import com.yl.online.model.enums.OrderStatus;
import com.yl.online.model.enums.PayType;
import com.yl.online.model.enums.PaymentStatus;
import com.yl.online.model.enums.RefundStatus;
import com.yl.online.model.model.Order;
import com.yl.online.trade.dao.PaymentDao;
import com.yl.online.trade.dao.TradeOrderDao;
import com.yl.online.trade.service.TradeOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.math.RoundingMode;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 交易订单业务处理接口实现
 *
 * @author 聚合支付有限公司
 * @since 2016年7月27日
 * @version V1.0.0
 */
@Service("tradeOrderService")
public class TradeOrderServiceImpl implements TradeOrderService {

	private static final Logger logger = LoggerFactory.getLogger(TradeOrderServiceImpl.class);

	@Resource
	private TradeOrderDao tradeOrderDao;
	@Resource
	private PaymentDao paymentDao;
	@Resource
	private CustomerInterface customerInterface;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.lefu.online.trade.service.TradeOrderService#complete(com.lefu.online
	 * .trade.bean.TradeContext)
	 */
	@Override
	public Map<String, Object> selectOrderCount(Map<String, Object> params) {
		try {

			return tradeOrderDao.selectOrderCount(params);
		} catch (Exception e) {
			logger.error("查询交易总条数异常", e);
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Order> findTradeOrder(Page page, Map<String, Object> params) {

		return tradeOrderDao.findAllTradeOrder(page, params);
	}


	@Override
	public void complete(TradeContext tradeContext) {
		// 支付流水信息
		Payment payment = tradeContext.getPayment();
		// 根据订单号查询支付订单信息
		Order order = tradeContext.getOrder();
		// 支付成功
		if (PaymentStatus.SUCCESS.equals(payment.getStatus())) {
			order.setCost(payment.getPayinterfaceFee());
			order.setStatus(OrderStatus.SUCCESS);
			order.setRefundStatus(RefundStatus.NOT_REFUND);
			order.setPaidAmount(AmountUtils.add(order.getPaidAmount(), payment.getAmount()));
			order.setPayerFee(payment.getPayerFee());
			// order.setReceiverFee(AmountUtils.add(
			// order.getReceiverFee() == null ? 0 : order.getReceiverFee(),
			// payment.getReceiverFee()));

			order.setReceiverFee(payment.getReceiverFee());
			order.setRemitFee(payment.getRemitFee());
			// 可退金额
			order.setRefundableAmount(AmountUtils.subtract(payment.getAmount(), payment.getReceiverFee()));
			order.setPayType(payment.getPayType());
		} else if (PaymentStatus.FAILED.equals(payment.getStatus())) { // 支付失败
			order.setStatus(OrderStatus.FAILED);
		}
		tradeOrderDao.update(order);
		tradeContext.setOrder(order);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.lefu.online.trade.service.TradeOrderService#create(com.lefu.online
	 * .trade.model.TradeOrder)
	 */
	@Override
	public void create(Order order) {
		tradeOrderDao.insert(order);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.lefu.online.trade.service.TradeOrderService#queryByCode(java.lang
	 * .String)
	 */
	@Override
	public Order queryByCode(String code) {
		return tradeOrderDao.findByCode(code);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.lefu.online.trade.service.TradeOrderService#findByRequestCode(java
	 * .lang.String)
	 */
	@Override
	public Order queryByRequestCode(String receiver, String requestCode) {
		return tradeOrderDao.findByRequestCode(receiver, requestCode);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<Order> queryBy(Page page) {
		return tradeOrderDao.findBy(page);
	}

	@Override
	public Order queryByInterfaceRequestID(String interfaceRequestID) {
		Payment payment = paymentDao.findByInterfaceRequestId(interfaceRequestID);
		return tradeOrderDao.findByCode(payment.getOrderCode());
	}

	@Override
	public void closeTimeoutOrder(Order order) {
		tradeOrderDao.closeTimeoutOrder(order);
	}

	@Override
	public Order queryByPaymentCode(String businessOrderID) {
		Payment payment = paymentDao.findByCode(businessOrderID);
		return tradeOrderDao.findByCode(payment.getOrderCode());
	}

	@Override
	public List<Order> queryWaitCloseOrderBy(int maxNums) {
		return tradeOrderDao.queryWaitCloseOrderBy(maxNums);
	}

	@Override
	public void modifyOrder(Order order) {
		tradeOrderDao.update(order);
	}

	@Override
	public void modifyOrderClearingStatus(Order order) {
		tradeOrderDao.modifyOrderClearingStatus(order);
	}

	// 查询交易订单信息和手续费
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Object findAllTradeOrderAndFee(Page page, Map<String, Object> params) {
		ArrayList list = new ArrayList<>();
		// 运营查询入口
		if (params.get("system") != null && params.get("system").toString().equals("BOSS")) {
			Object agentNo=params.get("agentNo");
			if (agentNo!=null){
				String customer = customerInterface.findCustNoByAgentNo(agentNo.toString());
				if (customer != null) {
					String cust[] = customer.split(",");
					for (int i = 0; i < cust.length; i++) {
						list.add(cust[i]);
					}
				}
			}
			List<Order> tradeOrders = tradeOrderDao.findAllTradeOrderAndFee(page, params, list);
			try {
				page.setObject(tradeOrders);
				return page;
			} catch (Exception e) {
				logger.error("交易查询异常：{}", e);
			}
		}

		// 商户查询入口
		if(params.get("sys") != null && params.get("sys").toString().equals("CUSTOMER")){
            params.put("system", "CUSTOMER");
			List<Order> tradeOrders = tradeOrderDao.findAllTradeOrderAndFee(page, params, list);
			try {
				page.setObject(tradeOrders);
				return page;
			} catch (Exception e) {
				logger.error("交易查询异常：{}", e);
			}
		}

		// 服务商查询入口
		String customer = customerInterface.findCustNoByAgentNo(params.get("agentNo").toString());
		if (customer != null) {
			String cust[] = customer.split(",");
			for (int i = 0; i < cust.length; i++) {
				list.add(cust[i]);
			}
			List<Order> tradeOrders = tradeOrderDao.findAllTradeOrderAndFee(page, params, list);
			try {
				page.setObject(tradeOrders);
				return page;
			} catch (Exception e) {
				logger.error("交易查询异常：{}", e);
			}
		}
		return null;
	}

	// 根据订单号查询订单信息
	@Override
	public String queryTradeOrderByCode(String orderCode) {
		// 返回字串
		String resultStr = "";
		Order order = tradeOrderDao.findByCode(orderCode);
		try {
			resultStr = JsonUtils.toJsonString(order);
			return resultStr;
		} catch (Exception e) {
			logger.error("{}", e);
		}
		return resultStr;
	}

	// 订单费用合计
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public String orderFeeSum(Map<String, Object> params) {
		ArrayList list = new ArrayList<>();
		// 运营查询入口
		if (params.get("system") != null && params.get("system").toString().equals("BOSS")) {
			Object agentNo=params.get("agentNo");
			if (agentNo!=null){
				String customer = customerInterface.findCustNoByAgentNo(agentNo.toString());
				if (customer != null) {
					String cust[] = customer.split(",");
					for (int i = 0; i < cust.length; i++) {
						list.add(cust[i]);
					}
				}
			}
			Map<String, Object> result = tradeOrderDao.orderFeeSum(params, list);
			return JsonUtils.toJsonString(result);
		}

		// 商户查询入口
		if(params.get("sys") != null && params.get("sys").toString().equals("CUSTOMER")){
			params.put("system", "CUSTOMER");
			Map<String, Object> result = tradeOrderDao.orderFeeSum(params, list);
			return JsonUtils.toJsonString(result);
		}

		// 服务商查询入口
		String customer = customerInterface.findCustNoByAgentNo(params.get("agentNo").toString());
		if (customer != null) {
			String cust[] = customer.split(",");
			for (int i = 0; i < cust.length; i++) {
				list.add(cust[i]);
			}
			Map<String, Object> result = tradeOrderDao.orderFeeSum(params, list);
			return JsonUtils.toJsonString(result);
		}
		return null;
	}

	// 订单信息导出
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public String orderInfoExport(Map<String, Object> params) {
		ArrayList list = new ArrayList<>();
		// 运营查询入口
		if (params.get("system") != null && params.get("system").toString().equals("BOSS")) {
			Object agentNo=params.get("agentNo");
			if (agentNo!=null){
				String customer = customerInterface.findCustNoByAgentNo(agentNo.toString());
				if (customer != null) {
					String cust[] = customer.split(",");
					for (int i = 0; i < cust.length; i++) {
						list.add(cust[i]);
					}
				}
			}
			List<Order> orders = tradeOrderDao.exportTradeOrderInfo(params, list);
			return JsonUtils.toJsonString(orders);
		}

		// 商户查询入口
		if(params.get("sys") != null && params.get("sys").toString().equals("CUSTOMER")){
			params.put("system", "CUSTOMER");
			List<Order> orders = tradeOrderDao.exportTradeOrderInfo(params, list);
			return JsonUtils.toJsonString(orders);
		}

		// 服务商查询入口
		String customer = customerInterface.findCustNoByAgentNo(params.get("agentNo").toString());
		try {
			if (customer != null) {
				String cust[] = customer.split(",");
				for (int i = 0; i < cust.length; i++) {
					list.add(cust[i]);
				}
				List<Order> orders = tradeOrderDao.exportTradeOrderInfo(params, list);
				return JsonUtils.toJsonString(orders);
			}
		} catch (Exception e) {
			logger.error("交易查询导出失败：{}", e);
		}
		return null;
	}

	@Override
	public Order findOrderPayTradeInfo(Map<String, Object> params) {
		// XXX
		return null;
	}

	@Override
	public String findOrderBy(String partner, String outOrderId) {
		Order order = tradeOrderDao.findByRequestCode(partner, outOrderId);
		if (order == null) {
			return null;
		}
		return JsonUtils.toJsonString(order);
	}

	/**
	 * 订单信息分组合计
	 */
	@Override
	public Map<Object, Object> payOrderGroupSum(Map<String, String> params) {
		Map<Object, Object> result = new LinkedHashMap<Object, Object>();
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date orderTimeStart = null;
		Date orderTimeEnd = null;
		try {
			if (StringUtils.notBlank(params.get("orderTimeStart"))) {
				orderTimeStart = format.parse(params.get("orderTimeStart"));
			}
			if (StringUtils.notBlank(params.get("orderTimeEnd"))) {
				orderTimeEnd = format.parse(params.get("orderTimeEnd"));
			}
			// XXX
			return result;
		} catch (Exception e) {
			logger.error("{}", e);
		}
		return result;
	}

	@Override
	public List<Map<String, String>> orderSumByDay(Date orderTimeStart, Date orderTimeEnd, String receiver) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return tradeOrderDao.orderSumByDay(sdf.format(orderTimeStart), sdf.format(orderTimeEnd), receiver);
	}

	@Override
	public List<Map<String, String>> orderSum(Date orderTimeStart, Date orderTimeEnd, String status, List receiver) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		return tradeOrderDao.orderSum(sdf.format(DateUtils.getMaxTime(orderTimeStart)), sdf.format(DateUtils.getMinTime(orderTimeEnd)), status, receiver);
	}

	@Override
	public List<Map<String, String>> orderAmountSumByDay(String orderTimeStart, String orderTimeEnd, String receiver) {
		return tradeOrderDao.orderAmountSumByDay(orderTimeStart, orderTimeEnd, receiver);
	}

	@Override
	public List<Map<String, String>> orderAmountSumByPayType(Date orderTimeStart, Date orderTimeEnd, String receiver) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return tradeOrderDao.orderAmountSumByPayType(sdf.format(orderTimeStart), sdf.format(orderTimeEnd), receiver);
	}

	@Override
	public Double orderAmountSum(Date orderTime, String receiver, String payType) {
		double i = 0;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String timeStart = sdf.format(DateUtils.getMinTime(orderTime));
		String timeEnd = sdf.format(DateUtils.getMaxTime(orderTime));
		Map<String, Double> list = tradeOrderDao.orderAmountSum(timeStart, timeEnd, receiver, payType);
		if (list != null && list.size() > 0) {
			i = AmountUtils.round(list.get("amount"), 2, RoundingMode.HALF_UP);
		}
		return i;
	}

	@Override
	public List<Map<String, Object>> selectTradeOrder(Map<String, Object> params) {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			if(params.get("successPayTime")!=null){
				String strat=params.get("successPayTime").toString()+" 00:00:00";
				String end=params.get("successPayTime").toString()+" 23:59:59";
				Timestamp startDate=Timestamp.valueOf(strat);
				Timestamp endDate=Timestamp.valueOf(end);
				params.put("startSuccessPayTime", startDate);
				params.put("endSuccessPayTime", endDate);
			}
			if(params.get("payType")!=null){
				if(params.get("payType").toString().equals("B2C")){
					params.put("payType",PayType.B2C);
				}
				else if(params.get("payType").toString().equals("B2B")){
					params.put("payType",PayType.B2B);
				}
				else if(params.get("payType").toString().equals("AUTHPAY")){
					params.put("payType",PayType.AUTHPAY);
				}
				else if(params.get("payType").toString().equals("REMIT")){
					params.put("payType",PayType.REMIT);
				}
				else if(params.get("payType").toString().equals("WXJSAPI")){
					params.put("payType",PayType.WXJSAPI);
				}
				else if(params.get("payType").toString().equals("WXNATIVE")){
					params.put("payType",PayType.WXNATIVE);
				}
				else if(params.get("payType").toString().equals("ALIPAY")){
					params.put("payType",PayType.ALIPAY);
				}
				else if(params.get("payType").toString().equals("UNKNOWN")){
					params.put("payType",PayType.UNKNOWN);
				}
				else if(params.get("payType").toString().equals("ALL")){
					params.put("payType",PayType.ALL);
				}
			}
			List<Map<String, Object>> payMent = tradeOrderDao.selectTradeOrder(params);
			return payMent;
		} catch (Exception e) {
			logger.error("{}", e);
			return null;

		}
	}

	@Override
	public List<Map<String, Object>> orderWeekSumByDay(Map<String, Object> params) {
		return tradeOrderDao.orderWeekSumByDay(params);
	}

	@Override
	public List<Map<String, Object>> findOrderApp(Map<String, Object> params) {
		return tradeOrderDao.findOrderApp(params);
	}

	@Override
	public List<Map<String, Object>> selectTradeOrderSum(Map<String, Object> params) {
		List<Map<String, Object>> order =null;
		try {
			order = tradeOrderDao.selectTradeOrderSum(params);
			return order;
		}catch (Exception e) {
			logger.error("{}", e);
			return null;
		}
	}

	@Override
	public List<Map<String, Object>> selectTradeOrderDetailed(Map<String, Object> params) {
		List<Map<String, Object>> order =new ArrayList<Map<String, Object>>();
		try {
			order = tradeOrderDao.selectTradeOrderDetailed(params);
			return order;
		}catch (Exception e) {
			logger.error("{}", e);
			return null;
		}
	}

	@Override
	public Order findTradeOrderDetail(Map<String, Object> params) {
		return tradeOrderDao.findTradeOrderDetail(params);
	}

	@Override
	public List<Order> findOrderByJob(Map<String, Object> params) {
		return tradeOrderDao.findOrderByJob(params);
	}

	@Override
	public Map<String, Object> findTotalByJob(Map<String, Object> params) {
		return tradeOrderDao.findTotalByJob(params);
	}


}