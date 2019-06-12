package com.yl.account.core.context;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * MVC配置
 * 
 * @author 聚合支付有限公司
 * @since 2016年2月23日
 * @version V1.0.0
 */
@Configuration
@Import({ HessianServerConfig.class })
@EnableWebMvc
public class SpringMVCConfig extends WebMvcConfigurerAdapter {

	/**
	 * 配置拦截器
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		super.addInterceptors(registry);
	}

}
