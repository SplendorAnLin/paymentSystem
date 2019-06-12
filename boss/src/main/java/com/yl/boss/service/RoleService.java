package com.yl.boss.service;

import java.util.List;

import com.yl.boss.entity.Role;

/**
 * 角色业务接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月13日
 * @version V1.0.0
 */
public interface RoleService {

	List<Role> findAll();
	
	Role findById(Long id);
	
	Role update(Role roles);
	
	Role create(Role role);
	
	void delete(Role role);
	List<Role> findAllAvilable();
	public List<Role> findByName(String name);

}
