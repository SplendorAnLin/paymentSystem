/**
 *
 */
package com.yl.account.core.service.impl;

import com.yl.account.core.dao.AccountTransitSummaryDao;
import com.yl.account.core.service.AccountTransitSummaryService;
import com.yl.account.core.utils.CodeBuilder;
import com.yl.account.enums.CallType;
import com.yl.account.enums.SummaryStatus;
import com.yl.account.enums.TransitDebitSeq;
import com.yl.account.model.AccountTransitBalanceDetail;
import com.yl.account.model.AccountTransitSummary;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 账务非入账周期汇总业务处理接口实现
 *
 * @author 聚合支付有限公司
 * @version V1.0.0
 * @since 2016年3月22日
 */
@Service
public class AccountTransitSummaryServiceImpl implements AccountTransitSummaryService {

    @Resource
    private AccountTransitSummaryDao accountTransitSummaryDao;

    /*
     * (non-Javadoc)
     * @see com.yl.account.core.service.AccountTransitSummaryService#findTransitSummaryBy(java.lang.String, java.util.Date)
     */
    @Override
    public AccountTransitSummary findTransitSummaryBy(String accountNo, Date waitAccountDate) {
        return accountTransitSummaryDao.findTransitSummaryBy(accountNo, waitAccountDate);
    }

    /*
     * (non-Javadoc)
     * @see com.yl.account.core.service.AccountTransitSummaryService#create(com.yl.account.model.AccountTransitBalanceDetail)
     */
    @Override
    public void create(AccountTransitBalanceDetail accountTransitBalanceDetail) {
        AccountTransitSummary accountTransitSummary = new AccountTransitSummary();
        accountTransitSummary.setCode(CodeBuilder.getOrderIdByUUId());
        accountTransitSummary.setAccountNo(accountTransitBalanceDetail.getAccountNo());
        accountTransitSummary.setStatus(SummaryStatus.SUMMARY_ING);
        accountTransitSummary.setWaitAccountAmount(accountTransitBalanceDetail.getTransAmount());
        accountTransitSummary.setWaitAccountDate(accountTransitBalanceDetail.getWaitAccountDate());
        accountTransitSummaryDao.insert(accountTransitSummary);
    }

    /*
     * (non-Javadoc)
     * @see com.yl.account.core.service.AccountTransitSummaryService#modify(com.yl.account.model.AccountTransitSummary)
     */
    @Override
    public void addOrSubstractWaitAccountAmount(AccountTransitSummary accountTransitSummary) {
        accountTransitSummaryDao.addOrSubstractWaitAccountAmount(accountTransitSummary);
    }

    /*
     * (non-Javadoc)
     * @see com.yl.account.core.service.AccountTransitSummaryService#subtractWaitAccountAmount(com.yl.account.model.AccountTransitSummary)
     */
    @Override
    public void modifySummaryStatus(AccountTransitSummary accountTransitSummary) {
        accountTransitSummary.setStatus(SummaryStatus.SUMMARY_COMPLETE);
        accountTransitSummaryDao.modifySummaryStatus(accountTransitSummary);
    }

    /*
     * (non-Javadoc)
     * @see com.yl.account.core.service.AccountTransitSummaryService#findBatchTransitSummaryBySeq(com.yl.account.enums.TransitDebitSeq)
     */
    @Override
    public List<AccountTransitSummary> findBatchTransitSummaryBySeq(String accountNo, TransitDebitSeq transitDebitSeq) {
        return accountTransitSummaryDao.findBatchTransitSummaryBySeq(accountNo, transitDebitSeq.name());
    }

    @Override
    public void modifySummary(AccountTransitSummary accountTransitSummary) {
        accountTransitSummaryDao.modifySummary(accountTransitSummary);
    }

    /*
     * (non-Javadoc)
     * @see com.yl.account.core.service.AccountTransitSummaryService#findBatchTransitSummaryBy(int, int)
     */
    @Override
    public List<AccountTransitSummary> findBatchTransitSummaryBy(int rowNum, int rowId) {
        return accountTransitSummaryDao.findBatchTransitSummaryBy(rowNum, rowId);
    }

    /*
     * (non-Javadoc)
     * @see com.yl.account.core.service.AccountTransitSummaryService#findTransitSummaryBy(java.lang.String, java.util.Date)
     */
    @Override
    public AccountTransitSummary findTransitSummaryBy(CallType callType, Map<String, Object> accountBalanceQuery) {
        return accountTransitSummaryDao._findTransitSummaryBy(callType, accountBalanceQuery);
    }

}
