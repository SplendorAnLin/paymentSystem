package com.yl.account.core.dao.impl;

import com.lefu.commons.utils.Page;
import com.yl.account.core.ExceptionMessages;
import com.yl.account.core.dao.AccountDao;
import com.yl.account.core.dao.mapper.AccountBaseMapper;
import com.yl.account.core.exception.BussinessRuntimeException;
import com.yl.account.enums.AccountType;
import com.yl.account.enums.CallType;
import com.yl.account.enums.UserRole;
import com.yl.account.model.Account;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 账务管理业务逻辑处理接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年6月22日
 * @version V1.0.0
 */
@Repository("accountDao")
public class AccountDaoImpl implements AccountDao {

	@Resource
	private AccountBaseMapper accountBaseMapper;
	
	/*
	 * (non-Javadoc)
	 * @see com.lefu.account.core.dao.AccountManagementDao#create(com.lefu.account.model.Account)
	 */
	@Override
	public void create(Account account) {
		Date operatorDate = new Date();
		account.setCreateTime(operatorDate);
		account.setOpenDate(operatorDate);
		account.setVersion(System.currentTimeMillis());
		accountBaseMapper.insert(account);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lefu.account.core.dao.AccountDao#findAccountByCodeAndType(java.lang.String, com.lefu.account.api.enums.AccountType)
	 */
	@Override
	public Account findAccountByCode(String accountNo) {
		return accountBaseMapper.findAccountByCode(accountNo);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lefu.account.core.dao.AccountDao#modifyAccountInfo(com.lefu.account.model.Account)
	 */
	@Override
	public void modifyAccountInfo(Account account) {
		accountBaseMapper.modifyAccountInfo(account, System.currentTimeMillis());
	}

	/*
	 * (non-Javadoc)
	 * @see com.lefu.account.core.dao.AccountDao#modify(com.lefu.account.model.Account)
	 */
	@Override
	public void addValueableAndTransitBalance(Account account) {
		accountBaseMapper.addValueableAndTransitBalance(account, System.currentTimeMillis());
	}

	/*
	 * (non-Javadoc)
	 * @see com.lefu.account.core.dao.AccountDao#increaseFreezeBalance(com.lefu.account.model.Account)
	 */
	@Override
	public void addOrSubtractFreezeBalance(Account account) {
		accountBaseMapper.addFreezeBalance(account, System.currentTimeMillis());
	}

	/*
	 * (non-Javadoc)
	 * @see com.lefu.account.core.dao.AccountDao#addValueableBalance(com.lefu.account.model.Account)
	 */
	@Override
	public void addValueableBalance(Account account) {
		accountBaseMapper.addValueableBalance(account, System.currentTimeMillis());
	}

	/*
	 * (non-Javadoc)
	 * @see com.lefu.account.core.dao.AccountDao#subtractTransit(com.lefu.account.model.Account)
	 */
	@Override
	public void subtractTransit(Account account) {
		accountBaseMapper.subtractTransit(account, System.currentTimeMillis());
	}

	/*
	 * (non-Javadoc)
	 * @see com.lefu.account.core.dao.AccountDao#subtractValueableAndFreezeBalance(com.lefu.account.model.Account)
	 */
	@Override
	public void subtractValueableAndFreezeBalance(Account account) {
		accountBaseMapper.subtractValueableAndFreezeBalance(account, System.nanoTime());
	}

	/*
	 * (non-Javadoc)
	 * @see com.lefu.account.core.dao.AccountDao#subtractValueableAndTransitBalance(com.lefu.account.model.Account)
	 */
	@Override
	public void subtractValueableAndTransitBalance(Account account) {
		accountBaseMapper.subtractValueableAndTransitBalance(account, System.currentTimeMillis());
	}

	/*
	 * (non-Javadoc)
	 * @see com.lefu.account.core.dao.AccountDao#findAccountBy(java.lang.String, com.lefu.account.enums.UserRole, com.lefu.account.enums.AccountType)
	 */
	@Override
	public Account findAccountBy(String userNo, UserRole userRole, AccountType accountType) {
		List<Account> accounts = accountBaseMapper.findAccountBy(userNo, userRole, accountType);
		if (accounts != null && !accounts.isEmpty()) {
			if (accounts.size() != 1) throw new BussinessRuntimeException(ExceptionMessages.ACCOUNT_EXISTS_MORE_THAN_ONE);
			return accounts.get(0);
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see com.lefu.account.core.dao.AccountDao#findAccountBalanceBySum()
	 */
	@Override
	public List<Map<String, Double>> findAccountBalanceBySum() {
		return accountBaseMapper.findAccountBalanceBySum();
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.lefu.account.core.dao.AccountDao#findAccountByCodeAndType(java.lang.String, com.lefu.account.api.enums.AccountType)
	 */
	@Override
	public List<Account> findAccountBy(CallType callType, Map<String, Object> queryParams) {
		if (callType.equals(CallType.REAL_TIME)) return accountBaseMapper._findAccountBy(queryParams);
		return accountBaseMapper._findAccountBy(queryParams);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lefu.account.query.dao.AccountDao#findAccountByPage(java.util.Map, com.lefu.commons.utils.Page)
	 */
	@Override
	public List<Account> findAccountByPage(Map<String, Object> queryParams, Page<?> page) {
		return accountBaseMapper.findAllAccountByPage(queryParams, page);
	}

	@Override
	public List<Account> findWaitAbleAccounts(int nums,String operDate) {
		return accountBaseMapper.findWaitAbleAccounts(nums,operDate);
	}

	@Override
	public void updateAbleAmount(Account account, Date ableDate) {
		accountBaseMapper.updateAbleAmount(account, ableDate,System.currentTimeMillis());
	}

	@Override
	public List<Map<String, Object>> findAccTypeBalance() {
		// TODO Auto-generated method stub
		return accountBaseMapper.findAccTypeBalance();
	}
	
}
