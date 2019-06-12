package com.yl.agent.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.yl.agent.enums.LoginStatus;

/**
 * 登录日志
 * 
 * @author 聚合支付有限公司
 * @since 2016年7月8日
 * @version V1.0.0
 */
@Entity
@Table(name = "LOGIN_LOG")
public class LoginLog extends AutoIDEntity{
	
	private static final long serialVersionUID = 8488380207323697602L;
	/**
	 * sessionId
	 */
	private String sessionId;
	/**
	 * 登陆名称
	 */
	private String username;
	/**
	 * 登陆时间
	 */
	private Date loginTime;
    /**
     * 退出时间
     */
	private Date logoutTime;
	/**
	 * IP
	 */
	private String loginIp;
	/**
	 * 备注
	 */
	private String remarks;
	/**
	 * 登录状态
	 */
	private LoginStatus loginStatus;
	
	public LoginLog() {
		super();		
	}

	public LoginLog(String username, Date loginTime, String loginIp, LoginStatus loginStatus,String sessionId) {
		super();
		this.username = username;
		this.loginTime = loginTime;		
		this.loginIp = loginIp;
		this.loginStatus = loginStatus;
		this.sessionId = sessionId;
	}
	
	public LoginLog(String username, Date loginTime, Date logoutTime,
			String loginIp, String remarks, LoginStatus loginStatus,String sessionId) {
		super();
		this.username = username;
		this.loginTime = loginTime;
		this.logoutTime = logoutTime;
		this.loginIp = loginIp;
		this.remarks = remarks;
		this.loginStatus = loginStatus;
		this.sessionId = sessionId;
		
	}

	@Column(name="SESSION_ID", length = 50)
	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	@Column(name = "USERNAME", length = 50)
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(name = "LOGIN_TIME")
	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}
	
	@Column(name = "LOGOUT_TIME")
	public Date getLogoutTime() {
		return logoutTime;
	}

	public void setLogoutTime(Date logoutTime) {
		this.logoutTime = logoutTime;
	}
   
	@Column(name = "LOGIN_IP", length = 50)
	public String getLoginIp() {
		return loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	@Column(name = "REMARKS", length = 50)
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}


	@Enumerated(value = EnumType.STRING)
	@Column(name = "LOGIN_STATUS", columnDefinition = "VARCHAR(30)")
	public LoginStatus getLoginStatus() {
		return loginStatus;
	}

	public void setLoginStatus(LoginStatus loginStatus) {
		this.loginStatus = loginStatus;
	}
}
