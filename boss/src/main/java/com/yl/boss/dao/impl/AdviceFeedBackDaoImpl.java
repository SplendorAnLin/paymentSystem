package com.yl.boss.dao.impl;

import com.yl.boss.dao.AdviceFeedBackDao;
import com.yl.boss.dao.DAOException;
import com.yl.boss.dao.EntityDao;
import com.yl.boss.entity.AdviceFeedBack;

/**
 * 意见反馈数据接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月11日
 * @version V1.0.0
 */
public class AdviceFeedBackDaoImpl implements AdviceFeedBackDao {
	
	private EntityDao entityDao;

	public AdviceFeedBack create(AdviceFeedBack adviceFeedBack) {
		// TODO Auto-generated method stub
		if(adviceFeedBack == null){
			throw new DAOException("adviceFeedBack不能为空！");
		}
		entityDao.persist(adviceFeedBack);
		return adviceFeedBack;
	}
	
	public EntityDao getEntityDao() {
		return entityDao;
	}
	public void setEntityDao(EntityDao entityDao) {
		this.entityDao = entityDao;
	}

}
