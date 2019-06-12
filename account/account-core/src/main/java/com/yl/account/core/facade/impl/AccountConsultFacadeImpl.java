/**
 *
 */
package com.yl.account.core.facade.impl;

import com.lefu.commons.utils.lang.AmountUtils;
import com.lefu.commons.utils.lang.JsonUtils;
import com.yl.account.api.bean.AccountBussinessInterfaceBean;
import com.yl.account.api.bean.response.AccountConsultResponse;
import com.yl.account.api.enums.HandlerResult;
import com.yl.account.api.enums.Status;
import com.yl.account.bean.AccountConsult;
import com.yl.account.core.ExceptionMessages;
import com.yl.account.core.exception.BussinessException;
import com.yl.account.core.exception.BussinessRuntimeException;
import com.yl.account.core.facade.AccountConsultFacade;
import com.yl.account.core.service.*;
import com.yl.account.enums.AccountStatus;
import com.yl.account.enums.Currency;
import com.yl.account.enums.FreezeStatus;
import com.yl.account.enums.FundSymbol;
import com.yl.account.model.Account;
import com.yl.account.model.AccountFreezeDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 请款处理接口实现
 *
 * @author 聚合支付有限公司
 * @version V1.0.0
 * @since 2016年4月9日
 */
@Component
public class AccountConsultFacadeImpl implements AccountConsultFacade {

    private static Logger logger = LoggerFactory.getLogger(AccountConsultFacadeImpl.class);

    @Resource
    private AccountService accountService;
    @Resource
    private AccountRecordedDetailService accountRecordedDetailService;
    @Resource
    private AccountFreezeDetailService accountFreezeDetailService;
    @Resource
    private AccountChangeDetailService accountChangeDetailService;
    @Resource
    private AccountFreezeBalanceDetailService accountFreezeBalanceDetailService;

