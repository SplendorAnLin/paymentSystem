package com.yl.online.model.model;

import java.util.Date;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.URL;

import com.yl.online.model.enums.BusinessType;
import com.yl.online.model.enums.OrderClearingStatus;
import com.yl.online.model.enums.OrderStatus;
import com.yl.online.model.enums.PartnerRole;
import com.yl.online.model.enums.PayType;
import com.yl.online.model.enums.RefundStatus;
import com.yl.online.model.enums.RepeatFlag;
import com.yl.online.model.enums.SupportRefundHandleType;
import com.yl.online.model.enums.SupportRefundType;

/**
 * 订单
 * 
 * @author 聚合支付有限公司
 * @since 2016年7月17日
 * @version V1.0.0
 */
public class Order extends AutoStringIDModel {

	private static final long serialVersionUID = 6016225134848366883L;
	/** 业务类型 */
	@NotNull
	private BusinessType businessType;
	/** 业务标志1 */
	private String businessFlag1;
	/** 业务标志2 */
	private String businessFlag2;
	/** 请求订单号 */
	@NotNull
	private String requestCode;
	/** 付款方角色 */
	@NotNull
	private PartnerRole payerRole;
	/** 付款方 */
	@NotNull
	private String payer;
	/** 收款方角色 */
	@NotNull
	private PartnerRole receiverRole;
	/** 收款方 */
	@NotNull
	private String receiver;
	/** 订单重复支付标志 **/
	private RepeatFlag repeatFlag;
	/** 币种 */
	@NotNull
	private String currency;
	/** 订单金额 */
	@Digits(integer = 12, fraction = 2)
	private Double amount;
	/** 实付金额 */
	@Digits(integer = 12, fraction = 2)
	private double paidAmount = 0;
	/** 付款方总手续费 */
	@Digits(integer = 12, fraction = 2)
	private Double payerFee;
	/** 收款方总手续费 */
	@Digits(integer = 12, fraction = 2)
	private Double receiverFee;
	/** 交易成本 */
	@Digits(integer = 12, fraction = 2)
	private Double cost;
	/** 订单状态 */
	@NotNull
	private OrderStatus status;
	/** 清算状态 */
	@NotNull
	private OrderClearingStatus clearingStatus = OrderClearingStatus.UN_CLEARING;
	/** 清算完成时间 */
	private Date clearingFinishTime;
	/** 下单时间 */
	@NotNull
	private Date orderTime;
	/** 超时时间 */
	@NotNull
	private Date timeout;
	/** 关闭时间 */
	private Date closeTime;

	/** 成功支付时间 */
	private Date successPayTime;

	/** 退款状态 */
	@NotNull
	private RefundStatus refundStatus = RefundStatus.NOT_REFUND;
	/** 已退款总额 */
	private double refundAmount = 0;
	/** 可退金额 */
	private double refundableAmount = 0;
	/** 支持的退款方式 */
	private SupportRefundType supportRefundType = SupportRefundType.PART_REFUND;
	/** 支持的退款处理方式 */
	private SupportRefundHandleType supportRefundHandleType;

	/** 页面重定向URL */
	@URL
	private String redirectURL;
	/** 后台通知URL */
	@URL
	private String notifyURL;
	
	/** 商品信息 */
	private String products;
	
	/** 支付类型  */
	private PayType payType;
	/** 快捷代付手续费 */
	@Digits(integer = 12, fraction = 2)
	private Double remitFee;

	public BusinessType getBusinessType() {
		return businessType;
	}

	public void setBusinessType(BusinessType businessType) {
		this.businessType = businessType;
	}

	public String getBusinessFlag1() {
		return businessFlag1;
	}

	public void setBusinessFlag1(String businessFlag1) {
		this.businessFlag1 = businessFlag1;
	}

	public String getBusinessFlag2() {
		return businessFlag2;
	}

	public void setBusinessFlag2(String businessFlag2) {
		this.businessFlag2 = businessFlag2;
	}

