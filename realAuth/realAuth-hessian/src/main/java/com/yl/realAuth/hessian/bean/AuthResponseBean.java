package com.yl.realAuth.hessian.bean;

import java.io.Serializable;

import com.yl.realAuth.hessian.enums.AuthOrderStatus;
import com.yl.realAuth.hessian.enums.AuthResult;

/**
 * 实名认证响应实体
 * @author Shark
 * @since 2015年6月3日
 */
public class AuthResponseBean implements Serializable {

	private static final long serialVersionUID = 4962533566957759910L;
	/** 实名认证订单号 */
	private String orderCode;
	/** 实名认证状态 */
	private AuthOrderStatus authOrderStatus;
	/** 认证结果 */
	private AuthResult authResult;
	/** 响应信息 */
	private String responseMsg;
	/** 响应码 */
	private String responseCode;
	/** 交易成本 */
	private double cost;
	/** 接口订单号 */
	private String interfaceRequestId;

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public AuthResult getAuthResult() {
		return authResult;
	}

	public void setAuthResult(AuthResult authResult) {
		this.authResult = authResult;
	}

	public String getResponseMsg() {
		return responseMsg;
	}

	public void setResponseMsg(String responseMsg) {
		this.responseMsg = responseMsg;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public String getInterfaceRequestId() {
		return interfaceRequestId;
	}

	public void setInterfaceRequestId(String interfaceRequestId) {
		this.interfaceRequestId = interfaceRequestId;
	}

	public AuthOrderStatus getAuthOrderStatus() {
		return authOrderStatus;
	}

	public void setAuthOrderStatus(AuthOrderStatus authOrderStatus) {
		this.authOrderStatus = authOrderStatus;
	}

	public String getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	@Override
	public String toString() {
		return "AuthResponseBean [orderCode=" + orderCode + ", authOrderStatus=" + authOrderStatus + ", authResult=" + authResult + ", responseMsg=" + responseMsg
				+ ", cost=" + cost + ", interfaceRequestId=" + interfaceRequestId + ", responseCode=" + responseCode + "]";
	}

}
