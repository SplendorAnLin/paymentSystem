package com.yl.pay.pos.entity;

import com.yl.pay.pos.enums.LimitAmountControlRole;
import com.yl.pay.pos.enums.Status;
import com.yl.pay.pos.enums.TimeScopeType;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "CARD_AMOUNT_LIMIT")
public class CardAmountLimit extends BaseEntity{
	
	private String pan;								     //卡号 
	private LimitAmountControlRole controlRole;			//控制角色
	private TimeScopeType timeScopeType;				//时间范围类型
	private Double accumulatedAmount;					//累计金额
	private Double limitAmount;							//限制金额
	private Date lastTransTime;							//最后交易时间
	private Date createTime;							//创建时间
	private Status status;								//状态
	
	@Column(name = "pan", length = 50)
	public String getPan() {
		return pan;
	}
	public void setPan(String pan) {
		this.pan = pan;
	}
	@Enumerated(value = EnumType.STRING)
	@Column(name = "CONTROL_ROLE", columnDefinition = "VARCHAR(30)")
	public LimitAmountControlRole getControlRole() {
		return controlRole;
	}
	public void setControlRole(LimitAmountControlRole controlRole) {
		this.controlRole = controlRole;
	}
	
	@Column(name = "LIMIT_AMOUNT",columnDefinition = "double", length = 11, scale = 2)
	public Double getLimitAmount() {
		return limitAmount;
	}
	public void setLimitAmount(Double limitAmount) {
		this.limitAmount = limitAmount;
	}
	
	
	
	@Column(name = "CREATE_TIME", columnDefinition = "DATETIME")
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "STATUS", columnDefinition = "VARCHAR(20)")
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "TIME_SCOPE_TYPE", columnDefinition = "VARCHAR(20)")
	public TimeScopeType getTimeScopeType() {
		return timeScopeType;
	}
	public void setTimeScopeType(TimeScopeType timeScopeType) {
		this.timeScopeType = timeScopeType;
	}
	
	@Column(name = "ACCUMULATED_AMOUNT",columnDefinition = "double", length = 11, scale = 2)
	public Double getAccumulatedAmount() {
		return accumulatedAmount;
	}
	public void setAccumulatedAmount(Double accumulatedAmount) {
		this.accumulatedAmount = accumulatedAmount;
	}
	
	@Column(name = "LAST_TRANS_TIME", columnDefinition = "DATETIME")
	public Date getLastTransTime() {
		return lastTransTime;
	}
	public void setLastTransTime(Date lastTransTime) {
		this.lastTransTime = lastTransTime;
	}
	
	
	
	
	


}
