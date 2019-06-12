/**
 *
 */
package com.yl.account.core.service.impl;

import com.lefu.commons.utils.Page;
import com.lefu.commons.utils.lang.AmountUtils;
import com.lefu.commons.utils.lang.JsonUtils;
import com.lefu.commons.utils.lang.StringUtils;
import com.yl.account.api.bean.AccountBean;
import com.yl.account.api.bean.AccountRecordedDetailBean;
import com.yl.account.api.bean.response.AccountHistoryQueryResponse;
import com.yl.account.api.bean.response.AccountQueryResponse;
import com.yl.account.api.bean.response.AccountSummaryResponse;
import com.yl.account.api.enums.HandlerResult;
import com.yl.account.core.dao.AccountRecordedDetailDao;
import com.yl.account.core.service.AccountRecordedDetailService;
import com.yl.account.core.utils.AccountFundSymbolUtils;
import com.yl.account.core.utils.CodeBuilder;
import com.yl.account.enums.CallType;
import com.yl.account.enums.Currency;
import com.yl.account.enums.FundSymbol;
import com.yl.account.enums.UserRole;
import com.yl.account.model.AccountDayDetail;
import com.yl.account.model.AccountRecordedDetail;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 账务入账明细业务逻辑处理接口实现
 *
 * @author 聚合支付有限公司
 * @version V1.0.0
 * @since 2016年5月14日
 */
@Service
public class AccountRecordedDetailServiceImpl implements AccountRecordedDetailService {

    @Resource
    private AccountRecordedDetailDao accountRecordedDetailDao;

    /*
     * (non-Javadoc)
     * @see com.yl.account.core.service.AccountRecordedDetailService#createDebit(java.lang.String, com.yl.account.enums.Currency, java.lang.String,
     * java.util.Date, java.lang.String, double, com.yl.account.enums.FundSymbol, java.lang.Double, com.yl.account.enums.FundSymbol)
     */
    @Override
    public void create(String accountNo, String userNo, UserRole userRole, String systemFlowId, String bussinessCode, Currency currency, String transOrder,
                       Date transDate, String systemCode, Date waitAccountDate, double amount, FundSymbol symbol, Double fee, FundSymbol feeSymbol, double remainBalance) {
        AccountRecordedDetail accountRecordedDetail = new AccountRecordedDetail();
        accountRecordedDetail.setCode(CodeBuilder.build("ARD", "yyyyMMdd"));
        accountRecordedDetail.setUserNo(userNo);
        accountRecordedDetail.setUserRole(userRole);
        accountRecordedDetail.setSystemFlowId(systemFlowId);
        accountRecordedDetail.setBussinessCode(bussinessCode);
        accountRecordedDetail.setAccountNo(accountNo);
        accountRecordedDetail.setCurrency(currency);
        accountRecordedDetail.setSymbol(symbol);
        accountRecordedDetail.setSystemCode(systemCode);
        accountRecordedDetail.setWaitAccountDate(waitAccountDate);
        accountRecordedDetail.setTransAmount(amount);
        accountRecordedDetail.setTransFlow(transOrder);
        accountRecordedDetail.setTransTime(transDate);
        remainBalance = AmountUtils.add(AccountFundSymbolUtils.convertToRealAmount(symbol, amount), remainBalance);
        accountRecordedDetail.setRemainBalance(remainBalance);
        accountRecordedDetailDao.create(accountRecordedDetail);

        if (fee != null && feeSymbol != null) {
            AccountRecordedDetail accountFeeRecordedDetail = new AccountRecordedDetail();
            accountFeeRecordedDetail.setCode(CodeBuilder.getOrderIdByUUId());
            accountFeeRecordedDetail.setUserNo(userNo);
            accountFeeRecordedDetail.setUserRole(userRole);
            accountFeeRecordedDetail.setSystemFlowId(systemFlowId);
            accountFeeRecordedDetail.setBussinessCode(StringUtils.concatToSB(bussinessCode, "_FEE").toString());
            accountFeeRecordedDetail.setAccountNo(accountNo);
            accountFeeRecordedDetail.setCurrency(currency);
            accountFeeRecordedDetail.setSymbol(feeSymbol);
            accountFeeRecordedDetail.setSystemCode(systemCode);
            accountFeeRecordedDetail.setWaitAccountDate(waitAccountDate);
            accountFeeRecordedDetail.setTransAmount(fee);
            accountFeeRecordedDetail.setTransFlow(transOrder);
            accountFeeRecordedDetail.setTransTime(transDate);
            accountFeeRecordedDetail.setRemainBalance(AmountUtils.add(AccountFundSymbolUtils.convertToRealAmount(feeSymbol, fee), remainBalance));
            accountRecordedDetailDao.create(accountFeeRecordedDetail);

        }
    }

