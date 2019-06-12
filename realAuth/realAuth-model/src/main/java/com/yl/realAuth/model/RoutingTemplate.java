package com.yl.realAuth.model;

import com.yl.realAuth.enums.AuthBusiType;
import com.yl.realAuth.enums.TemplateStatus;

/**
 * 路由模板表
 * @author Shark
 * @since 2015年12月15日
 */
public class RoutingTemplate extends AutoStringIDModel {
	private static final long serialVersionUID = -4958407707041718660L;
	/** 模板状态 */
	private TemplateStatus status;
	/** 业务类型 */
	private AuthBusiType businessType;
	/** 模板名称 */
	private String name;

	public TemplateStatus getStatus() {
		return status;
	}

	public void setStatus(TemplateStatus status) {
		this.status = status;
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

}
