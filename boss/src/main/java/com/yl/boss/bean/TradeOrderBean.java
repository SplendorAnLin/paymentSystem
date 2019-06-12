package com.yl.boss.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.yl.boss.enums.BusinessType;
import com.yl.boss.enums.OrderType;
import com.yl.boss.enums.PartnerRole;
import com.yl.boss.enums.RepeatFlag;

/**
 * 交易订单Bean
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月8日
 * @version V1.0.0
 */
public class TradeOrderBean implements Serializable {

	private static final long serialVersionUID = 6016225134848366883L;
	/** 主键 */
	private Long id;
	/** 编号 */
	private String code;
	/** 版本号 */
	private Long version;
	/** 创建时间 */
	private Date createTime;
	/** 订单类型 */
	private OrderType orderType;
	/** 业务类型 */
	private BusinessType businessType;
	/** 业务标志1 */
	private String businessFlag1;
	/** 业务标志2 */
	private String businessFlag2;
	/** 付款方角色 */
	private PartnerRole payerRole;
	/** 付款方 */
	private String payer;
	/** 收款方角色 */
	private PartnerRole receiverRole;
	/** 收款方 */
	private String receiver;
	/** 订单状态 */
	private String status;
	/** 清分状态 */
	private String clearingStatus;
	/** 订单重复支付标志 **/
	private RepeatFlag repeatFlag;
	/** 请求订单号 */
	private String requestCode;
	/** 币种 */
	private String currency;
	/** 金额 */
	private BigDecimal amount;
	/** 已退款总额 */
	private BigDecimal refundTotalAmount = BigDecimal.ZERO;
	/** 可退金额 */
	private BigDecimal refundableAmount = BigDecimal.ZERO;
	/** 银行列表 **/
	private String bankCodes;
	/** 退款次数 */
	private int refundCount = 0;
	/** 退款总次数 */
	private int refundTotalCount = 0;
	/** 退款成功次数 */
	private int refundSuccessCount = 0;
	/** 最后一次退款时间 */
	private Date lastRefundTime;
	/** 下单时间 */
	private Date orderTime;
	/** 下单日期 */
	private Date orderDate;
	/** 支付时间 */
	private Date payTime;
	/** 支付日期 */
	private Date payDate;
	/** 超时时间 */
	private Date timeout;
	/** 关闭时间 */
	private Date closeTime;
	/** 页面重定向URL */
	private String redirectURL;
	/** 后台通知URL */
	private String notifyURL;
	/** 通知结果 */
	private String notifyResult;
	/** 通知次数 */
	private int notifyCount;
	/** 客户端IP */
	private String clientIP;
	/** 处理程序所在的服务器IP */
	private String handlerServerIP;
	/** 处理程序所在的服务器主机名 */
	private String handlerServerHost;

	/** 支付流水号 */
	private String paymentCode;
	/** 支付接口订单号 */
	private String payinterfaceRequestId;
	/** 支付接口编号 */
	private String payinterface;
	/** 退款状态 */
	private String refundStatus;
	/** 支付方式 */
	private String interfaceType;
	/** 订单金额 */
	private String amountStart;
	/** 订单金额 */
	private String amountEnd;
	/** 支付时间 */
	private Date successPayTimeStart;
	/** 支付时间 */
	private Date successPayTimeEnd;
	/** 下单时间 */
	private Date orderTimeStart;
	/** 下单时间 */
	private Date orderTimeEnd;

