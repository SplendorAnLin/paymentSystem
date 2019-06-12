package com.yl.realAuth.core.context;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Spring MVC配置
 * 
 * @author AnLin
 * @since 2016年9月10日
 */
@Configuration
@EnableWebMvc
@Import({ HessianServerConfig.class })
@ComponentScan(basePackages = "com.yl.realAuth.core.Controller", useDefaultFilters = false, includeFilters = @Filter(Controller.class))
public class SpringMVCConfig extends WebMvcConfigurerAdapter {

	/**
	 * 配置资源文件处理
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		super.addResourceHandlers(registry);
	}
	/**
	 * 配置拦截器
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		super.addInterceptors(registry);
	}
}
