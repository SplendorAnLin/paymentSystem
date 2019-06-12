package com.yl.receive.core.entity;

import java.io.Serializable;
import java.util.Date;

import com.yl.receive.core.enums.CerType;
import com.yl.receive.core.enums.ContractStatus;
import com.yl.receive.core.enums.SourceType;

/**
 * 签约信息
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月25日
 * @version V1.0.0
 */
public class ContractInfo implements Serializable {

	private static final long serialVersionUID = -2479295952372288646L;
	/** 所有者编号 */
	private String ownerId;
	/** 签约号 */
	private String contractId;
	/** 商户签约号 */
	private String requestId;
	/** 账号 */
	private String accountNo;
	/** 账户持有人 */
	private String accountName;
	/** 手机号 */
	private String mobile;
	/** 开户行名称 */
	private String openBankName;
	/** 银行代码 */
	private String bankCode;
	/** 所属省份 */
	private String province;
	/** 证件类型 */
	private CerType idType;
	/** 证件号码 */
	private String idCode;
	/** 授权书 */
	private String authImg;
	/** 证件扫描件 */
	private String idImg;
	/** 银行卡扫描件 */
	private String bankCardImg;
	/** 状态 */
	private ContractStatus status;
	/** 来源类型 */
	private SourceType sourceType;
	/** 创建时间 */
	private Date createTime;
	/** 最后更新时间 */
	private Date lastUpdateTime;
	/** 唯一索引 */
	private String UUID;
	/** 版本号 */
	private long version;
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
	public String getRequestId() {
		return requestId;
	}
	public void setRequestId(String requestId) {
		this.requestId = requestId;
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
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getOpenBankName() {
		return openBankName;
	}
	public void setOpenBankName(String openBankName) {
		this.openBankName = openBankName;
	}
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public CerType getIdType() {
		return idType;
	}
	public void setIdType(CerType idType) {
		this.idType = idType;
	}
	public String getIdCode() {
		return idCode;
	}
	public void setIdCode(String idCode) {
		this.idCode = idCode;
	}
	public String getAuthImg() {
		return authImg;
	}
	public void setAuthImg(String authImg) {
		this.authImg = authImg;
	}
	public String getIdImg() {
		return idImg;
	}
	public void setIdImg(String idImg) {
		this.idImg = idImg;
	}
	public String getBankCardImg() {
		return bankCardImg;
	}
	public void setBankCardImg(String bankCardImg) {
		this.bankCardImg = bankCardImg;
	}
	public ContractStatus getStatus() {
		return status;
	}
	public void setStatus(ContractStatus status) {
		this.status = status;
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
	public String getUUID() {
		return UUID;
	}
	public void setUUID(String uUID) {
		UUID = uUID;
	}
	public long getVersion() {
		return version;
	}
	public void setVersion(long version) {
		this.version = version;
	}
	
	@Override
	public String toString() {
		return "ContractInfo [ownerId=" + ownerId + ", contractId="
				+ contractId + ", requestId=" + requestId + ", accountNo="
				+ accountNo + ", accountName=" + accountName + ", mobile="
				+ mobile + ", openBankName=" + openBankName + ", bankCode="
				+ bankCode + ", province=" + province + ", idType=" + idType
				+ ", idCode=" + idCode + ", authImg=" + authImg + ", idImg="
				+ idImg + ", bankCardImg=" + bankCardImg + ", status=" + status
				+ ", sourceType=" + sourceType + ", createTime=" + createTime
				+ ", lastUpdateTime=" + lastUpdateTime + ", UUID=" + UUID
				+ ", version=" + version + "]";
	}

}
