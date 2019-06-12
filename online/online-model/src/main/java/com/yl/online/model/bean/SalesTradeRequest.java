package com.yl.online.model.bean;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.validator.constraints.URL;

/**
 * 普通消费交易请求Bean
 * 
 * @author 聚合支付有限公司
 * @since 2016年7月13日
 * @version V1.0.0
 */
public class SalesTradeRequest implements PartnerRequestInfo {
	private static final long serialVersionUID = -2612036212102226448L;
	/** 买方 */
	@NotNull
	private String buyer;
	/** 买方联系方式类型 */
	@NotNull
	@Pattern(regexp = "email|phone")
	private String buyerContactType;
	/** 买方联系方式 */
	@NotNull
	private String buyerContact;

	/** 支付方式 */
	@NotNull
	@Pattern(regexp = "ALL|B2C|B2B|B2C-DEBIT|B2C-CREDIT|OFFLINE")
	private String paymentType;
	/** 支付接口编号 */
	private String interfaceCode;
	/** 同一订单是否可重复提交 */
	@NotNull
	@Pattern(regexp = "TRUE|FALSE")
	private String retryFalg;

	/** 订单提交时间 */
	@NotNull
	private String submitTime;
	/** 订单超时时间 */
	@NotNull
	@Pattern(regexp = "\\d{0,5}M|\\d{1,3}H|\\d{1,2}D")
	private String timeout;
	/** 用户IP */
	private String clientIP;
	/** 页面重定向URL */
	@URL
	private String redirectURL;
	/** 后台通知URL */
	@URL
	private String notifyURL;

	/** 商品名称 */
	private String productName;
	/** 商品单价 */
	private String productPrice;
	/** 商品数量 */
	private String productNum;
	/** 商品描述 */
	private String productDesc;
	/** 商品展示URL */
	@URL
	private String productURL;

	public String getBuyer() {
		return buyer;
	}

	public void setBuyer(String buyer) {
		this.buyer = buyer;
	}

	public String getBuyerContactType() {
		return buyerContactType;
	}

	public void setBuyerContactType(String buyerContactType) {
		this.buyerContactType = buyerContactType;
	}

	public String getBuyerContact() {
		return buyerContact;
	}

	public void setBuyerContact(String buyerContact) {
		this.buyerContact = buyerContact;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public String getInterfaceCode() {
		return interfaceCode;
	}

	public void setInterfaceCode(String interfaceCode) {
		this.interfaceCode = interfaceCode;
	}

	public String getRetryFalg() {
		return retryFalg;
	}

	public void setRetryFalg(String retryFalg) {
		this.retryFalg = retryFalg;
	}

	public String getSubmitTime() {
		return submitTime;
	}

	public void setSubmitTime(String submitTime) {
		this.submitTime = submitTime;
	}

	public String getTimeout() {
		return timeout;
	}

	public void setTimeout(String timeout) {
		this.timeout = timeout;
	}

	public String getClientIP() {
		return clientIP;
	}

	public void setClientIP(String clientIP) {
		this.clientIP = clientIP;
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

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(String productPrice) {
		this.productPrice = productPrice;
	}

	public String getProductNum() {
		return productNum;
	}

	public void setProductNum(String productNum) {
		this.productNum = productNum;
	}

	public String getProductDesc() {
		return productDesc;
	}

	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
	}

	public String getProductURL() {
		return productURL;
	}

	public void setProductURL(String productURL) {
		this.productURL = productURL;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
