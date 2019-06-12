package com.yl.online.model.model;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.yl.online.model.enums.PartnerRole;
import com.yl.online.model.enums.PartnerRouterStatus;

/**
 * 合作方路由
 * 
 * @author 聚合支付有限公司
 * @since 2016年7月16日
 * @version V1.0.0
 */
public class PartnerRouter extends AutoStringIDModel {

	private static final long serialVersionUID = 8551381749689972633L;
	/** 合作方角色 */
	@NotNull
	private PartnerRole partnerRole;
	/** 合作方编号 */
	@NotBlank
	private String partnerCode;
	/** 合作方路由配置 */
	@NotNull
	private String profiles;
	/** 状态 */
	@NotNull
	private PartnerRouterStatus status;

	public PartnerRole getPartnerRole() {
		return partnerRole;
	}

	public void setPartnerRole(PartnerRole partnerRole) {
		this.partnerRole = partnerRole;
	}

	public String getPartnerCode() {
		return partnerCode;
	}

	public void setPartnerCode(String partnerCode) {
		this.partnerCode = partnerCode;
	}

	public String getProfiles() {
		return profiles;
	}

	public void setProfiles(String profiles) {
		this.profiles = profiles;
	}

	public PartnerRouterStatus getStatus() {
		return status;
	}

	public void setStatus(PartnerRouterStatus status) {
		this.status = status;
	}

}
