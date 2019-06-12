package com.yl.dpay.core.bean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import net.sf.json.util.JSONStringer;

/**
 * 通知信息
 * 
 * @author 聚合支付有限公司
 * @since 2016年5月11日
 * @version V1.0.0
 */
public class NotifyInfo implements Serializable {

	private static final long serialVersionUID = 7339719065531274L;

	/**
	 * 商户订单号
	 */
	private String customerOrderId;
	/**
	 * 代付订单号
	 */
	private String dpayOrderId;
	/**
	 * 响应码
	 */
	private String responseCode;
	/**
	 * 响应信息
	 */
	private String responseMsg;
	/**
	 * 处理时间
	 */
	private String orderTime;
	/**
	 * 金额
	 */
	private String amount;
	/**
	 * 手续费
	 */
	private String fee;

	public String getCustomerOrderId() {
		return customerOrderId;
	}

	public void setCustomerOrderId(String customerOrderId) {
		this.customerOrderId = customerOrderId;
	}

	public String getDpayOrderId() {
		return dpayOrderId;
	}

	public void setDpayOrderId(String dpayOrderId) {
		this.dpayOrderId = dpayOrderId;
	}

	public String getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	public String getResponseMsg() {
		return responseMsg;
	}

	public void setResponseMsg(String responseMsg) {
		this.responseMsg = responseMsg;
	}

	public String getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(Date orderTime) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		this.orderTime = sdf.format(orderTime);
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getFee() {
		return fee;
	}

	public void setFee(String fee) {
		this.fee = fee;
	}

	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}

	@Override
	public String toString() {
		return "NotifyInfo [customerOrderId=" + customerOrderId + ", dpayOrderId=" + dpayOrderId + ", responseCode=" + responseCode + ", responseMsg=" + responseMsg
				+ ", orderTime=" + orderTime + ", amount=" + amount + ", fee=" + fee + "]";
	}

	public String toJsonString() {
		JSONStringer jsonStringer = new JSONStringer();
		jsonStringer.object();
		jsonStringer.key("amount");
		jsonStringer.value(amount);
		jsonStringer.key("customerOrderId");
		jsonStringer.value(customerOrderId);
		jsonStringer.key("dpayOrderId");
		jsonStringer.value(dpayOrderId);
		jsonStringer.key("fee");
		jsonStringer.value(fee);
		jsonStringer.key("orderTime");
		jsonStringer.value(orderTime);
		jsonStringer.key("responseCode");
		jsonStringer.value(responseCode);
		jsonStringer.key("responseMsg");
		jsonStringer.value(responseMsg);
		jsonStringer.endObject();
		return jsonStringer.toString();
	}

}
