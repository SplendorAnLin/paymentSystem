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
 * 设备订单历史Bean
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月11日
 * @version V1.0.0
 */
@Entity
@Table(name = "DEVICE_PURCH_HISTORY")
public class DevicePurchHistory extends AutoIDEntity{
	private static final long serialVersionUID = 8087460773348745556L;
	@NotNull
	private String batchNumber;//批次号
	@NotNull
	private Integer quantity;//数量
	@NotNull
	private double unitPrice;//单价
	@NotNull
	private double total;//总价
	@NotNull
	private Long deviceTypeId;//设备类型ID
	@NotNull
	private String user;//采购人
	@NotNull
	private Date createTime;//创建时间
	@NotNull
	private Date updateTime;//最后更新时间
	@NotNull
	private ProcessStatus purchaseStatus;//采购状态
	private String oper;//操作人
	
	@Column(name = "BATCH_NUMBER",length=30)
	public String getBatchNumber() {
		return batchNumber;
	}
	public void setBatchNumber(String batchNumber) {
		this.batchNumber = batchNumber;
	}
	@Column(name = "QUANTITY")
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	@Column(name = "UNIT_PRICE", precision = 10, scale = 2)
	public double getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(double unitPrice) {
		this.unitPrice = unitPrice;
	}
	@Column(name = "TOTAL", precision = 10, scale = 2)
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	@Column(name = "USER", length = 30)
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
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
	@Enumerated(value = EnumType.STRING)
	@Column(name = "PURCHASE_STATUS", length = 30)
	public ProcessStatus getPurchaseStatus() {
		return purchaseStatus;
	}
	public void setPurchaseStatus(ProcessStatus purchaseStatus) {
		this.purchaseStatus = purchaseStatus;
	}
	@Column(name = "OPER", length = 30)
	public String getOper() {
		return oper;
	}
	public void setOper(String oper) {
		this.oper = oper;
	}
	@Column(name = "DEVICE_TYPE_ID")
	public Long getDeviceTypeId() {
		return deviceTypeId;
	}
	public void setDeviceTypeId(Long deviceTypeId) {
		this.deviceTypeId = deviceTypeId;
	}
	public DevicePurchHistory(DevicePurch devicePurch, String oper) {
		super();
		this.batchNumber = devicePurch.getBatchNumber();
		this.quantity = devicePurch.getQuantity();
		this.unitPrice = devicePurch.getUnitPrice();
		this.total = devicePurch.getTotal();
		this.user = devicePurch.getUser();
		this.deviceTypeId=devicePurch.getDeviceTypeId();
		this.createTime = devicePurch.getCreateTime();
		this.updateTime = devicePurch.getUpdateTime();
		this.purchaseStatus = devicePurch.getPurchaseStatus();
		this.oper = oper;
	}
	
	public DevicePurchHistory() {
		super();
	}
	
}
