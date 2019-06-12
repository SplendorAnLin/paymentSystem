package com.yl.boss.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.yl.boss.api.enums.ProcessStatus;

/**
 * 设备信息Bean
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月11日
 * @version V1.0.0
 */
@Entity
@Table(name = "DEVICE")
public class Device extends AutoIDEntity{
	private static final long serialVersionUID = -5591616154578277211L;
	
	private String customerPayNo;//收款码编号
	private String agentNo;//所属服务商
	private Date outWarehouseTime;//出库时间
	private String purchaseSerialNumber;//采购流水号
	private ProcessStatus status;//状态
	private String customerNo;//所属商户号
	private Date activateTime;//激活时间
	private Date createTime;//创建时间
	private Date updateTime;//更新时间
	private String batchNumber;//所属批次号
	@NotNull
	private Long deviceTypeId;//设备类型ID
	private String checkCode;//效验码
	private String qrcodeUrl;//二维码链接
	
	@Column(name="CUSTOMER_PAY_NO",length=30)
	public String getCustomerPayNo() {
		return customerPayNo;
	}
	public void setCustomerPayNo(String customerPayNo) {
		this.customerPayNo = customerPayNo;
	}
	@Column(name="AGENT_NO",length = 30)
	public String getAgentNo() {
		return agentNo;
	}
	public void setAgentNo(String agentNo) {
		this.agentNo = agentNo;
	}
	@Column(name = "OUT_WAREHOUSE_TIME", columnDefinition = "DATETIME")
	public Date getOutWarehouseTime() {
		return outWarehouseTime;
	}
	public void setOutWarehouseTime(Date outWarehouseTime) {
		this.outWarehouseTime = outWarehouseTime;
	}
	@Column(name = "PURCHASE_SERIAL_NUMBER", length = 35)
	public String getPurchaseSerialNumber() {
		return purchaseSerialNumber;
	}
	public void setPurchaseSerialNumber(String purchaseSerialNumber) {
		this.purchaseSerialNumber = purchaseSerialNumber;
	}
	@Enumerated(value = EnumType.STRING)
	@Column(name = "STATUS",length=30)
	public ProcessStatus getStatus() {
		return status;
	}
	public void setStatus(ProcessStatus status) {
		this.status = status;
	}
	@Column(name="CUSTOMER_NO",length = 30)
	public String getCustomerNo() {
		return customerNo;
	}
	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}
	@Column(name = "ACTIVATE_TIME", columnDefinition = "DATETIME")
	public Date getActivateTime() {
		return activateTime;
	}
	public void setActivateTime(Date activateTime) {
		this.activateTime = activateTime;
	}
	@Column(name = "CREATE_TIME", columnDefinition = "DATETIME")
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	@Column(name = "UPDATE_TIME", columnDefinition = "DATETIME")
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	@Column(name = "BATCH_NUMBER",length=30)
	public String getBatchNumber() {
		return batchNumber;
	}
	public void setBatchNumber(String batchNumber) {
		this.batchNumber = batchNumber;
	}
	@Column(name = "CHECK_CODE",length=10)
	public String getCheckCode() {
		return checkCode;
	}
	public void setCheckCode(String checkCode) {
		this.checkCode = checkCode;
	}
	@Column(name = "QRCODE_URL",length=300)
	public String getQrcodeUrl() {
		return qrcodeUrl;
	}
	public void setQrcodeUrl(String qrcodeUrl) {
		this.qrcodeUrl = qrcodeUrl;
	}
	@Column(name = "DEVICE_TYPE_ID")
	public Long getDeviceTypeId() {
		return deviceTypeId;
	}
	public void setDeviceTypeId(Long deviceTypeId) {
		this.deviceTypeId = deviceTypeId;
	}
}
