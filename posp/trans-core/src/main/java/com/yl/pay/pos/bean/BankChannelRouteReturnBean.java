package com.yl.pay.pos.bean;

import com.yl.pay.pos.entity.BankChannel;
import com.yl.pay.pos.entity.BankCustomerConfig;


/**
 * Title: 银行路由返回参数
 * Description: 
 * Copyright: Copyright (c)2011
 * Company: com.yl.pay
 * @author haitao.liu
 */

public class BankChannelRouteReturnBean {
	
	private BankChannel bankChannel;	//银行通道
	private String bankChannelFeeCode;	//通道费率规则编号
	private String bankChannelRate;		//银行通道费率
	private double bankChannelCost;		//银行通道成本
	private String bankCustomerNo;		//银行商户号
	private BankCustomerConfig bankCustomerConfig;
	
	
	/**
	 * @param bankChannel
	 * @param bankChannelRate
	 * @param bankChannelCost
	 * @param bankCustomerNo
	 */
	public BankChannelRouteReturnBean(BankChannel bankChannel,
			String bankChannelRate, double bankChannelCost,
			String bankCustomerNo) {
		super();
		this.bankChannel = bankChannel;
		this.bankChannelRate = bankChannelRate;
		this.bankChannelCost = bankChannelCost;
		this.bankCustomerNo = bankCustomerNo;
	}
	/**
	 * 
	 */
	public BankChannelRouteReturnBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	public BankChannel getBankChannel() {
		return bankChannel;
	}
	public void setBankChannel(BankChannel bankChannel) {
		this.bankChannel = bankChannel;
	}
	public String getBankChannelRate() {
		return bankChannelRate;
	}
	public void setBankChannelRate(String bankChannelRate) {
		this.bankChannelRate = bankChannelRate;
	}
	public double getBankChannelCost() {
		return bankChannelCost;
	}
	public void setBankChannelCost(double bankChannelCost) {
		this.bankChannelCost = bankChannelCost;
	}
	public String getBankCustomerNo() {
		return bankCustomerNo;
	}
	public void setBankCustomerNo(String bankCustomerNo) {
		this.bankCustomerNo = bankCustomerNo;
	}
	public String getBankChannelFeeCode() {
		return bankChannelFeeCode;
	}
	public void setBankChannelFeeCode(String bankChannelFeeCode) {
		this.bankChannelFeeCode = bankChannelFeeCode;
	}
	public BankCustomerConfig getBankCustomerConfig() {
		return bankCustomerConfig;
	}
	public void setBankCustomerConfig(BankCustomerConfig bankCustomerConfig) {
		this.bankCustomerConfig = bankCustomerConfig;
	}
	
	
}

