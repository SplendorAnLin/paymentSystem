/**
 * 
 */
package com.yl.account.core.dao.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.yl.account.core.ExceptionMessages;
import com.yl.account.core.dao.AccountBehaviorDao;
import com.yl.account.core.dao.mapper.AccountBehaviorBaseMapper;
import com.yl.account.core.exception.BussinessRuntimeException;
import com.yl.account.model.AccountBehavior;

/**
 * 账户行为数据处理接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年3月24日
 * @version V1.0.0
 */
@Repository("accountBehaviorDao")
public class AccountBehaviorDaoImpl implements AccountBehaviorDao {

	@Resource
	private AccountBehaviorBaseMapper accountBehaviorBaseMapper;

	/*
	 * (non-Javadoc)
	 * @see com.lefu.account.core.dao.AccountBehaviorDao#findAccountBehavior(java.lang.String, com.lefu.account.enums.AccountType)
	 */
	@Override
	public AccountBehavior findAccountBehavior(String accountNo) {
		return accountBehaviorBaseMapper.findAccountBehaviorBy(accountNo);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lefu.account.core.dao.AccountBehaviorDao#insert(com.lefu.account.model.AccountBehavior)
	 */
	@Override
	public void insert(AccountBehavior accountBehavior) {
		accountBehavior.setCreateTime(new Date());
		accountBehavior.setVersion(System.currentTimeMillis());
		accountBehaviorBaseMapper.insert(accountBehavior);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lefu.account.core.dao.AccountBehaviorDao#subtractPreFreezeCount(com.lefu.account.model.AccountBehavior)
	 */
	@Override
	public void addOrSubtractPreFreezeCount(AccountBehavior accountBehavior) {
		int updateResults = accountBehaviorBaseMapper.addOrSubtractPreFreezeCount(accountBehavior, System.currentTimeMillis());
		if (updateResults <= 0) throw new BussinessRuntimeException(ExceptionMessages.DATABASE_ACCOUNT_UPDATE_FAILURE);
	}

}
