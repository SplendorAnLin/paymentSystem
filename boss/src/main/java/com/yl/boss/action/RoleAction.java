package com.yl.boss.action;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.agent.api.enums.Status;
import com.yl.boss.Constant;
import com.yl.boss.entity.Authorization;
import com.yl.boss.entity.Function;
import com.yl.boss.entity.Menu;
import com.yl.boss.entity.Role;
import com.yl.boss.service.FunctionService;
import com.yl.boss.service.MenuService;
import com.yl.boss.service.RoleService;

/**
 * 角色控制器
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月8日
 * @version V1.0.0
 */
public class RoleAction extends Struts2ActionSupport{
	private static final long serialVersionUID = 672965980327097889L;
	private Role role;
	private RoleService roleService;
	private MenuService menuService ;
	private FunctionService functionService;
	private List<Function> allFunction = new ArrayList<Function>();
	private List<Menu> allMenu = new ArrayList<Menu>();
	private List<Long> menuList;
	private List<Menu> childrenmenu=new ArrayList<Menu>();
	private List<Menu> roleMenu;
	private List<Long> functionList;
	private List<Function> roleFunction;
	private String name;
	private String msg;
	
	/**
	 * 根据ID查询角色详细信息
	 * @return
	 */
	public String roleQueryById(){
		role=roleService.findById(role.getId());
		roleMenu=new ArrayList<Menu>();
		roleFunction=new ArrayList<Function>();
		for(Menu menu : role.getMenus()){
			roleMenu.add(menu);
		}
		for(Function fun : role.getFunctions()){
			roleFunction.add(fun);
		}
		return SUCCESS;
	}
	public String roleQueryAndAll(){
		allMenu=menuService.findShowAll();
		allFunction=functionService.findShowAll();
		role=roleService.findById(role.getId());
		roleMenu=new ArrayList<Menu>();
		roleFunction=new ArrayList<Function>();
		for (int i = 0; i < allMenu.size(); i++) {
			if (allMenu.get(i).getName().equals("角色管理")) {
				allMenu.remove(i);
			}
		}
		for (Menu menu : role.getMenus()) {
			if (menu!=null) {
				roleMenu.add(menu);
			}
		}
		for(Function fun : role.getFunctions()){
			if (fun!=null) {
				roleFunction.add(fun);
			}
		}
		return SUCCESS;
	}
	
	/**
	 * 添加权限角色
	 * @return
	 */
	public String addRole(){
		try {
			role.setStatus(Status.TRUE);
			role.setRoleType("OPERATOR");
			Set<Menu> menus = this.getMenuSet(menuList);
			Set<Function> functions= this.getFunctionSet(functionList);
			if (menus!=null&&menus.size()!=0) {
				role.setMenus(menus);
			}
			if (functions!=null&&functions.size()>0) {
				role.setFunctions(functions);
			}
			roleService.create(role) ;
			logger.info("角色创建成功： name : "+role.getName()+"type: "+role+" status : "+role.getStatus()+" remark : "+role.getRemark());
			msg="true";
		}catch (Exception e) {
			logger.info("创建权限角色失败",e) ;
			msg="false";
		}
		return SUCCESS;
	}
	
	public String checkRoleName(){
		try {
			List<Role> roles=roleService.findByName(URLDecoder.decode(name, "UTF-8"));
			if (roles.size()>0) {
				msg="false";
				
			}else {
				msg="true";
			}
		} catch (UnsupportedEncodingException e) {
			msg="false";
			e.printStackTrace();
		}
		return SUCCESS;
	}
	/**
	 *更新角色权限
	 * @return
	 */
	public String roleUpdate(){
		try {
			Role roleNew=roleService.findById(role.getId());
			roleNew.setName(role.getName());
			roleNew.setRemark(role.getRemark());
			Set<Menu> menus = this.getMenuSet(menuList);
			Set<Function> functions= this.getFunctionSet(functionList);
			roleNew.setMenus(menus);
			roleNew.setFunctions(functions);
			roleService.update(roleNew);
			logger.info("角色修改成功： name : "+role.getName()+"type: "+role+" status : "+role.getStatus()+" remark : "+role.getRemark());
			return SUCCESS ;
		}catch (Exception e) {
			logger.info("修改权限角色失败",e) ;
			return ERROR;
		}
	}
	public String roleUpdateStatus(){
		try {
			Role roleNew=roleService.findById(role.getId());
			if (roleNew.getStatus()==Status.TRUE) {
				roleNew.setStatus(Status.FALSE);
			}else{
				roleNew.setStatus(Status.TRUE);
			}
			roleService.update(roleNew);
			logger.info("角色修改成功： name : "+role.getName()+"type: "+role+" status : "+role.getStatus()+" remark : "+role.getRemark());
			return SUCCESS ;
		} catch (Exception e) {
			logger.info("修改权限角色失败",e) ;
			return ERROR;
		}
	}
	
	/**
	 * 获取所有目录和功能
	 * @return
	 */
	public String getMenuAndFun(){
		allMenu=menuService.findShowAll();
		allFunction=functionService.findShowAll();
		for (int i = 0; i < allMenu.size(); i++) {
			if (allMenu.get(i).getName().equals("角色管理")) {
				allMenu.remove(i);
			}
		}
		return SUCCESS;
	}
	
	private Set<Menu> getMenuSet(List<Long> menuList){
		if (menuList!=null) {
			Set<Menu> menus = new HashSet<Menu>();
			for (Long t : menuList) {
				try {
					Menu menu = menuService.findById(t);
					if (menu != null) {
						menus.add(menu);
					}
				} catch (Exception e) {
					continue;
				}
			}
			return menus;
		}
		return null;
	}
	
	private Set<Function> getFunctionSet(List<Long> functionList){
		if (functionList!=null) {
			Set<Function> functions = new HashSet<Function>();
			for (Long t : functionList) {
				try {
					Function function = functionService.findById(t);
					if (function != null) {
						functions.add(function);
					}
				} catch (Exception e) {
					continue;
				}
			}
			return functions;
		}
		return null;
	}

	public List<Long> getMenuList() {
		return menuList;
	}
	public void setMenuList(List<Long> menuList) {
		this.menuList = menuList;
	}
	public List<Long> getFunctionList() {
		return functionList;
	}
	public void setFunctionList(List<Long> functionList) {
		this.functionList = functionList;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	public RoleService getRoleService() {
		return roleService;
	}
	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}
	public MenuService getMenuService() {
		return menuService;
	}
	public void setMenuService(MenuService menuService) {
		this.menuService = menuService;
	}
	public FunctionService getFunctionService() {
		return functionService;
	}
	public void setFunctionService(FunctionService functionService) {
		this.functionService = functionService;
	}
	public List<Function> getAllFunction() {
		return allFunction;
	}
	public void setAllFunction(List<Function> allFunction) {
		this.allFunction = allFunction;
	}
	public List<Menu> getAllMenu() {
		return allMenu;
	}
	public void setAllMenu(List<Menu> allMenu) {
		this.allMenu = allMenu;
	}
	public List<Menu> getRoleMenu() {
		return roleMenu;
	}
	public void setRoleMenu(List<Menu> roleMenu) {
		this.roleMenu = roleMenu;
	}
	public List<Function> getRoleFunction() {
		return roleFunction;
	}
	public void setRoleFunction(List<Function> roleFunction) {
		this.roleFunction = roleFunction;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public List<Menu> getChildrenmenu() {
		return childrenmenu;
	}
	public void setChildrenmenu(List<Menu> childrenmenu) {
		this.childrenmenu = childrenmenu;
	}
}
