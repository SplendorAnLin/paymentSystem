package com.yl.account.core.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yl.account.core.dao.AccountDayDetailDao;
import com.yl.account.core.service.AccountDayDetailService;
import com.yl.account.model.AccountDayDetail;

/**
 * 商户余额收支明细表业务逻辑处理接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年5月14日
 * @version V1.0.0
 */
@Service
public class AccountDayDetailServiceImpl implements AccountDayDetailService {

	@Resource
	private AccountDayDetailDao accountDayDetailDao;

	@Override
	public void insert(List<AccountDayDetail> accountDayDetails) {
		accountDayDetailDao.insert(accountDayDetails);
	}

}
