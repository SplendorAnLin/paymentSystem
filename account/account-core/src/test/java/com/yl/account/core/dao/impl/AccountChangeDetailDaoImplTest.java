/**
 * 
 */
package com.yl.account.core.dao.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lefu.commons.utils.lang.DateUtils;
import com.yl.account.core.BaseTest;
import com.yl.account.core.dao.AccountChangeDetailDao;

/**
 * 账户变更详情处理实现单元测试
 * 
 * @author 聚合支付有限公司
 * @since 2016年3月24日
 * @version V1.0.0
 */
public class AccountChangeDetailDaoImplTest extends BaseTest {

	private static Logger logger = LoggerFactory.getLogger(AccountChangeDetailDaoImplTest.class);

	@Resource
	private AccountChangeDetailDao accountChangeDetailDao;

	/**
	 * Test method for {@link com.yl.account.core.dao.impl.AccountChangeDetailDaoImpl#findFreezeBalanceBy(java.util.Date, java.util.Date)}.
	 */
	@Test
	public void testFindFreezeBalanceBy() {

		Date dailyStart = DateUtils.parseDate("2015-2-13 00:00:00", "yyyy-MM-dd hh:mm:ss");
		Date dailyEnd = DateUtils.parseDate("2015-2-13 23:59:59", "yyyy-MM-dd hh:mm:ss");

		List<Map<String, Object>> map = accountChangeDetailDao.findFreezeBalanceBy(dailyStart, dailyEnd);
		logger.info("********** {} *********", map);
	}

}
