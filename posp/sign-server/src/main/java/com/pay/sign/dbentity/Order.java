package com.pay.sign.dbentity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.pay.sign.dao.util.BaseEntity;
import com.pay.sign.enums.CardType;
import com.pay.sign.enums.CurrencyType;
import com.pay.sign.enums.OrderStatus;
import com.pay.sign.enums.TransType;
import com.pay.sign.enums.YesNo;

@Entity
@Table(name = "POS_ORDER")
public class Order extends BaseEntity {
	
	private static final long serialVersionUID = 1L;
	
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
	
	private Long shopId;						//店铺ID
	
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
	@Column(name = "POS_REQUEST_ID", length = 50)
	public String getPosRequestId() {
		return posRequestId;
	}
	public void setPosRequestId(String posRequestId) {
		this.posRequestId = posRequestId;
	}
	@Column(name = "AUTHORIZATION_CODE", length = 30)
	public String getAuthorizationCode() {
		return authorizationCode;
	}
	public void setAuthorizationCode(String authorizationCode) {
		this.authorizationCode = authorizationCode;
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
	@Column(name = "CREATE_TIME", columnDefinition = "DATE")
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	@Column(name = "COMPLETE_TIME", columnDefinition = "DATE")
	public Date getCompleteTime() {
		return completeTime;
	}
	public void setCompleteTime(Date completeTime) {
		this.completeTime = completeTime;
	}
	@Column(name = "SETTLE_TIME", columnDefinition = "DATE")
	public Date getSettleTime() {
		return settleTime;
	}
	public void setSettleTime(Date settleTime) {
		this.settleTime = settleTime;
	}
	@Enumerated(value = EnumType.STRING)
	@Column(name = "STATUS", columnDefinition = "VARCHAR(20)")
	public OrderStatus getStatus() {
		return status;
	}
	public void setStatus(OrderStatus status) {
		this.status = status;
	}
	@Enumerated(value = EnumType.STRING)
	@Column(name = "CREDIT_STATUS", columnDefinition = "VARCHAR(20)")
	public YesNo getCreditStatus() {
		return creditStatus;
	}
	public void setCreditStatus(YesNo creditStatus) {
		this.creditStatus = creditStatus;
	}
	@Column(name = "CREDIT_TIME", columnDefinition = "DATE")
	public Date getCreditTime() {
		return creditTime;
	}
	public void setCreditTime(Date creditTime) {
		this.creditTime = creditTime;
	}
	@Column(name = "SHOP_ID", columnDefinition = "NUMBER")
	public Long getShopId() {
		return shopId;
	}
	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}
	
}
