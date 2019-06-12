/**
 * 
 */
package com.yl.account.core.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yl.account.core.BaseTest;
import com.yl.account.core.service.AccountService;

 /**
 * 账户业务实现单元测试
 * 
 * @author 聚合支付有限公司
 * @since 2016年3月24日
 * @version V1.0.0
 */
public class AccountServiceImplTest extends BaseTest {

	private static Logger logger = LoggerFactory.getLogger(AccountServiceImplTest.class);

	@Resource
	private AccountService accountService;

	/**
	 * Test method for {@link com.yl.account.core.service.impl.AccountServiceImpl#findAccountBalanceBySum()}.
	 */
	@Test
	public void testFindAccountBalanceBySum() {
		List<Map<String, Double>> map = accountService.findAccountBalanceBySum();
		logger.info("*********** {} *********", map);
	}

}
