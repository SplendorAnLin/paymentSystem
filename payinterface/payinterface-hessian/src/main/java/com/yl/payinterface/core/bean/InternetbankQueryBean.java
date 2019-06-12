package com.yl.payinterface.core.bean;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.lefu.hessian.bean.JsonBean;

/**
 * 支付查询接口实体
 * 
 * @author 聚合支付有限公司
 * @since 2016年4月24日
 * @version V1.0.0
 */
public class InternetbankQueryBean implements JsonBean {
	
	private static final long serialVersionUID = 8266160031208231511L;
	
	@NotBlank
	/** 支付接口编号 */
	private String interfaceInfoCode;
	/** 支付接口订单号（查询） */
	@NotBlank
	private String interfaceRequestID;
	/** 原接口请求订单号 */
	@NotBlank
	private String originalInterfaceRequestID;
	/** 原接口支付请求时间 */
	@NotNull
	private Date originalInterfaceRequestTime;
	/** 订单金额 */
	@NotNull
	private double amount;
	/** 业务完成方式 */
	@NotBlank
	private String businessCompleteType;

	public String getInterfaceInfoCode() {
		return interfaceInfoCode;
	}

	public void setInterfaceInfoCode(String interfaceInfoCode) {
		this.interfaceInfoCode = interfaceInfoCode;
	}

	public String getInterfaceRequestID() {
		return interfaceRequestID;
	}

	public void setInterfaceRequestID(String interfaceRequestID) {
		this.interfaceRequestID = interfaceRequestID;
	}

	public String getOriginalInterfaceRequestID() {
		return originalInterfaceRequestID;
	}

	public void setOriginalInterfaceRequestID(String originalInterfaceRequestID) {
		this.originalInterfaceRequestID = originalInterfaceRequestID;
	}

	public Date getOriginalInterfaceRequestTime() {
		return originalInterfaceRequestTime;
	}

	public void setOriginalInterfaceRequestTime(Date originalInterfaceRequestTime) {
		this.originalInterfaceRequestTime = originalInterfaceRequestTime;
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

	@Override
	public String toString() {
		return "InternetbankQueryBean [interfaceInfoCode=" + interfaceInfoCode + ", interfaceRequestID=" + interfaceRequestID + ", originalInterfaceRequestID="
				+ originalInterfaceRequestID + ", originalInterfaceRequestTime=" + originalInterfaceRequestTime + ", amount=" + amount + ", businessCompleteType="
				+ businessCompleteType + "]";
	}

}
