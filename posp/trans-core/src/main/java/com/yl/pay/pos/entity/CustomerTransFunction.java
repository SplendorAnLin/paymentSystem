package com.yl.pay.pos.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Title: 商户交易权限
 * Description:   
 * Copyright: 2015年7月6日上午10:09:54
 * Company: SDJ
 * @author zhongxiang.ren
 */
@Entity
@Table(name = "CUSTOMER_TRANS_FUNCTION")
public class CustomerTransFunction extends BaseEntity {

	private String customerNo;		
	private String sortCode;		
	private String status;

	@Column(name = "SORT_CODE", length = 50)
	public String getSortCode() {
		return sortCode;
	}
	
	public void setSortCode(String sortCode) {
		this.sortCode = sortCode;
	}
	@Column(name = "CUSTOMER_NO", length = 50)
	public String getCustomerNo() {
		return customerNo;
	}
	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}
	@Column(name = "STATUS", length = 20)
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}		
}
