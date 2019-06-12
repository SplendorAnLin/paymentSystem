package com.yl.customer.service;

import java.util.List;

import com.yl.customer.entity.Role;

/**
 * 角色Service
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月20日
 * @version V1.0.0
 */
public interface RoleService {

	List<Role> findAll();
	
	Role findById(Long id);
	
	Role update(Role roles);
	
	Role create(Role role);
	
	void delete(Role role);
	List<Role> findAllAvilable(String customerNo);
	List<Role> findByName(String name,String customerNo);
}
