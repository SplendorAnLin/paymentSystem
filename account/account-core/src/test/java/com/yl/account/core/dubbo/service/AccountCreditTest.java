package com.yl.account.core.dubbo.service;

import com.lefu.commons.utils.lang.DateUtils;
import com.lefu.commons.utils.lang.StringUtils;
import com.yl.account.api.bean.AccountBussinessInterfaceBean;
import com.yl.account.api.bean.request.AccountCompositeTally;
import com.yl.account.api.bean.request.TallyVoucher;
import com.yl.account.api.bean.response.AccountCompositeTallyResponse;
import com.yl.account.api.dubbo.AccountInterface;
import com.yl.account.api.enums.Currency;
import com.yl.account.api.enums.FundSymbol;
import com.yl.account.core.BaseTest;
import com.yl.account.core.utils.CodeBuilder;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author AnLin
 * @version V1.0.0
 * @since 2018/1/22
 */
public class AccountCreditTest extends BaseTest {

    private static Logger logger = LoggerFactory.getLogger(AccountCreditTest.class);

    @Resource
    private AccountInterface accountInterface;

    @Test
    public void testCredit() {
        final Map<String, String> params = new HashMap<>();
        params.put("thread1", null);
        params.put("thread2", null);
        params.put("thread3", null);
        params.put("thread4", null);
        params.put("thread5", null);
        params.put("thread6", null);
        params.put("thread7", null);

        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                params.put("thread1", credit());
            }
        });
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                params.put("thread2", credit());
            }
        });
        Thread thread3 = new Thread(new Runnable() {
            @Override
            public void run() {
                params.put("thread3", credit());
            }
        });
        Thread thread4 = new Thread(new Runnable() {
            @Override
            public void run() {
                params.put("thread4", credit());
            }
        });
        Thread thread5 = new Thread(new Runnable() {
            @Override
            public void run() {
                params.put("thread5", credit());
            }
        });
        Thread thread6 = new Thread(new Runnable() {
            @Override
            public void run() {
                params.put("thread6", credit());
            }
        });
        Thread thread7 = new Thread(new Runnable() {
            @Override
            public void run() {
                params.put("thread7", credit());
            }
        });

        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
        thread5.start();
        thread6.start();
        thread7.start();

        while (params.get("thread1") == null || params.get("thread2") == null || params.get("thread3") == null
                || params.get("thread4") == null || params.get("thread5") == null || params.get("thread6") == null ||
                params.get("thread7") == null
                ) {
        }
        System.out.println(params);
    }

    public String credit() {
        logger.info("调用账务入账接口【入可用余额】");
        AccountBussinessInterfaceBean accountBussinessInterfaceBean = new AccountBussinessInterfaceBean();
        accountBussinessInterfaceBean.setBussinessCode("ONLINE_CREDIT");
        accountBussinessInterfaceBean.setOperator("JOSE");
        accountBussinessInterfaceBean.setRemark("");
        accountBussinessInterfaceBean.setRequestTime(new Date());
        accountBussinessInterfaceBean.setSystemCode("1001");
        accountBussinessInterfaceBean.setSystemFlowId(UUID.randomUUID().toString().substring(18));

        AccountCompositeTally accountCompositeTally = new AccountCompositeTally();

        // 交易金额
        TallyVoucher amount = new TallyVoucher();
        amount.setAccountNo("1578175946144");
        amount.setAmount(2);
        amount.setSymbol(FundSymbol.PLUS);
        amount.setCurrency(Currency.RMB);
        amount.setFee(1d);
        amount.setFeeSymbol(FundSymbol.SUBTRACT);
        amount.setTransDate(new Date());
        amount.setTransOrder(CodeBuilder.getOrderIdByUUId());
        amount.setWaitAccountDate(DateUtils.addDays(new Date(), 0));

        List<TallyVoucher> tallyVouchers = new ArrayList<TallyVoucher>();
        tallyVouchers.add(amount);

        accountCompositeTally.setTallyVouchers(tallyVouchers);
        accountBussinessInterfaceBean.setTradeVoucher(accountCompositeTally);

        AccountCompositeTallyResponse accountCompositeTallyResponse = accountInterface.standardCompositeTally(accountBussinessInterfaceBean);
        logger.info("【入账返回接口信息】：{}", StringUtils.concatToSB(accountCompositeTallyResponse.getResult().name(), "-", accountCompositeTallyResponse.getStatus().name())
                .toString());
        return accountCompositeTallyResponse.getResult().name();
    }

}
