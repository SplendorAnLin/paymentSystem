/**
 * 
 */
package com.yl.account.core.service.impl;

import com.yl.account.core.dao.AccountCreditReverseDao;
import com.yl.account.core.service.AccountCreditReverseService;
import com.yl.account.core.utils.CodeBuilder;
import com.yl.account.model.AccountCreditReverse;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 账务补单业务逻辑处理接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年3月22日
 * @version V1.0.0
 */
@Service
public class AccountCreditReverseServiceImpl implements AccountCreditReverseService {

	@Resource
	private AccountCreditReverseDao accountCreditReverseDao;

	/*
	 * (non-Javadoc)
	 * @see com.yl.account.core.service.AccountCreditReverseService#create(com.yl.account.model.AccountCreditReverse)
	 */
	@Override
	public void create(AccountCreditReverse accountCreditReverse) {
		accountCreditReverse.setCode(CodeBuilder.getOrderIdByUUId());
		accountCreditReverseDao.create(accountCreditReverse);
	}

	/*
	 * (non-Javadoc)
	 * @see com.yl.account.core.service.AccountCreditReverseService#queryBy(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public AccountCreditReverse queryBy(String systemCode, String systemFlowId, String bussinessCode) {
		return accountCreditReverseDao.queryBy(systemCode, systemFlowId, bussinessCode);
	}

}
