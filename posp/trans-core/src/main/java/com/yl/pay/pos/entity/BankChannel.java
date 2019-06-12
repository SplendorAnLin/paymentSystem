package com.yl.pay.pos.entity;

import com.yl.pay.pos.enums.BankBillType;
import com.yl.pay.pos.enums.BankConnectType;
import com.yl.pay.pos.enums.Status;
import com.yl.pay.pos.enums.YesNo;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


/**
 * Title: 银行通道
 * Copyright: Copyright (c)2011
 * Company: lepay
 * @author haitao.liu
 */

@Entity
@Table(name = "BANK_CHANNEL_NEW")
public class BankChannel implements Serializable{

	private static final long serialVersionUID = -4691545972284960186L;
	
	private String code;							//编号
	private Integer optimistic;						//版本标识
	private Long id;								//
	private String name;							//名称
	private Bank bank;								//收单行
	private BankInterface  bankInterface;			//所属银行接口
	private String supportCardType;					//支持的卡类型
	private BankConnectType bankConnectType;		//银行连接类型
	private BankBillType bankBillType;				//银行账单类型
	private String supportIssuer;					//支持的发卡行
	private String mccCategory;						//所属MCC分类
	private String transFunction;					//交易权限分类码
	private Status status;							//状态
	private Date createTime;						//创建时间
	private String description;						//描述
	private YesNo isSupportIc;					//是否支持IC卡
	
	
	@Column(columnDefinition = "int", length = 11)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Id
	@Column(name = "CODE", length = 30)
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	@Version
	@Column(columnDefinition = "int", length = 11)
	public Integer getOptimistic() {
		return optimistic;
	}
	public void setOptimistic(Integer optimistic) {
		this.optimistic = optimistic;
	}
	
	@Column(name = "NAME", length = 100)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@ManyToOne(fetch = FetchType.EAGER )
	@JoinColumn(name = "BANK", columnDefinition = "VARCHAR(30)")
	public Bank getBank() {
		return bank;
	}
	public void setBank(Bank bank) {
		this.bank = bank;
	}
	
	@Column(name = "SUPPORT_CARD_TYPE", length = 80)
	public String getSupportCardType() {
		return supportCardType;
	}
	public void setSupportCardType(String supportCardType) {
		this.supportCardType = supportCardType;
	}
	
	@Column(name = "TRANS_FUNCTION", length = 20)
	public String getTransFunction() {
		return transFunction;
	}
	public void setTransFunction(String transFunction) {
		this.transFunction = transFunction;
	}
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "STATUS", columnDefinition = "VARCHAR(20)")
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	
	@Column(name = "DESCRIPTION", length = 200)
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	@ManyToOne(fetch = FetchType.EAGER )
	@JoinColumn(name = "BANK_INTERFACE", columnDefinition = "VARCHAR(30)")
	public BankInterface getBankInterface() {
		return bankInterface;
	}
	public void setBankInterface(BankInterface bankInterface) {
		this.bankInterface = bankInterface;
	}
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "BANK_CONNECT_TYPE", columnDefinition = "VARCHAR(30)")	
	public BankConnectType getBankConnectType() {
		return bankConnectType;
	}
	public void setBankConnectType(BankConnectType bankConnectType) {
		this.bankConnectType = bankConnectType;
	}
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "BANK_BILL_TYPE", columnDefinition = "VARCHAR(30)")
	public BankBillType getBankBillType() {
		return bankBillType;
	}
	public void setBankBillType(BankBillType bankBillType) {
		this.bankBillType = bankBillType;
	}
	
	@Column(name = "SUPPORT_ISSUER", length = 128)
	public String getSupportIssuer() {
		return supportIssuer;
	}
	public void setSupportIssuer(String supportIssuer) {
		this.supportIssuer = supportIssuer;
	}
	
	@Column(name = "MCC_CATEGORY", length = 30)
	public String getMccCategory() {
		return mccCategory;
	}
	public void setMccCategory(String mccCategory) {
		this.mccCategory = mccCategory;
	}

	@Column(name = "CREATE_TIME",columnDefinition = "DATETIME")
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	@Enumerated(value = EnumType.STRING)
	@Column(name = "IS_SUPPORT_IC", columnDefinition = "VARCHAR(30)")	
	public YesNo getIsSupportIc() {
		return isSupportIc;
	}
	public void setIsSupportIc(YesNo isSupportIc) {
		this.isSupportIc = isSupportIc;
	}
	
	
	
}
