package com.yl.online.trade.dao.impl;

import com.yl.online.model.bean.Payment;
import com.yl.online.trade.dao.PaymentDao;
import com.yl.online.trade.dao.TradeOrderDao;
import com.yl.online.trade.dao.mapper.PaymentMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * 支付记录数据访问接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年7月27日
 * @version V1.0.0
 */
@Repository("paymentDao")
public class PaymentDaoImpl implements PaymentDao {
	
	@Resource
	private PaymentMapper paymentMapper;
	@Resource
	private TradeOrderDao tradeOrderDao;

	@Override
	public void create(Payment payment) {
		paymentMapper.create(payment);
		tradeOrderDao.updatePay(payment.getOrderCode(), payment.getPayType().name());
	}
	
	@Override
	public int update(Payment payment) {
		return paymentMapper.update(payment);
	}

	@Override
	public Payment findByCode(String paymentCode) {
		return paymentMapper.findByCode(paymentCode);
	}

	@Override
	public List<Payment> findByOrderCode(String orderCode) {
		return paymentMapper.findByOrderCode(orderCode);
	}

	@Override
	public Payment findByInterfaceRequestId(String interfaceRequestId) {
		return paymentMapper.findByInterfaceRequestId(interfaceRequestId);
	}

	@Override
	public Payment queryLastPayment(String orderCode) {
		return paymentMapper.queryLastPayment(orderCode);
	}

}
