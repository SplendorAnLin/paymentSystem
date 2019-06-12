package com.yl.pay.pos.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * Title: 支付费用信息
 * Description: 
 * Copyright: Copyright (c)2011
 * Company: com.yl.pay
 * @author jun
 */

@Entity
@Table(name = "PAYMENT_FEE")
public class PaymentFee extends BaseEntity{

	private String bankChannelFeeCode;	//通道费率规则编号
	private String bankChannelRate;		//银行通道费率
	private String customerFeeCode;		//商户费率规则编号
	private String customerRate;		//商户费率
	
	/**
	 * @param bankChannelFeeCode
	 * @param bankChannelRate
	 * @param customerFeeCode
	 * @param customerRate
	 */
	public PaymentFee(String bankChannelFeeCode, String bankChannelRate,
			String customerFeeCode, String customerRate) {
		super();
		this.bankChannelFeeCode = bankChannelFeeCode;
		this.bankChannelRate = bankChannelRate;
		this.customerFeeCode = customerFeeCode;
		this.customerRate = customerRate;
	}
	/**
	 * 
	 */
	public PaymentFee() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	@Column(name = "BANK_CHANNEL_FEE_CODE", length = 25)
	public String getBankChannelFeeCode() {
		return bankChannelFeeCode;
	}
	public void setBankChannelFeeCode(String bankChannelFeeCode) {
		this.bankChannelFeeCode = bankChannelFeeCode;
	}
	
	@Column(name = "BANK_CHANNEL_RATE", length = 128)
	public String getBankChannelRate() {
		return bankChannelRate;
	}
	public void setBankChannelRate(String bankChannelRate) {
		this.bankChannelRate = bankChannelRate;
	}
	
	@Column(name = "CUSTOMER_FEE_CODE", length = 25)
	public String getCustomerFeeCode() {
		return customerFeeCode;
	}
	public void setCustomerFeeCode(String customerFeeCode) {
		this.customerFeeCode = customerFeeCode;
	}
	
	@Column(name = "CUSTOMER_RATE", length = 25)
	public String getCustomerRate() {
		return customerRate;
	}
	public void setCustomerRate(String customerRate) {
		this.customerRate = customerRate;
	}

	
	
}
