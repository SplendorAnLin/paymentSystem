package com.yl.realAuth.model;

import java.util.Date;

import com.yl.realAuth.enums.AuthResult;
import com.yl.realAuth.enums.NotifyStatus;

/**
 * 商户消费结果的订单实体
 * @author Shark
 * @since 2015年6月2日
 */
public class AuthResultNotifyOrder extends AutoStringIDModel {

	private static final long serialVersionUID = -5916110605973190161L;
	/** 交易订单编号 */
	private String orderCode;
	/** 交易订单处理结果 */
	private AuthResult authResult;
	/** 合作方请求流水号（唯一） */
	private String requestCode;
	/** 合作方后台通知地址 */
	private String notifyURL;
	/** 通知结果 */
	private NotifyStatus notifyResult;
	/** 通知次数 */
	private int notifyCount;
	/** 补单下次触发时间 */
	private Date nextFireTime;

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public String getRequestCode() {
		return requestCode;
	}

	public void setRequestCode(String requestCode) {
		this.requestCode = requestCode;
	}

	public String getNotifyURL() {
		return notifyURL;
	}

	public void setNotifyURL(String notifyURL) {
		this.notifyURL = notifyURL;
	}

	public NotifyStatus getNotifyResult() {
		return notifyResult;
	}

	public void setNotifyResult(NotifyStatus notifyResult) {
		this.notifyResult = notifyResult;
	}

	public int getNotifyCount() {
		return notifyCount;
	}

	public void setNotifyCount(int notifyCount) {
		this.notifyCount = notifyCount;
	}


	public Date getNextFireTime() {
		return nextFireTime;
	}

	public void setNextFireTime(Date nextFireTime) {
		this.nextFireTime = nextFireTime;
	}

	public AuthResult getAuthResult() {
		return authResult;
	}

	public void setAuthResult(AuthResult authResult) {
		this.authResult = authResult;
	}

	@Override
	public String toString() {
		return "AuthResultNotifyOrder [orderCode=" + orderCode + ", authResult=" + authResult + ", requestCode=" + requestCode + ", notifyURL=" + notifyURL
				+ ", notifyResult=" + notifyResult + ", notifyCount=" + notifyCount + ", nextFireTime=" + nextFireTime + "]";
	}

}
