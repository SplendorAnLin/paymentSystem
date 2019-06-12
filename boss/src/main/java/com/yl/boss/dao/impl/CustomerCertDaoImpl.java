package com.yl.boss.dao.impl;

import java.util.List;

import com.yl.boss.dao.CustomerCertDao;
import com.yl.boss.dao.EntityDao;
import com.yl.boss.entity.CustomerCert;

/**
 * 商户证件数据访问接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月11日
 * @version V1.0.0
 */
public class CustomerCertDaoImpl implements CustomerCertDao {

	private EntityDao entityDao;
	
	@Override
	public void create(CustomerCert customerCert) {
		entityDao.persist(customerCert);
	}

	@Override
	public void update(CustomerCert customerCert) {
		entityDao.update(customerCert);
	}

	public String findLegalPersonByNo(String customerNo){
		String hql = "select legalPerson from CustomerCert where customerNo = ?";
		List list = entityDao.find(hql,customerNo);
		if(list != null && list.size() > 0){
			return (String)list.get(0);
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public CustomerCert findByCustNo(String customerNo) {
		String hql = "from CustomerCert where customerNo = ?";
		List<CustomerCert> list = entityDao.find(hql,customerNo);
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}

	public EntityDao getEntityDao() {
		return entityDao;
	}

	public void setEntityDao(EntityDao entityDao) {
		this.entityDao = entityDao;
	}

}