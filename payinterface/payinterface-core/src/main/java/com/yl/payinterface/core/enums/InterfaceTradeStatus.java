package com.yl.payinterface.core.enums;

/**
 * 接口交易状态
 * 
 * @author 聚合支付有限公司
 * @since 2016年11月15日
 * @version V1.0.0
 */
public enum InterfaceTradeStatus {
	/** 未知 */
	UNKNOWN("未知"),
	/** 关闭 */
	CLOSED("关闭"),
	/** 成功 */
	SUCCESS("成功"),
	/** 失败待确认 */
	CONFIRM("失败待确认"),
	/** 失败 */
	FAILED("失败");
	
	private final String message;
	
	InterfaceTradeStatus(String message){
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
	
}
