/**
 * 
 */
package com.yl.boss.action;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.boss.entity.Menu;
import com.yl.boss.entity.Role;
import com.yl.boss.service.MenuService;
import com.yl.boss.service.RoleService;

/**
 * 菜单信息控制器
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月28日
 * @version V1.0.0
 */
public class MenuAction extends Struts2ActionSupport {
	private static final long serialVersionUID = -2326161764761346638L;
	private Menu menu;
	private List<Menu> menuList;
	private MenuService menuService;
	private RoleService roleService;
	private String menuName;
	private String msg;

	public String toMenuQuery() {
		menuList = menuService.findAll();
		return SUCCESS;
	}

	public String findMenuByName() {
		try {
			menuList = menuService.findMenuByName(java.net.URLDecoder.decode(menuName, "utf-8"));
			msg = JsonUtils.toJsonString(menuList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String findMenuName() {
		try {
			menu = menuService.findMenuName(java.net.URLDecoder.decode(menuName, "utf-8"));
			if (menu != null) {
				msg = JsonUtils.toJsonString(menu);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String toMenuEdit() {
		String menuId = getHttpRequest().getParameter("menuId");
		Long id = Long.parseLong(menuId);
		menu = menuService.findById(id);
		List<Menu> pMenus = menuService.findByLevel("1");
		getHttpRequest().setAttribute("pMenus", pMenus);
		return SUCCESS;
	}

	public String modifyMenu() {
		Menu menuDb = menuService.findById(menu.getId());
		menuDb.setName(menu.getName());
		menuDb.setUrl(menu.getUrl());
		menuDb.setDisplayOrder(menu.getDisplayOrder());
		menuDb.setStatus(menu.getStatus());
		menuDb.setParentId(menu.getParentId());
		menu = menuService.update(menuDb);
		return SUCCESS;
	}

	public String addChildMenu() {
		Menu menuDb = menuService.findById(menu.getParentId());
		Long level = Long.parseLong(menuDb.getLevel());
		menu.setIsLeaf("Y");
		menu.setLevel(String.valueOf(level + 1));
		menu = menuService.create(menu);
		//添加目录权限
		Role role=roleService.findById(Long.valueOf(1));
		Set<Menu> menus = new HashSet<Menu>();
		for (Menu menuOld : role.getMenus()) {
			menus.add(menuOld);
		}
		menus.add(menu);
		role.setMenus(menus);
		roleService.update(role);
		return SUCCESS;
	}

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public RoleService getRoleService() {
		return roleService;
	}

	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	public List<Menu> getMenuList() {
		return menuList;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public void setMenuList(List<Menu> menuList) {
		this.menuList = menuList;
	}

	public MenuService getMenuService() {
		return menuService;
	}

	public void setMenuService(MenuService menuService) {
		this.menuService = menuService;
	}
}