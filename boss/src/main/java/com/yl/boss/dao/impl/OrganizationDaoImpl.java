package com.yl.boss.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yl.boss.dao.DAOException;
import com.yl.boss.dao.EntityDao;
import com.yl.boss.dao.OrganizationDao;
import com.yl.boss.entity.Organization;

/**
 * 组织机构操作数据接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月14日
 * @version V1.0.0
 */
public class OrganizationDaoImpl implements OrganizationDao{


	private Log log= LogFactory.getLog(OrganizationDaoImpl.class);
	private EntityDao entityDao;
	
	public Organization create(Organization organization)
			throws DAOException {
		if(organization==null){
			throw new DAOException("Organization信息为空！");
		}
		entityDao.persist(organization);
		return organization;
	}

	public Organization findByCode(String code) {		
		return entityDao.findById(Organization.class, code);	
	}

	@SuppressWarnings("unchecked")
	public List<Organization> findAllOrganization() {
		String hql="from Organization o where length(code) = 2";
		List<Organization> list=entityDao.find(hql);
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<Organization> findByParentCode(String parentCode) {
		String hql="from Organization o where o.parentCode=?";
		return entityDao.find(hql,parentCode);
	}
	
	@SuppressWarnings("rawtypes")
	public List findByParent(String parentCode){
		String sql = "select o.CODE,o.NAME,o.OPTIMISTIC,o.PARENT_CODE," +
				"(select decode(count(*),0,'false','true') " +
				"   from tbl_pos_organization z where z.PARENT_CODE = o.code ) isParent " +
				"from tbl_pos_organization o where o.PARENT_CODE =:parentCode";
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("parentCode", parentCode);
		return entityDao.queryListBySqlQuery(sql,params);
	}
	public Integer findSubOrganizationSizeByCode(String parentCode){
		String hql = "select count(*) from Organization o where o.parentCode=?";
		return Integer.parseInt(String.valueOf(entityDao.find(hql,parentCode).get(0)));
	}
	public Organization findByName(String name) {
		String hql="from Organization o where o.name=?";
		return entityDao.findUnique(Organization.class, hql, name);
	}
	
	@SuppressWarnings("unchecked")
	public List<Organization> findProvince(){
		String hql = "from Organization where parentCode in ('1','2','3','4','5','6') order by code ";
		return entityDao.findInCache(hql, true, null);
	}
	@SuppressWarnings("unchecked")
	public List<Organization> findCityByCode(String code){
		String hql = " from Organization where code like ? and code !="+code;
		return entityDao.findInCache(hql, true, null, code+"%");
	}
	
	public EntityDao getEntityDao() {
		return entityDao;
	}

	public void setEntityDao(EntityDao entityDao) {
		this.entityDao = entityDao;
	}

}
