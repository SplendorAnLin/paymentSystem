package com.yl.online.trade.hessian.bean;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.lefu.hessian.bean.JsonBean;

/**
 * 接口策略Bean
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月11日
 * @version V1.0.0
 */
public class InterfacePolicyBean implements JsonBean {

	private static final long serialVersionUID = 3707518029988402427L;

	/** 编号 */
	private String code;

	/** 创建时间 */
	private Date createTime;

	/** 策略名称 */
	@NotBlank
	private String name;

	/** 接口类型 */
	@NotNull
	private String interfaceType;

	/** 接口策略配置 */
	@NotNull
	@Valid
	private List<InterfacePolicyProfileBean> profiles;

	/** 接口策略状态 */
	@NotNull
	private String status;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getInterfaceType() {
		return interfaceType;
	}

	public void setInterfaceType(String interfaceType) {
		this.interfaceType = interfaceType;
	}

	public List<InterfacePolicyProfileBean> getProfiles() {
		return profiles;
	}

	public void setProfiles(List<InterfacePolicyProfileBean> profiles) {
		this.profiles = profiles;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("InterfacePolicyBean [code=");
		builder.append(code);
		builder.append(", createTime=");
		builder.append(createTime);
		builder.append(", name=");
		builder.append(name);
		builder.append(", interfaceType=");
		builder.append(interfaceType);
		builder.append(", profiles=");
		builder.append(profiles);
		builder.append(", status=");
		builder.append(status);
		builder.append("]");
		return builder.toString();
	}

}
