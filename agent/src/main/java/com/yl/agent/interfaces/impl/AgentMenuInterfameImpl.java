package com.yl.agent.interfaces.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.codehaus.jackson.type.TypeReference;
import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.agent.api.bean.Menu;
import com.yl.agent.api.interfaces.AgentMenuInterface;
import com.yl.agent.entity.Role;
import com.yl.agent.service.MenuService;
import com.yl.agent.service.RoleService;

/**
 * 服务商菜单远程操作接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年7月11日
 * @version V1.0.0
 */
public class AgentMenuInterfameImpl implements AgentMenuInterface {

	private MenuService menuService;
	private RoleService roleService;
	/**
	 * 查询所有
	 */
	public List<Menu> findAll() {
		List<com.yl.agent.entity.Menu> list = menuService.findAll();
		try {
			if (list != null) {
				List<Menu> menuList = new ArrayList<>();
				for (com.yl.agent.entity.Menu menu : list) {
					menuList.add(JsonUtils.toObject(JsonUtils.toJsonString(menu), new TypeReference<Menu>() {
					}));
				}
				return menuList;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 根据ID查询
	 */
	@Override
	public Menu findById(Long id) {
		com.yl.agent.entity.Menu MenuDb = menuService.findById(id);
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
				MenuInfo.setStatus(JsonUtils.toJsonToObject(MenuDb.getStatus(), com.yl.agent.api.enums.Status.class));
				MenuInfo.setUrl(MenuDb.getUrl());
				if (MenuDb.getChildren() != null) {
					List<Menu> menuList = new ArrayList<>();
					for (com.yl.agent.entity.Menu menu : MenuDb.getChildren()) {
						menuList.add(JsonUtils.toObject(JsonUtils.toJsonString(menu.getChildren()),
								new TypeReference<Menu>() {
								}));
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
		com.yl.agent.entity.Menu MenuDb = menuService.findMenuName(name);
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
				MenuInfo.setStatus(JsonUtils.toJsonToObject(MenuDb.getStatus(), com.yl.agent.api.enums.Status.class));
				MenuInfo.setUrl(MenuDb.getUrl());
				if (MenuDb.getChildren() != null) {
					List<Menu> menuList = new ArrayList<>();
					for (com.yl.agent.entity.Menu menu : MenuDb.getChildren()) {
						menuList.add(JsonUtils.toObject(JsonUtils.toJsonString(menu.getChildren()),
								new TypeReference<Menu>() {
								}));
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
		List<com.yl.agent.entity.Menu> list = menuService.findMenuByName(name);
		try {
			if (list != null) {
				List<Menu> menuList = new ArrayList<>();
				for (com.yl.agent.entity.Menu menu : list) {
					menuList.add(JsonUtils.toObject(JsonUtils.toJsonString(menu), new TypeReference<Menu>() {
					}));
				}
				return menuList;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 根据等级查询
	 */
	@Override
	public List<Menu> findByLevel(String level) {
		List<com.yl.agent.entity.Menu> list = menuService.findByLevel(level);
		try {
			if (list != null) {
				List<Menu> menuList = new ArrayList<>();
				for (com.yl.agent.entity.Menu menu : list) {
					menuList.add(JsonUtils.toObject(JsonUtils.toJsonString(menu), new TypeReference<Menu>() {
					}));
				}
				return menuList;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 修改
	 */
	@Override
	public Menu update(Menu menu) {
		com.yl.agent.entity.Menu MenuInfo = menuService.findById(menu.getId());
		if (menu != null) {
			MenuInfo.setDisplayOrder(menu.getDisplayOrder());
			MenuInfo.setId(menu.getId());
			MenuInfo.setIsLeaf(menu.getIsLeaf());
			MenuInfo.setLabel(menu.getLabel());
			MenuInfo.setLevel(menu.getLevel());
			MenuInfo.setName(menu.getName());
			MenuInfo.setOptimistic(menu.getOptimistic());
			if (menu.getParentId() != null) {
				MenuInfo.setParentId(menu.getParentId());
			}
			MenuInfo.setRemark(menu.getRemark());
			MenuInfo.setStatus(menu.getStatus());
			if (menu.getUrl()!=null) {
				MenuInfo.setUrl(menu.getUrl());
			}
			if (menu.getChildren() != null) {
				List<com.yl.agent.entity.Menu> menuList = new ArrayList<>();
				for (Menu menufirst : menu.getChildren()) {
					menuList.add(JsonUtils.toObject(JsonUtils.toJsonString(menufirst.getChildren()),
							new TypeReference<com.yl.agent.entity.Menu>() {
							}));
				}
				MenuInfo.setChildren(menuList);
			}
			menuService.update(MenuInfo);
			return menu;
		}
		return null;
	}

	/**
	 * 新建
	 */
	@Override
	public Menu create(Menu menu) {
		com.yl.agent.entity.Menu MenuInfo = new com.yl.agent.entity.Menu();
		if (menu != null) {
			MenuInfo.setDisplayOrder(menu.getDisplayOrder());
			MenuInfo.setId(menu.getId());
			MenuInfo.setIsLeaf(menu.getIsLeaf());
			MenuInfo.setLabel(menu.getLabel());
			MenuInfo.setLevel(menu.getLevel());
			MenuInfo.setName(menu.getName());
			MenuInfo.setOptimistic(menu.getOptimistic());
			MenuInfo.setParentId(menu.getParentId());
			MenuInfo.setRemark(menu.getRemark());
			MenuInfo.setStatus(menu.getStatus());
			MenuInfo.setUrl(menu.getUrl());
			if (menu.getChildren() != null) {
				List<com.yl.agent.entity.Menu> menuList = new ArrayList<>();
				for (Menu menufirst : menu.getChildren()) {
					menuList.add(JsonUtils.toObject(JsonUtils.toJsonString(menufirst.getChildren()),
							new TypeReference<com.yl.agent.entity.Menu>() {
							}));
				}
				MenuInfo.setChildren(menuList);
			}
			menuService.create(MenuInfo);
			//添加目录权限
			Role role=roleService.findById(Long.valueOf(1));
			Set<com.yl.agent.entity.Menu> menus = new HashSet<com.yl.agent.entity.Menu>();
			for (com.yl.agent.entity.Menu menuOld : role.getMenus()) {
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