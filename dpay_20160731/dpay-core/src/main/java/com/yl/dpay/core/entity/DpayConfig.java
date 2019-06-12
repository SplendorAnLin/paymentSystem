package com.yl.dpay.core.entity;

import java.io.Serializable;

import com.yl.dpay.core.enums.RemitType;
import com.yl.dpay.core.enums.Status;

/**
 * 代付出款配置
 * 
 * @author 聚合支付有限公司
 * @since 2016年5月12日
 * @version V1.0.0
 */
public class DpayConfig extends BaseEntity implements Serializable{

	private static final long serialVersionUID = 1177254419817351823L;
	private RemitType remitType;	//付款类型
	private double minAmount;		//最小金额
	private double maxAmount;		//最大金额
	private double auditAmount;		//审核最小金额
	private Status reRemitFlag;		//是否支持重复出款
	private String startTime;		//出款开始时间
	private String endTime;			//出款结束时间
	private Status holidayStatus;	//假日付状态
	private Status status;			//出款状态

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public RemitType getRemitType() {
		return remitType;
	}

	public void setRemitType(RemitType remitType) {
		this.remitType = remitType;
	}

	public double getMinAmount() {
		return minAmount;
	}

	public void setMinAmount(double minAmount) {
		this.minAmount = minAmount;
	}

	public double getMaxAmount() {
		return maxAmount;
	}

	public void setMaxAmount(double maxAmount) {
		this.maxAmount = maxAmount;
	}

	public double getAuditAmount() {
		return auditAmount;
	}

	public void setAuditAmount(double auditAmount) {
		this.auditAmount = auditAmount;
	}

	public Status getReRemitFlag() {
		return reRemitFlag;
	}

	public void setReRemitFlag(Status reRemitFlag) {
		this.reRemitFlag = reRemitFlag;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public Status getHolidayStatus() {
		return holidayStatus;
	}

	public void setHolidayStatus(Status holidayStatus) {
		this.holidayStatus = holidayStatus;
	}

}
