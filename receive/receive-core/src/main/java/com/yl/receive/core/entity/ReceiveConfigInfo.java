package com.yl.receive.core.entity;

import java.io.Serializable;
import java.util.Date;

import com.yl.receive.core.enums.ReceiveConfigStatus;

/**
 * 代收配置
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月25日
 * @version V1.0.0
 */
public class ReceiveConfigInfo implements Serializable {

	private static final long serialVersionUID = 4244450011086700551L;

	private Long id;
	/** 所有者编号 */
	private String ownerId;
	/** 日限额 */
	private double dailyMaxAmount;
	/** 单批次最大笔数 */
	private int singleBatchMaxNum;
	/** 单笔最大金额 */
	private double singleMaxAmount;
	/** 商户私钥证书 */
	private String privateCer;
	/** 商户公钥证书 */
	private String publicCer;
	/** 是否校验授权 */
	private String isCheckContract;
	/** 创建时间 */
	private Date createTime;
	/** 最后更新时间 */
	private Date lastUpdateTime;
	/** 状态 */
	private ReceiveConfigStatus status;
	/** 备注 */
	private String remark;
	/** 版本号 */
	private long version;
	/** ip */
	private String custIp;
	/** 域名 */
	private String domain;
	/**操作人*/
	private String oper;

	public String getOper() {
		return oper;
	}

	public void setOper(String oper) {
		this.oper = oper;
	}

	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}

	public double getDailyMaxAmount() {
		return dailyMaxAmount;
	}

	public void setDailyMaxAmount(double dailyMaxAmount) {
		this.dailyMaxAmount = dailyMaxAmount;
	}

	public int getSingleBatchMaxNum() {
		return singleBatchMaxNum;
	}

	public void setSingleBatchMaxNum(int singleBatchMaxNum) {
		this.singleBatchMaxNum = singleBatchMaxNum;
	}

	public double getSingleMaxAmount() {
		return singleMaxAmount;
	}

	public void setSingleMaxAmount(double singleMaxAmount) {
		this.singleMaxAmount = singleMaxAmount;
	}

	public String getPrivateCer() {
		return privateCer;
	}

	public void setPrivateCer(String privateCer) {
		this.privateCer = privateCer;
	}

	public String getPublicCer() {
		return publicCer;
	}

	public void setPublicCer(String publicCer) {
		this.publicCer = publicCer;
	}

	public String getIsCheckContract() {
		return isCheckContract;
	}

	public void setIsCheckContract(String isCheckContract) {
		this.isCheckContract = isCheckContract;
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

	public ReceiveConfigStatus getStatus() {
		return status;
	}

	public void setStatus(ReceiveConfigStatus status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCustIp() {
		return custIp;
	}

	public void setCustIp(String custIp) {
		this.custIp = custIp;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	@Override
	public String toString() {
		return "ReceiveConfigInfo [id=" + id + ", ownerId=" + ownerId + ", dailyMaxAmount=" + dailyMaxAmount + ", singleBatchMaxNum=" + singleBatchMaxNum + ", singleMaxAmount=" + singleMaxAmount + ", privateCer=" + privateCer + ", publicCer=" + publicCer + ", isCheckContract=" + isCheckContract
				+ ", createTime=" + createTime + ", lastUpdateTime=" + lastUpdateTime + ", status=" + status + ", remark=" + remark + ", version=" + version + ", custIp=" + custIp + ", domain=" + domain + ", oper=" + oper + "]";
	}


}
