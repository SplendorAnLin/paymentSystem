package com.yl.receive.front.context;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

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
 * @since 2016年8月12日
 * @version V1.0.0
 */
@Configuration
@Import({ HessianClientConfig.class, MyBatisConfig.class, MariaDBConfig.class,SpringMVCConfig.class })
@ComponentScan(basePackages = "com.yl.receive.front", excludeFilters = { @Filter(Controller.class), @Filter(Configuration.class) })
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
