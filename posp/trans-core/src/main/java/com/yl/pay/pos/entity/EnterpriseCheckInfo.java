/**
 * 
 */
package com.yl.pay.pos.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * Title: 商户企业信息验证
 * Description: 
 * Copyright: Copyright (c)2014 
 * Company: com.yl.pay
 * 
 * @author zhendong.wu
 */
@Entity
@Table(name = "ENTERPRISE_CHECK_INFO")
public class EnterpriseCheckInfo extends BaseEntity{
	
	private static final long serialVersionUID = 1L;
	//状态：Y成功，N未查询到，Y_信息不符
	private String status;			
	private String agentNo;					//代理商编码
	private Date addTime;					//添加时间
	private String customerNo;				//商户编码
	
	
	
	@Column(name = "STATUS", length = 10)
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	@Column(name = "AGENT_NO", length = 30)
	public String getAgentNo() {
		return agentNo;
	}
	public void setAgentNo(String agentNo) {
		this.agentNo = agentNo;
	}
	
	
	@Column(name = "ADD_TIME", columnDefinition = "DATETIME")
	public Date getAddTime() {
		return addTime;
	}
	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}
	
	@Column(name = "CUSTOMER_NO", length = 30)
	public String getCustomerNo() {
		return customerNo;
	}
	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}
}
