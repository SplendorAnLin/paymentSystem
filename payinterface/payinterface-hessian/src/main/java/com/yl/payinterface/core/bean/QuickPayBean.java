package com.yl.payinterface.core.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * 认证持卡人Bean
 * 
 * @author 聚合支付有限公司
 * @since 2016年4月24日
 * @version V1.0.0
 */
public class QuickPayBean implements Serializable {

	private static final long serialVersionUID = -328215463361387292L;
	/** id */

	private long id;
	/** 银行卡号 */
	private String bankCardNo;
	/** 有效期 */
	private String validityDay;
	/** CVN2码 */
	private String cvv;
	/** 姓名 */
	private String payerName;
	/** 身份证号 */
	private String certNo;
	/** 绑定手机号 */
	private String payerMobNo;
	/** 支付订单号 */
	private String merOrderId;
	/** 接口编号 */
	private String interfaceCode;
	/** 金额 */
	private double amount;
	/** 银行 */
	private String bankCode;
	/** 创建时间 */
	private Date createTime;
	/** 交易订单号 */
	private String orderCode;
	/** 交易流水号 */
	private String paymentCode;
	/** 提供方编号 */
	private String providerCode;
	/** 商户编号 */
	private String ownerId;
	/** 商品名称 */
	private String productName;
	/** token **/
	private String token;
	/** 短信验证码 **/
	private String verifyCode;
	/** 结算金额 */
	private Double settleAmount;
	/** 结算卡开户行 */
	private Double quickPayFee;
	/** 结算卡开户行 */
	private Double remitFee;
	/** 结算卡开户行 FEE，AMOUNT */
	private String settleType;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getBankCardNo() {
		return bankCardNo;
	}

	public void setBankCardNo(String bankCardNo) {
		this.bankCardNo = bankCardNo;
	}

	public String getValidityDay() {
		return validityDay;
	}

	public void setValidityDay(String validityDay) {
		this.validityDay = validityDay;
	}

	public String getPayerName() {
		return payerName;
	}

	public void setPayerName(String payerName) {
		this.payerName = payerName;
	}

	public String getCertNo() {
		return certNo;
	}

	public void setCertNo(String certNo) {
		this.certNo = certNo;
	}

	public String getPayerMobNo() {
		return payerMobNo;
	}

	public void setPayerMobNo(String payerMobNo) {
		this.payerMobNo = payerMobNo;
	}

	public String getMerOrderId() {
		return merOrderId;
	}

	public void setMerOrderId(String merOrderId) {
		this.merOrderId = merOrderId;
	}

	public String getInterfaceCode() {
		return interfaceCode;
	}

	public void setInterfaceCode(String interfaceCode) {
		this.interfaceCode = interfaceCode;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCvv() {
		return cvv;
	}

	public void setCvv(String cvv) {
		this.cvv = cvv;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public String getPaymentCode() {
		return paymentCode;
	}

	public void setPaymentCode(String paymentCode) {
		this.paymentCode = paymentCode;
	}

	public String getProviderCode() {
		return providerCode;
	}

	public void setProviderCode(String providerCode) {
		this.providerCode = providerCode;
	}

	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getVerifyCode() {
		return verifyCode;
	}

	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}

	public Double getSettleAmount() {
		return settleAmount;
	}

	public void setSettleAmount(Double settleAmount) {
		this.settleAmount = settleAmount;
	}

	public Double getQuickPayFee() {
		return quickPayFee;
	}

	public void setQuickPayFee(Double quickPayFee) {
		this.quickPayFee = quickPayFee;
	}

	public Double getRemitFee() {
		return remitFee;
	}

	public void setRemitFee(Double remitFee) {
		this.remitFee = remitFee;
	}

	public String getSettleType() {
		return settleType;
	}

	public void setSettleType(String settleType) {
		this.settleType = settleType;
	}

	@Override
	public String toString() {
		return "QuickPayBean [id=" + id + ", bankCardNo=" + bankCardNo + ", validityDay=" + validityDay + ", cvv=" + cvv + ", payerName=" + payerName + ", certNo=" + certNo + ", payerMobNo=" + payerMobNo + ", merOrderId=" + merOrderId + ", interfaceCode=" + interfaceCode + ", amount=" + amount
				+ ", bankCode=" + bankCode + ", createTime=" + createTime + ", orderCode=" + orderCode + ", paymentCode=" + paymentCode + ", providerCode=" + providerCode + ", ownerId=" + ownerId + ", productName=" + productName + ", token=" + token + ", verifyCode=" + verifyCode
				+ ", settleAmount=" + settleAmount + ", quickPayFee=" + quickPayFee + ", remitFee=" + remitFee + ", settleType=" + settleType + "]";
	}

}
