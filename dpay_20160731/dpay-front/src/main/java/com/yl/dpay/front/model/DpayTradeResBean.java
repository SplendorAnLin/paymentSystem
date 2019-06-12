package com.yl.dpay.front.model;

import java.io.Serializable;

import com.yl.dpay.front.common.DpayException;
import com.yl.dpay.hessian.enums.DpayExceptionEnum;

/**
 * 代付下单响应bean
 * 
 * @author 聚合支付有限公司
 * @since 2016年5月22日
 * @version V1.0.0
 */
public class DpayTradeResBean implements Serializable {

	private static final long serialVersionUID = 6657322955004362102L;

	/**
	 * 商户订单号
	 */
	private String customerOrderCode;

	/**
	 * 代付订单号
	 */
	private String orderCode;

	/**
	 * 状态码
	 */
	private String responseCode;

	/**
	 * 状态信息
	 */
	private String responseMsg;

	/**
	 * 订单处理时间
	 */
	private String orderTime;

	/**
	 * 商户参数
	 */
	private String customerParam;

	public String getCustomerOrderCode() {
		return customerOrderCode;
	}

	public void setCustomerOrderCode(String customerOrderCode) {
		this.customerOrderCode = customerOrderCode;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
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

	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}

	public String getCustomerParam() {
		return customerParam;
	}

	public void setCustomerParam(String customerParam) {
		this.customerParam = customerParam;
	}
	
	@Override
	public String toString() {
		return "DpayTradeResBean [customerOrderCode=" + customerOrderCode
				+ ", orderCode=" + orderCode + ", responseCode=" + responseCode
				+ ", responseMsg=" + responseMsg + ", orderTime=" + orderTime
				+ ", customerParam=" + customerParam + "]";
	}

	public DpayTradeResBean convertResCode(Exception e, DpayTradeResBean dpayTradeResBean) {
		if (e instanceof DpayException) {
			dpayTradeResBean.setResponseCode(((DpayException) e).getCode());
			dpayTradeResBean.setResponseMsg(DpayExceptionEnum.getMessage(dpayTradeResBean.getResponseCode()));
		} else {
			dpayTradeResBean.setResponseCode(DpayExceptionEnum.SYSERR.getCode());
			dpayTradeResBean.setResponseMsg(DpayExceptionEnum.SYSERR.getMessage());
		}
		return dpayTradeResBean;
	}
}
