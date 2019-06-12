package com.yl.customer.api.bean;

import com.yl.customer.api.enums.Status;
import com.yl.customer.api.enums.YesNo;

/**
 * 功能
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月24日
 * @version V1.0.0
 */
public class Function extends BeseBean{

	private static final long serialVersionUID = 5336972628651366399L;
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
	 * 备注信息
	 */
	private String remark;
	/**
	 * 目录编号
	 */
	private String menuId;
	/**
	 * 角色
	 */
	/*private Set<Role> roles;*/
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public YesNo getIsCheck() {
		return isCheck;
	}
	public void setIsCheck(YesNo isCheck) {
		this.isCheck = isCheck;
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
/*	public Set<Role> getRoles() {
		return roles;
	}
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}*/
	public String getMenuId() {
		return menuId;
	}
	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

}
