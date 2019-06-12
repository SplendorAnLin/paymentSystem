package com.yl.online.model.model;

import java.util.Map;

import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.online.model.enums.ReverseStatus;

/**
 * 交易系统消费结果补单实体Bean
 * 
 * @author 聚合支付有限公司
 * @since 2016年7月23日
 * @version V1.0.0
 */
public class SalesResultReverseOrder extends AutoStringIDModel {

	private static final long serialVersionUID = 3274605786399733447L;
	/** 请求资金通道订单号 */
	private String interfaceRequestID;
	/** 银行响应订单号 */
	private String interfaceOrderID;
	/** 交易系统支付记录流水号 */
	private String businessOrderID;
	/** 接口提供方编码 */
	private String interfaceProvider;
	/** 卡种 */
	private String cardType;
	/** 消费处理结果码 */
	private String transStatus;
	/** 响应码 */
	private String responseCode;
	/** 响应描述 */
	private String responseMessage;
	/** 资金通道响应支付金额 */
	private String amount;
	/** 接口成本 */
	private String interfaceFee;
	/** 业务处理类型 */
	private String businessType = "SAILS";
	/** 消息处理状态 */
	private ReverseStatus reverseStatus = ReverseStatus.WAIT_REVERSE;
	/** 消息补单次数 */
	private int reverseCount;

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

	public String getTransStatus() {
		return transStatus;
	}

	public void setTransStatus(String transStatus) {
		this.transStatus = transStatus;
	}

	public String getResponseMessage() {
		return responseMessage;
	}

	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}

	public String getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	public String getBusinessOrderID() {
		return businessOrderID;
	}

	public void setBusinessOrderID(String businessOrderID) {
		this.businessOrderID = businessOrderID;
	}

	public String getInterfaceProvider() {
		return interfaceProvider;
	}

	public void setInterfaceProvider(String interfaceProvider) {
		this.interfaceProvider = interfaceProvider;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getInterfaFee() {
		return interfaceFee;
	}

	public void setInterfaFee(String interfaFee) {
		this.interfaceFee = interfaFee;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public ReverseStatus getReverseStatus() {
		return reverseStatus;
	}

	public void setReverseStatus(ReverseStatus reverseStatus) {
		this.reverseStatus = reverseStatus;
	}

	public int getReverseCount() {
		return reverseCount;
	}

	public void setReverseCount(int reverseCount) {
		this.reverseCount = reverseCount;
	}

	public static SalesResultReverseOrder parseMapToBean(Map<String, String> payResultParams) {
		return JsonUtils.toJsonToObject(payResultParams, SalesResultReverseOrder.class);
	}
	
	public String getInterfaceFee() {
		return interfaceFee;
	}

	public void setInterfaceFee(String interfaceFee) {
		this.interfaceFee = interfaceFee;
	}

	@Override
	public String toString() {
		return "SalesResultReverseOrder [interfaceRequestID=" + interfaceRequestID + ", interfaceOrderID=" + interfaceOrderID + ", businessOrderID="
				+ businessOrderID + ", interfaceProvider=" + interfaceProvider + ", cardType=" + cardType + ", transStatus=" + transStatus + ", responseCode="
				+ responseCode + ", responseMessage=" + responseMessage + ", amount=" + amount + ", interfaFee=" + interfaceFee + ", businessType=" + businessType
				+ ", reverseStatus=" + reverseStatus + ", reverseCount=" + reverseCount + "]";
	}

}
