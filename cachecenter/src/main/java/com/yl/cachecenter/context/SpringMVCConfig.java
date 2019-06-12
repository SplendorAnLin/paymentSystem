package com.yl.cachecenter.context;

import java.util.List;
import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import org.springframework.web.servlet.handler.SimpleUrlHandlerMapping;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.lefu.commons.web.springmvc.handler.SimpleForwardHttpRequestHandlerForToHTM;
import com.lefu.commons.web.springmvc.interceptor.ExceptionLogInterceptor;

/**
 * Spring MVC配置
 * 
 * @author 聚合支付有限公司
 * @since 2016年9月24日
 * @version V1.0.0
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.yl.cachecenter.web", useDefaultFilters = false, includeFilters = @Filter(Controller.class))
public class SpringMVCConfig extends WebMvcConfigurerAdapter {

	/**
	 * 配置拦截器
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new ExceptionLogInterceptor());
		super.addInterceptors(registry);
	}

	@Bean
	public MultipartResolver multipartResolver() {
		return new CommonsMultipartResolver();
	}

	/**
	 * 视图解析器
	 */
	@Bean
	public InternalResourceViewResolver internalResourceViewResolver() {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/WEB-INF/jsp/");
		resolver.setSuffix(".jsp");
		return resolver;
	}

	@Bean
	public SimpleMappingExceptionResolver simpleMappingExceptionResolver() {
		SimpleMappingExceptionResolver exceptionResolver = new SimpleMappingExceptionResolver();
		exceptionResolver.setDefaultErrorView("error/error");
		return exceptionResolver;
	}

	/**
	 * 配置异常处理器
	 */
	@Override
	public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
		exceptionResolvers.add(simpleMappingExceptionResolver());
		super.configureHandlerExceptionResolvers(exceptionResolvers);
	}

	@Bean
	public SimpleForwardHttpRequestHandlerForToHTM simpleForwardHttpRequestHandlerForToHTM() {
		return new SimpleForwardHttpRequestHandlerForToHTM();
	}

	@Bean
	public SimpleUrlHandlerMapping simpleUrlHandlerMapping() {
		SimpleUrlHandlerMapping simpleUrlHandlerMapping = new SimpleUrlHandlerMapping();
		Properties mappings = new Properties();
		mappings.put("/**", simpleForwardHttpRequestHandlerForToHTM());
		simpleUrlHandlerMapping.setMappings(mappings);
		return simpleUrlHandlerMapping;
	}

}
