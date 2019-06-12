package com.yl.account.core.dubbo.service;

import com.lefu.commons.utils.lang.DateUtils;
import com.lefu.commons.utils.lang.JsonUtils;
import com.lefu.commons.utils.lang.StringUtils;
import com.yl.account.api.bean.AccountBussinessInterfaceBean;
import com.yl.account.api.bean.request.AccountCompositeTally;
import com.yl.account.api.bean.request.AccountConsult;
import com.yl.account.api.bean.request.AccountPreFreeze;
import com.yl.account.api.bean.request.TallyVoucher;
import com.yl.account.api.bean.response.AccountCompositeTallyResponse;
import com.yl.account.api.bean.response.AccountConsultResponse;
import com.yl.account.api.bean.response.AccountPreFreezeResponse;
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
public class AccountPreFreezeTest extends BaseTest {

    private static Logger logger = LoggerFactory.getLogger(AccountPreFreezeTest.class);

    @Resource
    private AccountInterface accountInterface;

    @Test
    public void testPreFreeze() {
        final Map<String, String> params = new HashMap<>();
        params.put("thread1", null);
        params.put("thread2", null);
        params.put("thread3", null);

        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                params.put("thread1", preFreeze().getFreezeNo());
            }
        });
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                params.put("thread2", preFreeze().getFreezeNo());
            }
        });
        Thread thread3 = new Thread(new Runnable() {
            @Override
            public void run() {
                params.put("thread3", preFreeze().getFreezeNo());
            }
        });
//        Thread thread4 = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                params.put("thread4", testCredit());
//            }
//        });
//
//        thread4.start();
        thread1.start();
        thread2.start();
        thread3.start();

        while (params.get("thread1") == null || params.get("thread2") == null || params.get("thread3") == null
//                || params.get("thread4") == null
                ) {
        }
        System.out.println(params);
    }

    // 1107890.38
    private AccountPreFreezeResponse preFreeze() {
        logger.info("1.【预冻处理】************");
        AccountBussinessInterfaceBean accountBussinessInterfaceBean = new AccountBussinessInterfaceBean();
        accountBussinessInterfaceBean.setBussinessCode("PREFREEZE");
        accountBussinessInterfaceBean.setOperator("TESTER");
        accountBussinessInterfaceBean.setRequestTime(new Date());
        accountBussinessInterfaceBean.setSystemCode("ACCOUNT-CORE");
        accountBussinessInterfaceBean.setSystemFlowId(UUID.randomUUID().toString().substring(18));
        accountBussinessInterfaceBean.setRemark("直清");
        AccountPreFreeze accountPreFreeze = new AccountPreFreeze();
        accountPreFreeze.setAccountNo("1578175946144");
        accountPreFreeze.setFreezeLimit(DateUtils.addHours(new Date(), 1));
        accountPreFreeze.setPreFreezeAmount(10);
        accountBussinessInterfaceBean.setTradeVoucher(accountPreFreeze);

        AccountPreFreezeResponse accountPreFreezeResponse = accountInterface.preFreeze(accountBussinessInterfaceBean);
        logger.info("预冻返回数据：{}", accountPreFreezeResponse);
        return accountPreFreezeResponse;
    }

    @Test
    public void testConsult() throws InterruptedException {
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
                params.put("thread1", consult("d2c-4b90161facb8").getStatus().name());
            }
        });
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                params.put("thread2", consult("2af-12fc7e7fa787").getStatus().name());
            }
        });
        Thread thread3 = new Thread(new Runnable() {
            @Override
            public void run() {
                params.put("thread3", consult("e3a-abfef6ec5779").getStatus().name());
            }
        });
        Thread thread4 = new Thread(new Runnable() {
            @Override
            public void run() {
                params.put("thread4", consult("2d2-66fb188251b4").getStatus().name());
            }
        });
        Thread thread5 = new Thread(new Runnable() {
            @Override
            public void run() {
                params.put("thread5", consult("6fb-e1d57be2808c").getStatus().name());
            }
        });
        Thread thread6 = new Thread(new Runnable() {
            @Override
            public void run() {
                params.put("thread6", consult("43d-db7bdda0b07c").getStatus().name());
            }
        });
        Thread thread7 = new Thread(new Runnable() {
            @Override
            public void run() {
                params.put("thread7", consult("ed4-32c9ae484b9f").getStatus().name());
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
                ){}
        System.out.println(params);
    }

    /**
     * bf0-1262712cbacf
     * bf9-df8c3bdf3c70
     * b69-65c3ae583161
     * d44-5fbb4c3f3112
     * 87e-cee381773c9a
     * 9f1-9062f1f06636
     * 3c1-cb394bde3340
     */
    private AccountConsultResponse consult(String freezeNo){
        logger.info("发起请款操作");
        String uuid = UUID.randomUUID().toString().substring(18);
        System.out.println(uuid);
        AccountBussinessInterfaceBean accountBussinessInterfaceBean = new AccountBussinessInterfaceBean();
        accountBussinessInterfaceBean.setBussinessCode("SETTLE_AA");
        accountBussinessInterfaceBean.setOperator("JOSE");
        accountBussinessInterfaceBean.setRemark("");
        accountBussinessInterfaceBean.setRequestTime(new Date());
        accountBussinessInterfaceBean.setSystemCode("1001");
        accountBussinessInterfaceBean.setSystemFlowId(uuid);

        AccountConsult accountConsult = new AccountConsult();
        accountConsult.setFreezeNo(freezeNo);
        accountConsult.setConsultAmount(10);
        accountBussinessInterfaceBean.setTradeVoucher(accountConsult);

        AccountConsultResponse acp = accountInterface.consult(accountBussinessInterfaceBean);
        logger.info("请款返回结果：{}", JsonUtils.toJsonString(acp));
        return acp;
    }

    public String testCredit() {
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
        amount.setAmount(200);
        amount.setSymbol(FundSymbol.PLUS);
        amount.setCurrency(Currency.RMB);
        amount.setFee(10d);
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
