package com.yl.payinterface.core.bean;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

/**
 * 代收查询Bean
 * 
 * @author 聚合支付有限公司
 * @since 2016年4月24日
 * @version V1.0.0
 */
public class ReceiveQueryBean implements Serializable {
	
	private static final long serialVersionUID = -3759254757501151944L;
	/** 代收订单号（查询） */
	@NotBlank
	private String BusinessOrderID;
	/** 原接口请求订单号 */
	@NotBlank
	private String originalInterfaceRequestID;
	/** 订单金额 */
	@NotNull
	private double amount;
	/** 业务完成方式 */
	@NotBlank
	private String businessCompleteType;

	public String getOriginalInterfaceRequestID() {
		return originalInterfaceRequestID;
	}

	public void setOriginalInterfaceRequestID(String originalInterfaceRequestID) {
		this.originalInterfaceRequestID = originalInterfaceRequestID;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getBusinessCompleteType() {
		return businessCompleteType;
	}

	public void setBusinessCompleteType(String businessCompleteType) {
		this.businessCompleteType = businessCompleteType;
	}

	public String getBusinessOrderID() {
		return BusinessOrderID;
	}

	public void setBusinessOrderID(String businessOrderID) {
		BusinessOrderID = businessOrderID;
	}

	@Override
	public String toString() {
		return "ReceiveQueryBean [BusinessOrderID=" + BusinessOrderID
				+ ", originalInterfaceRequestID=" + originalInterfaceRequestID
				+ ", amount=" + amount + ", businessCompleteType="
				+ businessCompleteType + "]";
	}
	
}
