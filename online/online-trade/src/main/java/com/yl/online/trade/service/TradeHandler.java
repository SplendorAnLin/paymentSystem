package com.yl.online.trade.service;

import com.yl.online.model.bean.CreateOrderBean;
import com.yl.online.model.bean.CreatePaymentBean;
import com.yl.online.model.bean.PayResultBean;
import com.yl.online.model.bean.TradeContext;
import com.yl.online.model.model.Order;
import com.yl.online.trade.exception.BusinessException;

/**
 * 交易接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年7月27日
 * @version V1.0.0
 */
public interface TradeHandler {

	/**
	 * 创建订单
	 * @param tradeContext 交易上下文
	 * @throws BusinessException 业务异常
	 */
	Order createOrder(CreateOrderBean createOrderBean) throws BusinessException;

	/**
	 * 创建支付记录
	 * @param tradeContext 交易上下文
	 * @throws BusinessException 业务异常
	 */
	PayResultBean createPayment(CreatePaymentBean createPaymentBean) throws BusinessException;

	/**
	 * 交易完成后处理
	 * @param tradeContext 交易上下文
	 * @throws BusinessException 业务异常
	 */
	void complete(TradeContext tradeContext) throws BusinessException;

}
