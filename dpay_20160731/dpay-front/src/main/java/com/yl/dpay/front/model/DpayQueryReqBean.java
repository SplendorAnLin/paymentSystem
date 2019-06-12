package com.yl.dpay.front.model;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 代付查询请求bean
 * 
 * @author 聚合支付有限公司
 * @since 2016年5月22日
 * @version V1.0.0
 */
public class DpayQueryReqBean implements Serializable {

	private static final long serialVersionUID = 9143050289010618148L;

	/**
	 * 商户编号
	 */
	@NotNull
	@Size(max = 30)
	private String customerNo;

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

	public String getCipherText() {
		return cipherText;
	}

	public void setCipherText(String cipherText) {
		this.cipherText = cipherText;
	}

	@Override
	public String toString() {
		return "DpayQueryReqBean [customerNo=" + customerNo + ", cipherText="
				+ cipherText + "]";
	}

}
