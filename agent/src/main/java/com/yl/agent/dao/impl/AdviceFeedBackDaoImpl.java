package com.yl.agent.dao.impl;

import com.yl.agent.dao.AdviceFeedBackDao;
import com.yl.agent.dao.DAOException;
import com.yl.agent.dao.EntityDao;
import com.yl.agent.entity.AdviceFeedBack;

/**
 * 意见反馈实现接口处理
 * 
 * @author 聚合支付有限公司
 * @since 2016年7月1日
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
