package com.yl.online.trade.hessian.bean;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import com.lefu.hessian.bean.JsonBean;

/**
 * 合作方路由Bean
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月11日
 * @version V1.0.0
 */
public class PartnerRouterBean implements JsonBean {

	private static final long serialVersionUID = -2960689001034204208L;
	
	/** 编号 */
	private String code;
	
	/** 创建时间 */
	private Date createTime;
	
	/** 合作方角色 */
	@NotNull
	private String partnerRole;
	
	/** 合作方编号 */
	@NotBlank
	private String partnerCode;
	
	/** 合作方路由配置 */
	@NotEmpty
	@Valid
	private List<PartnerRouterProfileBean> profiles;
	
	/** 状态 */
	@NotBlank 
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

	public String getPartnerRole() {
		return partnerRole;
	}

	public void setPartnerRole(String partnerRole) {
		this.partnerRole = partnerRole;
	}

	public String getPartnerCode() {
		return partnerCode;
	}

	public void setPartnerCode(String partnerCode) {
		this.partnerCode = partnerCode;
	}

	public List<PartnerRouterProfileBean> getProfiles() {
		return profiles;
	}

	public void setProfiles(List<PartnerRouterProfileBean> profiles) {
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
		builder.append("PartnerRouterBean [code=");
		builder.append(code);
		builder.append(", createTime=");
		builder.append(createTime);
		builder.append(", partnerRole=");
		builder.append(partnerRole);
		builder.append(", partnerCode=");
		builder.append(partnerCode);
		builder.append(", profiles=");
		builder.append(profiles);
		builder.append(", status=");
		builder.append(status);
		builder.append("]");
		return builder.toString();
	}

	
}
