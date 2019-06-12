/**
 * 
 */
package com.yl.account.core.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yl.account.core.BaseTest;
import com.yl.account.core.service.AccountRecordedDetailService;
import com.yl.account.model.AccountDayDetail;

/**
 * 账户记录详情业务实现单元测试
 * 
 * @author 聚合支付有限公司
 * @since 2016年3月24日
 * @version V1.0.0
 */
public class AccountRecordedDetailServiceImplTest extends BaseTest {

	private static Logger logger = LoggerFactory.getLogger(AccountRecordedDetailServiceImplTest.class);

	@Resource
	private AccountRecordedDetailService accountRecordedDetailService;

	/**
	 * Test method for {@link com.yl.account.core.service.impl.AccountRecordedDetailServiceImpl#findBySum(java.util.Date, java.util.Date)}.
	 */
	@Test
	public void testFindBySum() {
		List<AccountDayDetail> accountDayDetails = accountRecordedDetailService.findBySum(DateUtils.addDays(new Date(), -1), new Date());
		logger.info("******** {} ********", accountDayDetails);
	}

}
