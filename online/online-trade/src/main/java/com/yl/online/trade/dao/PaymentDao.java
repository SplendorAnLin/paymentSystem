package com.yl.online.trade.dao;

import com.yl.online.model.bean.Payment;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 支付记录数据访问接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年7月27日
 * @version V1.0.0
 */
public interface PaymentDao {

	/**
	 * 新增支付记录
	 * @param payment 支付记录
	 */
	public void create(Payment payment);
	
	/**
	 * 更新支付记录
	 * @param payment 支付记录
	 * @return
	 */
	public int update(@Param("payment")Payment payment);

	/**
	 * 根据支付记录流水号编码查询支付记录信息
	 * @param paymentCode 支付记录流水号
	 * @return Payment
	 */
	public Payment findByCode(@Param("paymentCode")String paymentCode);
	
	/**
	 * 根据订单编号查询
	 * @param orderCode
	 * @return
	 */
	public List<Payment> findByOrderCode(@Param("orderCode")String orderCode);
	
	/**
	 * 根据接口订单号查询
	 * @param interfaceRequestId
	 * @return
	 */
	public Payment findByInterfaceRequestId(@Param("interfaceRequestId")String interfaceRequestId);

	/**
	 * 查询最后一笔交易流水
	 * @param orderCode
	 * @return
	 */
	Payment queryLastPayment(@Param("orderCode") String orderCode);

}