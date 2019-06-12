package com.yl.trans.front.context;

import com.yl.trans.front.socket.SocketServerConfig;
import com.yl.trans.front.utils.ConfigProperties;
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
 * @since 2016年2月23日
 * @version V1.0.0
 */
@Configuration
@Import({ SocketServerConfig.class, ConfigProperties.class })
@ComponentScan(basePackages = "com.yl.trans.front", excludeFilters = { @Filter(Controller.class), @Filter(Configuration.class) })
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
