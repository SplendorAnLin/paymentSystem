package com.yl.online.trade.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yl.online.model.model.AccountFailInfo;
import com.yl.online.trade.dao.AccountFailInfoDao;
import com.yl.online.trade.service.AccountFailInfoService;

/**
 * 入账服务接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年7月27日
 * @version V1.0.0
 */
@Service("accountService")
public class AccountFailInfoServiceImpl implements AccountFailInfoService {
	@Resource
	private AccountFailInfoDao accountDao;

	@Override
	public List<AccountFailInfo> queryAccountFacadeInfo(int maxCount, int maxNums) {
		return accountDao.queryAccountFacadeInfo(maxCount, maxNums);
	}

	@Override
	public void modifyAccountInfo(AccountFailInfo accountFacadeInfo) {
		accountDao.modifyAccountInfo(accountFacadeInfo);
	}

	@Override
	public void createAccountInfo(AccountFailInfo accountFacadeInfo) {
		accountDao.createAccountInfo(accountFacadeInfo);
	}

}
