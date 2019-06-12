package com.yl.online.gateway.bean;

import java.io.Serializable;

/**
 * 卡识别返回信息
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月4日
 * @version V1.0.0
 */
public class CardInfo implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -731619926394962228L;
	/** 开户行名称 */
	private String agencyName;
	/** 提供方编码 */
	private String providerCode;

	public String getAgencyName() {
		return agencyName;
	}

	public void setAgencyName(String agencyName) {
		this.agencyName = agencyName;
	}

	public String getProviderCode() {
		return providerCode;
	}

	public void setProviderCode(String providerCode) {
		this.providerCode = providerCode;
	}

}
