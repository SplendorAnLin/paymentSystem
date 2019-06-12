package com.yl.customer.service.impl;

import com.yl.customer.dao.AdviceFeedBackDao;
import com.yl.customer.entity.AdviceFeedBack;
import com.yl.customer.service.AdviceFeedBackService;

/**
 * 意见反馈业务接口处理
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月18日
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
