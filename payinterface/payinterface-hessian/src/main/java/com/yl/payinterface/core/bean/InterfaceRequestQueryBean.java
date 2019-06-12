package com.yl.payinterface.core.bean;

import java.io.Serializable;
import java.util.Date;

import com.yl.payinterface.core.enums.BusinessCompleteType;
import com.yl.payinterface.core.enums.InterfaceRequestType;
import com.yl.payinterface.core.enums.InterfaceTradeStatus;

/**
 * 提供方接口请求记录查询Bean
 * 
 * @author 聚合支付有限公司
 * @since 2016年4月24日
 * @version V1.0.0
 */
public class InterfaceRequestQueryBean implements Serializable {

	private static final long serialVersionUID = 6857124867313585731L;

	/** 主键 */
	private String id;
	/** 编号 */
	private String code;
	/** 版本号 */
	private Long version;
	/** 创建时间 */
	private Date createTime;
	/** 支付接口提供方编号 */
	private String interfaceProviderCode;
	/** 支付接口编号 */
	private String interfaceInfoCode;
	/** 业务请求订单号 */
	private String bussinessOrderID;
	/** 业务请求流水号 */
	private String bussinessFlowID;
	/** 接口请求订单号 */
	private String interfaceRequestID;
	/** 接口订单号 */
	private String interfaceOrderID;
	/** 订单金额 */
	private double amount;
	/** 手续费 */
	private Double fee;
	/** 接口请求时间 */
	private Date requestTime;
	/** 原接口请求订单号（针对退款） */
	private String originalInterfaceRequestID;
	/** 状态- */
	private InterfaceTradeStatus status;
	/** 卡类型 */
	private String cardType;
	/** 客户端IP */
	private String clientIP;
	/** 客户端Refer信息 */
	private String clientRefer;
	/** 返回码 */
	private String responseCode;
	/** 返回信息 */
	private String responseMessage;
	/** 请求完成时间 */
	private Date completeTime;
	/** 业务处理方式 */
	private InterfaceRequestType payInterfaceRequestType;
	/** 业务完成方式 */
	private BusinessCompleteType businessCompleteType;
	
	private String remark;
	/** 拥有者ID */
	private String ownerID;
	private String createTimeStart;
	private String createTimeEnd;
	private String requestTimeStart;
	private String requestTimeEnd;
	private String completeTimeStart;
	private String completeTimeEnd;
	private String amountStart;
	private String amountEnd;

	public String getInterfaceProviderCode() {
		return interfaceProviderCode;
	}

	public void setInterfaceProviderCode(String interfaceProviderCode) {
		this.interfaceProviderCode = interfaceProviderCode;
	}

	public String getInterfaceInfoCode() {
		return interfaceInfoCode;
	}

	public void setInterfaceInfoCode(String interfaceInfoCode) {
		this.interfaceInfoCode = interfaceInfoCode;
	}

	public String getBussinessOrderID() {
		return bussinessOrderID;
	}

	public void setBussinessOrderID(String bussinessOrderID) {
		this.bussinessOrderID = bussinessOrderID;
	}

	public String getBussinessFlowID() {
		return bussinessFlowID;
	}

	public void setBussinessFlowID(String bussinessFlowID) {
		this.bussinessFlowID = bussinessFlowID;
	}

	public String getInterfaceRequestID() {
		return interfaceRequestID;
	}

	public void setInterfaceRequestID(String interfaceRequestID) {
		this.interfaceRequestID = interfaceRequestID;
	}

	public String getInterfaceOrderID() {
		return interfaceOrderID;
	}

