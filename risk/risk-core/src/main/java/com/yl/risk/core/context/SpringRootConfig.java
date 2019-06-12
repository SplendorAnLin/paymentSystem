package com.yl.risk.core.context;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.stereotype.Controller;

/**
 * Spring上下文配置
 *
 * @author 聚合支付有限公司
 * @version V1.0.0
 * @since 2017年11月10日
 */
@Configuration
@Import({MariaDBConfig.class, MyBatisConfig.class, RiskConfig.class, HessianClientConfig.class})
@ComponentScan(basePackages = "com", excludeFilters = {@Filter(Controller.class), @Filter(Configuration.class)})
public class SpringRootConfig {

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

}
