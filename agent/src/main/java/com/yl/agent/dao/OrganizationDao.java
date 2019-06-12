package com.yl.agent.dao;

import java.util.List;

import com.yl.agent.entity.Organization;

/**
 * 组织机构操作
 * 
 * @author 聚合支付有限公司
 * @since 2016年7月1日
 * @version V1.0.0
 */
public interface OrganizationDao {

	/**
	 * 创建组织机构信息
	 * @param Organization
	 * @return Organization
	 * @throws OrganizationException
	 */
	public Organization create(Organization organization) throws DAOException;

	/**
	 * 根据code获取组织机构信息
	 * @param Organization
	 * @return
	 */
	
	public Organization findByCode(String code);
	
	/**
	 * 所有组织机构信息
	 * @param Organization
	 * @return list
	 */
	
	public List<Organization> findAllOrganization();
	
	/**
	 * 根据上级机构代码获取组织机构信息
	 * @param Organization
	 * @return
	 */
	
	public List<Organization> findByParentCode(String parentCode);
	

	/**
	 * 根据组织机构名称获取组织机构信息
	 * @param Organization
	 * @return
	 */
	
	public Organization findByName(String name);

	public Integer findSubOrganizationSizeByCode(String parentCode);
	
	public List findByParent(String parentCode);
	
	public List<Organization> findProvince();
	
	public List<Organization> findCityByCode(String code);
}
