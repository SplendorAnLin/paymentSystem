package com.yl.payinterface.core.model;

import com.yl.payinterface.core.enums.InterfaceType;
import com.yl.payinterface.core.enums.ReverseStatus;

/**
 * 
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月27日
 * @version V1.0.0
 */
public class InterfaceRequestReverse extends AutoStringIDModel {

	private static final long serialVersionUID = 7507254197383581950L;
	/** 接口编码 */
	private String interfaceInfoCode;
	/** 银行流水号 */
	private String interfaceOrderID;
	/** 接口提供方编码 */
	private String interfaceProviderCode;
	/** 支付接口订单号 */
	private String interfaceRequestID;
	/** 业务流水编码-交易系统 */
	private String bussinessOrderID;
	/** 卡种 */
	private String cardType;
	/** 金额 */
	private double amount;
	/** 补单状态 */
	private ReverseStatus reverseStatus = ReverseStatus.WAIT_REVERSE;
	/** 补单次数 */
	private int reverseCount;
	/** 业务类型 */
	private InterfaceType payType;

	public String getInterfaceInfoCode() {
		return interfaceInfoCode;
	}

	public void setInterfaceInfoCode(String interfaceInfoCode) {
		this.interfaceInfoCode = interfaceInfoCode;
	}

	public String getInterfaceOrderID() {
		return interfaceOrderID;
	}

	public void setInterfaceOrderID(String interfaceOrderID) {
		this.interfaceOrderID = interfaceOrderID;
	}

	public String getInterfaceProviderCode() {
		return interfaceProviderCode;
	}

	public void setInterfaceProviderCode(String interfaceProviderCode) {
		this.interfaceProviderCode = interfaceProviderCode;
	}

	public String getInterfaceRequestID() {
		return interfaceRequestID;
	}

	public void setInterfaceRequestID(String interfaceRequestID) {
		this.interfaceRequestID = interfaceRequestID;
	}

	public String getBussinessOrderID() {
		return bussinessOrderID;
	}

	public void setBussinessOrderID(String bussinessOrderID) {
		this.bussinessOrderID = bussinessOrderID;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
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

	public InterfaceType getPayType() {
		return payType;
	}

	public void setPayType(InterfaceType payType) {
		this.payType = payType;
	}

	@Override
	public String toString() {
		return "InterfaceRequestReverse [interfaceInfoCode=" + interfaceInfoCode + ", interfaceOrderID=" + interfaceOrderID + ", interfaceProviderCode=" + interfaceProviderCode + ", interfaceRequestID=" + interfaceRequestID + ", bussinessOrderID=" + bussinessOrderID + ", cardType=" + cardType
				+ ", amount=" + amount + ", reverseStatus=" + reverseStatus + ", reverseCount=" + reverseCount + ", payType=" + payType + "]";
	}

}
