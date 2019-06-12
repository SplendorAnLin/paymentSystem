package com.yl.boss.api.bean;

import java.util.Date;

import com.yl.boss.api.enums.OrderStatus;
import com.yl.boss.api.enums.ProcessStatus;
import com.yl.boss.api.enums.PurchaseType;
import com.yl.boss.api.enums.Status;

/**
 * 服务商设备订单Bean
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月16日
 * @version V1.0.0
 */
public class AgentDeviceOrderBean  extends BaseBean{
	private static final long serialVersionUID = 6245573418304569970L;
	
	private String purchaseSerialNumber;//采购流水号
	private String ownerId;//服务商编号
	private PurchaseType purchaseChannel;//采购渠道
	private Integer quantity;//数量
	private double unitPrice;//单价
	private double total;//总价
	private String user;//采购人
	private Date createTime;//创建时间
	private Date updateTime;//更新时间
	private ProcessStatus flowStatus;//流程状态
	private Status purchaseStatus;//采购状态
	private Long deviceTypeId;//设备类型ID
	private String outOrderId;// 支付订单号
	private OrderStatus orderStatus; //支付状态
	
	
	public OrderStatus getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getOutOrderId() {
		return outOrderId;
	}
	public void setOutOrderId(String outOrderId) {
		this.outOrderId = outOrderId;
	}
	public String getPurchaseSerialNumber() {
		return purchaseSerialNumber;
	}
	public void setPurchaseSerialNumber(String purchaseSerialNumber) {
		this.purchaseSerialNumber = purchaseSerialNumber;
	}
	public String getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}
	public PurchaseType getPurchaseChannel() {
		return purchaseChannel;
	}
	public void setPurchaseChannel(PurchaseType purchaseChannel) {
		this.purchaseChannel = purchaseChannel;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public double getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(double unitPrice) {
		this.unitPrice = unitPrice;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
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
	public ProcessStatus getFlowStatus() {
		return flowStatus;
	}
	public void setFlowStatus(ProcessStatus flowStatus) {
		this.flowStatus = flowStatus;
	}
	public Status getPurchaseStatus() {
		return purchaseStatus;
	}
	public void setPurchaseStatus(Status purchaseStatus) {
		this.purchaseStatus = purchaseStatus;
	}
	public Long getDeviceTypeId() {
		return deviceTypeId;
	}
	public void setDeviceTypeId(Long deviceTypeId) {
		this.deviceTypeId = deviceTypeId;
	}
}
