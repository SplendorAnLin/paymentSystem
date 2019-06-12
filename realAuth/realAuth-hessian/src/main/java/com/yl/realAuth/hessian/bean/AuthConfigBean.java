package com.yl.realAuth.hessian.bean;

import java.io.Serializable;
import java.util.Date;

import com.yl.realAuth.hessian.enums.AuthBusiType;
import com.yl.realAuth.hessian.enums.AuthConfigStatus;

/**
 * 实名认证开通配置实体
 * @author Shark
 * @since 2015年6月3日
 */
public class AuthConfigBean implements Serializable {

	private static final long serialVersionUID = 3647805582264223174L;
	/** 主键 */
	private String id;
	/** 编号 */
	private String code;
	/** 版本号 */
	private Long version;
	/** 创建时间 */
	private Date createTime;
	/** 所有者编号 */
	private String customerNo;
	/** 所有者名称 */
	private String customerName;
	/** 实名认证业务类型 */
	private AuthBusiType busiType;
	/** 状态 */
	private AuthConfigStatus status;
	/** 备注 */
	private String remark;
	/** 修改时间 */
	private Date lastUpdateTime;
	/** 是否实时 */
	private String isActual;
	/** 实名认证路由模板 */
	private String routingTemplateCode;
	
	private String name;

	@Override
	public String toString() {
		return "AuthConfigBean [id=" + id + ", code=" + code + ", version=" + version + ", createTime=" + createTime + ", customerNo=" + customerNo
				+ ", customerName=" + customerName + ", busiType=" + busiType + ", status=" + status + ", remark=" + remark + ", lastUpdateTime=" + lastUpdateTime
				+ ", isActual=" + isActual + ",routingTemplateCode=" + routingTemplateCode + "]";
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public AuthBusiType getBusiType() {
		return busiType;
	}

	public void setBusiType(AuthBusiType busiType) {
		this.busiType = busiType;
	}

	public AuthConfigStatus getStatus() {
		return status;
	}

	public void setStatus(AuthConfigStatus status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public String getCustomerNo() {
		return customerNo;
	}

	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getIsActual() {
		return isActual;
	}

	public void setIsActual(String isActual) {
		this.isActual = isActual;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRoutingTemplateCode() {
		return routingTemplateCode;
	}

	public void setRoutingTemplateCode(String routingTemplateCode) {
		this.routingTemplateCode = routingTemplateCode;
	}

}
