package com.yl.pay.pos.entity;

import com.yl.pay.pos.enums.*;

import javax.persistence.*;

import org.codehaus.jackson.annotate.JsonIgnore;

import java.util.Date;

/**
 * Title: 交易信息
 * Description:  
 * Copyright: Copyright (c)2011
 * Company: com.yl.pay
 * @author haitao.liu
 */

@Entity
@Table(name = "POS_PAYMENT")
public class Payment extends BaseEntity{
	
	private TransType transType;			//交易类型
	private String customerNo;				//POS商户号
	private String pan;						//卡号
	private String posCati;					//POS终端号
	private String posBatch;				//交易批次号
	private String posRequestId;			//POS流水号
	private Double amount;					//交易金额
	private CurrencyType currencyType;		//币种
	
	private String bankRequestId;			//银行请求号(11域)
	private String bankBatch;				//银行批次号
	private String bankExternalId;			//银行流水号(37域)
	private String bankPosCati;				//银行终端号
	private String bankCustomerNo;			//银行商户号
	private String authorizationCode;		//银行授权码
	private String bankDate;				//银行交易日期
	private String bankTime;				//银行交易时间	
	@JsonIgnore
	private BankInterface bankInterface;	//银行接口
	@JsonIgnore
	private BankChannel bankChannel;		//银行通道	
	private Double customerFee;				//商户手续费
	private Double bankCost;				//银行成本 
	@JsonIgnore
	private BankCustomerLevel bankCustomerLevel;	//银行商户级别
	
	private TransStatus status;				//订单交易状态
	private Date createTime;				//交易时间	
	private Date completeTime;				//完成时间
	@JsonIgnore
	private Bank issuer;					//发卡银行
	@JsonIgnore
	private Order order;					//订单
	@JsonIgnore
	private CardInfo cardInfo;				//卡信息
	@JsonIgnore
	private Payment sourcePayment;			//原交易Payment
	@JsonIgnore
	private PaymentFee paymentFee;			//支付费用信息
	private String innerPan;				//转入卡号
	private String serviceInfo;				//银联60.2域信息，服务点信息
	private String aquiringInstitutionId;	//银联32域，代理组织机构标识
	private String additionalRespData;  	//附加响应数据（工行44域）
	
	@Column(name = "PAN", length = 50)
	public String getPan() {
		return pan;
	}
	public void setPan(String pan) {
		this.pan = pan;
	}
	@Enumerated(value = EnumType.STRING)
	@Column(name = "TRANS_TYPE", columnDefinition = "VARCHAR(50)")
	public TransType getTransType() {
		return transType;
	}
	public void setTransType(TransType transType) {
		this.transType = transType;
	}
	
