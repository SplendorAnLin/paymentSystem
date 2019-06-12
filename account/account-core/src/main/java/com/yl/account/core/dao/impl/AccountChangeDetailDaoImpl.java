/**
 *
 */
package com.yl.account.core.dao.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.lefu.commons.utils.Page;
import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.account.core.dao.AccountChangeDetailDao;
import com.yl.account.core.dao.mapper.AccountChangeDetailBaseMapper;
import com.yl.account.model.AccountChangeDetail;
import com.yl.mq.producer.client.ProducerClient;
import com.yl.mq.producer.client.ProducerMessage;

/**
 * 账户变更记录数据处理接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年6月22日
 * @version V1.0.0
 */
@Repository("accountChangeDetailDao")
public class AccountChangeDetailDaoImpl implements AccountChangeDetailDao {
	private static final Logger logger=LoggerFactory.getLogger(AccountChangeDetailDaoImpl.class);
	@Resource
	private AccountChangeDetailBaseMapper accountChangeDetailBaseMapper;

	/*
	 * (non-Javadoc)
	 * @see com.lefu.account.core.dao.AccountChangeDetailDao#create(com.lefu.account.model.AccountChangeDetail)
	 */
	@Override
	public void create(AccountChangeDetail accountChangeDetail) {
		accountChangeDetail.setCreateTime(new Date());
		accountChangeDetail.setVersion(System.currentTimeMillis());
		accountChangeDetailBaseMapper.insert(accountChangeDetail);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lefu.account.core.dao.AccountChangeDetailDao#findAccountBySystemId(java.lang.String, java.lang.String)
	 */
	@Override
	public AccountChangeDetail findAccountBySystemId(String systemCode, String systemFlowId, String bussinessCode) {
		return accountChangeDetailBaseMapper.findAccountBySystemId(systemCode, systemFlowId, bussinessCode);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lefu.account.core.dao.AccountChangeDetailDao#findFreezeBalanceBy(java.util.Date, java.util.Date)
	 */
	@Override
	public List<Map<String, Object>> findFreezeBalanceBy(Date dailyStart, Date dailyEnd) {
		return accountChangeDetailBaseMapper.findFreezeBalanceBy(dailyStart, dailyEnd);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.lefu.account.query.dao.AccountDao#findAccountBySystemFlowId(java.lang.String)
	 */
	@Override
	public AccountChangeDetail findAccountBySystemFlowId(String systemCode, String systemFlowId) {
		return accountChangeDetailBaseMapper.findAccountBySystemFlowId(systemCode, systemFlowId);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lefu.account.query.dao.AccountChangeDetailDao#findAccountChangeRecordsBy(java.util.Map, com.lefu.commons.utils.Page)
	 */
	@Override
	public List<AccountChangeDetail> findAccountChangeRecordsBy(Map<String, Object> queryParams, Page<?> page) {
		return accountChangeDetailBaseMapper.findAllAccountChangeRecordsBy(queryParams, page);
	}

	@Override
	public List<Map<String, Object>> queryAccountChangeRecordsBy(Map<String, Object> queryParams) {
		return accountChangeDetailBaseMapper.queryAllAccountChangeRecordsBy(queryParams);
	}
}
