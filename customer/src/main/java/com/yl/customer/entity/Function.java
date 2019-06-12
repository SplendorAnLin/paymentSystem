package com.yl.customer.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.yl.customer.enums.Status;
import com.yl.customer.enums.YesNo;

/**
 * 功能 Bean
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月15日
 * @version V1.0.0
 */
@Entity
@Table(name = "FUNCTIONS")
@JsonIgnoreProperties(value="roles")
public class Function extends AutoIDEntity {

	/**
	 * 功能名称
	 */
	private String name;	
	/**
	 * 功能类型
	 */
	private String type;
	/**
	 * Action名称
	 */
	private String action;
	/**
	 * 是否验证
	 */	
	private YesNo isCheck;
	/**
	 * 状态
	 */
	private Status status;
	/**
	 * 目录编号
	 */
	private Long menuId;
	/**
	 * 备注信息
	 */
	private String remark;	
	/**
	 * 角色
	 */
	@JsonIgnore
	private Set<Role> roles ;
	
	@Column(name = "NAME", length = 30)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Column(name = "TYPE", length = 20)
	public String getType() {
		return type;
	}
	@Column(name = "MENU_ID")
	public Long getMenuId() {
		return menuId;
	}
	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	@Column(name = "ACTION", length = 100)
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}

	@Enumerated(value = EnumType.STRING)
	@Column(name = "IS_CHECK", columnDefinition = "VARCHAR(4)")
	public YesNo getIsCheck() {
		return isCheck;
	}
	public void setIsCheck(YesNo isCheck) {
		this.isCheck = isCheck;
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
	
	@ManyToMany(targetEntity=com.yl.customer.entity.Role.class)
	@JoinTable(name="ROLE_FUNCTION",
			joinColumns=@JoinColumn(name="FUNCTION_ID"),
            inverseJoinColumns=@JoinColumn(name="ROLE_ID")
    )
	public Set<Role> getRoles() {
		return roles;
	}
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	
}
