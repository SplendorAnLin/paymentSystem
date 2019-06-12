package com.yl.agent.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import com.yl.agent.api.enums.OperatorType;

/**
 * 用户认证信息
 * 
 * @author 聚合支付有限公司
 * @since 2016年7月11日
 * @version V1.0.0
 */
public class Authorization implements Serializable{

	private static final long serialVersionUID = 1259662288879677355L;

	private String agentNo;
	
	private String username;
	
	private String password;
	
	private String realname;
	
	private Long loginLogId;
	
	private String sessionId;
	
	private Long roleId;
	
	private String ipAddress;
	
	private Date loginTime;
	
	private Set roles;
	
	private Set resources;
	
	private Menu menu;
	
	private boolean isRepeat;
	
	private String showFlag;
	
	private String infoValue;
	
	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	public Authorization(){
	}
	
	public Authorization(String customerno,String username, String password, String realname,
			Long roleId,String ipAddress,Long loginLogId,Date loginTime, Set roles){
		this.agentNo = customerno;
		this.username = username;		
		this.password = password;		
		this.realname = realname;		
		this.roleId = roleId;		
		this.ipAddress = ipAddress;		
		this.loginTime = loginTime;
		this.roles = roles;
		this.loginLogId = loginLogId;		
	}
	
	public String getAgentNo() {
		return agentNo;
	}

	public void setAgentNo(String agentNo) {
		this.agentNo = agentNo;
	}

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

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	public Long getLoginLogId() {
		return loginLogId;
	}

	public void setLoginLogId(Long loginLogId) {
		this.loginLogId = loginLogId;
	}

	public Set getRoles() {
		return roles;
	}

	public void setRoles(Set roles) {
		this.roles = roles;
	}
	public Set getResources() {
		return resources;
	}

	public void setResources(Set resources) {
		this.resources = resources;
	}

	public boolean isRepeat() {
		return isRepeat;
	}

	public void setRepeat(boolean isRepeat) {
		this.isRepeat = isRepeat;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getShowFlag() {
		return showFlag;
	}

	public void setShowFlag(String showFlag) {
		this.showFlag = showFlag;
	}

	public String getInfoValue() {
		return infoValue;
	}

	public void setInfoValue(String infoValue) {
		this.infoValue = infoValue;
	}	
	
}
