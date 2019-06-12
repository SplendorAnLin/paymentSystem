package com.yl.realAuth.model;

import java.util.Date;

import com.yl.realAuth.enums.AuthBusiType;
import com.yl.realAuth.enums.AuthConfigStatus;

/**
 * 实名认证开通
 * @author Shark
 * @since 2015年6月2日
 */
public class AuthConfigInfo extends AutoStringIDModel {

	private static final long serialVersionUID = 4244450011086700551L;
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
	/** 路由模板 */
	private String routingTemplateCode;
	/**  */
	private String name;
	
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

	public AuthBusiType getBusiType() {
		return busiType;
	}

	public void setBusiType(AuthBusiType busiType) {
		this.busiType = busiType;
	}

	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public String getIsActual() {
		return isActual;
	}

	public void setIsActual(String isActual) {
		this.isActual = isActual;
	}

	public String getRoutingTemplateCode() {
		return routingTemplateCode;
	}

	public void setRoutingTemplateCode(String routingTemplateCode) {
		this.routingTemplateCode = routingTemplateCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "AuthConfigInfo [customerNo=" + customerNo + ", customerName=" + customerName + ", busiType=" + busiType + ", status=" + status + ", remark="
				+ remark + ", lastUpdateTime=" + lastUpdateTime + ", isActual=" + isActual + ", routingTemplateCode=" + routingTemplateCode + "]";
	}
}
