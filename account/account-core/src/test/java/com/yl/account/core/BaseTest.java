package com.yl.account.core;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.yl.account.core.context.SpringRootConfig;

/**
 * 测试类的父类，自动加载spring配置文件且运行于事务之下，测试完毕之后不回滚
 * 
 * @author 聚合支付有限公司
 * @since 2016年3月24日
 * @version V1.0.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { SpringRootConfig.class })
@ActiveProfiles("development")
@TransactionConfiguration(defaultRollback = false)
public class BaseTest {

	@Test
	public void testConfig() {

	}
}
