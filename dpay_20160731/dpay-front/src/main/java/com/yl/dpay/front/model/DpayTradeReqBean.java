package com.yl.dpay.front.model;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 代付下单bean
 * 
 * @author 聚合支付有限公司
 * @since 2016年5月22日
 * @version V1.0.0
 */
public class DpayTradeReqBean implements Serializable {

	private static final long serialVersionUID = -4485683571001962525L;

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
	 * 代付信息密文
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
		StringBuilder builder = new StringBuilder();
		builder.append("DpayTradeReqBean [customerNo=");
		builder.append(customerNo);
		builder.append(", versionCode=");
		builder.append(versionCode);
		builder.append(", customerParam=");
		builder.append(customerParam);
		builder.append(", customerRequestTime=");
		builder.append(customerRequestTime);
		builder.append(", cipherText=");
		builder.append(cipherText);
		builder.append("]");
		return builder.toString();
	}

}
