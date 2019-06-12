package com.yl.agent.service;

import java.util.List;

import com.yl.agent.entity.Organization;

/**
 * 用户管理类
 * 
 * @author 聚合支付有限公司
 * @since 2016年7月13日
 * @version V1.0.0
 */
public interface OrganizationService {
	public List<Organization> findAllProvince();
	public List<Organization> findCityByCode(String code);
	public Organization findByName(String name);
}
