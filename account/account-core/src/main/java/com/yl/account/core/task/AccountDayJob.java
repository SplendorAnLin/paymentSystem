package com.yl.account.core.task;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.lefu.commons.utils.lang.DateUtils;
import com.yl.account.core.service.AccountChangeDetailService;
import com.yl.account.core.service.AccountDayDetailService;
import com.yl.account.core.service.AccountDayService;
import com.yl.account.core.service.AccountRecordedDetailService;
import com.yl.account.model.AccountDay;
import com.yl.account.model.AccountDayDetail;

/**
 * 日收支表定时任务
 * 
 * @author 聚合支付有限公司
 * @since 2016年3月22日
 * @version V1.0.0
 */
@Component
public class AccountDayJob{

	private static Logger logger = LoggerFactory.getLogger(AccountDayJob.class);

	@Resource
	private AccountDayService accountDayService;
	@Resource
	private AccountDayDetailService accountDayDetailService;
	@Resource
	private AccountRecordedDetailService accountRecordedDetailService;
	@Resource
	private AccountChangeDetailService accountChangeDetailService;

	/*
	 * (non-Javadoc)
	 * @see com.lefu.orderliness.proxy.AbstractJob#execute()
	 */
	public void execute() {

		logger.info("*************** AccountDayJob Start ***************");

		Date dailyDate = DateUtils.addDays(new Date(), -1);

		Date dailyStart = DateUtils.getMinTime(dailyDate);
		Date dailyEnd = DateUtils.getMaxTime(dailyDate);

		List<AccountDayDetail> accountDayDetails = accountRecordedDetailService.findBySum(dailyStart, dailyEnd);

		if (accountDayDetails != null && accountDayDetails.isEmpty()) {

			accountDayDetailService.insert(accountDayDetails);

			List<Map<String, Object>> dailyFreezeSummarys = accountChangeDetailService.findFreezeBalanceBy(dailyStart, dailyEnd);

			AccountDay accountDay = new AccountDay();
			accountDay.setBusiDate(dailyDate);

			for (Map<String, Object> iterator : dailyFreezeSummarys) {
				if ("EN_OUT".equals(iterator.get("STATUS"))) accountDay.setDayFreeze(Double.valueOf(String.valueOf(iterator.get("BALANCE"))));
				else accountDay.setDayUnFree(Double.valueOf(String.valueOf(iterator.get("BALANCE"))));
			}

			accountDayService.insert(accountDay);

			logger.info("*************** AccountDayJob Proper End ***************");
		}

		logger.info("*************** AccountDayJob End ***************");
	}
}
