package com.yl.pay.pos.entity;

import com.yl.pay.pos.enums.*;

import javax.persistence.*;
import java.util.Date;

/**
 * Title: 银行请求信息
 * Description: 
 * Copyright: Copyright (c)2011
 * Company: com.yl.pay
 * @author haitao.liu
 */

@Entity
@Table(name = "BANK_REQUEST")
public class BankRequest extends BaseEntity{

	private TransType transType;			//交易类型
	private String pan;						//卡号
	private String posCati;					//POS终端号
	private Double amount;					//交易金额
	private String bankRequestId;			//银行请求ID(11域,POSP生成)
	private String localTransTime;			//银行交易时间
	private String localTransDate;			//银行交易日期
	private String posEntryModeCode;		//POS输入方式(22域)
	private String bankExternalId;			//银行参考号(37域,银行返回)
	private String authorizationCode;		//授权码
	private String bankResponseCode;		//银行返回码
	private String bankCustomerNo;			//银行商户号
	private String bankPosCati;				//银行终端号
	private String bankChannelCode;			//通道编号
	private String batchNo;					//银行批次号	
	
	private String sourceBankRequestId;		//原始交易银行请求ID
	private String sourceTranDate;			//原始交易日期	
	private Date createTime;				//创建时间
	private Date completeTime;				//完成时间	

	private Payment payment;				//交易信息
	private String bankInterfaceCode;		//银行接口编码
	private CardInfo cardInfo;				//卡信息		
	private Order order;					//订单信息
	
	@Column(name = "POS_CATI", length = 20)
	public String getPosCati() {
		return posCati;
	}
	public void setPosCati(String posCati) {
		this.posCati = posCati;
	}
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "TRANS_TYPE", columnDefinition = "VARCHAR(50)")
	public TransType getTransType() {
		return transType;
	}
	public void setTransType(TransType transType) {
		this.transType = transType;
	}
	
	@Column(name = "PAN", length = 50)
	public String getPan() {
		return pan;
	}
	public void setPan(String pan) {
		this.pan = pan;
	}
	
	@Column(name = "AMOUNT")
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	
	@Column(name = "BANK_REQUEST_ID", length = 50)
	public String getBankRequestId() {
		return bankRequestId;
	}
	public void setBankRequestId(String bankRequestId) {
		this.bankRequestId = bankRequestId;
	}
	
	@Column(name = "LOCAL_TRANS_TIME", length = 20)
	public String getLocalTransTime() {
		return localTransTime;
	}
	public void setLocalTransTime(String localTransTime) {
		this.localTransTime = localTransTime;
	}
	
	@Column(name = "LOCAL_TRANS_DATE", length = 20)
	public String getLocalTransDate() {
		return localTransDate;
	}
	public void setLocalTransDate(String localTransDate) {
		this.localTransDate = localTransDate;
	}
	
	@Column(name = "POS_ENTRY_MODE", length = 20)
	public String getPosEntryModeCode() {
		return posEntryModeCode;
	}
	public void setPosEntryModeCode(String posEntryModeCode) {
		this.posEntryModeCode = posEntryModeCode;
	}
	
	@Column(name = "BANK_EXTERNAL_ID", length = 50)
	public String getBankExternalId() {
		return bankExternalId;
	}
	public void setBankExternalId(String bankExternalId) {
		this.bankExternalId = bankExternalId;
	}
	
	@Column(name = "AUTHORIZATION_CODE", length = 20)
	public String getAuthorizationCode() {
		return authorizationCode;
	}
	public void setAuthorizationCode(String authorizationCode) {
		this.authorizationCode = authorizationCode;
	}
	
	@Column(name = "BANK_RESPONSE_CODE", length = 20)
	public String getBankResponseCode() {
		return bankResponseCode;
	}
	public void setBankResponseCode(String bankResponseCode) {
		this.bankResponseCode = bankResponseCode;
	}
	
	@Column(name = "BANK_CUSTOMER_NO", length = 20)
	public String getBankCustomerNo() {
		return bankCustomerNo;
	}
	public void setBankCustomerNo(String bankCustomerNo) {
		this.bankCustomerNo = bankCustomerNo;
	}
	
	@Column(name = "BANK_POS_CATI", length = 20)
	public String getBankPosCati() {
		return bankPosCati;
	}
	public void setBankPosCati(String bankPosCati) {
		this.bankPosCati = bankPosCati;
	}
	
	@Column(name = "BANK_CHANNEL_CODE", length = 20)
	public String getBankChannelCode() {
		return bankChannelCode;
	}
	public void setBankChannelCode(String bankChannelCode) {
		this.bankChannelCode = bankChannelCode;
	}
	
	@Column(name = "BATCH_NO", length = 20)
	public String getBatchNo() {
		return batchNo;
	}
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}
	
	@Column(name = "S_BANK_REQUEST_ID", length = 20)
	public String getSourceBankRequestId() {
		return sourceBankRequestId;
	}
	public void setSourceBankRequestId(String sourceBankRequestId) {
		this.sourceBankRequestId = sourceBankRequestId;
	}
	
	@Column(name = "S_TRANS_DATE", length = 20)
	public String getSourceTranDate() {
		return sourceTranDate;
	}
	public void setSourceTranDate(String sourceTranDate) {
		this.sourceTranDate = sourceTranDate;
	}
	
	@Column(name = "CREATE_TIME", columnDefinition = "DATETIME")
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@Column(name = "COMPLETE_TIME", columnDefinition = "DATETIME")
	public Date getCompleteTime() {
		return completeTime;
	}
	public void setCompleteTime(Date completeTime) {
		this.completeTime = completeTime;
	}
	
	@ManyToOne(fetch = FetchType.LAZY )
	public Payment getPayment() {
		return payment;
	}
	public void setPayment(Payment payment) {
		this.payment = payment;
	}
	
	@ManyToOne(fetch = FetchType.LAZY )
	public CardInfo getCardInfo() {
		return cardInfo;
	}
	public void setCardInfo(CardInfo cardInfo) {
		this.cardInfo = cardInfo;
	}
	
	@ManyToOne(fetch = FetchType.LAZY )
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	
	@Column(name = "BANK_INTERFACE_CODE", length = 20)
	public String getBankInterfaceCode() {
		return bankInterfaceCode;
	}
	public void setBankInterfaceCode(String bankInterfaceCode) {
		this.bankInterfaceCode = bankInterfaceCode;
	}	
}
