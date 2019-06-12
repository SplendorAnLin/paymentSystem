package com.yl.receive.front.model;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 代收交易请求
 * 
 * @author 聚合支付有限公司
 * @since 2016年10月17日
 * @version V1.0.0
 */
public class TradeRequest implements Serializable{
	
	private static final long serialVersionUID = -2243878570396679094L;
	
	/**
	 * 商户编号
	 */
	@NotNull
	@Size(max = 30)
	private String customerNo;

	/**
	 * 版本号
	 */
	@Size(min = 0, max = 30)
	private String versionCode;

	/**
	 * 商户参数
	 */
	@Size(min = 0, max = 200)
	private String customerParam;

	/**
	 * 商户请求时间
	 */
	@Size(min = 0, max = 50)
	private String customerRequestTime;

	/**
	 * 代收信息密文
	 */
	@NotNull
	private String cipherText;

	public String getCustomerNo() {
		return customerNo;
	}

	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}

	public String getVersionCode() {
		return versionCode;
	}

	public void setVersionCode(String versionCode) {
		this.versionCode = versionCode;
	}

	public String getCustomerParam() {
		return customerParam;
	}

	public void setCustomerParam(String customerParam) {
		this.customerParam = customerParam;
	}

	public String getCustomerRequestTime() {
		return customerRequestTime;
	}

	public void setCustomerRequestTime(String customerRequestTime) {
		this.customerRequestTime = customerRequestTime;
	}

	public String getCipherText() {
		return cipherText;
	}

	public void setCipherText(String cipherText) {
		this.cipherText = cipherText;
	}

	@Override
	public String toString() {
		return "TradeRequest [customerNo=" + customerNo + ", versionCode="
				+ versionCode + ", customerParam=" + customerParam
				+ ", customerRequestTime=" + customerRequestTime
				+ ", cipherText=" + cipherText + "]";
	}

}
