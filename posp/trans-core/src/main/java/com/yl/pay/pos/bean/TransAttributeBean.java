package com.yl.pay.pos.bean;


/**
 * Title: 交易路由相关交易属性
 * Description: 
 * Copyright: Copyright (c)2012
 * Company: com.yl.pay
 * @author jun
 */
public class TransAttributeBean {
	
	private String issuer;			//发卡行:A,B或!A,B
	private String cardType;		//卡种:A,B
	private String cardStyle;		//卡的样式、品牌:A或!A
	private String amountScope;		//金额范围:A~B,C~D
	private String dateScope;		//日期范围:A~B,C~D 当月几号至几号 
	private String timeScope;		//时间范围:A~B,C~D 当天几点至几点
	
	public String getIssuer() {
		return issuer;
	}

	public void setIssuer(String issuer) {
		if (issuer != null) {
			issuer = issuer.trim();
		}
		this.issuer = issuer;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		if (cardType != null) {
			cardType = cardType.trim();
		}
		this.cardType = cardType;
	}

	public String getCardStyle() {
		return cardStyle;
	}

	public void setCardStyle(String cardStyle) {
		if (cardStyle != null) {
			cardStyle = cardStyle.trim();
		}
		this.cardStyle = cardStyle;
	}

	public String getAmountScope() {
		return amountScope;
	}

	public void setAmountScope(String amountScope) {
		if (amountScope != null) {
			amountScope = amountScope.trim();
		}
		this.amountScope = amountScope;
	}

	public String getTimeScope() {
		return timeScope;
	}

	public void setTimeScope(String timeScope) {
		if (timeScope != null) {
			timeScope = timeScope.trim();
		}
		this.timeScope = timeScope;
	}

	public String getDateScope() {
		return dateScope;
	}

	public void setDateScope(String dateScope) {
		if (dateScope != null) {
			dateScope = dateScope.trim();
		}
		this.dateScope = dateScope;
	}
	
}