	@Column(name = "CUSTOMER_NO", length = 50)
	public String getCustomerNo() {
		return customerNo;
	}
	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}
	
	@Column(name = "POS_CATI", length = 20)
	public String getPosCati() {
		return posCati;
	}
	public void setPosCati(String posCati) {
		this.posCati = posCati;
	}
	
	@Column(name = "POS_BATCH", length = 20)
	public String getPosBatch() {
		return posBatch;
	}
	public void setPosBatch(String posBatch) {
		this.posBatch = posBatch;
	}
	
	@Column(name = "POS_REQUEST_ID", length = 50)
	public String getPosRequestId() {
		return posRequestId;
	}
	public void setPosRequestId(String posRequestId) {
		this.posRequestId = posRequestId;
	}
	
	@Column(name = "AMOUNT")
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "CURRENCY_TYPE", columnDefinition = "VARCHAR(20)")
	public CurrencyType getCurrencyType() {
		return currencyType;
	}
	public void setCurrencyType(CurrencyType currencyType) {
		this.currencyType = currencyType;
	}
	
	@Column(name = "BANK_REQUEST_ID", length = 50)
	public String getBankRequestId() {
		return bankRequestId;
	}
	public void setBankRequestId(String bankRequestId) {
		this.bankRequestId = bankRequestId;
	}
	
	@Column(name = "BANK_BATCH", length = 20)
	public String getBankBatch() {
		return bankBatch;
	}
	public void setBankBatch(String bankBatch) {
		this.bankBatch = bankBatch;
	}
	
	@Column(name = "BANK_EXTERNAL_ID", length = 100)
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
	
	@Column(name = "BANK_POS_CATI", length = 20)
	public String getBankPosCati() {
		return bankPosCati;
	}
	public void setBankPosCati(String bankPosCati) {
		this.bankPosCati = bankPosCati;
	}
	
	@Column(name = "BANK_CUSTOMER_NO", length = 20)
	public String getBankCustomerNo() {
		return bankCustomerNo;
	}
	public void setBankCustomerNo(String bankCustomerNo) {
		this.bankCustomerNo = bankCustomerNo;
	}

	
	@Column(name = "BANK_DATE", length = 10)
	public String getBankDate() {
		return bankDate;
	}
	public void setBankDate(String bankDate) {
		this.bankDate = bankDate;
	}
	
	@Column(name = "BANK_TIME", length = 10)
	public String getBankTime() {
		return bankTime;
	}
	public void setBankTime(String bankTime) {
		this.bankTime = bankTime;
	}
	
	@ManyToOne(fetch = FetchType.EAGER )
	@JoinColumn(name="BANK_CHANNEL",columnDefinition = "VARCHAR(30)")
	public BankChannel getBankChannel() {
		return bankChannel;
	}
	public void setBankChannel(BankChannel bankChannel) {
		this.bankChannel = bankChannel;
	}
	
	@Column(name = "CUSTOMER_FEE")
	public Double getCustomerFee() {
		return customerFee;
	}
	public void setCustomerFee(Double customerFee) {
		this.customerFee = customerFee;
	}
	
	@Column(name = "BANK_COST")
	public Double getBankCost() {
		return bankCost;
	}
	public void setBankCost(Double bankCost) {
		this.bankCost = bankCost;
	}
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "STATUS", columnDefinition = "VARCHAR(20)")
	public TransStatus getStatus() {
		return status;
	}
	public void setStatus(TransStatus status) {
		this.status = status;
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
	
	@ManyToOne(fetch = FetchType.EAGER )
	@JoinColumn(name="ISSUER",columnDefinition = "VARCHAR(30)")
	public Bank getIssuer() {
		return issuer;
	}
	public void setIssuer(Bank issuer) {
		this.issuer = issuer;
	}
	
	@ManyToOne(fetch = FetchType.EAGER )
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	
	@ManyToOne(fetch = FetchType.EAGER )
	public CardInfo getCardInfo() {
		return cardInfo;
	}
	public void setCardInfo(CardInfo cardInfo) {
		this.cardInfo = cardInfo;
	}
	
	@ManyToOne(fetch = FetchType.EAGER )
	public Payment getSourcePayment() {
		return sourcePayment;
	}
	public void setSourcePayment(Payment sourcePayment) {
		this.sourcePayment = sourcePayment;
	}
	
	@ManyToOne(fetch = FetchType.EAGER )
	@JoinColumn(name="BANK_INTERFACE",columnDefinition = "VARCHAR(20)")
	public BankInterface getBankInterface() {
		return bankInterface;
	}
	public void setBankInterface(BankInterface bankInterface) {
		this.bankInterface = bankInterface;
	}
	
	@ManyToOne(fetch = FetchType.EAGER )
	public PaymentFee getPaymentFee() {
		return paymentFee;
	}
	public void setPaymentFee(PaymentFee paymentFee) {
		this.paymentFee = paymentFee;
	}
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "BANK_CUSTOMER_LEVEL", columnDefinition = "VARCHAR(30)")
	public BankCustomerLevel getBankCustomerLevel() {
		return bankCustomerLevel;
	}
	public void setBankCustomerLevel(BankCustomerLevel bankCustomerLevel) {
		this.bankCustomerLevel = bankCustomerLevel;
	}
	
	
	@Column(name = "INNER_PAN" ,columnDefinition = "VARCHAR(50)")
	public String getInnerPan() {
		return innerPan;
	}
	public void setInnerPan(String innerPan) {
		this.innerPan = innerPan;
	}
	@Column(name = "SERVICE_INFO" ,columnDefinition = "VARCHAR(50)")
	public String getServiceInfo() {
		return serviceInfo;
	}
	public void setServiceInfo(String serviceInfo) {
		this.serviceInfo = serviceInfo;
	}
	
	@Column(name = "AQUIRING_INSTITUTION_ID" ,columnDefinition = "VARCHAR(50)")
	public String getAquiringInstitutionId() {
		return aquiringInstitutionId;
	}
	public void setAquiringInstitutionId(String aquiringInstitutionId) {
		this.aquiringInstitutionId = aquiringInstitutionId;
	}
	
	@Column(name = "ADDITIONAL_RESP_DATA" ,columnDefinition = "VARCHAR(100)")
	public String getAdditionalRespData() {
		return additionalRespData;
	}
	public void setAdditionalRespData(String additionalRespData) {
		this.additionalRespData = additionalRespData;
	}
}
