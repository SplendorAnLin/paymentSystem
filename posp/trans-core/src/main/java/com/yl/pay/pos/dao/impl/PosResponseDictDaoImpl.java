package com.yl.pay.pos.dao.impl;

import com.yl.pay.pos.dao.PosResponseDictDao;
import com.yl.pay.pos.dao.impl.helper.EntityDao;
import com.yl.pay.pos.entity.PosResponseDict;

/**
 * Title: BankResponseDictDao Dao
 * Description: 
 * Copyright: Copyright (c)2011
 * Company: com.yl.pay
 * @author haitao.liu
 */
public class PosResponseDictDaoImpl implements PosResponseDictDao{

	private EntityDao entityDao;
	
	//根据返回码查询
	public PosResponseDict findByResponseCode(String responseCode){
		
		String hql="from PosResponseDict b where b.respCode = ?  ";
		return entityDao.findUnique(PosResponseDict.class, hql, responseCode);
	}

	public EntityDao getEntityDao() {
		return entityDao;
	}
	public void setEntityDao(EntityDao entityDao) {
		this.entityDao = entityDao;
	}
}
