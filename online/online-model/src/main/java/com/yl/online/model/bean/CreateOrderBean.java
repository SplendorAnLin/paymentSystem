package com.yl.online.model.bean;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.yl.online.model.enums.BusinessType;
import com.yl.online.model.enums.PartnerRole;
import com.yl.online.model.enums.PayType;

/**
 * 创建交易订单Bean
 * 
 * @author 聚合支付有限公司
 * @since 2016年7月13日
 * @version V1.0.0
 */
public class CreateOrderBean implements Serializable {

	private static final long serialVersionUID = -8328696273847710291L;
	/** 业务类型 */
	@NotNull
	private BusinessType businessType;
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
	/** 合作方唯一订单号 */
	@NotNull
	private String outOrderId;
	/** 币种 */
	private String currency;
	/** 金额 */
	private Double amount;
	/** 接口编号 */
	private String interfaceCode;
	/** 后台通知URL */
	private String notifyURL;
	/** 超时时间 */
	private Date timeout;
	/** 业务扩展参数 */
	private String extParam;
	/** 客户端IP */
	private String clientIP;
	/** 支付类型 */
	private PayType payType;

	public BusinessType getBusinessType() {
		return businessType;
	}

	public void setBusinessType(BusinessType businessType) {
		this.businessType = businessType;
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

	public String getOutOrderId() {
		return outOrderId;
	}

	public void setOutOrderId(String outOrderId) {
		this.outOrderId = outOrderId;
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

	public String getInterfaceCode() {
		return interfaceCode;
	}

	public void setInterfaceCode(String interfaceCode) {
		this.interfaceCode = interfaceCode;
	}

	public String getNotifyURL() {
		return notifyURL;
	}

	public void setNotifyURL(String notifyURL) {
		this.notifyURL = notifyURL;
	}

	public Date getTimeout() {
		return timeout;
	}

	public void setTimeout(Date timeout) {
		this.timeout = timeout;
	}

	public String getExtParam() {
		return extParam;
	}

	public void setExtParam(String extParam) {
		this.extParam = extParam;
	}

	public String getClientIP() {
		return clientIP;
	}

	public void setClientIP(String clientIP) {
		this.clientIP = clientIP;
	}

	public PayType getPayType() {
		return payType;
	}

	public void setPayType(PayType payType) {
		this.payType = payType;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
