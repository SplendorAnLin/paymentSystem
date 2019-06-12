package com.yl.boss.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "REPORT_CUSTOMER")
public class ReportCustomer extends AutoIDEntity{
	private static final long serialVersionUID = 1L;

	private String customerNo;//商户编号
	private String interfaceNo;//通道ID
	private String appId;//APPID
	private String interfaceType;//通道类型
	private String industry;//行业编码
	private String attre;//保留字段
	private String remark;//备注
	private Date createTime;//创建时间
	private String status;//状态
	
	@Column(name="CUSTOMER_NO",length = 30)
	public String getCustomerNo() {
		return customerNo;
	}
	@Column(name="INTERFACE_NO",length = 30)
	public String getInterfaceNo() {
		return interfaceNo;
	}
	@Column(name="APP_ID",length = 50)
	public String getAppId() {
		return appId;
	}
	@Column(name="INTERFACE_TYPE",length = 20)
	public String getInterfaceType() {
		return interfaceType;
	}
	@Column(name="INDUSTRY",length = 20)
	public String getIndustry() {
		return industry;
	}
	@Column(name="ATTRE",length = 20)
	public String getAttre() {
		return attre;
	}
	@Column(name="REMARK",length = 20)
	public String getRemark() {
		return remark;
	}
	@Column(name = "CREATE_TIME", columnDefinition = "DATETIME")
	public Date getCreateTime() {
		return createTime;
	}
	@Column(name="STATUS",length = 20)
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}
	public void setInterfaceNo(String interfaceNo) {
		this.interfaceNo = interfaceNo;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public void setInterfaceType(String interfaceType) {
		this.interfaceType = interfaceType;
	}
	public void setIndustry(String industry) {
		this.industry = industry;
	}
	public void setAttre(String attre) {
		this.attre = attre;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
