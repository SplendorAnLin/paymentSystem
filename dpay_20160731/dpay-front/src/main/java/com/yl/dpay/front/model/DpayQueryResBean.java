package com.yl.dpay.front.model;

import java.io.Serializable;

import com.yl.dpay.front.common.DpayException;
import com.yl.dpay.hessian.enums.DpayExceptionEnum;

/**
 * 代付查询响应bean
 * 
 * @author 聚合支付有限公司
 * @since 2016年5月22日
 * @version V1.0.0
 */
public class DpayQueryResBean implements Serializable {

	private static final long serialVersionUID = 7927651568290347232L;

	/**
	 * 商户编号
	 */
	private String customerNo;

	/**
	 * 商户订单号
	 */
	private String cutomerOrderCode;

	/**
	 * 状态码
	 */
	private String responseCode;

	/**
	 * 响应信息
	 */
	private String responseMsg;

	/**
	 * 代付订单号
	 */
	private String orderCode;

	/**
	 * 金额
	 */
	private String amount;

	/**
	 * 用途
	 */
	private String description;

	/**
	 * 订单处理时间
	 */
	private String orderTime;

	public String getCustomerNo() {
		return customerNo;
	}

	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}

	public String getCutomerOrderCode() {
		return cutomerOrderCode;
	}

	public void setCutomerOrderCode(String cutomerOrderCode) {
		this.cutomerOrderCode = cutomerOrderCode;
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

	public String getOrderId() {
		return orderCode;
	}

	public void setOrderId(String orderId) {
		this.orderCode = orderId;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}

	@Override
	public String toString() {
		return "DpayQueryResBean [customerNo=" + customerNo + ", cutomerOrderCode=" + cutomerOrderCode + ", responseCode=" + responseCode + ", responseMsg="
				+ responseMsg + ", orderCode=" + orderCode + ", amount=" + amount + ", description=" + description + ", orderTime=" + orderTime
				+ "]";
	}
	
	public DpayQueryResBean convertResCode(Exception e, DpayQueryResBean dpayQueryResBean) {
		if (e instanceof DpayException) {
			dpayQueryResBean.setResponseCode(((DpayException) e).getCode());
			dpayQueryResBean.setResponseMsg(DpayExceptionEnum.getMessage(dpayQueryResBean.getResponseCode()));
		} else {
			dpayQueryResBean.setResponseCode(DpayExceptionEnum.SYSERR.getCode());
			dpayQueryResBean.setResponseMsg(DpayExceptionEnum.SYSERR.getMessage());
		}
		return dpayQueryResBean;
	}

}
