package com.yl.boss.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.yl.boss.enums.Status;
import com.yl.boss.enums.SyncResult;
import com.yl.boss.enums.SyncSys;
import com.yl.boss.enums.SyncType;

@Entity
@Table(name = "SYNC_INFO")
public class SyncInfo extends AutoIDEntity{
	private static final long serialVersionUID = 1L;
	
	private SyncType syncType;//同步信息的类型
	private String info;//实体信息
	private Date createTime;//创建时间
	private Date updateTime;//更新时间
	private Status isSync;//同步状态
	private Status isCreate;//创建还是新增
	private SyncResult result;//同步结果
	private Integer syncCount;//同步次数
	private SyncSys syncSys;//同步到xx系统
	private String msg;
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "SYNC_TYPE", columnDefinition = "VARCHAR(30)")
	public SyncType getSyncType() {
		return syncType;
	}
	@Column(name = "INFO", length = 350)
	public String getInfo() {
		return info;
	}
	@Column(name = "CREATE_TIME", columnDefinition = "DATETIME")
	public Date getCreateTime() {
		return createTime;
	}
	@Column(name = "UPDATE_TIME", columnDefinition = "DATETIME")
	public Date getUpdateTime() {
		return updateTime;
	}
	@Enumerated(value = EnumType.STRING)
	@Column(name = "IS_SYNC", columnDefinition = "VARCHAR(30)")
	public Status getIsSync() {
		return isSync;
	}
	@Enumerated(value = EnumType.STRING)
	@Column(name = "IS_CREATE", columnDefinition = "VARCHAR(30)")
	public Status getIsCreate() {
		return isCreate;
	}
	@Enumerated(value = EnumType.STRING)
	@Column(name = "RESULT", columnDefinition = "VARCHAR(30)")
	public SyncResult getResult() {
		return result;
	}
	@Column(name = "SYNC_COUNT")
	public Integer getSyncCount() {
		return syncCount;
	}
	@Enumerated(value = EnumType.STRING)
	@Column(name = "SYNC_SYS", columnDefinition = "VARCHAR(30)")
	public SyncSys getSyncSys() {
		return syncSys;
	}
	@Column(name = "MSG",length = 255)
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public void setSyncSys(SyncSys syncSys) {
		this.syncSys = syncSys;
	}
	public void setSyncCount(Integer syncCount) {
		this.syncCount = syncCount;
	}
	public void setSyncType(SyncType syncType) {
		this.syncType = syncType;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public void setIsSync(Status isSync) {
		this.isSync = isSync;
	}
	public void setIsCreate(Status isCreate) {
		this.isCreate = isCreate;
	}
	public void setResult(SyncResult result) {
		this.result = result;
	}
}
