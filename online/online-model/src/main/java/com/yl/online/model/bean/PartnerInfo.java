package com.yl.online.model.bean;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 合作方信息Bean
 * 
 * @author 聚合支付有限公司
 * @since 2016年7月13日
 * @version V1.0.0
 */
public class PartnerInfo {
	/** 角色 */
	private String role;
	/** 编号 */
	private String code;
	/** 简称 */
	private String shortName;
	/** 全称 */
	private String fullName;
	/** 状态 */
	private String status;
	/** 已开通服务编号 */
	private List<String> serviceNo;
	/** 密钥 */
	private Map<String, String> Keys;

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<String> getServiceNo() {
		return serviceNo;
	}

	public void setServiceNo(List<String> serviceNo) {
		this.serviceNo = serviceNo;
	}

	public Map<String, String> getKeys() {
		return Keys;
	}

	public void setKeys(Map<String, String> keys) {
		Keys = keys;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
