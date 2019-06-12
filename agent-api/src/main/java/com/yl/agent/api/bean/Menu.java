package com.yl.agent.api.bean;

import java.util.List;

import com.yl.agent.api.bean.BeseBean;
import com.yl.agent.api.bean.Menu;
import com.yl.agent.api.enums.Status;

/**
 * 菜单
 * 
 * @author 聚合支付有限公司
 * @since 2016年7月13日
 * @version V1.0.0
 */
public class Menu extends BeseBean{
	
	private static final long serialVersionUID = -6182509005509073389L;
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
	 * 下级菜单
	 */
	private List<Menu> children ;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getIsLeaf() {
		return isLeaf;
	}

	public void setIsLeaf(String isLeaf) {
		this.isLeaf = isLeaf;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public Integer getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(Integer displayOrder) {
		this.displayOrder = displayOrder;
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

	public List<Menu> getChildren() {
		return children;
	}

	public void setChildren(List<Menu> children) {
		this.children = children;
	}
}