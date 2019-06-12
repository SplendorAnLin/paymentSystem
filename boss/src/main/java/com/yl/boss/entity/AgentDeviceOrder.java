package com.yl.boss.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.yl.agent.api.enums.Status;
import com.yl.boss.api.enums.OrderStatus;
import com.yl.boss.api.enums.ProcessStatus;
import com.yl.boss.enums.PurchaseType;

/**
 * 服务商设备订单Bean
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月11日
 * @version V1.0.0
 */
@Entity
@Table(name = "AGENT_DEVICE_ORDER")
public class AgentDeviceOrder extends AutoIDEntity{
	private static final long serialVersionUID = 6245573418304569970L;
	
	@NotNull
	private String purchaseSerialNumber;//采购流水号
	private String ownerId;//服务商编号
	@NotNull
	private PurchaseType purchaseChannel;//采购渠道
	@NotNull
	private Integer quantity;//数量
	@NotNull
	private double unitPrice;//单价
	@NotNull
	private double total;//总价
	@NotNull
	private String user;//采购人
	@NotNull
	private Date createTime;//创建时间
	private Date updateTime;//更新时间
	@NotNull
	private Long deviceTypeId;//设备类型ID
	@NotNull
	private ProcessStatus flowStatus;//流程状态
	@NotNull
	private Status purchaseStatus;//采购状态
	@NotNull
	private String outOrderId;// 支付订单号
	@NotNull
	private OrderStatus orderStatus; //支付状态
	

	@Enumerated(value = EnumType.STRING)
	@Column(name = "ORDER_STATUS", length = 30)
	public OrderStatus getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}
	@Column(name = "OUT_ORDER_ID", length = 35)
	public String getOutOrderId() {
		return outOrderId;
	}
	public void setOutOrderId(String outOrderId) {
		this.outOrderId = outOrderId;
	}
	@Column(name = "PURCHASE_SERIAL_NUMBER", length = 35)
	public String getPurchaseSerialNumber() {
		return purchaseSerialNumber;
	}
	public void setPurchaseSerialNumber(String purchaseSerialNumber) {
		this.purchaseSerialNumber = purchaseSerialNumber;
	}

	@Column(name = "OWNER_ID", length = 30)
	public String getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}
	@Enumerated(value = EnumType.STRING)
	@Column(name = "PURCHASE_CHANNEL", columnDefinition = "VARCHAR(30)")
	public PurchaseType getPurchaseChannel() {
		return purchaseChannel;
	}
	public void setPurchaseChannel(PurchaseType purchaseChannel) {
		this.purchaseChannel = purchaseChannel;
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
	@Column(name = "FLOW_STATUS", length = 30)
	public ProcessStatus getFlowStatus() {
		return flowStatus;
	}
	public void setFlowStatus(ProcessStatus flowStatus) {
		this.flowStatus = flowStatus;
	}
	@Enumerated(value = EnumType.STRING)
	@Column(name = "PURCHASE_STATUS",length = 30)
	public Status getPurchaseStatus() {
		return purchaseStatus;
	}
	public void setPurchaseStatus(Status purchaseStatus) {
		this.purchaseStatus = purchaseStatus;
	}
	@Column(name = "DEVICE_TYPE_ID")
	public Long getDeviceTypeId() {
		return deviceTypeId;
	}
	public void setDeviceTypeId(Long deviceTypeId) {
		this.deviceTypeId = deviceTypeId;
	}
}