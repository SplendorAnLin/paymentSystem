/**
 *
 */
package com.yl.account.core.service.impl;

import com.lefu.commons.utils.lang.AmountUtils;
import com.yl.account.core.C;
import com.yl.account.core.ExceptionMessages;
import com.yl.account.core.exception.BussinessException;
import com.yl.account.core.exception.BussinessRuntimeException;
import com.yl.account.core.service.*;
import com.yl.account.core.utils.AccountFundSymbolUtils;
import com.yl.account.core.utils.CodeBuilder;
import com.yl.account.enums.*;
import com.yl.account.model.Account;
import com.yl.account.model.AccountBehavior;
import com.yl.account.model.AccountFreezeDetail;
import com.yl.account.model.AccountTransitSummary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 批量汇总入账处理接口实现
 *
 * @author 聚合支付有限公司
 * @version V1.0.0
 * @since 2016年3月22日
 */
@Service
public class AccountSummaryBatchCreditServiceImpl implements AccountSummaryBatchCreditService {

    private static Logger logger = LoggerFactory.getLogger(AccountSummaryBatchCreditServiceImpl.class);

    @Resource
    private AccountTransitSummaryService accountTransitSummaryService;
    @Resource
    private AccountService accountService;
    @Resource
    private AccountTransitBalanceDetailService accountTransitBalanceDetailService;
    @Resource
    private AccountBehaviorService accountBehaviorService;
    @Resource
    private AccountFreezeDetailService accountFreezeDetailService;
    @Resource
    private AccountFreezeBalanceDetailService accountFreezeBalanceDetailService;

    /*
     * (non-Javadoc)
     * @see com.yl.account.core.service.impl.AccountSummaryBatchCreditService#execute()
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = BussinessRuntimeException.class)
    public void batchSummaryCredit(AccountTransitSummary accountTransitSummary) {
        try {
            Account account = accountService.findAccountByCode(accountTransitSummary.getAccountNo());
            if (account == null) throw new BussinessException(ExceptionMessages.ACCOUNT_NOT_EXISTS);
            if (account.getStatus().equals(AccountStatus.FREEZE) || account.getStatus().equals(AccountStatus.END_IN))
                throw new BussinessException(
                        ExceptionMessages.ACCOUNT_STATUS_NOT_NORMAL);
            accountTransitSummaryService.modifySummaryStatus(accountTransitSummary);

            accountTransitBalanceDetailService.create(accountTransitSummary.getAccountNo(), Currency.RMB, accountTransitSummary.getCode(), new Date(), C.APP_NAME,
                    accountTransitSummary.getWaitAccountAmount(), FundSymbol.SUBTRACT, null, null, "BATCH_SUMMARY_CREDIT",
                    accountTransitSummary.getWaitAccountDate());

            account.setTransitBalance(AmountUtils.add(account.getTransitBalance(),
                    AccountFundSymbolUtils.convertToRealAmount(FundSymbol.SUBTRACT, accountTransitSummary.getWaitAccountAmount())));

            accountService.subtractTransit(account);

            AccountBehavior accountBehavior = accountBehaviorService.findAccountBehavior(account.getCode());
            if (accountBehavior != null && accountBehavior.getPreFreezeCount() > 0) {
                double creditAmount = accountTransitSummary.getWaitAccountAmount();

                List<AccountFreezeDetail> accountFreezeDetails = accountFreezeDetailService
                        .findAccountFreezeDetailBy(account.getCode(), FreezeStatus.PRE_FREEZE_ING);

                String transOrder = CodeBuilder.getOrderIdByUUId();
                for (AccountFreezeDetail accountFreezeDetail : accountFreezeDetails) {
                    // 需预冻金额
                    double remainPreFreezeBalance = AmountUtils.subtract(accountFreezeDetail.getPreFreezeBalance(), accountFreezeDetail.getFreezeBalance());
                    if (creditAmount < remainPreFreezeBalance) {
                        accountFreezeBalanceDetailService.create(C.APP_NAME, account.getCode(), transOrder, creditAmount, null, FreezeHandleType.PRE_FREEZE.name());
                        accountFreezeDetailService.addFreezeBalance(accountFreezeDetail, creditAmount);
                        accountService.addFreezeBalance(account, creditAmount);
                        break;
                    } else {
                        // 可入账户金额
                        double valueableCreditBalance = AmountUtils.subtract(creditAmount, remainPreFreezeBalance);
                        accountFreezeBalanceDetailService.create(C.APP_NAME, account.getCode(), transOrder, remainPreFreezeBalance, null,
                                FreezeHandleType.PRE_FREEZE.name());
                        accountFreezeDetailService.preFreezeComplete(accountFreezeDetail, remainPreFreezeBalance);
                        accountBehavior.setPreFreezeCount(accountBehavior.getPreFreezeCount() - 1);
                        accountBehaviorService.addOrSubtractPreFreezeCount(accountBehavior);
                        accountService.addFreezeBalance(account, remainPreFreezeBalance);
                        creditAmount = valueableCreditBalance;
                    }
                }
            }
        } catch (Exception e) {
            logger.error("{}", e);
            throw new BussinessRuntimeException(e.getMessage());
        }
    }
}