	public void setInterfaceOrderID(String interfaceOrderID) {
		this.interfaceOrderID = interfaceOrderID;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Double getFee() {
		return fee;
	}

	public void setFee(Double fee) {
		this.fee = fee;
	}

	public Date getRequestTime() {
		return requestTime;
	}

	public void setRequestTime(Date requestTime) {
		this.requestTime = requestTime;
	}

	public String getOriginalInterfaceRequestID() {
		return originalInterfaceRequestID;
	}

	public void setOriginalInterfaceRequestID(String originalInterfaceRequestID) {
		this.originalInterfaceRequestID = originalInterfaceRequestID;
	}

	public InterfaceTradeStatus getStatus() {
		return status;
	}

	public void setStatus(InterfaceTradeStatus status) {
		this.status = status;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getClientIP() {
		return clientIP;
	}

	public void setClientIP(String clientIP) {
		this.clientIP = clientIP;
	}

	public String getClientRefer() {
		return clientRefer;
	}

	public void setClientRefer(String clientRefer) {
		this.clientRefer = clientRefer;
	}

	public String getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	public String getResponseMessage() {
		return responseMessage;
	}

	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}

	public Date getCompleteTime() {
		return completeTime;
	}

	public void setCompleteTime(Date completeTime) {
		this.completeTime = completeTime;
	}

	public InterfaceRequestType getPayInterfaceRequestType() {
		return payInterfaceRequestType;
	}

	public void setPayInterfaceRequestType(InterfaceRequestType payInterfaceRequestType) {
		this.payInterfaceRequestType = payInterfaceRequestType;
	}

	public String getOwnerID() {
		return ownerID;
	}

	public void setOwnerID(String ownerID) {
		this.ownerID = ownerID;
	}

	public BusinessCompleteType getBusinessCompleteType() {
		return businessCompleteType;
	}

	public void setBusinessCompleteType(BusinessCompleteType businessCompleteType) {
		this.businessCompleteType = businessCompleteType;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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

	public String getRequestTimeStart() {
		return requestTimeStart;
	}

	public void setRequestTimeStart(String requestTimeStart) {
		this.requestTimeStart = requestTimeStart;
	}

	public String getRequestTimeEnd() {
		return requestTimeEnd;
	}

	public void setRequestTimeEnd(String requestTimeEnd) {
		this.requestTimeEnd = requestTimeEnd;
	}

	public String getCompleteTimeStart() {
		return completeTimeStart;
	}

	public void setCompleteTimeStart(String completeTimeStart) {
		this.completeTimeStart = completeTimeStart;
	}

	public String getCompleteTimeEnd() {
		return completeTimeEnd;
	}

	public void setCompleteTimeEnd(String completeTimeEnd) {
		this.completeTimeEnd = completeTimeEnd;
	}

	public String getAmountStart() {
		return amountStart;
	}

	public void setAmountStart(String amountStart) {
		this.amountStart = amountStart;
	}

	public String getAmountEnd() {
		return amountEnd;
	}

	public void setAmountEnd(String amountEnd) {
		this.amountEnd = amountEnd;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public String toString() {
		return "InterfaceRequestQueryBean [id=" + id + ", code=" + code + ", version=" + version + ", createTime=" + createTime + ", interfaceProviderCode=" + interfaceProviderCode + ", interfaceInfoCode=" + interfaceInfoCode + ", bussinessOrderID=" + bussinessOrderID + ", bussinessFlowID="
				+ bussinessFlowID + ", interfaceRequestID=" + interfaceRequestID + ", interfaceOrderID=" + interfaceOrderID + ", amount=" + amount + ", fee=" + fee + ", requestTime=" + requestTime + ", originalInterfaceRequestID=" + originalInterfaceRequestID + ", status=" + status + ", cardType="
				+ cardType + ", clientIP=" + clientIP + ", clientRefer=" + clientRefer + ", responseCode=" + responseCode + ", responseMessage=" + responseMessage + ", completeTime=" + completeTime + ", payInterfaceRequestType=" + payInterfaceRequestType + ", businessCompleteType="
				+ businessCompleteType + ", remark=" + remark + ", ownerID=" + ownerID + ", createTimeStart=" + createTimeStart + ", createTimeEnd=" + createTimeEnd + ", requestTimeStart=" + requestTimeStart + ", requestTimeEnd=" + requestTimeEnd + ", completeTimeStart=" + completeTimeStart
				+ ", completeTimeEnd=" + completeTimeEnd + ", amountStart=" + amountStart + ", amountEnd=" + amountEnd + "]";
	}

}