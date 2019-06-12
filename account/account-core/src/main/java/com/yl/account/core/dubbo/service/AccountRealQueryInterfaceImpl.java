/**
 * 
 */
package com.yl.account.core.dubbo.service;

import java.util.Map;

import javax.annotation.Resource;

import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.account.api.bean.request.AccountBalanceQuery;
import com.yl.account.api.bean.request.AccountQuery;
import com.yl.account.api.bean.response.AccountBalanceQueryResponse;
import com.yl.account.api.bean.response.AccountHistoryQueryResponse;
import com.yl.account.api.bean.response.AccountQueryResponse;
import com.yl.account.api.dubbo.AccountRealQueryInterface;
import com.yl.account.api.enums.HandlerResult;
import com.yl.account.core.service.AccountRecordedDetailService;
import com.yl.account.core.service.AccountService;
import com.yl.account.enums.CallType;

/**
 * 账户实时查询处理接口
 * 
 * @author 聚合支付有限公司
 * @since 2016年3月26日
 * @version V1.0.0
 */
@Service("accountRealQueryInterface")
public class AccountRealQueryInterfaceImpl implements AccountRealQueryInterface {

	private final Logger logger = LoggerFactory.getLogger(AccountRealQueryInterfaceImpl.class);

	@Resource
	private AccountService accountService;
	@Resource
	private AccountRecordedDetailService accountRecordedDetailService;

	/*
	 * (non-Javadoc)
	 * @see com.lefu.account.api.dubbo.AccountRealQueryInterface#_findAccountBalance(com.lefu.account.api.bean.request.AccountBalanceQuery)
	 */
	@Override
	public AccountBalanceQueryResponse _findAccountBalance(AccountBalanceQuery accountBalanceQuery) {
		AccountBalanceQueryResponse accountBalanceQueryResponse = null;
		try {
			logger.debug("业务系统调用账务实时账户余额查询接口：{}", accountBalanceQuery);
			accountBalanceQueryResponse = accountService.findAccountBalanceBy(CallType.REAL_TIME,
					JsonUtils.toObject(JsonUtils.toJsonString(accountBalanceQuery), new TypeReference<Map<String, Object>>() {}));
			logger.debug("业务系统调用账务实时账户余额查询接口返回信息：{}", accountBalanceQueryResponse);
		} catch (Exception e) {
			logger.error("-", e);
			if (accountBalanceQueryResponse == null) accountBalanceQueryResponse = new AccountBalanceQueryResponse();
			accountBalanceQueryResponse.setResult(HandlerResult.FAILED);
			accountBalanceQueryResponse.setResultMsg(e.getMessage());
		}
		return accountBalanceQueryResponse;
	}

	/*
	 * (non-Javadoc)
	 * @see com.lefu.account.api.dubbo.AccountRealQueryInterface#_findAccountHistoryBy(java.util.Map)
	 */
	@Override
	public AccountHistoryQueryResponse _findAccountHistoryBy(Map<String, Object> queryParams) {
		AccountHistoryQueryResponse accountHistoryQueryResponse = null;
		if (queryParams == null || queryParams.size() == 0) ;

		logger.debug("业务系统调用账务实时账户历史查询接口，参数：{}", queryParams);

		try {
			accountHistoryQueryResponse = accountRecordedDetailService.findAccountHistoryBy(CallType.REAL_TIME, queryParams);
		} catch (Exception e) {
			logger.error("-", e);
			if (accountHistoryQueryResponse == null) accountHistoryQueryResponse = new AccountHistoryQueryResponse();
			accountHistoryQueryResponse.setResult(HandlerResult.FAILED);
			accountHistoryQueryResponse.setResultMsg(e.getMessage());
		}
		return accountHistoryQueryResponse;
	}

	/*
	 * (non-Javadoc)
	 * @see com.lefu.account.api.dubbo.AccountRealQueryInterface#_findAccountBy(com.lefu.account.api.bean.request.AccountQuery)
	 */
	@Override
	public AccountQueryResponse _findAccountBy(AccountQuery accountQuery) {
		AccountQueryResponse accountQueryResponse = null;
		try {
			logger.debug("业务系统调用账户实时查询接口请求：{}", accountQuery);
			accountQueryResponse = accountService.findAccountBy(CallType.REAL_TIME,
					JsonUtils.toObject(JsonUtils.toJsonString(accountQuery), new TypeReference<Map<String, Object>>() {}));
		} catch (Exception e) {
			logger.error("-", e);
			if (accountQueryResponse == null) accountQueryResponse = new AccountQueryResponse();
			accountQueryResponse.setResult(HandlerResult.FAILED);
		}
		return accountQueryResponse;
	}

	/*
	 * (non-Javadoc)
	 * @see com.lefu.account.api.dubbo.AccountRealQueryInterface#_findAccontByAccountNo(java.lang.String)
	 */
	@Override
	public AccountQueryResponse _findAccontByAccountNo(String accountNo) {
		return accountService.findAccountByAccountNo(CallType.REAL_TIME, accountNo);
	}
}
