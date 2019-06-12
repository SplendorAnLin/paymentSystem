package com.yl.dpay.core.entity;

import java.io.Serializable;
import java.util.Date;

import com.yl.dpay.core.enums.AccountType;
import com.yl.dpay.core.enums.AuditStatus;
import com.yl.dpay.core.enums.CardType;
import com.yl.dpay.core.enums.CerType;
import com.yl.dpay.core.enums.NotifyStatus;
import com.yl.dpay.core.enums.OwnerRole;
import com.yl.dpay.core.enums.RequestStatus;
import com.yl.dpay.core.enums.RequestType;

/**
 * 代付信息
 * 
 * @author 聚合支付有限公司
 * @since 2016年5月12日
 * @version V1.0.0
 */
public class Request extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 6914530557756464006L;
	/**
	 * 请求编号
	 */
	private String requestNo;
	/**
	 * 请求类型
	 */
	private RequestType requestType;
	/**
	 * 金额
	 */
	private double amount;
	/**
	 * 手续费
	 */
	private double fee;
	/**
	 * 成本
	 */
	private double cost;
	/**
	 * 描述
	 */
	private String description;
	/**
	 * 状态
	 */
	private RequestStatus status = RequestStatus.WAIT;
	/**
	 * 完成描述
	 */
	private String completeMsg;
	/**
	 * 审核状态
	 */
	private AuditStatus auditStatus = AuditStatus.WAIT;
	/**
	 * 持有者ID
	 */
	private String ownerId;
	/**
	 * 持有者角色
	 */
	private OwnerRole ownerRole;
	/**
	 * 账户名称
	 */
	private String accountName;
	/**
	 * 账户编号
	 */
	private String accountNo;
	/**
	 * 账户类型
	 */
	private AccountType accountType;
	/**
	 * 卡类型
	 */
	private CardType cardType;
	/**
	 * 证件类型
	 */
	private CerType cerType;
	/**
	 * 证件编号
	 */
	private String cerNo;
	/**
	 * 流水号
	 */
	private String flowNo;
	/**
	 * 审核时间
	 */
	private Date auditDate;
	/**
	 * 完成时间
	 */
	private Date completeDate;
	/**
	 * 操作来源
	 */
	private String operator;
	/**
	 * 银行编号
	 */
	private String bankCode;
	/**
	 * 有效期
	 */
	private String validity;
	/**
	 * CVV2
	 */
	private String cvv;
	/**
	 * 银行名称
	 */
	private String bankName;
	/**
	 * 通知状态
	 */
	private NotifyStatus notifyStatus = NotifyStatus.WAIT;
	/**
	 * 通知次数
	 */
	private int notifyCount;
	/**
	 * 通知时间
	 */
	private Date notifyTime;
	/**
	 * 付款审核原因
	 */
	private String remitAdutiReason;
	/** 支付接口编号 */
	private String interfaceInfoCode;

	public String getRequestNo() {
		return requestNo;
	}

	public void setRequestNo(String requestNo) {
		this.requestNo = requestNo;
	}

	public RequestType getRequestType() {
		return requestType;
	}

	public void setRequestType(RequestType requestType) {
		this.requestType = requestType;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public RequestStatus getStatus() {
		return status;
	}

	public void setStatus(RequestStatus status) {
		this.status = status;
	}

	public AuditStatus getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(AuditStatus auditStatus) {
		this.auditStatus = auditStatus;
	}

	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public AccountType getAccountType() {
		return accountType;
	}

	public void setAccountType(AccountType accountType) {
		this.accountType = accountType;
	}

	public CerType getCerType() {
		return cerType;
	}

	public void setCerType(CerType cerType) {
		this.cerType = cerType;
	}

	public String getCerNo() {
		return cerNo;
	}

	public void setCerNo(String cerNo) {
		this.cerNo = cerNo;
	}

	public String getFlowNo() {
		return flowNo;
	}

	public void setFlowNo(String flowNo) {
		this.flowNo = flowNo;
	}

	public Date getAuditDate() {
		return auditDate;
	}

	public void setAuditDate(Date auditDate) {
		this.auditDate = auditDate;
	}

	public Date getCompleteDate() {
		return completeDate;
	}

	public void setCompleteDate(Date completeDate) {
		this.completeDate = completeDate;
	}

	public String getCompleteMsg() {
		return completeMsg;
	}

	public void setCompleteMsg(String completeMsg) {
		this.completeMsg = completeMsg;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public CardType getCardType() {
		return cardType;
	}

	public void setCardType(CardType cardType) {
		this.cardType = cardType;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getValidity() {
		return validity;
	}

	public void setValidity(String validity) {
		this.validity = validity;
	}

	public String getCvv() {
		return cvv;
	}

	public void setCvv(String cvv) {
		this.cvv = cvv;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public double getFee() {
		return fee;
	}

	public void setFee(double fee) {
		this.fee = fee;
	}

	public NotifyStatus getNotifyStatus() {
		return notifyStatus;
	}

	public void setNotifyStatus(NotifyStatus notifyStatus) {
		this.notifyStatus = notifyStatus;
	}

	public int getNotifyCount() {
		return notifyCount;
	}

	public void setNotifyCount(int notifyCount) {
		this.notifyCount = notifyCount;
	}

	public Date getNotifyTime() {
		return notifyTime;
	}

	public void setNotifyTime(Date notifyTime) {
		this.notifyTime = notifyTime;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}
	
	public OwnerRole getOwnerRole() {
		return ownerRole;
	}

	public void setOwnerRole(OwnerRole ownerRole) {
		this.ownerRole = ownerRole;
	}
	
	public String getRemitAdutiReason() {
		return remitAdutiReason;
	}

	public void setRemitAdutiReason(String remitAdutiReason) {
		this.remitAdutiReason = remitAdutiReason;
	}

	public String getInterfaceInfoCode() {
		return interfaceInfoCode;
	}

	public void setInterfaceInfoCode(String interfaceInfoCode) {
		this.interfaceInfoCode = interfaceInfoCode;
	}

	@Override
	public String toString() {
		return "Request [requestNo=" + requestNo + ", requestType=" + requestType + ", amount=" + amount + ", fee=" + fee + ", cost=" + cost + ", description=" + description + ", status=" + status + ", completeMsg=" + completeMsg + ", auditStatus=" + auditStatus + ", ownerId=" + ownerId
				+ ", ownerRole=" + ownerRole + ", accountName=" + accountName + ", accountNo=" + accountNo + ", accountType=" + accountType + ", cardType=" + cardType + ", cerType=" + cerType + ", cerNo=" + cerNo + ", flowNo=" + flowNo + ", auditDate=" + auditDate + ", completeDate=" + completeDate
				+ ", operator=" + operator + ", bankCode=" + bankCode + ", validity=" + validity + ", cvv=" + cvv + ", bankName=" + bankName + ", notifyStatus=" + notifyStatus + ", notifyCount=" + notifyCount + ", notifyTime=" + notifyTime + ", remitAdutiReason=" + remitAdutiReason
				+ ", interfaceInfoCode=" + interfaceInfoCode + "]";
	}

}
