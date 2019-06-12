package com.yl.pay.pos.entity;

import com.yl.pay.pos.enums.Status;

import javax.persistence.*;
import java.util.Date;

/**
 * Title: 交易返回商户编号
 * Description: 用于打印在POS小票上 
 * Copyright: Copyright (c)2014
 * Company: com.yl.pay
 * @author jun
 */

@Entity
@Table(name = "SYS_RESP_CUSTOMERNO_CONFIG")
public class SysRespCustomerNoConfig extends BaseEntity{
	
	private String customerNo;			//商户编号
	private String bankCustomerNo;		//银行商户号，用于打印在POS小票上面
	
	private Status status;				//状态
	private Date createTime;			//创建时间
	
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
	
	@Column(name = "CREATE_TIME", columnDefinition = "DATETIME")
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@Column(name = "BANK_CUSTOMER_NO", length = 50)
	public String getBankCustomerNo() {
		return bankCustomerNo;
	}
	public void setBankCustomerNo(String bankCustomerNo) {
		this.bankCustomerNo = bankCustomerNo;
	}
	
}
