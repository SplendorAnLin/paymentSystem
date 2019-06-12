package com.yl.payinterface.core.bean;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * 认证支付业务处理Bean
 * 
 * @author 聚合支付有限公司
 * @since 2016年4月24日
 * @version V1.0.0
 */
public class AuthPayTradeBean implements Serializable {

	private static final long serialVersionUID = 6482649410383887064L;

	/** 业务请求的支付接口编号 */
	@NotNull
	private String interfaceCode;
	/** 业务请求的支付接口提供方编号 */
	@NotNull
	private String InterfaceProviderCode;
	/** 业务编号 */
	private String businessCode;
	/** 业务订单编号 */
	@NotNull
	private String businessOrderID;
	/** 订单金额 */
	@Digits(integer = 10, fraction = 2)
	private double amount;
	/** 币种 */
	@NotNull
	private String currency;
	/** 业务订单时间 */
	@NotNull
	private Date businessOrderTime;
	@NotNull
	@Pattern(regexp = "PAY|QUERY")
	private String tradeType;
	/** 业务订单信息 */
	private String businessOrderMessage;
	/** 原业务订单号（针对退款） */
	private String originalBusinessOrderID;

	/** 分期付款期数 1、3、6、9、12、18、24;1代表全额付款 */
	private String installmentTimes;
	/** 卡类型 */
	private String cardType;

	/** 虚拟商品 0 实物商品标志位 1 */
	private String productType;
	/** 商品名称 */
	private String productName;
	/** 商品单价 */
	private String productPrice;
	/** 商品数量 */
	private String productNumber;
	/** 商品描述 */
	private String productDescription;

	/** IP */
	private String clientIp;
	/** 引用 */
	private String referer;

	public String getInterfaceCode() {
		return interfaceCode;
	}

	public void setInterfaceCode(String interfaceCode) {
		this.interfaceCode = interfaceCode;
	}

	public String getBusinessCode() {
		return businessCode;
	}

	public void setBusinessCode(String businessCode) {
		this.businessCode = businessCode;
	}

	public String getBusinessOrderID() {
		return businessOrderID;
	}

	public void setBusinessOrderID(String businessOrderID) {
		this.businessOrderID = businessOrderID;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getTradeType() {
		return tradeType;
	}

	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}

	public Date getBusinessOrderTime() {
		return businessOrderTime;
	}

	public void setBusinessOrderTime(Date businessOrderTime) {
		this.businessOrderTime = businessOrderTime;
	}

	public String getBusinessOrderMessage() {
		return businessOrderMessage;
	}

	public void setBusinessOrderMessage(String businessOrderMessage) {
		this.businessOrderMessage = businessOrderMessage;
	}

	public String getOriginalBusinessOrderID() {
		return originalBusinessOrderID;
	}

	public void setOriginalBusinessOrderID(String originalBusinessOrderID) {
		this.originalBusinessOrderID = originalBusinessOrderID;
	}

	public String getInstallmentTimes() {
		return installmentTimes;
	}

	public void setInstallmentTimes(String installmentTimes) {
		this.installmentTimes = installmentTimes;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
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

	public String getProductNumber() {
		return productNumber;
	}

	public void setProductNumber(String productNumber) {
		this.productNumber = productNumber;
	}

	public String getProductDescription() {
		return productDescription;
	}

	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}

	public String getClientIp() {
		return clientIp;
	}

	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}

	public String getReferer() {
		return referer;
	}

	public void setReferer(String referer) {
		this.referer = referer;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getInterfaceProviderCode() {
		return InterfaceProviderCode;
	}

	public void setInterfaceProviderCode(String interfaceProviderCode) {
		InterfaceProviderCode = interfaceProviderCode;
	}

	@Override
	public String toString() {
		return "AuthPayTradeBean [interfaceCode=" + interfaceCode + ", InterfaceProviderCode=" + InterfaceProviderCode + ", businessCode=" + businessCode
				+ ", businessOrderID=" + businessOrderID + ", amount=" + amount + ", currency=" + currency + ", businessOrderTime=" + businessOrderTime
				+ ", tradeType=" + tradeType + ", businessOrderMessage=" + businessOrderMessage + ", originalBusinessOrderID=" + originalBusinessOrderID
				+ ", installmentTimes=" + installmentTimes + ", cardType=" + cardType + ", productType=" + productType + ", productName=" + productName
				+ ", productPrice=" + productPrice + ", productNumber=" + productNumber + ", productDescription=" + productDescription + ", clientIp=" + clientIp
				+ ", referer=" + referer + "]";
	}

}
