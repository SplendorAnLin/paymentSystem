package com.yl.customer.action;

import java.lang.reflect.Array;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.mlw.vlh.ValueList;
import net.mlw.vlh.ValueListInfo;
import net.mlw.vlh.web.mvc.ValueListHandlerHelper;

import com.pay.common.util.PreventAttackUtil;
import com.yl.customer.Constant;
import com.yl.customer.entity.Authorization;
import com.yl.customer.entity.Function;
import com.yl.customer.entity.Menu;
import com.yl.customer.entity.Role;
import com.yl.customer.enums.Status;
import com.yl.customer.service.FunctionService;
import com.yl.customer.service.MenuService;
import com.yl.customer.service.RoleService;

/**
 * 角色控制器
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月8日
 * @version V1.0.0
 */
public class RoleAction extends Struts2ActionSupport {

	private static final long serialVersionUID = -4660062236006230059L;
	private ValueListHandlerHelper valueListHelper;
	private Role role;
	private RoleService roleService;
	private MenuService menuService;
	private FunctionService functionService;
	private List<Function> allFunction = new ArrayList<Function>();
	private List<Menu> allMenu = new ArrayList<Menu>();
	private List<Long> menuList;
	private List<Menu> roleMenu;
	private List<Menu> childrenmenu=new ArrayList<Menu>();
	private List<Long> functionList;
	private List<Function> roleFunction;
	private String name;
	private String msg;

	/**
	 * 根据ID查询角色详细信息
	 * 
	 * @return
	 */
	public String roleQueryById() {
		role = roleService.findById(role.getId());
		roleMenu = new ArrayList<Menu>();
		roleFunction = new ArrayList<Function>();
		for (Menu menu : role.getMenus()) {
			roleMenu.add(menu);
		}
		for (Function fun : role.getFunctions()) {
			roleFunction.add(fun);
		}
		return SUCCESS;
	}

