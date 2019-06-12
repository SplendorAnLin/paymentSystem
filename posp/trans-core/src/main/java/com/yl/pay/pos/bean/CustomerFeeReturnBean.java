package com.yl.pay.pos.bean;


/**
 * Title: 商户手续费返回数据
 * Description: 
 * Copyright: Copyright (c)2011
 * Company: com.yl.pay
 * @author haitao.liu
 */

public class CustomerFeeReturnBean {
	
	private String customerFeeCode;		//费率规则编号
	private String rate;				//费率
	private double fee;					//手续费
	
	public CustomerFeeReturnBean(String customerFeeCode, String rate, double fee) {
		super();
		this.customerFeeCode = customerFeeCode;
		this.rate = rate;
		this.fee = fee;
	}

	public CustomerFeeReturnBean() {
		super();
	}

	public String getCustomerFeeCode() {
		return customerFeeCode;
	}

	public void setCustomerFeeCode(String customerFeeCode) {
		this.customerFeeCode = customerFeeCode;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	public double getFee() {
		return fee;
	}

	public void setFee(double fee) {
		this.fee = fee;
	}
	
	
	
}
