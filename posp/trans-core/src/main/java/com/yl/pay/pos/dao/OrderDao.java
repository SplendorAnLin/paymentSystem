package com.yl.pay.pos.dao;

import com.yl.pay.pos.entity.Order;
import com.yl.pay.pos.entity.OrderPayment;
import com.yl.pay.pos.entity.OrderSendFailRecord;
import com.yl.pay.pos.enums.OrderStatus;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Title: Order Dao
 * Description: 
 * Copyright: Copyright (c)2011
 * Company: com.yl.pay
 * @author haitao.liu
 */
public interface OrderDao {

	//根据ID查询
	public Order findById(Long id);
		
	//根据终端号,批次号,流水号查询
	public Order findSourceOrder(String posCati, String posBatch, String posRequestId);

	//创建
	public Order create(Order order);

	//更新
	public Order update(Order order);

	//根据商户号、终端号、批次号、状态查找
	public List<Order> findByCustomerNoAndPosCatiAndPosBatchAndStatus(String customerNo, String posCati, String posBatch, OrderStatus status);

	//根据状态和完成时间查找
	public List<Order> findByCompleteTimeAndStatus(Date start, Date end);

	//根据终端号、批次号、流水号、卡号、金额查找
	public List<Order> findSourceOrder(String posCati, String posBatch, String posRequestId, String pan, double amount);
	//流水号
	public Order findByCode(String externalId);

	/**
	 * 根据卡号、授权码、状态查找
	 * @author haitao.liu
	 */
	public Order findSourceOrder(String pan, String authorizationCode, String transDate, OrderStatus status);
	/**
	 * 根据流水号查找成功交易
	 * @param exterId
	 * @return
	 */
	public Order findByExtrId(String exterId, OrderStatus status);
	/**
	 * 存储消息数据
	 * @param orderPayment
	 * @return
	 */
	public OrderPayment create(OrderPayment orderPayment);
	/**
	 * 删除消息数据
	 * @param orderPayment
	 */
	public void delete(OrderPayment orderPayment);
	/**
	 * 查询所有发送失败的消息数据
	 * @return
	 */
	public List<OrderPayment> findBy();

	public Map<String,String> findByMonitor();

	/**
	 * 保存入账失败订单ID
	 * @param orderSendFailRecord
	 */
	public OrderSendFailRecord create(OrderSendFailRecord orderSendFailRecord);
	/**
	 * 删除失败订单数据
	 * @param orderSendFailRecord
	 */
	public void delete(OrderSendFailRecord orderSendFailRecord);

	/**
	 * 查询所有入账失败的消息数据
	 * @return
	 */
	public List<OrderSendFailRecord> findAll();

	/**查询订单根据订单号
	 * @param externalId
	 * @return
	 */
	public Order findByExtrId(String externalId);

	/**
	 * 查询乐付宝成功订单
	 * @param start
	 * @param end
	 * @return
	 */
	public List<Order> findLfbByCompleteTimeAndStatus(Date start, Date end);

	/**
	 * @param start
	 * @param end
	 * @param bankInterfaceCode
	 * @return
	 */
	public List<Order> findByCompleteTimeAndStatusAndBankInterface(Date start,
                                                                   Date end, String bankInterfaceCode);

	/**
	 * 
	 * @return
	 */
	public List<Order> findByUnSycn();
	
	/**
	 * 查询POS订单合计
	 * @param type (boss || agent || customer)
	 * @param no (null || agentNo || customerNo)
	 * @param params (查询条件)
	 * @return
	 */
	public Map<String, Object> posOrderSum(String type,String no,Map<String,Object> params);
	
	/**
	 * 条件查询所有订单
	 * @param hql
	 * @return
	 */
	String orderSum(String orderTimeStart, String orderTimeEnd, String owner);
}



