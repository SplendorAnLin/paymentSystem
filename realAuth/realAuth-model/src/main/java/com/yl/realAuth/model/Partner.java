package com.yl.realAuth.model;

import java.util.List;

import com.yl.realAuth.enums.AntiPhishingType;
import com.yl.realAuth.enums.PartnerRole;
import com.yl.realAuth.enums.PartnerStatus;

/**
 * 合作方
 * @author rui.wang
 * @since 2014年4月18日
 */
public class Partner extends AutoStringIDModel {

	private static final long serialVersionUID = 1L;
	/** 合作方角色 */
	
	private PartnerRole role;
	/** 名称 */
	
	private String name;
	/** 昵称 */
	
	private String nickname;
	/** 状态 */
	
	private PartnerStatus status = PartnerStatus.NORMAL;
	/** 接口类型 */
	
	private List<String> interfaceType;
	/** 行业类别*/
	private String industryCategory;
	/** 防钓鱼开关 */
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
	private String webAddress;
	/** ICP备案号 */
	private String icpNumber;
	/** ICP经营许可证号 */
	private String icpLicense;

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

}
