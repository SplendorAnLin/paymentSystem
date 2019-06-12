package com.yl.account.core.service.impl;

import com.lefu.commons.utils.Page;
import com.lefu.commons.utils.lang.AmountUtils;
import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.account.api.bean.AccountBean;
import com.yl.account.api.bean.request.AccountOpen;
import com.yl.account.api.bean.response.AccountBalanceQueryResponse;
import com.yl.account.api.bean.response.AccountQueryResponse;
import com.yl.account.api.enums.HandlerResult;
import com.yl.account.core.ExceptionMessages;
import com.yl.account.core.dao.AccountDao;
import com.yl.account.core.exception.BussinessException;
import com.yl.account.core.exception.BussinessRuntimeException;
import com.yl.account.core.service.AccountService;
import com.yl.account.core.service.AccountTransitSummaryService;
import com.yl.account.enums.*;
import com.yl.account.enums.Currency;
import com.yl.account.model.Account;
import com.yl.account.model.AccountTransitSummary;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.*;

/**
 * 账务管理业务逻辑处理接口实现
 *
 * @author 聚合支付有限公司
 * @version V1.0.0
 * @since 2016年3月22日
 */
@Service
public class AccountServiceImpl implements AccountService {

    private static final Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);

    @Resource
    private AccountDao accountDao;

    @Resource
    private AccountTransitSummaryService accountTransitSummaryService;

    @Override
    @Transactional
    public void openAccount(AccountOpen accountOpenRequest, String accountNo) {
        Account account = new Account();
        account.setCode(accountNo);
        account.setCurrency(Currency.valueOf(accountOpenRequest.getCurrency().name()));
        account.setStatus(AccountStatus.valueOf(accountOpenRequest.getStatus().name()));
        account.setType(AccountType.valueOf(accountOpenRequest.getAccountType().name()));
        account.setUserNo(accountOpenRequest.getUserNo());
        account.setUserRole(UserRole.valueOf(accountOpenRequest.getUserRole().name()));
        account.setRemark("");
        account.setCycle(accountOpenRequest.getCycle());
        accountDao.create(account);
    }

    @Override
    @Transactional
    public void modifyAccountInfo(Account account, AccountStatus status) {
        account.setStatus(status);
        accountDao.modifyAccountInfo(account);

    }

    @Override
    @Transactional
    public void addValueableAndTransitBalance(Account account, double amount) {
        account.setBalance(AmountUtils.add(account.getBalance(), amount));
        account.setTransitBalance(AmountUtils.add(account.getTransitBalance(), amount));
        if (AmountUtils.less(account.getBalance(), 0) || AmountUtils.less(account.getTransitBalance(), 0))
            throw new BussinessRuntimeException(
                    ExceptionMessages.ACCOUNT_BALANCE_LESS_ZERO);
        accountDao.addValueableAndTransitBalance(account);
    }

    @Override
    public Account findAccountByCode(String accountNo) {
        return accountDao.findAccountByCode(accountNo);
    }

    @Override
    @Transactional
    public void addFreezeBalance(Account account, double valueableBalance) {
        account.setFreezeBalance(AmountUtils.add(account.getFreezeBalance(), valueableBalance));
        accountDao.addOrSubtractFreezeBalance(account);
    }

    @Override
    @Transactional
    public void addOrSubtractValueableBalance(Account account, double amount) {
        account.setBalance(AmountUtils.add(account.getBalance(), amount));
        if (AmountUtils.less(account.getBalance(), 0))
            throw new BussinessRuntimeException(ExceptionMessages.ACCOUNT_BALANCE_LESS_ZERO);
        accountDao.addValueableBalance(account);
    }

    @Override
    @Transactional
    public void subtractTransit(Account account) {
        accountDao.subtractTransit(account);
    }

    @Override
    @Transactional
    public void subtractFreeze(Account freezeAccount, double freezeBalance) {
        freezeAccount.setFreezeBalance(AmountUtils.subtract(freezeAccount.getFreezeBalance(), freezeBalance));
        if (AmountUtils.less(freezeAccount.getFreezeBalance(), 0))
            throw new BussinessRuntimeException(ExceptionMessages.ACCOUNT_BALANCE_LESS_ZERO);
        accountDao.addOrSubtractFreezeBalance(freezeAccount);
    }

    @Override
    public void subtractValueableAndFreezeBalance(Account account, double consultAmount) {
        account.setBalance(AmountUtils.subtract(account.getBalance(), consultAmount));
        account.setFreezeBalance(AmountUtils.subtract(account.getFreezeBalance(), consultAmount));
        if (AmountUtils.less(account.getBalance(), 0) || AmountUtils.less(account.getFreezeBalance(), 0))
            throw new BussinessRuntimeException(
                    ExceptionMessages.ACCOUNT_BALANCE_LESS_ZERO);
        accountDao.subtractValueableAndFreezeBalance(account);
    }

    @Override
    @Transactional
    public void subtractValueableAndTransitBalance(Account account, Double valueableAndTransitBalance) {
        account.setTransitBalance(AmountUtils.subtract(account.getTransitBalance(), valueableAndTransitBalance));
        account.setBalance(AmountUtils.subtract(account.getBalance(), valueableAndTransitBalance));
        if (AmountUtils.less(account.getBalance(), 0) || AmountUtils.less(account.getTransitBalance(), 0))
            throw new BussinessRuntimeException(
                    ExceptionMessages.ACCOUNT_BALANCE_LESS_ZERO);
        accountDao.subtractValueableAndTransitBalance(account);
    }

    @Override
    public Account findAccountBy(String userNo, UserRole userRole, AccountType accountType) {
        return accountDao.findAccountBy(userNo, userRole, accountType);
    }

    @Override
    public List<Map<String, Double>> findAccountBalanceBySum() {
        return accountDao.findAccountBalanceBySum();
    }

    @Override
    public AccountBalanceQueryResponse findAccountBalanceBy(CallType callType, Map<String, Object> accountBalanceQuery) {
        AccountBalanceQueryResponse accountBalanceQueryResponse = null;
        try {
            List<Account> accounts = accountDao.findAccountBy(callType, accountBalanceQuery);
            if (accounts == null || accounts.isEmpty())
                throw new BussinessException(ExceptionMessages.ACCOUNT_NOT_EXISTS);

            AccountTransitSummary accountTransitSummary = accountTransitSummaryService.findTransitSummaryBy(callType, accountBalanceQuery);

            Account account = accounts.get(0);
            accountBalanceQueryResponse = new AccountBalanceQueryResponse();
            accountBalanceQueryResponse.setAccountNo(account.getCode());
            accountBalanceQueryResponse.setStatus(com.yl.account.api.enums.AccountStatus.valueOf(account.getStatus().name()));
            accountBalanceQueryResponse.setResult(HandlerResult.SUCCESS);
            accountBalanceQueryResponse.setBalance(account.getBalance());
            accountBalanceQueryResponse.setFreezeBalance(account.getFreezeBalance());
            accountBalanceQueryResponse.setTransitBalance(account.getTransitBalance());
            double subtractFreezeAmt = AmountUtils.subtract(account.getBalance(), account.getFreezeBalance()); // subtract freeze amount
            double availavleBalance = AmountUtils.subtract(subtractFreezeAmt, account.getTransitBalance()); // subtract transit amount
            accountBalanceQueryResponse.setAvailavleBalance(availavleBalance);
            accountBalanceQueryResponse.setAvailableTransitBalance(AmountUtils.subtract(account.getTransitBalance(),
                    accountTransitSummary != null ? accountTransitSummary.getWaitAccountAmount() : 0));
            accountBalanceQueryResponse.setFinishTime(new Date());

        } catch (BussinessException e) {
            logger.error("{}", e);
            throw new BussinessRuntimeException(ExceptionMessages.DATABASE_OPERATOR_ERROR);
        }

        return accountBalanceQueryResponse;
    }

    @Override
    public AccountQueryResponse findAccountByAccountNo(CallType callType, String accountNo) {
        AccountQueryResponse accountQueryResponse = null;
        try {
            Map<String, Object> accountBalanceQuery = new HashMap<String, Object>();
            accountBalanceQuery.put("accountNo", accountNo);

            List<Account> accounts = accountDao.findAccountBy(callType, accountBalanceQuery);
            if (accounts == null || accounts.isEmpty())
                throw new BussinessException(ExceptionMessages.ACCOUNT_NOT_EXISTS);

            Account account = accounts.get(0);
            accountQueryResponse = new AccountQueryResponse();
            accountQueryResponse.setResult(HandlerResult.SUCCESS);
            accountQueryResponse.setFinishTime(new Date());

            List<AccountBean> accountBeans = new ArrayList<AccountBean>();
            accountBeans.add(JsonUtils.toJsonToObject(account, AccountBean.class));

            accountQueryResponse.setAccountBeans(accountBeans);
        } catch (BussinessException e) {
            logger.error("{}", e);
            throw new BussinessRuntimeException(ExceptionMessages.DATABASE_OPERATOR_ERROR);
        }
        return accountQueryResponse;
    }

    @Override
    public AccountQueryResponse findAccountBy(CallType callType, Map<String, Object> queryParams) {
        AccountQueryResponse accountQueryResponse = null;
        try {
            List<Account> accounts = accountDao.findAccountBy(callType, queryParams);

            accountQueryResponse = new AccountQueryResponse();
            accountQueryResponse.setResult(HandlerResult.SUCCESS);
            accountQueryResponse.setFinishTime(new Date());
            accountQueryResponse.setAccountBeans(JsonUtils.toObject(JsonUtils.toJsonString(accounts), new TypeReference<List<AccountBean>>() {
            }));
        } catch (Exception e) {
            logger.error("{}", e);
            throw new BussinessRuntimeException(ExceptionMessages.DATABASE_OPERATOR_ERROR);
        }
        return accountQueryResponse;
    }

    @Override
    public AccountQueryResponse findAccountByPage(Map<String, Object> queryParams, Page<?> page) {
        AccountQueryResponse accountQueryResponse = null;
        try {
            List<Account> accounts = accountDao.findAccountByPage(queryParams, page);
            accountQueryResponse = new AccountQueryResponse();
            accountQueryResponse.setResult(HandlerResult.SUCCESS);
            accountQueryResponse.setFinishTime(new Date());
            accountQueryResponse.setAccountBeans(JsonUtils.toObject(JsonUtils.toJsonString(accounts), new TypeReference<List<AccountBean>>() {
            }));
            accountQueryResponse.setPage(page);
        } catch (Exception e) {
            logger.error("{}", e);
            throw new BussinessRuntimeException(ExceptionMessages.DATABASE_OPERATOR_ERROR);
        }
        return accountQueryResponse;
    }

    @Override
    public List<Account> findWaitAbleAccounts(int nums, String operDate) {
        return accountDao.findWaitAbleAccounts(nums, operDate);
    }

    @Override
    @Transactional
    public void updateAbleBalance(String accountNo, double amount, Date ableDate) {
        Account account = accountDao.findAccountByCode(accountNo);
        if (account != null) {
            if (account.getTransitBalance() < amount) {
                throw new RuntimeException("账户:" + accountNo + " 待转可用余额:" + amount + " 大于,在途余额:" + account.getTransitBalance());
            }
            account.setTransitBalance(AmountUtils.subtract(account.getTransitBalance(), amount));
            account.setAbleDate(ableDate);
            accountDao.updateAbleAmount(account, ableDate);
        }

    }

    @Override
    public List<Map<String, Object>> findAccTypeBalance() {
        return accountDao.findAccTypeBalance();
    }

}
