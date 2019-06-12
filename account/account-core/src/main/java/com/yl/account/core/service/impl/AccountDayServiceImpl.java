package com.yl.account.core.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yl.account.core.dao.AccountDayDao;
import com.yl.account.core.service.AccountDayService;
import com.yl.account.model.AccountDay;

/**
 * 商户余额表业务逻辑处理接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年3月22日
 * @version V1.0.0
 */
@Service
public class AccountDayServiceImpl implements AccountDayService {

	@Resource
	private AccountDayDao accountDayDao;

	/*
	 * (non-Javadoc)
	 * @see com.yl.account.core.service.AccountDayService#insert(com.yl.account.model.AccountDay)
	 */
	@Override
	public void insert(AccountDay accountDay) {
		accountDayDao.insert(accountDay);
	}

}
