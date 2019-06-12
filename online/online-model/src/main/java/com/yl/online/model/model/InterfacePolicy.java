package com.yl.online.model.model;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.yl.online.model.enums.InterfacePolicyStatus;
import com.yl.online.model.enums.InterfaceType;

/**
 * 接口策略
 * 
 * @author 聚合支付有限公司
 * @since 2016年7月16日
 * @version V1.0.0
 */
public class InterfacePolicy extends AutoStringIDModel {

	private static final long serialVersionUID = 6565473707017879003L;
	/** 策略名称 */
	@NotBlank
	private String name;
	/** 接口类型 */
	@NotNull
	private InterfaceType interfaceType;
	/** 接口策略配置 */
	@NotNull
	private String profiles;
	/** 接口策略状态 */
	@NotNull
	private InterfacePolicyStatus status;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public InterfaceType getInterfaceType() {
		return interfaceType;
	}

	public void setInterfaceType(InterfaceType interfaceType) {
		this.interfaceType = interfaceType;
	}

	public String getProfiles() {
		return profiles;
	}

	public void setProfiles(String profiles) {
		this.profiles = profiles;
	}

	public InterfacePolicyStatus getStatus() {
		return status;
	}

	public void setStatus(InterfacePolicyStatus status) {
		this.status = status;
	}

}