    /*
     * (non-Javadoc)
     * @see com.yl.account.core.service.AccountRecordedDetailService#findBy(java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public boolean findBy(String accountNo, String transOrder, String systemCode, String bussinessCode, String systemFlowId) {
        return accountRecordedDetailDao.findBy(accountNo, transOrder, systemCode, bussinessCode, systemFlowId) > 0;
    }

    /*
     * (non-Javadoc)
     * @see com.yl.account.core.service.AccountRecordedDetailService#findBySum(com.yl.account.model.AccountRecordedDetail)
     */
    @Override
    public List<AccountDayDetail> findBySum(Date dailyStart, Date dailyEnd) {
        return accountRecordedDetailDao.findBySum(dailyStart, dailyEnd);
    }

    /*
     * (non-Javadoc)
     * @see com.yl.account.query.service.AccountRecordedDetailService#findAccountHistoryBy(java.util.Map)
     */
    @Override
    public AccountHistoryQueryResponse findAccountHistoryBy(Map<String, Object> accountHistoryQueryParams, Page<?> page) {
        List<AccountRecordedDetail> accountRecordedDetails = accountRecordedDetailDao.findAccountHistoryBy(accountHistoryQueryParams, page);

        AccountHistoryQueryResponse accountHistoryQueryResponse = new AccountHistoryQueryResponse();
        accountHistoryQueryResponse.setAccountRecordedDetailBeans(JsonUtils.toObject(JsonUtils.toJsonString(accountRecordedDetails),
                new TypeReference<List<AccountRecordedDetailBean>>() {
                }));
        accountHistoryQueryResponse.setPage(page);
        accountHistoryQueryResponse.setResult(HandlerResult.SUCCESS);
        accountHistoryQueryResponse.setFinishTime(new Date());
        return accountHistoryQueryResponse;
    }

    /*
     * (non-Javadoc)
     * @see com.yl.account.query.service.AccountRecordedDetailService#findAccountSummaryBy(java.util.Map)
     */
    @Override
    public AccountSummaryResponse findAccountSummaryBy(Map<String, Object> queryParams) {
        List<Object> returnValue = accountRecordedDetailDao.findAccountSummaryBy(queryParams);

        AccountSummaryResponse accountSummaryResponse = new AccountSummaryResponse();
        accountSummaryResponse.setResult(HandlerResult.SUCCESS);
        accountSummaryResponse.setFinishTime(new Date());

        for (Object summary : returnValue) {

            Map<String, Object> summaryMap = JsonUtils.toObject(JsonUtils.toJsonString(summary), new TypeReference<Map<String, Object>>() {
            });

            if (FundSymbol.PLUS.name().equals(summaryMap.get("fundSymbol"))) {
                accountSummaryResponse.setRaiseTotalBalance(Double.valueOf(summaryMap.get("summaryBalance").toString()));
                accountSummaryResponse.setRaiseTotalCount(Integer.valueOf(summaryMap.get("summaryCount").toString()));
            } else {
                accountSummaryResponse.setSubtractTotalBalance(Double.valueOf(summaryMap.get("summaryBalance").toString()));
                accountSummaryResponse.setSubtractTotalCount(Integer.valueOf(summaryMap.get("summaryCount").toString()));
            }

        }
        return accountSummaryResponse;
    }

