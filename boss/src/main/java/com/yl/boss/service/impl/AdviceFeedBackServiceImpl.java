package com.yl.boss.service.impl;

import com.yl.boss.dao.AdviceFeedBackDao;
import com.yl.boss.entity.AdviceFeedBack;
import com.yl.boss.service.AdviceFeedBackService;

/**
 * 意见反馈业务接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月13日
 * @version V1.0.0
 */
public class AdviceFeedBackServiceImpl implements AdviceFeedBackService{
	private AdviceFeedBackDao adviceFeedBackDao;
	public AdviceFeedBack create(AdviceFeedBack adviceFeedBack) {
		// TODO Auto-generated method stub
		return adviceFeedBackDao.create(adviceFeedBack);
	}
	public AdviceFeedBackDao getAdviceFeedBackDao() {
		return adviceFeedBackDao;
	}
	public void setAdviceFeedBackDao(AdviceFeedBackDao adviceFeedBackDao) {
		this.adviceFeedBackDao = adviceFeedBackDao;
	}
}
