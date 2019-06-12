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
 * ClassName: DownSubMerchant
 * 
 * @description
 * @author Xinxing Jiang
 * @Date Jan 16, 2017
 * 
 */
public class SubMerchant {
	/** 版本号 */
	private Integer versionNo;
	/** 商户编号 */
	private String mchNo;

	/** 下游商户号 */
	private String subMchId;

	/** 子商户编号 */
	private String subMchNo;

	/** 民生代理商号 */
	private String cooperator;

	/** 通道类型 */
	private String payWay;

	/** 商户名 */
	private String shortName;

	/** 商户全名 */
	private String merchantName;

	/** 地址 */
	private String address;

	/** 手机号 */
	private String phone;

	/** 商户类型 */
	private String category;

	/** 结算卡号 */
	private String accNo;

	/** 收款人姓名 */
	private String accName;

	/** 银行联行号 */
	private String bankType;

	/** 银行名 */
	private String bankName;

	/** T0代付费率 */
	private BigDecimal t0drawFee;

	/** T0结算额 */
	private BigDecimal t0tradeRate;

	/** T1代付费率 */
	private BigDecimal t1drawFee;

	/** T1结算额 */
	private BigDecimal t1tradeRate;

	/** 组织机构号 */
	private String orgCode;

	/** 联系人姓名 */
	private String contactName;

	/** 联系人电话 */
	private String contactPhone;

	/** 联系人电话 */
	private String contactMobile;

	/** 联系人邮箱 */
	private String contactEmail;

	/** 商户身份证号 */
	private String idCard;

	/** 商户营业执照号 */
	private String merchantLicense;

	/** 备注 */
	private String remark;

	/** 状态 */
	private String status;

	public String getMchNo() {
		return mchNo;
	}

	public void setMchNo(String mchNo) {
		this.mchNo = mchNo;
	}

	public String getSubMchId() {
		return subMchId;
	}

	public void setSubMchId(String subMchId) {
		this.subMchId = subMchId;
	}

	public String getSubMchNo() {
		return subMchNo;
	}

	public void setSubMchNo(String subMchNo) {
		this.subMchNo = subMchNo;
	}

	public String getCooperator() {
		return cooperator;
	}

	public void setCooperator(String cooperator) {
		this.cooperator = cooperator;
	}

	public String getPayWay() {
		return payWay;
	}

	public void setPayWay(String payWay) {
		this.payWay = payWay;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getAccNo() {
		return accNo;
	}

	public void setAccNo(String accNo) {
		this.accNo = accNo;
	}

	public String getAccName() {
		return accName;
	}

	public void setAccName(String accName) {
		this.accName = accName;
	}

	public String getBankType() {
		return bankType;
	}

	public void setBankType(String bankType) {
		this.bankType = bankType;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public BigDecimal getT0drawFee() {
		return t0drawFee;
	}

	public void setT0drawFee(BigDecimal t0drawFee) {
		this.t0drawFee = t0drawFee;
	}

	public BigDecimal getT0tradeRate() {
		return t0tradeRate;
	}

	public void setT0tradeRate(BigDecimal t0tradeRate) {
		this.t0tradeRate = t0tradeRate;
	}

	public BigDecimal getT1drawFee() {
		return t1drawFee;
	}

	public void setT1drawFee(BigDecimal t1drawFee) {
		this.t1drawFee = t1drawFee;
	}

	public BigDecimal getT1tradeRate() {
		return t1tradeRate;
	}

	public void setT1tradeRate(BigDecimal t1tradeRate) {
		this.t1tradeRate = t1tradeRate;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public String getContactMobile() {
		return contactMobile;
	}

	public void setContactMobile(String contactMobile) {
		this.contactMobile = contactMobile;
	}

	public String getContactEmail() {
		return contactEmail;
	}

	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getMerchantLicense() {
		return merchantLicense;
	}

	public void setMerchantLicense(String merchantLicense) {
		this.merchantLicense = merchantLicense;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getVersionNo() {
		return versionNo;
	}

	public void setVersionNo(Integer versionNo) {
		this.versionNo = versionNo;
	}

}
