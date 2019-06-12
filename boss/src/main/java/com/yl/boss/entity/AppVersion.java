package com.yl.boss.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.yl.boss.enums.Status;

@Entity
@Table(name = "APP_VERSION")
public class AppVersion extends AutoIDEntity{
	private static final long serialVersionUID = -68264355394345090L;

	private String agentNo;//代理商编号
	private String shortApp;//app简称
	private String version;//oem版本号
	private String url;//下载地址
	private Status enforce;//强制更新TRUE为开启
	private Status status;//状态
	private String appInfo;//app信息
	private String type;//app类型
	private String remark;//备注
	private Date createTime;
	private Status appAuditStatus;
	
	@Column(name = "CREATE_TIME", columnDefinition = "DATE")
	public Date getCreateTime() {
		return createTime;
	}
	@Column(name = "AGENT_NO", length = 30)
	public String getAgentNo() {
		return agentNo;
	}
	@Column(name = "SHORT_APP", length = 30)
	public String getShortApp() {
		return shortApp;
	}
	@Column(name = "VERSION", length = 20)
	public String getVersion() {
		return version;
	}
	@Column(name = "APP_INFO", length = 3000)
	public String getAppInfo() {
		return appInfo;
	}
	@Column(name = "TYPE", length = 10)
	public String getType() {
		return type;
	}
	@Column(name = "REMARK", length = 3000)
	public String getRemark() {
		return remark;
	}
	@Column(name = "URL", length = 50)
	public String getUrl() {
		return url;
	}
	@Enumerated(value = EnumType.STRING)
	@Column(name = "ENFORCE", length = 10)
	public Status getEnforce() {
		return enforce;
	}
	@Enumerated(value = EnumType.STRING)
	@Column(name = "STATUS", length = 10)
	public Status getStatus() {
		return status;
	}
	@Enumerated(value = EnumType.STRING)
	@Column(name = "APP_AUDIT_STATUS", length = 10)
	public Status getAppAuditStatus() {
		return appAuditStatus;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public void setStatus(Status status) {
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
	public void setEnforce(Status enforce) {
		this.enforce = enforce;
	}
	public void setAppAuditStatus(Status appAuditStatus) {
		this.appAuditStatus = appAuditStatus;
	}
	
	
}
