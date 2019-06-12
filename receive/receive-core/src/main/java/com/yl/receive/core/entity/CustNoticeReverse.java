package com.yl.receive.core.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.yl.receive.core.enums.NotifyStatus;
import com.yl.receive.core.enums.OrderStatus;

/**
 * 商户消费结果的订单实体
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月25日
 * @version V1.0.0
 */
public class CustNoticeReverse extends BaseEntity implements Serializable {

	private static final long serialVersionUID = -5916110605973190161L;
	/** 交易订单编号 */
	private String orderCode;
	/** 交易订单处理结果 */
	private OrderStatus orderStatus;
	/** 合作方请求流水号（唯一） */
	private String requestCode;
	/** 合作方后台通知地址 */
	private String notifyURL;
	/** 通知结果 */
	private NotifyStatus notifyResult;
	/** 通知次数 */
	private int notifyCount;
	/** 补单下次触发时间线 */
	private List<Date> nextFireTimes = new ArrayList<Date>();
	/** 补单下次触发时间 */
	private Date nextFireTime;

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getRequestCode() {
		return requestCode;
	}

	public void setRequestCode(String requestCode) {
		this.requestCode = requestCode;
	}

	public String getNotifyURL() {
		return notifyURL;
	}

	public void setNotifyURL(String notifyURL) {
		this.notifyURL = notifyURL;
	}

	public NotifyStatus getNotifyResult() {
		return notifyResult;
	}

	public void setNotifyResult(NotifyStatus notifyResult) {
		this.notifyResult = notifyResult;
	}

	public int getNotifyCount() {
		return notifyCount;
	}

	public void setNotifyCount(int notifyCount) {
		this.notifyCount = notifyCount;
	}

	public Date getNextFireTimes() {
		if (nextFireTimes.size() == 0) return null;
		return nextFireTimes.get(nextFireTimes.size() - 1);
	}

	public void setNextFireTimes(Date nextFireTime) {
		nextFireTimes.add(nextFireTime);
	}

	public Date getNextFireTime() {
		return nextFireTime;
	}

	public void setNextFireTime(Date nextFireTime) {
		this.nextFireTime = nextFireTime;
	}

	@Override
	public String toString() {
		return "MerchantSalesResultNotifyOrder [orderCode=" + orderCode + ", orderStatus=" + orderStatus + ", requestCode=" + requestCode + ", notifyURL="
				+ notifyURL + ", notifyResult=" + notifyResult + ", notifyCount=" + notifyCount + ", nextFireTimes=" + nextFireTimes + ", nextFireTime="
				+ nextFireTime + "]";
	}

	// public Order parsetToOrderBean() {
	// Order order = new Order();
	// order.setCode(this.orderCode);
	// order.setStatus(this.orderStatus);
	// order.setRequestCode(this.requestCode);
	// order.setNotifyURL(this.notifyURL);
	// return order;
	// }
}
