package com.yl.pay.pos.bean;

import java.io.Serializable;

/**
 * 代付bean
 * @author sihe.li
 *
 */
public class ProxyPayBean implements Serializable{

	
	/** 商户号 **/
	private String bankCustomerNo; 
	/** POS交易流水号 **/
	private String sysFlowNo;	
	/** 交易卡号 **/
	private String pan;
	/** 交易金额 **/
	private String transAmount;
	/** 交易商户 **/
	private String cumstomerNo;
	/** 收款行姓名 **/
	private String custName;
	/** 收款行卡号 **/
	private String custCardNo;
	/** 收款人收款金额 **/
	private String custAmount;
	/** 收款人银行名称 **/
	private String issName;
	/** 收款人开户行行号 **/
	private String issNo;
	/** 订单号 **/
	private String orderNo;
	/** 代付推送地址 **/
	private String notifyUrl;
	/** 验证信息 **/
	private String signCode;

	public String getBankCustomerNo() {
		return bankCustomerNo;
	}
	public void setBankCustomerNo(String bankCustomerNo) {
		this.bankCustomerNo = bankCustomerNo;
	}
	public String getSysFlowNo() {
		return sysFlowNo;
	}
	public void setSysFlowNo(String sysFlowNo) {
		this.sysFlowNo = sysFlowNo;
	}
	public String getPan() {
		return pan;
	}
	public void setPan(String pan) {
		this.pan = pan;
	}
	public String getTransAmount() {
		return transAmount;
	}
	public void setTransAmount(String transAmount) {
		this.transAmount = transAmount;
	}
	public String getCumstomerNo() {
		return cumstomerNo;
	}
	public void setCumstomerNo(String cumstomerNo) {
		this.cumstomerNo = cumstomerNo;
	}
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	public String getCustCardNo() {
		return custCardNo;
	}
	public void setCustCardNo(String custCardNo) {
		this.custCardNo = custCardNo;
	}
	public String getCustAmount() {
		return custAmount;
	}
	public void setCustAmount(String custAmount) {
		this.custAmount = custAmount;
	}
	public String getIssName() {
		return issName;
	}
	public void setIssName(String issName) {
		this.issName = issName;
	}
	public String getIssNo() {
		return issNo;
	}
	public void setIssNo(String issNo) {
		this.issNo = issNo;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getNotifyUrl() {
		return notifyUrl;
	}
	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}
	public String getSignCode() {
		return signCode;
	}
	public void setSignCode(String signCode) {
		this.signCode = signCode;
	}
	
	
	
}
