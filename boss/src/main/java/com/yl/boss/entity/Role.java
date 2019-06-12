package com.yl.boss.entity;

import java.util.HashSet;
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
import javax.persistence.Table;

import com.yl.agent.api.enums.Status;

/**
 * 角色Bean
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月11日
 * @version V1.0.0
 */
@Entity
@Table(name = "ROLES")
public class Role extends AutoIDEntity {

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
	private Set<Operator> operators = new HashSet<Operator>();
	
	@Column(name = "NAME", length = 30)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "STATUS", columnDefinition = "VARCHAR(30)")
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	
	@Column(name = "REMARK", length = 100)
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}	
	
	@Column(name = "ROLE_TYPE", length = 20)
	public String getRoleType() {
		return roleType;
	}
	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}
	
	@ManyToMany(cascade={CascadeType.MERGE},targetEntity=com.yl.boss.entity.Function.class,fetch=FetchType.EAGER)
	@JoinTable(name="ROLE_FUNCTION",
			joinColumns=@JoinColumn(name="ROLE_ID"),
            inverseJoinColumns=@JoinColumn(name="FUNCTION_ID")
    )
	public Set<Function> getFunctions() {
		return functions;
	}
	
	public void setFunctions(Set<Function> functions) {
		this.functions = functions;
	}
	
	@ManyToMany(cascade={CascadeType.MERGE},targetEntity=com.yl.boss.entity.Menu.class,fetch=FetchType.EAGER)
	@JoinTable(name="ROLE_MENU",
			joinColumns=@JoinColumn(name="ROLE_ID"),
            inverseJoinColumns=@JoinColumn(name="MENU_ID")
    )
	public Set<Menu> getMenus() {
		return menus;
	}
	public void setMenus(Set<Menu> menus) {
		this.menus = menus;
	}
	
	@ManyToMany(cascade={CascadeType.MERGE},targetEntity=com.yl.boss.entity.Operator.class,fetch=FetchType.LAZY)
	@JoinTable(name="OPERATOR_ROLE",
			joinColumns=@JoinColumn(name="ROLE_ID"),
            inverseJoinColumns=@JoinColumn(name="OPERATOR_ID")
    )
	public Set<Operator> getOperators() {
		return operators;
	}
	public void setOperators(Set<Operator> operators) {
		this.operators = operators;
	}
}
