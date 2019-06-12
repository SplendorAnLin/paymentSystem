package com.yl.rate.core.context;

import com.lefu.commons.web.spring.ApplicationContextUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.support.ResourcePropertySource;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.*;
import java.io.IOException;
import java.util.EnumSet;

/**
 * Web应用初始化
 *
 * @author 聚合支付有限公司
 * @version V1.0.0
 * @since 2016年5月11日
 */
public class WebInitializer implements WebApplicationInitializer {
    private static final Logger logger = LoggerFactory.getLogger(WebInitializer.class);

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        // 创建Spring上下文
        AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
        rootContext.register(SpringRootConfig.class);
        try {
            rootContext.getEnvironment().getPropertySources().addFirst(new ResourcePropertySource("classpath:/environment.properties"));
        } catch (IOException e) {
            logger.error("", e);
        }
        // 管理Spring上下文的生命周期
        servletContext.addListener(new ContextLoaderListener(rootContext));
        // 将Spring上下文放入工具类
        ApplicationContextUtils.setApplicationContext(rootContext);
        // 字符编码过滤器
        FilterRegistration.Dynamic characterEncodingFilter = servletContext.addFilter("characterEncodingFilter", new CharacterEncodingFilter());
        characterEncodingFilter.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), false, "/*");
        characterEncodingFilter.setInitParameter("encoding", "UTF-8");
        characterEncodingFilter.setInitParameter("forceEncoding", "true");

        // 创建SpringMVC上下文
        AnnotationConfigWebApplicationContext dispatcherContext = new AnnotationConfigWebApplicationContext();
        dispatcherContext.register(SpringMVCConfig.class);
        // 注册SpringMVC分发器
        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("dispatcher", new DispatcherServlet(dispatcherContext));
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");
    }
}
