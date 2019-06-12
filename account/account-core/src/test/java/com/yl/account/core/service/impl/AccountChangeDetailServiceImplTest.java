/**
 * 
 */
package com.yl.account.core.service.impl;

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

/**
 * 账户变更详情业务实现单元测试
 * 
 * @author 聚合支付有限公司
 * @since 2016年3月24日
 * @version V1.0.0
 */
public class AccountChangeDetailServiceImplTest extends BaseTest {

	private Logger logger = LoggerFactory.getLogger(AccountChangeDetailServiceImplTest.class);

	@Resource
	private AccountChangeDetailService accountChangeDetailService;

	/**
	 * Test method for {@link com.yl.account.core.service.impl.AccountChangeDetailServiceImpl#findFreezeBalanceBy(java.util.Date, java.util.Date)}.
	 */
	@Test
	public void testFindFreezeBalanceBy() {
		Date dailyStart = DateUtils.parseDate("2015-2-19 00:00:00", "yyyy-MM-dd hh:mm:ss");
		Date dailyEnd = DateUtils.parseDate("2015-2-19 23:59:59", "yyyy-MM-dd hh:mm:ss");

		List<Map<String, Object>> result = accountChangeDetailService.findFreezeBalanceBy(dailyStart, dailyEnd);
		logger.info("***** {} *****", result);
	}

}
