package com.yl.payinterface.core.handle.impl.auth.rxt100001;

/**
 * 支付响应实体
 * 
 * @author 聚合支付有限公司
 * @since 2017年4月28日
 * @version V1.0.0
 */
public class PayRespVO {
	
	private String invokeStatus;
	private String failReason;
	private String redirectUrl;
	
	public String getFailReason() {
		return failReason;
	}
	public void setFailReason(String failReason) {
		this.failReason = failReason;
	}
	public String getRedirectUrl() {
		return redirectUrl;
	}
	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}
	public String getInvokeStatus() {
		return invokeStatus;
	}
	public void setInvokeStatus(String invokeStatus) {
		this.invokeStatus = invokeStatus;
	}

	

}
