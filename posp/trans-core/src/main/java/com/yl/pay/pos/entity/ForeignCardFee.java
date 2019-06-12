package com.yl.pay.pos.entity;

import com.yl.pay.pos.enums.ComputeFeeMode;
import com.yl.pay.pos.enums.Status;

import javax.persistence.*;

/**
 * Title: 银行通道费率规则配置
 * Description: 
 * Copyright: Copyright (c)2011
 * Company: com.yl.pay
 * @author jun
 */

@Entity
@Table(name = "FOREIGN_CARD_FEE")
public class ForeignCardFee extends BaseEntity{
	
	private String mccCategory;				//行业类别
	
	private ComputeFeeMode computeFeeMode;	//计费方式
	private String rate;					//费率
	private Double fixedFee;				//单笔固定费用
	private Double upperLimitFee;			//手续费上限
	private Double lowerLimitFee;			//手续费下限
	private Status status;					//状态	
	
	@Column(name = "MCC_CATEGORY", length = 20)
	public String getMccCategory() {
		return mccCategory;
	}
	public void setMccCategory(String mccCategory) {
		this.mccCategory = mccCategory;
	}
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "COMPUTE_MODE", columnDefinition = "VARCHAR(30)")
	public ComputeFeeMode getComputeFeeMode() {
		return computeFeeMode;
	}
	public void setComputeFeeMode(ComputeFeeMode computeFeeMode) {
		this.computeFeeMode = computeFeeMode;
	}
	
	@Column(name = "RATE", length = 128)
	public String getRate() {
		return rate;
	}
	public void setRate(String rate) {
		this.rate = rate;
	}
	
	@Column(name = "FIXED_FEE", columnDefinition = "double", length = 11, scale = 5)
	public Double getFixedFee() {
		return fixedFee;
	}
	public void setFixedFee(Double fixedFee) {
		this.fixedFee = fixedFee;
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
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "STATUS", columnDefinition = "VARCHAR(20)")
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	
}


