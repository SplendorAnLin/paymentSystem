package com.yl.receive.front.model;

import java.io.Serializable;

import com.yl.receive.front.common.ReceiveCodeEnum;
import com.yl.receive.front.common.ReceiveException;

/**
 * 代收下单响应bean
 * 
 * @author 聚合支付有限公司
 * @since 2016年10月17日
 * @version V1.0.0
 */
public class TradeResponse implements Serializable {

	private static final long serialVersionUID = 6657322955004362102L;

	/**
	 * 商户订单号
	 */
	private String customerOrderCode;

	/**
	 * 代收订单号
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
	
	public TradeResponse convertResCode(Exception e, TradeResponse tradeResponse) {
		if (e instanceof ReceiveException) {
			tradeResponse.setResponseCode(((ReceiveException) e).getCode());
			tradeResponse.setResponseMsg(ReceiveCodeEnum.getMessage(tradeResponse.getResponseCode()));
		} else {
			if(e instanceof com.yl.receive.hessian.exception.ReceiveException && ((com.yl.receive.hessian.exception.ReceiveException) e).getCode().equals("1006")){
				tradeResponse.setResponseCode(((com.yl.receive.hessian.exception.ReceiveException) e).getCode());
				tradeResponse.setResponseMsg(((com.yl.receive.hessian.exception.ReceiveException) e).getMessage());
			}else{
				tradeResponse.setResponseCode(ReceiveCodeEnum.SYS_ERROR.getCode());
				tradeResponse.setResponseMsg(ReceiveCodeEnum.SYS_ERROR.getMessage());
			}
		}
		return tradeResponse;
	}
}
