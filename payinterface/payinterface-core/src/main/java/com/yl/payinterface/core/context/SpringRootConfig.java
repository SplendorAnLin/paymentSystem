package com.yl.payinterface.core.context;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.stereotype.Controller;

import com.lefu.commons.cache.context.CacheConfig;
import com.yl.mq.producer.client.config.ProducerClientConfig;

/**
 * Spring上下文配置
 * 
 * @author 聚合支付有限公司
 * @since 2016年11月11日
 * @version V1.0.0
 */
@Configuration
@Import({ HessianClientConfig.class, InternetbankTradeConfig.class, RemitTradeConfig.class, AuthPayTradeConfig.class,QuickPayTradeConfig.class, MyBatisConfig.class,
	MariaDBConfig.class,HessianServerConfig.class,WalletPayTradeConfig.class,ReceiveTradeConfig.class,ChannelRecionConfig.class,
	ChannelReverseConfig.class, RealAuthTradeConfig.class, BindCardConfig.class, CacheConfig.class, ProducerClientConfig.class, QuickPayOpenCardConfig.class})
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
