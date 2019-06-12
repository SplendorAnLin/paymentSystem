package com.yl.realAuth.bean;

import java.io.Serializable;

import com.yl.realAuth.enums.CardType;

/**
 * 按卡类型区分的信息
 * @author boyang.sun
 * @since 2016年3月8日
 */
public class CardTypeInfo implements Serializable {

	private static final long serialVersionUID = -215604098028654109L;
	/** 卡类型 */
	private CardType cardType;
	/** 支持的接口提供方编号列表 */
	private String[] supportProviders;

	public CardType getCardType() {
		return cardType;
	}

	public void setCardType(CardType cardType) {
		this.cardType = cardType;
	}

	public String[] getSupportProviders() {
		return supportProviders;
	}

	public void setSupportProviders(String[] supportProviders) {
		this.supportProviders = supportProviders;
	}

}
