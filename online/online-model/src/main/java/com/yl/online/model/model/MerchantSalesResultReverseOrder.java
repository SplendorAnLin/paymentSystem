package com.yl.online.model.model;

import com.yl.online.model.enums.OrderStatus;
import com.yl.online.model.enums.ReverseStatus;

/**
 * 商户消费结果补单实体Bean
 * 
 * @author 聚合支付有限公司
 * @since 2016年7月16日
 * @version V1.0.0
 */
public class MerchantSalesResultReverseOrder extends AutoStringIDModel {

	private static final long serialVersionUID = 4159028676978697362L;

	/** 商户编号 */
	private String receiver;
	/** 商户请求订单号 */
	private String requestCode;	
	/** 交易订单号 */
	private String orderCode;
	/** 订单状态 */
	private OrderStatus status;
	/** 消息处理状态 */
	private ReverseStatus reverseStatus = ReverseStatus.WAIT_REVERSE;
	/** 消息补单次数 */
	private int reverseCount;

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getRequestCode() {
		return requestCode;
	}

	public void setRequestCode(String requestCode) {
		this.requestCode = requestCode;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public OrderStatus getStatus() {
		return status;
	}

	public void setStatus(OrderStatus status) {
		this.status = status;
	}

	public ReverseStatus getReverseStatus() {
		return reverseStatus;
	}

	public void setReverseStatus(ReverseStatus reverseStatus) {
		this.reverseStatus = reverseStatus;
	}

	public int getReverseCount() {
		return reverseCount;
	}

	public void setReverseCount(int reverseCount) {
		this.reverseCount = reverseCount;
	}

	@Override
	public String toString() {
		return "MerchantSalesResultReverseOrder [requestCode=" + requestCode
				+ ", orderCode=" + orderCode + ", status=" + status
				+ ", reverseStatus=" + reverseStatus + ", reverseCount="
				+ reverseCount + "]";
	}

	public static MerchantSalesResultReverseOrder parseToBean(Order order) {
		MerchantSalesResultReverseOrder merchantSalesResultReverseOrder = new MerchantSalesResultReverseOrder();
		merchantSalesResultReverseOrder.setOrderCode(order.getCode());
		merchantSalesResultReverseOrder.setRequestCode(order.getRequestCode());
		merchantSalesResultReverseOrder.setStatus(order.getStatus());
		return merchantSalesResultReverseOrder;
	}

	public Order toOrder() {
		Order order = new Order();
		order.setCode(this.getOrderCode());
		order.setRequestCode(this.getRequestCode());
		order.setStatus(this.getStatus());
		return order;
	}

}
