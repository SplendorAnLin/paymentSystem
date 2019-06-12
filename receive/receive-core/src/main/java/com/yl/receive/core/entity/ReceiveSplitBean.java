package com.yl.receive.core.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 代收分润实体
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月25日
 * @version V1.0.0
 */
public class ReceiveSplitBean implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -5449966600564673831L;
	/** 所有者编号 */
	private String ownerId;
	/** 收款单号 */
	private String receiveId;
	/** 金额 */
	private double amount;
	/** 手续费 */
	private double fee;
	/** 代扣成本 */
	private double cost;
	/** 创建时间 */
	private Date createTime;
	/** 最后修改时间 */
	private Date lastUpdateTime;
	/** 请求状态 */
	private String receiveStatus;
	/** 支付类型 */
	private String payType;

	public ReceiveSplitBean() {}

	public ReceiveSplitBean(ReceiveRequest receiveRequest) {
		this.amount = receiveRequest.getAmount();
		this.cost = receiveRequest.getCost();
		this.createTime = receiveRequest.getCreateTime();
		this.fee = receiveRequest.getFee();
		this.lastUpdateTime = receiveRequest.getLastUpdateTime();
		this.ownerId = receiveRequest.getOwnerId();
		this.payType = "RECEIVE";
		this.receiveId = receiveRequest.getReceiveId();
		this.receiveStatus = receiveRequest.getOrderStatus().name();
	}

	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}

	public String getReceiveId() {
		return receiveId;
	}

	public void setReceiveId(String receiveId) {
		this.receiveId = receiveId;
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

	public String getReceiveStatus() {
		return receiveStatus;
	}

	public void setReceiveStatus(String receiveStatus) {
		this.receiveStatus = receiveStatus;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}
}
