package com.yl.boss.dao.impl;

import javax.annotation.Resource;

import com.yl.boss.dao.EntityDao;
import com.yl.boss.dao.UserFeedBackDao;
import com.yl.boss.entity.UserFeedback;

/**
 * 意见反馈数据接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月14日
 * @version V1.0.0
 */
public class UserFeedBackDaoImpl implements UserFeedBackDao {
	
	@Resource
	private EntityDao entityDao;
	
	@Override
	public void save(UserFeedback feedBack) {
			entityDao.save(feedBack);
	}
	public EntityDao getEntityDao() {
		return entityDao;
	}
	public void setEntityDao(EntityDao entityDao) {
		this.entityDao = entityDao;
	}

}
