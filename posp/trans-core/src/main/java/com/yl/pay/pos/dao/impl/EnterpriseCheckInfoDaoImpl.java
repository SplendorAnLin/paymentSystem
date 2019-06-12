/**
 * 
 */
package com.yl.pay.pos.dao.impl;

import com.yl.pay.pos.dao.EnterpriseCheckInfoDao;
import com.yl.pay.pos.dao.impl.helper.EntityDao;
import com.yl.pay.pos.entity.EnterpriseCheckInfo;

import java.util.List;

/**
 * Title: 
 * Description: 
 * Copyright: Copyright (c)2015 
 * Company: com.yl.pay
 * 
 * @author zhendong.wu
 */
public class EnterpriseCheckInfoDaoImpl implements EnterpriseCheckInfoDao {

	private EntityDao entityDao;
	@Override
	public EnterpriseCheckInfo findByCustomerNo(String customerNo) {
		EnterpriseCheckInfo enterpriseCheckInfo=null;
		String hql="from EnterpriseCheckInfo where customerNo=?";
		List<EnterpriseCheckInfo> liEnterpriseCheckInfo=entityDao.find(hql,customerNo);
		if(liEnterpriseCheckInfo!=null&&liEnterpriseCheckInfo.size()>0){
			enterpriseCheckInfo=liEnterpriseCheckInfo.get(0);
		}
		return enterpriseCheckInfo;
	}
	public void setEntityDao(EntityDao entityDao) {
		this.entityDao = entityDao;
	}

}
