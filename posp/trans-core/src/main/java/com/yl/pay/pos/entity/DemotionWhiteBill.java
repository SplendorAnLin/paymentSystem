package com.yl.pay.pos.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Title:降级交易拦截白名单
 * Description: 
 * Copyright: Copyright (c)2014 
 * Company: com.yl.pay
 * @author zhongxiang.ren
 */
@Entity
@Table(name = "DEMOTION_WHITE_BILL")
public class DemotionWhiteBill extends BaseEntity{
	private String bankCode;        //银行
	private Integer panLength;		//卡号长度
	private Integer verifyLength;	//卡标识长度
	private String verifyCode;		//卡标识号取值
	
	@Column(name = "BANK_CODE", length = 30)
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	
	@Column(name = "PAN_LENGTH")
	public Integer getPanLength() {
		return panLength;
	}
	public void setPanLength(Integer panLength) {
		this.panLength = panLength;
	}
	
	@Column(name = "VERIFY_LENGTH")
	public Integer getVerifyLength() {
		return verifyLength;
	}
	public void setVerifyLength(Integer verifyLength) {
		this.verifyLength = verifyLength;
	}
	
	@Column(name = "VERIFY_CODE", length = 20)
	public String getVerifyCode() {
		return verifyCode;
	}
	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}
}
