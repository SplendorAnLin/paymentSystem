package com.yl.cachecenter.model;

import java.io.Serializable;

/**
 * 发行者识别号码
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月14日
 * @version V1.0.0
 */
public class IIN implements Serializable {

	private static final long serialVersionUID = 6625398415141272566L;
	/** 卡识别号 */
	private String code;
	/** 卡识别长度 */
	private int length;
	/** 卡号长度 */
	private int panLength;
	/** 接口提供方编号 */
	private String providerCode;
	/** 卡种 */
	private String cardType;
	/** 卡名称 */
	private String cardName;
	/** 机构代码 */
	private String agencyCode;
	/** 机构名称 */
	private String agencyName;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public int getPanLength() {
		return panLength;
	}

	public void setPanLength(int panLength) {
		this.panLength = panLength;
	}

	public String getProviderCode() {
		return providerCode;
	}

	public void setProviderCode(String providerCode) {
		this.providerCode = providerCode;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getCardName() {
		return cardName;
	}

	public void setCardName(String cardName) {
		this.cardName = cardName;
	}

	public String getAgencyCode() {
		return agencyCode;
	}

	public void setAgencyCode(String agencyCode) {
		this.agencyCode = agencyCode;
	}

	public String getAgencyName() {
		return agencyName;
	}

	public void setAgencyName(String agencyName) {
		this.agencyName = agencyName;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("IIN [code=");
		builder.append(code);
		builder.append(", length=");
		builder.append(length);
		builder.append(", panLength=");
		builder.append(panLength);
		builder.append(", providerCode=");
		builder.append(providerCode);
		builder.append(", cardType=");
		builder.append(cardType);
		builder.append(", cardName=");
		builder.append(cardName);
		builder.append(", agencyCode=");
		builder.append(agencyCode);
		builder.append(", agencyName=");
		builder.append(agencyName);
		builder.append("]");
		return builder.toString();
	}

}