    /*
     * (non-Javadoc)
     * @see com.lefu.accAccountChangeDetailount.query.service.AccountService#findAccountBySystemFlowId(java.lang.String)
     */
    @Override
    public AccountQueryResponse findAccountByTransOrder(String systemCode, String transOrder) {
        List<AccountBean> accounts = null;
        AccountRecordedDetail accountRecordedDetail = accountRecordedDetailDao.findAccountByTransOrder(systemCode, transOrder).get(0);
        if (accountRecordedDetail != null) {
            String accountNo = accountRecordedDetail.getAccountNo();
            AccountBean account = new AccountBean();
            account.setCode(accountNo);
            accounts = new ArrayList<AccountBean>();
            accounts.add(account);
        }

        AccountQueryResponse accountQueryResponse = new AccountQueryResponse();
        accountQueryResponse.setResult(HandlerResult.SUCCESS);
        accountQueryResponse.setFinishTime(new Date());
        accountQueryResponse.setAccountBeans(accounts);
        return accountQueryResponse;
    }

    /*
     * (non-Javadoc)
     * @see com.yl.account.query.service.AccountRecordedDetailService#findAccountHistoryBy(com.yl.account.enums.CallType, java.util.Map)
     */
    @Override
    public AccountHistoryQueryResponse findAccountHistoryBy(CallType realTime, Map<String, Object> queryParams) {

        List<AccountRecordedDetail> accountRecordedDetails = accountRecordedDetailDao._findAccountHistoryBy(realTime, queryParams);

        AccountHistoryQueryResponse accountHistoryQueryResponse = new AccountHistoryQueryResponse();
        accountHistoryQueryResponse.setAccountRecordedDetailBeans(JsonUtils.toObject(JsonUtils.toJsonString(accountRecordedDetails),
                new TypeReference<List<AccountRecordedDetailBean>>() {
                }));
        accountHistoryQueryResponse.setResult(HandlerResult.SUCCESS);
        accountHistoryQueryResponse.setFinishTime(new Date());

        return accountHistoryQueryResponse;
    }

    @Override
    public List<Map<String, Object>> queryAccountHistory(Map<String, Object> params) {

        return accountRecordedDetailDao._queryAccountHistorBy(params);
    }


    /*
     * (non-Javadoc)
     * @see com.yl.account.query.service.AccountRecordedDetailService#findAccountHistoryBy()
     */
    @Override
    public List<Map<String, Object>> findAccountHistoryBy(int currentResutl, int rowNum) {
        return accountRecordedDetailDao.findAccountHistoryBy(currentResutl, rowNum);
    }

    @Override
    public AccountHistoryQueryResponse findAccountHistoryExportBy(Map<String, Object> accountHistoryQueryParams) {
        List<AccountRecordedDetail> accountRecordedDetails = accountRecordedDetailDao.findAccountHistoryExportBy(accountHistoryQueryParams);

        AccountHistoryQueryResponse accountHistoryQueryResponse = new AccountHistoryQueryResponse();
        accountHistoryQueryResponse.setAccountRecordedDetailBeans(JsonUtils.toObject(JsonUtils.toJsonString(accountRecordedDetails),
                new TypeReference<List<AccountRecordedDetailBean>>() {
                }));
        accountHistoryQueryResponse.setResult(HandlerResult.SUCCESS);
        accountHistoryQueryResponse.setFinishTime(new Date());
        return accountHistoryQueryResponse;
    }

    @Override
    public List<AccountRecordedDetail> findAccountHistory(Map<String, Object> accountHistoryQueryParams) {
        return accountRecordedDetailDao.findAccountHistory(accountHistoryQueryParams);
    }

    @Override
    public Map<String, Object> findAccountHistorySum(Map<String, Object> accountHistoryQueryParams) {
        return accountRecordedDetailDao.findAccountHistorySum(accountHistoryQueryParams);
    }
}