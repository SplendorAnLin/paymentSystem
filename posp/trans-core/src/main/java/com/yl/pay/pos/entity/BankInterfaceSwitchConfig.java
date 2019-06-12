package com.yl.pay.pos.entity;

import com.yl.pay.pos.enums.Status;

import javax.persistence.*;


/**
 * Title: 银行接口切换配置
 * Description: 
 * Copyright: Copyright (c)2011
 * Company: com.yl.pay
 * @author jun
 */

@Entity
@Table(name = "BANK_INTERFACE_SWITCH_CONFIG")
public class BankInterfaceSwitchConfig extends BaseEntity {
	
	private String interfaceCode;				//银行接口编号
	private String respCode;					//银行返回码
	private Status status;						//状态
	
	@Column(name = "INTERFACE_CODE", length = 20)
	public String getInterfaceCode() {
		return interfaceCode;
	}
	public void setInterfaceCode(String interfaceCode) {
		this.interfaceCode = interfaceCode;
	}
	
	@Column(name = "RESP_CODE", length = 20)
	public String getRespCode() {
		return respCode;
	}
	public void setRespCode(String respCode) {
		this.respCode = respCode;
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

