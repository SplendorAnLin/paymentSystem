package com.yl.dpay.hessian.beans;

import java.io.Serializable;
import java.util.Date;

import com.yl.dpay.hessian.enums.RemitType;
import com.yl.dpay.hessian.enums.Status;

/**
 * 代付出款配置Bean
 * 
 * @author 聚合支付有限公司
 * @since 2016年5月15日
 * @version V1.0.0
 */
public class DpayConfigBean implements Serializable{

	private static final long serialVersionUID = 1177254419817351823L;
	
	private Long id;
	private Date createDate;		//创建时间
	private RemitType remitType;	//付款类型
	private double minAmount;		//最小金额
	private double maxAmount;		//最大金额
	private double auditAmount;		//审核最小金额
	private Status reRemitFlag;		//是否支持重复出款
	private String startTime;		//出款开始时间
	private String endTime;			//出款结束时间
	private Status holidayStatus;	//假日付状态
	private Status status;			//出款状态
	private String oper;			//操作员
	
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
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
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public String getOper() {
		return oper;
	}
	public void setOper(String oper) {
		this.oper = oper;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

}