    /*
     * (non-Javadoc)
     * @see com.yl.account.core.facade.AccountConsultFacade#consult(com.yl.account.api.bean.AccountBussinessInterfaceBean)
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = BussinessRuntimeException.class)
    @Override
    public AccountConsultResponse consult(AccountBussinessInterfaceBean accountBussinessInterfaceBean) {
        AccountConsultResponse accountConsultResponse = null;
        try {
            AccountConsult accountConsult = JsonUtils.toJsonToObject(accountBussinessInterfaceBean.getTradeVoucher(), AccountConsult.class);

            logger.info("账务系统接收业务系统[请款]指令：{}", accountConsult);

            AccountFreezeDetail accountFreezeDetail = accountFreezeDetailService.findAccountFreezeDetailByFreezeNo(accountConsult.getFreezeNo());
            if (accountFreezeDetail == null)
                throw new BussinessException(ExceptionMessages.ACCOUNT_FREEZE_DETAI_NOT_EXISTS);
            if (accountFreezeDetail.getStatus().equals(FreezeStatus.UNFREEZE))
                throw new BussinessException(ExceptionMessages.ACCOUNT_STATUS_HAS_BEEN_THAW);

            if (accountFreezeBalanceDetailService.findBy(accountBussinessInterfaceBean.getSystemCode(), accountBussinessInterfaceBean.getSystemFlowId(),
                    accountBussinessInterfaceBean.getBussinessCode(), accountConsult.getConsultAmount())) {

                accountConsultResponse = new AccountConsultResponse();
                accountConsultResponse.setResult(HandlerResult.SUCCESS);
                accountConsultResponse.setStatus(Status.SUCCESS);
                accountConsultResponse.setRemainAmount(AmountUtils.subtract(accountFreezeDetail.getFreezeBalance(), accountFreezeDetail.getRequestBalance()));
                accountConsultResponse.setFinishTime(new Date());
                return accountConsultResponse;
            }

            double valueableConsultBalance = 0;
            double requestBalance = accountFreezeDetail.getRequestBalance() == null ? 0 : accountFreezeDetail.getRequestBalance();

            if (accountFreezeDetail.equals(FreezeStatus.FREEZE))
                valueableConsultBalance = accountFreezeDetail.getFreezeBalance(); // 冻结
            else
                valueableConsultBalance = AmountUtils.subtract(accountFreezeDetail.getFreezeBalance(), requestBalance); // 预冻

            if (accountConsult.getConsultAmount() > valueableConsultBalance) throw new BussinessException(
                    ExceptionMessages.ACCOUNT_CONSULT_AMOUNT_GREATETHAN_PREFREEZE_AMOUNT);

            accountFreezeBalanceDetailService.create(accountBussinessInterfaceBean.getSystemCode(), accountFreezeDetail.getAccountNo(),
                    accountBussinessInterfaceBean.getSystemFlowId(), accountConsult.getConsultAmount(), null, accountBussinessInterfaceBean.getBussinessCode());

            accountFreezeDetail.setRequestBalance(AmountUtils.add(requestBalance, accountConsult.getConsultAmount()));
            accountFreezeDetail.setRequestBalanceCount(accountFreezeDetail.getRequestBalanceCount() == null ? 1 : accountFreezeDetail.getRequestBalanceCount() + 1);

            if ((accountFreezeDetail.getStatus().equals(FreezeStatus.PRE_FREEZE_COMPLETE) && AmountUtils.eq(accountFreezeDetail.getRequestBalance(),
                    accountFreezeDetail.getPreFreezeBalance()))
                    || (accountFreezeDetail.getStatus().equals(FreezeStatus.FREEZE) && AmountUtils.eq(accountFreezeDetail.getRequestBalance(),
                    accountFreezeDetail.getFreezeBalance()))) {
                accountFreezeDetail.setStatus(FreezeStatus.UNFREEZE);
            }
            accountFreezeDetailService.addConsultBalance(accountFreezeDetail);

            Account account = accountService.findAccountByCode(accountFreezeDetail.getAccountNo());
            if (account == null) throw new BussinessException(ExceptionMessages.ACCOUNT_NOT_EXISTS);
            if (account.getStatus().equals(AccountStatus.FREEZE) || accountBussinessInterfaceBean.getBussinessCode().equals("ADJUST") ? false : account.getStatus()
                    .equals(AccountStatus.EN_OUT))
                throw new BussinessException(ExceptionMessages.ACCOUNT_STATUS_NOT_NORMAL);

            accountChangeDetailService.create(accountBussinessInterfaceBean.getBussinessCode(), new Date(), account.getCode(), null, null,
                    accountBussinessInterfaceBean.getSystemCode(), accountBussinessInterfaceBean.getSystemFlowId(), account.getUserNo(), account.getUserRole(),
                    account.getBalance(), account.getFreezeBalance(), account.getTransitBalance(), FundSymbol.SUBTRACT, accountConsult.getConsultAmount(),
                    accountBussinessInterfaceBean.getOperator(), accountBussinessInterfaceBean.getRemark());

            accountRecordedDetailService.create(account.getCode(), account.getUserNo(), account.getUserRole(), accountBussinessInterfaceBean.getSystemFlowId(),
                    accountBussinessInterfaceBean.getBussinessCode(), Currency.RMB, accountBussinessInterfaceBean.getSystemFlowId(), new Date(),
                    accountBussinessInterfaceBean.getSystemCode(), new Date(), accountConsult.getConsultAmount(), FundSymbol.SUBTRACT, null, null, account.getBalance());

            accountService.subtractValueableAndFreezeBalance(account, accountConsult.getConsultAmount());

            accountConsultResponse = new AccountConsultResponse();
            accountConsultResponse.setResult(HandlerResult.SUCCESS);
            accountConsultResponse.setStatus(Status.SUCCESS);
            accountConsultResponse.setRemainAmount(AmountUtils.subtract(valueableConsultBalance, accountConsult.getConsultAmount()));
            accountConsultResponse.setFinishTime(new Date());
        } catch (Exception e) {
            logger.error("{}", e);
            throw new BussinessRuntimeException(e.getMessage());
        }

        return accountConsultResponse;
    }
}
