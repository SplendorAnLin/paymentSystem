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

/**
 * 
 * ClassName: DownMsTixianData
 * 
 * @description
 * @author woo_maven_auto_generate
 * @Date 2017/01/17
 * 
 */
public class DrawData {

	/** 接口版本号 */
	private Integer versionNo;

	/** 商户号 * */
	private String mchNo;

	/** 子商户号 */
	private String subMchNo;

	/** 下游提现流水号 */
	private String advanceNo;

	/** 订单时间 */
	private String orderDate;

	/** 商户实收 */
	private BigDecimal drawAmount;

	/** 提现单笔手续费 */
	private BigDecimal drawFee;

	/** 交易手续费 */
	private BigDecimal tradeFee;

	/** 结算时间 */
	private String settleDate;

	public Integer getVersionNo() {
		return versionNo;
	}

	public void setVersionNo(Integer versionNo) {
		this.versionNo = versionNo;
	}

	public String getMchNo() {
		return mchNo;
	}

	public void setMchNo(String mchNo) {
		this.mchNo = mchNo;
	}

	public String getSubMchNo() {
		return subMchNo;
	}

	public void setSubMchNo(String subMchNo) {
		this.subMchNo = subMchNo;
	}

	public String getAdvanceNo() {
		return advanceNo;
	}

	public void setAdvanceNo(String advanceNo) {
		this.advanceNo = advanceNo;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	public BigDecimal getDrawAmount() {
		return drawAmount;
	}

	public void setDrawAmount(BigDecimal drawAmount) {
		this.drawAmount = drawAmount;
	}

	public BigDecimal getDrawFee() {
		return drawFee;
	}

	public void setDrawFee(BigDecimal drawFee) {
		this.drawFee = drawFee;
	}

	public BigDecimal getTradeFee() {
		return tradeFee;
	}

	public void setTradeFee(BigDecimal tradeFee) {
		this.tradeFee = tradeFee;
	}

	public String getSettleDate() {
		return settleDate;
	}

	public void setSettleDate(String settleDate) {
		this.settleDate = settleDate;
	}

	/**
	 * @return the deleteStatus
	 */
	// public EnumDeleteStatus getDeleteStatus() {
	// return deleteStatus;
	// }

	/**
	 * @param deleteStatus
	 *            the deleteStatus to set
	 */
	// public void setDeleteStatus(EnumDeleteStatus deleteStatus) {
	// this.deleteStatus = deleteStatus;
	// }

}
