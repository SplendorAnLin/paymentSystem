package com.yl.pay.pos.entity;

import com.yl.pay.pos.enums.Status;

import javax.persistence.*;
import java.util.Date;


/**
 * Title: 银行通道与银行商户信息配置
 * Description: 
 * Copyright: Copyright (c)2011
 * Company: com.yl.pay
 * @author haitao.liu
 */

@Entity
@Table(name = "BANK_CUSTOMER_CONFIG")
public class BankCustomerConfig extends BaseEntity{
	
	private Bank bank;							//所属银行
	private String bankInterface;				//所属银行接口	
	private String bankChannelCode;				//银行通道编号
	private String  bankCustomerNo;				//银行商户号
	private String customerNo;					//商户编号
	private Date effectTime;					//生效时间
	private Date createTime;					//创建时间
	private Status status;						//状态	
	private String posCati;						//终端号

	@Column(name = "BANK_CHANNEL_CODE", length = 20)
	public String getBankChannelCode() {
		return bankChannelCode;
	}
	public void setBankChannelCode(String bankChannelCode) {
		this.bankChannelCode = bankChannelCode;
	}

	@ManyToOne(fetch = FetchType.LAZY )
	@JoinColumn(name = "BANK", columnDefinition = "VARCHAR(30)")
	public Bank getBank() {
		return bank;
	}
	public void setBank(Bank bank) {
		this.bank = bank;
	}

	@Column(name = "BANK_CUSTOMER_NO", length = 50)
	public String getBankCustomerNo() {
		return bankCustomerNo;
	}
	public void setBankCustomerNo(String bankCustomerNo) {
		this.bankCustomerNo = bankCustomerNo;
	}

	@Column(name = "CUSTOMER_NO", length = 30)
	public String getCustomerNo() {
		return customerNo;
	}
	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}

	@Enumerated(value = EnumType.STRING)
	@Column(name = "STATUS", columnDefinition = "VARCHAR(20)")	
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	
	@Column(name = "BANK_INTERFACE", length = 30)
	public String getBankInterface() {
		return bankInterface;
	}
	public void setBankInterface(String bankInterface) {
		this.bankInterface = bankInterface;
	}
	
	@Column(name = "EFFECT_TIME",columnDefinition = "DATETIME")
	public Date getEffectTime() {
		return effectTime;
	}
	public void setEffectTime(Date effectTime) {
		this.effectTime = effectTime;
	}
	
	@Column(name = "CREATE_TIME",columnDefinition = "DATETIME")
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@Column(name = "POS_CATI", length = 20)
	public String getPosCati() {
		return posCati;
	}
	public void setPosCati(String posCati) {
		this.posCati = posCati;
	}
	
	
	
}
