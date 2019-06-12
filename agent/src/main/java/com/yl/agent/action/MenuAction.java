/**
 * 
 */
package com.yl.agent.action;

import java.util.List;

import com.yl.agent.entity.Menu;
import com.yl.agent.service.MenuService;

/**
 * 菜单控制器
 * 
 * @author 聚合支付有限公司
 * @since 2016年6月28日
 * @version V1.0.0
 */
public class MenuAction extends Struts2ActionSupport {
	
	private Menu menu;;
	private List<Menu> menuList;
	private MenuService menuService;
	
	public String toMenuQuery(){
		menuList = menuService.findAll();
		return SUCCESS;
	}
	
	public String toMenuEdit(){
		String menuId = getHttpRequest().getParameter("menuId");
		Long id = Long.parseLong(menuId);
		menu = menuService.findById(id);
		List<Menu> pMenus = menuService.findByLevel("1");
		getHttpRequest().setAttribute("pMenus", pMenus);
		return SUCCESS;
	}
	
	public String modifyMenu(){
		Menu menuDb = menuService.findById(menu.getId());
		menuDb.setName(menu.getName());
		menuDb.setUrl(menu.getUrl());
		menuDb.setDisplayOrder(menu.getDisplayOrder());
		menuDb.setStatus(menu.getStatus());
		menuDb.setParentId(menu.getParentId());
		menu = menuService.update(menuDb);
		return SUCCESS;
	}
	
	public String addChildMenu(){
		Menu menuDb = menuService.findById(menu.getParentId());
		Long level = Long.parseLong(menuDb.getLevel());
		menu.setIsLeaf("Y");
		menu.setLevel(String.valueOf(level+1));
		menu = menuService.create(menu);
		return SUCCESS;
	}

	public Menu getMenu() {
		return menu;
	}
	public void setMenu(Menu menu) {
		this.menu = menu;
	}
	public List<Menu> getMenuList() {
		return menuList;
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
