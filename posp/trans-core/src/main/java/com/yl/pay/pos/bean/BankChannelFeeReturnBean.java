package com.yl.pay.pos.bean;


/**
 * Title: 银行通道成本数据 
 * Description: 
 * Copyright: Copyright (c)2011
 * Company: com.yl.pay
 * @author jun
 */

public class BankChannelFeeReturnBean {
	private String bankChannelFeeCode;		//银行通道费率规则编号
	private String bankChannelRate;			//银行通道费率
	private double bankChannelCost;			//银行通道成本

	public BankChannelFeeReturnBean() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	public BankChannelFeeReturnBean(String bankChannelRate,
			double bankChannelCost) {
		super();
		this.bankChannelRate = bankChannelRate;
		this.bankChannelCost = bankChannelCost;
	}


	/**
	 * @param bankChannelFeeCode
	 * @param bankChannelRate
	 * @param bankChannelCost
	 */
	public BankChannelFeeReturnBean(String bankChannelFeeCode,
			String bankChannelRate, double bankChannelCost) {
		super();
		this.bankChannelFeeCode = bankChannelFeeCode;
		this.bankChannelRate = bankChannelRate;
		this.bankChannelCost = bankChannelCost;
	}


	public String getBankChannelFeeCode() {
		return bankChannelFeeCode;
	}

	public void setBankChannelFeeCode(String bankChannelFeeCode) {
		this.bankChannelFeeCode = bankChannelFeeCode;
	}

	public double getBankChannelCost() {
		return bankChannelCost;
	}

	public void setBankChannelCost(double bankChannelCost) {
		this.bankChannelCost = bankChannelCost;
	}

	public String getBankChannelRate() {
		return bankChannelRate;
	}

	public void setBankChannelRate(String bankChannelRate) {
		this.bankChannelRate = bankChannelRate;
	}

	
	
}

