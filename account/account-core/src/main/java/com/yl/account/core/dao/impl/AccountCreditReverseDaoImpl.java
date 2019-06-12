/**
 * 
 */
package com.yl.account.core.dao.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.yl.account.core.dao.AccountCreditReverseDao;
import com.yl.account.core.dao.mapper.AccountCreditReverseMapper;
import com.yl.account.model.AccountCreditReverse;

/**
 * 账务补单数据处理接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年3月24日
 * @version V1.0.0
 */
@Repository("accountCreditReverseDao")
public class AccountCreditReverseDaoImpl implements AccountCreditReverseDao {

	@Resource
	private AccountCreditReverseMapper accountCreditReverseMapper;

	/*
	 * (non-Javadoc)
	 * @see com.lefu.account.core.dao.AccountCreditReverseDao#create(com.lefu.account.model.AccountCreditReverse)
	 */
	@Override
	public void create(AccountCreditReverse accountCreditReverse) {
		accountCreditReverse.setCreateTime(new Date());
		accountCreditReverse.setVersion(System.currentTimeMillis());
		accountCreditReverseMapper.create(accountCreditReverse);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lefu.account.core.dao.AccountCreditReverseDao#queryBy(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public AccountCreditReverse queryBy(String systemCode, String systemFlowId, String bussinessCode) {
		return accountCreditReverseMapper.queryBy(systemCode, systemFlowId, bussinessCode);
	}

}
