package com.yl.agent.api.bean;

import java.util.HashSet;
import java.util.Set;

import com.yl.agent.api.bean.BeseBean;
import com.yl.agent.api.bean.Function;
import com.yl.agent.api.bean.Menu;
import com.yl.agent.api.enums.Status;

/**
 * 角色
 * 
 * @author 聚合支付有限公司
 * @since 2016年7月13日
 * @version V1.0.0
 */
public class Role extends BeseBean{
	
	private static final long serialVersionUID = 4958577765794044456L;
	/**
	 * 角色名称
	 */
	private String name;
	/**
	 * 状态
	 */
	private Status status;
	/**
	 * 备注信息
	 */
	private String remark;	
	/**
	 * 角色类型
	 */
	private String roleType;
	/**
	 * 功能
	 */	
	private Set<Function> functions = new HashSet<Function>();	
	/**
	 * 菜单
	 */	
	private Set<Menu> menus = new HashSet<Menu>();	
	/**
	 *操作员 
	 */	
	private Set<AgentOperator> operators = new HashSet<AgentOperator>();
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getRoleType() {
		return roleType;
	}
	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}
	public Set<Function> getFunctions() {
		return functions;
	}
	public void setFunctions(Set<Function> functions) {
		this.functions = functions;
	}
	public Set<Menu> getMenus() {
		return menus;
	}
	public void setMenus(Set<Menu> menus) {
		this.menus = menus;
	}
	public Set<AgentOperator> getOperators() {
		return operators;
	}
	public void setOperators(Set<AgentOperator> operators) {
		this.operators = operators;
	}
	
}
