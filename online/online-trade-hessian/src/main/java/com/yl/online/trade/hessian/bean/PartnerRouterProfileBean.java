package com.yl.online.trade.hessian.bean;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.lefu.hessian.bean.JsonBean;

/**
 * 合作方路由配置Bean
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月11日
 * @version V1.0.0
 */
public class PartnerRouterProfileBean implements JsonBean {
	
	private static final long serialVersionUID = 5812636859592708509L;
	
	/** 接口类型 */
	@NotNull
	private String interfaceType;
	
	/** 策略选择方式 */
	@NotNull
	private String policySelectType;
	
	/** 指定策略 */
	private List<InterfacePolicyProfileBean> specifiedPolicyProfiles;
	
	/** 模板策略 */
	private String templateInterfacePolicy;

	public String getInterfaceType() {
		return interfaceType;
	}

	public void setInterfaceType(String interfaceType) {
		this.interfaceType = interfaceType;
	}

	public String getPolicySelectType() {
		return policySelectType;
	}

	public void setPolicySelectType(String policySelectType) {
		this.policySelectType = policySelectType;
	}

	public List<InterfacePolicyProfileBean> getSpecifiedPolicyProfiles() {
		return specifiedPolicyProfiles;
	}

	public void setSpecifiedPolicyProfiles(List<InterfacePolicyProfileBean> specifiedPolicyProfiles) {
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