package com.yl.customer.entity;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.yl.boss.api.enums.CustomerStatus;

/**
 * 商户Bean
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月15日
 * @version V1.0.0
 */
@Entity
@Table(name = "CUSTOMER")
public class Customer extends AutoIDEntity{
	
	private static final long serialVersionUID = -3374131465600669380L;
	private String customerNo;				//商户编号	
	private String name;					//商户名称
	private String linkMan;					//联系人
	private String phone;					//联系电话
	private String addr;					//联系地址
	private Date createTime;				//创建时间
	private String operator;				//操作人
	private CustomerStatus status;			//商户状态
	private String manualAudit = "FALSE"; 	//true：自动审核
	private String complexPassword; 		//手动审核必输
	private String usePhoneCheck = "FALSE";	//短信验证
	
	@Column(name = "CUSTOMER_NO", length = 30)
	public String getCustomerNo() {
		return customerNo;
	}
	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}

	@Enumerated(value = EnumType.STRING)
	@Column(name = "STATUS", columnDefinition = "VARCHAR(30)")
	public CustomerStatus getStatus() {
		return status;
	}
	public void setStatus(CustomerStatus status) {
		this.status = status;
	}
	
	@Column(name = "NAME")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name = "LINK_MAN")
	public String getLinkMan() {
		return linkMan;
	}
	public void setLinkMan(String linkMan) {
		this.linkMan = linkMan;
	}
	
	@Column(name = "PHONE")
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@Column(name = "ADDR")
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	
	@Column(name = "CREATE_TIME", columnDefinition = "DATETIME")
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@Column(name = "OPERATOR")
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	
	@Column(name = "MANUAL_AUDIT")
	public String getManualAudit() {
		return manualAudit;
	}
	public void setManualAudit(String manualAudit) {
		this.manualAudit = manualAudit;
	}
	
	@Column(name = "COMPLEX_PASSWORD")
	public String getComplexPassword() {
		return complexPassword;
	}
	public void setComplexPassword(String complexPassword) {
		this.complexPassword = complexPassword;
	}
	
	@Column(name = "USE_PHONE_CHECK")
	public String getUsePhoneCheck() {
		return usePhoneCheck;
	}
	public void setUsePhoneCheck(String usePhoneCheck) {
		this.usePhoneCheck = usePhoneCheck;
	}
	
}
