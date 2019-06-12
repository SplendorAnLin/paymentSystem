package com.yl.online.model.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.validator.constraints.NotBlank;

import com.yl.online.model.enums.CardType;
import com.yl.online.model.enums.InterfacePolicyType;

/**
 * 接口策略配置Bean
 * 
 * @author 聚合支付有限公司
 * @since 2016年7月13日
 * @version V1.0.0
 */
public class InterfacePolicyProfile implements Serializable {
	private static final long serialVersionUID = -4664858248278068869L;
	/** 卡类型 */
	@NotNull
	private CardType cardType;
	/** 接口提供方编号 */
	@NotBlank
	private String interfaceProviderCode;
	/** 接口策略类型 */
	@NotNull
	private InterfacePolicyType policyType;
	/** 接口编号 */
	@NotNull
	@Valid
	private List<InterfaceInfoForRouter> interfaceInfos;
	/** 生效时间 */
	@NotNull
	private Date effectTime;
	/** 失效时间 */
	@NotNull
	private Date expireTime;

	public CardType getCardType() {
		return cardType;
	}

	public void setCardType(CardType cardType) {
		this.cardType = cardType;
	}

	public InterfacePolicyType getPolicyType() {
		return policyType;
	}

	public void setPolicyType(InterfacePolicyType policyType) {
		this.policyType = policyType;
	}

	public String getInterfaceProviderCode() {
		return interfaceProviderCode;
	}

	public void setInterfaceProviderCode(String interfaceProviderCode) {
		this.interfaceProviderCode = interfaceProviderCode;
	}

	public List<InterfaceInfoForRouter> getInterfaceInfos() {
		return interfaceInfos;
	}

	public void setInterfaceInfos(List<InterfaceInfoForRouter> interfaceInfos) {
		this.interfaceInfos = interfaceInfos;
	}

	public Date getEffectTime() {
		return effectTime;
	}

	public void setEffectTime(Date effectTime) {
		this.effectTime = effectTime;
	}

	public Date getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(Date expireTime) {
		this.expireTime = expireTime;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
