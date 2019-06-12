package com.yl.customer.interfaces.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.codehaus.jackson.type.TypeReference;

import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.customer.api.bean.Menu;
import com.yl.customer.api.interfaces.CustMenuInterface;
import com.yl.customer.entity.Role;
import com.yl.customer.service.MenuService;
import com.yl.customer.service.RoleService;

/**
 * 商户菜单远程操作接口处理
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月24日
 * @version V1.0.0
 */
public class CustMenuInterfaceImpl implements CustMenuInterface{

	private MenuService menuService;
	private RoleService roleService;
	
	@Override
	public List<Menu> findAll() {
		List<com.yl.customer.entity.Menu> list=menuService.findAll();
		try {
			if(list != null){
				List<Menu> menuList = new ArrayList<>();
				for(com.yl.customer.entity.Menu menu : list){
					menuList.add(JsonUtils.toObject(JsonUtils.toJsonString(menu), new TypeReference<Menu>(){}));
				}
				return menuList;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Menu findById(Long id) {
		com.yl.customer.entity.Menu MenuDb = menuService.findById(id);
		try {
			if (MenuDb != null) {
				Menu MenuInfo = new Menu();
				MenuInfo.setDisplayOrder(MenuDb.getDisplayOrder());
				MenuInfo.setId(MenuDb.getId());
				MenuInfo.setIsLeaf(MenuDb.getIsLeaf());
				MenuInfo.setLabel(MenuDb.getLabel());
				MenuInfo.setLevel(MenuDb.getLevel());
				MenuInfo.setName(MenuDb.getName());
				MenuInfo.setOptimistic(MenuDb.getOptimistic());
				MenuInfo.setParentId(MenuDb.getParentId());
				MenuInfo.setRemark(MenuDb.getRemark());
				MenuInfo.setStatus(JsonUtils.toJsonToObject(MenuDb.getStatus(), com.yl.customer.api.enums.Status.class));
				MenuInfo.setUrl(MenuDb.getUrl());
				if(MenuDb.getChildren() != null){
					List<Menu> menuList = new ArrayList<>();
					for(com.yl.customer.entity.Menu menu : MenuDb.getChildren()){
						menuList.add(JsonUtils.toObject(JsonUtils.toJsonString(menu.getChildren()), new TypeReference<Menu>(){}));
					}
					MenuInfo.setChildren(menuList);
				}
				return MenuInfo;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 根据目录名查询
	 */
	@Override
	public Menu findMenuName(String name) {
		com.yl.customer.entity.Menu MenuDb = menuService.findMenuName(name);
		try {
			if (MenuDb != null) {
				Menu MenuInfo = new Menu();
				MenuInfo.setDisplayOrder(MenuDb.getDisplayOrder());
				MenuInfo.setId(MenuDb.getId());
				MenuInfo.setIsLeaf(MenuDb.getIsLeaf());
				MenuInfo.setLabel(MenuDb.getLabel());
				MenuInfo.setLevel(MenuDb.getLevel());
				MenuInfo.setName(MenuDb.getName());
				MenuInfo.setOptimistic(MenuDb.getOptimistic());
				MenuInfo.setParentId(MenuDb.getParentId());
				MenuInfo.setRemark(MenuDb.getRemark());
				MenuInfo.setStatus(JsonUtils.toJsonToObject(MenuDb.getStatus(), com.yl.customer.api.enums.Status.class));
				MenuInfo.setUrl(MenuDb.getUrl());
				if(MenuDb.getChildren() != null){
					List<Menu> menuList = new ArrayList<>();
					for(com.yl.customer.entity.Menu menu : MenuDb.getChildren()){
						menuList.add(JsonUtils.toObject(JsonUtils.toJsonString(menu.getChildren()), new TypeReference<Menu>(){}));
					}
					MenuInfo.setChildren(menuList);
				}
				return MenuInfo;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 根据目录名模糊查询
	 */
	@Override
	public List<Menu> findMenuByName(String name) {
		List<com.yl.customer.entity.Menu> list = menuService.findMenuByName(name);
		try {
			if(list != null){
				List<Menu> menuList = new ArrayList<>();
				for(com.yl.customer.entity.Menu menu : list){
					menuList.add(JsonUtils.toObject(JsonUtils.toJsonString(menu), new TypeReference<Menu>(){}));
				}
				return menuList;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	@Override
	public List<Menu> findByLevel(String level) {
		List<com.yl.customer.entity.Menu> list=menuService.findByLevel(level);
		try {
			if(list != null){
				List<Menu> menuList = new ArrayList<>();
				for(com.yl.customer.entity.Menu menu : list){
					menuList.add(JsonUtils.toObject(JsonUtils.toJsonString(menu), new TypeReference<Menu>(){}));
				}
				return menuList;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Menu update(Menu menu) {
		com.yl.customer.entity.Menu MenuInfo = menuService.findById(menu.getId());
		if(menu != null){
			MenuInfo.setDisplayOrder(menu.getDisplayOrder());
			MenuInfo.setId(menu.getId());
			MenuInfo.setIsLeaf(menu.getIsLeaf());
			MenuInfo.setLabel(menu.getLabel());
			MenuInfo.setLevel(menu.getLevel());
			MenuInfo.setName(menu.getName());
			if (menu.getParentId()!=null) {
				MenuInfo.setParentId(menu.getParentId());
			}
			MenuInfo.setRemark(menu.getRemark());
			MenuInfo.setStatus(JsonUtils.toJsonToObject(menu.getStatus(), com.yl.customer.enums.Status.class));
			if (menu.getUrl()!=null) {
				MenuInfo.setUrl(menu.getUrl());
			}
			if(menu.getChildren() != null){
				List<com.yl.customer.entity.Menu> menuList = new ArrayList<>();
				for (Menu menufirst : menu.getChildren()) {
					menuList.add(JsonUtils.toObject(JsonUtils.toJsonString(menufirst.getChildren()), new TypeReference<com.yl.customer.entity.Menu>(){}));
				}
				MenuInfo.setChildren(menuList);
			}
			menuService.update(MenuInfo);
			return menu;
		}
		return null;
	}

	@Override
	public Menu create(Menu menu) {
		com.yl.customer.entity.Menu MenuInfo = new com.yl.customer.entity.Menu();
		if(menu != null){
			MenuInfo.setDisplayOrder(menu.getDisplayOrder());
			MenuInfo.setId(menu.getId());
			MenuInfo.setIsLeaf(menu.getIsLeaf());
			MenuInfo.setLabel(menu.getLabel());
			MenuInfo.setLevel(menu.getLevel());
			MenuInfo.setName(menu.getName());
			MenuInfo.setOptimistic(menu.getOptimistic());
			MenuInfo.setParentId(menu.getParentId());
			MenuInfo.setRemark(menu.getRemark());
			MenuInfo.setStatus(JsonUtils.toJsonToObject(menu.getStatus(), com.yl.customer.enums.Status.class));
			MenuInfo.setUrl(menu.getUrl());
			if(menu.getChildren() != null){
				List<com.yl.customer.entity.Menu> menuList = new ArrayList<>();
				for (Menu menufirst : menu.getChildren()) {
					menuList.add(JsonUtils.toObject(JsonUtils.toJsonString(menufirst.getChildren()), new TypeReference<com.yl.customer.entity.Menu>(){}));
				}
				MenuInfo.setChildren(menuList);
			}
			menuService.create(MenuInfo);
			//添加目录权限
			Role role=roleService.findById(Long.valueOf(1));
			Set<com.yl.customer.entity.Menu> menus = new HashSet<com.yl.customer.entity.Menu>();
			for (com.yl.customer.entity.Menu menuOld : role.getMenus()) {
				menus.add(menuOld);
			}
			menus.add(MenuInfo);
			role.setMenus(menus);
			roleService.update(role);
			return menu;
		}
		return null;
	}

	public void setMenuService(MenuService menuService) {
		this.menuService = menuService;
	}

	public RoleService getRoleService() {
		return roleService;
	}

	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}
}