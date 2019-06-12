package com.yl.rate.core.context;

import com.lefu.commons.cache.context.CacheConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.stereotype.Controller;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

/**
 * Spring上下文配置
 *
 * @author 聚合支付有限公司
 * @version V1.0.0
 * @since 2016年5月11日
 */
@Configuration
@Import({MariaDBConfig.class, HessianServerConfig.class, MyBatisConfig.class, CacheConfig.class})
@ComponentScan(basePackages = "com.yl", excludeFilters = {@Filter(Controller.class), @Filter(Configuration.class)})
public class SpringRootConfig {

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public Validator validator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        return factory.getValidator();
    }
}
