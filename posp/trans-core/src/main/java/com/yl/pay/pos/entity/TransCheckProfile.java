package com.yl.pay.pos.entity;

import com.yl.pay.pos.enums.Status;

import javax.persistence.*;
import java.util.Date;

/**
 * Title: 交易校验通用配置（黑、白名单）
 * Description: 
 * Copyright: Copyright (c)2014
 * Company: com.yl.pay
 * @author jun
 */

@Entity
@Table(name = "TRANS_CHECK_PROFILE")
public class TransCheckProfile extends BaseEntity{
	/**
	 * 业务类型   
	 * 消费撤销黑名单 PurchaseVoidBlacklist
	 */
	private String bizType;   
	/**
	 * 属性类型
	 * 商户 CUSTOMER
	 * 终端 POS
	 */
	private String profileType;
	/**
	 * 属性值
	 */
	private String profileValue;
	/**
	 * 状态
	 */
	private Status status;		
	/**
	 * 创建时间
	 */
	private Date createTime;
	
	@Column(name = "BIZ_TYPE", length = 50)
	public String getBizType() {
		return bizType;
	}
	public void setBizType(String bizType) {
		this.bizType = bizType;
	}
	
	@Column(name = "PROFILE_TYPE", length = 50)
	public String getProfileType() {
		return profileType;
	}
	public void setProfileType(String profileType) {
		this.profileType = profileType;
	}
	
	@Column(name = "PROFILE_VALUE", length = 50)
	public String getProfileValue() {
		return profileValue;
	}
	public void setProfileValue(String profileValue) {
		this.profileValue = profileValue;
	}
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "STATUS", columnDefinition = "VARCHAR(20)")
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	
	@Column(name = "CREATE_TIME", columnDefinition = "DATETIME")
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}		

}
