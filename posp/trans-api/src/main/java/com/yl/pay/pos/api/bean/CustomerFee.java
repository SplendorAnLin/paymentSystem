package com.yl.pay.pos.api.bean;

import java.util.Date;

/**
 * Title: 商户费率配置
 * Description:  
 * Copyright: Copyright (c)2011
 * Company: lepay
 * @author haitao.liu
 */
public class CustomerFee extends BaseEntity{

	private String code;				//编号 （唯一）	
	private String customerNo;			//商户编号
	private String mcc;					//行业编码
	private String issuer;				//发卡行
	private String cardType;			//卡种
	
	private String computeMode;	//计费方式
	private String rate;				//费率
	private Double fixedFee;			//单笔固定费用
	private Boolean upperLimit;			//是否有手续费上限
	private Boolean lowerLimit;			//是否有手续费下限
	private Double upperLimitFee;		//手续费上限
	private Double lowerLimitFee;		//手续费下限
	
	private Date effectTime;			//生效时间
	private Date expireTime;			//过期时间
	private Date createTime;			//创建时间
	private String status;				//状态
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	public String getCustomerNo() {
		return customerNo;
	}
	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}
	
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Boolean getUpperLimit() {
		return upperLimit;
	}
	public void setUpperLimit(Boolean upperLimit) {
		this.upperLimit = upperLimit;
	}
	
	public Double getUpperLimitFee() {
		return upperLimitFee;
	}
	public void setUpperLimitFee(Double upperLimitFee) {
		this.upperLimitFee = upperLimitFee;
	}
	
	public Double getLowerLimitFee() {
		return lowerLimitFee;
	}
	public void setLowerLimitFee(Double lowerLimitFee) {
		this.lowerLimitFee = lowerLimitFee;
	}
	
	public String getIssuer() {
		return issuer;
	}
	public void setIssuer(String issuer) {
		this.issuer = issuer;
	}
	public Date getEffectTime() {
		return effectTime;
	}
	public void setEffectTime(Date effectTime) {
		this.effectTime = effectTime;
	}
	public Date getExpireTime() {
		return expireTime;
	}
	public void setExpireTime(Date expireTime) {
		this.expireTime = expireTime;
	}
	public String getComputeMode() {
		return computeMode;
	}
	public String getStatus() {
		return status;
	}
	public void setComputeMode(String computeMode) {
		this.computeMode = computeMode;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCardType() {
		return cardType;
	}
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	public Double getFixedFee() {
		return fixedFee;
	}
	public void setFixedFee(Double fixedFee) {
		this.fixedFee = fixedFee;
	}
	public String getRate() {
		return rate;
	}
	public void setRate(String rate) {
		this.rate = rate;
	}
	public Boolean getLowerLimit() {
		return lowerLimit;
	}
	public void setLowerLimit(Boolean lowerLimit) {
		this.lowerLimit = lowerLimit;
	}
	public String getMcc() {
		return mcc;
	}
	public void setMcc(String mcc) {
		this.mcc = mcc;
	}
}