	public String getRequestCode() {
		return requestCode;
	}

	public void setRequestCode(String requestCode) {
		this.requestCode = requestCode;
	}

	public PartnerRole getPayerRole() {
		return payerRole;
	}

	public void setPayerRole(PartnerRole payerRole) {
		this.payerRole = payerRole;
	}

	public String getPayer() {
		return payer;
	}

	public void setPayer(String payer) {
		this.payer = payer;
	}

	public PartnerRole getReceiverRole() {
		return receiverRole;
	}

	public void setReceiverRole(PartnerRole receiverRole) {
		this.receiverRole = receiverRole;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public RepeatFlag getRepeatFlag() {
		return repeatFlag;
	}

	public void setRepeatFlag(RepeatFlag repeatFlag) {
		this.repeatFlag = repeatFlag;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public double getPaidAmount() {
		return paidAmount;
	}

	public void setPaidAmount(double paidAmount) {
		this.paidAmount = paidAmount;
	}

	public Double getPayerFee() {
		return payerFee;
	}

	public void setPayerFee(Double payerFee) {
		this.payerFee = payerFee;
	}

	public Double getReceiverFee() {
		return receiverFee;
	}

	public void setReceiverFee(Double receiverFee) {
		this.receiverFee = receiverFee;
	}

	public Double getCost() {
		return cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}

	public OrderStatus getStatus() {
		return status;
	}

	public void setStatus(OrderStatus status) {
		this.status = status;
	}

	public OrderClearingStatus getClearingStatus() {
		return clearingStatus;
	}

	public void setClearingStatus(OrderClearingStatus clearingStatus) {
		this.clearingStatus = clearingStatus;
	}

	public Date getClearingFinishTime() {
		return clearingFinishTime;
	}

	public void setClearingFinishTime(Date clearingFinishTime) {
		this.clearingFinishTime = clearingFinishTime;
	}

	public Date getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}

	public Date getTimeout() {
		return timeout;
	}

	public void setTimeout(Date timeout) {
		this.timeout = timeout;
	}

	public Date getCloseTime() {
		return closeTime;
	}

	public void setCloseTime(Date closeTime) {
		this.closeTime = closeTime;
	}

	public Date getSuccessPayTime() {
		return successPayTime;
	}

	public void setSuccessPayTime(Date successPayTime) {
		this.successPayTime = successPayTime;
	}

	public RefundStatus getRefundStatus() {
		return refundStatus;
	}

	public void setRefundStatus(RefundStatus refundStatus) {
		this.refundStatus = refundStatus;
	}

	public double getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(double refundAmount) {
		this.refundAmount = refundAmount;
	}

	public double getRefundableAmount() {
		return refundableAmount;
	}

	public void setRefundableAmount(double refundableAmount) {
		this.refundableAmount = refundableAmount;
	}

	public SupportRefundType getSupportRefundType() {
		return supportRefundType;
	}

	public void setSupportRefundType(SupportRefundType supportRefundType) {
		this.supportRefundType = supportRefundType;
	}

	public SupportRefundHandleType getSupportRefundHandleType() {
		return supportRefundHandleType;
	}

	public void setSupportRefundHandleType(SupportRefundHandleType supportRefundHandleType) {
		this.supportRefundHandleType = supportRefundHandleType;
	}

	public String getRedirectURL() {
		return redirectURL;
	}

	public void setRedirectURL(String redirectURL) {
		this.redirectURL = redirectURL;
	}

	public String getNotifyURL() {
		return notifyURL;
	}

	public void setNotifyURL(String notifyURL) {
		this.notifyURL = notifyURL;
	}

	public String getProducts() {
		return products;
	}

	public void setProducts(String products) {
		this.products = products;
	}

	public PayType getPayType() {
		return payType;
	}

	public void setPayType(PayType payType) {
		this.payType = payType;
	}

	public Double getRemitFee() {
		return remitFee;
	}

	public void setRemitFee(Double remitFee) {
		this.remitFee = remitFee;
	}
}
