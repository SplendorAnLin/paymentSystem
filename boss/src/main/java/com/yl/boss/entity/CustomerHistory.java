package com.yl.boss.entity;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.yl.boss.enums.CustomerStatus;
import com.yl.boss.enums.CustomerType;
import com.yl.boss.enums.YesNo;

/**
 * 商户信息历史Bean
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月11日
 * @version V1.0.0
 */
@Entity
@Table(name = "CUSTOMER_HISTORY")
public class CustomerHistory extends AutoIDEntity{
	
	private static final long serialVersionUID = -3374131465600669380L;
	private String agentNo;					//服务商编号
	private String customerNo;				//商户编号	
	private String fullName;				//商户全称
	private String shortName;				//商户简称
	private String phoneNo;					//业务联系手机号
	private CustomerStatus status;			//商户状态
	private String linkMan;					//联系人
	private CustomerType customerType;		//商户类型(企业|个体户)
	private String addr;					//通讯地址(对公填注册地址)
	private double cautionMoney;			//保证金
	private Date createTime;
	private String province;				//所在省
	private String city;					//所在市
	private String oper;
	private String mcc;	 					//MCC
	private Date openTime ;              	//开通时间 
	private YesNo useCustomPermission;		//使用定制权限
	private String organization;			//所在地区编码
	private String idCard;					//身份证
	private String eMail;					//邮箱
	private String msg;
	
	@Column(name = "FULL_NAME", length = 100)
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	@Column(name = "CUSTOMER_NO", length = 30)
	public String getCustomerNo() {
		return customerNo;
	}
	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}
	@Column(name = "PHONE_NO", length = 30)
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	
	@Column(name = "SHORT_NAME", length = 100)
	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "STATUS", columnDefinition = "VARCHAR(30)")
	public CustomerStatus getStatus() {
		return status;
	}
	public void setStatus(CustomerStatus status) {
		this.status = status;
	}
	
	@Column(name = "LINK_NAME", length = 30)
	public String getLinkMan() {
		return linkMan;
	}
	public void setLinkMan(String linkMan) {
		this.linkMan = linkMan;
	}
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "CUSTOMER_TYPE", columnDefinition = "VARCHAR(30)")
	public CustomerType getCustomerType() {
		return customerType;
	}
	public void setCustomerType(CustomerType customerType) {
		this.customerType = customerType;
	}
	
	@Column(name = "ADDR", length = 100)
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	
	@Column(name = "CREATE_TIME", columnDefinition = "DATE")
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@Column(name = "OPER", length = 50)
	public String getOper() {
		return oper;
	}
	public void setOper(String oper) {
		this.oper = oper;
	}
	
	@Column(name = "CAUTION_MONEY", precision = 10, scale = 2)
	public double getCautionMoney() {
		return cautionMoney;
	}
	public void setCautionMoney(double cautionMoney) {
		this.cautionMoney = cautionMoney;
	}
	
	@Column(name = "AGENT_NO", length = 30)
	public String getAgentNo() {
		return agentNo;
	}
	public void setAgentNo(String agentNo) {
		this.agentNo = agentNo;
	}
	
	@Column(name = "PROVINCE", length = 30)
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	
	@Column(name = "CITY", length = 30)
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	
	@Column(name = "MSG")
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	@Column(name = "MCC", length = 30)
	public String getMcc() {
		return mcc;
	}
	public void setMcc(String mcc) {
		this.mcc = mcc;
	}
	
	@Column(name = "OPEN_TIME", columnDefinition = "DATE")
	public Date getOpenTime() {
		return openTime;
	}
	public void setOpenTime(Date openTime) {
		this.openTime = openTime;
	}
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "USE_CUSTOM_PERMISSIAN", columnDefinition = "VARCHAR(30)")
	public YesNo getUseCustomPermission() {
		return useCustomPermission;
	}
	public void setUseCustomPermission(YesNo useCustomPermission) {
		this.useCustomPermission = useCustomPermission;
	}
	
	@Column(name = "ORGANIZATION", length = 30)
	public String getOrganization() {
		return organization;
	}
	public void setOrganization(String organization) {
		this.organization = organization;
	}
	@Column(name = "ID_CARD", length = 20)
    public String getIdCard() {
        return idCard;
    }
    @Column(name = "E_Mail", length = 30)
    public String getEMail() {
        return eMail;
    }
    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }
    public void setEMail(String eMail) {
        this.eMail = eMail;
    }
	
	public CustomerHistory(Customer customer, String oper) {
		super();
		this.agentNo = customer.getAgentNo();
		this.customerNo = customer.getCustomerNo();
		this.fullName = customer.getFullName();
		this.shortName = customer.getShortName();
		this.phoneNo = customer.getPhoneNo();
		this.status = customer.getStatus();
		this.linkMan = customer.getLinkMan();
		this.customerType = customer.getCustomerType();
		this.addr = customer.getAddr();
		this.cautionMoney = customer.getCautionMoney();
		this.createTime = new Date();
		this.province = customer.getProvince();
		this.city = customer.getCity();
		this.idCard=customer.getIdCard();
		this.eMail=customer.getEMail();
		this.oper = oper;
		this.mcc = customer.getMcc();
		this.openTime = customer.getOpenTime();
		this.useCustomPermission = customer.getUseCustomPermission();
		this.organization = customer.getOrganization();
	}
	
	public CustomerHistory() {
		super();
	}
	
	public CustomerHistory(Customer customer, String oper, String msg) {
		super();
		this.agentNo = customer.getAgentNo();
		this.customerNo = customer.getCustomerNo();
		this.fullName = customer.getFullName();
		this.shortName = customer.getShortName();
		this.phoneNo = customer.getPhoneNo();
		this.status = customer.getStatus();
		this.eMail=customer.getEMail();
		this.linkMan = customer.getLinkMan();
		this.customerType = customer.getCustomerType();
		this.addr = customer.getAddr();
		this.cautionMoney = customer.getCautionMoney();
		this.idCard=customer.getIdCard();
		this.createTime = new Date();
		this.province = customer.getProvince();
		this.city = customer.getCity();
		this.oper = oper;
		this.msg = msg;
		this.mcc = customer.getMcc();
		this.openTime = customer.getOpenTime();
		this.useCustomPermission = customer.getUseCustomPermission();
		this.organization = customer.getOrganization();
	}
}