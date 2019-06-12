package com.yl.payinterface.core.model;

import com.yl.payinterface.core.enums.InterfaceTradeStatus;
import com.yl.payinterface.core.enums.ReverseStatus;

/**
 * 补单接口完成信息实体
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月27日
 * @version V1.0.0
 */
public class InterfaceRequestCompleteReverse extends AutoStringIDModel {

	private static final long serialVersionUID = 565816115432776633L;

	/** 支付接口编码-对应原支付接口请求流水号 */
	private String interfaceRequestID;
	/** 接口交易状态 */
	private InterfaceTradeStatus interfaceTradeStatus;
	/** 补单状态 */
	private ReverseStatus reverseStatus = ReverseStatus.WAIT_REVERSE;
	/** 补单次数 */
	private int reverseCount;

	public String getInterfaceRequestID() {
		return interfaceRequestID;
	}

	public void setInterfaceRequestID(String interfaceRequestID) {
		this.interfaceRequestID = interfaceRequestID;
	}

	public InterfaceTradeStatus getInterfaceTradeStatus() {
		return interfaceTradeStatus;
	}

	public void setInterfaceTradeStatus(InterfaceTradeStatus interfaceTradeStatus) {
		this.interfaceTradeStatus = interfaceTradeStatus;
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

	@Override
	public String toString() {
		return "InterfaceRequestCompleteReverse [interfaceRequestID=" + interfaceRequestID + ", interfaceTradeStatus=" + interfaceTradeStatus + ", reverseStatus="
				+ reverseStatus + ", reverseCount=" + reverseCount + "]";
	}

}
