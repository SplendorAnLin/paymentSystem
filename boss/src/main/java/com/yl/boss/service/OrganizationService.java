package com.yl.boss.service;

import java.util.List;

import com.yl.boss.entity.Organization;

/**
 * 用户管理类业务接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月13日
 * @version V1.0.0
 */
public interface OrganizationService {
	public List<Organization> findAllProvince();
	public List<Organization> findCityByCode(String code);
	public Organization findByName(String name);
	public List<Organization> findByParentCode(String code);
}
