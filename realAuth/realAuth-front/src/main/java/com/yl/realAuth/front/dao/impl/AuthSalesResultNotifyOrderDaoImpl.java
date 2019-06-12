package com.yl.realAuth.front.dao.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.lefu.commons.utils.Page;
import com.yl.realAuth.front.dao.AuthSalesResultNotifyOrderDao;
import com.yl.realAuth.front.dao.mapper.AuthSalesResultNotifyOrderMapper;
import com.yl.realAuth.model.AuthResultNotifyOrder;

@Repository("authSalesResultNotifyOrderDao")
public class AuthSalesResultNotifyOrderDaoImpl implements AuthSalesResultNotifyOrderDao {

	@Resource
	private AuthSalesResultNotifyOrderMapper authSalesResultNotifyOrderMapper;

	@Override
	public AuthResultNotifyOrder queryOrderByCode(String code) {
		return authSalesResultNotifyOrderMapper.queryOrderByCode(code);
	}

	@Override
	public void recordFaildOrder(AuthResultNotifyOrder authResultNotifyOrder) {
		authSalesResultNotifyOrderMapper.recordFaildOrder(authResultNotifyOrder);

	}

	@Override
	public List<AuthResultNotifyOrder> queryBy(int maxCount, Page page, Date before, Date now) {
		return authSalesResultNotifyOrderMapper.queryBy(maxCount, page, before, now);
	}

	@Override
	public void updateAuthSalesResultNotifyOrder(AuthResultNotifyOrder authResultNotifyOrder) {
		authSalesResultNotifyOrderMapper.updateAuthSalesResultNotifyOrder(authResultNotifyOrder);

	}

	@Override
	public void updateNotifyCount(AuthResultNotifyOrder authResultNotifyOrder) {
		authSalesResultNotifyOrderMapper.updateNotifyCount(authResultNotifyOrder);
	}

}
