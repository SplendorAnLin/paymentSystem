package com.yl.chat.context;

import org.springframework.context.annotation.*;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.stereotype.Controller;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

/**
 * Spring上下文配置
 *
 * @author rui
 * @since 2013年8月21日
 */
@Configuration
@Import({HessianClientConfig.class, MariaDBConfig.class, MyBatisConfig.class, HessianServerConfig.class, ConsumerConfig.class})
@ComponentScan(basePackages = "com", excludeFilters = {@Filter(Controller.class), @Filter(Configuration.class)})
@ImportResource({"classpath:/cacheconfig.xml", "classpath:/taskConfig.xml"})
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