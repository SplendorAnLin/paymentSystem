package com.yl.risk.core.context;

import com.lefu.commons.web.spring.ApplicationContextUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.io.support.ResourcePropertySource;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.*;
import java.io.IOException;
import java.util.EnumSet;

/**
 * Web应用初始化
 *
 * @author 聚合支付有限公司
 * @since 2017年11月10日
 * @version V1.0.0
 */
public class  WebInitializer implements WebApplicationInitializer {
    private static final Logger logger = LoggerFactory.getLogger(WebInitializer.class);

    public void onStartup(ServletContext servletContext) throws ServletException {
        // 创建Spring上下文
        AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
        rootContext.register(SpringRootConfig.class);
        ConfigurableEnvironment environment = rootContext.getEnvironment();
        try {
             environment.getPropertySources().addLast(new ResourcePropertySource("classpath:/system.properties"));
        } catch (IOException e) {
            logger.error("", e);
        }
        // 管理Spring上下文的生命周期
        servletContext.addListener(new ContextLoaderListener(rootContext));
        // 将Spring上下文放入工具类
        ApplicationContextUtils.setApplicationContext(rootContext);
          // 字符编码过滤器
        org.springframework.web.filter.CharacterEncodingFilter encodingFilter = new org.springframework.web.filter.CharacterEncodingFilter();
        encodingFilter.setEncoding("UTF-8");
        encodingFilter.setForceEncoding(true);
        FilterRegistration.Dynamic characterEncodingFilter = servletContext.addFilter("characterEncodingFilter", encodingFilter);
        characterEncodingFilter.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), false, "/*");

        // 创建SpringMVC上下文
        AnnotationConfigWebApplicationContext dispatcherContext = new AnnotationConfigWebApplicationContext();
        dispatcherContext.register(SpringMVCConfig.class);
        // 注册SpringMVC分发器
        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("dispatcher", new DispatcherServlet(dispatcherContext));
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");
    }
}
