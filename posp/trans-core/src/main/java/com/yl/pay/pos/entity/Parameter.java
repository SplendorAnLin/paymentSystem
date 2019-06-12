package com.yl.pay.pos.entity;

import com.yl.pay.pos.enums.Status;

import javax.persistence.*;
import java.util.Date;

/**
 * Title:	POS参数信息表
 * Description:  
 * Copyright: Copyright (c)2010
 * Company: com.yl.pay
 * @author haitao.liu
 */
@Entity
@Table(name = "TMS_PARAMETER")
public class Parameter extends BaseEntity {

	private String posCATI;				//POS终端号
	private String posSn;				//序列号
	private String paramVersion;		//参数版本号
	private String paramContent; 		//参数列表
	private Date createTime;			//创建时间
	private Status status;				//状态
	private Date effTime; 				//生效时间
	private Date expTime;				//失效时间	
	private String createOperator;		//创建人
	
	@Column(name="POS_CATI", length = 20)
	public String getPosCATI() {
		return posCATI;
	}
	
	public void setPosCATI(String posCATI) {
		this.posCATI = posCATI;
	}
	
	@Column(name="POS_SN", length = 50)
	public String getPosSn() {
		return posSn;
	}
	
	public void setPosSn(String posSn) {
		this.posSn = posSn;
	}

	@Column(name="PARAM_VERSION", length = 20)
	public String getParamVersion() {
		return paramVersion;
	}

	public void setParamVersion(String paramVersion) {
		this.paramVersion = paramVersion;
	}

	@Column(name="PARAM_CONTENT", length = 2048)
	public String getParamContent() {
		return paramContent;
	}

	public void setParamContent(String paramContent) {
		this.paramContent = paramContent;
	}
	
	@Column(name = "CREATE_TIME", columnDefinition = "DATETIME")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Enumerated(value = EnumType.STRING)
	@Column(name = "STATUS", columnDefinition = "VARCHAR(30)")
	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	@Column(name = "EFF_TIME", columnDefinition = "DATETIME")
	public Date getEffTime() {
		return effTime;
	}

	public void setEffTime(Date effTime) {
		this.effTime = effTime;
	}

	@Column(name = "EXP_TIME", columnDefinition = "DATETIME")
	public Date getExpTime() {
		return expTime;
	}

	public void setExpTime(Date expTime) {
		this.expTime = expTime;
	}

	@Column(name = "CREATE_OPERATOR", length = 20)
	public String getCreateOperator() {
		return createOperator;
	}

	public void setCreateOperator(String createOperator) {
		this.createOperator = createOperator;
	}
}
