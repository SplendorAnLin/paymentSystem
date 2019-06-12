package com.yl.boss.entity;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.yl.boss.enums.CustomerType;

/**
 * 商户结算卡信息Bean
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月11日
 * @version V1.0.0
 */
@Entity
@Table(name = "CUSTOMER_SETTLE")
public class CustomerSettle extends AutoIDEntity{
	
	private static final long serialVersionUID = -3374131465600669380L;
	private String customerNo;				//商户编号
	private String accountName;				//账户名称
	private String accountNo;				//账户编号
	private CustomerType customerType;		//商户类型 对公|对私
	private String bankCode;				//所属银行编号
	private String openBankName;			//开户行名称
	private Date createTime;
	
	@Column(name = "CUSTOMER_NO", length = 30)
	public String getCustomerNo() {
		return customerNo;
	}
	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}
	
	@Column(name = "CREATE_TIME", columnDefinition = "DATE")
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@Column(name = "ACCOUNT_NAME", length = 500)
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	
	@Column(name = "ACCOUNT_NO", length = 200)
	public String getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "CUSTOMER_TYPE", columnDefinition = "VARCHAR(30)")
	public CustomerType getCustomerType() {
		return customerType;
	}
	public void setCustomerType(CustomerType customerType) {
		this.customerType = customerType;
	}
	
	@Column(name = "BANK_CODE", length = 30)
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	
	@Column(name = "OPEN_BANK_NAME", length = 500)
	public String getOpenBankName() {
		return openBankName;
	}
	public void setOpenBankName(String openBankName) {
		this.openBankName = openBankName;
	}
	
}
