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

public class AdvanceData {

	/** 接口版本号 */
	private Integer versionNo;

	/** 收款人姓名 */
	private String accName;

	/** 收款人电话 */
	private String accTel;

	/** 收款卡银行 */
	private String bankName;

	/** 收款卡号 */
	private String cardNo;

	/** 联行行号 */
	private String bankCode;

	/** 原下游交易流水号 */
	private String tradeNo;

	/** 原交易流水号 */
	private String transNo;

	/** 代付流水号 */
	private String advanceNo;

	/** 金额 */
	private BigDecimal amount;

	/** 商户编号 */
	private String mchNo;

	/** 代付状态 */
	private String status;

	/** 代付状态描述 */
	private String statusDesc;

	public Integer getVersionNo() {
		return versionNo;
	}

	public void setVersionNo(Integer versionNo) {
		this.versionNo = versionNo;
	}

	public String getAccName() {
		return accName;
	}

	public void setAccName(String accName) {
		this.accName = accName;
	}

	public String getAccTel() {
		return accTel;
	}

	public void setAccTel(String accTel) {
		this.accTel = accTel;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
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

	public String getAdvanceNo() {
		return advanceNo;
	}

	public void setAdvanceNo(String advanceNo) {
		this.advanceNo = advanceNo;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getMchNo() {
		return mchNo;
	}

	public void setMchNo(String mchNo) {
		this.mchNo = mchNo;
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

}
