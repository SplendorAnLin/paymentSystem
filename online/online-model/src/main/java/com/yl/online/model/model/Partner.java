package com.yl.online.model.model;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.yl.online.model.enums.AntiPhishingType;
import com.yl.online.model.enums.PartnerRole;
import com.yl.online.model.enums.PartnerStatus;

/**
 * 合作方
 * 
 * @author 聚合支付有限公司
 * @since 2016年7月18日
 * @version V1.0.0
 */
public class Partner extends AutoStringIDModel {

	private static final long serialVersionUID = 1L;
	/** 合作方角色 */
	@NotNull
	private PartnerRole role;
	/** 名称 */
	@NotBlank
	private String name;
	/** 昵称 */
	@NotBlank
	private String nickname;
	/** 状态 */
	@NotNull
	private PartnerStatus status = PartnerStatus.NORMAL;
	/** 接口类型 */
	@NotNull
	private List<String> interfaceType;
	/** 行业类别*/
	@NotBlank
	private String industryCategory;
	/** 防钓鱼开关 */
	@NotNull
	private Boolean antiPhishingFlag = false;
	/** 防钓鱼方式 */
	private AntiPhishingType antiPhishingType;
	/** 来源地址 */
	private String referer;
	/** 来源IP */
	private List<String> ip;
	/** 所属地区 */
	private String belongArea;
	/** 后台通知URL */
	private String notifyURL;
	/** 页面重定向URL */
	private String redirectURL;
	/** 网址 */
	@NotBlank
	private String webAddress;
	/** ICP备案号 */
	@NotBlank
	private String icpNumber;
	/** ICP经营许可证号 */
	@NotBlank
	private String icpLicense;
	/** 密钥 */
	private String key;

	public PartnerRole getRole() {
		return role;
	}

	public void setRole(PartnerRole role) {
		this.role = role;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public PartnerStatus getStatus() {
		return status;
	}

	public void setStatus(PartnerStatus status) {
		this.status = status;
	}

	public List<String> getInterfaceType() {
		return interfaceType;
	}

	public void setInterfaceType(List<String> interfaceType) {
		this.interfaceType = interfaceType;
	}

	public String getIndustryCategory() {
		return industryCategory;
	}

	public void setIndustryCategory(String industryCategory) {
		this.industryCategory = industryCategory;
	}

	public Boolean getAntiPhishingFlag() {
		return antiPhishingFlag;
	}

	public void setAntiPhishingFlag(Boolean antiPhishingFlag) {
		this.antiPhishingFlag = antiPhishingFlag;
	}

	public AntiPhishingType getAntiPhishingType() {
		return antiPhishingType;
	}

	public void setAntiPhishingType(AntiPhishingType antiPhishingType) {
		this.antiPhishingType = antiPhishingType;
	}

	public String getReferer() {
		return referer;
	}

	public void setReferer(String referer) {
		this.referer = referer;
	}

	public List<String> getIp() {
		return ip;
	}

	public void setIp(List<String> ip) {
		this.ip = ip;
	}

	public String getBelongArea() {
		return belongArea;
	}

	public void setBelongArea(String belongArea) {
		this.belongArea = belongArea;
	}

	public String getNotifyURL() {
		return notifyURL;
	}

	public void setNotifyURL(String notifyURL) {
		this.notifyURL = notifyURL;
	}

	public String getRedirectURL() {
		return redirectURL;
	}

	public void setRedirectURL(String redirectURL) {
		this.redirectURL = redirectURL;
	}

	public String getWebAddress() {
		return webAddress;
	}

	public void setWebAddress(String webAddress) {
		this.webAddress = webAddress;
	}

	public String getIcpNumber() {
		return icpNumber;
	}

	public void setIcpNumber(String icpNumber) {
		this.icpNumber = icpNumber;
	}

	public String getIcpLicense() {
		return icpLicense;
	}

	public void setIcpLicense(String icpLicense) {
		this.icpLicense = icpLicense;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

}
