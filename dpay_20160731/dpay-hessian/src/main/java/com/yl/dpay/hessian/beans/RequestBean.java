package com.yl.dpay.hessian.beans;

import java.io.Serializable;
import java.util.Date;

import com.yl.dpay.hessian.enums.NotifyStatus;

/**
 * 代付信息bean
 * 
 * @author 聚合支付有限公司
 * @since 2016年5月15日
 * @version V1.0.0
 */
public class RequestBean implements Serializable{

	private static final long serialVersionUID = -3753342796293434525L;
	
	/**
	 * 请求编号
	 */
	private String requestNo;
	/**
	 * 请求类型
	 */
	private String requestType;
	/**
	 * 金额
	 */
	private Double amount;
	/**
	 * 手续费
	 */
	private double fee;
	/**
	 * 成本
	 */
	private double cost;
	/**
	 * 创建时间
	 */
	private Date createDate;
	/**
	 * 描述
	 */
	private String description;
	/**
	 * 状态
	 */
	private String status;
	/**
	 * 完成描述
	 */
	private String completeMsg;
	/**
	 * 审核状态
	 */
	private String auditStatus;
	/**
	 * 持有者ID
	 */
	private String ownerId;
	/**
	 * 持有者角色
	 */
	private String ownerRole;
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
	private String accountType;
	/**
	 * 证件类型
	 */
	private String cerType;
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
	 * 卡类型
	 */
	private String cardType;
	/**
	 * 银行编号
	 */
	private String bankCode;
	/**
	 * 银行名称
	 */
	private String bankName;
	/**
	 * 有效期
	 */
	private String validity;
	/**
	 * CVV2
	 */
	private String cvv;
	/**
	 * 修改原因
	 */
	private String reason;
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
	 * 是否保存联系人
	 */
	private boolean saveInfo;
	/**
	 * 付款审核原因
	 */
	private String remitAdutiReason;


	
	public boolean isSaveInfo() {
		return saveInfo;
	}
	public void setSaveInfo(boolean saveInfo) {
		this.saveInfo = saveInfo;
	}
	public String getRequestNo() {
		return requestNo;
	}
	public void setRequestNo(String requestNo) {
		this.requestNo = requestNo;
	}
	public String getRequestType() {
		return requestType;
	}
	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCompleteMsg() {
		return completeMsg;
	}
	public void setCompleteMsg(String completeMsg) {
		this.completeMsg = completeMsg;
	}
	public String getAuditStatus() {
		return auditStatus;
	}
	public void setAuditStatus(String auditStatus) {
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
	public String getAccountType() {
		return accountType;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	public String getCerType() {
		return cerType;
	}
	public void setCerType(String cerType) {
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
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getCardType() {
		return cardType;
	}
	public void setCardType(String cardType) {
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
	
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
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
	public String getOwnerRole() {
		return ownerRole;
	}
	public void setOwnerRole(String ownerRole) {
		this.ownerRole = ownerRole;
	}
	
	public String getRemitAdutiReason() {
		return remitAdutiReason;
	}
	public void setRemitAdutiReason(String remitAdutiReason) {
		this.remitAdutiReason = remitAdutiReason;
	}
	
	public double getFee() {
		return fee;
	}
	public void setFee(double fee) {
		this.fee = fee;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	public double getCost() {
		return cost;
	}
	public void setCost(double cost) {
		this.cost = cost;
	}
	@Override
	public String toString() {
		return "RequestBean [requestNo=" + requestNo + ", requestType="
				+ requestType + ", amount=" + amount + ", description="
				+ description + ", status=" + status + ", completeMsg="
				+ completeMsg + ", auditStatus=" + auditStatus + ", ownerId="
				+ ownerId + ", ownerRole=" + ownerRole + ", accountName="
				+ accountName + ", accountNo=" + accountNo + ", accountType="
				+ accountType + ", cerType=" + cerType + ", cerNo=" + cerNo
				+ ", flowNo=" + flowNo + ", auditDate=" + auditDate
				+ ", completeDate=" + completeDate + ", operator=" + operator
				+ ", cardType=" + cardType + ", bankCode=" + bankCode
				+ ", bankName=" + bankName + ", validity=" + validity
				+ ", cvv=" + cvv + ", reason=" + reason + ", notifyStatus="
				+ notifyStatus + ", notifyCount=" + notifyCount
				+ ", notifyTime=" + notifyTime + "]";
	}
}