package com.yl.pay.pos.bean;

import java.util.Date;
import com.yl.pay.pos.enums.*;
/**
 * 订单
 * 
 * @author 聚合支付有限公司
 * @since 2016年7月17日
 * @version V1.0.0
 */
public class OemPosOrderBean {

	private static final long serialVersionUID = 6016225134848366883L;
	
	private Long orderId;						//订单ID
	private Long sourceOrderId;					//原订单ID
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
	
	private Long finalPaymentId;				//最后一笔payment
	
	private YesNo isIC;					  		//是否是IC卡
	private Double bankCost;					//银行成本
	private Double customerFee;					//手续费
	private String bankCustomerNo;				//银行商户号
	private String bankChannelCode;				//银行通道编号
	private String businessType;						//是否为乐付宝交易
	private YesNo isSyn;						//是否同步
	private String responCode;					//返回码
	private String routeType;				//交易路由类型
	private String bankInterface;
	private Long shopId;
	private Long customerId;
	private String issuer;
	private Long cardinfoId;

	private String agentNo;
	private String shortName;

	
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public Long getSourceOrderId() {
		return sourceOrderId;
	}
	public void setSourceOrderId(Long sourceOrderId) {
		this.sourceOrderId = sourceOrderId;
	}
	public TransType getTransType() {
		return transType;
	}
	public void setTransType(TransType transType) {
		this.transType = transType;
	}
	public String getCustomerNo() {
		return customerNo;
	}
	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}
	public String getCustomerOrderCode() {
		return customerOrderCode;
	}
	public void setCustomerOrderCode(String customerOrderCode) {
		this.customerOrderCode = customerOrderCode;
	}
	public String getExternalId() {
		return externalId;
	}
	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}
	public String getPosBatch() {
		return posBatch;
	}
	public void setPosBatch(String posBatch) {
		this.posBatch = posBatch;
	}
	public String getPosCati() {
		return posCati;
	}
	public void setPosCati(String posCati) {
		this.posCati = posCati;
	}
	public String getPosRequestId() {
		return posRequestId;
	}
	public void setPosRequestId(String posRequestId) {
		this.posRequestId = posRequestId;
	}
	public String getAuthorizationCode() {
		return authorizationCode;
	}
	public void setAuthorizationCode(String authorizationCode) {
		this.authorizationCode = authorizationCode;
	}
	public String getPan() {
		return pan;
	}
	public void setPan(String pan) {
		this.pan = pan;
	}
	public CardType getCardType() {
		return cardType;
	}
	public void setCardType(CardType cardType) {
		this.cardType = cardType;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public CurrencyType getCurrencyType() {
		return currencyType;
	}
	public void setCurrencyType(CurrencyType currencyType) {
		this.currencyType = currencyType;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getCompleteTime() {
		return completeTime;
	}
	public void setCompleteTime(Date completeTime) {
		this.completeTime = completeTime;
	}
	public Date getSettleTime() {
		return settleTime;
	}
	public void setSettleTime(Date settleTime) {
		this.settleTime = settleTime;
	}
	public OrderStatus getStatus() {
		return status;
	}
	public void setStatus(OrderStatus status) {
		this.status = status;
	}
	public YesNo getCreditStatus() {
		return creditStatus;
	}
	public void setCreditStatus(YesNo creditStatus) {
		this.creditStatus = creditStatus;
	}
	public Date getCreditTime() {
		return creditTime;
	}
	public void setCreditTime(Date creditTime) {
		this.creditTime = creditTime;
	}
	public Long getFinalPaymentId() {
		return finalPaymentId;
	}
	public void setFinalPaymentId(Long finalPaymentId) {
		this.finalPaymentId = finalPaymentId;
	}
	public YesNo getIsIC() {
		return isIC;
	}
	public void setIsIC(YesNo isIC) {
		this.isIC = isIC;
	}
	public Double getBankCost() {
		return bankCost;
	}
	public void setBankCost(Double bankCost) {
		this.bankCost = bankCost;
	}
	public Double getCustomerFee() {
		return customerFee;
	}
	public void setCustomerFee(Double customerFee) {
		this.customerFee = customerFee;
	}
	public String getBankCustomerNo() {
		return bankCustomerNo;
	}
	public void setBankCustomerNo(String bankCustomerNo) {
		this.bankCustomerNo = bankCustomerNo;
	}
	public String getBankChannelCode() {
		return bankChannelCode;
	}
	public void setBankChannelCode(String bankChannelCode) {
		this.bankChannelCode = bankChannelCode;
	}
	public String getBusinessType() {
		return businessType;
	}
	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}
	public YesNo getIsSyn() {
		return isSyn;
	}
	public void setIsSyn(YesNo isSyn) {
		this.isSyn = isSyn;
	}
	public String getResponCode() {
		return responCode;
	}
	public void setResponCode(String responCode) {
		this.responCode = responCode;
	}
	public String getRouteType() {
		return routeType;
	}
	public void setRouteType(String routeType) {
		this.routeType = routeType;
	}
	public String getBankInterface() {
		return bankInterface;
	}
	public void setBankInterface(String bankInterface) {
		this.bankInterface = bankInterface;
	}
	public Long getShopId() {
		return shopId;
	}
	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}
	public Long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	public String getIssuer() {
		return issuer;
	}
	public void setIssuer(String issuer) {
		this.issuer = issuer;
	}
	public Long getCardinfoId() {
		return cardinfoId;
	}
	public void setCardinfoId(Long cardinfoId) {
		this.cardinfoId = cardinfoId;
	}

	public String getAgentNo() {
		return agentNo;
	}

	public void setAgentNo(String agentNo) {
		this.agentNo = agentNo;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	@Override
	public String toString() {
		return "OemPosOrderBean [orderId=" + orderId + ", sourceOrderId=" + sourceOrderId + ", transType=" + transType
				+ ", customerNo=" + customerNo + ", customerOrderCode=" + customerOrderCode + ", externalId="
				+ externalId + ", posBatch=" + posBatch + ", posCati=" + posCati + ", posRequestId=" + posRequestId
				+ ", authorizationCode=" + authorizationCode + ", pan=" + pan + ", cardType=" + cardType + ", amount="
				+ amount + ", currencyType=" + currencyType + ", description=" + description + ", createTime="
				+ createTime + ", completeTime=" + completeTime + ", settleTime=" + settleTime + ", status=" + status
				+ ", creditStatus=" + creditStatus + ", creditTime=" + creditTime + ", finalPaymentId=" + finalPaymentId
				+ ", isIC=" + isIC + ", bankCost=" + bankCost + ", customerFee=" + customerFee + ", bankCustomerNo="
				+ bankCustomerNo + ", bankChannelCode=" + bankChannelCode + ", businessType=" + businessType
				+ ", isSyn=" + isSyn + ", responCode=" + responCode + ", routeType=" + routeType + ", bankInterface="
				+ bankInterface + ", shopId=" + shopId + ", customerId=" + customerId + ", issuer=" + issuer
				+ ", cardinfoId=" + cardinfoId + ", agentNo=" + agentNo + ", shortName=" + shortName + "]";
	}
	
	

	
}
