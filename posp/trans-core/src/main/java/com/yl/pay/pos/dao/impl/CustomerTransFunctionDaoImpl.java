package com.yl.pay.pos.dao.impl;

import com.yl.pay.pos.dao.CustomerTransFunctionDao;
import com.yl.pay.pos.dao.impl.helper.EntityDao;
import com.yl.pay.pos.entity.CustomerTransFunction;

/**
 * Title: 
 * Description:   
 * Copyright: 2015年7月6日上午10:34:06
 * Company: SDJ
 * @author zhongxiang.ren
 */
public class CustomerTransFunctionDaoImpl implements CustomerTransFunctionDao {
	private EntityDao entityDao;
	/* (non-Javadoc)
	 * @see com.com.yl.pay.pos.dao.CustomerTransFunctionDao#findByCustomerNo(java.lang.String)
	 */
	@Override
	public CustomerTransFunction findByCustomerNo(String customerNo) {
		String hql = "from CustomerTransFunction where customerNo = ?";
		return entityDao.findUnique(CustomerTransFunction.class, hql, customerNo);
	}
	
	public void setEntityDao(EntityDao entityDao) {
		this.entityDao = entityDao;
	}

}
