package com.yl.receive.front.model;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

/**
 * 代收查询请求信息
 * 
 * @author 聚合支付有限公司
 * @since 2016年10月17日
 * @version V1.0.0
 */
public class QueryRequest implements Serializable{

	private static final long serialVersionUID = -6154130455233325334L;
	/** 所有者编号 */
	@NotNull
	private String customerNo;
	/** 接口版本号 */
	@NotNull
	private String versionCode;
	/** 密文 */
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
	public String getCipherText() {
		return cipherText;
	}
	public void setCipherText(String cipherText) {
		this.cipherText = cipherText;
	}
	@Override
	public String toString() {
		return "QueryRequest [customerNo=" + customerNo + ", versionCode="
				+ versionCode + ", cipherText=" + cipherText + "]";
	}

}
