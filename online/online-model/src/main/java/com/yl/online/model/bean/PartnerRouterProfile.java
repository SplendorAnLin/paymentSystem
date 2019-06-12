package com.yl.online.model.bean;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.yl.online.model.enums.InterfaceType;
import com.yl.online.model.enums.PartnerRouterPolicySelectType;

/**
 * 合作方路由配置Bean
 * 
 * @author 聚合支付有限公司
 * @since 2016年7月13日
 * @version V1.0.0
 */
public class PartnerRouterProfile implements Serializable {
	private static final long serialVersionUID = 7495116008611312343L;
	/** 接口类型 */
	@NotNull
	private InterfaceType interfaceType;
	/** 策略选择方式 */
	@NotNull
	private PartnerRouterPolicySelectType policySelectType;
	/** 指定策略 */
	private List<InterfacePolicyProfile> specifiedPolicyProfiles;
	/** 模板策略 */
	private String templateInterfacePolicy;

	public InterfaceType getInterfaceType() {
		return interfaceType;
	}

	public void setInterfaceType(InterfaceType interfaceType) {
		this.interfaceType = interfaceType;
	}

	public PartnerRouterPolicySelectType getPolicySelectType() {
		return policySelectType;
	}

	public void setPolicySelectType(PartnerRouterPolicySelectType policySelectType) {
		this.policySelectType = policySelectType;
	}

	public List<InterfacePolicyProfile> getSpecifiedPolicyProfiles() {
		return specifiedPolicyProfiles;
	}

	public void setSpecifiedPolicyProfiles(List<InterfacePolicyProfile> specifiedPolicyProfiles) {
		this.specifiedPolicyProfiles = specifiedPolicyProfiles;
	}

	public String getTemplateInterfacePolicy() {
		return templateInterfacePolicy;
	}

	public void setTemplateInterfacePolicy(String templateInterfacePolicy) {
		this.templateInterfacePolicy = templateInterfacePolicy;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}