package com.yl.receive.core.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 代收批次
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月25日
 * @version V1.0.0
 */
public class ReceiveBatch implements Serializable{

	private static final long serialVersionUID = -8663850541743644427L;

	/** 所有者编号 */
	private String ownerId;
	/** 批次号 */
	private String batchNo;
	/** 总金额 */
	private double totalAmount;
	/** 成功金额 */
	private double totalSuccessAmount;
	/** 失败金额 */
	private double totalFailAmount;
	/** 总手续费 */
	private double totalFee;
	/** 总笔数 */
	private int num;
	/** 成功笔数 */
	private int successNum;
	/** 失败笔数 */
	private int failNum;
	/** 创建时间 */
	private Date createTime;
	/** 最后修改时间 */
	private Date lastUpdateTime;
	/** 版本号 */
	private long version;
	public String getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}
	public String getBatchNo() {
		return batchNo;
	}
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}
	public double getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}
	public double getTotalSuccessAmount() {
		return totalSuccessAmount;
	}
	public void setTotalSuccessAmount(double totalSuccessAmount) {
		this.totalSuccessAmount = totalSuccessAmount;
	}
	public double getTotalFailAmount() {
		return totalFailAmount;
	}
	public void setTotalFailAmount(double totalFailAmount) {
		this.totalFailAmount = totalFailAmount;
	}
	public double getTotalFee() {
		return totalFee;
	}
	public void setTotalFee(double totalFee) {
		this.totalFee = totalFee;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public int getSuccessNum() {
		return successNum;
	}
	public void setSuccessNum(int successNum) {
		this.successNum = successNum;
	}
	public int getFailNum() {
		return failNum;
	}
	public void setFailNum(int failNum) {
		this.failNum = failNum;
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
	public long getVersion() {
		return version;
	}
	public void setVersion(long version) {
		this.version = version;
	}
	@Override
	public String toString() {
		return "ReceiveBatch [ownerId=" + ownerId + ", batchNo=" + batchNo + ", totalAmount=" + totalAmount + ", totalSuccessAmount=" + totalSuccessAmount
				+ ", totalFailAmount=" + totalFailAmount + ", totalFee=" + totalFee + ", num=" + num + ", successNum=" + successNum + ", failNum=" + failNum
				+ ", createTime=" + createTime + ", lastUpdateTime=" + lastUpdateTime + ", version=" + version + "]";
	}

}
