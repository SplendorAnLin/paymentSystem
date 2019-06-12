package com.yl.agent.api.bean;

import java.util.Date;

import com.yl.agent.api.bean.BeseBean;
import com.yl.agent.api.enums.Status;

/**
 * 服务商操作员
 * 
 * @author 聚合支付有限公司
 * @since 2016年7月13日
 * @version V1.0.0
 */
public class AgentOperator extends BeseBean{

	private static final long serialVersionUID = -6655330752271757661L;
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
	private String organization;
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
    //private Set<Role> roles; 
    /**
     * 商户编号
     */
    private String AgentNo;
    
    /**
     * 公开密码
     */
    private String publicPassword;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getCreateOperator() {
		return createOperator;
	}

	public void setCreateOperator(String createOperator) {
		this.createOperator = createOperator;
	}

	public String getReloginFlag() {
		return reloginFlag;
	}

	public void setReloginFlag(String reloginFlag) {
		this.reloginFlag = reloginFlag;
	}

	public String getAllowBeginTime() {
		return allowBeginTime;
	}

	public void setAllowBeginTime(String allowBeginTime) {
		this.allowBeginTime = allowBeginTime;
	}

	public String getAllowEndTime() {
		return allowEndTime;
	}

	public void setAllowEndTime(String allowEndTime) {
		this.allowEndTime = allowEndTime;
	}

	public Date getPasswdModifyTime() {
		return passwdModifyTime;
	}

	public void setPasswdModifyTime(Date passwdModifyTime) {
		this.passwdModifyTime = passwdModifyTime;
	}

	public Date getPasswdExpireTime() {
		return passwdExpireTime;
	}

	public void setPasswdExpireTime(Date passwdExpireTime) {
		this.passwdExpireTime = passwdExpireTime;
	}

	public Date getPasswdEffectTime() {
		return passwdEffectTime;
	}

	public void setPasswdEffectTime(Date passwdEffectTime) {
		this.passwdEffectTime = passwdEffectTime;
	}

	public Integer getModifyPasswdCycle() {
		return modifyPasswdCycle;
	}

	public void setModifyPasswdCycle(Integer modifyPasswdCycle) {
		this.modifyPasswdCycle = modifyPasswdCycle;
	}

	public Integer getTryTimes() {
		return tryTimes;
	}

	public void setTryTimes(Integer tryTimes) {
		this.tryTimes = tryTimes;
	}

	public Integer getMaxErrorTimes() {
		return maxErrorTimes;
	}

	public void setMaxErrorTimes(Integer maxErrorTimes) {
		this.maxErrorTimes = maxErrorTimes;
	}

	public Date getLastLoginErrTime() {
		return lastLoginErrTime;
	}

	public void setLastLoginErrTime(Date lastLoginErrTime) {
		this.lastLoginErrTime = lastLoginErrTime;
	}
//
//	public Set<Role> getRoles() {
//		return roles;
//	}
//
//	public void setRoles(Set<Role> roles) {
//		this.roles = roles;
//	}

	public String getPublicPassword() {
		return publicPassword;
	}

	public void setPublicPassword(String publicPassword) {
		this.publicPassword = publicPassword;
	}

	public String getAgentNo() {
		return AgentNo;
	}

	public void setAgentNo(String agentNo) {
		AgentNo = agentNo;
	}
    
}
