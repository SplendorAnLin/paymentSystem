/**
 *
 */
package com.yl.account.core.service.impl;

import com.lefu.commons.utils.Page;
import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.account.api.bean.AccountBean;
import com.yl.account.api.bean.AccountChangeDetailBean;
import com.yl.account.api.bean.response.AccountChangeRecordsResponse;
import com.yl.account.api.bean.response.AccountQueryResponse;
import com.yl.account.api.enums.HandlerResult;
import com.yl.account.core.ExceptionMessages;
import com.yl.account.core.dao.AccountChangeDetailDao;
import com.yl.account.core.exception.BussinessRuntimeException;
import com.yl.account.core.service.AccountChangeDetailService;
import com.yl.account.core.utils.CodeBuilder;
import com.yl.account.enums.AccountStatus;
import com.yl.account.enums.AccountType;
import com.yl.account.enums.FundSymbol;
import com.yl.account.enums.UserRole;
import com.yl.account.model.AccountChangeDetail;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 账户变更逻辑处理接口实现
 * 
 * @author 聚合支付有限公司
 * @since 2016年3月22日
 * @version V1.0.0
 */
@Service
public class AccountChangeDetailServiceImpl implements AccountChangeDetailService {
	
	private static final Logger logger = LoggerFactory.getLogger(AccountChangeDetailServiceImpl.class);

	@Resource
	private AccountChangeDetailDao accountChangeDetailDao;

	/*
	 * (non-Javadoc)
	 * @see com.yl.account.core.service.AccountChangeDetailService#create(com.yl.account.model.AccountChangeDetail)
	 */
	@Override
	public void create(String bussinessCode, Date requestTime, String accountNo, AccountType type, AccountStatus status, String systemCode, String systemFlowId,
			String userNo, UserRole role, Double balance, Double freezeBalance, Double transitBalance, FundSymbol symbol, Double amount, String operator,
			String changeReason) {
		AccountChangeDetail accountChangeDetail = new AccountChangeDetail();
		accountChangeDetail.setCode(CodeBuilder.getOrderIdByUUId());
		accountChangeDetail.setBussinessCode(bussinessCode);
		accountChangeDetail.setRequestTime(requestTime);
		accountChangeDetail.setAccountNo(accountNo);
		accountChangeDetail.setAccountType(type);
		accountChangeDetail.setAccountStatus(status);
		accountChangeDetail.setSystemCode(systemCode);
		accountChangeDetail.setSystemFlow(systemFlowId);
		accountChangeDetail.setUserNo(userNo);
		accountChangeDetail.setUserRole(role);
		accountChangeDetail.setBalance(balance);
		accountChangeDetail.setFreezeBalance(freezeBalance);
		accountChangeDetail.setTransitBalance(transitBalance);
		accountChangeDetail.setSymbol(symbol);
		accountChangeDetail.setAmount(amount);
		accountChangeDetail.setOperator(operator);
		accountChangeDetail.setChangeReason(changeReason);
		accountChangeDetailDao.create(accountChangeDetail);

	}

	/*
	 * (non-Javadoc)
	 * @see com.yl.account.core.service.AccountChangeDetailService#findAccountBySystemId(java.lang.String, java.lang.String)
	 */
	@Override
	public AccountChangeDetail findAccountBySystemId(String systemCode, String systemFlowId, String bussinessCode) {
		return accountChangeDetailDao.findAccountBySystemId(systemCode, systemFlowId, bussinessCode);
	}

	/*
	 * (non-Javadoc)
	 * @see com.yl.account.core.service.AccountChangeDetailService#findFreezeBalanceBy(java.util.Date, java.util.Date)
	 */
	@Override
	public List<Map<String, Object>> findFreezeBalanceBy(Date dailyStart, Date dailyEnd) {
		return accountChangeDetailDao.findFreezeBalanceBy(dailyStart, dailyEnd);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.lefu.accAccountChangeDetailount.query.service.AccountService#findAccountBySystemFlowId(java.lang.String)
	 */
	@Override
	public AccountQueryResponse findAccountBySystemFlowId(String systemCode, String systemFlowId) {
		AccountQueryResponse accountQueryResponse = null;
		List<AccountBean> accounts = null;
		try {
			AccountChangeDetail accountChangeDetail = accountChangeDetailDao.findAccountBySystemFlowId(systemCode, systemFlowId);
			if (accountChangeDetail != null) {
				String accountNo = accountChangeDetail.getAccountNo();
				AccountBean account = new AccountBean();
				account.setCode(accountNo);
				accounts = new ArrayList<AccountBean>();
				accounts.add(account);
			}
			accountQueryResponse = new AccountQueryResponse();
			accountQueryResponse.setResult(HandlerResult.SUCCESS);
			accountQueryResponse.setFinishTime(new Date());
			accountQueryResponse.setAccountBeans(accounts);
		} catch (Exception e) {
			logger.error("{}", e);
			throw new BussinessRuntimeException(ExceptionMessages.DATABASE_OPERATOR_ERROR);
		}
		return accountQueryResponse;
	}

	/*
	 * (non-Javadoc)
	 * @see com.yl.account.query.service.AccountChangeDetailService#findAccountChangeRecordsBy(java.util.Map, com.lefu.commons.utils.Page)
	 */
	@Override
	public AccountChangeRecordsResponse findAccountChangeRecordsBy(Map<String, Object> queryParams, Page<?> page) {
		AccountChangeRecordsResponse accountChangeRecordsResponse = null;
		try {
			List<AccountChangeDetail> accountChangeDetails = accountChangeDetailDao.findAccountChangeRecordsBy(queryParams, page);

			accountChangeRecordsResponse = new AccountChangeRecordsResponse();
			accountChangeRecordsResponse.setResult(HandlerResult.SUCCESS);
			accountChangeRecordsResponse.setFinishTime(new Date());
			accountChangeRecordsResponse.setAccountChangeDetailBeans(JsonUtils.toObject(JsonUtils.toJsonString(accountChangeDetails),
					new TypeReference<List<AccountChangeDetailBean>>() {}));
			accountChangeRecordsResponse.setPage(page);
		} catch (Exception e) {
			logger.error("{}", e);
			throw new BussinessRuntimeException(ExceptionMessages.DATABASE_OPERATOR_ERROR);
		}
		return accountChangeRecordsResponse;
	}

	@Override
	public List<Map<String, Object>> querycountChangeRecordsBy(Map<String, Object> queryParams) {
		   
		return accountChangeDetailDao.queryAccountChangeRecordsBy(queryParams);
	}
  
	
}
