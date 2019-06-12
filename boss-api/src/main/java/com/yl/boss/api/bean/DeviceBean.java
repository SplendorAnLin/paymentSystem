package com.yl.boss.api.bean;

import java.util.Date;

import com.yl.boss.api.enums.ProcessStatus;

/**
 * 设备信息Bean
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月16日
 * @version V1.0.0
 */
public class DeviceBean extends BaseBean{
	private static final long serialVersionUID = -5591616154578277211L;
	
	String customerPayNo;//收款码编号
	String agentNo;//所属服务商
	Date outWarehouseTime;//出库时间
	String purchaseSerialNumber;//采购流水号
	ProcessStatus status;//状态
	String CustomerNo;//所属商户号
	Date activateTime;//激活时间
	Date createTime;//创建时间
	Date updateTime;//更新时间
	String batchNumber;//所属批次号
	private Long deviceTypeId;//设备类型ID
	String checkCode;//效验码
	String qrcodeUrl;//二维码链接
	public String getCustomerPayNo() {
		return customerPayNo;
	}
	public void setCustomerPayNo(String customerPayNo) {
		this.customerPayNo = customerPayNo;
	}
	public String getAgentNo() {
		return agentNo;
	}
	public void setAgentNo(String agentNo) {
		this.agentNo = agentNo;
	}
	public Date getOutWarehouseTime() {
		return outWarehouseTime;
	}
	public void setOutWarehouseTime(Date outWarehouseTime) {
		this.outWarehouseTime = outWarehouseTime;
	}
	public String getPurchaseSerialNumber() {
		return purchaseSerialNumber;
	}
	public void setPurchaseSerialNumber(String purchaseSerialNumber) {
		this.purchaseSerialNumber = purchaseSerialNumber;
	}
	public ProcessStatus getStatus() {
		return status;
	}
	public void setStatus(ProcessStatus status) {
		this.status = status;
	}
	public String getCustomerNo() {
		return CustomerNo;
	}
	public void setCustomerNo(String customerNo) {
		CustomerNo = customerNo;
	}
	public Date getActivateTime() {
		return activateTime;
	}
	public void setActivateTime(Date activateTime) {
		this.activateTime = activateTime;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getBatchNumber() {
		return batchNumber;
	}
	public void setBatchNumber(String batchNumber) {
		this.batchNumber = batchNumber;
	}
	public String getCheckCode() {
		return checkCode;
	}
	public void setCheckCode(String checkCode) {
		this.checkCode = checkCode;
	}
	public String getQrcodeUrl() {
		return qrcodeUrl;
	}
	public void setQrcodeUrl(String qrcodeUrl) {
		this.qrcodeUrl = qrcodeUrl;
	}
	public Long getDeviceTypeId() {
		return deviceTypeId;
	}
	public void setDeviceTypeId(Long deviceTypeId) {
		this.deviceTypeId = deviceTypeId;
	}
	
}
