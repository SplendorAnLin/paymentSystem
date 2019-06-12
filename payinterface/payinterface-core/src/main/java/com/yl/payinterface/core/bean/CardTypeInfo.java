package com.yl.payinterface.core.bean;

import java.io.Serializable;

import com.yl.payinterface.core.enums.CardType;

/**
 * 按卡类型区分的信息
 * 
 * @author 聚合支付有限公司
 * @since 2016年11月11日
 * @version V1.0.0
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
