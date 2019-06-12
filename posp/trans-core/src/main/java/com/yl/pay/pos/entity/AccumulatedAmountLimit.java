package com.yl.pay.pos.entity;

import com.yl.pay.pos.enums.LimitAmountControlRole;
import com.yl.pay.pos.enums.Status;
import com.yl.pay.pos.enums.TimeScopeType;

import javax.persistence.*;
import java.util.Date;


/**
 * Title: 累计交易金额限制
 * Description: 
 * Copyright: Copyright (c)2011
 * Company: com.yl.pay
 * @author jun
 */

@Entity
@Table(name = "ACCUMULATED_AMOUNT_LIMIT")
public class AccumulatedAmountLimit extends BaseEntity{
	
	private String code;								//编号（唯一）
	private String bizType;								//A 业务类型
	private String mcc;									//B 行业类型
	private String bankCode;							//C 银行编号
	private String cardType;							//D 卡类型
	private String entryCombine;						//条件组合类型 A|B|C|D
	private String ownerId;								//持有者ID 
	private LimitAmountControlRole controlRole;			//控制角色
	private TimeScopeType timeScopeType;				//时间范围类型
	private Double accumulatedAmount;					//累计金额
	private Double limitAmount;							//限制金额
	private Date lastTransTime;							//最后交易时间
	private Date expireTime;							//过期时间
	private Date effectTime;							//生效时间
	private Date createTime;							//创建时间
	private Status status;								//状态
	private String cardForm;							//卡形式
	
	@Column(name = "CODE", length = 20,unique = true)
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	@Column(name = "BIZ_TYPE", length = 50)
	public String getBizType() {
		return bizType;
	}
	public void setBizType(String bizType) {
		this.bizType = bizType;
	}
	
	@Column(name = "MCC", length = 10)
	public String getMcc() {
		return mcc;
	}
	public void setMcc(String mcc) {
		this.mcc = mcc;
	}
	
	@Column(name = "BANK_CODE", length = 30)
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	
	@Column(name = "CARD_TYPE", length = 20)
	public String getCardType() {
		return cardType;
	}
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	
	@Column(name = "ENTRY_COMBINE", length = 30)
	public String getEntryCombine() {
		return entryCombine;
	}
	public void setEntryCombine(String entryCombine) {
		this.entryCombine = entryCombine;
	}
	
	@Column(name = "OWNER_ID", length = 50)
	public String getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "CONTROL_ROLE", columnDefinition = "VARCHAR(30)")
	public LimitAmountControlRole getControlRole() {
		return controlRole;
	}
	public void setControlRole(LimitAmountControlRole controlRole) {
		this.controlRole = controlRole;
	}
	
	@Column(name = "LIMIT_AMOUNT",columnDefinition = "int", length = 11)
	public Double getLimitAmount() {
		return limitAmount;
	}
	public void setLimitAmount(Double limitAmount) {
		this.limitAmount = limitAmount;
	}
	
	@Column(name = "EXPIRE_TIME", columnDefinition = "DATETIME")
	public Date getExpireTime() {
		return expireTime;
	}
	public void setExpireTime(Date expireTime) {
		this.expireTime = expireTime;
	}
	
	@Column(name = "EFFECT_TIME", columnDefinition = "DATETIME")
	public Date getEffectTime() {
		return effectTime;
	}
	public void setEffectTime(Date effectTime) {
		this.effectTime = effectTime;
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
	
	@Column(name = "ACCUMULATED_AMOUNT",columnDefinition = "int", length = 11)
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
	
	@Column(name = "CARD_FORM", length = 50)
	public String getCardForm() {
		return cardForm;
	}
	public void setCardForm(String cardForm) {
		this.cardForm = cardForm;
	}
	
	
	
}
