package com.yl.pay.pos.api.bean;

import java.util.Date;

public class Order extends BaseEntity{
	
	private String transType;				//交易类型
	private String customerNo;					//商户号
	private String customerOrderCode;			//商家订单号
	private String externalId;					//系统流水号,参考号
	
	private String posBatch;					//POS批次号
	private String posCati;						//POS终端号
	private String posRequestId;				//POS流水号
	private String authorizationCode;			//授权码
	private String pan;							//卡号
	private String cardType;					//卡类型
	private Double amount;						//交易金额
	private String currencyType;			//币种
	
	private String description;					//订单描述信息
	private Date createTime;					//创建时间	
	private Date completeTime;					//完成时间
	private Date settleTime;					//结算时间	
	private String status;					//订单交易状态
	private String creditStatus;					//入账状态
	private Date  creditTime;					//入账时间
	
	private String bankInterfaceName;			//接口名称	
	private Long finalPaymentId;				//最后一笔payment
	
	private String isIC;					  		//是否是IC卡
	private Double bankCost;					//银行成本
	private Double customerFee;					//手续费
	private String bankCustomerNo;				//银行商户号
	private String bankChannelCode;				//银行通道编号
	private String businessType;						//是否为乐付宝交易
	private String isSyn;						//是否同步
	private String responCode;					//返回码
	public String getTransType() {
		return transType;
	}
	public String getCustomerNo() {
		return customerNo;
	}
	public String getCustomerOrderCode() {
		return customerOrderCode;
	}
	public String getExternalId() {
		return externalId;
	}
	public String getPosBatch() {
		return posBatch;
	}
	public String getPosCati() {
		return posCati;
	}
	public String getPosRequestId() {
		return posRequestId;
	}
	public String getAuthorizationCode() {
		return authorizationCode;
	}
	public String getPan() {
		return pan;
	}
	public String getCardType() {
		return cardType;
	}
	public Double getAmount() {
		return amount;
	}
	public String getCurrencyType() {
		return currencyType;
	}
	public String getDescription() {
		return description;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public Date getCompleteTime() {
		return completeTime;
	}
	public Date getSettleTime() {
		return settleTime;
	}
	public String getStatus() {
		return status;
	}
	public String getCreditStatus() {
		return creditStatus;
	}
	public Date getCreditTime() {
		return creditTime;
	}
	public Long getFinalPaymentId() {
		return finalPaymentId;
	}
	public String getIsIC() {
		return isIC;
	}
	public Double getBankCost() {
		return bankCost;
	}
	public Double getCustomerFee() {
		return customerFee;
	}
	public String getBankCustomerNo() {
		return bankCustomerNo;
	}
	public String getBankChannelCode() {
		return bankChannelCode;
	}
	public String getBusinessType() {
		return businessType;
	}
	public String getIsSyn() {
		return isSyn;
	}
	public String getResponCode() {
		return responCode;
	}
	public void setTransType(String transType) {
		this.transType = transType;
	}
	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}
	public void setCustomerOrderCode(String customerOrderCode) {
		this.customerOrderCode = customerOrderCode;
	}
	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}
	public void setPosBatch(String posBatch) {
		this.posBatch = posBatch;
	}
	public void setPosCati(String posCati) {
		this.posCati = posCati;
	}
	public void setPosRequestId(String posRequestId) {
		this.posRequestId = posRequestId;
	}
	public void setAuthorizationCode(String authorizationCode) {
		this.authorizationCode = authorizationCode;
	}
	public void setPan(String pan) {
		this.pan = pan;
	}
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public void setCurrencyType(String currencyType) {
		this.currencyType = currencyType;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public void setCompleteTime(Date completeTime) {
		this.completeTime = completeTime;
	}
	public void setSettleTime(Date settleTime) {
		this.settleTime = settleTime;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public void setCreditStatus(String creditStatus) {
		this.creditStatus = creditStatus;
	}
	public void setCreditTime(Date creditTime) {
		this.creditTime = creditTime;
	}
	public void setFinalPaymentId(Long finalPaymentId) {
		this.finalPaymentId = finalPaymentId;
	}
	public void setIsIC(String isIC) {
		this.isIC = isIC;
	}
	public void setBankCost(Double bankCost) {
		this.bankCost = bankCost;
	}
	public void setCustomerFee(Double customerFee) {
		this.customerFee = customerFee;
	}
	public void setBankCustomerNo(String bankCustomerNo) {
		this.bankCustomerNo = bankCustomerNo;
	}
	public void setBankChannelCode(String bankChannelCode) {
		this.bankChannelCode = bankChannelCode;
	}
	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}
	public void setIsSyn(String isSyn) {
		this.isSyn = isSyn;
	}
	public void setResponCode(String responCode) {
		this.responCode = responCode;
	}
	public String getBankInterfaceName() {
		return bankInterfaceName;
	}
	public void setBankInterfaceName(String bankInterfaceName) {
		this.bankInterfaceName = bankInterfaceName;
	}
}
