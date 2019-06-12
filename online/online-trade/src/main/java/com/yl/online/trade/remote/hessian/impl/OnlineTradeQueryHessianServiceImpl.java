package com.yl.online.trade.remote.hessian.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.lefu.commons.utils.Page;
import com.yl.online.model.bean.Payment;
import com.yl.online.model.model.Order;
import com.yl.online.trade.dao.TradeOrderDao;
import com.yl.online.trade.hessian.OnlineTradeQueryHessianService;
import com.yl.online.trade.service.PaymentService;
import com.yl.online.trade.service.TradeOrderService;

/**
 * 在线交易查詢服务接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月13日
 * @version V1.0.0
 */
@Service("onlineTradeQueryHessianService")
public class OnlineTradeQueryHessianServiceImpl implements OnlineTradeQueryHessianService {

	@Resource
	private TradeOrderService tradeOrderService;
	@Resource
	private PaymentService paymentService;
	@Resource
	private TradeOrderDao tradeOrderDao;
	
	@Override
	public List<Order> customerRecon(Map<String, Object> params) {
		return tradeOrderDao.customerRecon(params);
	}
	
	@Override
	public Page findTradeOrder(Page page, Map<String, Object> params) {
		page.setObject(tradeOrderService.findTradeOrder(page, params));
		return page;
	}
	
	@Override
	public Object findAllTradeOrderAndFee(Page page, Map<String, Object> params) {
		return tradeOrderService.findAllTradeOrderAndFee(page, params);
	}

	@Override
	public Object findOrderByCode(String tradeOrderCode) {
		return tradeOrderService.queryByCode(tradeOrderCode);
	}

	@Override
	public String orderFeeSum(Map<String, Object> params) {
		return tradeOrderService.orderFeeSum(params);
	}

	@Override
	public Object findRefundInfo(Page page, Map<String, Object> params) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String orderInfoExport(Map<String, Object> params) {
		return tradeOrderService.orderInfoExport(params);
	}

	@Override
	public String findOrderBy(String partner, String outOrderId) {
		return tradeOrderService.findOrderBy(partner, outOrderId);
	}

	@Override
	public Map<Object, Object> payOrderGroupSum(Map<String, String> params) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Object findPaymentByOrderCode(String orderCode) {
		List<Payment> list = paymentService.findByOrderCode(orderCode);
		return list;
	}

	@Override
	public List<Map<String,String>> orderSumByDay(Date orderTimeStart,
			Date orderTimeEnd,String receiver) {
		return tradeOrderService.orderSumByDay(orderTimeStart, orderTimeEnd, receiver);
	}

	@Override
	public List<Map<String, String>> orderSum(Date orderTimeStart, Date orderTimeEnd, String status, List receiver) {
		return tradeOrderService.orderSum(orderTimeStart, orderTimeEnd, status, receiver);
	}

	@Override
	public List<Map<String, String>> orderAmountSumByDay(String orderTimeStart, String orderTimeEnd, String receiver) {
		return tradeOrderService.orderAmountSumByDay(orderTimeStart, orderTimeEnd, receiver);
	}

	@Override
	public List<Map<String, String>> orderAmountSumByPayType(Date orderTimeStart, Date orderTimeEnd, String receiver) {
		return tradeOrderService.orderAmountSumByPayType(orderTimeStart, orderTimeEnd, receiver);
	}

	@Override
	public List<Map<String, Object>> selectTradeOrder(Map<String, Object> params) {
		try {
			return tradeOrderService.selectTradeOrder(params);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Map<String, Object>> orderWeekSumByDay(Map<String, Object> params) {
		return tradeOrderService.orderWeekSumByDay(params);
	}
	
	@Override
	public List<Map<String, Object>> findOrderApp(Map<String, Object> params) {
		return tradeOrderService.findOrderApp(params);
	}

	@Override
	public List<Map<String, Object>> selectTradeOrderSum(Map<String, Object> params) {
		try {
			return tradeOrderService.selectTradeOrderSum(params);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Map<String, Object>> selectTradeOrderDetailed(Map<String, Object> params) {
		try {
			return tradeOrderService.selectTradeOrderDetailed(params);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Map<String, Object> selectOrderCount(Map<String, Object> params) {
		return tradeOrderService.selectOrderCount(params);
	}

	@Override
	public Order findTradeOrderDetail(Map<String, Object> params) {
		return tradeOrderService.findTradeOrderDetail(params);
	}
}