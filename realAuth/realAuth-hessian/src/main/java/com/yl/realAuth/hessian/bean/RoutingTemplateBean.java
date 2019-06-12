package com.yl.realAuth.hessian.bean;

import java.io.Serializable;
import java.util.List;

import com.yl.realAuth.bean.RoutingRuleInfo;
import com.yl.realAuth.enums.TemplateStatus;
import com.yl.realAuth.hessian.enums.AuthBusiType;

/**
 * 路由模板表
 * @author Shark
 * @since 2015年12月15日
 */
public class RoutingTemplateBean implements Serializable {
	private static final long serialVersionUID = 6375517015634807324L;
	/** 模板状态 */
	private TemplateStatus status;
	/** 路由规则 */
	private List<RoutingRuleInfo> ruleInfo;
	/** 业务类型 */
	private AuthBusiType businessType;
	/** 模板名称 */
	private String name;

	private String code;

	public TemplateStatus getStatus() {
		return status;
	}

	public void setStatus(TemplateStatus status) {
		this.status = status;
	}

	public List<RoutingRuleInfo> getRuleInfo() {
		return ruleInfo;
	}

	public void setRuleInfo(List<RoutingRuleInfo> ruleInfo) {
		this.ruleInfo = ruleInfo;
	}

	public AuthBusiType getBusinessType() {
		return businessType;
	}

	public void setBusinessType(AuthBusiType businessType) {
		this.businessType = businessType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
