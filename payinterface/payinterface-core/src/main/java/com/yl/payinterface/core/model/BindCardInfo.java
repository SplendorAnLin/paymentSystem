package com.yl.payinterface.core.model;

import java.util.Date;

import com.yl.payinterface.core.enums.AuthPayBindCardInfoStatus;

/**
 * 
 * @ClassName BindCardInfo
 * @Description 同名进出认证支付绑卡信息记录表
 * @author 聚合支付
 * @date 2017年9月1日 下午9:07:49
 */
public class BindCardInfo extends AutoStringIDModel {

	private static final long serialVersionUID = -6174769753948598932L;

	/** 支付接口编号 */
	private String interfaceInfoCode;
	/** 绑卡支付信息 */
	private String token;
	/** 银行卡号 */
	private String cardNo;
	/** 备注字段 */
	private String remark;
	/** 有效日期 */
	private Date effectiveDate;
	/** 失效日期 */
	private Date expiryDate;
	/** cvv */
	private String cvv;
	/** 信誉卡有效期- 月 */
	private String validityMonth;
	/** 信誉卡有效期- 年 */
	private String validityYear;

	private AuthPayBindCardInfoStatus status;

	public String getInterfaceInfoCode() {
		return interfaceInfoCode;
	}

	public void setInterfaceInfoCode(String interfaceInfoCode) {
		this.interfaceInfoCode = interfaceInfoCode;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public AuthPayBindCardInfoStatus getStatus() {
		return status;
	}

	public void setStatus(AuthPayBindCardInfoStatus status) {
		this.status = status;
	}

	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	public String getCvv() {
		return cvv;
	}

	public void setCvv(String cvv) {
		this.cvv = cvv;
	}

	public String getValidityMonth() {
		return validityMonth;
	}

	public void setValidityMonth(String validityMonth) {
		this.validityMonth = validityMonth;
	}

	public String getValidityYear() {
		return validityYear;
	}

	public void setValidityYear(String validityYear) {
		this.validityYear = validityYear;
	}

	@Override
	public String toString() {
		return "BindCardInfo [interfaceInfoCode=" + interfaceInfoCode + ", token=" + token + ", cardNo=" + cardNo + ", remark=" + remark + ", effectiveDate=" + effectiveDate + ", expiryDate=" + expiryDate + ", cvv=" + cvv + ", validityMonth=" + validityMonth + ", validityYear=" + validityYear
				+ ", status=" + status + "]";
	}

}
