package com.yl.payinterface.core.bean;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * 钱包支付交易业务处理Bean
 * 
 * @author 聚合支付有限公司
 * @since 2016年4月24日
 * @version V1.0.0
 */
public class WalletSalesTradeBean implements Serializable {

	private static final long serialVersionUID = -3758549242410013166L;

	/** 业务请求的支付接口编号 */
	@NotNull
	private String interfaceCode;
	/** 业务请求的支付接口提供方编号 */
	@NotNull
	private String InterfaceProviderCode;
	/** 支付订单编号 */
	@NotNull
	private String businessOrderID;
	/** 业务订单编号 */
	@NotNull
	private String bussinessFlowID;
	/** 订单金额 */
	@Digits(integer = 10, fraction = 2)
	private double amount;
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
	/** 卡类型 */
	private String cardType;
	/** 商品名称 */
	private String productName;

	/** IP */
	private String clientIp;
	/** 引用 */
	private String referer;

	/** 拥有者角色 */
	private String ownerRole;
	/** 拥有者ID */
	private String ownerID;
	/** 下单返回地址 */
	private String orderBackUrl;
	
	/** 授权编号 */
	private String authCode;

	public String getInterfaceCode() {
		return interfaceCode;
	}

	public void setInterfaceCode(String interfaceCode) {
		this.interfaceCode = interfaceCode;
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

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
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

	public String getOwnerRole() {
		return ownerRole;
	}

	public void setOwnerRole(String ownerRole) {
		this.ownerRole = ownerRole;
	}

	public String getOwnerID() {
		return ownerID;
	}

	public void setOwnerID(String ownerID) {
		this.ownerID = ownerID;
	}

	public String getOrderBackUrl() {
		return orderBackUrl;
	}

	public void setOrderBackUrl(String orderBackUrl) {
		this.orderBackUrl = orderBackUrl;
	}

	public String getAuthCode() {
		return authCode;
	}

	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}

	public String getBussinessFlowID() {
		return bussinessFlowID;
	}

	public void setBussinessFlowID(String bussinessFlowID) {
		this.bussinessFlowID = bussinessFlowID;
	}

	@Override
	public String toString() {
		return "WalletSalesTradeBean [interfaceCode=" + interfaceCode
				+ ", InterfaceProviderCode=" + InterfaceProviderCode
				+ ", businessOrderID=" + businessOrderID + ", bussinessFlowID="
				+ bussinessFlowID + ", amount=" + amount
				+ ", businessOrderTime=" + businessOrderTime + ", tradeType="
				+ tradeType + ", businessOrderMessage=" + businessOrderMessage
				+ ", originalBusinessOrderID=" + originalBusinessOrderID
				+ ", cardType=" + cardType + ", productName=" + productName
				+ ", clientIp=" + clientIp + ", referer=" + referer
				+ ", ownerRole=" + ownerRole + ", ownerID=" + ownerID
				+ ", orderBackUrl=" + orderBackUrl + ", authCode=" + authCode
				+ "]";
	}

}
