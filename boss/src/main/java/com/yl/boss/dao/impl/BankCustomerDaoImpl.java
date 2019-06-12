package com.yl.boss.dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.lefu.commons.utils.Page;
import com.yl.boss.dao.BankCustomerDao;
import com.yl.boss.dao.EntityDao;
import com.yl.boss.entity.BankCustomer;
import com.yl.boss.enums.Status;

/**
 * Title: Description: Copyright: Copyright (c)2012 Company: com.yl.pay
 * 
 * @author jinyan.lou
 */
@SuppressWarnings("unchecked")
public class BankCustomerDaoImpl implements BankCustomerDao {
	
	private EntityDao entityDao;
	
	@Override
	public BankCustomer findByBankCustomerNo(String bankCustomerNo) {
		String hql = "from BankCustomer b where 1=1 and b.bankCustomerNo=? and b.status=? ";
		return (BankCustomer) entityDao.find(hql,bankCustomerNo,Status.TRUE).get(0);
	}

	@Override
	public List<BankCustomer> findByOrg(Page<BankCustomer> page, String org, String category) {
		DetachedCriteria dc=DetachedCriteria.forClass(BankCustomer.class);
		dc.add(Restrictions.eq("status", Status.TRUE));
		if(null != org && !org.isEmpty()){
			dc.add(Restrictions.eq("organization", org));
		} else if (null != category && !category.isEmpty()){
			dc.add(Restrictions.eq("mccCategory", category));
		}
		dc.addOrder(Order.asc("id"));
		int currentPage = page.getCurrentPage();
		int showCount = page.getShowCount();
		List<BankCustomer> list = entityDao.findByCriteria(dc, currentPage*showCount, page.getShowCount());
		return list;
	}
	
	@Override
	public Integer findCountByOrg(String org, String category) {
		String hql = "select count(*)  from BankCustomer b where 1=1 and b.status=?";
		boolean orgFalg = false;
		boolean categoryFalg = false;
		if(null != org && !org.isEmpty()){
			hql += " and b.organization=?";
			orgFalg = true;
		} else if (null != category && !category.isEmpty()){
			hql += " and b.mccCategory=?";
			categoryFalg = true;
		}
		if(orgFalg && categoryFalg){
			return Integer.valueOf(String.valueOf(entityDao.find(hql,Status.TRUE,org,category).get(0)));
		} else if (orgFalg && !categoryFalg){
			return Integer.valueOf(String.valueOf(entityDao.find(hql,Status.TRUE,org).get(0)));
		} else if (!orgFalg && categoryFalg){
			return Integer.valueOf(String.valueOf(entityDao.find(hql,Status.TRUE,category).get(0)));
		} else {
			return Integer.valueOf(String.valueOf(entityDao.find(hql,Status.TRUE).get(0)));
		}
	}
	

	public EntityDao getEntityDao() {
		return entityDao;
	}

	public void setEntityDao(EntityDao entityDao) {
		this.entityDao = entityDao;
	}
	
	
}
