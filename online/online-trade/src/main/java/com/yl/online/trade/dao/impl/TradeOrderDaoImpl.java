package com.yl.online.trade.dao.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.lefu.commons.utils.Page;
import com.yl.online.model.model.Order;
import com.yl.online.trade.dao.TradeOrderDao;
import com.yl.online.trade.dao.mapper.TradeOrderMapper;
import com.yl.online.trade.utils.CodeBuilder;

/**
 * 交易订单数据访问接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年7月27日
 * @version V1.0.0
 */
@Repository("tradeOrderDao")
public class TradeOrderDaoImpl implements TradeOrderDao {
	
	@Resource
	private TradeOrderMapper tradeOrderMapper;

	@Override
	public void insert(Order order) {
		order.setCode(CodeBuilder.build("TO", "yyyyMMdd"));
		order.setCreateTime(new Date());
		order.setVersion(System.nanoTime());
		tradeOrderMapper.insert(order);
	}

	@Override
	public Order findByCode(String code) {
		return tradeOrderMapper.findByCode(code);
	}

	@Override
	public Order findByRequestCode(String receiver, String requestCode) {
		return tradeOrderMapper.findByRequestCode(receiver, requestCode);
	}

	@Override
	public void update(Order order) {
		tradeOrderMapper.update(order);
	}

	@Override
	public List<Order> findBy(Page page) {
			return tradeOrderMapper.findBy(page);
	}

	@Override
	public void closeTimeoutOrder(Order order) {
		tradeOrderMapper.closeTimeoutOrder(order);		
	}

	@Override
	public List<Order> queryWaitCloseOrderBy(int maxNums) {
		return tradeOrderMapper.queryWaitCloseOrderBy(maxNums);
	}

	@Override
	public void modifyOrderClearingStatus(Order order) {
		tradeOrderMapper.modifyOrderClearingStatus(order);
	}

	@Override
	public List<Order> findAllTradeOrderAndFee(Page page, @Param("params")Map<String, Object> params,@Param("list")List<String> list) {
		return tradeOrderMapper.findAllTradeOrderAndFee(page, params,list);
	}

	@Override
	public Map<String, Object> orderFeeSum(Map<String, Object> params,List<String> list) {
		return tradeOrderMapper.orderFeeSum(params,list);
	}

	@Override
	public List<Order> exportTradeOrderInfo(Map<String, Object> params,List<String> list) {
		return tradeOrderMapper.exportTradeOrderInfo(params,list);
	}

	@Override
	public List<Order> payOrderGroupSum(Map<String, String> params, Date orderTimeStart, Date orderTimeEnd) {
		return tradeOrderMapper.payOrderGroupSum(params, orderTimeStart, orderTimeEnd);
	}

	@Override
	public List<Map<String,String>> orderSumByDay(String orderTimeStart,
			String orderTimeEnd,String receiver) {
		return tradeOrderMapper.orderSumByDay(orderTimeStart, orderTimeEnd,receiver);
	}

	@Override
	public List<Map<String, String>> orderSum(String orderTimeStart, String orderTimeEnd, String status,List<String> receiver) {
		return tradeOrderMapper.orderSum(orderTimeStart, orderTimeEnd, status, receiver);
	}

	@Override
	public List<Map<String, String>> orderAmountSumByDay(String orderTimeStart, String orderTimeEnd, String receiver) {
		return tradeOrderMapper.orderAmountSumByDay(orderTimeStart, orderTimeEnd, receiver);
	}

	@Override
	public List<Map<String, String>> orderAmountSumByPayType(String orderTimeStart, String orderTimeEnd,
			String receiver) {
		return tradeOrderMapper.orderAmountSumByPayType(orderTimeStart, orderTimeEnd, receiver);
	}

	@Override
	public Map<String, Double> orderAmountSum(String timeStart, String timeEnd, String receiver, String payType) {
		return tradeOrderMapper.orderAmountSum(timeStart, timeEnd, receiver, payType);
	}

	@Override
	public void updatePay(String orderCode, String payType) {
		tradeOrderMapper.updatePay(orderCode, payType);
	}

	@Override
	public List<Map<String, Object>> selectTradeOrder(Map<String, Object> params) {
		try {
			List<Map<String, Object>> list=tradeOrderMapper.selectTradeOrder(params);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Map<String, Object>> orderWeekSumByDay(Map<String, Object> params) {
		return tradeOrderMapper.orderWeekSumByDay(params);
	}

	@Override
	public List<Map<String, Object>> findOrderApp(Map<String, Object> params) {
		return tradeOrderMapper.findOrderApp(params);
	}

	@Override
	public List<Map<String, Object>> selectTradeOrderSum(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return tradeOrderMapper.selectTradeOrderSum(params);
	}

	@Override
	public List<Map<String, Object>> selectTradeOrderDetailed(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return tradeOrderMapper.selectTradeOrderDetailed(params);
	}

	@Override
	public Map<String, Object> selectOrderCount(Map<String, Object> params) {
		
		return tradeOrderMapper.selectOrderCount(params);
	}

	@Override
	public List<Order> findAllTradeOrder(Page page, Map<String, Object> params) {
		
		return tradeOrderMapper.findAllTradeOrder(page, params);
	}

	@Override
	public Order findTradeOrderDetail(Map<String, Object> params) {
		
		return tradeOrderMapper.findTradeOrderDetail(params);
	}

	@Override
	public List<Order> customerRecon(Map<String, Object> params) {
		return tradeOrderMapper.customerRecon(params);
	}
	@Override
	public List<Order> findOrderByJob(Map<String, Object> params) {
		return tradeOrderMapper.findOrderByJob(params);
	}

	@Override
	public Map<String, Object> findTotalByJob(Map<String, Object> params) {
		return tradeOrderMapper.findTotalByJob(params);
	}
}