	public OrderType getOrderType() {
		return orderType;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public void setOrderType(OrderType orderType) {
		this.orderType = orderType;
	}

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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public RepeatFlag getRepeatFlag() {
		return repeatFlag;
	}

	public void setRepeatFlag(RepeatFlag repeatFlag) {
		this.repeatFlag = repeatFlag;
	}

	public String getRequestCode() {
		return requestCode;
	}

	public void setRequestCode(String requestCode) {
		this.requestCode = requestCode;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getRefundTotalAmount() {
		return refundTotalAmount;
	}

	public void setRefundTotalAmount(BigDecimal refundTotalAmount) {
		this.refundTotalAmount = refundTotalAmount;
	}

	public BigDecimal getRefundableAmount() {
		return refundableAmount;
	}

	public void setRefundableAmount(BigDecimal refundableAmount) {
		this.refundableAmount = refundableAmount;
	}

	public String getBankCodes() {
		return bankCodes;
	}

	public void setBankCodes(String bankCodes) {
		this.bankCodes = bankCodes;
	}

	public int getRefundCount() {
		return refundCount;
	}

	public void setRefundCount(int refundCount) {
		this.refundCount = refundCount;
	}

	public int getRefundTotalCount() {
		return refundTotalCount;
	}

	public void setRefundTotalCount(int refundTotalCount) {
		this.refundTotalCount = refundTotalCount;
	}

	public int getRefundSuccessCount() {
		return refundSuccessCount;
	}

	public void setRefundSuccessCount(int refundSuccessCount) {
		this.refundSuccessCount = refundSuccessCount;
	}

	public Date getLastRefundTime() {
		return lastRefundTime;
	}

	public void setLastRefundTime(Date lastRefundTime) {
		this.lastRefundTime = lastRefundTime;
	}

	public Date getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}

	public Date getPayDate() {
		return payDate;
	}

	public void setPayDate(Date payDate) {
		this.payDate = payDate;
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

	public String getNotifyResult() {
		return notifyResult;
	}

	public void setNotifyResult(String notifyResult) {
		this.notifyResult = notifyResult;
	}

	public int getNotifyCount() {
		return notifyCount;
	}

	public void setNotifyCount(int notifyCount) {
		this.notifyCount = notifyCount;
	}

	public String getClientIP() {
		return clientIP;
	}

	public void setClientIP(String clientIP) {
		this.clientIP = clientIP;
	}

	public String getHandlerServerIP() {
		return handlerServerIP;
	}

	public void setHandlerServerIP(String handlerServerIP) {
		this.handlerServerIP = handlerServerIP;
	}

	public String getHandlerServerHost() {
		return handlerServerHost;
	}

	public void setHandlerServerHost(String handlerServerHost) {
		this.handlerServerHost = handlerServerHost;
	}

	public String getClearingStatus() {
		return clearingStatus;
	}

	public void setClearingStatus(String clearingStatus) {
		this.clearingStatus = clearingStatus;
	}

	public String getPaymentCode() {
		return paymentCode;
	}

	public void setPaymentCode(String paymentCode) {
		this.paymentCode = paymentCode;
	}

	public String getPayinterfaceRequestId() {
		return payinterfaceRequestId;
	}

	public void setPayinterfaceRequestId(String payinterfaceRequestId) {
		this.payinterfaceRequestId = payinterfaceRequestId;
	}

	public String getPayinterface() {
		return payinterface;
	}

	public void setPayinterface(String payinterface) {
		this.payinterface = payinterface;
	}

	public String getRefundStatus() {
		return refundStatus;
	}

	public void setRefundStatus(String refundStatus) {
		this.refundStatus = refundStatus;
	}

	public String getInterfaceType() {
		return interfaceType;
	}

	public void setInterfaceType(String interfaceType) {
		this.interfaceType = interfaceType;
	}

	public String getAmountStart() {
		return amountStart;
	}

	public void setAmountStart(String amountStart) {
		this.amountStart = amountStart;
	}

	public String getAmountEnd() {
		return amountEnd;
	}

	public void setAmountEnd(String amountEnd) {
		this.amountEnd = amountEnd;
	}

	public Date getSuccessPayTimeStart() {
		return successPayTimeStart;
	}

	public void setSuccessPayTimeStart(Date successPayTimeStart) {
		this.successPayTimeStart = successPayTimeStart;
	}

	public Date getSuccessPayTimeEnd() {
		return successPayTimeEnd;
	}

	public void setSuccessPayTimeEnd(Date successPayTimeEnd) {
		this.successPayTimeEnd = successPayTimeEnd;
	}

	public Date getOrderTimeStart() {
		return orderTimeStart;
	}

	public void setOrderTimeStart(Date orderTimeStart) {
		this.orderTimeStart = orderTimeStart;
	}

	public Date getOrderTimeEnd() {
		return orderTimeEnd;
	}

	public void setOrderTimeEnd(Date orderTimeEnd) {
		this.orderTimeEnd = orderTimeEnd;
	}

}
