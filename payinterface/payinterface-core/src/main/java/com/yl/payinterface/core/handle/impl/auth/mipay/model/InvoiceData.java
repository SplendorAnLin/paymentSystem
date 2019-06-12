/******************************************************************************
 *                                                                             
 *                      Woodare PROPRIETARY INFORMATION                        
 *                                                                             
 *          The information contained herein is proprietary to Woodare         
 *           and shall not be reproduced or disclosed in whole or in part      
 *                    or used for any design or manufacture                    
 *              without direct written authorization from FengDa.              
 *                                                                             
 *            Copyright (c) 2013 by Woodare.  All rights reserved.             
 *                                                                             
 *****************************************************************************/
package com.yl.payinterface.core.handle.impl.auth.mipay.model;

import java.math.BigDecimal;

public class InvoiceData {

	/** 接口版本号 */
	private Integer versionNo;

	/** 交易金额 */
	private BigDecimal price;

	/** 备注 */
	private String description;

	/** 商户号 * */
	private String mchNo;

	/** 交易流水号 */
	private String tradeNo;

	/** 上游交易流水号 */
	private String transNo;

	/** 交易返回值 */
	private String tranStr;

	/** 订单时间 */
	private String orderDate;

	/** 订单时间 */
	private String notifyUrl;

	private String callbackUrl;

	/** 交易类型 */
	private String orderType;

	/** 交易状态 */
	private String status;

	/** 交易状态描述 */
	private String statusDesc;
	
	/** 交易方式 */
	private String payMchType;

	/** 子商户号 */
	private String subMchNo;
	
	private String qfType;

	public Integer getVersionNo() {
		return versionNo;
	}

	public void setVersionNo(Integer versionNo) {
		this.versionNo = versionNo;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getMchNo() {
		return mchNo;
	}

	public void setMchNo(String mchNo) {
		this.mchNo = mchNo;
	}

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	public String getTransNo() {
		return transNo;
	}

	public void setTransNo(String transNo) {
		this.transNo = transNo;
	}

	public String getTranStr() {
		return tranStr;
	}

	public void setTranStr(String tranStr) {
		this.tranStr = tranStr;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	public String getNotifyUrl() {
		return notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatusDesc() {
		return statusDesc;
	}

	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
	}

	public String getCallbackUrl() {
		return callbackUrl;
	}

	public void setCallbackUrl(String callbackUrl) {
		this.callbackUrl = callbackUrl;
	}

	public String getPayMchType() {
		return payMchType;
	}

	public void setPayMchType(String payMchType) {
		this.payMchType = payMchType;
	}

	public String getSubMchNo() {
		return subMchNo;
	}

	public void setSubMchNo(String subMchNo) {
		this.subMchNo = subMchNo;
	}

	public String getQfType() {
		return qfType;
	}

	public void setQfType(String qfType) {
		this.qfType = qfType;
	}

}
