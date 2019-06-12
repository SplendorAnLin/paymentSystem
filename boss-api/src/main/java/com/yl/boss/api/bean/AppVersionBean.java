package com.yl.boss.api.bean;

import java.util.Date;

import com.yl.boss.api.enums.Status;


public class AppVersionBean extends BaseBean{
	private static final long serialVersionUID = -2768037611875401910L;
	
	private String agentNo;//代理商编号
	private String shortApp;//app简称
	private String version;//oem版本号
	private String url;//下载地址
	private String enforce;//强制更新TRUE为开启
	private String status;//状态
	private String appInfo;//app信息
	private String type;//app类型
	private String remark;//备注
	private Date createTime;
	private String appAuditStatus;//是否审核到苹果商城

	public String getAppAuditStatus() {
		return appAuditStatus;
	}

	public void setAppAuditStatus(String appAuditStatus) {
		this.appAuditStatus = appAuditStatus;
	}

	public Date getCreateTime() {
		return createTime;
	}
	public String getAgentNo() {
		return agentNo;
	}
	public String getShortApp() {
		return shortApp;
	}
	public String getVersion() {
		return version;
	}
	public String getAppInfo() {
		return appInfo;
	}
	public String getType() {
		return type;
	}
	public String getRemark() {
		return remark;
	}
	public String getUrl() {
		return url;
	}
	public String getEnforce() {
		return enforce;
	}
	public String getStatus() {
		return status;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public void setAgentNo(String agentNo) {
		this.agentNo = agentNo;
	}
	public void setShortApp(String shortApp) {
		this.shortApp = shortApp;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public void setAppInfo(String appInfo) {
		this.appInfo = appInfo;
	}
	public void setType(String type) {
		this.type = type;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public void setEnforce(String enforce) {
		this.enforce = enforce;
	}
	
}
