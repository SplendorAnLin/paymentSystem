package com.yl.agent.service.impl;

import com.yl.agent.dao.AdviceFeedBackDao;
import com.yl.agent.entity.AdviceFeedBack;
import com.yl.agent.service.AdviceFeedBackService;

/**
 * 意见反馈业务接口处理
 * 
 * @author 聚合支付有限公司
 * @since 2016年7月13日
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
