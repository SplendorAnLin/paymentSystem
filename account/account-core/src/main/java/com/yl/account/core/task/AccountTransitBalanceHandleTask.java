package com.yl.account.core.task;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lefu.commons.utils.lang.AmountUtils;
import com.lefu.commons.utils.lang.DateUtils;
import com.yl.account.core.service.AccountService;
import com.yl.account.core.service.AccountTransitBalanceDetailService;
import com.yl.account.enums.FundSymbol;
import com.yl.account.model.Account;
import com.yl.account.model.AccountTransitBalanceDetail;

/**
 * 账务在途资金处理
 * 
 * @author 聚合支付有限公司
 * @since 2016年4月12日
 * @version V1.0.0
 */
public class AccountTransitBalanceHandleTask {
	private static Logger logger = LoggerFactory.getLogger(AccountTransitBalanceHandleTask.class);

	private static final int ACCOUNT_ORDER_NUM = 1000;

	@Resource
	private AccountTransitBalanceDetailService accountTransitBalanceDetailService;
	@Resource
	private AccountService accountService;

	public void execute() {
		long startTime = System.currentTimeMillis();
		logger.info("账户在途资金转可用定时批量处理 开始");
		
		String accountDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date())+" 00:00:00";
		List<Account> accountList = accountService.findWaitAbleAccounts(ACCOUNT_ORDER_NUM, accountDate);
		ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(10, 30, 300, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(1000));
		
		for (Account account : accountList) {
			poolExecutor.execute(new AccountTransitBalanceHandleThread(account,accountTransitBalanceDetailService,accountDate,accountService));
		}
		
		poolExecutor.shutdown();
		
		while (!poolExecutor.isTerminated()) {
			try {
				Thread.sleep(300L);
			} catch (InterruptedException e) {
				logger.error("账户在途资金转可用定时批量处理 等待线程结束异常", e);
			}
		}

		logger.info("账户在途资金转可用定时批量处理 结束,耗时 " + (System.currentTimeMillis() - startTime) + "ms");
	}

}
class AccountTransitBalanceHandleThread implements Runnable {
	
	private static Logger logger = LoggerFactory.getLogger(AccountTransitBalanceHandleThread.class);
	
	private AccountTransitBalanceDetailService accountTransitBalanceDetailService;
	private AccountService accountService;
	private Account account;
	private String accountDate;
	
	public AccountTransitBalanceHandleThread(Account account,AccountTransitBalanceDetailService accountTransitBalanceDetailService, 
			String accountDate,AccountService accountService){
		this.account = account;
		this.accountTransitBalanceDetailService = accountTransitBalanceDetailService;
		this.accountDate = accountDate;
		this.accountService = accountService;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@SuppressWarnings("deprecation")
	@Override
	public void run() {
		double waitBalance = 0d;
		List<AccountTransitBalanceDetail> list = accountTransitBalanceDetailService.findTransitBalanceDetailBy(account.getCode(), accountDate);
		for(AccountTransitBalanceDetail balanceDetail : list){
			if(balanceDetail.getSymbol() == FundSymbol.PLUS){
				waitBalance = AmountUtils.add(waitBalance, balanceDetail.getTransAmount());
			}
			if(balanceDetail.getSymbol() == FundSymbol.SUBTRACT){
				waitBalance = AmountUtils.subtract(waitBalance, balanceDetail.getTransAmount());
			}
		}
		
		try {
			Date accDate = new Date();
			accDate.setHours(0);
			accDate.setMinutes(0);
			accDate.setSeconds(0);
			if(waitBalance <= 0d && account.getAbleDate() == accDate){
				return;
			}
			accountService.updateAbleBalance(account.getCode(), waitBalance,accDate);
			logger.info("账户:"+account.getCode()+" 在途资金:"+ waitBalance +"转可用完成");
		} catch (Exception e) {
			logger.error("账户:"+account.getCode()+" 在途资金转可用时出现异常:", e);
		}
	}

}