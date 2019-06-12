package com.yl.rate.core.test;

import com.yl.rate.core.context.SpringRootConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 测试类的父类，自动加载spring配置文件且运行于事务之下，测试完毕之后不回滚
 *
 * @author 聚合支付有限公司
 * @version V1.0.0
 * @since 2016年11月27日
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SpringRootConfig.class})
@ActiveProfiles("development")
public abstract class BaseTest {
    @Test
    public void testConfig() {

    }
}
