package com.yl.payinterface.core.model;

import java.util.Date;
import com.yl.payinterface.core.enums.BusinessCompleteType;
import com.yl.payinterface.core.enums.InterfaceRequestType;
import com.yl.payinterface.core.enums.InterfaceTradeStatus;

/**
 * 提供方接口请求记录
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月27日
 * @version V1.0.0
 */
public class InterfaceRequest extends AutoStringIDModel {

	private static final long serialVersionUID = 6857124867313585731L;

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
	private InterfaceTradeStatus status = InterfaceTradeStatus.UNKNOWN;
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
	/** 拥有者ID */
	private String ownerID;
	/** 备注 */
	private String remark;

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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public String toString() {
		return "InterfaceRequest [interfaceProviderCode="
				+ interfaceProviderCode + ", interfaceInfoCode="
				+ interfaceInfoCode + ", bussinessOrderID=" + bussinessOrderID
				+ ", bussinessFlowID=" + bussinessFlowID
				+ ", interfaceRequestID=" + interfaceRequestID
				+ ", interfaceOrderID=" + interfaceOrderID + ", amount="
				+ amount + ", fee=" + fee + ", requestTime=" + requestTime
				+ ", originalInterfaceRequestID=" + originalInterfaceRequestID
				+ ", status=" + status + ", cardType=" + cardType
				+ ", clientIP=" + clientIP + ", clientRefer=" + clientRefer
				+ ", responseCode=" + responseCode + ", responseMessage="
				+ responseMessage + ", completeTime=" + completeTime
				+ ", payInterfaceRequestType=" + payInterfaceRequestType
				+ ", businessCompleteType=" + businessCompleteType
				+ ", ownerID=" + ownerID + "]";
	}

}