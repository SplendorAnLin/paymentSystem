package com.yl.pay.pos.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "ORDER_SEND_FAIL_RECORD")
public class OrderSendFailRecord extends BaseEntity{

	private static final long serialVersionUID = -7366135641431675564L;

	private long orderId;
	
	private Date createTime;

	@Column(name = "ORDER_ID",columnDefinition = "int", length = 11)
	public long getOrderId() {
		return orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}

	@Column(name = "CREATE_TIME",columnDefinition = "DATETIME")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	
}
