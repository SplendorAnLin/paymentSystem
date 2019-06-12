package com.yl.pay.pos.entity;

import javax.persistence.*;

import com.yl.pay.pos.enums.ProxyPayStatus;

import java.io.Serializable;
import java.util.Date;

/**
 * Title: 代付信息
 * Copyright: Copyright (c)2011
 * Company: lepay
 * @author haitao.liu
 */

@Entity
@Table(name = "PROXY_PAY_INFO")
public class ProxyPayInfo implements Serializable{

	private Integer id;		//版本标识
	/** 商户号 **/
	private String bankCustomerNo; 
	/** POS交易流水号 **/
	private String sysFlowNo;	
	/** 交易卡号 **/
	private String pan;
	/** 交易金额 **/
	private String transAmount;
	/** 交易商户 **/
	private String customerNo;
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
	/** 响应码 **/
	private String rspCode;
	/** 响应信息 **/
	private String rspMsg;
	/** 状态：INIT,PAY_REQ_SUCC,PAY_REQ_FAIL,PAY_RESP_SUCC,PAY_RESP_FAIL **/
	private ProxyPayStatus status;
	/** 描述 **/
	private String remark;
	private Date lastUpdateTime;	//最后签到时间
	private Date createTime;		//创建时间
	
	/** 卡类型：PERSONAL(对私)，BUSINESS(对公)**/
	private String cardType;
	/** 检索号 **/
	private String externalId;
	
	/** 通道检索号 **/
	private String bankExternalId;
	
	/**代付成本**/
	private String proxyPayCost;
	
	private Date orderCompleteTime;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "ID", length = 20)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name = "BANK_CUSTOMER_NO", length = 30)
	public String getBankCustomerNo() {
		return bankCustomerNo;
	}
	public void setBankCustomerNo(String bankCustomerNo) {
		this.bankCustomerNo = bankCustomerNo;
	}
	
	@Column(name = "SYS_FLOW_NO", length = 20)
	public String getSysFlowNo() {
		return sysFlowNo;
	}
	public void setSysFlowNo(String sysFlowNo) {
		this.sysFlowNo = sysFlowNo;
	}
	
	@Column(name = "PAN", length = 30)
	public String getPan() {
		return pan;
	}
	public void setPan(String pan) {
		this.pan = pan;
	}
	
	@Column(name = "TRANS_AMOUNT", length = 20)
	public String getTransAmount() {
		return transAmount;
	}
	public void setTransAmount(String transAmount) {
		this.transAmount = transAmount;
	}
	
	@Column(name = "CUSTOMER_NO", length = 30)
	public String getCustomerNo() {
		return customerNo;
	}
	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}

	
	@Column(name = "CUST_NAME", length = 30)
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	
	@Column(name = "CUST_CARD_NO", length = 30)
	public String getCustCardNo() {
		return custCardNo;
	}
	public void setCustCardNo(String custCardNo) {
		this.custCardNo = custCardNo;
	}
	
	@Column(name = "CUST_AMOUNT", length = 30)
	public String getCustAmount() {
		return custAmount;
	}
	public void setCustAmount(String custAmount) {
		this.custAmount = custAmount;
	}
	
	@Column(name = "ISS_NAME", length = 150)
	public String getIssName() {
		return issName;
	}
	public void setIssName(String issName) {
		this.issName = issName;
	}
	
	@Column(name = "ISS_NO", length = 30)
	public String getIssNo() {
		return issNo;
	}
	public void setIssNo(String issNo) {
		this.issNo = issNo;
	}
	
	@Column(name = "ORDER_NO", length = 30)
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	
	@Column(name = "NOTIFY_URL", length = 100)
	public String getNotifyUrl() {
		return notifyUrl;
	}
	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}
	
	@Column(name = "SIGN_CODE", length = 50)
	public String getSignCode() {
		return signCode;
	}
	public void setSignCode(String signCode) {
		this.signCode = signCode;
	}
	
	@Column(name = "RSP_CODE", length = 10)
	public String getRspCode() {
		return rspCode;
	}
	public void setRspCode(String rspCode) {
		this.rspCode = rspCode;
	}
	
	@Column(name = "RSP_MSG", length = 100)
	public String getRspMsg() {
		return rspMsg;
	}
	public void setRspMsg(String rspMsg) {
		this.rspMsg = rspMsg;
	}
	@Enumerated(value = EnumType.STRING)
	@Column(name = "STATUS", columnDefinition = "VARCHAR(10)")
	public ProxyPayStatus getStatus() {
		return status;
	}
	public void setStatus(ProxyPayStatus status) {
		this.status = status;
	}
	
	@Column(name = "REMARK ", length = 200)
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Column(name = "LAST_UPDATE_TIME", columnDefinition = "DATETIME")
	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}
	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
	
	
	@Column(name = "CREATE_TIME", columnDefinition = "DATETIME")
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@Column(name = "CARD_TYPE", length=20)
	public String getCardType() {
		return cardType;
	}
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	
	@Column(name = "EXTERNAL_ID", length=20)
	public String getExternalId() {
		return externalId;
	}
	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}
	
	@Column(name = "BANK_EXTERNAL_ID", length=20)
	public String getBankExternalId() {
		return bankExternalId;
	}
	public void setBankExternalId(String bankExternalId) {
		this.bankExternalId = bankExternalId;
	}
	
	@Column(name = "PROXY_PAY_COST", length=20)
	public String getProxyPayCost() {
		return proxyPayCost;
	}
	public void setProxyPayCost(String proxyPayCost) {
		this.proxyPayCost = proxyPayCost;
	}
	
	@Column(name = "ORDER_COMPLETE_TIME", columnDefinition = "DATETIME")
	public Date getOrderCompleteTime() {
		return orderCompleteTime;
	}
	public void setOrderCompleteTime(Date orderCompleteTime) {
		this.orderCompleteTime = orderCompleteTime;
	}
	@Override
	public String toString() {
		return "ProxyPayInfo [id=" + id + ", bankCustomerNo=" + bankCustomerNo + ", sysFlowNo=" + sysFlowNo + ", pan="
				+ pan + ", transAmount=" + transAmount + ", customerNo=" + customerNo + ", custName=" + custName
				+ ", custCardNo=" + custCardNo + ", custAmount=" + custAmount + ", issName=" + issName + ", issNo="
				+ issNo + ", orderNo=" + orderNo + ", notifyUrl=" + notifyUrl + ", signCode=" + signCode + ", rspCode="
				+ rspCode + ", rspMsg=" + rspMsg + ", status=" + status + ", remark=" + remark + ", lastUpdateTime="
				+ lastUpdateTime + ", createTime=" + createTime + ", cardType=" + cardType + ", externalId="
				+ externalId + ", bankExternalId=" + bankExternalId + ", proxyPayCost=" + proxyPayCost + "]";
	}
	
	

}