	public String roleQueryAndAll() {
		allMenu = menuService.findShowAll();
		allFunction = functionService.findShowAll();
		role = roleService.findById(role.getId());
		roleMenu = new ArrayList<Menu>();
		roleFunction = new ArrayList<Function>();
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
	
	public String checkRoleName(){
		try {
			Authorization auth = (Authorization) getSession().getAttribute(Constant.SESSION_AUTH);
			List<Role> roles=roleService.findByName(URLDecoder.decode(name, "UTF-8"),auth.getCustomerno());
			if (roles.size()>0) {
				msg="false";
			}else {
				msg="true";
			}
		} catch (Exception e) {
			msg="false";
			e.printStackTrace();
		}
		return SUCCESS;
	}

	@SuppressWarnings("unchecked")
	public String roleQueryAll() {
		Authorization auth = (Authorization) getSession().getAttribute(Constant.SESSION_AUTH);
		Map<String, Object> params = retrieveParam(getHttpRequest().getParameterMap()); // request
		params.put("customer_no", auth.getCustomerno());
		ValueListInfo info = new ValueListInfo(params);
		if (params.get("pagingPage") == null) {
			info.setPagingPage(1);
		}
		ValueList valueList = valueListHelper.getValueList("roleInfo", info);
		getHttpRequest().setAttribute("roleInfo", valueList);
		return SUCCESS;
	}

	/**
	 * 添加权限角色
	 * 
	 * @return
	 */
	public String roleAdd() {
		try {
			role.setStatus(Status.TRUE);
			role.setRoleType("OPERATOR");
			Set<Menu> menus = this.getMenuSet(menuList);
			Set<Function> functions = this.getFunctionSet(functionList);
			if (menus!=null&&menus.size() != 0) {
				role.setMenus(menus);
			}
			if (functions!=null&&functions.size()>0) {
				role.setFunctions(functions);
			}
			Authorization auth = (Authorization) getSession().getAttribute(Constant.SESSION_AUTH);
			role.setCustomerNo(auth.getCustomerno());
			roleService.create(role);
			logger.info("角色创建成功： name : " + role.getName() + "type: " + role + " status : " + role.getStatus()
					+ " remark : " + role.getRemark());
			return SUCCESS;
		} catch (Exception e) {
			logger.info("创建权限角色失败", e);
			return ERROR;
		}
	}

	/**
	 * 更新角色权限
	 * 
	 * @return
	 */
	public String roleUpdate() {
		try {
			Role roleNew = roleService.findById(role.getId());
			roleNew.setName(role.getName());
			roleNew.setRemark(role.getRemark());
			Set<Menu> menus = this.getMenuSet(menuList);
			Set<Function> functions = this.getFunctionSet(functionList);
			roleNew.setMenus(menus);
			roleNew.setFunctions(functions);
			roleService.update(roleNew);
			logger.info("角色修改成功： name : " + role.getName() + "type: " + role + " status : " + role.getStatus()
					+ " remark : " + role.getRemark());
			return SUCCESS;
		} catch (Exception e) {
			logger.info("修改权限角色失败", e);
			return ERROR;
		}
	}

	public String roleUpdateStatus() {
		try {
			Role roleNew = roleService.findById(role.getId());
			if (roleNew.getStatus() == Status.TRUE) {
				roleNew.setStatus(Status.FALSE);
			} else {
				roleNew.setStatus(Status.TRUE);
			}
			roleService.update(roleNew);
			logger.info("角色修改成功： name : " + role.getName() + "type: " + role + " status : " + role.getStatus()
					+ " remark : " + role.getRemark());
			return SUCCESS;
		} catch (Exception e) {
			logger.info("修改权限角色失败", e);
			return ERROR;
		}
	}

	/**
	 * 获取所有目录和功能
	 * 
	 * @return
	 */
	public String getMenuAndFun() {
		Authorization auth = (Authorization) getSession().getAttribute(Constant.SESSION_AUTH);
		if(auth.getRoleId() == 1){
			allMenu=menuService.findShowAll();
			allFunction=functionService.findShowAll();
			for (int i = 0; i < allMenu.size(); i++) {
				if (allMenu.get(i).getName().equals("角色管理")) {
					allMenu.remove(i);
				}
			}
		}
		return SUCCESS;
	}

	private Set<Menu> getMenuSet(List<Long> menuList) {
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

	private Set<Function> getFunctionSet(List<Long> functionList) {
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

	// 原始请求参数转换
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map retrieveParam(Map requestMap) {
		Map resultMap = new HashMap();
		for (Object key : requestMap.keySet()) {
			Object value = requestMap.get(key);
			if (value != null) {
				if (value.getClass().isArray()) {
					int length = Array.getLength(value);
					if (length == 1) {
						boolean includedInSQLInjectionWhitelist = false;
						boolean includedInXSSWhitelist = false;
						if (!includedInSQLInjectionWhitelist) {
							resultMap.put(key,
									PreventAttackUtil.filterSQLInjection(Array.get(value, 0).toString()).trim());
						}
						if (!includedInXSSWhitelist) {
							if (resultMap.containsKey(key)) {
								resultMap.put(key, PreventAttackUtil.filterXSS(resultMap.get(key).toString()).trim());
							} else {
								resultMap.put(key, PreventAttackUtil.filterXSS(Array.get(value, 0).toString()).trim());
							}
						}
						if (includedInSQLInjectionWhitelist && includedInXSSWhitelist) {
							resultMap.put(key, Array.get(value, 0).toString().trim());
						}
					}
					if (length > 1) {
						resultMap.put(key, value);
					}
				} else {
					boolean includedInSQLInjectionWhitelist = false;
					boolean includedInXSSWhitelist = false;
					if (!includedInSQLInjectionWhitelist) {
						resultMap.put(key, PreventAttackUtil.filterSQLInjection(value.toString()).trim());
					}
					if (!includedInXSSWhitelist) {
						if (resultMap.containsKey(key)) {
							resultMap.put(key, PreventAttackUtil.filterXSS(resultMap.get(key).toString()).trim());
						} else {
							resultMap.put(key, PreventAttackUtil.filterXSS(value.toString()).trim());
						}
					}
					if (includedInSQLInjectionWhitelist && includedInXSSWhitelist) {
						resultMap.put(key, value.toString().trim());
					}
				}
			}
		}
		return resultMap;
	}

	public ValueListHandlerHelper getValueListHelper() {
		return valueListHelper;
	}

	public void setValueListHelper(ValueListHandlerHelper valueListHelper) {
		this.valueListHelper = valueListHelper;
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
