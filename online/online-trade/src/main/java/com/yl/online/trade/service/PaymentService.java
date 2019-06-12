package com.yl.online.trade.service;

import com.yl.online.model.bean.Payment;
import com.yl.online.model.bean.TradeContext;

import java.util.List;

/**
 * 支付记录服务接口
 *
 * @author 聚合支付有限公司
 * @since 2016年7月27日
 * @version V1.0.0
 */
public interface PaymentService {

	/**
	 * 新增支付记录
	 * @param payment 支付记录
	 * @param order 支付订单
	 */
	public void create(Payment payment);

	/**
	 * 支付交易完成处理
	 * @param tradeContext
	 */
	public void complete(TradeContext tradeContext);

	/**
	 * 根据订单号查询
	 * @param orderCode
	 * @return
	 */
	public List<Payment> findByOrderCode(String orderCode);

	/**
	 * 根据支付编号查询
	 * @param code
	 * @return
	 */
	public Payment findByCode(String code);

	/**
	 * 更新支付记录
	 * @param payment
	 */
	public void update(Payment payment);

	/**
	 * 查询最后一笔交易流水
	 * @param orderCode
	 * @return
	 */
	Payment queryLastPayment(String orderCode);

}
