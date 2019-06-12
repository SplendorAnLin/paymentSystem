package com.yl.online.trade.context;

import javax.annotation.Resource;

import org.junit.Test;
import org.quartz.Scheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yl.online.trade.BaseTest;

/**
 * 定时策略配置测试
 * 
 * @author 聚合支付有限公司
 * @since 2016年8月8日
 * @version V1.0.0
 */
public class QuartzConfigTest extends BaseTest {
	private static final Logger logger = LoggerFactory.getLogger(QuartzConfigTest.class);
	@Resource
	private Scheduler scheduler;

	@Test
	public void testScheduler() throws Exception {
		logger.info(scheduler.toString());
		logger.info(scheduler.getSchedulerInstanceId());
	}

}
