package com.yl.boss.api.bean;

import java.util.Date;
import com.yl.boss.api.enums.CustomerBeanType;
import com.yl.boss.api.enums.CustomerSource;
import com.yl.boss.api.enums.CustomerStatus;
import com.yl.boss.api.enums.Status;

/**
 * 商户信息Bean
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月16日
 * @version V1.0.0
 */
public class Customer extends BaseBean{

	private static final long serialVersionUID = 6102093781175047240L;
	private String agentNo;					//服务商编号
	private String customerNo;				//商户编号	
	private String fullName;				//商户全称
	private String shortName;				//商户简称
	private String phoneNo;					//业务联系手机号
	private CustomerStatus status;			//商户状态 枚举类型是‘CustomerStatus’
	private String linkMan;					//联系人
	private CustomerBeanType customerType;	//商户类型(企业|个体户)
	private String addr;					//通讯地址(对公填注册地址)
	private double cautionMoney;			//保证金
	private Date createTime;
	private String province;				//所在省
	private String city;					//所在市
	private String mcc;	 					//MCC
	private Date openTime ;              	//开通时间 
	private String useCustomPermission;		//使用定制权限
	private String organization;			//所在地区编码
	private String idCard;					//身份证
	private String eMail;					//邮箱
	private CustomerSource source;			//来源
	
	public String getAgentNo() {
		return agentNo;
	}
	public void setAgentNo(String agentNo) {
		this.agentNo = agentNo;
	}
	public String getCustomerNo() {
		return customerNo;
	}
	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	
	public CustomerStatus getStatus() {
		return status;
	}
	public void setStatus(CustomerStatus status) {
		this.status = status;
	}
	public String getLinkMan() {
		return linkMan;
	}
	public void setLinkMan(String linkMan) {
		this.linkMan = linkMan;
	}
	public CustomerBeanType getCustomerType() {
		return customerType;
	}
	public void setCustomerType(CustomerBeanType customerType) {
		this.customerType = customerType;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public double getCautionMoney() {
		return cautionMoney;
	}
	public void setCautionMoney(double cautionMoney) {
		this.cautionMoney = cautionMoney;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getMcc() {
		return mcc;
	}
	public void setMcc(String mcc) {
		this.mcc = mcc;
	}
	public Date getOpenTime() {
		return openTime;
	}
	public void setOpenTime(Date openTime) {
		this.openTime = openTime;
	}
	public String getUseCustomPermission() {
		return useCustomPermission;
	}
	public void setUseCustomPermission(String useCustomPermission) {
		this.useCustomPermission = useCustomPermission;
	}
	public String getOrganization() {
		return organization;
	}
	public void setOrganization(String organization) {
		this.organization = organization;
	}
	public String getIdCard() {
		return idCard;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	public String getEMail() {
		return eMail;
	}
	public void setEMail(String eMail) {
		this.eMail = eMail;
	}
	public CustomerSource getSource() {
		return source;
	}
	public void setSource(CustomerSource source) {
		this.source = source;
	}
}