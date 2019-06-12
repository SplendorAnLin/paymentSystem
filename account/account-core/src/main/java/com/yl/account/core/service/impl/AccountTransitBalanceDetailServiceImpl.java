/**
 *
 */
package com.yl.account.core.service.impl;

import com.lefu.commons.utils.lang.AmountUtils;
import com.lefu.commons.utils.lang.StringUtils;
import com.yl.account.core.dao.AccountTransitBalanceDetailDao;
import com.yl.account.core.service.AccountTransitBalanceDetailService;
import com.yl.account.core.utils.AccountFundSymbolUtils;
import com.yl.account.core.utils.CodeBuilder;
import com.yl.account.enums.Currency;
import com.yl.account.enums.FundSymbol;
import com.yl.account.enums.TransitDebitSeq;
import com.yl.account.model.AccountTransitBalanceDetail;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 账务在途资金明细接口实现
 *
 * @author 聚合支付有限公司
 * @version V1.0.0
 * @since 2016年3月22日
 */
@Service
public class AccountTransitBalanceDetailServiceImpl implements AccountTransitBalanceDetailService {

    @Resource
    private AccountTransitBalanceDetailDao accountTransitBalanceDetailDao;

    /*
     * (non-Javadoc)
     * @see com.lefu.account.core.service.AccountTransitBalanceDetailService#create(com.lefu.account.model.AccountTransitBalanceDetail)
     */
    @Override
    public AccountTransitBalanceDetail create(String accountNo, Currency currency, String transOrder, Date transDate, String systemCode, double amount,
                                              FundSymbol symbol, Double fee, FundSymbol feeSymbol, String bussinessCode, Date waitAccountDate) {

        double transAmountFee = 0;
        AccountTransitBalanceDetail accountTransitFinalBalanceDetail = null;
        // persist transit balance detail
        AccountTransitBalanceDetail accountTransitBalanceDetail = new AccountTransitBalanceDetail();
        accountTransitBalanceDetail.setCode(CodeBuilder.build("ATBD", "yyyyMMdd"));
        accountTransitBalanceDetail.setSystemCode(systemCode);
        accountTransitBalanceDetail.setAccountNo(accountNo);
        accountTransitBalanceDetail.setCurrency(currency);
        accountTransitBalanceDetail.setTransFlow(transOrder);
        accountTransitBalanceDetail.setTransAmount(amount);
        accountTransitBalanceDetail.setSymbol(symbol);
        accountTransitBalanceDetail.setBussinessCode(bussinessCode);
        accountTransitBalanceDetail.setWaitAccountDate(waitAccountDate);
        accountTransitBalanceDetailDao.insert(accountTransitBalanceDetail);

        // persist transit fee balance detail
        if (fee != null && feeSymbol != null) {
            AccountTransitBalanceDetail accountTransitFeeBalanceDetail = new AccountTransitBalanceDetail();
            accountTransitFeeBalanceDetail.setCode(CodeBuilder.getOrderIdByUUId());
            accountTransitFeeBalanceDetail.setSystemCode(systemCode);
            accountTransitFeeBalanceDetail.setAccountNo(accountNo);
            accountTransitFeeBalanceDetail.setCurrency(currency);
            accountTransitFeeBalanceDetail.setTransFlow(transOrder);
            accountTransitFeeBalanceDetail.setTransAmount(fee);
            accountTransitFeeBalanceDetail.setSymbol(feeSymbol);
            accountTransitFeeBalanceDetail.setBussinessCode(StringUtils.concatToSB(bussinessCode, "_FEE").toString());
            accountTransitFeeBalanceDetail.setWaitAccountDate(waitAccountDate);
            accountTransitBalanceDetailDao.insert(accountTransitFeeBalanceDetail);
            transAmountFee = AccountFundSymbolUtils.convertToRealAmount(feeSymbol, fee);
        }

        accountTransitFinalBalanceDetail = new AccountTransitBalanceDetail();
        accountTransitFinalBalanceDetail.setAccountNo(accountNo);

        accountTransitFinalBalanceDetail.setTransAmount(AmountUtils.add(amount, transAmountFee));
        accountTransitFinalBalanceDetail.setWaitAccountDate(waitAccountDate);
        return accountTransitFinalBalanceDetail;
    }

    /*
     * (non-Javadoc)
     * @see com.yl.account.core.service.AccountTransitBalanceDetailService#findTransitBalanceDetailBy(java.lang.String, java.lang.String)
     */
    @Override
    public List<AccountTransitBalanceDetail> findTransitBalanceDetailBy(String accountNo, String origTransOrder, String origBussinessCode,
                                                                        String origBussinessCodeFee, TransitDebitSeq transitDebitSeq) {
        return accountTransitBalanceDetailDao.findTransitBalanceDetailBy(accountNo, origTransOrder, origBussinessCode, origBussinessCodeFee, transitDebitSeq);
    }

    /*
     * (non-Javadoc)
     * @see com.yl.account.core.service.AccountTransitBalanceDetailService#modifyWaitAccountDate(com.yl.account.model.AccountTransitBalanceDetail)
     */
    @Override
    public void modifyWaitAccountDate(AccountTransitBalanceDetail originalAccountTransitBalanceDetail) {
        accountTransitBalanceDetailDao.modifyWaitAccountDate(originalAccountTransitBalanceDetail);
    }

    @Override
    public List<AccountTransitBalanceDetail> findTransitBalanceDetailBy(
            String accountNo, String accountDate) {
        return accountTransitBalanceDetailDao.findTransitBalanceDetailBy(accountNo, accountDate);
    }

}
