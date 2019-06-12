package com.yl.dpay.front.model;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import net.sf.json.util.JSONStringer;

/**
 * 代付查询信息Bean
 * 
 * @author 聚合支付有限公司
 * @since 2016年5月22日
 * @version V1.0.0
 */
public class DpayQueryInfo implements Serializable {

	private static final long serialVersionUID = -7821074630427116399L;

	/**
	 * 商户编号
	 */
	@NotNull
	@Size(max = 30)
	private String customerNo;

	/**
	 * 商户订单号
	 */
	@NotNull
	@Size(max = 30)
	private String cutomerOrderCode;

	public String getCustomerNo() {
		return customerNo;
	}

	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}

	public String getCutomerOrderCode() {
		return cutomerOrderCode;
	}

	public void setCutomerOrderCode(String cutomerOrderCode) {
		this.cutomerOrderCode = cutomerOrderCode;
	}

	@Override
	public String toString() {
		return "DpayQueryInfo [customerNo=" + customerNo + ", cutomerOrderCode=" + cutomerOrderCode + "]";
	}

	public DpayQueryInfo(DpayQueryReqBean dpayQueryReqBean) {
		super();
		this.customerNo = dpayQueryReqBean.getCustomerNo();
	}

	public String toJsonString() {
		JSONStringer jsonStringer = new JSONStringer();
		jsonStringer.object();
		jsonStringer.key("customerNo");
		jsonStringer.value(customerNo);
		jsonStringer.key("cutomerOrderCode");
		jsonStringer.value(cutomerOrderCode);
		jsonStringer.endObject();
		return jsonStringer.toString();
	}

	public DpayQueryInfo() {
		super();
	}

}
