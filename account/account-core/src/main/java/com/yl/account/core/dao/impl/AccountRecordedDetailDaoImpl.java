/**
 *
 */
package com.yl.account.core.dao.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.lefu.commons.utils.Page;
import com.yl.account.core.dao.AccountRecordedDetailDao;
import com.yl.account.core.dao.mapper.AccountRecordedDetailBaseMapper;
import com.yl.account.enums.CallType;
import com.yl.account.model.AccountDayDetail;
import com.yl.account.model.AccountRecordedDetail;

/**
 * 账务入账明细数据处理接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年5月14日
 * @version V1.0.0
 */
@Repository("accountRecordedDetailDao")
public class AccountRecordedDetailDaoImpl implements AccountRecordedDetailDao {

	@Resource
	private AccountRecordedDetailBaseMapper accountRecordedDetailBaseMapper;

	/*
	 * (non-Javadoc)
	 * @see com.lefu.account.core.dao.AccountRecordedDetailDao#create(com.lefu.account.model.AccountRecordedDetail)
	 */
	@Override
	public void create(AccountRecordedDetail accountRecordedDetail) {
		accountRecordedDetail.setCreateTime(new Date());
		accountRecordedDetail.setVersion(System.currentTimeMillis());
		accountRecordedDetailBaseMapper.insert(accountRecordedDetail);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lefu.account.core.dao.AccountRecordedDetailDao#findBy(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public int findBy(String accountNo, String transOrder, String systemCode, String bussinessCode, String systemFlowId) {
		return accountRecordedDetailBaseMapper.findBy(accountNo, transOrder, systemCode, bussinessCode, systemFlowId);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lefu.account.core.dao.AccountRecordedDetailDao#findBySum(com.lefu.account.model.AccountRecordedDetail)
	 */
	@Override
	public List<AccountDayDetail> findBySum(Date dailyStart, Date dailyEnd) {
		return accountRecordedDetailBaseMapper.findBySum(dailyStart, dailyEnd);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.lefu.account.query.dao.AccountDao#findAccountHistoryBy(java.util.Map)
	 */
	@Override
	public List<AccountRecordedDetail> findAccountHistoryBy(Map<String, Object> accountHistoryQueryParams, Page<?> page) {
		return accountRecordedDetailBaseMapper.findAllAccountHistoryBy(accountHistoryQueryParams, page);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lefu.account.query.dao.AccountRecordedDetailDao#findAccountSummaryBy(java.util.Map)
	 */
	@Override
	public List<Object> findAccountSummaryBy(Map<String, Object> accountHistoryQueryParams) {
		return accountRecordedDetailBaseMapper.findAccountSummaryBy(accountHistoryQueryParams);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lefu.account.query.dao.AccountDao#findAccountBySystemFlowId(java.lang.String)
	 */
	@Override
	public List<AccountRecordedDetail> findAccountByTransOrder(String systemCode, String transOrder) {
		return accountRecordedDetailBaseMapper.findAccountByTransOrder(systemCode, transOrder);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lefu.account.query.dao.AccountRecordedDetailDao#findAccountHistoryBy(com.lefu.account.enums.CallType, java.util.Map)
	 */
	@Override
	public List<AccountRecordedDetail> _findAccountHistoryBy(CallType realTime, Map<String, Object> queryParams) {
		if (CallType.REAL_TIME.equals(realTime)) return accountRecordedDetailBaseMapper._findAccountHistoryBy(queryParams);
		return accountRecordedDetailBaseMapper.findAllAccountHistoryBy(queryParams, null);
	}
    
	@Override
	public List<Map<String, Object>> _queryAccountHistorBy(Map<String, Object> params) {
		
		return accountRecordedDetailBaseMapper.queryAccountHistoryBy(params);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.lefu.account.query.dao.AccountRecordedDetailDao#findAccountHistoryBy()
	 */
	@Override
	public List<Map<String, Object>> findAccountHistoryBy(int currentResutl, int rowNum) {
		return accountRecordedDetailBaseMapper.findAccountHistoryBy(currentResutl, rowNum);
	}

	@Override
	public List<AccountRecordedDetail> findAccountHistoryExportBy(Map<String, Object> accountHistoryQueryParams) {
		return accountRecordedDetailBaseMapper.findAccountHistoryExportBy(accountHistoryQueryParams);
	}

	@Override
	public Map<String, Object> findAccountHistorySum(Map<String, Object> accountHistoryQueryParams) {
		return accountRecordedDetailBaseMapper.findAccountHistorySum(accountHistoryQueryParams);
	}
	
	@Override
	public List<AccountRecordedDetail> findAccountHistory(Map<String, Object> accountHistoryQueryParams) {
		return accountRecordedDetailBaseMapper.findAccountHistory(accountHistoryQueryParams);
	}
}