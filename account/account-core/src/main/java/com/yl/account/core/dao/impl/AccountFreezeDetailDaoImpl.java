/**
 * 
 */
package com.yl.account.core.dao.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.lefu.commons.utils.Page;
import com.yl.account.api.bean.response.AccountFreezeDetailResponse;
import com.yl.account.core.ExceptionMessages;
import com.yl.account.core.dao.AccountFreezeDetailDao;
import com.yl.account.core.dao.mapper.AccountFreezeDetailBaseMapper;
import com.yl.account.core.exception.BussinessRuntimeException;
import com.yl.account.enums.FreezeStatus;
import com.yl.account.model.AccountFreezeDetail;

/**
 * 账户冻结明细数据处理接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年5月14日
 * @version V1.0.0
 */
@Repository("accountFreezeDetailDao")
public class AccountFreezeDetailDaoImpl implements AccountFreezeDetailDao {

	@Resource
	private AccountFreezeDetailBaseMapper accountFreezeDetailBaseMapper;

	/*
	 * (non-Javadoc)
	 * @see com.lefu.account.core.dao.AccountFreezeDetailDao#create(com.lefu.account.model.AccountFreezeDetail)
	 */
	@Override
	public AccountFreezeDetail create(AccountFreezeDetail accountFreezeDetail) {
		accountFreezeDetail.setCreateTime(new Date());
		accountFreezeDetail.setVersion(System.currentTimeMillis());
		accountFreezeDetailBaseMapper.create(accountFreezeDetail);
		return accountFreezeDetail;
	}

	/*
	 * (non-Javadoc)
	 * @see com.lefu.account.core.dao.AccountFreezeDetailDao#findAccountFreezeDetailBy(java.lang.String, com.lefu.account.enums.FreezeStatus)
	 */
	@Override
	public List<AccountFreezeDetail> findAccountFreezeDetailBy(String accountNo, FreezeStatus freezeStatus) {
		return accountFreezeDetailBaseMapper.findAccountFreezeDetailBy(accountNo, freezeStatus);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lefu.account.core.dao.AccountFreezeDetailDao#addFreezeBalance(com.lefu.account.model.AccountFreezeDetail)
	 */
	@Override
	public void addFreezeBalance(AccountFreezeDetail accountFreezeDetail) {
		int updateResults = accountFreezeDetailBaseMapper.addFreezeBalance(accountFreezeDetail, System.currentTimeMillis());
		if (updateResults <= 0) throw new BussinessRuntimeException(ExceptionMessages.DATABASE_ACCOUNT_UPDATE_FAILURE);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lefu.account.core.dao.AccountFreezeDetailDao#preFreezeComplete(com.lefu.account.model.AccountFreezeDetail)
	 */
	@Override
	public void preFreezeComplete(AccountFreezeDetail accountFreezeDetail) {
		int updateResults = accountFreezeDetailBaseMapper.preFreezeComplete(accountFreezeDetail, System.currentTimeMillis());
		if (updateResults <= 0) throw new BussinessRuntimeException(ExceptionMessages.DATABASE_ACCOUNT_UPDATE_FAILURE);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lefu.account.core.dao.AccountFreezeDetailDao#findAccountFreezeDetailByFreezeNo(java.lang.String)
	 */
	@Override
	public AccountFreezeDetail findAccountFreezeDetailByFreezeNo(String freezeNo) {
		return accountFreezeDetailBaseMapper.findAccountFreezeDetailByFreezeNo(freezeNo);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lefu.account.core.dao.AccountFreezeDetailDao#thawComplete(com.lefu.account.model.AccountFreezeDetail)
	 */
	@Override
	public void thawComplete(AccountFreezeDetail accountFreezeDetail) {
		int updateResults = accountFreezeDetailBaseMapper.thawComplete(accountFreezeDetail, System.currentTimeMillis());
		if (updateResults <= 0) throw new BussinessRuntimeException(ExceptionMessages.DATABASE_ACCOUNT_UPDATE_FAILURE);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lefu.account.core.dao.AccountFreezeDetailDao#addConsultBalance(com.lefu.account.model.AccountFreezeDetail)
	 */
	@Override
	public void addConsultBalance(AccountFreezeDetail accountFreezeDetail) {
		int updateResults = accountFreezeDetailBaseMapper.addConsultBalance(accountFreezeDetail, System.currentTimeMillis());
		if (updateResults <= 0) throw new BussinessRuntimeException(ExceptionMessages.DATABASE_ACCOUNT_UPDATE_FAILURE);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lefu.account.core.dao.AccountFreezeDetailDao#findAccountFreezeDetailsBy(com.lefu.commons.utils.Page)
	 */
	@Override
	public List<AccountFreezeDetail> findAccountFreezeDetailsBy(Page<List<AccountFreezeDetail>> page) {
		return accountFreezeDetailBaseMapper.findAllAccountFreezeDetailsBy(page, new Date());
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.lefu.account.query.dao.AccountFreezeDetailDao#findAccountDetailBy(java.util.Map)
	 */
	@Override
	public AccountFreezeDetail findAccountDetailBy(Map<String, Object> queryParams) {
		return accountFreezeDetailBaseMapper.findAccountDetailBy(queryParams);
	}

	@Override
	public List<AccountFreezeDetailResponse> findAccountFzBy(Map<String, Object> accountHistoryQueryParams,
			Page<?> page) {
		return accountFreezeDetailBaseMapper.findAccountFzBy(accountHistoryQueryParams, page);
	}

	@Override
	public boolean queryFreezeNoExists(String freezeNo) {
		int result = accountFreezeDetailBaseMapper.queryFreezeNoExists(freezeNo);
		if(result == 1){
			return true;
		}
		return false;
	}
}
