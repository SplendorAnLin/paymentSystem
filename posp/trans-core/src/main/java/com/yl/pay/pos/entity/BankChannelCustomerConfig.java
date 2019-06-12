package com.yl.pay.pos.entity;

import com.yl.pay.pos.enums.BankCustomerLevel;
import com.yl.pay.pos.enums.Status;

import javax.persistence.*;

/**
 * Title: 银行通道与银行商户信息配置
 * Description: 
 * Copyright: Copyright (c)2011
 * Company: com.yl.pay
 * @author haitao.liu
 */

@Entity
@Table(name = "BANK_CHANNEL_CUSTOMER_CONFIG")
public class BankChannelCustomerConfig extends BaseEntity{
	
	private String bankChannelCode;				//银行通道编号
	private Bank bank;							//所属银行
	private BankInterface bankInterface;		//所属银行接口
	private String  bankCustomerNo;				//银行商户号
	private BankCustomerLevel customerLevel;	//商户级别
	private String customerNo;					//商户编号
	private String supportOrganization;			//支持的地区
	private Status status;						//状态	

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

	@ManyToOne(fetch = FetchType.LAZY )
	@JoinColumn(name = "BANK_INTERFACE", columnDefinition = "VARCHAR(20)")
	public BankInterface getBankInterface() {
		return bankInterface;
	}
	public void setBankInterface(BankInterface bankInterface) {
		this.bankInterface = bankInterface;
	}

	@Column(name = "BANK_CUSTOMER_NO", length = 50)
	public String getBankCustomerNo() {
		return bankCustomerNo;
	}
	public void setBankCustomerNo(String bankCustomerNo) {
		this.bankCustomerNo = bankCustomerNo;
	}

	@Enumerated(value = EnumType.STRING)
	@Column(name = "CUSTOMER_LEVEL", columnDefinition = "VARCHAR(30)")
	public BankCustomerLevel getCustomerLevel() {
		return customerLevel;
	}
	public void setCustomerLevel(BankCustomerLevel customerLevel) {
		this.customerLevel = customerLevel;
	}

	@Column(name = "CUSTOMER_NO", length = 30)
	public String getCustomerNo() {
		return customerNo;
	}
	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}

	@Column(name = "SUPPORT_ORGANIZATION", length = 1024)
	public String getSupportOrganization() {
		return supportOrganization;
	}
	public void setSupportOrganization(String supportOrganization) {
		this.supportOrganization = supportOrganization;
	}
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "STATUS", columnDefinition = "VARCHAR(20)")	
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
}
