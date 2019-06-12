package com.yl.pay.pos.entity;


import com.yl.pay.pos.enums.Status;

import javax.persistence.*;

/**
 * 商户级交易权限-配置表
 * Title: 
 * Description:   
 * Copyright: 2015年7月6日上午10:10:58
 * Company: SDJ
 * @author zhongxiang.ren
 */
@Entity
@Table(name = "CUSTOMER_TRANS_SORT")
public class CustomerTransSort extends BaseEntity {

	private String  sortCode;		//	规则码
	private String  transType;		//	交易类型
	private Status	status;		    //  状态
	private String  operator;       //  操作人
	
	@Column(name = "OPERATOR", length = 50)
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	
	@Column(name = "TRANS_TYPE", length = 30)
	public String getTransType() {
		return transType;
	}
	public void setTransType(String transType) {
		this.transType = transType;
	}
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "STATUS", length = 20)
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	@Column(name = "SORT_CODE", length = 30)
	public String getSortCode() {
		return sortCode;
	}
	public void setSortCode(String sortCode) {
		this.sortCode = sortCode;
	}

	
}
