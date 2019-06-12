package com.yl.boss.entity;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.yl.boss.api.enums.AgentStatus;
import com.yl.boss.api.enums.AgentType;

/**
 * 服务商信息Bean
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月11日
 * @version V1.0.0
 */
@Entity
@Table(name = "AGENT")
public class Agent extends AutoIDEntity{
	
	private static final long serialVersionUID = -3374131465600669380L;
	private String agentNo;					//服务商编号	
	private String fullName;				//服务商全称
	private String shortName;				//服务商简称
	private String phoneNo;					//业务联系手机号
	private AgentStatus status;				//状态
	private String linkMan;					//联系人
	private AgentType agentType;			//服务商类型(企业|个体户)
	private String addr;					//通讯地址(对公填注册地址)
	private Double cautionMoney;			//保证金
	private Date createTime;
	private String parenId;					//所属父级服务商
	private Integer agentLevel;					//服务商等级
	private String province;				//所在省
	private String city;					//所在市
	private Date openTime ;              	//开通时间
	private String organization;			//所在地区编码
	private String idCard;					//身份证
	private String eMail;			        //邮箱
	
	@Column(name = "FULL_NAME", length = 100, unique = true)
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	@Column(name = "PHONE_NO", length = 30)
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	
	@Column(name = "SHORT_NAME", length = 100, unique = true)
	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	
	@Column(name = "LINK_NAME", length = 30)
	public String getLinkMan() {
		return linkMan;
	}
	public void setLinkMan(String linkMan) {
		this.linkMan = linkMan;
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
	
	@Column(name = "CAUTION_MONEY", precision = 10, scale = 2)
	public Double getCautionMoney() {
		return cautionMoney;
	}
	public void setCautionMoney(Double cautionMoney) {
		this.cautionMoney = cautionMoney;
	}
	
	@Column(name = "AGENT_NO", length = 30, unique = true)
	public String getAgentNo() {
		return agentNo;
	}
	public void setAgentNo(String agentNo) {
		this.agentNo = agentNo;
	}
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "STATUS", columnDefinition = "VARCHAR(30)")
	public AgentStatus getStatus() {
		return status;
	}
	public void setStatus(AgentStatus status) {
		this.status = status;
	}
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "AGENT_TYPE", columnDefinition = "VARCHAR(30)")
	public AgentType getAgentType() {
		return agentType;
	}
	public void setAgentType(AgentType agentType) {
		this.agentType = agentType;
	}
	
	@Column(name = "PAREN_ID", length = 30)
	public String getParenId() {
		return parenId;
	}
	
	public void setParenId(String parenId) {
		this.parenId = parenId;
	}
	
	@Column(name = "AGENT_LEVEL", length = 11)
	public Integer getAgentLevel() {
		return agentLevel;
	}
	
	public void setAgentLevel(Integer agentLevel) {
		this.agentLevel = agentLevel;
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
	
	@Column(name = "OPEN_TIME", columnDefinition = "DATE")
	public Date getOpenTime() {
		return openTime;
	}
	public void setOpenTime(Date openTime) {
		this.openTime = openTime;
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
}
