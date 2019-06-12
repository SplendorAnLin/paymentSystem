package com.yl.receive.core.entity;

import java.io.Serializable;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * 代收请求（接口）
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月25日
 * @version V1.0.0
 */
public class InterfaceReceiveRequest implements Serializable {

	private static final long serialVersionUID = 5106056370114532219L;
	/** 所有者编号 */
	@NotNull
	private String partnerId;
	/** 所有者名称 */
	@NotNull
	private String partnerName;
	/** 签约号（授权码） */
	private String contractId;
	/** 账户号 */
	@NotNull
	private String accountNo;
	/** 账户名 */
	@NotNull
	private String accountName;
	/** 账户类型 */
	@NotNull
	@Pattern(regexp = "INDIVIDUAL|OPEN", message = "must contain only  INDIVIDUAL  and OPEN")
	private String accType;
	/** 账号类型 */
	@NotNull
	private String accNoType;
	/** 金额 */
	@NotNull
	@Digits(integer = 9, fraction = 2)
	private double amount;
	/** 手续费 */
	@Digits(integer = 9, fraction = 2)
	private double fee;
	/** 币种 */
	@NotNull
	@Pattern(regexp = "CNY", message = "must contain only  CNY")
	private String curType;
	/** 开户行 */
	@NotNull
	private String openBank;
	/** 证件类型 */
	@NotNull
	private String certificatesType;
	/** 证件编号 */
	@NotNull
	private String certificatesCode;
	/** 省份 */
	@NotNull
	private String province;
	/** 银行机构编码 */
	@NotNull
	private String payerBankNo;
	/** 批次号 */
	@NotNull
	private String batchNo;
	/** 商家订单号 */
	@NotNull
	private String seqId;
	/** 收款单号 */
	private String receiveId;
	/** 通知地址 */
	private String notifyUrl;
	/** 用途 */
	private String usage;
	/** 备注 */
	private String remark;
	/** 创建时间 */
	private String createTime;
	/** 请求状态 */
	private String applyStatus;
	/** 代收状态 */
	private String receiveStatus;
	/** 清算状态 */
	private String clearStatus;
	/** 所属银行 */
	private String belongsBank;
	/** 城市 */
	private String city;
	/** 响应码 */
	private String responseCode;
	/** 响应信息 */
	private String responseMsg;
	/** 手机号 */
	private String phone;

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

	public String getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
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

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getAccType() {
		return accType;
	}

	public void setAccType(String accType) {
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

	public String getCurType() {
		return curType;
	}

	public void setCurType(String curType) {
		this.curType = curType;
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

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getApplyStatus() {
		return applyStatus;
	}

	public void setApplyStatus(String applyStatus) {
		this.applyStatus = applyStatus;
	}

	public String getReceiveStatus() {
		return receiveStatus;
	}

	public void setReceiveStatus(String receiveStatus) {
		this.receiveStatus = receiveStatus;
	}

	public String getClearStatus() {
		return clearStatus;
	}

	public void setClearStatus(String clearStatus) {
		this.clearStatus = clearStatus;
	}

	public String getOpenBank() {
		return openBank;
	}

	public void setOpenBank(String openBank) {
		this.openBank = openBank;
	}

	public String getBelongsBank() {
		return belongsBank;
	}

	public void setBelongsBank(String belongsBank) {
		this.belongsBank = belongsBank;
	}

	public String getPartnerName() {
		return partnerName;
	}

	public void setPartnerName(String partnerName) {
		this.partnerName = partnerName;
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

	public String getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	public String getResponseMsg() {
		return responseMsg;
	}

	public void setResponseMsg(String responseMsg) {
		this.responseMsg = responseMsg;
	}

	public String getAccNoType() {
		return accNoType;
	}

	public void setAccNoType(String accNoType) {
		this.accNoType = accNoType;
	}

	public String getPayerBankNo() {
		return payerBankNo;
	}

	public void setPayerBankNo(String payerBankNo) {
		this.payerBankNo = payerBankNo;
	}
}
