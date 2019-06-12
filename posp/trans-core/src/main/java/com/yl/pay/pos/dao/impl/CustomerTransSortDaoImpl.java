package com.yl.pay.pos.dao.impl;

import com.yl.pay.pos.dao.CustomerTransSortDao;
import com.yl.pay.pos.dao.impl.helper.EntityDao;
import com.yl.pay.pos.entity.CustomerTransSort;

/**
 * Title: 
 * Description:   
 * Copyright: 2015年7月6日上午10:36:14
 * Company: SDJ
 * @author zhongxiang.ren
 */
public class CustomerTransSortDaoImpl implements CustomerTransSortDao {
	private EntityDao entityDao;
	/* (non-Javadoc)
	 * @see com.com.yl.pay.pos.dao.CustomerTransSortDao#findBySortAndTransType(java.lang.String, java.lang.String)
	 */
	@Override
	public CustomerTransSort findBySortAndTransType(String sortCode,
			String transType) {
		String hql = "from CustomerTransSort where sortCode = ? and transType = ?";
		return entityDao.findUnique(CustomerTransSort.class, hql, sortCode,transType);
	}
	
	public void setEntityDao(EntityDao entityDao) {
		this.entityDao = entityDao;
	}

}
