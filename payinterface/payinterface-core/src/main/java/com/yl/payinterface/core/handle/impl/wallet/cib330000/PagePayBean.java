package com.yl.payinterface.core.handle.impl.wallet.cib330000;

/**
 * 交易分页Bean
 * 
 * @author 聚合支付有限公司
 * @since 2016年11月18日
 * @version V1.0.0
 */
public class PagePayBean {
	private String appId;
	private String timeStamp;
	private String nonceStr;
	private String Package;
	private String signType;

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getNonceStr() {
		return nonceStr;
	}

	public void setNonceStr(String nonceStr) {
		this.nonceStr = nonceStr;
	}

	public String getPackage() {
		return Package;
	}

	public void setPackage(String package1) {
		Package = package1;
	}

	public String getSignType() {
		return signType;
	}

	public void setSignType(String signType) {
		this.signType = signType;
	}

	@Override
	public String toString() {
		return "PagePayBean [appId=" + appId + ", timeStamp=" + timeStamp + ", nonceStr=" + nonceStr + ", Package=" + Package + ", signType=" + signType + "]";
	}

}
