/**
 * 
 */
package com.yl.account.core.task;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lefu.commons.utils.lang.DateUtils;
import com.yl.account.core.BaseTest;
import com.yl.account.core.service.AccountChangeDetailService;
import com.yl.account.model.AccountDay;

/**
 * 日收支表定时工作任务测试单元
 * 
 * @author 聚合支付有限公司
 * @since 2016年3月24日
 * @version V1.0.0
 */
public class AccountDayJobTest extends BaseTest {

	private static Logger logger = LoggerFactory.getLogger(AccountDayJobTest.class);

	@Resource
	private AccountChangeDetailService accountChangeDetailService;

	/**
	 * Test method for {@link com.yl.account.core.task.AccountDayJob#execute()}.
	 */
	@Test
	public void testExecute() {

		Date dailyStart = DateUtils.parseDate("2015-2-13 00:00:00", "yyyy-MM-dd hh:mm:ss");
		Date dailyEnd = DateUtils.parseDate("2015-2-13 23:59:59", "yyyy-MM-dd hh:mm:ss");

		List<Map<String, Object>> dailyFreezeSummarys = accountChangeDetailService.findFreezeBalanceBy(dailyStart, dailyEnd);

		AccountDay accountDay = new AccountDay();
		accountDay.setBusiDate(new Date());

		for (Map<String, Object> iterator : dailyFreezeSummarys) {
			if ("EN_OUT".equals(iterator.get("STATUS"))) accountDay.setDayFreeze(Double.valueOf(String.valueOf(iterator.get("BALANCE"))));
			else accountDay.setDayUnFree(Double.valueOf(String.valueOf(iterator.get("BALANCE"))));
		}

		logger.info("****** AccountDay : {}", accountDay);
	}
}
