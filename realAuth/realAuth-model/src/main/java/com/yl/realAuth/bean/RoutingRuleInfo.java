package com.yl.realAuth.bean;

import java.io.Serializable;

import com.yl.realAuth.enums.CardType;

/**
 * 路由规则表
 * @author Shark
 * @since 2015年12月15日
 */
public class RoutingRuleInfo implements Serializable {
	private static final long serialVersionUID = 4772576719650762575L;
	/** 主键 */
	private String code;
	/** 银行编码 */
	private String bankCode;
	/** 卡类型 */
	private CardType cardType;
	/** 优先级 */
	private Integer priority;
	/** 渠道code */
	private String channelCode;

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public CardType getCardType() {
		return cardType;
	}

	public void setCardType(CardType cardType) {
		this.cardType = cardType;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public String getChannelCode() {
		return channelCode;
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
