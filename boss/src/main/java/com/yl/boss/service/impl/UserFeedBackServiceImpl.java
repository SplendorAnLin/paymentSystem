package com.yl.boss.service.impl;

import javax.annotation.Resource;

import com.yl.boss.dao.UserFeedBackDao;
import com.yl.boss.entity.UserFeedback;
import com.yl.boss.service.UserFeedBackService;

/**
 * 意见反馈业务接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月13日
 * @version V1.0.0
 */
public class UserFeedBackServiceImpl implements UserFeedBackService {
	
	@Resource
	private UserFeedBackDao userFeedBackDao;
	
	@Override
	public void save(UserFeedback feedBack) {
		userFeedBackDao.save(feedBack);
	}

}
