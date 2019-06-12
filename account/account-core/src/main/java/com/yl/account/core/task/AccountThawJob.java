/**
 * 
 */
package com.yl.account.core.task;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.lefu.commons.utils.Page;
import com.yl.account.api.bean.AccountBussinessInterfaceBean;
import com.yl.account.api.bean.request.AccountThaw;
import com.yl.account.core.C;
import com.yl.account.core.facade.AccountPrefreezeThawFacade;
import com.yl.account.core.facade.AccountThawFacade;
import com.yl.account.core.service.AccountFreezeDetailService;
import com.yl.account.enums.FreezeStatus;
import com.yl.account.model.AccountFreezeDetail;

/**
 * 账户解冻定时处理
 * 
 * @author 聚合支付有限公司
 * @since 2016年3月22日
 * @version V1.0.0
 */
@Component
@Scope("prototype")
public class AccountThawJob {

	private static Logger logger = LoggerFactory.getLogger(AccountThawJob.class);

	private static final int BATCH_REVERSE_COUNT = 40;

	private static final int MAX_THREAD_COUNT = 5;

	@Resource
	private AccountFreezeDetailService accountFreezeDetailService;
	@Resource
	private AccountThawFacade accountThawFacade;
	@Resource
	private AccountPrefreezeThawFacade accountPrefreezeThawFacade;

	private ThreadGroup accountThawThreadGroup = new ThreadGroup("AccountThawJobTG");

	/*
	 * (non-Javadoc)
	 * @see com.lefu.orderliness.proxy.AbstractJob#execute()
	 */
	public void execute() {
		logger.info("账户解冻定时批量处理冻结信息");
		Page<List<AccountFreezeDetail>> page = new Page<>();
		page.setShowCount(BATCH_REVERSE_COUNT);

		while (accountThawThreadGroup.activeCount() >= MAX_THREAD_COUNT) {
			try {
				Thread.sleep(1000L);
			} catch (InterruptedException e) {
				logger.error("AccountThawJob wait thread exception", e);
				throw new RuntimeException();
			}
		}

		List<AccountFreezeDetail> accountFreezeDetails = accountFreezeDetailService.findAccountFreezeDetailsBy(page);
		for (AccountFreezeDetail accountFreezeDetail : accountFreezeDetails) {

			try {
				AccountThawWorkThread workThread = new AccountThawWorkThread();
				workThread.setAccountFreezeDetail(accountFreezeDetail);
				//XXX
				//workThread.setSystemFlowId(idGeneratorInterface.getNewID(C.ACCOUNT_FREEZE_BALANCE_DETAIL_SYSFLOWID));
				new Thread(accountThawThreadGroup, workThread).start();
			} catch (Exception e) {
				logger.error("-", e);
			}
		}

		while (accountThawThreadGroup.activeCount() != 0) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				logger.error("AccountThawJob wait end exception", e);
			}
		}
	}

	private class AccountThawWorkThread implements Runnable {

		/*
		 * (non-Javadoc)
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			// 冻结状态
			AccountBussinessInterfaceBean accountBussinessInterfaceBean = new AccountBussinessInterfaceBean();
			accountBussinessInterfaceBean.setOperator(C.APP_NAME);
			accountBussinessInterfaceBean.setRequestTime(new Date());
			accountBussinessInterfaceBean.setSystemCode(C.APP_NAME);
			accountBussinessInterfaceBean.setSystemFlowId(this.systemFlowId);

			AccountThaw accountThaw = new AccountThaw();
			accountThaw.setAccountNo(this.accountFreezeDetail.getAccountNo());
			accountThaw.setFreezeNo(this.accountFreezeDetail.getFreezeNo());
			accountBussinessInterfaceBean.setTradeVoucher(accountThaw);

			logger.info("工作线程处理的解冻明细信息：{}", this.accountFreezeDetail);
			if (this.accountFreezeDetail.getStatus().equals(FreezeStatus.FREEZE)) {
				accountBussinessInterfaceBean.setBussinessCode("FREEZE_THAW");
				accountThawFacade.thaw(accountBussinessInterfaceBean);
			} else {
				accountBussinessInterfaceBean.setBussinessCode("PREFREEZE_THAW");
				accountPrefreezeThawFacade.thaw(accountBussinessInterfaceBean);
			}
		}

		private AccountFreezeDetail accountFreezeDetail;

		private String systemFlowId;

		public void setAccountFreezeDetail(AccountFreezeDetail accountFreezeDetail) {
			this.accountFreezeDetail = accountFreezeDetail;
		}

	}

}
