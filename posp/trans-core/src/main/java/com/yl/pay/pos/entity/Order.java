package com.yl.pay.pos.entity;

import com.yl.pay.pos.enums.*;

import javax.persistence.*;

import org.codehaus.jackson.annotate.JsonIgnore;

import java.util.Date;

/**
 * Title: 卡片信息
 * Description:  POS终端
 * Copyright: Copyright (c)2011
 * Company: com.yl.pay
 * @author haitao.liu
 */

@Entity
@Table(name = "POS_ORDER")
public class Order extends BaseEntity{
	
	private TransType transType;				//交易类型
	private String customerNo;					//商户号
	private String customerOrderCode;			//商家订单号
	private String externalId;					//系统流水号,参考号
	
	private String posBatch;					//POS批次号
	private String posCati;						//POS终端号
	private String posRequestId;				//POS流水号
	private String authorizationCode;			//授权码
	private String pan;							//卡号
	private CardType cardType;					//卡类型
	private Double amount;						//交易金额
	private CurrencyType currencyType;			//币种
	
	private String description;					//订单描述信息
	private Date createTime;					//创建时间	
	private Date completeTime;					//完成时间
	private Date settleTime;					//结算时间	
	private OrderStatus status;					//订单交易状态
	private YesNo creditStatus;					//入账状态
	private Date  creditTime;					//入账时间
	
	@JsonIgnore
	private Bank issuer;						//发卡行
	@JsonIgnore
	private BankInterface bankInterface;		//收单接口
	@JsonIgnore
	private CardInfo cardInfo;					//卡信息
	@JsonIgnore
	private Shop shop;							//所属网点
	@JsonIgnore
	private Customer customer;					//商户信息	
	private Long finalPaymentId;				//最后一笔payment
	
	private YesNo isIC;					  		//是否是IC卡
	private Double bankCost;					//银行成本
	private Double customerFee;					//手续费
	private String bankCustomerNo;				//银行商户号
	private String bankChannelCode;				//银行通道编号
	private String businessType;						//是否为乐付宝交易
	private YesNo isSyn;						//是否同步
	private String responCode;					//返回码
	//private RouteType routeType;				//交易路由类型
		
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
	
	@Column(name = "CUSTOMER_ORDER_CODE", length = 100)
	public String getCustomerOrderCode() {
		return customerOrderCode;
	}
	public void setCustomerOrderCode(String customerOrderCode) {
		this.customerOrderCode = customerOrderCode;
	}
	
	@Column(name = "EXTERNAL_ID", length = 30)
	public String getExternalId() {
		return externalId;
	}
	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}

	@Column(name = "POS_BATCH", length = 20)
	public String getPosBatch() {
		return posBatch;
	}
	public void setPosBatch(String posBatch) {
		this.posBatch = posBatch;
	}
	
	@Column(name = "POS_CATI", length = 20)
	public String getPosCati() {
		return posCati;
	}
	public void setPosCati(String posCati) {
		this.posCati = posCati;
	}
	
	@Column(name = "PAN", length = 50)
	public String getPan() {
		return pan;
	}
	public void setPan(String pan) {
		this.pan = pan;
	}
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "CARD_TYPE", columnDefinition = "VARCHAR(20)")
	public CardType getCardType() {
		return cardType;
	}
	public void setCardType(CardType cardType) {
		this.cardType = cardType;
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
	
	@Column(name = "DESCRIPTION", length = 100)
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
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
	
	@Column(name = "SETTLE_TIME", columnDefinition = "DATETIME")
	public Date getSettleTime() {
		return settleTime;
	}
	public void setSettleTime(Date settleTime) {
		this.settleTime = settleTime;
	}
	
	@Column(name = "AUTHORIZATION_CODE", length = 30)
	public String getAuthorizationCode() {
		return authorizationCode;
	}
	public void setAuthorizationCode(String authorizationCode) {
		this.authorizationCode = authorizationCode;
	}
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "STATUS", columnDefinition = "VARCHAR(20)")
	public OrderStatus getStatus() {
		return status;
	}
	public void setStatus(OrderStatus status) {
		this.status = status;
	}
	
	@ManyToOne(fetch = FetchType.EAGER )
	@JoinColumn(name="ISSUER",columnDefinition = "VARCHAR(20)")
	public Bank getIssuer() {
		return issuer;
	}
	public void setIssuer(Bank issuer) {
		this.issuer = issuer;
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
	public CardInfo getCardInfo() {
		return cardInfo;
	}
	public void setCardInfo(CardInfo cardInfo) {
		this.cardInfo = cardInfo;
	}
	
	@ManyToOne(fetch = FetchType.EAGER )
	public Shop getShop() {
		return shop;
	}
	public void setShop(Shop shop) {
		this.shop = shop;
	}
	
	@ManyToOne(fetch = FetchType.EAGER )
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "CREDIT_STATUS", columnDefinition = "VARCHAR(20)")
	public YesNo getCreditStatus() {
		return creditStatus;
	}
	public void setCreditStatus(YesNo creditStatus) {
		this.creditStatus = creditStatus;
	}
	
	@Column(name = "CREDIT_TIME", columnDefinition = "DATETIME")
	public Date getCreditTime() {
		return creditTime;
	}
	public void setCreditTime(Date creditTime) {
		this.creditTime = creditTime;
	}
	
	@Column(name="FINAL_PAYMENT_ID",columnDefinition = "int", length = 11)
	public Long getFinalPaymentId() {
		return finalPaymentId;
	}
	public void setFinalPaymentId(Long finalPaymentId) {
		this.finalPaymentId = finalPaymentId;
	}
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "IS_IC", columnDefinition = "VARCHAR(20)")
	public YesNo getIsIC() {
		return isIC;
	}
	public void setIsIC(YesNo isIC) {
		this.isIC = isIC;
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
	
	@Column(name = "BANK_CUSTOMER_NO", length = 60)
	public String getBankCustomerNo() {
		return bankCustomerNo;
	}
	public void setBankCustomerNo(String bankCustomerNo) {
		this.bankCustomerNo = bankCustomerNo;
	}
//	
//	@Enumerated(value = EnumType.STRING)
//	@Column(name = "IS_LFB", columnDefinition = "VARCHAR(20)")
//	public YesNo getIsLfb() {
//		return isLfb;
//	}
//	public void setIsLfb(YesNo isLfb) {
//		this.isLfb = isLfb;
//	}
	@Column(name = "business_Type", length = 60)
	public String getBusinessType() {
		return businessType;
	}
	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}
	@Column(name = "bank_channel_code", length = 32)
	public String getBankChannelCode() {
		return bankChannelCode;
	}
	public void setBankChannelCode(String bankChannelCode) {
		this.bankChannelCode = bankChannelCode;
	}
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "is_Syn", columnDefinition = "VARCHAR(20)")
	public YesNo getIsSyn() {
		return isSyn;
	}
	public void setIsSyn(YesNo isSyn) {
		this.isSyn = isSyn;
	}
	@Column(name = "respon_Code", length = 32)
	public String getResponCode() {
		return responCode;
	}
	public void setResponCode(String responCode) {
		this.responCode = responCode;
	}
	
//	@Enumerated(value = EnumType.STRING)
//	@Column(name = "ROUTE_TYPE", columnDefinition = "VARCHAR(20)")
//	public RouteType getRouteType() {
//		return routeType;
//	}
//	public void setRouteType(RouteType routeType) {
//		this.routeType = routeType;
//	}
	
	
}
