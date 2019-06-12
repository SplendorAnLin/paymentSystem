package com.yl.pay.pos.entity;

import com.yl.pay.pos.enums.Status;

import javax.persistence.*;

/**
 * 上海银联32域上送按银行商户编号配置
 * @author haitao.liu
 */
@Entity
@Table(name = "UNION_PAY_BCN_32_CONFIG")
public class UnionPayBCN32Config extends BaseEntity {

	private static final long serialVersionUID = 1232427668166267147L;
	/**
	 * 银行商户编号
	 */
	private String bankCustomerNo;
	/**
	 * 状态
	 */
	private Status status;
	/**
	 * 要上送的乐富地区码
	 */
	private String lefuOrgCode;
	/**
	 * 要上送的商户名称
	 */
	private String customerName;

	@Column(name = "BANK_CUSTOMER_NO", length = 30, unique = true)
	public String getBankCustomerNo() {
		return bankCustomerNo;
	}

	public void setBankCustomerNo(String bankCustomerNo) {
		this.bankCustomerNo = bankCustomerNo;
	}

	@Enumerated(EnumType.STRING)
	@Column(name = "status", length = 10)
	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	@Column(name = "LEFU_ORG_CODE", length = 10)
	public String getLefuOrgCode() {
		return lefuOrgCode;
	}

	public void setLefuOrgCode(String lefuOrgCode) {
		this.lefuOrgCode = lefuOrgCode;
	}

	@Column(name = "CUSTOMER_NAME", length = 100)
	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

}
