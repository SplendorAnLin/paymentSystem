package com.yl.online.model.bean;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.yl.online.model.enums.PayType;
import com.yl.online.model.enums.PaymentStatus;

/**
 * 支付记录Bean
 * 
 * @author 聚合支付有限公司
 * @since 2016年7月13日
 * @version V1.0.0
 */
public class Payment implements Serializable {

	private static final long serialVersionUID = 223450459059135428L;
	/** 主键 */
	private String id;
	/** 编号 */
	private String code;
	/** 订单编号 */
	private String orderCode;
	/** 支付方式 */
	private PayType payType;
	/** 支付时间 */
	@NotNull
	private Date payTime;
	/** 状态 */
	@NotNull
	private PaymentStatus status;

	/** 支付金额 */
	@NotNull
	@Digits(integer = 12, fraction = 2)
	private double amount;
	/** 收款方手续费 */
	@Digits(integer = 12, fraction = 2)
	private Double receiverFee;
	@Digits(integer = 12, fraction = 2)
	private Double payerFee;
	@Digits(integer = 12, fraction = 2)
	private Double remitFee;
	/** 支付接口编号 */
	@NotNull
	private String payinterface;
	/** 支付接口订单号 */
	private String payinterfaceRequestId;
	/** 资金通道流水号 */
	private String payinterfaceOrder;
	/** 接口成本 */
	private Double payinterfaceFee;

	/** 支付完成时间 */
	private Date completeTime;
	/** 支付接口返回交易时间 */
	private Date returnTradeTime;
	/** 支付接口返回码 */
	private String responseCode;
	/** 支付接口返回信息 */
	private String responseInfo;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public PayType getPayType() {
		return payType;
	}

	public void setPayType(PayType payType) {
		this.payType = payType;
	}

	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}

	public PaymentStatus getStatus() {
		return status;
	}

	public void setStatus(PaymentStatus status) {
		this.status = status;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Double getReceiverFee() {
		return receiverFee;
	}

	public void setReceiverFee(Double receiverFee) {
		this.receiverFee = receiverFee;
	}

	public String getPayinterface() {
		return payinterface;
	}

	public void setPayinterface(String payinterface) {
		this.payinterface = payinterface;
	}

	public String getPayinterfaceRequestId() {
		return payinterfaceRequestId;
	}

	public void setPayinterfaceRequestId(String payinterfaceRequestId) {
		this.payinterfaceRequestId = payinterfaceRequestId;
	}

	public String getPayinterfaceOrder() {
		return payinterfaceOrder;
	}

	public void setPayinterfaceOrder(String payinterfaceOrder) {
		this.payinterfaceOrder = payinterfaceOrder;
	}

	public Double getPayinterfaceFee() {
		return payinterfaceFee;
	}

	public void setPayinterfaceFee(Double payinterfaceFee) {
		this.payinterfaceFee = payinterfaceFee;
	}

	public Date getCompleteTime() {
		return completeTime;
	}

	public void setCompleteTime(Date completeTime) {
		this.completeTime = completeTime;
	}

	public Date getReturnTradeTime() {
		return returnTradeTime;
	}

	public void setReturnTradeTime(Date returnTradeTime) {
		this.returnTradeTime = returnTradeTime;
	}

	public String getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	public String getResponseInfo() {
		return responseInfo;
	}

	public void setResponseInfo(String responseInfo) {
		this.responseInfo = responseInfo;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Double getPayerFee() {
		return payerFee;
	}

	public void setPayerFee(Double payerFee) {
		this.payerFee = payerFee;
	}

	public Double getRemitFee() {
		return remitFee;
	}

	public void setRemitFee(Double remitFee) {
		this.remitFee = remitFee;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
