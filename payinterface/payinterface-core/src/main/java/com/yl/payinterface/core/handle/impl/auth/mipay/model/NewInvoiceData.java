/******************************************************************************
 *                                                                             
 *                      Woodare PROPRIETARY INFORMATION                        
 *                                                                             
 *          The information contained herein is proprietary to Woodare         
 *           and shall not be reproduced or disclosed in whole or in part      
 *                    or used for any design or manufacture                    
 *              without direct written authorization from FengDa.              
 *                                                                             
 *            Copyright (c) 2013 by Woodare.  All rights reserved.             
 *                                                                             
 *****************************************************************************/
package com.yl.payinterface.core.handle.impl.auth.mipay.model;

import java.math.BigDecimal;

/**
 * 
 * ClassName: DownNoCardInvoiceData
 * 
 * @description
 * @author woo_maven_auto_generate
 * @Date 2017/01/09
 * 
 */
public class NewInvoiceData {

	/** 接口版本号 */
	private Integer versionNo;

	/** 交易金额 */
	private BigDecimal price;

	/** 备注 */
	private String description;

	/** 商户号 * */
	private String mchNo;

	/** 下游交易流水号 */
	private String tradeNo;

	/** 交易流水号 */
	private String transNo;

	/** 交易返回值 */
	private String tranStr;

	/** 订单时间 */
	private String orderDate;

	/** 通知地址 */
	private String notifyUrl;

	/** 交易状态 */
	private String status;

	/** 交易状态描述 */
	private String statusDesc;

	/** 回调地址 */
	private String callbackUrl;

	/** 代付状态 */
	private String qfStatus;

	/** 商户清算费率 */
	private BigDecimal downPayFee;

	/** 商户每笔代付费 */
	private BigDecimal downDrawFee;

	/** 商户清算金额 */
	private BigDecimal downRealPrice;

	/** 商户利润 */
	private BigDecimal profit;

	/** 收款人姓名 */
	private String accName;

	/** 收款卡银行 */
	private String bankName;

	/** 收款卡号 */
	private String cardNo;

	/** 联行行号 */
	private String bankCode;

	/** 收款人身份证号 */
	private String accIdCard;

	/** 收款人手机号 */
	private String accTel;

	/** 支付卡号 */
	private String payCardNo;

	/** 快捷授权Token */
	private String token;

	/** 短信验证码 */
	private String smsCode;

	public Integer getVersionNo() {
		return versionNo;
	}

	public void setVersionNo(Integer versionNo) {
		this.versionNo = versionNo;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getMchNo() {
		return mchNo;
	}

	public void setMchNo(String mchNo) {
		this.mchNo = mchNo;
	}

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	public String getTransNo() {
		return transNo;
	}

	public void setTransNo(String transNo) {
		this.transNo = transNo;
	}

	public String getTranStr() {
		return tranStr;
	}

	public void setTranStr(String tranStr) {
		this.tranStr = tranStr;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	public String getNotifyUrl() {
		return notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatusDesc() {
		return statusDesc;
	}

	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
	}

	public String getCallbackUrl() {
		return callbackUrl;
	}

	public void setCallbackUrl(String callbackUrl) {
		this.callbackUrl = callbackUrl;
	}

	public String getQfStatus() {
		return qfStatus;
	}

	public void setQfStatus(String qfStatus) {
		this.qfStatus = qfStatus;
	}

	public BigDecimal getDownRealPrice() {
		return downRealPrice;
	}

	public void setDownRealPrice(BigDecimal downRealPrice) {
		this.downRealPrice = downRealPrice;
	}

	public BigDecimal getProfit() {
		return profit;
	}

	public void setProfit(BigDecimal profit) {
		this.profit = profit;
	}

	public String getAccName() {
		return accName;
	}

	public void setAccName(String accName) {
		this.accName = accName;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getAccIdCard() {
		return accIdCard;
	}

	public void setAccIdCard(String accIdCard) {
		this.accIdCard = accIdCard;
	}

	public BigDecimal getDownPayFee() {
		return downPayFee;
	}

	public void setDownPayFee(BigDecimal downPayFee) {
		this.downPayFee = downPayFee;
	}

	public String getPayCardNo() {
		return payCardNo;
	}

	public void setPayCardNo(String payCardNo) {
		this.payCardNo = payCardNo;
	}

	public BigDecimal getDownDrawFee() {
		return downDrawFee;
	}

	public void setDownDrawFee(BigDecimal downDrawFee) {
		this.downDrawFee = downDrawFee;
	}

	public String getAccTel() {
		return accTel;
	}

	public void setAccTel(String accTel) {
		this.accTel = accTel;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getSmsCode() {
		return smsCode;
	}

	public void setSmsCode(String smsCode) {
		this.smsCode = smsCode;
	}
}
