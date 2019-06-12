/**
 * 
 */
package com.yl.account.core.service.impl;

import com.yl.account.core.dao.AccountBehaviorDao;
import com.yl.account.core.service.AccountBehaviorService;
import com.yl.account.core.utils.CodeBuilder;
import com.yl.account.model.Account;
import com.yl.account.model.AccountBehavior;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 账户行为逻辑处理接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年3月22日
 * @version V1.0.0
 */
@Service
public class AccountBehaviorServiceImpl implements AccountBehaviorService {

	@Resource
	private AccountBehaviorDao accountBehaviorDao;

	/*
	 * (non-Javadoc)
	 * @see com.yl.account.core.service.AccountBehaviorService#findAccountBehavior(java.lang.String, com.yl.account.enums.AccountType)
	 */
	@Override
	public AccountBehavior findAccountBehavior(String accountNo) {
		return accountBehaviorDao.findAccountBehavior(accountNo);
	}

	/*
	 * (non-Javadoc)
	 * @see com.yl.account.core.service.AccountBehaviorService#subtractPreFreezeCount(java.lang.String)
	 */
	@Override
	public void addOrSubtractPreFreezeCount(AccountBehavior accountBehavior) {
		accountBehaviorDao.addOrSubtractPreFreezeCount(accountBehavior);
	}

	/*
	 * (non-Javadoc)
	 * @see com.yl.account.core.service.AccountBehaviorService#insert(com.yl.account.model.AccountBehavior)
	 */
	@Override
	public void insert(Account account) {
		AccountBehavior accountBehavior = new AccountBehavior();
		accountBehavior.setCode(CodeBuilder.getOrderIdByUUId());
		accountBehavior.setAccountNo(account.getCode());
		accountBehavior.setAccountType(account.getType());
		accountBehavior.setPreFreezeCount(1);
		accountBehaviorDao.insert(accountBehavior);
	}

}
