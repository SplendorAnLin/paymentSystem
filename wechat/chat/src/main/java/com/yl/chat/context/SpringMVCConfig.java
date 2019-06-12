package com.yl.chat.context;

import com.lefu.commons.web.springmvc.handler.SimpleForwardHttpRequestHandler;
import com.yl.chat.web.filter.AuthFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import org.springframework.web.servlet.handler.SimpleUrlHandlerMapping;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import java.util.List;
import java.util.Properties;

/**
 * Spring MVC配置
 *
 * @author rui
 * @since 2013年8月21日
 */
@Configuration
@EnableWebMvc
@Import({HessianServerConfig.class})
@ComponentScan(basePackages = "com.yl.chat", useDefaultFilters = false, includeFilters = @Filter(Controller.class))
public class SpringMVCConfig extends WebMvcConfigurerAdapter {

    /**
     * 配置资源文件处理
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/js/**").addResourceLocations("/js/");
        registry.addResourceHandler("/css/**").addResourceLocations("/css/");
        registry.addResourceHandler("/mobleGateway/**").addResourceLocations("/mobleGateway/");
        registry.addResourceHandler("/image/**").addResourceLocations("/image/");
        registry.addResourceHandler("/fonts/**").addResourceLocations("/fonts/");
        registry.addResourceHandler("/*.txt").addResourceLocations("/*.txt");
        super.addResourceHandlers(registry);
    }

    /**
     * 配置拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthFilter("/*"));
        super.addInterceptors(registry);
    }

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
        exceptionResolver.setExceptionAttribute("ex");
        return exceptionResolver;
    }

    /**
     * 上传文件解析
     *
     * @return
     */
    @Bean(name = "multipartResolver")
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver muliMultipartResolver = new CommonsMultipartResolver();
        muliMultipartResolver.setMaxUploadSize(2097152); //图片不能大于2M
        muliMultipartResolver.setDefaultEncoding("UTF-8");
        return muliMultipartResolver;
    }

    /*
     * 配置异常处理器
     */
    @Override
    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
        exceptionResolvers.add(simpleMappingExceptionResolver());
        super.configureHandlerExceptionResolvers(exceptionResolvers);
    }

    @Bean
    public SimpleForwardHttpRequestHandler simpleForwardHttpRequestHandler() {
        return new SimpleForwardHttpRequestHandler();
    }

    @Bean
    public SimpleUrlHandlerMapping simpleUrlHandlerMapping() {
        SimpleUrlHandlerMapping simpleUrlHandlerMapping = new SimpleUrlHandlerMapping();
        Properties mappings = new Properties();
        mappings.put("/**", simpleForwardHttpRequestHandler());
        simpleUrlHandlerMapping.setMappings(mappings);
        return simpleUrlHandlerMapping;
    }
}