package com.yl.receive.core.entity;

import java.io.Serializable;
import java.util.Date;

import com.yl.receive.core.enums.AccountType;
import com.yl.receive.core.enums.ApplyStatus;
import com.yl.receive.core.enums.AuditStatus;
import com.yl.receive.core.enums.CardType;
import com.yl.receive.core.enums.CerType;
import com.yl.receive.core.enums.OrderStatus;
import com.yl.receive.core.enums.OwnerRole;
import com.yl.receive.core.enums.RequestClearingStatus;
import com.yl.receive.core.enums.SourceType;

/**
 * 代收请求
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月25日
 * @version V1.0.0
 */
public class ReceiveRequest implements Serializable {

	private static final long serialVersionUID = -6423306908301870095L;

	private Long id;
	/** 银行编号 */
	private String payerBankNo;
	/** 所有者编号 */
	private String ownerId;
	/** 持有者角色 */
	private OwnerRole ownerRole;
	/** 签约号（授权码） */
	private String contractId;
	/** 收款单号 */
	private String receiveId;
	/** 批次号 */
	private String batchNo;
	/** 商家订单号 */
	private String seqId;
	/** 账户号 */
	private String accountNo;
	/** 账户号 */
	private String accountNoEncrpty;
	/** 账户名 */
	private String accountName;
	/** 账户类型 */
	private AccountType accType;
	/** 账号类型 */
	private CardType accNoType;
	/** 金额 */
	private double amount;
	/** 手续费 */
	private double fee;
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
	/** 版本号 */
	private long version;
	/** 开户行 */
	private String openBank;
	/** 所有者名称 */
	private String ownerName;
	/** 省份 */
	private String province;
	/** 城市 */
	private String city;
	/** 证件类型 */
	private CerType certificatesType;
	/** 证件号码 */
	private String certificatesCode;
	/** 证件号码加密 */
	private String certificatesCodeEncrpty;
	/** 代扣成本 */
	private double cost;
	/** 状态 */
	private OrderStatus orderStatus;
	/** 支付接口编号 */
	private String interfaceRequestId;
	/** 电话 */
	private String phone;

	private RequestClearingStatus clearingStatus = RequestClearingStatus.UN_CLEARING;

	public String getPayerBankNo() {
		return payerBankNo;
	}

	public void setPayerBankNo(String payerBankNo) {
		this.payerBankNo = payerBankNo;
	}

	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
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

	public String getSeqId() {
		return seqId;
	}

	public void setSeqId(String seqId) {
		this.seqId = seqId;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getAccountNoEncrpty() {
		return accountNoEncrpty;
	}

	public void setAccountNoEncrpty(String accountNoEncrpty) {
		this.accountNoEncrpty = accountNoEncrpty;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public AccountType getAccType() {
		return accType;
	}

	public void setAccType(AccountType accType) {
		this.accType = accType;
	}

	public CardType getAccNoType() {
		return accNoType;
	}

	public void setAccNoType(CardType accNoType) {
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

	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
	}

	public String getOpenBank() {
		return openBank;
	}

	public void setOpenBank(String openBank) {
		this.openBank = openBank;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
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

	public CerType getCertificatesType() {
		return certificatesType;
	}

	public void setCertificatesType(CerType certificatesType) {
		this.certificatesType = certificatesType;
	}

	public String getCertificatesCode() {
		return certificatesCode;
	}

	public void setCertificatesCode(String certificatesCode) {
		this.certificatesCode = certificatesCode;
	}

	public String getCertificatesCodeEncrpty() {
		return certificatesCodeEncrpty;
	}

	public void setCertificatesCodeEncrpty(String certificatesCodeEncrpty) {
		this.certificatesCodeEncrpty = certificatesCodeEncrpty;
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

	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getInterfaceRequestId() {
		return interfaceRequestId;
	}

	public void setInterfaceRequestId(String interfaceRequestId) {
		this.interfaceRequestId = interfaceRequestId;
	}

	public RequestClearingStatus getClearingStatus() {
		return clearingStatus;
	}

	public void setClearingStatus(RequestClearingStatus clearingStatus) {
		this.clearingStatus = clearingStatus;
	}

	@Override
	public String toString() {
		return "ReceiveRequest [id=" + id + ", payerBankNo=" + payerBankNo + ", ownerId=" + ownerId + ", ownerRole=" + ownerRole + ", contractId=" + contractId + ", receiveId=" + receiveId + ", batchNo=" + batchNo + ", seqId=" + seqId + ", accountNo=" + accountNo + ", accountNoEncrpty="
				+ accountNoEncrpty + ", accountName=" + accountName + ", accType=" + accType + ", accNoType=" + accNoType + ", amount=" + amount + ", fee=" + fee + ", notifyUrl=" + notifyUrl + ", remark=" + remark + ", sourceType=" + sourceType + ", createTime=" + createTime + ", lastUpdateTime="
				+ lastUpdateTime + ", auditStatus=" + auditStatus + ", applyStatus=" + applyStatus + ", version=" + version + ", openBank=" + openBank + ", ownerName=" + ownerName + ", province=" + province + ", city=" + city + ", certificatesType=" + certificatesType + ", certificatesCode="
				+ certificatesCode + ", certificatesCodeEncrpty=" + certificatesCodeEncrpty + ", cost=" + cost + ", orderStatus=" + orderStatus + ", interfaceRequestId=" + interfaceRequestId + ", phone=" + phone + ", clearingStatus=" + clearingStatus + "]";
	}

}
