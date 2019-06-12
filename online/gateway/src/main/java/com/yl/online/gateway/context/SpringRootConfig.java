package com.yl.online.gateway.context;

import com.lefu.commons.cache.context.CacheConfig;
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
 * @author 聚合支付有限公司
 * @since 2016年9月2日
 * @version V1.0.0
 */
@Configuration
@Import({ HessianClientConfig.class, TradeConfig.class, QueryConfig.class,MariaDBConfig.class, MyBatisConfig.class, HessianServerConfig.class, SpringMVCConfig.class,
		CacheConfig.class })
@ComponentScan(basePackages = "com", excludeFilters = { @Filter(Controller.class), @Filter(Configuration.class) })
@ImportResource({ "classpath:/taskConfig.xml" })
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
