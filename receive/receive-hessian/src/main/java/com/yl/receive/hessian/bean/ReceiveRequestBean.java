package com.yl.receive.hessian.bean;

import java.io.Serializable;
import java.util.Date;

import com.yl.receive.hessian.enums.AccNoType;
import com.yl.receive.hessian.enums.AccType;
import com.yl.receive.hessian.enums.ApplyStatus;
import com.yl.receive.hessian.enums.AuditStatus;
import com.yl.receive.hessian.enums.OrderStatus;
import com.yl.receive.hessian.enums.OwnerRole;
import com.yl.receive.hessian.enums.ReceiveStatus;
import com.yl.receive.hessian.enums.RequestClearingStatus;
import com.yl.receive.hessian.enums.ResponseMsg;
import com.yl.receive.hessian.enums.SourceType;

/**
 * 代收请求实体
 * 
 * @author 聚合支付有限公司
 * @since 2016年11月13日
 * @version V1.0.0
 */
public class ReceiveRequestBean implements Serializable {

	private static final long serialVersionUID = -6146872903512061861L;

	/** 所有者编号 */
	private String customerNo;
	/** 所有者角色 */
	private OwnerRole ownerRole;
	/** 签约号（授权码） */
	private String contractId;
	/** 收款单号 */
	private String receiveId;
	/** 批次号 */
	private String batchNo;
	/** 商家订单号 */
	private String customerOrderCode;
	/** 账户号 */
	private String accountNo;
	/** 账户名 */
	private String accountName;
	/** 账户类型 */
	private AccType accType;
	/** 账号类型 */
	private AccNoType accNoType;
	/** 金额 */
	private double amount;
	/** 手续费 */
	private double fee;
	/** 成本 */
	private double cost;
	/** 通知地址 */
	private String notifyUrl;
	/** 备注 */
	private String remark;
	/** 数据来源 */
	private SourceType sourceType;
	/** 创建时间 */
	private Date createTime;
	/** 最后修改时间 */
	private Date lastUpdateTime;
	/** 审核状态 */
	private AuditStatus auditStatus;
	/** 请求状态 */
	private ApplyStatus applyStatus;
	/** 代收状态 */
	private ReceiveStatus receiveStatus;
	/** 清算状态 */
	private RequestClearingStatus clearStatus;
	/** 响应码 */
	private ResponseMsg responseMsg;
	/** 开户行 */
	private String openBank;
	/** 失败信息 */
	private String failMsg;
	/** 通道编号 */
	private String channelId;
	/** 省份 */
	private String province;
	/** 城市 */
	private String city;
	/** 证件类型 */
	private String certificatesType;
	/** 证件号码 */
	private String certificatesCode;
	/** 通知号码 */
	private String phone;
	/** 银行机构编码 */
	private String payerBankNo;
	/** ip */
	private String ip;
	/** 状态 */
	private OrderStatus orderStatus;

	public String getCustomerNo() {
		return customerNo;
	}

	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}

	public String getContractId() {
		return contractId;
	}

	public void setContractId(String contractId) {
		this.contractId = contractId;
	}

	public String getReceiveId() {
		return receiveId;
	}

	public void setReceiveId(String receiveId) {
		this.receiveId = receiveId;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public String getCustomerOrderCode() {
		return customerOrderCode;
	}

	public void setCustomerOrderCode(String customerOrderCode) {
		this.customerOrderCode = customerOrderCode;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public AccType getAccType() {
		return accType;
	}

	public void setAccType(AccType accType) {
		this.accType = accType;
	}

	public AccNoType getAccNoType() {
		return accNoType;
	}

	public void setAccNoType(AccNoType accNoType) {
		this.accNoType = accNoType;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public double getFee() {
		return fee;
	}

	public void setFee(double fee) {
		this.fee = fee;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public String getNotifyUrl() {
		return notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public SourceType getSourceType() {
		return sourceType;
	}

	public void setSourceType(SourceType sourceType) {
		this.sourceType = sourceType;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public AuditStatus getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(AuditStatus auditStatus) {
		this.auditStatus = auditStatus;
	}

	public ApplyStatus getApplyStatus() {
		return applyStatus;
	}

	public void setApplyStatus(ApplyStatus applyStatus) {
		this.applyStatus = applyStatus;
	}

	public ReceiveStatus getReceiveStatus() {
		return receiveStatus;
	}

	public void setReceiveStatus(ReceiveStatus receiveStatus) {
		this.receiveStatus = receiveStatus;
	}

	public RequestClearingStatus getClearStatus() {
		return clearStatus;
	}

	public void setClearStatus(RequestClearingStatus clearStatus) {
		this.clearStatus = clearStatus;
	}

	public ResponseMsg getResponseMsg() {
		return responseMsg;
	}

	public void setResponseMsg(ResponseMsg responseMsg) {
		this.responseMsg = responseMsg;
	}

	public String getOpenBank() {
		return openBank;
	}

	public void setOpenBank(String openBank) {
		this.openBank = openBank;
	}

	public String getFailMsg() {
		return failMsg;
	}

	public void setFailMsg(String failMsg) {
		this.failMsg = failMsg;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCertificatesType() {
		return certificatesType;
	}

	public void setCertificatesType(String certificatesType) {
		this.certificatesType = certificatesType;
	}

	public String getCertificatesCode() {
		return certificatesCode;
	}

	public void setCertificatesCode(String certificatesCode) {
		this.certificatesCode = certificatesCode;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPayerBankNo() {
		return payerBankNo;
	}

	public void setPayerBankNo(String payerBankNo) {
		this.payerBankNo = payerBankNo;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public OwnerRole getOwnerRole() {
		return ownerRole;
	}

	public void setOwnerRole(OwnerRole ownerRole) {
		this.ownerRole = ownerRole;
	}

	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}

	@Override
	public String toString() {
		return "ReceiveRequestBean [customerNo=" + customerNo + ", ownerRole=" + ownerRole + ", contractId=" + contractId + ", receiveId=" + receiveId + ", batchNo=" + batchNo + ", customerOrderCode=" + customerOrderCode + ", accountNo=" + accountNo + ", accountName=" + accountName + ", accType="
				+ accType + ", accNoType=" + accNoType + ", amount=" + amount + ", fee=" + fee + ", cost=" + cost + ", notifyUrl=" + notifyUrl + ", remark=" + remark + ", sourceType=" + sourceType + ", createTime=" + createTime + ", lastUpdateTime=" + lastUpdateTime + ", auditStatus=" + auditStatus
				+ ", applyStatus=" + applyStatus + ", receiveStatus=" + receiveStatus + ", clearStatus=" + clearStatus + ", responseMsg=" + responseMsg + ", openBank=" + openBank + ", failMsg=" + failMsg + ", channelId=" + channelId + ", province=" + province + ", city=" + city
				+ ", certificatesType=" + certificatesType + ", certificatesCode=" + certificatesCode + ", phone=" + phone + ", payerBankNo=" + payerBankNo + ", ip=" + ip + "]";
	}

}
