package com.yl.pay.pos.dao.impl;

import com.yl.pay.pos.dao.ForeignCardFeeDao;
import com.yl.pay.pos.dao.impl.helper.EntityDao;
import com.yl.pay.pos.entity.ForeignCardFee;
import com.yl.pay.pos.enums.Status;

public class ForeignCardFeeDaoImpl implements ForeignCardFeeDao {

	public EntityDao entityDao;
	@Override
	public ForeignCardFee findByMccCateGoryAndStatus(String mccCategory,
			Status status) {
		String hql="from ForeignCardFee fcf where fcf.mccCategory=? and fcf.status=?";
		return entityDao.findUnique(ForeignCardFee.class, hql,mccCategory,status);
	}
	public void setEntityDao(EntityDao entityDao) {
		this.entityDao = entityDao;
	}
	
	

}
