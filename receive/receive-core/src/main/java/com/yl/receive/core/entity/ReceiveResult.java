package com.yl.receive.core.entity;

import java.io.Serializable;
import java.util.Date;

import com.yl.receive.core.enums.AccountType;
import com.yl.receive.core.enums.ReceiveStatus;
import com.yl.receive.core.enums.RequestClearingStatus;
import com.yl.receive.core.enums.SourceType;

/**
 * 代收结果
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月25日
 * @version V1.0.0
 */
public class ReceiveResult implements Serializable {

	private static final long serialVersionUID = -4735953880959963726L;
	/** 所有者编号 */
	private String ownerId;
	/** 支付接口订单号 */
	private String interfaceRequestID;
	/** 通道编号 */
	private String channelId;
	/** 收款单号 */
	private String receiveId;
	/** 商家订单号 */
	private String seqId;
	/** 批次号 */
	private String batchNo;
	/** 账户号 */
	private String accountNo;
	/** 账户名 */
	private String accountName;
	/** 账户类型 */
	private AccountType accType;
	/** 金额 */
	private double amount;
	/** 手续费 */
	private double fee;
	/** 通知地址 */
	private String notifyUrl;
	/** 用途 */
	private String usage;
	/** 备注 */
	private String remark;
	/** 数据来源 */
	private SourceType sourceType;
	/** 创建时间 */
	private Date createTime;
	/** 完成时间 */
	private Date lastUpdateTime;
	/** 请求状态 */
	private ReceiveStatus receiveStatus;
	/** 清算状态 */
	private RequestClearingStatus clearStatus;
	/** 唯一索引 */
	private String UUID;
	/** 是否通知 */
	private boolean isNotify;
	/** 版本号 */
	private long version;
	/** 失败信息 */
	private String failMsg;
	public String getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}
	public String getInterfaceRequestID() {
		return interfaceRequestID;
	}
	public void setInterfaceRequestID(String interfaceRequestID) {
		this.interfaceRequestID = interfaceRequestID;
	}
	public String getChannelId() {
		return channelId;
	}
	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}
	public String getReceiveId() {
		return receiveId;
	}
	public void setReceiveId(String receiveId) {
		this.receiveId = receiveId;
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
	public String getUsage() {
		return usage;
	}
	public void setUsage(String usage) {
		this.usage = usage;
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
	public String getUUID() {
		return UUID;
	}
	public void setUUID(String uUID) {
		UUID = uUID;
	}
	public boolean isNotify() {
		return isNotify;
	}
	public void setNotify(boolean isNotify) {
		this.isNotify = isNotify;
	}
	public long getVersion() {
		return version;
	}
	public void setVersion(long version) {
		this.version = version;
	}
	public String getFailMsg() {
		return failMsg;
	}
	public void setFailMsg(String failMsg) {
		this.failMsg = failMsg;
	}
	public String getBatchNo() {
		return batchNo;
	}
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}
	public ReceiveResult() {
		super();
	}
	
	@Override
	public String toString() {
		return "ReceiveResult [ownerId=" + ownerId + ", interfaceRequestID="
				+ interfaceRequestID + ", channelId=" + channelId
				+ ", receiveId=" + receiveId + ", seqId=" + seqId
				+ ", batchNo=" + batchNo + ", accountNo=" + accountNo
				+ ", accountName=" + accountName + ", accType=" + accType
				+ ", amount=" + amount + ", fee=" + fee + ", notifyUrl="
				+ notifyUrl + ", usage=" + usage + ", remark=" + remark
				+ ", sourceType=" + sourceType + ", createTime=" + createTime
				+ ", lastUpdateTime=" + lastUpdateTime + ", receiveStatus="
				+ receiveStatus + ", clearStatus=" + clearStatus + ", UUID="
				+ UUID + ", isNotify=" + isNotify + ", version=" + version
				+ ", failMsg=" + failMsg + "]";
	}

}
