package com.yl.realAuth.front.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.lefu.commons.utils.Page;
import com.yl.realAuth.front.dao.AuthSalesResultNotifyOrderDao;
import com.yl.realAuth.front.service.AuthSalesResultNotifyOrderService;
import com.yl.realAuth.model.AuthResultNotifyOrder;

/**
 * 实名认证补单记录服务
 * @author kewei.liu
 * @since 2015年7月10日
 */
@Service("authSalesResultNotifyOrderService")
public class AuthSalesResultNotifyOrderServiceImpl implements AuthSalesResultNotifyOrderService {

	@Resource
	private AuthSalesResultNotifyOrderDao authSalesResultNotifyOrderDao;

	@Override
	public AuthResultNotifyOrder queryByOrderCode(String code) {
		return authSalesResultNotifyOrderDao.queryOrderByCode(code);
	}

	@Override
	public void recordFaildOrder(AuthResultNotifyOrder authResultNotifyOrder) {
		authSalesResultNotifyOrderDao.recordFaildOrder(authResultNotifyOrder);
	}

	@Override
	public List<AuthResultNotifyOrder> queryBy(int maxCount, Page page, Date before, Date now) {
		return authSalesResultNotifyOrderDao.queryBy(maxCount, page, before, now);
	}

	@Override
	public void updateAuthSalesResultNotifyOrder(AuthResultNotifyOrder authResultNotifyOrder) {
		authSalesResultNotifyOrderDao.updateAuthSalesResultNotifyOrder(authResultNotifyOrder);
	}

	@Override
	public void updateNotifyCount(AuthResultNotifyOrder authResultNotifyOrder) {
		authSalesResultNotifyOrderDao.updateNotifyCount(authResultNotifyOrder);
	}

}
