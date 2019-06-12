/**
 * 
 */
package com.yl.account.core.dao.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.yl.account.core.ExceptionMessages;
import com.yl.account.core.dao.AccountTransitBalanceDetailDao;
import com.yl.account.core.dao.mapper.AccountTransitBalanceDetailBaseMapper;
import com.yl.account.core.exception.BussinessRuntimeException;
import com.yl.account.enums.TransitDebitSeq;
import com.yl.account.model.AccountTransitBalanceDetail;

/**
 * 在途资金明细数据处理接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年3月24日
 * @version V1.0.0
 */
@Repository("accountTransitBalanceDetailDao")
public class AccountTransitBalanceDetailDaoImpl implements AccountTransitBalanceDetailDao {

	@Resource
	private AccountTransitBalanceDetailBaseMapper accountTransitBalanceDetailBaseMapper;

	/*
	 * (non-Javadoc)
	 * @see com.lefu.account.core.dao.AccountTransitBalanceDetailDao#insert(com.lefu.account.model.AccountTransitBalanceDetail)
	 */
	@Override
	public AccountTransitBalanceDetail insert(AccountTransitBalanceDetail accountTransitBalanceDetail) {
		Date systemDate = new Date();
		accountTransitBalanceDetail.setCreateTime(systemDate);
		accountTransitBalanceDetail.setVersion(System.currentTimeMillis());
		accountTransitBalanceDetail.setHandleTime(systemDate);
		accountTransitBalanceDetailBaseMapper.insert(accountTransitBalanceDetail);
		return accountTransitBalanceDetail;
	}

	/*
	 * (non-Javadoc)
	 * @see com.lefu.account.core.dao.AccountTransitBalanceDetailDao#findTransitBalanceDetailBy(java.lang.String, java.lang.String)
	 */
	@Override
	public List<AccountTransitBalanceDetail> findTransitBalanceDetailBy(String accountNo, String origTransOrder, String origBussinessCode,
			String origBussinessCodeFee, TransitDebitSeq transitDebitSeq) {
		return accountTransitBalanceDetailBaseMapper.findTransitBalanceDetailBy(accountNo, origTransOrder, origBussinessCode, origBussinessCodeFee,
				transitDebitSeq.name());
	}

	/*
	 * (non-Javadoc)
	 * @see com.lefu.account.core.dao.AccountTransitBalanceDetailDao#modifyWaitAccountDate(com.lefu.account.model.AccountTransitBalanceDetail)
	 */
	@Override
	public void modifyWaitAccountDate(AccountTransitBalanceDetail originalAccountTransitBalanceDetail) {
		int updateResults = accountTransitBalanceDetailBaseMapper.modifyWaitAccountDate(originalAccountTransitBalanceDetail, System.currentTimeMillis());
		if (updateResults <= 0) throw new BussinessRuntimeException(ExceptionMessages.DATABASE_ACCOUNT_UPDATE_FAILURE);
	}

	@Override
	public List<AccountTransitBalanceDetail> findTransitBalanceDetailBy(
			String accountNo, String accountDate) {
		return accountTransitBalanceDetailBaseMapper.findTransitBalanceDetailForAbleBy(accountNo, accountDate);
	}

}
