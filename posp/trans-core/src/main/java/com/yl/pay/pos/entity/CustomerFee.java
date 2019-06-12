package com.yl.pay.pos.entity;

import com.yl.pay.pos.enums.ComputeFeeMode;
import com.yl.pay.pos.enums.Status;

import javax.persistence.*;
import java.util.Date;

/**
 * Title: 商户费率配置
 * Description:  
 * Copyright: Copyright (c)2011
 * Company: lepay
 * @author haitao.liu
 */

@Entity
@Table(name = "CUSTOMER_FEE")
public class CustomerFee extends BaseEntity{

	private String code;				//编号 （唯一）	
	private String customerNo;			//商户编号
	private String mcc;					//行业编码
	private String issuer;				//发卡行
	private String cardType;			//卡种
	
	private ComputeFeeMode computeMode;	//计费方式
	private String rate;				//费率
	private Double fixedFee;			//单笔固定费用
	private Boolean upperLimit;			//是否有手续费上限
	private Boolean lowerLimit;			//是否有手续费下限
	private Double upperLimitFee;		//手续费上限
	private Double lowerLimitFee;		//手续费下限
	
	private Date effectTime;			//生效时间
	private Date expireTime;			//过期时间
	private Date createTime;			//创建时间
	private Status status;				//状态
	
	@Column(name = "CODE", length = 15,unique = true)
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	@Column(name = "CUSTOMER_NO", length = 30)
	public String getCustomerNo() {
		return customerNo;
	}
	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}
	
	@Column(name = "CREATE_TIME", columnDefinition = "DATETIME")
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "UPPER_LIMIT")
	public Boolean getUpperLimit() {
		return upperLimit;
	}
	public void setUpperLimit(Boolean upperLimit) {
		this.upperLimit = upperLimit;
	}
	
	@Column(name = "UPPER_LIMIT_FEE", columnDefinition = "double", length = 11, scale = 5)
	public Double getUpperLimitFee() {
		return upperLimitFee;
	}
	public void setUpperLimitFee(Double upperLimitFee) {
		this.upperLimitFee = upperLimitFee;
	}
	
	@Column(name = "LOWER_LIMIT_FEE", columnDefinition = "double", length = 11, scale = 5)
	public Double getLowerLimitFee() {
		return lowerLimitFee;
	}
	public void setLowerLimitFee(Double lowerLimitFee) {
		this.lowerLimitFee = lowerLimitFee;
	}
	
	@Column(name="ISSUER",length = 30)
	public String getIssuer() {
		return issuer;
	}
	public void setIssuer(String issuer) {
		this.issuer = issuer;
	}
	
	@Column(name = "EFFECT_TIME",columnDefinition = "DATETIME")
	public Date getEffectTime() {
		return effectTime;
	}
	public void setEffectTime(Date effectTime) {
		this.effectTime = effectTime;
	}
	
	@Column(name = "EXPIRE_TIME",columnDefinition = "DATETIME")
	public Date getExpireTime() {
		return expireTime;
	}
	public void setExpireTime(Date expireTime) {
		this.expireTime = expireTime;
	}
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "COMPUTE_MODE", columnDefinition = "VARCHAR(30)")
	public ComputeFeeMode getComputeMode() {
		return computeMode;
	}
	public void setComputeMode(ComputeFeeMode computeMode) {
		this.computeMode = computeMode;
	}
	
	@Column(name="CARD_TYPE",length = 30)
	public String getCardType() {
		return cardType;
	}
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	
	@Column(name = "FIXED_FEE", columnDefinition = "double", length = 11, scale = 5)
	public Double getFixedFee() {
		return fixedFee;
	}
	public void setFixedFee(Double fixedFee) {
		this.fixedFee = fixedFee;
	}
	
	@Column(name = "RATE", length = 20)
	public String getRate() {
		return rate;
	}
	public void setRate(String rate) {
		this.rate = rate;
	}
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "STATUS", columnDefinition = "VARCHAR(30)")
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	
	@Column(name = "LOWER_LIMIT")
	public Boolean getLowerLimit() {
		return lowerLimit;
	}
	public void setLowerLimit(Boolean lowerLimit) {
		this.lowerLimit = lowerLimit;
	}
	
	@Column(name = "MCC", length = 30)
	public String getMcc() {
		return mcc;
	}
	public void setMcc(String mcc) {
		this.mcc = mcc;
	}
	
	
	
}
