package com.yl.pay.pos.api.bean;

import java.util.Date;

/**
 * Title: 交易信息
 * Description:  
 * Copyright: Copyright (c)2011
 * Company: com.yl.pay
 * @author haitao.liu
 */
public class Payment extends BaseEntity{
	
	private String transType;			//交易类型
	private String customerNo;				//POS商户号
	private String pan;						//卡号
	private String posCati;					//POS终端号
	private String posBatch;				//交易批次号
	private String posRequestId;			//POS流水号
	private Double amount;					//交易金额
	private String currencyType;		//币种
	
	private String bankRequestId;			//银行请求号(11域)
	private String bankBatch;				//银行批次号
	private String bankExternalId;			//银行流水号(37域)
	private String bankPosCati;				//银行终端号
	private String bankCustomerNo;			//银行商户号
	
	private Double customerFee;				//商户手续费
	private Double bankCost;				//银行成本 
	
	private String status;				//订单交易状态
	private Date createTime;				//交易时间	
	private Date completeTime;				//完成时间
	
	private String sourcePaymentNo;		//原交易Payment
	private String bankChannelRate;		//银行通道费率
	private String customerRate;		//商户费率
	private String interfaceName;		//接口名称
	
	public String getSourcePaymentNo() {
		return sourcePaymentNo;
	}
	public String getBankChannelRate() {
		return bankChannelRate;
	}
	public String getCustomerRate() {
		return customerRate;
	}
	public String getInterfaceName() {
		return interfaceName;
	}
	public void setSourcePaymentNo(String sourcePaymentNo) {
		this.sourcePaymentNo = sourcePaymentNo;
	}
	public void setBankChannelRate(String bankChannelRate) {
		this.bankChannelRate = bankChannelRate;
	}
	public void setCustomerRate(String customerRate) {
		this.customerRate = customerRate;
	}
	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}
	public String getTransType() {
		return transType;
	}
	public String getCustomerNo() {
		return customerNo;
	}
	public String getPan() {
		return pan;
	}
	public String getPosCati() {
		return posCati;
	}
	public String getPosBatch() {
		return posBatch;
	}
	public String getPosRequestId() {
		return posRequestId;
	}
	public Double getAmount() {
		return amount;
	}
	public String getCurrencyType() {
		return currencyType;
	}
	public String getBankRequestId() {
		return bankRequestId;
	}
	public String getBankBatch() {
		return bankBatch;
	}
	public String getBankExternalId() {
		return bankExternalId;
	}
	public String getBankPosCati() {
		return bankPosCati;
	}
	public String getBankCustomerNo() {
		return bankCustomerNo;
	}
	public Double getCustomerFee() {
		return customerFee;
	}
	public Double getBankCost() {
		return bankCost;
	}
	public String getStatus() {
		return status;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public Date getCompleteTime() {
		return completeTime;
	}
	public void setTransType(String transType) {
		this.transType = transType;
	}
	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}
	public void setPan(String pan) {
		this.pan = pan;
	}
	public void setPosCati(String posCati) {
		this.posCati = posCati;
	}
	public void setPosBatch(String posBatch) {
		this.posBatch = posBatch;
	}
	public void setPosRequestId(String posRequestId) {
		this.posRequestId = posRequestId;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public void setCurrencyType(String currencyType) {
		this.currencyType = currencyType;
	}
	public void setBankRequestId(String bankRequestId) {
		this.bankRequestId = bankRequestId;
	}
	public void setBankBatch(String bankBatch) {
		this.bankBatch = bankBatch;
	}
	public void setBankExternalId(String bankExternalId) {
		this.bankExternalId = bankExternalId;
	}
	public void setBankPosCati(String bankPosCati) {
		this.bankPosCati = bankPosCati;
	}
	public void setBankCustomerNo(String bankCustomerNo) {
		this.bankCustomerNo = bankCustomerNo;
	}
	public void setCustomerFee(Double customerFee) {
		this.customerFee = customerFee;
	}
	public void setBankCost(Double bankCost) {
		this.bankCost = bankCost;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public void setCompleteTime(Date completeTime) {
		this.completeTime = completeTime;
	}
}
