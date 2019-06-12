/**
 * 
 */
package com.yl.account.core.dao.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.yl.account.core.ExceptionMessages;
import com.yl.account.core.dao.AccountTransitSummaryDao;
import com.yl.account.core.dao.mapper.AccountTransitSummaryBaseMapper;
import com.yl.account.core.exception.BussinessRuntimeException;
import com.yl.account.enums.CallType;
import com.yl.account.model.AccountTransitSummary;

/**
 * 账务费入账周期汇总数据处理接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年5月14日
 * @version V1.0.0
 */
@Repository("accountTransitSummaryDao")
public class AccountTransitSummaryDaoImpl implements AccountTransitSummaryDao {

	@Resource
	private AccountTransitSummaryBaseMapper accountTransitSummaryBaseMapper;
	
	/*
	 * (non-Javadoc)
	 * @see com.lefu.account.core.dao.AccountTransitSummaryDao#findTransitSummaryBy(java.lang.String, java.util.Date)
	 */
	@Override
	public AccountTransitSummary findTransitSummaryBy(String accountNo, Date waitAccountDate) {
		return accountTransitSummaryBaseMapper.findTransitSummaryBy(accountNo, waitAccountDate);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lefu.account.core.dao.AccountTransitSummaryDao#insert(com.lefu.account.model.AccountTransitSummary)
	 */
	@Override
	public void insert(AccountTransitSummary accountTransitSummary) {
		accountTransitSummary.setCreateTime(new Date());
		accountTransitSummary.setVersion(System.currentTimeMillis());
		accountTransitSummaryBaseMapper.insert(accountTransitSummary);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lefu.account.core.dao.AccountTransitSummaryDao#modify(com.lefu.account.model.AccountTransitSummary)
	 */
	@Override
	public void addOrSubstractWaitAccountAmount(AccountTransitSummary accountTransitSummary) {
		int updateResults = accountTransitSummaryBaseMapper.addOrSubstractWaitAccountAmount(accountTransitSummary, System.currentTimeMillis());
		if (updateResults <= 0) throw new BussinessRuntimeException(ExceptionMessages.DATABASE_ACCOUNT_UPDATE_FAILURE);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lefu.account.core.dao.AccountTransitSummaryDao#subtractWaitAccountAmount(com.lefu.account.model.AccountTransitSummary)
	 */
	@Override
	public void modifySummaryStatus(AccountTransitSummary accountTransitSummary) {
		int updateResults = accountTransitSummaryBaseMapper.modifySummaryStatus(accountTransitSummary, System.currentTimeMillis());
		if (updateResults <= 0) throw new BussinessRuntimeException(ExceptionMessages.DATABASE_ACCOUNT_UPDATE_FAILURE);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lefu.account.core.dao.AccountTransitSummaryDao#findBatchTransitSummaryBySeq(java.lang.String)
	 */
	@Override
	public List<AccountTransitSummary> findBatchTransitSummaryBySeq(String accountNo, String transitDebitSeq) {
		return accountTransitSummaryBaseMapper.findBatchTransitSummaryBySeq(accountNo, transitDebitSeq);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lefu.account.core.dao.AccountTransitSummaryDao#modifySummary(com.lefu.account.model.AccountTransitSummary)
	 */
	@Override
	public void modifySummary(AccountTransitSummary accountTransitSummary) {
		int updateResults = accountTransitSummaryBaseMapper.modifySummary(accountTransitSummary, System.currentTimeMillis());
		if (updateResults <= 0) throw new BussinessRuntimeException(ExceptionMessages.DATABASE_ACCOUNT_UPDATE_FAILURE);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lefu.account.core.dao.AccountTransitSummaryDao#findBatchTransitSummaryBy(int, int)
	 */
	@Override
	public List<AccountTransitSummary> findBatchTransitSummaryBy(int rowNum, int rowId) {
		return accountTransitSummaryBaseMapper.findBatchTransitSummaryBy(rowNum, rowId, new Date());
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.lefu.account.core.dao.AccountTransitSummaryDao#findTransitSummaryBy(java.lang.String)
	 */
	@Override
	public AccountTransitSummary _findTransitSummaryBy(CallType callType, Map<String, Object> accountBalanceQuery) {
		if (callType.equals(CallType.REAL_TIME)) accountTransitSummaryBaseMapper._findTransitSummaryBy(accountBalanceQuery);
		return accountTransitSummaryBaseMapper._findTransitSummaryBy(accountBalanceQuery);
	}

}
