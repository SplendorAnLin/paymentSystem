package com.yl.pay.pos.entity;

import com.yl.pay.pos.enums.BankPosRunStatus;
import com.yl.pay.pos.enums.BankPosUseStatus;
import com.yl.pay.pos.enums.Status;

import javax.persistence.*;
import java.util.Date;


/**
 * Title: 银行接口商户及终端信息
 * Description: 
 * Copyright: Copyright (c)2011
 * Company: com.yl.pay
 * @author haitao.liu
 */

@Entity
@Table(name = "BANK_INTERFACE_TERMINAL")
public class BankInterfaceTerminal extends BaseEntity{
	
	private BankInterface bankInterface;			//银行接口
	private String bankCustomerNo;					//银行商户号
	private String bankCustomerName;				//银行商户名
	private String bankBatch;						//银行批次号
	private String mcc;								//MCC码
	private String bankPosCati;						//银行终端号
	private Date lockTime;							//终端锁定时间
	private Date lastUseTime;						//最后一次使用时间
	private BankPosRunStatus bankPosRunStatus;		//银行终端运行状态
	private BankPosUseStatus bankPosUseStatus;		//银行终端使用状态
	private String pSecretData;						//密码安全数据
	private String mSecretData;						//签名安全数据
	private String encryptTerminal;					//加密终端号
	private Status status;							//状态
	private String encryptCustomerNo;				//加密商户号

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

	@Column(name = "MCC", length = 10)
	public String getMcc() {
		return mcc;
	}
	public void setMcc(String mcc) {
		this.mcc = mcc;
	}

	@Column(name = "BANK_POS_CATI", length = 30)
	public String getBankPosCati() {
		return bankPosCati;
	}
	public void setBankPosCati(String bankPosCati) {
		this.bankPosCati = bankPosCati;
	}

	@Column(name = "LOCK_TIME",columnDefinition = "DATETIME")
	public Date getLockTime() {
		return lockTime;
	}
	public void setLockTime(Date lockTime) {
		this.lockTime = lockTime;
	}
	@Column(name = "LAST_USE_TIME",columnDefinition = "DATETIME")
	public Date getLastUseTime() {
		return lastUseTime;
	}
	public void setLastUseTime(Date lastUseTime) {
		this.lastUseTime = lastUseTime;
	}

	@Enumerated(value = EnumType.STRING)
	@Column(name = "BANK_POS_RUN_STATUS", columnDefinition = "VARCHAR(20)")
	public BankPosRunStatus getBankPosRunStatus() {
		return bankPosRunStatus;
	}
	public void setBankPosRunStatus(BankPosRunStatus bankPosRunStatus) {
		this.bankPosRunStatus = bankPosRunStatus;
	}

	@Enumerated(value = EnumType.STRING)
	@Column(name = "BANK_POS_USE_STATUS", columnDefinition = "VARCHAR(20)")
	public BankPosUseStatus getBankPosUseStatus() {
		return bankPosUseStatus;
	}
	public void setBankPosUseStatus(BankPosUseStatus bankPosUseStatus) {
		this.bankPosUseStatus = bankPosUseStatus;
	}

	@Column(name = "P_SECRET_DATA", length = 50)
	public String getpSecretData() {
		return pSecretData;
	}
	public void setpSecretData(String pSecretData) {
		this.pSecretData = pSecretData;
	}

	@Column(name = "M_SECRET_DATA", length = 50)
	public String getmSecretData() {
		return mSecretData;
	}
	public void setmSecretData(String mSecretData) {
		this.mSecretData = mSecretData;
	}

	@Enumerated(value = EnumType.STRING)
	@Column(name = "STATUS", columnDefinition = "VARCHAR(20)")
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	
	@Column(name = "BANK_BATCH", length = 20)
	public String getBankBatch() {
		return bankBatch;
	}
	public void setBankBatch(String bankBatch) {
		this.bankBatch = bankBatch;
	}
	
	@Column(name = "BANK_CUSTOMER_NAME", length = 100)
	public String getBankCustomerName() {
		return bankCustomerName;
	}
	public void setBankCustomerName(String bankCustomerName) {
		this.bankCustomerName = bankCustomerName;
	}

	@Column(name = "ENCRYPT_TERMINAL", length = 30)
	public String getEncryptTerminal() {
		return encryptTerminal;
	}
	public void setEncryptTerminal(String encryptTerminal) {
		this.encryptTerminal = encryptTerminal;
	}
	@Column(name = "ENCRYPT_CUSTOMER_NO", length = 30)
	public String getEncryptCustomerNo() {
		return encryptCustomerNo;
	}
	public void setEncryptCustomerNo(String encryptCustomerNo) {
		this.encryptCustomerNo = encryptCustomerNo;
	}
	//复制属性
	public void copy(BankInterfaceTerminal bankInterfaceTerminal){
		this.setBankInterface(bankInterfaceTerminal.getBankInterface());
		this.setBankCustomerNo(bankInterfaceTerminal.getBankCustomerNo());
		this.setBankPosCati(bankInterfaceTerminal.getBankPosCati());
		this.setEncryptTerminal(bankInterfaceTerminal.getEncryptTerminal());
		this.setMcc(bankInterfaceTerminal.getMcc());
	}

	
}
