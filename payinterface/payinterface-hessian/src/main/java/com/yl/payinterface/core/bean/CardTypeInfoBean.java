package com.yl.payinterface.core.bean;

import java.io.Serializable;
import java.util.Arrays;

import com.yl.payinterface.core.enums.CardType;

/**
 * 按卡类型区分的信息Bean
 * 
 * @author 聚合支付有限公司
 * @since 2016年4月24日
 * @version V1.0.0
 */
public class CardTypeInfoBean implements Serializable {

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

	@Override
	public String toString() {
		return "CardTypeInfoBean [cardType=" + cardType + ", supportProviders=" + Arrays.toString(supportProviders) + "]";
	}
}
