package com.yl.agent.entity;

import java.util.List;
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
import javax.persistence.Transient;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.yl.agent.api.enums.Status;

/**
 * 菜单
 * 
 * @author 聚合支付有限公司
 * @since 2016年7月8日
 * @version V1.0.0
 */
@Entity
@Table(name = "MENU")
@JsonIgnoreProperties(value="roles")
public class Menu extends AutoIDEntity {

	private static final long serialVersionUID = 427047597701146621L;
	/**
	 * 名称
	 */
	private String name;	
	/**
	 * 标签
	 */
	private String label;
	/**
	 * 是否叶子节点
	 */
	private String isLeaf;
	/**
	 * 上级菜单ID
	 */
	private Long parentId;
	/**
	 * 菜单链接地址
	 */	
	private String url;
	/**
	 * 菜单级别
	 */	
	private String level;
	/**
	 * 显示顺序
	 */	
	private Integer displayOrder;
	/**
	 * 状态
	 */
	private Status status;
	/**
	 * 备注信息
	 */
	private String remark;	
	/**
	 * 角色
	 */
	@JsonIgnore
	private Set<Role> roles ;
	
	/**
	 * 下级菜单
	 */
	private List<Menu> children ;	
	
	@Column(name = "NAME", length = 50)
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
	
	@Column(name = "LABEL", length = 200)
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}

	@Column(name = "IS_LEAF",  length = 4)
	public String getIsLeaf() {
		return isLeaf;
	}
	public void setIsLeaf(String isLeaf) {
		this.isLeaf = isLeaf;
	}
	
	@Column(name = "LEVEL", length = 3)
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	
	@Column(name = "URL", length = 200)
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	@Column(name = "DISPLAY_ORDER", length = 3)
	public Integer getDisplayOrder() {
		return displayOrder;
	}
	public void setDisplayOrder(Integer displayOrder) {
		this.displayOrder = displayOrder;
	}
	
	@Column(name = "PARENT_ID")
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	
	@Column(name = "REMARK", length = 100)
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@ManyToMany(cascade={CascadeType.MERGE},targetEntity=com.yl.agent.entity.Role.class,fetch=FetchType.EAGER)
	@JoinTable(name="ROLE_MENU",
			joinColumns=@JoinColumn(name="MENU_ID"),
            inverseJoinColumns=@JoinColumn(name="ROLE_ID")
    )
	public Set<Role> getRoles() {
		return roles;
	}
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	@Transient
	public List<Menu> getChildren() {
		return children;
	}
	public void setChildren(List<Menu> children) {
		this.children = children;
	}
}