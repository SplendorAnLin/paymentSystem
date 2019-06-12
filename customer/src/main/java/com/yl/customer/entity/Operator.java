package com.yl.customer.entity;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.pay.common.util.DateUtil;
import com.yl.customer.enums.OperatorType;
import com.yl.customer.enums.Status;
import com.yl.customer.enums.YesNo;

/**
 * 操作员Bean
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月15日
 * @version V1.0.0
 */
@Entity
@Table(name = "OPERATOR")
public class Operator extends AutoIDEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -410510552636158931L;
	/**
	 * 操作员的登录的名称
	 */
	private String username;
	/**
	 * 操作员的登录密码
	 */
	private String password;
	/**
	 * 操作员真实姓名
	 */
	private String realname;
	/**
	 * 操作员类型
	 */
	private Long roleId;
	/**
	 * 机构编码
	 */
	private Organization organization;
    /**
     * 操作员创建时间
     */
    private Date createTime;
	/**
	 *开通状态
	 */
    private Status status;
    /**
     * 创建人
     */
    private String createOperator;     
    /**
     * 重复登录标志
     */
    private String reloginFlag;
    /**
     * 登录许可起始时间(每天的登录时间段)
     */
    private String  allowBeginTime;
    /**
     * 登录许可截止时间(每天的登录时间段)
     */
    private String  allowEndTime;
    /**
     * 密码最后修改时间
     */
    private Date passwdModifyTime;
    /**
     * 密码到期时间
     */
    private Date passwdExpireTime;
    /**
     * 密码生效时间
     */
    private Date passwdEffectTime;
    /**
     * 密码修改间隔（单位：天）
     */
    private Integer modifyPasswdCycle;
    /**
     * 同时登录的数量
     */
    private Integer tryTimes;
    /**
     * 当前登录次数
     */
    private Integer maxErrorTimes;
    /**
     * 登录失败的时间
     */
    private Date lastLoginErrTime;
    /**
     * 角色
     */
    private Set<Role> roles; 
    /**
     * 商户编号
     */
    private String customerNo;
    
    private String publicPassword;
    
    private String complexPassword; 		//审核密码
    
    @Column(name = "public_password", length = 50)
	public String getPublicPassword() {
		return publicPassword;
	}

	public void setPublicPassword(String publicPassword) {

	}

	private YesNo isSend;					//短信是否发送
	
	private Date lastSendTime;				//最后发送短信时间

	@Enumerated(value = EnumType.STRING)
	@Column(name = "IS_SEND", columnDefinition = "VARCHAR(10)")
	public YesNo getIsSend() {
		return isSend;
	}
	public void setIsSend(YesNo isSend) {
		this.isSend = isSend;
	}
	@Column(name = "last_send_time", columnDefinition = "DATETIME")
	public Date getLastSendTime() {
		return lastSendTime;
	}
	public void setLastSendTime(Date lastSendTime) {
		this.lastSendTime = lastSendTime;
	}

	public Operator() {
		super();
	}	

	@Column(name = "USERNAME", length = 50)
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

	@Column(name = "REALNAME", length = 100)
	public String getRealname() {
		return realname;
	}
	public void setRealname(String realname) {
		this.realname = realname;
	}

	@Column(name = "PASSWORD", length = 50)
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Column(name = "ROLE_ID", columnDefinition = "bigint(30)")
	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
	
	@Column(name = "CREATE_TIME", columnDefinition = "DATETIME")
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "STATUS", columnDefinition = "VARCHAR(30)")
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}

	@Column(name = "CREATE_OPERATOR", length = 50)
	public String getCreateOperator() {
		return createOperator;
	}
	public void setCreateOperator(String createOperator) {
		this.createOperator = createOperator;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="ORG_CODE")
	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	@Column(name = "RELOGIN_FLAG", length = 1)	
	public String getReloginFlag() {
		return reloginFlag;
	}

	public void setReloginFlag(String reloginFlag) {
		this.reloginFlag = reloginFlag;
	}
	
	@Column(name = "ALLOW_BEGIN_TIME", length = 50)
	public String getAllowBeginTime() {
		return allowBeginTime;
	}

	public void setAllowBeginTime(String allowBeginTime) {
		this.allowBeginTime = allowBeginTime;
	}

	@Column(name = "ALLOW_END_TIME", length = 50)
	public String getAllowEndTime() {
		return allowEndTime;
	}

	public void setAllowEndTime(String allowEndTime) {
		this.allowEndTime = allowEndTime;
	}

	@Column(name = "PASSWD_MODIFY_TIME", columnDefinition = "DATETIME")	
	public Date getPasswdModifyTime() {
		return passwdModifyTime;
	}

	public void setPasswdModifyTime(Date passwdModifyTime) {
		this.passwdModifyTime = passwdModifyTime;
	}

	@Column(name = "PASSWD_EXPIRE_TIME", columnDefinition = "DATETIME")	
	public Date getPasswdExpireTime() {
		return passwdExpireTime;
	}

	public void setPasswdExpireTime(Date passwdExpireTime) {
		this.passwdExpireTime = passwdExpireTime;
	}

	@Column(name = "PASSWD_EFFECT_TIME", columnDefinition = "DATETIME")	
	public Date getPasswdEffectTime() {
		return passwdEffectTime;
	}

	public void setPasswdEffectTime(Date passwdEffectTime) {
		this.passwdEffectTime = passwdEffectTime;
	}

	@Column(name = "MODIFY_PASSWD_CYCLE",length = 4)	
	public Integer getModifyPasswdCycle() {
		return modifyPasswdCycle;
	}

	public void setModifyPasswdCycle(Integer modifyPasswdCycle) {
		this.modifyPasswdCycle = modifyPasswdCycle;
	}

	@Column(name = "TRY_TIMES",length = 4)
	public Integer getTryTimes() {
		return tryTimes;
	}

	public void setTryTimes(Integer tryTimes) {
		this.tryTimes = tryTimes;
	}

	@Column(name = "MAX_ERROR_TIMES",length = 4)
	public Integer getMaxErrorTimes() {
		return maxErrorTimes;
	}

	public void setMaxErrorTimes(Integer maxErrorTimes) {
		this.maxErrorTimes = maxErrorTimes;
	}
	
	@Column(name = "LAST_LOGIN_ERR_TIME", columnDefinition = "DATETIME")
	public Date getLastLoginErrTime() {
		return lastLoginErrTime;
	}

	public void setLastLoginErrTime(Date lastLoginErrTime) {
		this.lastLoginErrTime = lastLoginErrTime;
	}
	
	@ManyToMany(targetEntity=com.yl.customer.entity.Role.class,fetch=FetchType.EAGER,cascade={CascadeType.MERGE})
	@JoinTable(name="OPERATOR_ROLE",
			joinColumns=@JoinColumn(name="OPERATOR_ID"),
            inverseJoinColumns=@JoinColumn(name="ROLE_ID")
            
    )    	
	public Set<Role> getRoles() {
		return roles;
	}
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	@Column(name = "CUSTOMER_NO",length = 50)
	public String getCustomerNo() {
		return customerNo;
	}
	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}
	
	@Column(name = "COMPLEX_PASSWORD",length = 50)
	public String getComplexPassword() {
		return complexPassword;
	}

	public void setComplexPassword(String complexPassword) {
		this.complexPassword = complexPassword;
	}

	public String toString() {		
        StringBuffer buf = new StringBuffer();
        buf.append("Operator:");
        buf.append("[username=").append(username).append("]");
        buf.append("[realname=").append(realname).append("]");
        buf.append("[organization=").append(organization==null?"null":organization.getCode()).append("]");
        buf.append("[createTime=").append(DateUtil.formatDate(createTime)).append("]");
        buf.append("[status=").append(status).append("]");
        buf.append("[createOperator=").append(createOperator).append("]");
        buf.append("[allowBeginTime=").append(allowBeginTime).append("]");
        buf.append("[allowEndTime=").append(allowEndTime).append("]");
        buf.append("[passwdModifyTime=").append(DateUtil.formatDate(passwdModifyTime)).append("]");
        buf.append("[passwdExpireTime=").append(DateUtil.formatDate(passwdExpireTime)).append("]");
        buf.append("[passwdEffectTime=").append(DateUtil.formatDate(passwdEffectTime)).append("]");
        buf.append("[modifyPasswdCycle=").append(modifyPasswdCycle==null?"":modifyPasswdCycle.toString()).append("]");
        buf.append("[passwdExpireTime=").append(DateUtil.formatDate(passwdExpireTime)).append("]");
       
        return buf.toString();
    }    
}
