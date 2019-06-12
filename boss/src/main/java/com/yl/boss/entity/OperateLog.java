package com.yl.boss.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.pay.common.util.StringUtil;
import com.yl.boss.enums.OperateType;

/**
 * 操作日志Bean
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月11日
 * @version V1.0.0
 */
@Entity
@Table(name = "OPERATE_LOG")
public class OperateLog extends AutoIDEntity{
	/**
	 * 操作员
	 */
	private String username;
	/**
	 * 操作时间
	 */
	private Date operateDate;
	/**
	 * 操作类型
	 */
	private OperateType operateType;
	/**
	 * 操作实体
	 */	
	private String entity;	
	/**
	 * 操作描述信息
	 */
	private String operateDesc;
	/**
	 * 操作描述附加信息
	 */
	private String operateDescAppend;
	
	public OperateLog(){		
	}
	
	public OperateLog(String username,OperateType operateType, Object entity, String operateDesc){
		this.username = username;
		this.operateType = operateType;
		this.entity = StringUtil.getObjectName(entity.getClass());
		this.operateDate = new Date();
		this.operateDesc = operateDesc;		
	}
	
	public OperateLog(String username,OperateType operateType, Object entity, String operateDesc, String operateDescAppend){
		this.username = username;
		this.operateType = operateType;
		this.entity = StringUtil.getObjectName(entity.getClass());
		this.operateDate = new Date();
		this.operateDesc = operateDesc;	
		this.operateDescAppend = operateDescAppend;
	}
		

	@Column(name = "USERNAME", columnDefinition = "VARCHAR(50)")
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(name = "OPERATE_DATE", columnDefinition = "DATE")
	public Date getOperateDate() {
		return operateDate;
	}

	public void setOperateDate(Date operateDate) {
		this.operateDate = operateDate;
	}

	@Enumerated(value = EnumType.STRING)
	@Column(name = "OPERATE_TYPE", columnDefinition = "VARCHAR(30)")
	public OperateType getOperateType() {
		return operateType;
	}

	public void setOperateType(OperateType operateType) {
		this.operateType = operateType;
	}

	@Column(name = "ENTITY", columnDefinition = "VARCHAR(50)")
	public String getEntity() {
		return entity;
	}

	public void setEntity(String entity) {
		this.entity = entity;
	}
	
	@Column(name = "OPERATE_DESC", columnDefinition = "VARCHAR(1000)")
		public String getOperateDesc() {
		return operateDesc;
	}

	public void setOperateDesc(String operateDesc) {
		this.operateDesc = operateDesc;
	}

	@Column(name = "OPERATE_DESC_APPEND", columnDefinition = "VARCHAR(1000)")
	public String getOperateDescAppend() {
		return operateDescAppend;
	}

	public void setOperateDescAppend(String operateDescAppend) {
		this.operateDescAppend = operateDescAppend;
	}	
}
