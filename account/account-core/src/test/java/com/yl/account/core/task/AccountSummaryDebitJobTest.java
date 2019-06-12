/**
 * 
 */
package com.yl.account.core.task;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yl.account.core.BaseTest;
import com.yl.account.core.service.AccountSummaryBatchCreditService;
import com.yl.account.core.service.AccountTransitSummaryService;
import com.yl.account.core.task.AccountSummaryDebitJob;
import com.yl.account.model.AccountTransitSummary;

/**
 * 非入账周期汇总入账定时单元测试
 * 
 * @author 聚合支付有限公司
 * @since 2016年3月24日
 * @version V1.0.0
 */
public class AccountSummaryDebitJobTest extends BaseTest {

	private static Logger logger = LoggerFactory.getLogger(AccountSummaryDebitJob.class);
	private static final int MAX_THREAD_COUNT = 100;

	/** 账业务处理服务 */
	@Resource
	private AccountSummaryBatchCreditService accountSummaryBatchCreditService;
	/** 账务非入账周期汇总业务处理服务 */
	@Resource
	private AccountTransitSummaryService accountTransitSummaryService;
	/** 汇总线程组 */
	private ThreadGroup accountSummaryThreadGroup = new ThreadGroup("AccountSummaryDebitTG");

	@Test
	public void testExecute() {

		int count = 0;
		int size = 0;
		List<AccountTransitSummary> accountTransitSummaries = null;
		int flag = 0;
		while (count == 0 || size != 0) {

			while (accountSummaryThreadGroup.activeCount() >= MAX_THREAD_COUNT) {
				try {
					Thread.sleep(1000L);
				} catch (InterruptedException e) {
					logger.error("AccountSummaryDebitJob wait thread exception", e);
					throw new RuntimeException();
				}
			}

			accountTransitSummaries = accountTransitSummaryService.findBatchTransitSummaryBy(1000, 0);

			if (accountTransitSummaries == null || accountTransitSummaries.size() == 0) break;
			flag++;
			for (final AccountTransitSummary accountTransitSummary : accountTransitSummaries) {
				logger.info("****************非入账周期汇总信息:{}，次数：{}*****************", accountTransitSummary.getAccountNo(), flag);
				try {
					WorkThread workThread = new WorkThread();
					workThread.setAccountTransitSummary(accountTransitSummary.clone());
					new Thread(accountSummaryThreadGroup, workThread).start();
				} catch (CloneNotSupportedException e) {
					logger.error("{}", e);
				}

			}

			count += accountTransitSummaries.size();
			size = accountTransitSummaries.size();
			accountTransitSummaries = null;

			while (accountSummaryThreadGroup.activeCount() != 0) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					logger.error("AccountSummaryDebitJob wait end exception", e);
				}
			}

		}

		logger.debug("当前非入账周期汇总定时中活动线程数：{}", accountSummaryThreadGroup.activeCount());

		logger.info("非入账周期汇总定时处理汇总数据：{} 次数", count);
	}

	class WorkThread implements Runnable {

		private AccountTransitSummary accountTransitSummary;

		@Override
		public void run() {
			try {
				logger.debug("非入账周期汇总信息:{}", this.accountTransitSummary);
				accountSummaryBatchCreditService.batchSummaryCredit(this.accountTransitSummary);
			} catch (Exception e) {
				logger.error("{}", e);
			}
		}

		public void setAccountTransitSummary(AccountTransitSummary accountTransitSummary) {
			try {
				this.accountTransitSummary = accountTransitSummary.clone();
			} catch (CloneNotSupportedException e) {}
		}
	}

}
