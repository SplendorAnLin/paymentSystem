package com.yl.online.gateway.bean;

import java.io.Serializable;

/**
 * 支付成功页面参数
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月4日
 * @version V1.0.0
 */
public class MobilePaySuccessParam implements Serializable {

	private static final long serialVersionUID = -3616862617750357142L;
	/** 支付订单号 */
	private String orderId;
	/** 返回信息 */
	private String returnMessage;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getReturnMessage() {
		return returnMessage;
	}

	public void setReturnMessage(String returnMessage) {
		this.returnMessage = returnMessage;
	}

}
