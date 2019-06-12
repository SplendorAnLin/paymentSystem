package com.yl.payinterface.core.handle.impl.remit.klt100004.single.dto.impl.query.request;

import com.yl.payinterface.core.handle.impl.remit.klt100004.single.dto.IBody;

/**
 * 开联通
 *
 * @author AnLin
 * @version V1.0.0
 * @since 2018/4/3
 */
public class QueryRequestBody implements IBody {
	private String mchtId;
	private String mchtOrderNo;
	private String mchtBatchNo;
	private String paymentBusinessType;
	private String orderDate;
	public String getMchtId() {
		return mchtId;
	}
	public String getMchtOrderNo() {
		return mchtOrderNo;
	}

	public void setMchtId(String mchtId) {
		this.mchtId = mchtId;
	}

	public void setMchtOrderNo(String mchtOrderNo) {
		this.mchtOrderNo = mchtOrderNo;
	}
	public String getMchtBatchNo() {
		return mchtBatchNo;
	}
	public void setMchtBatchNo(String mchtBatchNo) {
		this.mchtBatchNo = mchtBatchNo;
	}
	public String getPaymentBusinessType() {
		return paymentBusinessType;
	}
	public void setPaymentBusinessType(String paymentBusinessType) {
		this.paymentBusinessType = paymentBusinessType;
	}
	public String getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}
}