package com.yl.boss.api.bean;

import java.util.Date;

import com.yl.boss.api.enums.AgentStatus;
import com.yl.boss.api.enums.AgentType;

/**
 * 服务商信息Bean
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月11日
 * @version V1.0.0
 */
public class Agent extends BaseBean{

	private static final long serialVersionUID = 5148628227069167894L;
	private String agentNo;					//服务商编号	
	private String fullName;				//服务商全称
	private String shortName;				//服务商简称
	private String phoneNo;					//业务联系手机号
	private AgentStatus status;				//状态
	private String linkMan;					//联系人
	private AgentType agentType;			//服务商类型(企业|个体户)
	private String addr;					//通讯地址(对公填注册地址)
	private double cautionMoney;			//保证金
	private Date createTime;
	private String parenId;					//所属父级服务商
	private int agentLevel;					//服务商等级
	private String province;				//所在省
	private String city;					//所在市
	private Date openTime ;              	//开通时间
	private String organization;			//所在地区编码
	private String idCard;					//身份证
	private String eMail;			//企业信用码
	
	public String getAgentNo() {
		return agentNo;
	}
	public void setAgentNo(String agentNo) {
		this.agentNo = agentNo;
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
	public AgentStatus getStatus() {
		return status;
	}
	public void setStatus(AgentStatus status) {
		this.status = status;
	}
	public String getLinkMan() {
		return linkMan;
	}
	public void setLinkMan(String linkMan) {
		this.linkMan = linkMan;
	}
	public AgentType getAgentType() {
		return agentType;
	}
	public void setAgentType(AgentType agentType) {
		this.agentType = agentType;
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
	public String getParenId() {
		return parenId;
	}
	public void setParenId(String parenId) {
		this.parenId = parenId;
	}
	public int getAgentLevel() {
		return agentLevel;
	}
	public void setAgentLevel(int agentLevel) {
		this.agentLevel = agentLevel;
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
	public Date getOpenTime() {
		return openTime;
	}
	public void setOpenTime(Date openTime) {
		this.openTime = openTime;
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
	
	
}
