package com.yl.pay.pos.entity;

import com.yl.pay.pos.enums.Status;
import com.yl.pay.pos.enums.TransType;

import javax.persistence.*;


/**
 * Title: 银行通道交易权限配置
 * Description: 
 * Copyright: Copyright (c)2011
 * Company: com.yl.pay
 * @author haitao.liu
 */

@Entity
@Table(name = "BANK_CHANNEL_FUNCTION")
public class BankChannelFunction extends BaseEntity{

	private String sortCode;			//分类编码
	private TransType transType;		//交易类型
	private Status status;				//状态
	
	
	@Column(name = "SORT_CODE", length = 20)
	public String getSortCode() {
		return sortCode;
	}
	public void setSortCode(String sortCode) {
		this.sortCode = sortCode;
	}
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "TRANS_TYPE", columnDefinition = "VARCHAR(50)")
	public TransType getTransType() {
		return transType;
	}
	public void setTransType(TransType transType) {
		this.transType = transType;
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

