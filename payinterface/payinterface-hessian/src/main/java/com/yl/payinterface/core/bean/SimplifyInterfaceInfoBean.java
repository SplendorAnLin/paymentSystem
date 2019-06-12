package com.yl.payinterface.core.bean;

import java.io.Serializable;
import java.util.List;

import com.yl.payinterface.core.enums.CardType;
import com.yl.payinterface.core.enums.InterfaceType;

/**
 * 接口信息Bean
 * 
 * @author 聚合支付有限公司
 * @since 2016年4月24日
 * @version V1.0.0
 */
public class SimplifyInterfaceInfoBean implements Serializable {

	private static final long serialVersionUID = -1646748116499825649L;

	/** 编号 */
	private String code;
	/** 接口类型 */
	private InterfaceType type;
	/** 卡类型 */
	private List<CardType> cardType;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public InterfaceType getType() {
		return type;
	}

	public void setType(InterfaceType type) {
		this.type = type;
	}

	public List<CardType> getCardType() {
		return cardType;
	}

	public void setCardType(List<CardType> cardType) {
		this.cardType = cardType;
	}

}
