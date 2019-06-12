package com.yl.realAuth.hessian.bean;

import java.io.Serializable;

public class QueryAuthConfigBean implements Serializable {

	private static final long serialVersionUID = 4343699904634258407L;
	/** 商户编号 */
	private String customerNo;
	/** 商户名称 */
	private String customerName;
	/** 业务类型 */
	private String busiType;
	/** 业务状态 */
	private String status;
	/** 备用域 */
	private String remark;
	/** 创建起始时间 */
	private String createTimeStart;
	/** 创建终止时间 */
	private String createTimeEnd;
	/** */
	private String isActual;
	/** 路由模板编号 */
	private String routingTemplateCode;

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

	public String getBusiType() {
		return busiType;
	}

	public void setBusiType(String busiType) {
		this.busiType = busiType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCreateTimeStart() {
		return createTimeStart;
	}

	public void setCreateTimeStart(String createTimeStart) {
		this.createTimeStart = createTimeStart;
	}

	public String getCreateTimeEnd() {
		return createTimeEnd;
	}

	public void setCreateTimeEnd(String createTimeEnd) {
		this.createTimeEnd = createTimeEnd;
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

	@Override
	public String toString() {
		return "QueryAuthConfigBean [customerNo=" + customerNo + ", customerName=" + customerName + ", busiType=" + busiType + ", status=" + status + ", remark="
				+ remark + ", createTimeStart=" + createTimeStart + ", createTimeEnd=" + createTimeEnd + ", isActual=" + isActual + ",routingTemplateCode="
				+ routingTemplateCode + "]";
	}

}
