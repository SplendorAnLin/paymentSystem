package com.yl.pay.pos.bean;

import com.yl.pay.pos.enums.CardType;

/**
 * Title: 商户手续费计算参数
 * Description: 
 * Copyright: Copyright (c)2011
 * Company: com.yl.pay
 * @author haitao.liu
 */

public class CustomerFeeBean {
	
	private String customerNo;			//商户编号
	private String issuer;				//发卡行编号
	private CardType cardType;			//卡类型
	private double trxAmount;			//交易金额
	private String mcc;					//MCC
	
	/**
	 * @param customerNo
	 * @param issuer
	 * @param cardType
	 * @param trxAmount
	 * @param mcc
	 */
	public CustomerFeeBean(String customerNo, String issuer, CardType cardType,
			double trxAmount, String mcc) {
		super();
		this.customerNo = customerNo;
		this.issuer = issuer;
		this.cardType = cardType;
		this.trxAmount = trxAmount;
		this.mcc = mcc;
	}

	/**
	 * 
	 */
	public CustomerFeeBean() {
		super();
	}

	public String getCustomerNo() {
		return customerNo;
	}

	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}

	public String getIssuer() {
		return issuer;
	}

	public void setIssuer(String issuer) {
		this.issuer = issuer;
	}

	public CardType getCardType() {
		return cardType;
	}

	public void setCardType(CardType cardType) {
		this.cardType = cardType;
	}

	public double getTrxAmount() {
		return trxAmount;
	}

	public void setTrxAmount(double trxAmount) {
		this.trxAmount = trxAmount;
	}

	public String getMcc() {
		return mcc;
	}

	public void setMcc(String mcc) {
		this.mcc = mcc;
	}	
}

